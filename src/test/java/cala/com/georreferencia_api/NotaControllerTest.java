package cala.com.georreferencia_api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
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
    void debeDesactivarNota() throws Exception {
        mockMvc.perform(put("/api/notas/{id}/desactivar", 50L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.delete").value(true));
    }

    @Test
    void debeActivarNota() throws Exception {
        // Primero aseguramos que esté en true (o usamos una que sepamos que está en true)
        mockMvc.perform(put("/api/notas/{id}/activar", 50L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.delete").value(false));
    }
       
    @Test
    void debeCancelarRecordatorio() throws Exception {
        // Cancela el recordatorio de la nota
        mockMvc.perform(put("/api/notas/{id}/cancelar-recordatorio", 998))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(998))
                .andExpect(jsonPath("$.recordatorioCancelado").value(true));
    }

    @Test
    void debePostponerRecordatorio() throws Exception {
        // Postpone el recordatorio 5 días, 2 horas y 30 minutos
        mockMvc.perform(put("/api/notas/{id}/postponer-recordatorio/{dias}/{horas}/{minutos}", 998, 5, 2, 30))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(998))
                .andExpect(jsonPath("$.fechaRecordatorio").isNotEmpty());
    }

    @Test
    void debeObtenerNotaConCamposRecordatorio() throws Exception {
        // Verifica que la nota incluya los nuevos campos fechaRecordatorio y recordatorioCancelado
        mockMvc.perform(get("/api/notas/{id}", 998))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(998))
                .andExpect(jsonPath("$.fechaRecordatorio").isNotEmpty())
                .andExpect(jsonPath("$.recordatorioCancelado").value(false));
    }
}
