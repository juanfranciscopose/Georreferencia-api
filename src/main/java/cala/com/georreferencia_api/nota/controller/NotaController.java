package cala.com.georreferencia_api.nota.controller;

import cala.com.georreferencia_api.nota.dto.NotaDTO;
import cala.com.georreferencia_api.nota.service.NotaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notas")
@RequiredArgsConstructor
public class NotaController {

    private final NotaService service;

    @GetMapping("/{id}")
    public ResponseEntity<NotaDTO> getById(@PathVariable Long id) {
        // Asumiendo que agregaste findById a tu NotaService
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}/desactivar")
    public ResponseEntity<NotaDTO> darDeBajaLogica(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteLogico(id));
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<NotaDTO> darDeAltaLogica(@PathVariable Long id) {
        return ResponseEntity.ok(service.altaLogica(id));
    }
}
