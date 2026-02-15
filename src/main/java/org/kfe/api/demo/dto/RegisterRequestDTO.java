package org.kfe.api.demo.dto;

public class RegisterRequestDTO {

    private String username;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String email;
    private String password;
    private String role;

    //GETTERS
    public String getUsername() {
        return username;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    //SETTER
     public void setUsername(String username) {
            this.username = username;
        }

        public void setApellidoPaterno(String apellidoPaterno) {
            this.apellidoPaterno = apellidoPaterno;
        }

        public void setApellidoMaterno(String apellidoMaterno) {
            this.apellidoMaterno = apellidoMaterno;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setRole(String role) {
            this.role = role;
        }
}
