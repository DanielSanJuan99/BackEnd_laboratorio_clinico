package duoc.usuarios.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private Integer roles_id;
    private Integer laboratorios_id;

    public Usuario(){}

    public Usuario(Integer id, String nombre, String apellido, String email, String password, Integer roles_id, Integer laboratorios_id) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.roles_id = roles_id;
        this.laboratorios_id = laboratorios_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
     
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRol() {
        return roles_id;
    }

    public void setRol(Integer roles_id) {
        this.roles_id = roles_id;
    }

    public Integer getLaboratorio() {
        return laboratorios_id;
    }

    public void setLaboratorio(Integer laboratorios_id) {
        this.laboratorios_id = laboratorios_id;
    }
}
