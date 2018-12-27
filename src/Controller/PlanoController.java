package Controller;

import DAO.PlanoDAO;
import Interfaces_Inversao_Controle.IPesquisarPlano;
import Model.Parcela;
import Model.Plano;
import View.FrmPlanoIncAlt;
import View.FrmPlanoLista;
import View.FrmPlanoPesquisar;
import java.util.ArrayList;
import java.util.Date;

/**
 * Classe do tipo controller relacionada aos planos da aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public class PlanoController {

    /**
     * Tela de plano lista
     */
    private FrmPlanoLista frmPlanoLista;
    /**
     * Tela de plano Inc/Alt
     */
    private FrmPlanoIncAlt frmPlanoIncAlt;
    /**
     * Tela de plano pesquisar
     */
    private FrmPlanoPesquisar frmPlanoPesquisar;
    /**
     * Objeto da classe DAO referente a classe Plano
     */
    private PlanoDAO planoDAO;

    /**
     * Instância da própria classe em questão, por conta do padrão de projeto
     * Sington
     */
    private static PlanoController planoController;

    /**
     * Construtor da classe, na qual o agendamentoDAO será instânciado
     */
    private PlanoController() {
        planoDAO = new PlanoDAO();
    }

    /**
     * Método responsável retornar uma instância da própria classe
     *
     * @return Retorna uma instância da própria classe
     */
    public static PlanoController getInstance() {
        if (planoController == null) {
            planoController = new PlanoController();
        }

        return planoController;
    }

    /**
     * Método responsável por invocar a tela de FrmPlanoLista
     *
     * @param visible visibilidade da tela, pode ser true ou false
     */
    public void FrmPlanoListaSetVisible(boolean visible) {
        frmPlanoLista = FrmPlanoLista.getInstance();
        this.frmPlanoLista.setVisible(visible);
    }

    /**
     * Método responsável por invocar a tela de FrmPlanoIncAlt
     *
     * @param p plano que será editado, pode-se passar null caso a intenção seja
     * cadastrar um plano
     * @param visible visibilidade da tela, pode ser true ou false
     */
    public void frmPlanoIncAltSetVisible(Plano p, boolean visible) {
        frmPlanoIncAlt = FrmPlanoIncAlt.getInstance(p);
        this.frmPlanoIncAlt.setVisible(visible);
    }

    /**
     * Método responsável por invocar a tela FrmPlanoPesquisar
     *
     * @param iPesquisarPlano classe que implementa a interface
     * 'IPesquisarPlano'
     * @param visible visibilidade da tela, pode ser true ou false
     */
    public void FrmPlanoPesquisar(IPesquisarPlano iPesquisarPlano, boolean visible) {
        frmPlanoPesquisar = FrmPlanoPesquisar.getInstance(iPesquisarPlano);
        this.frmPlanoPesquisar.setVisible(visible);
    }

    /**
     * Método responsável por inserir um plano
     *
     * @param p plano que será inserido
     * @throws Exception disparada durante o processo de inserção
     */
    public void inserirPlano(Plano p) throws Exception {
        planoDAO.inserir(p);
    }

    /**
     * Método responsável por editar um plano
     *
     * @param p plano que será editado
     * @throws Exception disparada durante o processo de edição
     */
    public void editarPlano(Plano p) throws Exception {
        planoDAO.editar(p);
    }

    /**
     * Método responsável por realizar a pesquisa de plano
     *
     * @param nome nome do plano, pode ser "" esse parâmetro não seja adequado
     * na pesquisa em questão
     * @param status status do plano
     * @return retorna um ArrayList com os planos encontrados
     * @throws Exception disparada durante o processo de pesquisa
     */
    public ArrayList pesquisarPlano(String nome, String status) throws Exception {
        return planoDAO.pesquisar(nome, status);
    }

    /**
     * Validar os atributos do plano
     *
     * @param p plano que será validado
     * @param pAntesAlterar plano antes de sofrer as alterações, caso seja um
     * plano novo basta passar null
     * @throws Exception disparada caso algum atributo esteja inválido
     */
    public void validarAtributos(Plano p, Plano pAntesAlterar) throws Exception {
        if (p.getStatus().equals("")) {
            frmPlanoIncAlt.getTxtDescricao().requestFocus();
            frmPlanoIncAlt.getCmbStatus().requestFocus();
            throw new Exception("Status Inválido");
        }

        if (p.getNomeInstituicao().equals("")) {
            frmPlanoIncAlt.getTxtNomeInstituicao().requestFocus();
            throw new Exception("Nome instituição inválido");
        }

        if (p.getQuantiConsultas() <= 0) {
            frmPlanoIncAlt.getTxtQuantiConsultas().requestFocus();
            frmPlanoIncAlt.getTxtQuantiConsultas().requestFocusInWindow();
            throw new Exception("A quantidade deve ser superior a 0");
        }

        if (pAntesAlterar != null && planoDAO.existeNomeInstituicao(p.getNomeInstituicao()) && !p.getNomeInstituicao().equals(pAntesAlterar.getNomeInstituicao())) {
            frmPlanoIncAlt.getTxtNomeInstituicao().requestFocus();
            throw new Exception("Nome instituição já existente");
        }

        if (p.getDescricao().equals("")) {
            frmPlanoIncAlt.getTxtDescricao().requestFocus();
            throw new Exception("Descrição Inválida");
        }
    }

    public ArrayList gerarParcelas(Date dataInicial, int quantiParcelas, double valorParcela, int saltoDias, String mensagemBase) {
        ArrayList<Parcela> parcelas = new ArrayList();
        
        for (int i = 0; i < 10; i++) {
            Parcela p = new Parcela();
            
        }

        return parcelas;
    }
}
