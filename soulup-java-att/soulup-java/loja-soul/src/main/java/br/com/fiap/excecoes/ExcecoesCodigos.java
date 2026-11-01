package br.com.fiap.excecoes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Map;

@RestControllerAdvice
public class ExcecoesCodigos {

    private ResponseEntity<Map<String, String>> resposta(HttpStatus status, String mensagem) {
        return ResponseEntity.status(status).body(
                Map.of("erro", String.valueOf(status.value()), "mensagem", mensagem));
    }

    // Regras de negócio: o status vem da própria exceção (400, 403, 404, 409...)
    @ExceptionHandler(ExcecoesRegraNegocio.class)
    public ResponseEntity<Map<String, String>> regraNegocio(ExcecoesRegraNegocio e) {
        return resposta(e.getStatus(), e.getMessage());
    }

    @ExceptionHandler({IllegalArgumentException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<Map<String, String>> erro400(Exception e) {
        return resposta(HttpStatus.BAD_REQUEST,
                "Os dados enviados são inválidos verifique as informações e tente novamente.");
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, String>> erro404(NoResourceFoundException e) {
        return resposta(HttpStatus.NOT_FOUND,
                "O recurso solicitado não foi encontrado verifique o endereço e tente novamente.");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, String>> erro405(HttpRequestMethodNotSupportedException e) {
        return resposta(HttpStatus.METHOD_NOT_ALLOWED, "Método HTTP não permitido para este endereço.");
    }

    @ExceptionHandler(ExcecoesConexao.class)
    public ResponseEntity<Map<String, String>> erroConexao(ExcecoesConexao e) {
        return resposta(HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocorreu um erro inesperado no servidor tente novamente mais tarde");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> erro500(Exception e) {
        return resposta(HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocorreu um erro inesperado no servidor tente novamente mais tarde");
    }
}