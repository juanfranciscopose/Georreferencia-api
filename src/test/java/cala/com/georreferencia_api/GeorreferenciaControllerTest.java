package cala.com.georreferencia_api;

import cala.com.georreferencia_api.main.dto.GeorreferenciaDTO;
import cala.com.georreferencia_api.nota.dto.NotaDTO;
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

import java.util.List;

import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
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
        dto.setUgId(1L);

        mockMvc.perform(post("/api/personas/1/georreferencias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.calle").value("Calle Falsa 123"));
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
                .andExpect(jsonPath("$", hasSize(4)))
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

        mockMvc.perform(put("/api/georreferencias/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.calle").value("Nueva Avenida"));
    }

    @Test
    void testDeleteGeorreferencia() throws Exception {
        mockMvc.perform(delete("/api/personas/{personaId}/georreferencias/{georreferenciaId}", 1L, 2L)
            .contentType(MediaType.APPLICATION_JSON))
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
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void debeCrearGeorreferenciaConNotasYPersona() throws Exception {
        NotaDTO notaDto = new NotaDTO();
        notaDto.setTexto("Nota de prueba de integración");

        GeorreferenciaDTO dto = new GeorreferenciaDTO();
        dto.setCalle("Calle Nueva 123");
        dto.setUgId(1L);
        dto.setNotas(List.of(notaDto));

        mockMvc.perform(post("/api/personas/{personaId}/georreferencias", 99L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.calle").value("Calle Nueva 123"))
                .andExpect(jsonPath("$.notas", hasSize(1)))
                .andExpect(jsonPath("$.notas[0].texto").value("Nota de prueba de integración"));
    }

    @Test
    void debeFiltrarPorPersonaId() throws Exception {
        mockMvc.perform(get("/api/personas/{personaId}/georreferencias", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(2));
    }

    @Test
    void debeActualizarGeorreferencia() throws Exception {
        GeorreferenciaDTO updateDto = new GeorreferenciaDTO();
        updateDto.setCalle("Calle Modificada");

        mockMvc.perform(put("/api/georreferencias/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.calle").value("Calle Modificada"));
    }

    @Test
    void debeBorrarGeorreferenciaYRelacion() throws Exception {
        mockMvc.perform(delete("/api/personas/{pId}/georreferencias/{gId}", 1L, 2L))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/georreferencias/{id}", 2L))
                .andExpect(status().isNoContent());
    }

    @Test
    void debeSincronizarNotasEnUpdate() throws Exception {
        NotaDTO notaExistente = new NotaDTO();
        notaExistente.setId(50L);

        NotaDTO notaNueva = new NotaDTO();
        notaNueva.setTexto("Esta nota es totalmente nueva");

        GeorreferenciaDTO updateDto = new GeorreferenciaDTO();
        updateDto.setCalle("Calle Actualizada con Notas");
        updateDto.setNotas(List.of(notaExistente, notaNueva));

        // 2. Ejecutamos el Update
        mockMvc.perform(put("/api/georreferencias/10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notas", hasSize(2)))
                .andExpect(jsonPath("$.notas[?(@.id==50)]").exists())
                .andExpect(jsonPath("$.notas[?(@.texto=='Esta nota es totalmente nueva')]").exists());
    }

    @Test
    void debeFallarSiNotaNoExiste() throws Exception {
        NotaDTO notaFantasma = new NotaDTO();
        notaFantasma.setId(999L);

        GeorreferenciaDTO dto = new GeorreferenciaDTO();
        dto.setNotas(List.of(notaFantasma));

        mockMvc.perform(put("/api/georreferencias/10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNoContent());
    }

    @Test
    void debeBorrarTodoPorPersonaId() throws Exception {
        mockMvc.perform(delete("/api/personas/{idPersona}/georreferencias", 10L))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/georreferencias/10"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/georreferencias/11"))
                .andExpect(status().isNoContent());
    }

    @Test
    void borrarGeoNoBorraNota() throws Exception {
        mockMvc.perform(delete("/api/personas/{pId}/georreferencias/{gId}", 10L, 10L))
                .andExpect(status().isNoContent());
    }

    @Test
    void debeSincronizarNotasCompleto_MantenerAgregarYBorrar() throws Exception {
        
        NotaDTO notaAMantener = new NotaDTO();
        notaAMantener.setId(50L); 

        NotaDTO notaNueva = new NotaDTO();
        notaNueva.setTexto("Nota Creada en Update");

        GeorreferenciaDTO updateDto = new GeorreferenciaDTO();
        updateDto.setCalle("Calle Sincronizada");
        updateDto.setNotas(List.of(notaAMantener, notaNueva));

        mockMvc.perform(put("/api/georreferencias/10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        
        mockMvc.perform(get("/api/georreferencias/10"))
                .andExpect(jsonPath("$.notas", hasSize(2)))
                .andExpect(jsonPath("$.notas[?(@.id==50)]").exists())
                .andExpect(jsonPath("$.notas[?(@.id==51)]").doesNotExist())
                .andExpect(jsonPath("$.notas[?(@.texto=='Nota Creada en Update')]").exists())
                .andExpect(jsonPath("$.notas[?(@.texto=='Nota Creada en Update')].id").isNotEmpty());
   }

}