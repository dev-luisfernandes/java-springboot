package br.com.fiap.entities;

public class Administrador {

    private String email;
    private String senha;
    private String perfil;

    public Administrador() {
        this.email = "admin@gmail.com";
        this.senha = "adm123";
        this.perfil = "ADMIN";
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getPerfil() {
        return perfil;
    }

    public boolean validarCredenciais(String emailInput, String senhaInput) {
        return this.email.equalsIgnoreCase(emailInput) && this.senha.equals(senhaInput);
    }

    @Override
    public String toString() {
        return "Administrador{" +
                "email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", perfil='" + perfil + '\'' +
                '}';
    }
}