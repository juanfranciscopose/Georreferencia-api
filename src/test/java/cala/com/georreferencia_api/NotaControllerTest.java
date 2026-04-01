package cala.com.georreferencia_api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class NotaControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Debe dar de baja lógica a una nota (delete = true)")
    void debeDesactivarNota() throws Exception {
        mockMvc.perform(put("/api/notas/{id}/desactivar", 50L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.delete").value(true));
    }

    @Test
    @DisplayName("Debe dar de alta lógica a una nota (delete = false)")
    void debeActivarNota() throws Exception {
        // Primero aseguramos que esté en true (o usamos una que sepamos que está en true)
        mockMvc.perform(put("/api/notas/{id}/activar", 50L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.delete").value(false));
    }

    @Test
    @DisplayName("Verificar integridad: Borrar Georreferencia NO debe eliminar la Nota física")
    void verificarNotaSobreviveABorradoGeo() throws Exception {
        // 1. Verificamos que la nota 50 existe
        mockMvc.perform(get("/api/notas/50"))
                .andExpect(status().isOk());

        // 2. Borramos la Georreferencia 10 (que usa la nota 50)
        mockMvc.perform(delete("/api/personas/10/georreferencias/10"))
                .andExpect(status().isNoContent());

        // 3. LA PRUEBA DE FUEGO: La nota 50 debe seguir existiendo en su propia tabla
        mockMvc.perform(get("/api/notas/50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.texto").value("Nota preexistente importante"));
    }
}
