package org.kfe.api.demo.service.impl;

import org.kfe.api.demo.dto.UpdateUserRequestDTO;
import org.kfe.api.demo.entity.User;
import org.kfe.api.demo.exception.UsuarioNoEncontradoException;
import org.kfe.api.demo.repository.UserRepository;
import org.kfe.api.demo.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public List<User> searchByName(String name) {
        return userRepository.findByUsernameContainingIgnoreCase(name);
    }

    //  Retorna todos los usuarios
    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    // Actualiza solo los campos enviados (si vienen nulos, no se pisan)
    @Override
    public User update(Long id, UpdateUserRequestDTO request) {

        // Si el ID no existe → 404
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException("ID: " + id));

        if (request.getUsername() != null && !request.getUsername().isBlank()) {
            user.setUsername(request.getUsername());
        }

        if (request.getApellidoPaterno() != null && !request.getApellidoPaterno().isBlank()) {
            user.setApellidoPaterno(request.getApellidoPaterno());
        }

        if (request.getApellidoMaterno() != null && !request.getApellidoMaterno().isBlank()) {
            user.setApellidoMaterno(request.getApellidoMaterno());
        }

        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            user.setEmail(request.getEmail());
        }

        // Solo actualiza la contraseña si se envía una nueva
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(encoder.encode(request.getPassword()));
        }

        return userRepository.save(user);
    }

    //  Elimina el usuario, si no existe → 404
    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UsuarioNoEncontradoException("ID: " + id);
        }
        userRepository.deleteById(id);
    }
}