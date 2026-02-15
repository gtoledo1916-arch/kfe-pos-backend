package org.kfe.api.demo.security;

import org.kfe.api.demo.exception.UsuarioNoEncontradoException;
import org.kfe.api.demo.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // 1. Busca el usuario, si no existe lanza excepción
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsuarioNoEncontradoException(email));

        // 2. Verifica que el usuario tenga rol asignado
        if (user.getRole() == null) {
            throw new UsernameNotFoundException("El usuario no tiene un rol asignado: " + email);
        }

        // 3. Construye el UserDetails con el rol actual de la BD
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }
}