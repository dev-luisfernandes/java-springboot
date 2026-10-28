package br.com.fiap.bo;

import br.com.fiap.entities.Administrador;

public class AdministradorBo {

    public Administrador autenticarBo(String email, String senha) {
        if (email == null || email.trim().isEmpty() || senha == null || senha.trim().isEmpty()) {
            return null;
        }

        Administrador adminPadrao = new Administrador();

        // Utiliza o próprio método da sua entidade
        if (adminPadrao.validarCredenciais(email, senha)) {
            return adminPadrao;
        }

        return null;
    }
}