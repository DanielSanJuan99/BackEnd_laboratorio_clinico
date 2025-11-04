package duoc.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import duoc.usuarios.entity.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer> {
}