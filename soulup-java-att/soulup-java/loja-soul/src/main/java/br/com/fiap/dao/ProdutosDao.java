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

    public Connection minhaConexao;

    public ProdutosDao() throws SQLException, ClassNotFoundException {
        super();
        this.minhaConexao = new ConexaoFactory().conexao();
    }

    public List<Produtos> selecionar() throws SQLException {
        List<Produtos> lista = new ArrayList<>();
        PreparedStatement stmt = minhaConexao.prepareStatement("SELECT * FROM TB_SOUL_PRODUTOS");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Produtos p = new Produtos();
            p.setId(rs.getInt("ID"));
            p.setNome(rs.getString("NOME"));
            p.setDescricao(rs.getString("DESCRICAO"));
            p.setUrlImagem(rs.getString("URL_IMAGEM"));
            p.setQuantidade(rs.getInt("QUANTIDADE"));
            p.setTipo(rs.getString("TIPO"));
            p.setPontos(rs.getDouble("PONTOS"));
            lista.add(p);
        }

        rs.close();
        stmt.close();
        return lista;
    }

    public Produtos buscarPorId(int id) throws SQLException {
        Produtos p = null;
        PreparedStatement stmt = minhaConexao.prepareStatement("SELECT * FROM TB_SOUL_PRODUTOS WHERE ID = ?");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            p = new Produtos();
            p.setId(rs.getInt("ID"));
            p.setNome(rs.getString("NOME"));
            p.setDescricao(rs.getString("DESCRICAO"));
            p.setUrlImagem(rs.getString("URL_IMAGEM"));
            p.setQuantidade(rs.getInt("QUANTIDADE"));
            p.setTipo(rs.getString("TIPO"));
            p.setPontos(rs.getDouble("PONTOS"));
        }

        rs.close();
        stmt.close();
        return p;
    }

    public String inserir(Produtos produto) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "INSERT INTO TB_SOUL_PRODUTOS (NOME, DESCRICAO, URL_IMAGEM, QUANTIDADE, TIPO, PONTOS) VALUES (?, ?, ?, ?, ?, ?)");
        stmt.setString(1, produto.getNome());
        stmt.setString(2, produto.getDescricao());
        stmt.setString(3, produto.getUrlImagem());
        stmt.setInt(4, produto.getQuantidade());
        stmt.setString(5, produto.getTipo());
        stmt.setDouble(6, produto.getPontos());

        stmt.execute();
        stmt.close();
        return "Produto cadastrado com sucesso!";
    }

    public String atualizar(Produtos produto) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "UPDATE TB_SOUL_PRODUTOS SET NOME = ?, DESCRICAO = ?, URL_IMAGEM = ?, QUANTIDADE = ?, TIPO = ?, PONTOS = ? WHERE ID = ?");
        stmt.setString(1, produto.getNome());
        stmt.setString(2, produto.getDescricao());
        stmt.setString(3, produto.getUrlImagem());
        stmt.setInt(4, produto.getQuantidade());
        stmt.setString(5, produto.getTipo());
        stmt.setDouble(6, produto.getPontos());
        stmt.setInt(7, produto.getId());

        stmt.executeUpdate();
        stmt.close();
        return "Produto atualizado com sucesso!";
    }

    public String deletar(int id) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement("DELETE FROM TB_SOUL_PRODUTOS WHERE ID = ?");
        stmt.setInt(1, id);
        stmt.execute();
        stmt.close();
        return "Produto deletado com sucesso!";
    }

}