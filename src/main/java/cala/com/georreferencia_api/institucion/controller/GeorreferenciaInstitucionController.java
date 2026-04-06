package cala.com.georreferencia_api.institucion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cala.com.georreferencia_api.institucion.service.GeorreferenciaInstitucionService;
import cala.com.georreferencia_api.main.dto.GeorreferenciaDTO;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GeorreferenciaInstitucionController {
    
    @Autowired
    private GeorreferenciaInstitucionService service;
    
    @GetMapping("/instituciones/{institucionId}/georreferencias")
    public ResponseEntity<List<GeorreferenciaDTO>> findAllbyIdInstitucion(@PathVariable @NotNull Long institucionId) {
        return ResponseEntity.ok(service.findByidInstitucion(institucionId));
    }

    @PostMapping("/instituciones/{institucionId}/georreferencias")
    public ResponseEntity<GeorreferenciaDTO> create(@PathVariable @NotNull Long institucionId, @RequestBody GeorreferenciaDTO dto) {
        return ResponseEntity.ok(service.create(institucionId, dto));
    }

    @DeleteMapping("/instituciones/{idInstitucion}/georreferencias/{idGeorreferencia}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull Long idInstitucion,@PathVariable @NotNull Long idGeorreferencia) {
        service.delete(idInstitucion, idGeorreferencia);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/instituciones/{idInstitucion}/georreferencias")
    public ResponseEntity<Void> deleteByIdInstitucion(@PathVariable @NotNull Long idInstitucion) {
        service.deleteByIdInstitucion(idInstitucion);
        return ResponseEntity.noContent().build();
    }

}
