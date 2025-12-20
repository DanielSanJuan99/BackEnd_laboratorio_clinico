package duoc.resultados.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "unidad_medida")
public class UnidadMedida {
    @Id
    private Long id;
    private String nombre;
}