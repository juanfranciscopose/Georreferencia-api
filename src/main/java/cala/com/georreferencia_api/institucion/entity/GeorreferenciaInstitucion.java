package cala.com.georreferencia_api.institucion.entity;

import cala.com.georreferencia_api.institucion.dto.ItemParametriaDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "georreferencias_instituciones", schema = "data")
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ParametriaId.class)
@Getter
@Setter
public class GeorreferenciaInstitucion {
    @Id
    private Long institucionId;
    @Id
    private Long georreferenciaId;

    public  ItemParametriaDTO toDto(){
        return new ItemParametriaDTO(this.getInstitucionId(), this.getGeorreferenciaId());
    }
}
