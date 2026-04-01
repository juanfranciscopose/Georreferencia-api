package cala.com.georreferencia_api.ug.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import cala.com.georreferencia_api.ug.dto.UgDTO;
import cala.com.georreferencia_api.ug.dto.UgQuery;

public interface UgService {

    UgDTO create(UgDTO dto);

    UgDTO update(Long id, UgDTO dto);

    void delete(Long id);

    UgDTO findById(Long id);

    List<UgDTO> findAll(UgQuery query, Pageable page);
    
}