package duoc.resultados.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tipo_examen")
public class TipoExamen {
    @Id
    private Long id;
    private String nombre;
}