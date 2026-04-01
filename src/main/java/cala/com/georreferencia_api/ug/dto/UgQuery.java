package cala.com.georreferencia_api.ug.dto;

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
public class UgQuery {
    private Long barrioId;
    private Long localidadId;
    private Long ciudadId;
    private Integer provinciaId;
    private Boolean destacado;
}
