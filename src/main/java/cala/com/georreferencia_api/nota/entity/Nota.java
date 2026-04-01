package cala.com.georreferencia_api.nota.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import cala.com.georreferencia_api.main.entity.Georreferencia;
import cala.com.georreferencia_api.nota.dto.NotaDTO;
import cala.com.georreferencia_api.ug.entity.Ug;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "notas", schema = "data")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Nota {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "georreferencia_gen")
    @SequenceGenerator(
        name = "georreferencia_gen", 
        sequenceName = "data.georreferencias_seq", 
        allocationSize = 1)
        // TODO: falta autor de la nota
    @Column
    private Long id;

    @Column
    private String texto;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean delete;

    @ManyToMany(mappedBy = "notas")
    private Set<Georreferencia> georreferencias = new HashSet<>();

    @ManyToMany(mappedBy = "notas")
    private Set<Ug> ugs = new HashSet<>();

    public NotaDTO toDto () {
        return new NotaDTO(
            this.getId(),
            this.getTexto(),
            this.getFechaCreacion(),
            this.getDelete()
        );
    }
}
