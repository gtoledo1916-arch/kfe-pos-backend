package org.kfe.api.demo.service.impl;

import lombok.RequiredArgsConstructor;
import org.kfe.api.demo.dto.LoginRequestDTO;
import org.kfe.api.demo.entity.User;
import org.kfe.api.demo.repository.UserRepository;
import org.kfe.api.demo.security.JwtService;
import org.kfe.api.demo.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;

    @Override
    public Object login(LoginRequestDTO request) {

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        return jwtService.generateToken(user);
    }
}
