package org.kfe.api.demo.service;

import org.kfe.api.demo.dto.LoginRequestDTO;

public interface AuthService {

    Object login(LoginRequestDTO request);
}
