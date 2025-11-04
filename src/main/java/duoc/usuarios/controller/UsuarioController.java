package duoc.usuarios.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import duoc.usuarios.service.UsuarioService;
import jakarta.validation.Valid;
import duoc.usuarios.entity.Usuario;
import duoc.usuarios.model.UsuarioModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;


@Slf4j
@RestController
@RequestMapping("/api/usuarios")
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
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioModel.getNombre());
        usuario.setApellido(usuarioModel.getApellido());
        usuario.setEmail(usuarioModel.getEmail());
        usuario.setPassword(usuarioModel.getPassword());
        usuario.setRol(usuarioModel.getRol().getId());
        usuario.setLaboratorio(usuarioModel.getLaboratorio().getId());
        Usuario usuarioGuardado = usuarioService.guardarUsuario(usuario);
        log.info("Usuario creado con ID: {}", usuarioGuardado.getId());
        return ResponseEntity.ok(usuarioGuardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Integer id, @Valid @RequestBody UsuarioModel usuarioModel) {
        Optional<Usuario> usuarioExistente = usuarioService.obtenerUsuarioPorId(id);
        if (usuarioExistente.isPresent()) {
            Usuario usuario = usuarioExistente.get();
            usuario.setNombre(usuarioModel.getNombre());
            usuario.setApellido(usuarioModel.getApellido());
            usuario.setEmail(usuarioModel.getEmail());
            usuario.setPassword(usuarioModel.getPassword());
            usuario.setRol(usuarioModel.getRol().getId());
            usuario.setLaboratorio(usuarioModel.getLaboratorio().getId());
            Usuario usuarioActualizado = usuarioService.guardarUsuario(usuario);
            log.info("Usuario actualizado con ID: {}", usuarioActualizado.getId());
            return ResponseEntity.ok(usuarioActualizado);
        } else {
            log.warn("Usuario con ID: {} no encontrado para actualización", id);
            return ResponseEntity.notFound().build();
        }
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
