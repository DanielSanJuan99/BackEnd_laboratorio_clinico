package duoc.usuarios.config;

import duoc.usuarios.entity.Rol;
import duoc.usuarios.entity.Usuario;
import duoc.usuarios.repository.RolRepository;
import duoc.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("--- INICIANDO PROCESO DE SEEDING (CARGA DE DATOS) ---");

        Rol rolAdmin = rolRepository.findByNombre("ADMIN");
        
        if (rolAdmin == null) {
            rolAdmin = new Rol();
            rolAdmin.setNombre("ADMIN");
            rolAdmin.setDescripcion("Administrador con acceso total");
            rolAdmin = rolRepository.save(rolAdmin);
            System.out.println("Rol ADMIN creado exitosamente.");
        } else {
            System.out.println("El Rol ADMIN ya existía.");
        }

        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail("admin@duoc.cl");

        if (usuarioExistente.isEmpty()) {
            Usuario admin = new Usuario();
            admin.setNombre("Administrador");
            admin.setApellido("Sistema");
            admin.setEmail("admin@duoc.cl");

            admin.setPassword(passwordEncoder.encode("123456"));
            
            admin.setRol(rolAdmin);
            // admin.setLaboratorio(null); // Opcional: El admin global no suele tener laboratorio asignado

            usuarioRepository.save(admin);
            System.out.println("Usuario ADMIN creado: admin@duoc.cl / pass: 123456");
        } else {
            System.out.println("El usuario ADMIN ya existía. No se realizaron cambios.");
        }

        System.out.println("--- PROCESO DE SEEDING FINALIZADO ---");
    }
}