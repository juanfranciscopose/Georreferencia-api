package cala.com.georreferencia_api.main.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import cala.com.georreferencia_api.nota.dto.NotaDTO;
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
public class GeorreferenciaDTO {
    private Long id;
    private String estadoEdilicio;
    private Boolean edificioElectoral;
    private String calle;
    private String entre1;
    private String entre2;
    private String numero;
    private String depto;
    private String observaciones;
    private Long ugId;
    private Long localidadId;
    private Long barrioId;
    private Long ciudadId;
    private Integer provinciaId;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private Integer estadoEdilicioId;
    private List<NotaDTO> notas = new ArrayList<>();
}
