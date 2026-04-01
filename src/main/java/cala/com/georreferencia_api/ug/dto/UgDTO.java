package cala.com.georreferencia_api.ug.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class UgDTO {

    private Long id;

    @NotBlank(message = "La descripción corta es obligatoria")
    @Size(max = 20, message = "El nombre corto del barrio no puede superar 20 caracteres")
    private String nombreCorto;

    @NotBlank(message = "La descripción larga es obligatoria")
    @Size(max = 150, message = "El nombre largo del barrio no puede superar 150 caracteres")
    private String nombreLargo;

    private String accesos;

    private String descripcion;

    private Long localidadId;

    private Long barrioId;

    private Long ciudadId;

    private Integer provinciaId;

    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaActualizacion;

    private Boolean destacado;

}
