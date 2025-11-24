package duoc.repository;

import org.springframework.stereotype.Repository;
import duoc.entity.Laboratorio;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface LaboratorioRepository extends JpaRepository<Laboratorio, Long> {}