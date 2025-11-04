package duoc.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import duoc.usuarios.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
}
