package org.kfe.api.demo.service;

import org.kfe.api.demo.dto.UpdateUserRequestDTO;
import org.kfe.api.demo.entity.User;

import java.util.List;

public interface UserService {

    List<User> searchByName(String username);


    // obtener todos los usuarios
    List<User> getAll();

    // actualizar usuario por ID
    User update(Long id, UpdateUserRequestDTO request);

    //  eliminar usuario por ID
    void delete(Long id);
}
