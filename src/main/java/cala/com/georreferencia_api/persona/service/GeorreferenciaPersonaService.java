package cala.com.georreferencia_api.persona.service;

import java.util.List;

import cala.com.georreferencia_api.main.dto.GeorreferenciaDTO;

public interface GeorreferenciaPersonaService {
    
    List<GeorreferenciaDTO> findByidPersona(Long idPersona);

    GeorreferenciaDTO create(Long idPersona, GeorreferenciaDTO georreferenciaDTO);
    
    void delete(Long idPersona, Long idGeorreferencia);

    void deleteByIdPersona(Long idPersona);
}
