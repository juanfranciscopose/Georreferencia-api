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
@Transactional
public class GeorreferenciaInstitucionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testFindAllByIdInstitucion() throws Exception {
        mockMvc.perform(get("/api/instituciones/100/georreferencias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    void testFindAllByIdInstitucionNotFound() throws Exception {
        mockMvc.perform(get("/api/instituciones/999/georreferencias"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testCreateGeorreferenciaForInstitucion() throws Exception {
        GeorreferenciaDTO dto = new GeorreferenciaDTO();
        dto.setCalle("Calle Nueva Institucion");
        dto.setUgId(1L);
        dto.setLocalidadId(100L);
        dto.setBarrioId(10L);
        dto.setCiudadId(500L);
        dto.setProvinciaId(1);
        dto.setEstadoEdilicioId("1");

        mockMvc.perform(post("/api/instituciones/100/georreferencias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.calle").value("Calle Nueva Institucion"));
    }

    @Test
    void testDeleteGeorreferenciaFromInstitucion() throws Exception {
        mockMvc.perform(delete("/api/instituciones/100/georreferencias/1"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/instituciones/100/georreferencias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(2));
    }

    @Test
    void testDeleteAllGeorreferenciasFromInstitucion() throws Exception {
        mockMvc.perform(delete("/api/instituciones/100/georreferencias"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/instituciones/100/georreferencias"))
                .andExpect(status().isNoContent());
    }
}