package cala.com.georreferencia_api.main.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cala.com.georreferencia_api.main.dto.GeorreferenciaDTO;
import cala.com.georreferencia_api.nota.dto.NotaDTO;
import cala.com.georreferencia_api.nota.entity.Nota;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
        name = "georreferencias_notas",
        schema = "data",
        joinColumns = @JoinColumn(name = "georreferencia_id"),
        inverseJoinColumns = @JoinColumn(name = "nota_id")
    )
    private Set<Nota> notas = new HashSet<>();

    public void addNota(Nota nota) {
        this.notas.add(nota);
        nota.getGeorreferencias().add(this);
    }

    public void removeNota(Nota nota) {
        this.notas.remove(nota);
        nota.getGeorreferencias().remove(this);
    }

    public GeorreferenciaDTO toDTO() {
        List<NotaDTO> notasDto = (this.notas != null) 
            ? this.notas.stream().map(Nota::toDto).toList() 
            : new ArrayList<>();
        
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
            this.getFechaActualizacion(),
            notasDto
        );
    }
}
