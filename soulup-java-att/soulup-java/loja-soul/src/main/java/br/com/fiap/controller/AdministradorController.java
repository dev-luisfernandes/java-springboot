package br.com.fiap.controller;

import br.com.fiap.bo.AdministradorBo;
import br.com.fiap.entities.Administrador;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/administradores")
public class AdministradorController {

    private AdministradorBo adminBo = new AdministradorBo();

    // POST /administradores/login -> Autenticação de E-mail e Senha
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Administrador adminInput) {
        try {
            Administrador adminAutenticado = adminBo.autenticarBo(adminInput.getEmail(), adminInput.getSenha());

            if (adminAutenticado != null) {
                return ResponseEntity.ok(adminAutenticado);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("E-mail ou senha inválidos.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro na autenticação: " + e.getMessage());
        }
    }
}