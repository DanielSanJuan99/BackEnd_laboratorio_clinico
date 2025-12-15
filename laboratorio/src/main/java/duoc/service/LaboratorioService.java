package duoc.service;

import duoc.entity.Convenio;
import duoc.entity.Laboratorio;
import duoc.model.LaboratorioModel;
import duoc.repository.LaboratorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LaboratorioService {

    @Autowired
    private LaboratorioRepository laboratorioRepository;

    public List<Laboratorio> listarLaboratorios() {
        return laboratorioRepository.findAll();
    }

    public Optional<Laboratorio> obtenerLaboratorioPorId(Long id) {
        return laboratorioRepository.findById(id);
    }

    public Laboratorio guardarLaboratorio(LaboratorioModel modelo) {
        Laboratorio laboratorio = new Laboratorio();
        
        // Mapeo de datos básicos
        laboratorio.setNombre(modelo.getNombre());
        laboratorio.setTelefono(modelo.getTelefono());
        laboratorio.setWebUrl(modelo.getWebUrl());
        laboratorio.setEmail(modelo.getEmail());

        // Mapeo de la Relación (Asignación)
        if (modelo.getConvenioId() != null) {
            // Usamos el truco de crear la instancia temporal con el ID
            Convenio convenio = new Convenio();
            convenio.setId(modelo.getConvenioId());
            laboratorio.setConvenio(convenio);
        }

        return laboratorioRepository.save(laboratorio);
    }
    
    // Método para actualizar (opcional pero útil)
    public Laboratorio actualizarLaboratorio(Long id, LaboratorioModel modelo) {
        Optional<Laboratorio> labExistente = laboratorioRepository.findById(id);
        
        if (labExistente.isPresent()) {
            Laboratorio laboratorio = labExistente.get();
            laboratorio.setNombre(modelo.getNombre());
            laboratorio.setTelefono(modelo.getTelefono());
            laboratorio.setWebUrl(modelo.getWebUrl());
            laboratorio.setEmail(modelo.getEmail());

            if (modelo.getConvenioId() != null) {
                Convenio convenio = new Convenio();
                convenio.setId(modelo.getConvenioId());
                laboratorio.setConvenio(convenio);
            }
            return laboratorioRepository.save(laboratorio);
        }
        return null;
    }
}