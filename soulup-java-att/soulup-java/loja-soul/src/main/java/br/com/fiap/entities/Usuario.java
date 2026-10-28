package br.com.fiap.entities;

public class Usuario {

    private int usuarioId;
    private String documento;
    private String cpfApenasNumeros;
    private String nomeCompleto;
    private String nomeUsuario;
    private String email;
    private String senha;
    private String pais;
    private String dataNascimento;
    private String celular;
    private boolean aceitouTermos;
    private String perfil; // Fixo como "USER"
    private double pontos; // Inicia sempre com 50
    private String tier;   // "Verde", "Prata", "Ouro"
    private String situacao;

    public Usuario() {
        this.pontos = 50.0;
        this.perfil = "USER";
        this.situacao = "ATIVO";
        atualizarTier();
    }

    public Usuario(int usuarioId, String documento, String cpfApenasNumeros, String nomeCompleto, String nomeUsuario,
                   String email, String senha, String pais, String dataNascimento, String celular, boolean aceitouTermos) {
        this.usuarioId = usuarioId;
        this.documento = documento;
        this.cpfApenasNumeros = cpfApenasNumeros;
        this.nomeCompleto = nomeCompleto;
        this.nomeUsuario = nomeUsuario;
        this.email = email;
        this.senha = senha;
        this.pais = pais;
        this.dataNascimento = dataNascimento;
        this.celular = celular;
        this.aceitouTermos = aceitouTermos;
        this.perfil = "USER";
        this.pontos = 50.0;
        this.situacao = "ATIVO";
        atualizarTier();
    }

    public void atualizarTier() {
        if (this.pontos >= 5550) {
            this.tier = "Ouro";
        } else if (this.pontos >= 3000) {
            this.tier = "Prata";
        } else {
            this.tier = "Verde";
        }
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getCpfApenasNumeros() {
        return cpfApenasNumeros;
    }

    public void setCpfApenasNumeros(String cpfApenasNumeros) {
        this.cpfApenasNumeros = cpfApenasNumeros;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }
    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public boolean isAceitouTermos() {
        return aceitouTermos;
    }

    public void setAceitouTermos(boolean aceitouTermos) {
        this.aceitouTermos = aceitouTermos;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = "USER";
    }

    public double getPontos() {
        return pontos;
    }

    public void setPontos(double pontos) {
        this.pontos = pontos;
        atualizarTier();
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getSituacao() {
        return situacao;
    }
    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "usuarioId=" + usuarioId +
                ", documento='" + documento + '\'' +
                ", cpfApenasNumeros='" + cpfApenasNumeros + '\'' +
                ", nomeCompleto='" + nomeCompleto + '\'' +
                ", nomeUsuario='" + nomeUsuario + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", pais='" + pais + '\'' +
                ", dataNascimento='" + dataNascimento + '\'' +
                ", celular='" + celular + '\'' +
                ", aceitouTermos=" + aceitouTermos +
                ", perfil='" + perfil + '\'' +
                ", pontos=" + pontos +
                ", tier='" + tier + '\'' +
                ", situacao='" + situacao + '\'' +
                '}';
    }
}