package cala.com.georreferencia_api.institucion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cala.com.georreferencia_api.exceptions.EntityNotFoundException;
import cala.com.georreferencia_api.institucion.entity.GeorreferenciaInstitucion;
import cala.com.georreferencia_api.institucion.entity.ParametriaId;
import cala.com.georreferencia_api.institucion.repository.GeorreferenciaInstitucionRepository;
import cala.com.georreferencia_api.main.dto.GeorreferenciaDTO;
import cala.com.georreferencia_api.main.entity.Georreferencia;
import cala.com.georreferencia_api.main.repository.GeorreferenciaRepository;
import cala.com.georreferencia_api.main.service.GeorreferenciaService;

@Service
public class GeorreferenciaInstitucionServiceImpl implements GeorreferenciaInstitucionService{

    @Autowired
    private GeorreferenciaRepository repository;

    @Autowired 
    private GeorreferenciaInstitucionRepository georreferenciaInstitucionRepository;

    @Autowired
    private GeorreferenciaService service;

    @Override
    public List<GeorreferenciaDTO> findByidInstitucion(Long idInstitucion) {
        List<GeorreferenciaInstitucion> geoInstituciones = georreferenciaInstitucionRepository.findByInstitucionId(idInstitucion);
        if (geoInstituciones.isEmpty()) {
            throw new EntityNotFoundException("Georreferencias no encontrada para la institución: " + idInstitucion);
        }
        return geoInstituciones.stream()
            .map(geoInstitucion -> repository.findById(geoInstitucion.getGeorreferenciaId())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró geo con ID: " + geoInstitucion.getGeorreferenciaId())))
            .map(Georreferencia::toDTO)
            .toList();
    }

    @Override
    public GeorreferenciaDTO create(Long idInstitucion, GeorreferenciaDTO georreferenciaDTO) {
        GeorreferenciaDTO geoDTO = service.create(georreferenciaDTO);
        GeorreferenciaInstitucion geoInst = new GeorreferenciaInstitucion(geoDTO.getId(), idInstitucion);
        georreferenciaInstitucionRepository.save(geoInst);
        return geoDTO;
    }

    @Override
    public void delete(Long idInstitucion, Long idGeorreferencia) {
        georreferenciaInstitucionRepository.deleteById(new ParametriaId(idInstitucion, idGeorreferencia));
        service.delete(idGeorreferencia);
    }

    @Override
    public void deleteByIdInstitucion(Long idInstitucion) {
        List<GeorreferenciaInstitucion> geoInstituciones = georreferenciaInstitucionRepository.findByInstitucionId(idInstitucion);
        if (geoInstituciones.isEmpty()) {
            throw new EntityNotFoundException("Georreferencias no encontrada para la institución: " + idInstitucion);
        }
        georreferenciaInstitucionRepository.deleteByInstitucionId(idInstitucion);
        geoInstituciones.stream()
            .map(geoInstitucion -> repository.findById(geoInstitucion.getGeorreferenciaId())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró Georreferencias con ID: " + geoInstitucion.getGeorreferenciaId())))
            .forEach(p -> service.delete(p.getId()));
    }
    
}
