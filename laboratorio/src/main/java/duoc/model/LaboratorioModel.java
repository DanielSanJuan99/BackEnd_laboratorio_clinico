package duoc.model;

import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class LaboratorioModel {
    @Id
    @Positive(message = "El id debe ser un número positivo")
    private Long id;

    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @Size(min = 8, max = 9, message = "El teléfono debe tener entre 8 y 9 caracteres")
    private String telefono;

    private String webUrl;

    @Email(message = "El email debe tener un formato válido")
    private String email;

    @NotNull(message = "El id del convenio no puede ser nulo")
    @Positive(message = "El id del convenio debe ser un número positivo")
    private Long convenioId;
}
