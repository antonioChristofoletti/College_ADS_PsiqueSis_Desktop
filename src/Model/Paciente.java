package Model;

import java.util.ArrayList;
import java.util.Date;


/**
 * Classe do tipo Model referente aos pacientes da aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public class Paciente extends Pessoa {
    protected String numeroPasta;
    protected String capaz;
    protected Date dataInicio;
    protected Plano plano;
    protected ArrayList<Localizacao> localizacoes;
    protected ArrayList<Responsavel> responsaveis;
    protected ArrayList<Atividade> pacienteAtividades;
    
    public String getNumeroPasta() {
        return numeroPasta;
    }

    public void setNumeroPasta(String numeroPasta) {
        this.numeroPasta = numeroPasta;
    }

    public String getCapaz() {
        return capaz;
    }

    public void setCapaz(String capaz) {
        this.capaz = capaz;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public ArrayList<Localizacao> getLocalizacoes() {
        return localizacoes;
    }

    public void setLocalizacoes(ArrayList<Localizacao> localizacoes) {
        this.localizacoes = localizacoes;
    }

    public ArrayList<Responsavel> getResponsaveis() {
        return responsaveis;
    }

    public void setResponsaveis(ArrayList<Responsavel> responsaveis) {
        this.responsaveis = responsaveis;
    }  

    public ArrayList<Atividade> getPacienteAtividades() {
        return pacienteAtividades;
    }

    public void setPacienteAtividades(ArrayList<Atividade> pacienteAtividades) {
        this.pacienteAtividades = pacienteAtividades;
    }
}