package org.kfe.api.demo.service.impl;

import org.kfe.api.demo.dto.LoginRequestDTO;
import org.kfe.api.demo.dto.RegisterRequestDTO;
import org.kfe.api.demo.entity.Role;
import org.kfe.api.demo.entity.User;
import org.kfe.api.demo.exception.CredencialesInvalidasException;
import org.kfe.api.demo.exception.UsuarioNoEncontradoException;
import org.kfe.api.demo.repository.UserRepository;
import org.kfe.api.demo.security.JwtService;
import org.kfe.api.demo.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;

    public AuthServiceImpl(UserRepository userRepository, JwtService jwtService, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.encoder = encoder;
    }

    @Override
    public String login(LoginRequestDTO request) {

        // Si el email no existe → 404
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsuarioNoEncontradoException(request.getEmail()));

        // Si el password es incorrecto → 401
        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new CredencialesInvalidasException();
        }

        return jwtService.generateToken(user);
    }

    @Override
    public String register(RegisterRequestDTO request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setApellidoPaterno(request.getApellidoPaterno());
        user.setApellidoMaterno(request.getApellidoMaterno());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole(Role.valueOf(request.getRole().toUpperCase()));

        userRepository.save(user);

        return "Usuario creado correctamente";
    }
}