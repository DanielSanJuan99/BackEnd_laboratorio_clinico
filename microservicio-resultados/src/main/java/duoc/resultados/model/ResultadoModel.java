package duoc.resultados.model;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ResultadoModel {
    @Id
    @Positive(message = "El id debe ser un número positivo")
    private Long id;
    
    @NotNull
    private Float valorResultado;
    private Float valorRefMin;
    private Float valorRefMax;
    private String observacion;
    private LocalDate fechaExamen;

    // IDs para las relaciones
    @NotNull
    private Long laboratorioId;
    @NotNull
    private Long tipoExamenId;
    @NotNull
    private Long tipoParametroId;
    @NotNull
    private Long unidadMedidaId;
    @NotNull
    private Long usuarioId;
}