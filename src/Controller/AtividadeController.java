package Controller;

import DAO.AtividadeDAO;
import Interfaces_Inversao_Controle.IPesquisarAtividade;
import Model.Atividade;
import View.FrmAtividadeIncAlt;
import View.FrmAtividadeLista;
import View.FrmAtividadePesquisar;
import java.util.ArrayList;

/**
 * Classe do tipo controller relacionada as atividades da aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public class AtividadeController {

    /**
     * Tela de atividade lista
     */
    private FrmAtividadeLista FrmatividadeLista;
    /**
     * Tela de atividade Inc/Alt
     */
    private FrmAtividadeIncAlt frmAtividadeIncAlt;
    /**
     * Tela de atividade de pesquisa
     */
    private FrmAtividadePesquisar frmAtividadePesquisar;
    /**
     * Objeto DAO referente a Atividade
     */
    private AtividadeDAO atividadeDAO;
    /**
     * Objeto da própria classe AtividadeController por conta do padrão de
     * projeto Sington
     */
    private static AtividadeController atividadeController;

    /**
     * Construtor da classe
     */
    private AtividadeController() {
        atividadeDAO = new AtividadeDAO();
    }

    /**
     * Retorna uma instância da própria classe
     *
     * @return retorna uma instância da própria classe
     */
    public static AtividadeController getInstance() {
        if (atividadeController == null) {
            atividadeController = new AtividadeController();
        }

        return atividadeController;
    }

    /**
     * Método responsável por invocar a tela de FrmAtividadeLista
     *
     * @param visible visibilidade da tela, pode ser true ou false
     */
    public void frmAtividadeListaSetVisible(boolean visible) {
        FrmatividadeLista = FrmAtividadeLista.getInstance();
        this.FrmatividadeLista.setVisible(visible);
    }

    /**
     * Método responsável por invocar a tela de FrmAtividadeIncAlt
     *
     * @param a atividade de edição, pode ser um parâmetro null caso a ideia
     * seja inserir um novo registro
     * @param visible visibilidade da tela, pode ser true ou false
     */
    public void frmAtividadeIncAltSetVisible(Atividade a, boolean visible) {
        frmAtividadeIncAlt = FrmAtividadeIncAlt.getInstance(a);
        this.frmAtividadeIncAlt.setVisible(visible);
    }

    /**
     * Método responsável por invocar a tela de FrmAtividadePesquisar
     *
     * @param iPesquisarAtividade objeto que implementa a interface
     * 'IPesquisarAtividade' que está chamando esse método
     * @param visible visibilidade da tela, pode ser true ou false
     */
    public void frmAtividadePesquisarSetVisible(IPesquisarAtividade iPesquisarAtividade, boolean visible) {
        frmAtividadePesquisar = FrmAtividadePesquisar.getInstance(iPesquisarAtividade);
        this.frmAtividadePesquisar.setVisible(visible);
    }

    /**
     * Método responsável por inserir uma atividade
     *
     * @param a atividade que será inserida
     * @throws Exception disparada durante o processo de inserção
     */
    public void inserirAtividade(Atividade a) throws Exception {
        atividadeDAO.inserir(a);
    }

    /**
     * Método responsável por editar uma atividade
     *
     * @param a atividade que será editada
     * @throws Exception disparada durante o processo de atualização
     */
    public void editarAtividade(Atividade a) throws Exception {
        atividadeDAO.editar(a);
    }

    /**
     * Método responsável por pesquisar atividades
     *
     * @param nome nome da atividade, pode ser "" caso esse parâmetro não seja
     * adequado na pesquisa em questão
     * @param status status da atividade
     * @return retorna um ArrayList com as atividades encontradas
     * @throws Exception disparada durante o processo de pesquisa
     */
    public ArrayList pesquisarAtividade(String nome, String status) throws Exception {
        return atividadeDAO.pesquisar(nome, status);
    }

    /**
     * Método responsável por validar os atributos de uma atividade
     *
     * @param a atividade que será validada
     * @param aAntesAlterar atividade antes de sofrer as alterações, pode ser
     * null caso a atividade seja nova
     * @throws Exception disparada caso algum atributo esteja inválido
     */
    public void validarAtributos(Atividade a, Atividade aAntesAlterar) throws Exception {
        if (a.getNome().equals("")) {
            frmAtividadeIncAlt.getTxtNome().requestFocus();
            throw new Exception("Nome inválido");
        }

        if (aAntesAlterar != null && atividadeDAO.existeNomeAtividade(a.getNome()) && !a.getNome().equals(aAntesAlterar.getNome())) {
            frmAtividadeIncAlt.getTxtNome().requestFocus();
            throw new Exception("Nome atividade já existente");
        }

        if (a.getDescricao().equals("")) {
            frmAtividadeIncAlt.getTxtDescricao().requestFocus();
            throw new Exception("Descrição Inválida");
        }

        if (a.getStatus().equals("")) {
            frmAtividadeIncAlt.getTxtDescricao().requestFocus();
            frmAtividadeIncAlt.getCmbStatus().requestFocus();
            throw new Exception("Status Inválido");
        }
    }
}
