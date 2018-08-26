package br.com.mateus.sugarme.Model.Users;

public class Medico {
    private String nome;
    private String telefone;
    private String dtNascimento;
    private String cpf;
    private String crm;
    private String especialidade;
    private String uf;



    public Medico(String nome, String telefone, String dtNascimento, String cpf, String crm, String especialidade, String uf) {
        this.nome = nome;
        this.telefone = telefone;
        this.dtNascimento = dtNascimento;
        this.cpf = cpf;
        this.crm = crm;
        this.uf = uf;
        this.especialidade = especialidade;
    }

    public Medico() {
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

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
}
