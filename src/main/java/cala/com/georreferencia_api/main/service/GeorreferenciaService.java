package cala.com.georreferencia_api.main.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import cala.com.georreferencia_api.main.dto.GeorreferenciaDTO;
import cala.com.georreferencia_api.main.dto.GeorreferenciaQuery;

public interface GeorreferenciaService {

    List<GeorreferenciaDTO> findAll(GeorreferenciaQuery query, Pageable page);

    GeorreferenciaDTO findById(Long id);

    GeorreferenciaDTO create(GeorreferenciaDTO dto);

    GeorreferenciaDTO update(Long id, GeorreferenciaDTO dto);

    void delete(Long id);

}