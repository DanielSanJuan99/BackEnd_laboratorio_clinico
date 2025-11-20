package duoc.usuarios.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String apellido;
    private String email;
    private String password;

    @ManyToOne(targetEntity = Rol.class)
    @JoinColumn(name = "roles_id")
    private Rol rol;

    @ManyToOne(targetEntity = Laboratorio.class)
    @JoinColumn(name = "laboratorios_id")
    private Laboratorio laboratorio;
}
