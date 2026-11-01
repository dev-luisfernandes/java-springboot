package br.com.fiap.dao;

import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.entities.Produtos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutosDao {

    private Connection obterConexao() throws SQLException, ClassNotFoundException {
        return new ConexaoFactory().conexao();
    }

    private Produtos mapear(ResultSet rs) throws SQLException {
        Produtos p = new Produtos();
        p.setId(rs.getInt("ID"));
        p.setNome(rs.getString("NOME"));
        p.setDescricao(rs.getString("DESCRICAO"));
        p.setUrlImagem(rs.getString("URL_IMAGEM"));
        p.setQuantidade(rs.getInt("QUANTIDADE"));
        p.setTipo(rs.getString("TIPO"));
        p.setPontos(rs.getDouble("PONTOS"));
        return p;
    }

    public List<Produtos> selecionar() throws SQLException, ClassNotFoundException {
        List<Produtos> lista = new ArrayList<>();
        try (Connection conn = obterConexao();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM TB_SOUL_PRODUTOS");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public Produtos buscarPorId(int id) throws SQLException, ClassNotFoundException {
        try (Connection conn = obterConexao();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM TB_SOUL_PRODUTOS WHERE ID = ?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    public String inserir(Produtos produto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO TB_SOUL_PRODUTOS (NOME, DESCRICAO, URL_IMAGEM, QUANTIDADE, TIPO, PONTOS) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"ID"})) {
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setString(3, produto.getUrlImagem());
            stmt.setInt(4, produto.getQuantidade());
            stmt.setString(5, produto.getTipo());
            stmt.setDouble(6, produto.getPontos());
            stmt.executeUpdate();

            // Devolve o ID gerado ao objeto (usado no header Location do POST)
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    produto.setId(keys.getInt(1));
                }
            } catch (SQLException ignorado) {
                // o insert já foi feito; só não foi possível ler o ID
            }
        }
        return "Produto cadastrado com sucesso!";
    }

    public String atualizar(Produtos produto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE TB_SOUL_PRODUTOS SET NOME = ?, DESCRICAO = ?, URL_IMAGEM = ?, QUANTIDADE = ?, TIPO = ?, PONTOS = ? WHERE ID = ?";
        try (Connection conn = obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setString(3, produto.getUrlImagem());
            stmt.setInt(4, produto.getQuantidade());
            stmt.setString(5, produto.getTipo());
            stmt.setDouble(6, produto.getPontos());
            stmt.setInt(7, produto.getId());
            stmt.executeUpdate();
        }
        return "Produto atualizado com sucesso!";
    }

    public String deletar(int id) throws SQLException, ClassNotFoundException {
        try (Connection conn = obterConexao();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM TB_SOUL_PRODUTOS WHERE ID = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
        return "Produto deletado com sucesso!";
    }
}