package Model;

import java.util.ArrayList;

/**
 * Classe do tipo Model referente aos responsáveis da aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public class Responsavel extends Pessoa {
    protected ArrayList<Paciente> pacientes;
    protected String parentesco;
    
    public ArrayList<Paciente> getPacientes() {
        return pacientes;
    }

    public void setPacientes(ArrayList<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }
}