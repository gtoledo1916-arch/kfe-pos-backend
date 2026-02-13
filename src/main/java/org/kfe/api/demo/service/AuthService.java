package org.kfe.api.demo.service;

import org.kfe.api.demo.dto.LoginRequestDTO;
import org.kfe.api.demo.dto.RegisterRequestDTO;

public interface AuthService {

    String login(LoginRequestDTO request);

    String register(RegisterRequestDTO request);
}
