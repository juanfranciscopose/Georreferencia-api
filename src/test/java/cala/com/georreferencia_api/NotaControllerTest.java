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
        mockMvc.perform(put("/api/notas/{id}/activar", 50L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.delete").value(false));
    }
       
    @Test
    void debeCancelarRecordatorio() throws Exception {
        mockMvc.perform(put("/api/notas/{id}/cancelar-recordatorio", 998))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(998))
                .andExpect(jsonPath("$.recordatorioCancelado").value(true));
    }

    @Test
    void debePostponerRecordatorio() throws Exception {
        mockMvc.perform(put("/api/notas/{id}/postponer-recordatorio/{dias}/{horas}/{minutos}", 998, 5, 2, 30))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(998))
                .andExpect(jsonPath("$.fechaRecordatorio").isNotEmpty());
    }

    @Test
    void debeObtenerNotaConCamposRecordatorio() throws Exception {
        mockMvc.perform(get("/api/notas/{id}", 998))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(998))
                .andExpect(jsonPath("$.fechaRecordatorio").isNotEmpty())
                .andExpect(jsonPath("$.recordatorioCancelado").value(false));
    }
}
