package Model;

import java.util.ArrayList;

/**
 * Classe do tipo Model referente as permissões da aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public class Permissao {

    protected int id;
    protected String descricao;
    protected String status;
    protected ArrayList<Pessoa> usuarios;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Pessoa> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<Pessoa> usuarios) {
        this.usuarios = usuarios;
    }
}
