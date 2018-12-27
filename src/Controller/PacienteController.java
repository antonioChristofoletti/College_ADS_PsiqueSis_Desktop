/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.PacienteDAO;
import Model.Paciente;
import Model.Responsavel;
import View.FrmPacienteIncAlt;
import View.FrmPacienteLista;
import java.util.ArrayList;

/**
 * Classe do tipo controller relacionada aos pacientes da aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public class PacienteController extends PessoaController{

    /**
     * Tela de paciente lista
     */
    private FrmPacienteLista frmPacienteLista;
    /**
     * Tela de paciente Inc/Alt
     */
    private FrmPacienteIncAlt frmPacienteIncAlt;
    /**
     * Objeto da própria classe PacienteController por conta do padrão de
     * projeto Sington
     */
    private static PacienteController pacienteController;
    /**
     * Objeto DAO referente ao paciente
     */
    private PacienteDAO pacienteDAO;

    /**
     * Construtor da classe
     */
    private PacienteController() {
        pacienteDAO = new PacienteDAO();
    }

    /**
     * Método responsável por invocar a tela de FrmPacienteIncAlt
     *
     * @param p paciente de edição, pode ser um parâmetro null caso a ideia seja
     * inserir um novo registro
     * @param visible visibilidade da tela, pode ser true ou false
     */
    public void frmPacienteIncAltSetVisible(Paciente p, boolean visible) {
        frmPacienteIncAlt = FrmPacienteIncAlt.getInstance(p);
        this.frmPacienteIncAlt.setVisible(visible);
    }

    /**
     * Método responsável por invocar a tela de FrmPacienteLista
     *
     * @param visible visibilidade da tela, pode ser true ou false
     */
    public void FrmPacienteListaSetVisible(boolean visible) {
        frmPacienteLista = FrmPacienteLista.getInstance();
        this.frmPacienteLista.setVisible(visible);
    }

    /**
     * Método responsável por retornar uma instância da própria classe
     *
     * @return retorna uma instância da própria classe
     */
    public static PacienteController getInstance() {
        if (pacienteController == null) {
            pacienteController = new PacienteController();
        }

        return pacienteController;
    }

    /** 
     * Método responsável por validar os atributos do paciente
     *
     * @param p paciente que será validado
     * @param pAntesEdicao paciente antes de sofrer as alterações, caso seja um
     * paciente novo basta passar null
     * @throws Exception disparada caso algum atributo esteja inválido
     */
    public void validarAtributos(Paciente p, Paciente pAntesEdicao) throws Exception {
        if (p.getNome().equals("")) {
            frmPacienteIncAlt.getTxtNomePaciente().requestFocus();
            throw new Exception("Preencha o campo de nome");
        }

        if (p.getRg().length() != 9 && p.getRg().length() != 10) {
            frmPacienteIncAlt.getTxtrg().requestFocus();
            throw new Exception("RG inválido");
        }

        if (pacienteDAO.existeRG(p.getRg()) && (((pAntesEdicao == null)) || (pAntesEdicao != null && !pAntesEdicao.getRg().equals(p.getRg())))) {
            frmPacienteIncAlt.getTxtrg().requestFocus();
            throw new Exception("O RG já existe no sistema");
        }

        if (!this.isCPF(p.getCpf())) {
            frmPacienteIncAlt.getTxtcpf().requestFocus();
            throw new Exception("O CPF é inválido");
        }

        if (pacienteDAO.existeCPF(p.getCpf()) && (((pAntesEdicao == null)) || (pAntesEdicao != null && !pAntesEdicao.getCpf().equals(p.getCpf())))) {
            frmPacienteIncAlt.getTxtrg().requestFocus();
            throw new Exception("O CPF já existe no sistema");
        }

        if (p.getTelefone1().length() != 10 && p.getTelefone1().length() != 11) {
            frmPacienteIncAlt.getTxttelefone1().requestFocus();
            throw new Exception("O telefone 1 é inválido");
        }

        if (pacienteDAO.existeTelefone(p.getTelefone1()) && (((pAntesEdicao == null)) || (pAntesEdicao != null && !pAntesEdicao.getTelefone1().equals(p.getTelefone1())))) {
            frmPacienteIncAlt.getTxttelefone1().requestFocus();
            throw new Exception("O telefone 1 já existe no sistema");
        }

        if (p.getTelefone2().length() != 10 && p.getTelefone2().length() != 11
                && !p.getTelefone2().equals("")) {
            frmPacienteIncAlt.getTxttelefone2().requestFocus();
            throw new Exception("O telefone 2 é inválido");
        }

        if (!p.getTelefone2().equals("")
                && (pacienteDAO.existeTelefone(p.getTelefone2()) && (((pAntesEdicao == null)) || (pAntesEdicao != null && !pAntesEdicao.getTelefone2().equals(p.getTelefone2()))))) {
            frmPacienteIncAlt.getTxttelefone2().requestFocus();
            throw new Exception("O telefone 2 já existe no sistema");
        }

        if (p.getCidade().equals("")) {
            frmPacienteIncAlt.getTxtCidade().requestFocus();
            throw new Exception("Preencha o campo de cidade");
        }

        if (p.getBairro().equals("")) {
            frmPacienteIncAlt.getTxtBairro().requestFocus();
            throw new Exception("Preencha o campo de bairro");
        }

        if (p.getLogradouro().equals("")) {
            frmPacienteIncAlt.getTxtLogradouro().requestFocus();
            throw new Exception("Preencha o campo de logradouro");
        }

        if (p.getNomeLogin().equals("")) {
            frmPacienteIncAlt.getTxtNomeLogin().requestFocus();
            throw new Exception("Preencha o campo de nome login");
        }

        if (pacienteDAO.existeUsuarioLogin(p.getNomeLogin()) && (((pAntesEdicao == null)) || (pAntesEdicao != null && !pAntesEdicao.getNomeLogin().equals(p.getNomeLogin())))) {
            frmPacienteIncAlt.getTxtNomeLogin().requestFocus();
            throw new Exception("O nome login já existe no sistema");
        }

        if (p.getSenha().equals("") || (p.getSenha().length() % 2) != 0) {
            frmPacienteIncAlt.getTxtSenha1().requestFocus();
            throw new Exception("Ambos os campos de senha devem ser preenchidos igualmente");
        }

        String s1 = p.getSenha().substring(0, p.getSenha().length() / 2);
        String s2 = p.getSenha().substring((p.getSenha().length() / 2), p.getSenha().length());

        if (!s1.equals(s2)) {
            frmPacienteIncAlt.getTxtSenha1().requestFocus();
            throw new Exception("Ambos os campos de senha devem ser preenchidos igualmente");
        }

        if (p.getNumeroPasta().equals("")) {
            frmPacienteIncAlt.getTxtNumeroPasta().requestFocus();
            throw new Exception("Preencha o campo de número de pasta");
        }

        if (p.getCapaz().equals("")) {
            frmPacienteIncAlt.getCmbResponsavel().requestFocus();
            throw new Exception("O campo de responsabilidade é inválido");
        }

        if (p.getDataInicio() == null) {
            frmPacienteIncAlt.getTxtDataInicio().requestFocus();
            frmPacienteIncAlt.getTxtDataInicio().requestFocusInWindow();
            throw new Exception("O campo de data de início é inválida");
        }

        if (p.getPlano() == null) {
            frmPacienteIncAlt.getBtnPesquisarPlano().requestFocus();
            throw new Exception("Plano de saúde inválido");
        }
    }

    /**
     * Método responsável por inserir um paciente
     *
     * @param p paciente que será inserido
     * @throws Exception disparada durante o processo de inserção
     */
    public void inserirPaciente(Paciente p) throws Exception {
        pacienteDAO.inserirPaciente(p);
    }

    /**
     * Método responsável por editar um paciente
     *
     * @param p paciente que será editado
     * @param senhaAntiga senha antes de realizar a atualização, importante por conta da criptografia MD5
     * @throws Exception disparada durante o processo de atualização
     */
    public void editarPaciente(Paciente p, String senhaAntiga) throws Exception {
        pacienteDAO.editarPaciente(p, senhaAntiga);
    }

    /**
     * Método responsável por pesquisar pacientes
     *
     * @param nome nome do paciente, pode ser "" caso esse parâmetro não seja
     * adequado na pesquisa em questão
     * @param rg rg do paciente, pode ser "" caso esse parâmetro não seja
     * adequado na pesquisa em questão
     * @param status status do paciente
     * @param r responsável que será utilizado como parâmetr, caso não seja adequado na pesquisa em questão pode-se utilizar null
     * @return retorna um ArrayList de pacientes pesquisados
     * @throws Exception disparada durante o processo de pesquisa
     */
    public ArrayList pesquisarPaciente(String nome, String rg, String status, Responsavel r) throws Exception {
        return pacienteDAO.pesquisar(nome, rg, status, r);
    }
}
