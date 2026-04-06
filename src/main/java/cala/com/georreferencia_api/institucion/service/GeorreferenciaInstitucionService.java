package cala.com.georreferencia_api.institucion.service;

import java.util.List;

import cala.com.georreferencia_api.main.dto.GeorreferenciaDTO;

public interface GeorreferenciaInstitucionService {
    
    List<GeorreferenciaDTO> findByidInstitucion(Long idInstitucion);

    GeorreferenciaDTO create(Long idInstitucion, GeorreferenciaDTO georreferenciaDTO);
    
    void delete(Long idInstitucion, Long idGeorreferencia);

    void deleteByIdInstitucion(Long idInstitucion);
}
