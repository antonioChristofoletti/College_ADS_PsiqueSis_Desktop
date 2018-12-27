package Model;

import java.util.ArrayList;

/**
 * Classe do tipo Model referente aos funcionários da aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public class Funcionario extends Pessoa {

    protected String email;
    protected ArrayList<Agendamento> agendamentosRelacionados;
    protected ArrayList<Orcamento> financeiro;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList getAgendamentosRelacionados() {
        return agendamentosRelacionados;
    }

    public void setAgendamentosRelacionados(ArrayList agendamentosRelacionados) {
        this.agendamentosRelacionados = agendamentosRelacionados;
    }

    public ArrayList getFinanceiro() {
        return financeiro;
    }

    public void setFinanceiro(ArrayList financeiro) {
        this.financeiro = financeiro;
    }
}