package Controller;

import DAO.LocalizacaoDAO;
import Interfaces_Inversao_Controle.IPesquisarLocalizacao;
import Model.Localizacao;
import View.FrmLocalizacaoIncAlt;
import View.FrmLocalizacaoLista;
import View.FrmLocalizacaoPesquisar;
import java.util.ArrayList;

/**
 * Classe do tipo controller relacionada as localizações da aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public class LocalizacaoController {

    /**
     * Tela de localização lista
     */
    private FrmLocalizacaoLista frmLocalizacaoLista;
    /**
     * Tela de localização Inc/Alt
     */
    private FrmLocalizacaoIncAlt frmLocalizacaoIncAlt;
    /**
     * Tela de localização pesquisa
     */
    private FrmLocalizacaoPesquisar frmLocalizacaoPesquisar;
    /**
     * Objeto DAO referente a localização
     */
    private LocalizacaoDAO localizacaoDAO;
    /**
     * Objeto da própria classe LocalizacaoController por conta do padrão de
     * projeto Sington
     */
    private static LocalizacaoController localizacaoController;

    /**
     * Construtor da classe
     */
    private LocalizacaoController() {
        localizacaoDAO = new LocalizacaoDAO();
    }

    /**
     * Retorna uma instância da própria classe
     *
     * @return retorna uma instância da própria classe
     */
    public static LocalizacaoController getInstance() {
        if (localizacaoController == null) {
            localizacaoController = new LocalizacaoController();
        }

        return localizacaoController;
    }

    /**
     * Método responsável por invocar a tela de FrmLocalizacaoLista
     *
     * @param visible visibilidade da tela, pode ser true ou false
     */
    public void frmLocalizacaoListaSetVisible(boolean visible) {
        frmLocalizacaoLista = FrmLocalizacaoLista.getInstance();
        this.frmLocalizacaoLista.setVisible(visible);
    }

    /**
     * Método responsável por invocar a tela de FrmLocalizacaoIncAlt
     *
     * @param l localizacao de edição, pode ser um parâmetro null caso a ideia
     * seja inserir um novo registro
     * @param visible visibilidade da tela, pode ser true ou false
     */
    public void frmLocalizacaoIncAltSetVisible(Localizacao l, boolean visible) {
        frmLocalizacaoIncAlt = FrmLocalizacaoIncAlt.getInstance(l);
        this.frmLocalizacaoIncAlt.setVisible(visible);
    }

    /**
     *
     * @param iPesquisarLocalizacao objeto que implementa a interface
     * 'IPesquisarLocalizacao' que está chamando esse método
     * @param visible visibilidade da tela, pode ser true ou false
     */
    public void frmLocalizacaoPesquisarSetVisible(IPesquisarLocalizacao iPesquisarLocalizacao, boolean visible) {
        frmLocalizacaoPesquisar = FrmLocalizacaoPesquisar.getInstance(iPesquisarLocalizacao);
        this.frmLocalizacaoPesquisar.setVisible(visible);
    }

    /**
     * Método responsável por inserir uma localização
     *
     * @param l localização que será inserida
     * @throws Exception disparada durante o processo de inserção
     */
    public void inserirLocalizacao(Localizacao l) throws Exception {
        localizacaoDAO.inserir(l);
    }

    /**
     * Método responsável por editar uma localização
     *
     * @param l localização que será editada
     * @throws Exception disparada durante o processo de atualização
     */
    public void editarLocalizacao(Localizacao l) throws Exception {
        localizacaoDAO.editar(l);
    }

    /**
     * Método responsável por pesquisar localizações
     *
     * @param nome nome da localização, pode ser "" caso esse parâmetro não seja
     * adequado na pesquisa em questão
     * @param status status da localização, pode ser "" caso esse parâmetro não
     * seja adequado na pesquisa em questão
     * @return retorna um ArrayList com as localizações encontradas
     * @throws Exception disparada durante o processo de pesquisa
     */
    public ArrayList pesquisarLocalizacao(String nome, String status) throws Exception {
        return localizacaoDAO.pesquisar(nome, status);
    }

    /**
     * Método responsável por validar os atributos de uma localização
     *
     * @param l localização que será validada
     * @param lAntesAlterar localização antes de sofrer as alterações, pode ser
     * null caso a localização seja nova
     * @throws Exception disparada caso algum atributo esteja inválido
     */
    public void validarAtributos(Localizacao l, Localizacao lAntesAlterar) throws Exception {
        if (l.getNome().equals("")) {
            frmLocalizacaoIncAlt.getTxtNome().requestFocus();
            throw new Exception("Nome inválido");
        }

        if (l.getStatus().equals("")) {
            frmLocalizacaoIncAlt.getTxtDescricao().requestFocus();
            frmLocalizacaoIncAlt.getCmbStatus().requestFocus();
            throw new Exception("Status Inválido");
        }

        if (localizacaoDAO.existeNomeLocalizacao(l.getNome()) && (((lAntesAlterar == null)) || (lAntesAlterar != null && !lAntesAlterar.getNome().equals(l.getNome())))) {
            frmLocalizacaoIncAlt.getTxtNome().requestFocus();
            throw new Exception("O nome da localização já existe no sistema");
        }

        if (l.getTelefone1().length() != 10 && l.getTelefone1().length() != 11) {
            frmLocalizacaoIncAlt.getTxttelefone1().requestFocus();
            throw new Exception("O telefone 1 é inválido");
        }

        if (localizacaoDAO.existeTelefone(l.getTelefone1()) && (((lAntesAlterar == null)) || (lAntesAlterar != null && !lAntesAlterar.getTelefone1().equals(l.getTelefone1())))) {
            frmLocalizacaoIncAlt.getTxttelefone1().requestFocus();
            throw new Exception("O telefone 1 já existe no sistema");
        }

        if (l.getTelefone2().length() != 10 && l.getTelefone2().length() != 11
                && !l.getTelefone2().equals("")) {
            frmLocalizacaoIncAlt.getTxttelefone2().requestFocus();
            throw new Exception("O telefone 2 é inválido");
        }

        if (!l.getTelefone2().equals("")
                && localizacaoDAO.existeTelefone(l.getTelefone2()) && (((lAntesAlterar == null)) || (lAntesAlterar != null && !lAntesAlterar.getTelefone2().equals(l.getTelefone2())))) {
            frmLocalizacaoIncAlt.getTxttelefone2().requestFocus();
            throw new Exception("O telefone 2 já existe no sistema");
        }

        if (l.getTelefone1().equals(l.getTelefone2())) {
            frmLocalizacaoIncAlt.getTxttelefone1().requestFocus();
            throw new Exception("O telefone 1 não pode ser igual ao telefone 2");
        }

        if (l.getCidade().equals("")) {
            frmLocalizacaoIncAlt.getTxtCidade().requestFocus();
            throw new Exception("Preencha o campo de cidade");
        }

        if (l.getBairro().equals("")) {
            frmLocalizacaoIncAlt.getTxtBairro().requestFocus();
            throw new Exception("Preencha o campo de bairro");
        }

        if (l.getLogradouro().equals("")) {
            frmLocalizacaoIncAlt.getTxtLogradouro().requestFocus();
            throw new Exception("Preencha o campo de logradouro");
        }

        if (l.getDescricao().equals("")) {
            frmLocalizacaoIncAlt.getTxtDescricao().requestFocus();
            throw new Exception("Descrição Inválida");
        }
    }
}
