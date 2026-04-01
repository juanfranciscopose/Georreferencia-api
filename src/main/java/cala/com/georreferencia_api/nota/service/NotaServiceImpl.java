package cala.com.georreferencia_api.nota.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cala.com.georreferencia_api.exceptions.EntityNotFoundException;
import cala.com.georreferencia_api.nota.dto.NotaDTO;
import cala.com.georreferencia_api.nota.entity.Nota;
import cala.com.georreferencia_api.nota.repository.NotaRepository;

@Service
public class NotaServiceImpl implements NotaService{

    @Autowired
    private NotaRepository repository;

    @Override
    public NotaDTO deleteLogico(Long id) {
        Nota nota = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Nota no encontrada: " + id));
        nota.setDelete(true);
        Nota updated = repository.save(nota);
        return updated.toDto();
    }

    @Override
    public NotaDTO altaLogica(Long id) {
       Nota nota = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Nota no encontrada: " + id));
        nota.setDelete(false);
        Nota updated = repository.save(nota);
        return updated.toDto();
    }

    @Override
    public NotaDTO findById(Long id) {
        return repository.findById(id)
                .map(Nota::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Nota no encontrada: " + id));
    }
    
}
