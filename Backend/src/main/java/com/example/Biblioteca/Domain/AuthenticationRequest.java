package com.example.Biblioteca.Domain;

public class AuthenticationRequest {
        private String cnp;
        private String password;

    public String getCnp() {
        return cnp;
    }

    public String getPassword() {
        return password;
    }

    public void setCnp(String cod) {
        this.cnp = cod;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
