package cala.com.georreferencia_api.nota.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotaDTO {
    private Long id;
    private String texto;
    private LocalDateTime fechaCreacion;
    private Boolean delete;
    private String codigoRecordatorio;
}
