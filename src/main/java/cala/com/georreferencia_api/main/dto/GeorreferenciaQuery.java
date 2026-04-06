package cala.com.georreferencia_api.main.dto;

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
public class GeorreferenciaQuery {
    private Long ugId;
    private Long localidadId;
    private Long barrioId;
    private Long ciudadId;
    private Integer provinciaId;
    private Integer estadoEdilicioId;
}
