package br.com.fiap.excecoes;

import org.springframework.http.HttpStatus;

public class ExcecoesRegraNegocio extends Exception {

    private final HttpStatus status;

    public ExcecoesRegraNegocio() {
        super();
        this.status = HttpStatus.BAD_REQUEST;
    }

    public ExcecoesRegraNegocio(String mensagem) {
        this(mensagem, HttpStatus.BAD_REQUEST);
    }

    public ExcecoesRegraNegocio(String mensagem, HttpStatus status) {
        super(mensagem);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}