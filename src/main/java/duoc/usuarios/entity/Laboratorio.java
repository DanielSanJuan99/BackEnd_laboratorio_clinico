package duoc.usuarios.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;

@Data
@Entity
@Table(name = "laboratorios")
public class Laboratorio {
	@Id
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "nombre", nullable = false, length = 100)
	private String nombre;

	@Column(name = "telefono", length = 20)
	private String telefono;

	@Column(name = "web_url", length = 100)
	private String webUrl;

	@Column(name = "email", nullable = false, length = 100)
	private String email;

	@Column(name = "convenios_id", nullable = false)
	private Integer conveniosId;
}
