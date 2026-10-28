package br.com.fiap.dao;

import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.entities.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {

    private Connection obterConexao() throws SQLException, ClassNotFoundException {
        return new ConexaoFactory().conexao();
    }

    public String inserir(Usuario usuario) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO TB_SOUL_USUARIOS (DOCUMENTO, CPF_NUMEROS, NOME_COMPLETO, NOME_USUARIO, EMAIL, SENHA, PAIS, DATA_NASC, CELULAR, ACEITOU_TERMOS, PERFIL, PONTOS, TIER, SITUACAO) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String doc = (usuario.getDocumento() != null && !usuario.getDocumento().trim().isEmpty())
                ? usuario.getDocumento()
                : usuario.getCpfApenasNumeros();

        try (Connection conn = obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, doc);
            stmt.setString(2, usuario.getCpfApenasNumeros());
            stmt.setString(3, usuario.getNomeCompleto());
            stmt.setString(4, usuario.getNomeUsuario());
            stmt.setString(5, usuario.getEmail());
            stmt.setString(6, usuario.getSenha());
            stmt.setString(7, usuario.getPais());
            stmt.setString(8, usuario.getDataNascimento());
            stmt.setString(9, usuario.getCelular());
            stmt.setInt(10, usuario.isAceitouTermos() ? 1 : 0);
            stmt.setString(11, "USER");
            stmt.setDouble(12, 50.0);
            stmt.setString(13, usuario.getTier() != null ? usuario.getTier() : "Verde");
            stmt.setString(14, usuario.getSituacao() != null ? usuario.getSituacao() : "ATIVO");

            stmt.execute();
            return "Usuário cadastrado com sucesso!";
        }
    }

    public Usuario buscarPorEmail(String email) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM TB_SOUL_USUARIOS WHERE EMAIL = ?";
        Usuario u = null;

        try (Connection conn = obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    u = new Usuario();
                    u.setDocumento(rs.getString("DOCUMENTO"));
                    u.setCpfApenasNumeros(rs.getString("CPF_NUMEROS"));
                    u.setNomeCompleto(rs.getString("NOME_COMPLETO"));
                    u.setNomeUsuario(rs.getString("NOME_USUARIO"));
                    u.setEmail(rs.getString("EMAIL"));
                    u.setSenha(rs.getString("SENHA"));
                    u.setPais(rs.getString("PAIS"));
                    u.setDataNascimento(rs.getString("DATA_NASC"));
                    u.setCelular(rs.getString("CELULAR"));
                    u.setAceitouTermos(rs.getInt("ACEITOU_TERMOS") == 1);
                    u.setPontos(rs.getDouble("PONTOS"));
                    u.setTier(rs.getString("TIER"));
                    u.setSituacao(rs.getString("SITUACAO"));
                }
            }
        }
        return u;
    }

    public List<Usuario> selecionar() throws SQLException, ClassNotFoundException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM TB_SOUL_USUARIOS";

        try (Connection conn = obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setDocumento(rs.getString("DOCUMENTO"));
                u.setCpfApenasNumeros(rs.getString("CPF_NUMEROS"));
                u.setNomeCompleto(rs.getString("NOME_COMPLETO"));
                u.setNomeUsuario(rs.getString("NOME_USUARIO"));
                u.setEmail(rs.getString("EMAIL"));
                u.setSenha(rs.getString("SENHA"));
                u.setPais(rs.getString("PAIS"));
                u.setDataNascimento(rs.getString("DATA_NASC"));
                u.setCelular(rs.getString("CELULAR"));
                u.setAceitouTermos(rs.getInt("ACEITOU_TERMOS") == 1);
                u.setPontos(rs.getDouble("PONTOS"));
                u.setTier(rs.getString("TIER"));
                u.setSituacao(rs.getString("SITUACAO"));
                lista.add(u);
            }
        }
        return lista;
    }

    public String atualizar(Usuario usuario) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE TB_SOUL_USUARIOS SET DOCUMENTO = ?, CPF_NUMEROS = ?, NOME_COMPLETO = ?, NOME_USUARIO = ?, SENHA = ?, PAIS = ?, DATA_NASC = ?, CELULAR = ?, ACEITOU_TERMOS = ?, PONTOS = ?, TIER = ?, SITUACAO = ? WHERE EMAIL = ?";

        try (Connection conn = obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getDocumento());
            stmt.setString(2, usuario.getCpfApenasNumeros());
            stmt.setString(3, usuario.getNomeCompleto());
            stmt.setString(4, usuario.getNomeUsuario());
            stmt.setString(5, usuario.getSenha());
            stmt.setString(6, usuario.getPais());
            stmt.setString(7, usuario.getDataNascimento());
            stmt.setString(8, usuario.getCelular());
            stmt.setInt(9, usuario.isAceitouTermos() ? 1 : 0);
            stmt.setDouble(10, usuario.getPontos());
            stmt.setString(11, usuario.getTier());
            stmt.setString(12, usuario.getSituacao());
            stmt.setString(13, usuario.getEmail());

            stmt.executeUpdate();
            return "Usuário atualizado com sucesso!";
        }
    }

    public String adicionarPontosEcologicos(String email, double pontosGanhos) throws SQLException, ClassNotFoundException {
        Usuario usuarioAtual = buscarPorEmail(email);

        if (usuarioAtual != null) {
            // Atualiza a pontuação na entidade
            double novoSaldo = usuarioAtual.getPontos() + pontosGanhos;
            usuarioAtual.setPontos(novoSaldo); // O setPontos chama o atualizarTier() automaticamente

            // Query atualizada para persistir PONTOS e TIER no Oracle
            String sql = "UPDATE TB_SOUL_USUARIOS SET PONTOS = ?, TIER = ? WHERE EMAIL = ?";

            try (Connection conn = obterConexao();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setDouble(1, usuarioAtual.getPontos());
                stmt.setString(2, usuarioAtual.getTier());
                stmt.setString(3, email);

                stmt.executeUpdate();
                return "Parabéns! Você ganhou " + pontosGanhos + " pontos. Saldo: " + novoSaldo + " | Novo Tier: " + usuarioAtual.getTier();
            }
        } else {
            return "Erro: Usuário não encontrado no sistema.";
        }
    }

    public String deletarPorEmail(String email) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM TB_SOUL_USUARIOS WHERE EMAIL = ?";

        try (Connection conn = obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                return "Usuário deletado com sucesso!";
            } else {
                return "Erro: Usuário não encontrado.";
            }
        }
    }
}