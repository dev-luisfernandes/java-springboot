package br.com.fiap.controller;

import br.com.fiap.bo.UsuarioBo;
import br.com.fiap.entities.Administrador;
import br.com.fiap.entities.Usuario;
import br.com.fiap.excecoes.ExcecoesConexao;
import br.com.fiap.excecoes.ExcecoesRegraNegocio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    UsuarioBo usuarioBo = new UsuarioBo();
    Administrador adminPadrao = new Administrador();

    @GetMapping
    public ResponseEntity<ArrayList<Usuario>> selecionarRs(
            @RequestHeader(value = "Admin-Email", required = false) String emailAdmin,
            @RequestHeader(value = "Admin-Senha", required = false) String senhaAdmin) throws ExcecoesConexao, ExcecoesRegraNegocio {

        if (!adminPadrao.validarCredenciais(emailAdmin, senhaAdmin)) {
            throw new ExcecoesRegraNegocio("Acesso negado: apenas Administradores possuem essa permissão.");
        }
        return ResponseEntity.ok(usuarioBo.selecionarBo());
    }

    // Deletar usuário por e-mail (Exclusivo ADMIN)
    @DeleteMapping("/email/{email}")
    public ResponseEntity<Void> deletarRs(
            @RequestHeader(value = "Admin-Email", required = false) String emailAdmin,
            @RequestHeader(value = "Admin-Senha", required = false) String senhaAdmin,
            @PathVariable String email) throws ExcecoesConexao, ExcecoesRegraNegocio {

        if (!adminPadrao.validarCredenciais(emailAdmin, senhaAdmin)) {
            throw new ExcecoesRegraNegocio("Acesso negado: apenas Administradores podem deletar usuários.");
        }

        usuarioBo.deletarPorEmailBo(email);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> buscarPorEmailRs(@PathVariable String email) throws ExcecoesConexao, ExcecoesRegraNegocio {
        Usuario usuario = usuarioBo.buscarPorEmailBo(email);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Void> inserirRs(@RequestBody Usuario usuario) throws ExcecoesConexao, ExcecoesRegraNegocio {
        usuarioBo.inserirBo(usuario);
        URI location = URI.create("/usuario/email/" + usuario.getEmail());
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/pontos/{email}/{pontosGanhos}")
    public ResponseEntity<String> adicionarPontosRs(
            @PathVariable String email,
            @PathVariable double pontosGanhos) throws ExcecoesConexao, ExcecoesRegraNegocio {

        String mensagem = usuarioBo.adicionarPontosEcologicosBo(email, pontosGanhos);
        return ResponseEntity.ok(mensagem);
    }

    // PUT: Atualizar dados do usuário
    @PutMapping
    public ResponseEntity<Void> atualizarRs(@RequestBody Usuario usuario) throws ExcecoesConexao, ExcecoesRegraNegocio {
        usuarioBo.atualizarBo(usuario);
        return ResponseEntity.ok().build();
    }

    // POST: Login/Validação simples de usuário comum
    @PostMapping("/login")
    public ResponseEntity<Usuario> loginRs(@RequestBody Usuario loginInput) throws ExcecoesConexao, ExcecoesRegraNegocio {
        Usuario usuario = usuarioBo.buscarPorEmailBo(loginInput.getEmail());

        if (usuario != null && usuario.getSenha().equals(loginInput.getSenha())) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.status(org.springframework.http.HttpStatus.UNAUTHORIZED).build();
    }

}