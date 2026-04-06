package cala.com.georreferencia_api.nota.service;

import cala.com.georreferencia_api.nota.dto.NotaDTO;

public interface NotaService {  
    public NotaDTO findById(Long id);
    public NotaDTO deleteLogico(Long id);
    public NotaDTO altaLogica(Long id);
    public NotaDTO cancelarRecordatorio(Long id);
    public NotaDTO postponerRecordatorio(Long id, int dias, int horas, int minutos);
}
