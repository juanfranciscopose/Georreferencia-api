package cala.com.georreferencia_api.persona.dto;

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
    private Long personaId;
    private Long georreferenciaId;
}
