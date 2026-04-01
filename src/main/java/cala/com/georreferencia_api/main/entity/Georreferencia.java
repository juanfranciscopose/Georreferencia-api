package cala.com.georreferencia_api.main.entity;

import java.time.LocalDateTime;

import cala.com.georreferencia_api.main.dto.GeorreferenciaDTO;
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
@Table(name = "georreferencias", schema = "data")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Georreferencia {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "georreferencia_gen")
    @SequenceGenerator(
        name = "georreferencia_gen", 
        sequenceName = "data.georreferencias_seq", 
        allocationSize = 0)
    @Column
    private Long id;

    @Column(length = 50)
    private String estadoEdilicio;

    @Column(name = "edificio_electoral", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean edificioElectoral;

    @Column(length = 100)
    private String calle;

    @Column(length = 100)
    private String entre1;

    @Column(length = 100)
    private String entre2;

    @Column(length = 20)
    private String numero;

    @Column(length = 20)
    private String depto;

    @Column(length = 255)
    private String observaciones;

    @Column(name = "ug_id")
    private Long ugId;

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

    public GeorreferenciaDTO toDTO() {

        return new GeorreferenciaDTO(
            this.getId(),
            this.getEstadoEdilicio(),
            this.getEdificioElectoral(),
            this.getCalle(),
            this.getEntre1(),
            this.getEntre2(),
            this.getNumero(),
            this.getDepto(),
            this.getObservaciones(),
            this.getUgId(),
            this.getLocalidadId(),
            this.getBarrioId(),
            this.getCiudadId(),
            this.getProvinciaId(),
            this.getFechaCreacion(),
            this.getFechaActualizacion()
        );
    }
}
