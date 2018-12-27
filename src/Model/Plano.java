package Model;

import java.util.ArrayList;

/**
 * Classe do tipo Model referente aos planos da aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public class Plano {
    protected int id;
    protected String nomeInstituicao;
    protected int quantiConsultas;
    protected String descricao;
    protected String status;
    protected ArrayList<Paciente> pacientes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeInstituicao() {
        return nomeInstituicao;
    }

    public void setNomeInstituicao(String nomeInstituicao) {
        this.nomeInstituicao = nomeInstituicao;
    }

    public int getQuantiConsultas() {
        return quantiConsultas;
    }

    public void setQuantiConsultas(int quantiConsultas) {
        this.quantiConsultas = quantiConsultas;
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

    public ArrayList<Paciente> getPacientes() {
        return pacientes;
    }

    public void setPacientes(ArrayList<Paciente> pacientes) {
        this.pacientes = pacientes;
    }
}