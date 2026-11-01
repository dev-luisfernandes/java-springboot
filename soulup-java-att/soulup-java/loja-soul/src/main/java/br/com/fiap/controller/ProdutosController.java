package br.com.fiap.controller;

import br.com.fiap.bo.ProdutosBo;
import br.com.fiap.entities.Administrador;
import br.com.fiap.entities.Produtos;
import br.com.fiap.excecoes.ExcecoesConexao;
import br.com.fiap.excecoes.ExcecoesRegraNegocio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;

@RestController
@RequestMapping("/produtos")
public class ProdutosController {

    ProdutosBo produtosBo = new ProdutosBo();
    Administrador adminPadrao = new Administrador();

    @GetMapping
    public ArrayList<Produtos> selecionarRs() throws ExcecoesConexao {
        return produtosBo.selecionarBo();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produtos> buscarPorIdRs(@PathVariable int id) throws ExcecoesConexao, ExcecoesRegraNegocio {
        Produtos p = produtosBo.buscarPorIdBo(id);
        return (p != null) ? ResponseEntity.ok(p) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Void> inserirRs(
            @RequestHeader(value = "Admin-Email", required = false) String emailAdmin,
            @RequestHeader(value = "Admin-Senha", required = false) String senhaAdmin,
            @RequestBody Produtos produto) throws ExcecoesConexao, ExcecoesRegraNegocio {

        if (!adminPadrao.validarCredenciais(emailAdmin, senhaAdmin)) {
            throw new ExcecoesRegraNegocio("Acesso negado: apenas Administradores podem cadastrar produtos.", HttpStatus.FORBIDDEN);
        }

        produtosBo.inserirBo(produto);
        URI location = URI.create("/produtos/" + produto.getId());
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<Void> atualizarRs(
            @RequestHeader(value = "Admin-Email", required = false) String emailAdmin,
            @RequestHeader(value = "Admin-Senha", required = false) String senhaAdmin,
            @RequestBody Produtos produto) throws ExcecoesConexao, ExcecoesRegraNegocio {

        if (!adminPadrao.validarCredenciais(emailAdmin, senhaAdmin)) {
            throw new ExcecoesRegraNegocio("Acesso negado: apenas Administradores podem cadastrar produtos.", HttpStatus.FORBIDDEN);
        }

        produtosBo.atualizarBo(produto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarRs(
            @RequestHeader(value = "Admin-Email", required = false) String emailAdmin,
            @RequestHeader(value = "Admin-Senha", required = false) String senhaAdmin,
            @PathVariable int id) throws ExcecoesConexao, ExcecoesRegraNegocio {

        if (!adminPadrao.validarCredenciais(emailAdmin, senhaAdmin)) {
            throw new ExcecoesRegraNegocio("Acesso negado: apenas Administradores podem cadastrar produtos.", HttpStatus.FORBIDDEN);
        }

        produtosBo.deletarBo(id);
        return ResponseEntity.ok().build();
    }

}