package duoc.resultados.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tipo_parametro")
public class TipoParametro {
    @Id
    private Long id;
    private String nombre;
}