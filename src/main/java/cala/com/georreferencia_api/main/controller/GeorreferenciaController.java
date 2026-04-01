package cala.com.georreferencia_api.main.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cala.com.georreferencia_api.main.dto.GeorreferenciaDTO;
import cala.com.georreferencia_api.main.dto.GeorreferenciaQuery;
import cala.com.georreferencia_api.main.service.GeorreferenciaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/georreferencias")
@RequiredArgsConstructor
public class GeorreferenciaController {
    
    private final GeorreferenciaService service;
    
    @GetMapping("/{id}")
    public ResponseEntity<GeorreferenciaDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<GeorreferenciaDTO>> findAll(GeorreferenciaQuery query, Pageable page) {
        return ResponseEntity.ok(service.findAll(query, page));
    }

    @PostMapping
    public ResponseEntity<GeorreferenciaDTO> create(@RequestBody GeorreferenciaDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeorreferenciaDTO> update(@PathVariable Long id, @RequestBody GeorreferenciaDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
