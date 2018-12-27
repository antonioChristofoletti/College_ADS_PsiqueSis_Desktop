package Model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Classe do tipo Model referente aos orçamentos da aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public class Orcamento {

    protected int id;
    protected Date dataVencimento;
    protected String descricao;
    protected String status;
    protected String tipo;
    protected Funcionario funcionarioResponsavel;
    protected ArrayList<Parcela> parcelas;
    protected double valorRegimeCompetencia;
    protected double valorRegimeCaixa;

    public Orcamento() {
        parcelas = new ArrayList();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date data) {
        this.dataVencimento = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Funcionario getFuncionarioResponsavel() {
        return funcionarioResponsavel;
    }

    public void setFuncionarioResponsavel(Funcionario funcionarioResponsavel) {
        this.funcionarioResponsavel = funcionarioResponsavel;
    }

    public ArrayList<Parcela> getParcelas() {
        return parcelas;
    }

    public void setParcelas(ArrayList<Parcela> parcelas) {
        this.parcelas = parcelas;

        valorRegimeCaixa = valorRegimeCompetencia = 0;
        for (Parcela parcela : parcelas) {
            if (parcela.getStatus().equals("Pago")) {
                valorRegimeCaixa += parcela.getValor();
            }
            valorRegimeCompetencia += parcela.getValor();
        }
    }

    public double getValorRegimeCompetencia() {
        return valorRegimeCompetencia;
    }

    public double getValorRegimeCaixa() {
        return valorRegimeCaixa;
    }
}
