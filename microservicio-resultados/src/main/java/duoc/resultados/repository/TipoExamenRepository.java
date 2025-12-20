package duoc.resultados.repository;

import duoc.resultados.entity.TipoExamen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoExamenRepository extends JpaRepository<TipoExamen, Long> {}
