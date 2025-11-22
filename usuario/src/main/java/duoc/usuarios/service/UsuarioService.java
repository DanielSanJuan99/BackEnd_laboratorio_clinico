package duoc.usuarios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import duoc.usuarios.repository.RolRepository;
import duoc.usuarios.repository.LaboratorioRepository;
import duoc.usuarios.repository.UsuarioRepository;
import duoc.usuarios.entity.Laboratorio;
import duoc.usuarios.entity.Rol;
import duoc.usuarios.entity.Usuario;
import duoc.usuarios.model.UsuarioModel;

@Service
public class UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private LaboratorioRepository laboratorioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    public Usuario guardarUsuario(Integer id, UsuarioModel usuarioModel) {
        Usuario usuario = Optional.ofNullable(id)
            .flatMap(usuarioRepository::findById)
            .orElse(new Usuario());

        usuario.setNombre(usuarioModel.getNombre());
        usuario.setApellido(usuarioModel.getApellido());
        usuario.setEmail(usuarioModel.getEmail());

        String passNueva = usuarioModel.getPassword();

        if (passNueva != null && !passNueva.isBlank()) {
            String passwordEncriptada = passwordEncoder.encode(passNueva);
            usuario.setPassword(passwordEncriptada);

        } else {
            if (usuario.getId() != null) {
                // Mantener la contraseña existente si no se proporciona una nueva
                Usuario usuarioExistente = usuarioRepository.findById(usuario.getId()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                usuario.setPassword(usuarioExistente.getPassword());
            }
        }

        if (usuarioModel.getPassword() != null && !usuarioModel.getPassword().isEmpty()) {
            String passwordEncriptada = passwordEncoder.encode(usuarioModel.getPassword());
            usuario.setPassword(passwordEncriptada);
        }

        if (usuarioModel.getRolId() != null) {
            Rol rol = rolRepository.findById(usuarioModel.getRolId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
            usuario.setRol(rol);
        }

        if (usuarioModel.getLaboratorioId() != null) {
            Laboratorio lab = laboratorioRepository.findById(usuarioModel.getLaboratorioId())
                .orElseThrow(() -> new RuntimeException("Laboratorio no encontrado"));
            usuario.setLaboratorio(lab);
        }

        if (usuario.getId() == null) {
            logger.info("Creando nuevo usuario: {}", usuario.getNombre(), " ", usuario.getApellido(), " - ", usuario.getEmail());
        } else {
            logger.info("Actualizando usuario con ID: {}", usuario.getId());
        }
        
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
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
