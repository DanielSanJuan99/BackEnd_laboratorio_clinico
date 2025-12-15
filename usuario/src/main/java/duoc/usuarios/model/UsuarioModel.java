package duoc.usuarios.model;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import lombok.Data;

// Modificar para construir el modelo de datos con validaciones
@Data
public class UsuarioModel {
    @Id
    @Positive(message = "El id debe ser un número positivo")
    private Integer id;

    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;

    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    private String apellido;

    @Email(message = "El email debe tener un formato válido")
    @Size(min = 5, max = 100, message = "El email debe tener entre 5 y 100 caracteres")
    private String email;

    @Size(min = 6, max = 50, message = "La contraseña debe tener mínimo 6 caracteres y máximo 50 caracteres")
    private String password;

    // Relación con Rol
    private Integer rolId;

    // Relación con Laboratorio
    private Integer laboratorioId;
}