package cala.com.georreferencia_api.ug.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cala.com.georreferencia_api.exceptions.EntityNotFoundException;
import cala.com.georreferencia_api.nota.dto.NotaDTO;
import cala.com.georreferencia_api.nota.entity.Nota;
import cala.com.georreferencia_api.nota.repository.NotaRepository;
import cala.com.georreferencia_api.ug.dto.UgDTO;
import cala.com.georreferencia_api.ug.dto.UgQuery;
import cala.com.georreferencia_api.ug.entity.Ug;
import cala.com.georreferencia_api.ug.repository.UgRepository;
import cala.com.georreferencia_api.ug.specification.ObtenerUgSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UgServiceImpl implements UgService {

    private final UgRepository repository;

    private final NotaRepository notaRepository;
    
    @Override
    public List<UgDTO> findAll(UgQuery query, Pageable page) {
        var spec = ObtenerUgSpecification.build(query);
        Page<Ug> ugs = repository.findAll(spec, page);
        return ugs.stream().map(Ug::toDTO).toList();
    }

    @Override
    public UgDTO findById(Long id) {
        Ug ug = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Ug no encontrado: " + id));
        return ug.toDTO();
    }

    @Override
    public UgDTO create(UgDTO dto) {
        Ug ug = new Ug();
        ug.setId(null);
        ug.setNombreCorto(dto.getNombreCorto());
        ug.setNombreLargo(dto.getNombreLargo());
        ug.setAccesos(dto.getAccesos());
        ug.setDescripcion(dto.getDescripcion());
        ug.setLocalidadId(dto.getLocalidadId());
        ug.setBarrioId(dto.getBarrioId());
        ug.setCiudadId(dto.getCiudadId());
        ug.setProvinciaId(dto.getProvinciaId());
        ug.setFechaCreacion(LocalDateTime.now());
        ug.setFechaActualizacion(null);
        ug.setDestacado(dto.getDestacado());
        // Manejo de Notas en la creación
        if (dto.getNotas() != null && !dto.getNotas().isEmpty()) {
            dto.getNotas().forEach(notaDto -> {
                Nota nota = procesarNota(notaDto);
                ug.addNota(nota);
            });
        }
        return repository.save(ug).toDTO();
    }

    @Override
    public UgDTO update(Long id, UgDTO dto) {
        Ug ug = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Ug no encontrado: " + id));
        ug.setNombreCorto(dto.getNombreCorto());
        ug.setNombreLargo(dto.getNombreLargo());
        ug.setAccesos(dto.getAccesos());
        ug.setDescripcion(dto.getDescripcion());
        ug.setLocalidadId(dto.getLocalidadId());
        ug.setBarrioId(dto.getBarrioId());
        ug.setCiudadId(dto.getCiudadId());
        ug.setProvinciaId(dto.getProvinciaId());
        ug.setFechaActualizacion(LocalDateTime.now());
        ug.setDestacado(dto.getDestacado());
        // Sincronización de Notas
        if (dto.getNotas() != null) {
            // 1. Limpiamos las relaciones actuales
            ug.getNotas().clear();
            // 2. Agregamos las notas del DTO
            dto.getNotas().forEach(notaDto -> {
                Nota nota = procesarNota(notaDto);
                ug.addNota(nota);
            });
        }
        return repository.save(ug).toDTO();
    }

    /**
     * Método privado para decidir si la nota es nueva o una existente
     */
    private Nota procesarNota(NotaDTO dto) {
        if (dto.getId() != null) {
            return notaRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Nota no encontrada: " + dto.getId()));
        } else {
            Nota nuevaNota = new Nota();
            nuevaNota.setTexto(dto.getTexto());
            nuevaNota.setFechaCreacion(LocalDateTime.now());
            nuevaNota.setDelete(false);
            return notaRepository.save(nuevaNota);
        }
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Ug no encontrado: " + id);
        }
        repository.deleteById(id);
    }

}
