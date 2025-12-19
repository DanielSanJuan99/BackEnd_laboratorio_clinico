package duoc.resultados.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "resultados")
public class Resultado {
    // TODO: REVISAR SEQUENCE GENERATOR
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resultados_seq") 
    @SequenceGenerator(name = "resultados_seq", sequenceName = "SEQ_RESULTADOS", allocationSize = 1)
    private Long id;

    @Column(name = "valor_resultado")
    private Float valorResultado;

    @Column(name = "valor_ref_min")
    private Float valorRefMin;

    @Column(name = "valor_ref_max")
    private Float valorRefMax;

    private String observacion;

    @Column(name = "fecha_examen")
    private LocalDate fechaExamen;

    // --- RELACIONES ---

    // Relación Laboratorio
    @Column(name = "laboratorios_id")
    private Long laboratorioId; 

    // Relación TipoExamen
    @ManyToOne
    @JoinColumn(name = "tipo_examen_id")
    private TipoExamen tipoExamen;

    // Relación TipoParametro
    @ManyToOne
    @JoinColumn(name = "tipo_parametro_id")
    private TipoParametro tipoParametro;

    // Relación UnidadMedida
    @ManyToOne
    @JoinColumn(name = "unidad_medida_id")
    private UnidadMedida unidadMedida;
    
    // TODO: GENERAR FK EN BD ENTRE USUARIO Y RESULTADO
    @Column(name = "usuarios_id") 
    private Long usuarioId; 
}