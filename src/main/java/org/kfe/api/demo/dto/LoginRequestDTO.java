package org.kfe.api.demo.dto;

public class LoginRequestDTO {

    private String email;
    private String password;

    //GETTERS
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }

    //SETTERS
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
