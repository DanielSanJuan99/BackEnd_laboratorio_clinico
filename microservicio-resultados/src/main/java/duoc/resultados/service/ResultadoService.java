package duoc.resultados.service;

import duoc.resultados.entity.*;
import duoc.resultados.model.ResultadoModel;
import duoc.resultados.repository.ResultadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ResultadoService {

    @Autowired 
    private ResultadoRepository resultadoRepository;

    public List<Resultado> listarResultados() {
        return resultadoRepository.findAll();
    }
    
    public Optional<Resultado> obtenerPorId(Long id) {
        return resultadoRepository.findById(id);
    }

    public List<Resultado> obtenerPorUsuario(Long usuarioId) {
        return resultadoRepository.findByUsuarioId(usuarioId);
    }

    public Resultado guardarResultado(ResultadoModel modelo) {
        Resultado resultado = new Resultado();

        // Mapeo de campos simples
        resultado.setValorResultado(modelo.getValorResultado());
        resultado.setValorRefMin(modelo.getValorRefMin());
        resultado.setValorRefMax(modelo.getValorRefMax());
        resultado.setObservacion(modelo.getObservacion());
        resultado.setFechaExamen(modelo.getFechaExamen());
        resultado.setUsuarioId(modelo.getUsuarioId());
        resultado.setLaboratorioId(modelo.getLaboratorioId());

        if (modelo.getTipoExamenId() != null) {
            TipoExamen te = new TipoExamen();
            te.setId(modelo.getTipoExamenId());
            resultado.setTipoExamen(te);
        }

        if (modelo.getTipoParametroId() != null) {
            TipoParametro tp = new TipoParametro();
            tp.setId(modelo.getTipoParametroId());
            resultado.setTipoParametro(tp);
        }

        if (modelo.getUnidadMedidaId() != null) {
            UnidadMedida um = new UnidadMedida();
            um.setId(modelo.getUnidadMedidaId());
            resultado.setUnidadMedida(um);
        }

        return resultadoRepository.save(resultado);
    }

    public Resultado actualizarResultado(Long id, ResultadoModel modelo) {
        Optional<Resultado> resultadoExistente = resultadoRepository.findById(id);

        if (resultadoExistente.isPresent()) {
            Resultado resultado = resultadoExistente.get();

            resultado.setValorResultado(modelo.getValorResultado());
            resultado.setValorRefMin(modelo.getValorRefMin());
            resultado.setValorRefMax(modelo.getValorRefMax());
            resultado.setObservacion(modelo.getObservacion());
            resultado.setFechaExamen(modelo.getFechaExamen());
            resultado.setLaboratorioId(modelo.getLaboratorioId());
            resultado.setUsuarioId(modelo.getUsuarioId());

            if (modelo.getTipoExamenId() != null) {
                TipoExamen te = new TipoExamen();
                te.setId(modelo.getTipoExamenId());
                resultado.setTipoExamen(te);
            }

            if (modelo.getTipoParametroId() != null) {
                TipoParametro tp = new TipoParametro();
                tp.setId(modelo.getTipoParametroId());
                resultado.setTipoParametro(tp);
            }

            if (modelo.getUnidadMedidaId() != null) {
                UnidadMedida um = new UnidadMedida();
                um.setId(modelo.getUnidadMedidaId());
                resultado.setUnidadMedida(um);
            }

            return resultadoRepository.save(resultado);
        }
        return null;
    }

    public void eliminarResultado(Long id) {
        if (!resultadoRepository.existsById(id)) {
            throw new EntityNotFoundException("No se puede eliminar, ID no existe");
        }
        resultadoRepository.deleteById(id);
    }
}