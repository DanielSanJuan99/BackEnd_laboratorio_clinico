package duoc.resultados.repository;

import duoc.resultados.entity.TipoParametro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoParametroRepository extends JpaRepository<TipoParametro, Long> {}
