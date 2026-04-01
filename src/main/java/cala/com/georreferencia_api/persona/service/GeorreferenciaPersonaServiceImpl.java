package cala.com.georreferencia_api.persona.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cala.com.georreferencia_api.exceptions.EntityNotFoundException;
import cala.com.georreferencia_api.main.dto.GeorreferenciaDTO;
import cala.com.georreferencia_api.main.entity.Georreferencia;
import cala.com.georreferencia_api.main.repository.GeorreferenciaRepository;
import cala.com.georreferencia_api.main.service.GeorreferenciaService;
import cala.com.georreferencia_api.persona.entity.GeorreferenciaPersona;
import cala.com.georreferencia_api.persona.entity.ParametriaId;
import cala.com.georreferencia_api.persona.repository.GeorreferenciaPersonaRepository;

@Service
public class GeorreferenciaPersonaServiceImpl implements GeorreferenciaPersonaService{

    @Autowired
    private GeorreferenciaRepository repository;

    @Autowired 
    private GeorreferenciaPersonaRepository georreferenciaPersonaRepository;

    @Autowired
    private GeorreferenciaService service;

    @Override
    public List<GeorreferenciaDTO> findByidPersona(Long idPersona) {
        List<GeorreferenciaPersona> geoPersonas = georreferenciaPersonaRepository.findByPersonaId(idPersona);
        if (geoPersonas.isEmpty()) {
            throw new EntityNotFoundException("Georreferencias no encontrada para la persona: " + idPersona);
        }
        return geoPersonas.stream()
            .map(geoPersona -> repository.findById(geoPersona.getGeorreferenciaId())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró geo con ID: " + geoPersona.getGeorreferenciaId())))
            .map(Georreferencia::toDTO)
            .toList();
    }

    @Override
    public GeorreferenciaDTO create(Long idPersona, GeorreferenciaDTO georreferenciaDTO) {
        GeorreferenciaDTO geoDTO = service.create(georreferenciaDTO);
        GeorreferenciaPersona geoPer = new GeorreferenciaPersona(geoDTO.getId(), idPersona);
        georreferenciaPersonaRepository.save(geoPer);
        return geoDTO;
    }

    @Override
    public void delete(Long idPersona, Long idGeorreferencia) {
        georreferenciaPersonaRepository.deleteById(new ParametriaId(idPersona, idGeorreferencia));
        service.delete(idGeorreferencia);
    }

    @Override
    public void deleteByIdPersona(Long idPersona) {
        List<GeorreferenciaPersona> geoPersonas = georreferenciaPersonaRepository.findByPersonaId(idPersona);
        if (geoPersonas.isEmpty()) {
            throw new EntityNotFoundException("Georreferencias no encontrada para la persona: " + idPersona);
        }
        georreferenciaPersonaRepository.deleteByPersonaId(idPersona);
        geoPersonas.stream()
            .map(geoPersona -> repository.findById(geoPersona.getGeorreferenciaId())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró Georreferencias con ID: " + geoPersona.getGeorreferenciaId())))
            .forEach(p -> service.delete(p.getId()));
    }
    
}
