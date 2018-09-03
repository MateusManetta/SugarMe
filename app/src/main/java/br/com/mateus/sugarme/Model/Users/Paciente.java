package br.com.mateus.sugarme.Model.Users;

import java.io.Serializable;

public class Paciente implements Serializable {
    private String nome;
    private String telefone;
    private String dtNascimento;
    private String cpf;

    public Paciente(String nome, String telefone, String dtNascimento, String cpf) {
        this.nome = nome;
        this.telefone = telefone;
        this.dtNascimento = dtNascimento;
        this.cpf = cpf;
    }

    public Paciente() {
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(String dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }


}
