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
import cala.com.georreferencia_api.nota.dto.NotaDTO;
import cala.com.georreferencia_api.nota.entity.Nota;
import cala.com.georreferencia_api.nota.repository.NotaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class GeorreferenciaServiceImpl implements GeorreferenciaService {

    private final GeorreferenciaRepository repository;

    private final NotaRepository notaRepository;

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
    @Transactional
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
        // Manejo de Notas
        if (dto.getNotas() != null && !dto.getNotas().isEmpty()) {
            dto.getNotas().forEach(notaDto -> {
                // procesarNota ya no debería hacer .save() interno
                Nota nota = procesarNota(notaDto);
                georreferencia.addNota(nota);
            });
        }
        Georreferencia saved = repository.save(georreferencia);
        repository.flush();
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
        // Sincronización de Notas
        if (dto.getNotas() != null) {
            georreferencia.getNotas().clear();
            dto.getNotas().forEach(notaDto -> {
                Nota nota = procesarNota(notaDto);
                georreferencia.addNota(nota);
            });
        }
        Georreferencia updated = repository.save(georreferencia);
        repository.flush();
        return updated.toDTO();
    }

    private Nota procesarNota(NotaDTO dto) {
        if (dto.getId() != null) {
            return notaRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Nota no encontrada: " + dto.getId()));
        } else {
            Nota nuevaNota = new Nota();
            nuevaNota.setTexto(dto.getTexto());
            nuevaNota.setFechaCreacion(LocalDateTime.now());
            nuevaNota.setDelete(false);
            return nuevaNota;
        }
    }

    @Override
    public void delete(Long id) {
        Georreferencia geo = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Georreferencia no encontrada: " + id));
        
        geo.getNotas().clear();
        repository.delete(geo);
    }
}
