package duoc.usuarios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import duoc.usuarios.repository.UsuarioRepository;
import duoc.usuarios.entity.Usuario;

@Service
public class UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    //Obtener todos los usuarios
    public List<Usuario> listarUsuarios() {
        logger.info("Listando todos los usuarios");
        logger.debug("Query ejecutada: SELECT * FROM usuarios");
        List<Usuario> usuarios = usuarioRepository.findAll();
        if (usuarios.isEmpty()) {
            logger.warn("No se encontraron usuarios en la base de datos");
        } else {
            logger.info("Cantidad de usuarios encontrados: {}", usuarios.size());
        }
        return usuarioRepository.findAll();
    }

    //Obtener usuario por ID
    public Optional<Usuario> obtenerUsuarioPorId(Integer id) {
        logger.info("Obteniendo usuario con ID: {}", id);
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isEmpty()) {
            logger.warn("No se encontró usuario con ID: {}", id);
        } else {
            logger.info("Usuario encontrado: {}", usuario.get().getEmail());
        }
        return usuario;
    }

    //Guardar nuevo usuario
    public Usuario guardarUsuario(Usuario usuario) {
        logger.info("Guardando nuevo usuario: {}", usuario.getNombre(), " ", usuario.getApellido(), " - ", usuario.getEmail());
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        logger.info("Usuario guardado con ID: {}", usuarioGuardado.getId());
        return usuarioGuardado;
    }

    //Eliminar usuario
    public void eliminarUsuario(Integer id) {
        logger.info("Eliminando usuario con ID: {}", id);
        if (!usuarioRepository.existsById(id)) {
            logger.warn("No se encontró usuario con ID: {}", id);
            return;
        } else {
            logger.info("Usuario con ID: {} eliminado", id);
        }
        usuarioRepository.deleteById(id);
    }
}
