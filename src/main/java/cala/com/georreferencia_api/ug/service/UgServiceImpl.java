package cala.com.georreferencia_api.ug.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cala.com.georreferencia_api.exceptions.EntityNotFoundException;
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
        return repository.save(ug).toDTO();
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Ug no encontrado: " + id);
        }
        repository.deleteById(id);
    }

}
