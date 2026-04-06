package cala.com.georreferencia_api.institucion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ItemParametriaDTO {
    private Long institucionId;
    private Long georreferenciaId;
}
