package Model;

import java.util.Date;

/**
 * Classe do tipo Model referente aos agendamentos da aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public class Agendamento {

    protected int id;
    protected String motivo;
    protected String observacao;
    protected Date dataAgendada;
    protected String status;
    protected Date dataCriacao;
    /**
     * Objeto que armazena o usuário que criou o agendamento
     */
    protected Pessoa pessoaCriadora;
    /**
     * Objeto que armazena o funcionário respectivo para aquele agendamento
     */
    protected Funcionario funcionarioResponsavel;

    /**
     * Objeto que armazena a pessoa que sera atendida no agendamento
     */
    protected Pessoa pessoaAtendida;

    /**
     * Duração estipulada do agendamento em minutos
     */
    protected int tempoDuracao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Date getDataAgendada() {
        return dataAgendada;
    }

    public void setDataAgendada(Date dataAgendada) {
        this.dataAgendada = dataAgendada;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Pessoa getPessoaCriadora() {
        return pessoaCriadora;
    }

    public void setPessoaCriadora(Pessoa pessoaCriadora) {
        this.pessoaCriadora = pessoaCriadora;
    }

    public Funcionario getFuncionarioResponsavel() {
        return funcionarioResponsavel;
    }

    public void setFuncionarioResponsavel(Funcionario funcionarioResponsavel) {
        this.funcionarioResponsavel = funcionarioResponsavel;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public int getTempoDuracao() {
        return tempoDuracao;
    }

    public void setTempoDuracao(int tempoDuracao) {
        this.tempoDuracao = tempoDuracao;
    }
    
        public Pessoa getPessoaAtendida() {
        return pessoaAtendida;
    }

    public void setPessoaAtendida(Pessoa pessoaAtendida) {
        this.pessoaAtendida = pessoaAtendida;
    }

    /**
     * Método responsável por clonar um agendamento, o mesmo está relacionado ao
     * padrão de projeto ProtoType
     *
     * @return retorna um clone do objeto em questão
     */
    public Agendamento clonar() {
        Agendamento a = new Agendamento();
        a.setId(id);
        a.setMotivo(motivo);
        a.setDataAgendada(dataAgendada);
        a.setDataCriacao(dataCriacao);
        a.setFuncionarioResponsavel(funcionarioResponsavel);
        a.setObservacao(observacao);
        a.setPessoaCriadora(pessoaCriadora);
        a.setPessoaAtendida(pessoaAtendida);
        a.setStatus(status);
        a.setTempoDuracao(tempoDuracao);
        return a;
    }
}
