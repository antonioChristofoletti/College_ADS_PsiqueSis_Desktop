/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.ResponsavelDAO;
import Interfaces_Inversao_Controle.IPesquisarResponsavel;
import Model.Responsavel;
import View.FrmResponsavelIncAlt;
import View.FrmResponsavelLista;
import View.FrmResponsavelPesquisar;
import java.util.ArrayList;

/**
 * Classe do tipo controller relacionada aos responsáveis da aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public class ResponsavelController extends PessoaController {

    /**
     * Tela de responsável lista
     */
    private FrmResponsavelLista frmResponsavelLista;
    /**
     * Tela de responsável Inc/Alt
     */
    private FrmResponsavelIncAlt frmResponsavelIncAlt;
    /**
     * Tela de responsável pesquisar
     */
    private FrmResponsavelPesquisar frmResponsavelPesquisar;
    /**
     * objeto do tipo 'ResponsavelController' por conta do padrão de projeto
     * singleton
     */
    private static ResponsavelController responsavelController;
    /**
     * Objeto DAO referente ao responsável
     */
    private ResponsavelDAO responsavelDAO;

    /**
     * Construtor da classe
     */
    private ResponsavelController() {
        responsavelDAO = new ResponsavelDAO();
    }

    /**
     * Método responsável por invocar a tela de FrmResponsavelIncAlt
     *
     * @param r responsável de edição, pode ser um parâmetro null caso a ideia
     * seja inserir um novo registro
     * @param visible visibilidade da tela, pode ser true ou false
     */
    public void frmResponsavelIncAltSetVisible(Responsavel r, boolean visible) {
        frmResponsavelIncAlt = frmResponsavelIncAlt.getInstance(r);
        this.frmResponsavelIncAlt.setVisible(visible);
    }

    /**
     * Método responsável por invocar a tela de FrmResponsavelLista
     *
     * @param visible visibilidade da tela, pode ser true ou false
     */
    public void frmResponsavelListaSetVisible(boolean visible) {
        frmResponsavelLista = FrmResponsavelLista.getInstance();
        this.frmResponsavelLista.setVisible(visible);
    }

    /**
     * Método responsável por invocar a tela de FrmResponsavelPesquisar
     *
     * @param iPesquisarResponsavel classe que implementa a interface
     * 'iPesquisarResponsavel'
     * @param visible visibilidade da tela, pode ser true ou false
     */
    public void frmResponsavelPesquisarSetVisible(IPesquisarResponsavel iPesquisarResponsavel, boolean visible) {
        frmResponsavelPesquisar = FrmResponsavelPesquisar.getInstance(iPesquisarResponsavel);
        this.frmResponsavelPesquisar.setVisible(visible);
    }

    /**
     * Método responsável retornar uma instância da própria classe
     *
     * @return Retorna uma instância da própria classe
     */
    public static ResponsavelController getInstance() {
        if (responsavelController == null) {
            responsavelController = new ResponsavelController();
        }
        return responsavelController;
    }

    /**
     * Método responsável por validar os atributos normais de um responsável
     *
     * @param r responsável que será validado
     * @throws Exception disparada caso algum atributo esteja inválido
     */
    private void validarAtributosNormais(Responsavel r) throws Exception {
        if (r.getStatus().equals("")) {
            frmResponsavelIncAlt.getTxtNome().requestFocus();
            throw new Exception("Selecione um status válido");
        }

        if (r.getNome().equals("")) {
            frmResponsavelIncAlt.getTxtNome().requestFocus();
            throw new Exception("Preencha o campo de nome");
        }

        if (r.getRg().length() != 9 && r.getRg().length() != 10) {
            frmResponsavelIncAlt.getTxtrg().requestFocus();
            throw new Exception("RG inválido");
        }

        if (!this.isCPF(r.getCpf())) {
            frmResponsavelIncAlt.getTxtcpf().requestFocus();
            throw new Exception("O CPF é inválido");
        }

        if (r.getTelefone1().length() != 10 && r.getTelefone1().length() != 11) {
            frmResponsavelIncAlt.getTxttelefone1().requestFocus();
            throw new Exception("O telefone 1 é inválido");
        }

        if (r.getTelefone2().length() != 10 && r.getTelefone2().length() != 11
                && !r.getTelefone2().equals("")) {
            frmResponsavelIncAlt.getTxttelefone2().requestFocus();
            throw new Exception("O telefone 2 é inválido");
        }

        if (r.getTelefone1().equals(r.getTelefone2())) {
            frmResponsavelIncAlt.getTxttelefone1().requestFocus();
            throw new Exception("O telefone 1 não pode ser igual ao telefone 2");
        }

        if (r.getCidade().equals("")) {
            frmResponsavelIncAlt.getTxtCidade().requestFocus();
            throw new Exception("Preencha o campo de cidade");
        }

        if (r.getBairro().equals("")) {
            frmResponsavelIncAlt.getTxtBairro().requestFocus();
            throw new Exception("Preencha o campo de bairro");
        }

        if (r.getLogradouro().equals("")) {
            frmResponsavelIncAlt.getTxtLogradouro().requestFocus();
            throw new Exception("Preencha o campo de logradouro");
        }

        if (r.getNomeLogin().equals("")) {
            frmResponsavelIncAlt.getTxtNomeLogin().requestFocus();
            throw new Exception("Preencha o campo de nome login");
        }

        if (r.getSenha().equals("") || (r.getSenha().length() % 2) != 0) {
            frmResponsavelIncAlt.getTxtSenha1().requestFocus();
            throw new Exception("Ambos os campos de senha devem ser preenchidos igualmente");
        }

        String s1 = r.getSenha().substring(0, r.getSenha().length() / 2);
        String s2 = r.getSenha().substring((r.getSenha().length() / 2), r.getSenha().length());

        if (!s1.equals(s2)) {
            frmResponsavelIncAlt.getTxtSenha1().requestFocus();
            throw new Exception("Ambos os campos de senha devem ser preenchidos igualmente");
        }
    }

    /**
     * Validar os atributos do responsável
     *
     * @param r responsável que será validado
     * @param rAntesEdicao responsável antes de sofrer as alterações, caso seja
     * um plano novo basta passar null
     * @throws Exception disparada caso algum atributo esteja inválido
     */
    public void validarAtributos(Responsavel r, Responsavel rAntesEdicao) throws Exception {
        validarAtributosNormais(r);

        if (responsavelDAO.existeRG(r.getRg()) && (((rAntesEdicao == null)) || (rAntesEdicao != null && !rAntesEdicao.getRg().equals(r.getRg())))) {
            frmResponsavelIncAlt.getTxtrg().requestFocus();
            throw new Exception("O RG já existe no sistema");
        }

        if (responsavelDAO.existeCPF(r.getCpf()) && (((rAntesEdicao == null)) || (rAntesEdicao != null && !rAntesEdicao.getCpf().equals(r.getCpf())))) {
            frmResponsavelIncAlt.getTxtrg().requestFocus();
            throw new Exception("O CPF já existe no sistema");
        }

        if (responsavelDAO.existeTelefone(r.getTelefone1()) && (((rAntesEdicao == null)) || (rAntesEdicao != null && !rAntesEdicao.getTelefone1().equals(r.getTelefone1())))) {
            frmResponsavelIncAlt.getTxttelefone1().requestFocus();
            throw new Exception("O telefone 1 já existe no sistema");
        }

        if (!r.getTelefone2().equals("")
                && responsavelDAO.existeTelefone(r.getTelefone2()) && (((rAntesEdicao == null)) || (rAntesEdicao != null && !rAntesEdicao.getTelefone2().equals(r.getTelefone2())))) {
            frmResponsavelIncAlt.getTxttelefone2().requestFocus();
            throw new Exception("O telefone 2 já existe no sistema");
        }

        if (responsavelDAO.existeUsuarioLogin(r.getNomeLogin()) && (((rAntesEdicao == null)) || (rAntesEdicao != null && !rAntesEdicao.getNomeLogin().equals(r.getNomeLogin())))) {
            frmResponsavelIncAlt.getTxtNomeLogin().requestFocus();
            throw new Exception("O nome login já existe no sistema");
        }
    }

    /**
     * Método responsável por inserir um responsável
     *
     * @param r responsável que será inserido
     * @throws Exception disparada durante o processo de inserção
     */
    public void inserirResponsavel(Responsavel r) throws Exception {
        responsavelDAO.inserirResponsavel(r);
    }

    /**
     * Método responsável por editar um responsável
     *
     * @param r responsável que será editado
     * @param senhaAntiga senha antes de realizar a atualização, importante
     * por conta da criptografia MD5
     * @throws Exception disparada durante o processo de edição
     */
    public void editarResponsavel(Responsavel r, String senhaAntiga) throws Exception {
        responsavelDAO.editartResponsavel(r, senhaAntiga);
    }

    /**
     * Método responsável por realizar a pesquisa de responsáveis
     *
     * @param nome nome do responsável, pode ser "" caso esse parâmetro não seja
     * adequado na pesquisa em questão
     * @param status status do responsável
     * @param rg rg do responsável, pode ser "" caso esse parâmetro não seja
     * adequado na pesquisa em questão
     * @return retorna um ArrayList com os responsáveis encontrados
     * @throws Exception disparada durante o processo de pesquisa
     */
    public ArrayList pesquisarResponsavel(String nome, String rg, String status) throws Exception {
        return responsavelDAO.pesquisar(nome, rg, status);
    }
}
