package cala.com.georreferencia_api.ug.entity;

import java.time.LocalDateTime;

import cala.com.georreferencia_api.ug.dto.UgDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ugs", schema = "data")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Ug {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ug_gen")
    @SequenceGenerator(
        name = "ug_gen", 
        sequenceName = "data.ugs_seq", 
        allocationSize = 0)
    @Column
    private Long id;
    
    @Column(length = 20, nullable = false)
    private String nombreCorto;
    
    @Column(length = 150, nullable = false)
    private String nombreLargo;

    @Column(length = 255)
    private String accesos;
    
    @Column(length = 255)
    private String descripcion;

    @Column(name = "localidad_id")
    private Long localidadId;
    
    @Column(name = "barrio_id")
    private Long barrioId;

    @Column(name = "ciudad_id")
    private Long ciudadId; 

    @Column(name = "provincia_id")
    private Integer provinciaId;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean destacado;

    public UgDTO toDTO() {

        return new UgDTO(
            this.getId(),
            this.getNombreCorto(),
            this.getNombreLargo(),
            this.getAccesos(),
            this.getDescripcion(),
            this.getLocalidadId(),
            this.getBarrioId(),
            this.getCiudadId(),
            this.getProvinciaId(),
            this.getFechaCreacion(),
            this.getFechaActualizacion(),
            this.getDestacado()
        );
    }
}
