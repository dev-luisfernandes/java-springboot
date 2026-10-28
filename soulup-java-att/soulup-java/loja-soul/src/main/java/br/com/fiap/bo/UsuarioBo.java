package br.com.fiap.bo;

import br.com.fiap.dao.UsuarioDao;
import br.com.fiap.entities.Usuario;
import br.com.fiap.excecoes.ExcecoesConexao;
import br.com.fiap.excecoes.ExcecoesRegraNegocio;

import java.util.ArrayList;

public class UsuarioBo {

    private UsuarioDao usuarioDao;

    public ArrayList<Usuario> selecionarBo() throws ExcecoesConexao {
        try {
            usuarioDao = new UsuarioDao();
            return (ArrayList<Usuario>) usuarioDao.selecionar();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExcecoesConexao(e);
        }
    }

    public Usuario buscarPorEmailBo(String email) throws ExcecoesConexao, ExcecoesRegraNegocio {
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            throw new ExcecoesRegraNegocio("O e-mail informado é inválido.");
        }

        try {
            usuarioDao = new UsuarioDao();
            return usuarioDao.buscarPorEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExcecoesConexao(e);
        }
    }

    public void inserirBo(Usuario usuario) throws ExcecoesConexao, ExcecoesRegraNegocio {
        validarUsuario(usuario);

        try {
            usuarioDao = new UsuarioDao();

            if (usuarioDao.buscarPorEmail(usuario.getEmail()) != null) {
                throw new ExcecoesRegraNegocio("Já existe um usuário cadastrado com este e-mail.");
            }

            usuario.setPerfil("USER");
            usuario.setPontos(50.0);
            usuario.atualizarTier();

            usuarioDao.inserir(usuario);
        } catch (ExcecoesRegraNegocio e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExcecoesConexao(e);
        }
    }

    public void deletarPorEmailBo(String email) throws ExcecoesConexao, ExcecoesRegraNegocio {
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            throw new ExcecoesRegraNegocio("O e-mail informado para exclusão é inválido.");
        }

        try {
            usuarioDao = new UsuarioDao();

            if (usuarioDao.buscarPorEmail(email) == null) {
                throw new ExcecoesRegraNegocio("Usuário não encontrado para o e-mail informado.");
            }

            usuarioDao.deletarPorEmail(email);
        } catch (ExcecoesRegraNegocio e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExcecoesConexao(e);
        }
    }

    public void atualizarBo(Usuario usuario) throws ExcecoesConexao, ExcecoesRegraNegocio {
        if (usuario == null || usuario.getEmail() == null) {
            throw new ExcecoesRegraNegocio("O e-mail do usuário é obrigatório para atualização.");
        }

        try {
            usuarioDao = new UsuarioDao();

            if (usuarioDao.buscarPorEmail(usuario.getEmail()) == null) {
                throw new ExcecoesRegraNegocio("Usuário não encontrado para atualização.");
            }

            usuario.atualizarTier();
            usuarioDao.atualizar(usuario);
        } catch (ExcecoesRegraNegocio e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExcecoesConexao(e);
        }
    }


    public String adicionarPontosEcologicosBo(String email, double pontosGanhos) throws ExcecoesConexao, ExcecoesRegraNegocio {
        if (email == null || email.trim().isEmpty()) {
            throw new ExcecoesRegraNegocio("O e-mail informado é inválido.");
        }

        if (pontosGanhos <= 0) {
            throw new ExcecoesRegraNegocio("A quantidade de pontos deve ser maior que zero.");
        }

        try {
            usuarioDao = new UsuarioDao();

            if (usuarioDao.buscarPorEmail(email) == null) {
                throw new ExcecoesRegraNegocio("Usuário não encontrado para o e-mail informado.");
            }

            return usuarioDao.adicionarPontosEcologicos(email, pontosGanhos);
        } catch (ExcecoesRegraNegocio e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExcecoesConexao(e);
        }
    }

    private void validarUsuario(Usuario usuario) throws ExcecoesRegraNegocio {
        if (usuario == null) {
            throw new ExcecoesRegraNegocio("Os dados do usuário não foram informados.");
        }

        if (!usuario.isAceitouTermos()) {
            throw new ExcecoesRegraNegocio("É necessário aceitar os termos de uso para cadastrar.");
        }

        if (usuario.getCpfApenasNumeros() == null) {
            throw new ExcecoesRegraNegocio("O CPF é obrigatório.");
        }

        String cpfLimpo = usuario.getCpfApenasNumeros().replaceAll("[^0-9]", "");
        if (cpfLimpo.length() != 11) {
            throw new ExcecoesRegraNegocio("O CPF deve possuir exatamente 11 caracteres numéricos.");
        }
        usuario.setCpfApenasNumeros(cpfLimpo);

        if (usuario.getEmail() == null || !usuario.getEmail().contains("@")) {
            throw new ExcecoesRegraNegocio("E-mail informado é inválido.");
        }
    }
}