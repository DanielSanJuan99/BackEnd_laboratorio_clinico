package duoc.resultados.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import duoc.resultados.entity.Resultado;
import duoc.resultados.model.ResultadoModel;
import duoc.resultados.service.ResultadoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/resultados")
@CrossOrigin(origins = "http://localhost:4200")
public class ResultadoController {
    
    @Autowired
    private ResultadoService resultadoService;

    @Operation(summary = "Obtener todos los resultados listados")
    @GetMapping
    public ResponseEntity<List<Resultado>> listarResultados() {
        log.info("Recibiendo solicitud para obtener todos los resultados");
        List<Resultado> resultados = resultadoService.listarResultados();
        return ResponseEntity.ok(resultados);
    }

    @Operation(summary = "Obtener resultados por ID")
    @GetMapping("/{id}")
    public Optional<Resultado> obtenerPorId(@PathVariable Long id) {
        log.info("Recibiendo solicitud para obtener resultado con ID: {}", id);
        return resultadoService.obtenerPorId(id);
    }

    @Operation(summary = "Listar resultados por Paciente")
    @GetMapping("/paciente/{usuarioId}")
    public ResponseEntity<List<Resultado>> listarPorPaciente(@PathVariable Long usuarioId) {
        List<Resultado> resultados = resultadoService.obtenerPorUsuario(usuarioId);
        return ResponseEntity.ok(resultados);
    }

    @Operation(summary = "Crear nuevo resultado")
    @PostMapping
    public ResponseEntity<Resultado> crearResultado(@Valid @RequestBody ResultadoModel resultadoModel) {
        Resultado resultadoGuardado = resultadoService.guardarResultado(resultadoModel);
        return ResponseEntity.ok(resultadoGuardado);
    }

    @Operation(summary = "Actualizar resultado existente")
    @PutMapping("/{id}")
    public ResponseEntity<Resultado> actualizarResultado(@PathVariable Long id, @Valid @RequestBody ResultadoModel resultadoModel) {
        Resultado resultadoActualizado = resultadoService.actualizarResultado(id, resultadoModel);
        if (resultadoActualizado != null) {
            return ResponseEntity.ok(resultadoActualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Eliminar resultado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarResultado(@PathVariable Long id) {
        try {
            resultadoService.eliminarResultado(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            log.error("Error al eliminar resultado: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
