package duoc.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import duoc.usuarios.entity.Rol;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    Rol findByNombre(String nombre);
}