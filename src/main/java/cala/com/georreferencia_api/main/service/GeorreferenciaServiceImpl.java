package cala.com.georreferencia_api.main.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cala.com.georreferencia_api.exceptions.EntityNotFoundException;
import cala.com.georreferencia_api.main.dto.GeorreferenciaDTO;
import cala.com.georreferencia_api.main.dto.GeorreferenciaQuery;
import cala.com.georreferencia_api.main.entity.Georreferencia;
import cala.com.georreferencia_api.main.repository.GeorreferenciaRepository;
import cala.com.georreferencia_api.main.specification.ObtenerGeorreferenciaSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class GeorreferenciaServiceImpl implements GeorreferenciaService {

    private final GeorreferenciaRepository repository;

    @Override
    public GeorreferenciaDTO findById(Long id) {
        Georreferencia georreferencia = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Georreferencia no encontrada: " + id));
        return georreferencia.toDTO();
    }

    @Override
    public List<GeorreferenciaDTO> findAll(GeorreferenciaQuery query, Pageable page) {
        var spec = ObtenerGeorreferenciaSpecification.build(query);
        Page<Georreferencia> georreferencias = repository.findAll(spec, page);
        return georreferencias.stream()
            .map(Georreferencia::toDTO)
            .toList();
    }

    @Override
    public GeorreferenciaDTO create(GeorreferenciaDTO dto) {
        Georreferencia georreferencia = new Georreferencia();
        georreferencia.setId(null);
        georreferencia.setEstadoEdilicio(dto.getEstadoEdilicio());
        georreferencia.setEdificioElectoral(dto.getEdificioElectoral());
        georreferencia.setCalle(dto.getCalle());
        georreferencia.setEntre1(dto.getEntre1());
        georreferencia.setEntre2(dto.getEntre2());
        georreferencia.setNumero(dto.getNumero());
        georreferencia.setDepto(dto.getDepto());
        georreferencia.setObservaciones(dto.getObservaciones());
        georreferencia.setBarrioId(dto.getBarrioId());
        georreferencia.setLocalidadId(dto.getLocalidadId());
        georreferencia.setCiudadId(dto.getCiudadId());
        georreferencia.setUgId(dto.getUgId());
        georreferencia.setProvinciaId(dto.getProvinciaId());
        georreferencia.setFechaCreacion(LocalDateTime.now());
        georreferencia.setFechaActualizacion(null);
        Georreferencia saved = repository.save(georreferencia);
        return saved.toDTO();
    }

    @Override
    public GeorreferenciaDTO update(Long id, GeorreferenciaDTO dto) {
        Georreferencia georreferencia = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Georreferencia no encontrada: " + id));
        georreferencia.setEstadoEdilicio(dto.getEstadoEdilicio());
        georreferencia.setEdificioElectoral(dto.getEdificioElectoral());
        georreferencia.setCalle(dto.getCalle());
        georreferencia.setEntre1(dto.getEntre1());
        georreferencia.setEntre2(dto.getEntre2());
        georreferencia.setNumero(dto.getNumero());
        georreferencia.setDepto(dto.getDepto());
        georreferencia.setObservaciones(dto.getObservaciones());
        georreferencia.setBarrioId(dto.getBarrioId());
        georreferencia.setLocalidadId(dto.getLocalidadId());
        georreferencia.setCiudadId(dto.getCiudadId());
        georreferencia.setUgId(dto.getUgId());
        georreferencia.setProvinciaId(dto.getProvinciaId());
        georreferencia.setFechaActualizacion(LocalDateTime.now());
        Georreferencia updated = repository.save(georreferencia);
        return updated.toDTO();
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Georreferencia no encontrada: " + id);
        }
        repository.deleteById(id);
    }
}
