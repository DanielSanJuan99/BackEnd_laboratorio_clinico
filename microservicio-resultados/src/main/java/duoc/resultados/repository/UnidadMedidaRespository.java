package duoc.resultados.repository;

import duoc.resultados.entity.UnidadMedida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadMedidaRespository extends JpaRepository<UnidadMedida, Long> {}
