package org.kfe.api.demo.service;

import org.kfe.api.demo.entity.User;

import java.util.List;

public interface UserService {

    List<User> searchByName(String username);
}
