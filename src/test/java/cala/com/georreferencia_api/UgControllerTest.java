package cala.com.georreferencia_api;

import cala.com.georreferencia_api.ug.dto.UgDTO;
import tools.jackson.databind.ObjectMapper;
import static org.hamcrest.Matchers.hasSize;
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

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class UgControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateUg() throws Exception {
        UgDTO dto = new UgDTO();
        dto.setId(3L);
        dto.setNombreCorto("UG-TEST");
        dto.setNombreLargo("Unidad de Prueba");
        dto.setLocalidadId(100L);

        mockMvc.perform(post("/api/ugs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreCorto").value("UG-TEST"));
    }

    @Test
    void testGetUgById() throws Exception {
        mockMvc.perform(get("/api/ugs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreCorto").value("UG-CENTRO-1"));
    }

    @Test
    void testUpdateUg() throws Exception {
        UgDTO updateDto = new UgDTO();
        updateDto.setNombreCorto("UG-MOD");
        updateDto.setNombreLargo("Nombre Modificado");
        updateDto.setLocalidadId(100L);

        mockMvc.perform(put("/api/ugs/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreCorto").value("UG-MOD"))
                .andExpect(jsonPath("$.nombreLargo").value("Nombre Modificado"));
    }

    @Test
    void testDeleteUg() throws Exception {
        mockMvc.perform(delete("/api/ugs/2"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/ugs/2"))
                .andExpect(status().isNoContent());
    }

    @Test
    void debeFiltrarUgsPorLocalidad() throws Exception {
        mockMvc.perform(get("/api/ugs")
                .param("localidadId", "100")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void debeFiltrarUgsPorDestacado() throws Exception {
        mockMvc.perform(get("/api/ugs")
                .param("destacado", "true")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void debeRetornarVacioCuandoNoCoincidenFiltros() throws Exception {
        mockMvc.perform(get("/api/ugs")
                .param("barrioId", "99999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}