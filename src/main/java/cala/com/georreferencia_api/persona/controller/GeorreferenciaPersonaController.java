package cala.com.georreferencia_api.persona.controller;

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

import cala.com.georreferencia_api.main.dto.GeorreferenciaDTO;
import cala.com.georreferencia_api.persona.service.GeorreferenciaPersonaService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GeorreferenciaPersonaController {
    
    @Autowired
    private GeorreferenciaPersonaService service;
    
    @GetMapping("/personas/{personaId}/georreferencias")
    public ResponseEntity<List<GeorreferenciaDTO>> findAllbyIdPersona(@PathVariable @NotNull Long personaId) {
        return ResponseEntity.ok(service.findByidPersona(personaId));
    }

    @PostMapping("/personas/{personaId}/georreferencias")
    public ResponseEntity<GeorreferenciaDTO> create(@PathVariable @NotNull Long personaId, @RequestBody GeorreferenciaDTO dto) {
        return ResponseEntity.ok(service.create(personaId, dto));
    }

    @DeleteMapping("/personas/{idPersona}/georreferencias/{idGeorreferencia}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull Long idPersona,@PathVariable @NotNull Long idGeorreferencia) {
        service.delete(idPersona, idGeorreferencia);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/personas/{idPersona}/georreferencias")
    public ResponseEntity<Void> deleteByIdPersona(@PathVariable @NotNull Long idPersona) {
        service.deleteByIdPersona(idPersona);
        return ResponseEntity.noContent().build();
    }

}
