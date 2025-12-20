package duoc.resultados.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tipo_parametro")
public class TipoParametro {
    @Id
    private Long id;
    private String nombre;
}