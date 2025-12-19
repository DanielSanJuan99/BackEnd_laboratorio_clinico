package duoc.resultados.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "unidad_medida")
public class UnidadMedida {
    @Id
    private Long id;
    private String nombre;
}