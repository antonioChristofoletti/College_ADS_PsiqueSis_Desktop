package Model;

import java.util.Date;

/**
 * Classe do tipo Model referente as parcelas da aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public class Parcela {
    protected int id;
    protected Date dataVencimento;
    protected String status;
    protected Double Valor;
    protected String descricao;
    protected Orcamento orcamento;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getValor() {
        return Valor;
    }

    public void setValor(Double Valor) {
        this.Valor = Valor;
    }

    public Orcamento getorcamento() {
        return orcamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
    }
}