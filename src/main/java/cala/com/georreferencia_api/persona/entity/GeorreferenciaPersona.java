package cala.com.georreferencia_api.persona.entity;

import cala.com.georreferencia_api.persona.dto.ItemParametriaDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "georreferencias_personas", schema = "data")
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ParametriaId.class)
@Getter
@Setter
public class GeorreferenciaPersona {
    @Id
    private Long personaId;
    @Id
    private Long georreferenciaId;

    public  ItemParametriaDTO toDto(){
        return new ItemParametriaDTO(this.getPersonaId(), this.getGeorreferenciaId());
    }
}
