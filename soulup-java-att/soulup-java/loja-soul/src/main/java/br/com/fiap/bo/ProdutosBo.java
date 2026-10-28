package br.com.fiap.bo;

import br.com.fiap.dao.ProdutosDao;
import br.com.fiap.entities.Produtos;
import br.com.fiap.excecoes.ExcecoesConexao;
import br.com.fiap.excecoes.ExcecoesRegraNegocio;

import java.util.ArrayList;

public class ProdutosBo {

    private ProdutosDao produtosDao;

    public ArrayList<Produtos> selecionarBo() throws ExcecoesConexao {
        try {
            produtosDao = new ProdutosDao();
            return (ArrayList<Produtos>) produtosDao.selecionar();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExcecoesConexao(e);
        }
    }

    public Produtos buscarPorIdBo(int id) throws ExcecoesConexao, ExcecoesRegraNegocio {
        if (id <= 0) {
            throw new ExcecoesRegraNegocio("O ID do produto deve ser maior que zero.");
        }

        try {
            produtosDao = new ProdutosDao();
            return produtosDao.buscarPorId(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExcecoesConexao(e);
        }
    }

    public void inserirBo(Produtos produto) throws ExcecoesConexao, ExcecoesRegraNegocio {
        validarProduto(produto);

        try {
            produtosDao = new ProdutosDao();
            produtosDao.inserir(produto);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExcecoesConexao(e);
        }
    }

    public void atualizarBo(Produtos produto) throws ExcecoesConexao, ExcecoesRegraNegocio {
        if (produto == null || produto.getId() <= 0) {
            throw new ExcecoesRegraNegocio("O ID do produto é obrigatório para atualização.");
        }

        validarProduto(produto);

        try {
            produtosDao = new ProdutosDao();

            if (produtosDao.buscarPorId(produto.getId()) == null) {
                throw new ExcecoesRegraNegocio("Produto não encontrado para atualização.");
            }

            produtosDao.atualizar(produto);
        } catch (ExcecoesRegraNegocio e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExcecoesConexao(e);
        }
    }

    public void deletarBo(int id) throws ExcecoesConexao, ExcecoesRegraNegocio {
        if (id <= 0) {
            throw new ExcecoesRegraNegocio("O ID do produto é inválido.");
        }

        try {
            produtosDao = new ProdutosDao();

            if (produtosDao.buscarPorId(id) == null) {
                throw new ExcecoesRegraNegocio("Produto não encontrado para o ID informado.");
            }

            produtosDao.deletar(id);
        } catch (ExcecoesRegraNegocio e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExcecoesConexao(e);
        }
    }

    private void validarProduto(Produtos produto) throws ExcecoesRegraNegocio {
        if (produto == null) {
            throw new ExcecoesRegraNegocio("Os dados do produto não foram informados.");
        }

        if (produto.getNome() == null || produto.getNome().trim().isEmpty()) {
            throw new ExcecoesRegraNegocio("O nome do produto é obrigatório.");
        }

        if (produto.getPontos() <= 0) {
            throw new ExcecoesRegraNegocio("O preço em pontos do produto deve ser maior que zero.");
        }
    }
}