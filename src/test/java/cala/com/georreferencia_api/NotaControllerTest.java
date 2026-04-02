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

}
