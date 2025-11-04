package duoc.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import duoc.usuarios.entity.Laboratorio;

public interface LaboratorioRepository extends JpaRepository<Laboratorio, Integer> {
}