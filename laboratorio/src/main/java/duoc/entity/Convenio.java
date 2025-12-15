package duoc.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "convenios")
public class Convenio {
    @Id
    private Long id;
    private String nombre;
}

