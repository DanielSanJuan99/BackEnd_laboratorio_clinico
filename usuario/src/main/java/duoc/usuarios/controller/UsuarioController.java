package duoc.usuarios.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import duoc.usuarios.service.UsuarioService;
import jakarta.validation.Valid;
import duoc.usuarios.entity.Usuario;
import duoc.usuarios.model.UsuarioModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;

@Slf4j
@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> obtenerUsuarios() {
        log.info("Recibiendo solicitud para obtener todos los usuarios");
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/{id}")
    public Optional<Usuario> obtenerUsuarioPorId(@PathVariable Integer id) {
        log.info("Recibiendo solicitud para obtener usuario con ID: {}", id);
        return usuarioService.obtenerUsuarioPorId(id);
    }

    @PostMapping
    public ResponseEntity<Usuario> crearUsusario(@Valid @RequestBody UsuarioModel usuarioModel) {
        Usuario usuarioGuardado = usuarioService.guardarUsuario(null, usuarioModel);
        log.info("Usuario creado con ID: {}", usuarioGuardado.getId());
        return ResponseEntity.ok(usuarioGuardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Integer id, @Valid @RequestBody UsuarioModel usuarioModel) {
            Usuario usuarioActualizado = usuarioService.guardarUsuario(id, usuarioModel);
            log.info("Usuario actualizado con ID: {}", usuarioActualizado.getId());
            return ResponseEntity.ok(usuarioActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        Optional<Usuario> usuarioExistente = usuarioService.obtenerUsuarioPorId(id);
        if (usuarioExistente.isPresent()) {
            usuarioService.eliminarUsuario(id);
            log.info("Usuario eliminado con ID: {}", id);
            return ResponseEntity.noContent().build();
        } else {
            log.warn("Usuario con ID: {} no encontrado para eliminación", id);
            return ResponseEntity.notFound().build();
        }
    }
}
