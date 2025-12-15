package duoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import duoc.entity.Laboratorio;
import duoc.model.LaboratorioModel;
import duoc.service.LaboratorioService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/laboratorios")
@CrossOrigin(origins = "http://localhost:4200")
public class LaboratorioController {
    @Autowired
    private LaboratorioService laboratorioService;

    @GetMapping
    public ResponseEntity<List<Laboratorio>> listarLaboratorios() {
        List<Laboratorio> laboratorios = laboratorioService.listarLaboratorios();
        return ResponseEntity.ok(laboratorios);
    }

    @GetMapping("/{id}")
    public Optional<Laboratorio> obtenerLaboratorioPorId(@PathVariable Long id) {
        return laboratorioService.obtenerLaboratorioPorId(id);
    }
    

    @PostMapping
    public ResponseEntity<Laboratorio> crearLaboratorio(@Valid @RequestBody LaboratorioModel laboratorioModel) {
        Laboratorio laboratorioGuardado = laboratorioService.guardarLaboratorio(laboratorioModel);
        return ResponseEntity.ok(laboratorioGuardado);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Laboratorio> actualizarLaboratorio(@PathVariable Long id, @RequestBody LaboratorioModel laboratorioModel) {
        Laboratorio laboratorioActualizado = laboratorioService.actualizarLaboratorio(id, laboratorioModel);
        if (laboratorioActualizado != null) {
            return ResponseEntity.ok(laboratorioActualizado);
        }
        return ResponseEntity.notFound().build();
    }
}
