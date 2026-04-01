package cala.com.georreferencia_api.ug.controller;

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

import cala.com.georreferencia_api.ug.dto.UgDTO;
import cala.com.georreferencia_api.ug.dto.UgQuery;
import cala.com.georreferencia_api.ug.service.UgService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ugs")
@RequiredArgsConstructor
public class UgController {
    
    private final UgService service;

    @GetMapping
    public ResponseEntity<List<UgDTO>> findAll(UgQuery query, Pageable page) {
        return ResponseEntity.ok(service.findAll(query, page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UgDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<UgDTO> create(@RequestBody UgDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UgDTO> update(@PathVariable Long id, @RequestBody UgDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
