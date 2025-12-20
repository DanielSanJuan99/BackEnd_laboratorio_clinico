package duoc.resultados.service;

import duoc.resultados.entity.*;
import duoc.resultados.model.ResultadoModel;
import duoc.resultados.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ResultadoService {

    @Autowired private ResultadoRepository resultadoRepository;
    @Autowired private TipoExamenRepository tipoExamenRepository;
    @Autowired private TipoParametroRepository tipoParametroRepository;
    @Autowired private UnidadMedidaRepository unidadMedidaRepository;

    public List<Resultado> listarTodos() {
        return resultadoRepository.findAll();
    }
    
    public Resultado obtenerPorId(Long id) {
        return resultadoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Resultado no encontrado con ID: " + id));
    }

    public List<Resultado> listarPorPaciente(Long usuarioId) {
        return resultadoRepository.findByUsuarioId(usuarioId);
    }

    public Resultado guardarResultado(ResultadoModel model) {
        Resultado resultado = new Resultado();
        
        // Si viene ID, es una edición
        if (model.getId() != null) {
            resultado = obtenerPorId(model.getId());
        }

        // Mapeo de campos simples
        resultado.setValorResultado(model.getValorResultado());
        resultado.setValorRefMin(model.getValorRefMin());
        resultado.setValorRefMax(model.getValorRefMax());
        resultado.setObservacion(model.getObservacion());
        resultado.setFechaExamen(model.getFechaExamen());
        resultado.setUsuarioId(model.getUsuarioId());
        resultado.setLaboratorioId(model.getLaboratorioId());

        TipoExamen te = tipoExamenRepository.findById(model.getTipoExamenId())
            .orElseThrow(() -> new EntityNotFoundException("Tipo Examen inválido"));
        resultado.setTipoExamen(te);

        TipoParametro tp = tipoParametroRepository.findById(model.getTipoParametroId())
            .orElseThrow(() -> new EntityNotFoundException("Tipo Parámetro inválido"));
        resultado.setTipoParametro(tp);

        UnidadMedida um = unidadMedidaRepository.findById(model.getUnidadMedidaId())
            .orElseThrow(() -> new EntityNotFoundException("Unidad Medida inválida"));
        resultado.setUnidadMedida(um);

        return resultadoRepository.save(resultado);
    }

    public void eliminarResultado(Long id) {
        if (!resultadoRepository.existsById(id)) {
            throw new EntityNotFoundException("No se puede eliminar, ID no existe");
        }
        resultadoRepository.deleteById(id);
    }
}