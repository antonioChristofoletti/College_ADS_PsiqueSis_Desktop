package Model;

import java.util.Date;

/**
 * Classe do tipo Model referente aos históricos da aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public class Historico {

    protected int id;
    protected String descricao;
    protected Date data;
    protected Pessoa pessoa;

    public Historico(String descricao, Date data, Pessoa pessoa) {
        this.descricao = descricao;
        this.data = data;
        this.pessoa = pessoa;
    }
    
    public Historico()
    {
        
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}