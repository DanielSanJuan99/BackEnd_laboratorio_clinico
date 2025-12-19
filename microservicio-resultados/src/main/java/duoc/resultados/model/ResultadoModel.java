package duoc.resultados.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ResultadoModel {
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
    
    private Long usuarioId;
}