package duoc.usuarios.model;

// import duoc.usuarios.entity.Rol;
// import duoc.usuarios.entity.Laboratorio;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import lombok.Data;

// Modificar para construir el modelo de datos con validaciones
@Data
public class UsuarioModel {
    @Id
    @NotBlank(message = "El id no puede estar vacío")
    @Positive(message = "El id debe ser un número positivo")
    private Integer id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    private String apellido;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email debe tener un formato válido")
    @Size(min = 5, max = 100, message = "El email debe tener entre 5 y 100 caracteres")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 60, max = 60, message = "La contraseña debe tener 60 caracteres (hash BCrypt)")
    private String password;

    // Relación con Rol
    @NotNull(message = "El rol no puede ser nulo")
    private Integer rolId;

    // Relación con Laboratorio
    @NotNull(message = "El laboratorio no puede ser nulo")
    private Integer laboratorioId;
}