package duoc.usuarios.service;

import duoc.usuarios.entity.Usuario;
import duoc.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 1. Buscamos el usuario en la BD por email
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        // 2. Convertimos el Rol de tu BD a "GrantedAuthority" de Spring Security
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (usuario.getRol() != null) {
            // Spring Security suele esperar roles con el prefijo "ROLE_" (opcional pero recomendado)
            // Asumimos que tu entidad Rol tiene un método getNombre()
            String nombreRol = usuario.getRol().getNombre();
            authorities.add(new SimpleGrantedAuthority(nombreRol));
        }

        // 3. Retornamos el objeto User oficial de Spring Security
        return new User(
                usuario.getEmail(),
                usuario.getPassword(), // Spring verificará esto contra la password encriptada
                true, // Enabled
                true, // Account Non Expired
                true, // Credentials Non Expired
                true, // Account Non Locked
                authorities
        );
    }
}
