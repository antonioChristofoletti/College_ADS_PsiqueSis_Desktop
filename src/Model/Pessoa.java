package Model;

import java.util.ArrayList;

/**
 * Classe do tipo Model referente as pessoas da aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public abstract class Pessoa {

    protected int idPessoa;
    protected int idEspecifico;
    protected String nome;
    protected String rg;
    protected String cpf;
    protected String telefone1;
    protected String telefone2;
    protected String cidade;
    protected String bairro;
    protected String logradouro;
    protected String nomeLogin;
    protected String senha;
    protected String status;
    protected ArrayList<Permissao> permissoes;
    protected ArrayList<Agendamento> agendamentosAgendados;
    protected ArrayList<Historico> historicos;
    protected Maquina maquina;

    public Pessoa() {
        permissoes = new ArrayList();
        agendamentosAgendados = new ArrayList();
        historicos = new ArrayList();
    }

    public static Pessoa factory(String tipo) throws Exception {
        switch (tipo) {
            case "Funcionário":
                return new Funcionario();
            case "Paciente":
                return new Paciente();
            case "Responsável":
                return new Responsavel();
        }

        throw new Exception("Erro ao retornar pessoa. Tipo de pessoa não encontrado");
    }

    public int getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNomeLogin() {
        return nomeLogin;
    }

    public void setNomeLogin(String nomeLogin) {
        this.nomeLogin = nomeLogin;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Permissao> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(ArrayList<Permissao> permissoes) {
        this.permissoes = permissoes;
    }

    public ArrayList<Agendamento> getAgendamentosAgendados() {
        return agendamentosAgendados;
    }

    public void setAgendamentosAgendados(ArrayList<Agendamento> agendamentosAgendados) {
        this.agendamentosAgendados = agendamentosAgendados;
    }

    public ArrayList<Historico> getHistoricos() {
        return historicos;
    }

    public void setHistoricos(ArrayList<Historico> historicos) {
        this.historicos = historicos;
    }

    public Maquina getMaquina() {
        return maquina;
    }

    public void setMaquina(Maquina maquina) {
        this.maquina = maquina;
    }

    public int getIdEspecifico() {
        return idEspecifico;
    }

    public void setIdEspecifico(int idFuncionario) {
        this.idEspecifico = idFuncionario;
    }
}
