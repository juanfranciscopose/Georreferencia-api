package cala.com.georreferencia_api.persona.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ParametriaId implements Serializable{
    private Long personaId;
    private Long georreferenciaId;
}
