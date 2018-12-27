package Controller;

import DAO.AgendamentoDAO;
import Model.Agendamento;
import Model.Funcionario;
import Model.Pessoa;
import View.FrmAgendamentoIncAlt;
import View.FrmAgendamentoLista;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JSpinner.NumberEditor;

/**
 * Classe do tipo controller relacionada aos agendamentos da aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public class AgendamentoController {

    /**
     * Tela de agendamento lista
     */
    private FrmAgendamentoLista frmAgendamentoLista;

    /**
     * Tela de agendamento Inc/Alt
     */
    private FrmAgendamentoIncAlt frmAgendamentoIncAlt;

    /**
     * Objeto do tipo AgendamentoDAO
     */
    private AgendamentoDAO agendamentoDAO;

    /**
     * Instância da própria classe em questão, por conta do padrão de projeto
     * Sington
     */
    private static AgendamentoController agendamentoController;

    /**
     * Construtor da classe, na qual o agendamentoDAO será instânciado
     */
    private AgendamentoController() {
        agendamentoDAO = new AgendamentoDAO();
    }

    /**
     * Método responsável por retornar uma instância da própria classe
     *
     * @return Retorna uma instância da própria classe
     */
    public static AgendamentoController getInstance() {
        if (agendamentoController == null) {
            agendamentoController = new AgendamentoController();
        }

        return agendamentoController;
    }

    /**
     * Método responsável por invocar a tela de FrmAgendamentoIncAlt
     *
     * @param a agendamento de edição, pode ser um parâmetro null caso a ideia
     * seja inserir um novo registro
     * @param visible visibilidade da tela, pode ser true ou false
     */
    public void frmAgendamentoIncAlt(Agendamento a, boolean visible) {
        frmAgendamentoIncAlt = FrmAgendamentoIncAlt.getInstance(a);
        this.frmAgendamentoIncAlt.setVisible(visible);
    }

    /**
     * Método responsável por invocar a tela de FrmAgendamentoLista
     *
     * @param visible visibilidade da tela, pode ser true ou false
     */
    public void frmAgendamentoLista(boolean visible) {
        frmAgendamentoLista = FrmAgendamentoLista.getInstance();
        this.frmAgendamentoLista.setVisible(visible);
    }

    /**
     * Método responsável por inserir um novo agendamento
     *
     * @param a agendamento que será inserido
     * @throws Exception caso ocorra algum erro durante a fase de inserção dos
     * dados no banco de dados
     */
    public void inserirAgendamento(Agendamento a) throws Exception {
        agendamentoDAO.inserir(a);
    }

    /**
     * Método responsável por editar um agendamento
     *
     * @param a agendamento que será editado
     * @throws Exception caso ocorra algum erro durante a fase de edição dos
     * dados no banco de dados
     */
    public void editarAgendamento(Agendamento a) throws Exception {
        agendamentoDAO.editar(a);
    }

    /**
     * Método responsável por realizar uma pesquisa de agendamentos
     *
     * @param idFuncionario id do funcionário relacionado a pesquisa, pode ser
     * "" caso esse parâmetro não seja adequado na pesquisa em questão
     * @param dtInicial data inicial em que o agendamento foi criado, pode ser
     * null caso esse parâmetro não seja adequado na pesquisa em questão
     * @param dtFinal data final em que o agendamento foi criado, pode ser null
     * caso esse parâmetro não seja adequado na pesquisa em questão
     * @param situacao situação do agendamento
     * @param ordemDados referente ao ORDER BY da consulta
     * @return retorna um ArrayList com os agendamentos encontrados
     * @throws Exception gerado caso ocorra algum erro durante a pesquisa
     */
    public ArrayList pesquisarAgendamento(String idFuncionario, Date dtInicial, Date dtFinal, String situacao, String ordemDados) throws Exception {
        return agendamentoDAO.pesquisar(idFuncionario, dtInicial, dtFinal, situacao, ordemDados);
    }

    /**
     * Método responsável por validar os atributos do agendamento
     *
     * @param a agendamento que será validado
     * @throws Exception exceção gerada caso algum atributo esteja inválido
     */
    public void validarAtributos(Agendamento a) throws Exception {
        if (a.getDataAgendada() == null) {
            frmAgendamentoIncAlt.getTxtDataAgendamento().requestFocus();
            frmAgendamentoIncAlt.getTxtDataAgendamento().requestFocusInWindow();
            throw new Exception("Data agendamento inválida");
        }

        if (a.getTempoDuracao() <= 0) {
            frmAgendamentoIncAlt.getTxtTempoMinutos().requestFocus();
            NumberEditor ned = (NumberEditor) frmAgendamentoIncAlt.getTxtTempoMinutos().getEditor();
            ned.getTextField().requestFocusInWindow();
            throw new Exception("O tempo de duração deve ser superior a 0");
        }

        if (a.getFuncionarioResponsavel() == null) {
            frmAgendamentoIncAlt.getTxtFuncionarioDestino().requestFocus();
            throw new Exception("O funcionário de destino é inválido");
        }

        if (a.getFuncionarioResponsavel() == null) {
            frmAgendamentoIncAlt.getTxtPessoaAtendida().requestFocus();
            throw new Exception("A pessoa atendida é inválida");
        }

        if (a.getStatus().equals("")) {
            frmAgendamentoIncAlt.getCmbSituacao().requestFocus();
            throw new Exception("O status é inválido");
        }

        if (a.getMotivo().equals("")) {
            frmAgendamentoIncAlt.getTxtMotivo().requestFocus();
            throw new Exception("O motivo é inválido");
        }

        if (a.getObservacao().equals("")) {
            frmAgendamentoIncAlt.getTxtObservacoes().requestFocus();
            throw new Exception("A observação é inválido");
        }
    }

    /**
     * Método responsável por replicar e inserir os novos agendamentos no banco
     * de dados através da classe DAO
     *
     * @param agendamentoOriginal agendamento que será utilizado como base
     * @param quantiAgendamentos quantidade de agendamentos novos que deverão
     * ser gerados
     * @param quantiHoras intervalo de horas entre cada agendamento gerado
     * @throws Exception disparada durante a fase de duplicação ou de inserção
     * dos novos agendamentos
     */
    public void replicarAgendamento(Agendamento agendamentoOriginal, int quantiAgendamentos, int quantiHoras) throws Exception {
        Date d = new Date();
        Date proximaData = agendamentoOriginal.getDataAgendada();
        Calendar calendar = Calendar.getInstance();
        Pessoa p = new Funcionario();
        p.setIdPessoa(1);
        for (int i = 0; i < quantiAgendamentos; i++) {
            Agendamento a = agendamentoOriginal.clonar();

            a.setDataCriacao(new Date());
            calendar.setTime(proximaData);
            calendar.add(Calendar.HOUR, quantiHoras);
            proximaData = calendar.getTime();
            a.setDataAgendada(proximaData);

            agendamentoDAO.inserir(a);
        }
    }

    /**
     * Método responsável por verificar se um agendamento está com conflito com
     * outros agendamentos
     *
     * @param id id do agendamento
     * @return retorna um ArrayList com os 'ids' dos agendamentos que estão
     * causando os conflitos
     * @throws Exception exceção gerada durante a fase de pesquisa ou análise
     */
    public ArrayList existeConflitoData(String id) throws Exception {
        return agendamentoDAO.existeConflitoData(id);
    }
}
