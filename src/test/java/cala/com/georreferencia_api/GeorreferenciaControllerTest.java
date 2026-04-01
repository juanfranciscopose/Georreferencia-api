package cala.com.georreferencia_api;

import cala.com.georreferencia_api.main.dto.GeorreferenciaDTO;
import tools.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional // Limpia la base de datos después de cada test
public class GeorreferenciaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateGeorreferencia() throws Exception {
        GeorreferenciaDTO dto = new GeorreferenciaDTO();
        dto.setId(10L);
        dto.setCalle("Calle Falsa 123");
        dto.setEstadoEdilicio("BUENO");
        dto.setUgId(1L);

        mockMvc.perform(post("/api/georreferencias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.calle").value("Calle Falsa 123"))
                .andExpect(jsonPath("$.estadoEdilicio").value("BUENO"));
    }

    @Test
    void testGetById() throws Exception {
        mockMvc.perform(get("/api/georreferencias/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.calle").value("Calle 50"));
    }

    @Test
    void debeFiltrarGeorreferenciasPorUgId() throws Exception {
        mockMvc.perform(get("/api/georreferencias")
                .param("ugId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].ugId").value(1))
                .andExpect(jsonPath("$[1].ugId").value(1));
    }

    @Test
    void debeFiltrarGeorreferenciasPorLocalidadYBarrio() throws Exception {
        mockMvc.perform(get("/api/georreferencias")
                .param("localidadId", "100")
                .param("barrioId", "20")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(3));
    }

    @Test
    void testUpdateGeorreferencia() throws Exception {
        GeorreferenciaDTO updateDto = new GeorreferenciaDTO();
        updateDto.setCalle("Nueva Avenida");
        updateDto.setEstadoEdilicio("EXCELENTE");

        mockMvc.perform(put("/api/georreferencias/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.calle").value("Nueva Avenida"));
    }

    @Test
    void testDeleteGeorreferencia() throws Exception {
        mockMvc.perform(delete("/api/georreferencias/2"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/georreferencias/2"))
                .andExpect(status().isNoContent()); 
    }

    @Test
    void debeRetornarListaPaginadaYOrdenada() throws Exception {
        mockMvc.perform(get("/api/georreferencias")
                .param("page", "0")
                .param("size", "2")
                .param("sort", "id,desc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(3));
    }
}