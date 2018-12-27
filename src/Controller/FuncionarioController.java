/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.FuncionarioDAO;
import Model.Funcionario;
import Model.Historico;
import View.FrmFuncionarioIncAlt;
import View.FrmFuncionarioLista;
import View.FrmHistoricoFuncionario;
import View.FrmPessoaPesquisar;
import java.util.ArrayList;
import java.util.Date;
import Model.Permissao;
import Model.Pessoa;
import View.FrmFuncionarioLogin;
import View.FrmTelaPrincipal;
import Interfaces_Inversao_Controle.IPesquisarPessoa;

/**
 * Classe do tipo controller relacionada aos funcionários da aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public class FuncionarioController extends PessoaController {

    /**
     * Armazena o funcionário logado no sistema
     */
    private static Funcionario funcionarioLogado;

    /**
     * Tela funcionário lista
     */
    private FrmFuncionarioLista frmFuncionarioLista;
    /**
     * Tela funcionário Inc/Alt
     */
    private FrmFuncionarioIncAlt frmFuncionarioIncAlt;
    /**
     * Tela funcionário de pesquisa de histórico
     */
    private FrmHistoricoFuncionario frmHistoricoLista;
    /**
     * Tela pesquisa de funcionário
     */
    private FrmPessoaPesquisar frmPessoaPesquisar;
    /**
     * Tela de login de funcionário
     */
    private FrmFuncionarioLogin frmFuncionarioLogin;
    /**
     * Tela principal do sistema
     */
    private FrmTelaPrincipal frmTelaPrincipal;

    /**
     * Objeto do próprio tipo da classe, relacionado ao padrão de projeto
     * sington
     */
    private static FuncionarioController funcionarioController;
    /**
     * Objeto do tipo DAO relacionado aos funcionários do sistema
     */
    private FuncionarioDAO funcionarioDAO;

    /**
     * Objeto do tipo PacienteController
     */
    private PacienteController pacienteController;
    /**
     * Objeto do tipo ResponsavelController
     */
    private ResponsavelController responsavelController;
    /**
     * Objeto do tipo LocalizacaoController
     */
    private LocalizacaoController localizacaoController;
    /**
     * Objeto do tipo AtividadeController
     */
    private AtividadeController atividadeController;
    /**
     * Objeto do tipo HistoricoController
     */
    private HistoricoController historicoController;
    /**
     * Objeto do tipo PlanoController
     */
    private PlanoController planoController;
    /**
     * Objeto do tipo AgendamentoController
     */
    private AgendamentoController agendamentoController;
    /**
     * Objeto do tipo OrcamentoController
     */
    private OrcamentoController orcamentoController;
    /**
     * Objeto do tipo ParcelaController
     */
    private ParcelaController parcelaController;
    /**
     * Objeto do tipo PermissaoController
     */
    private PermissaoController permissaoController;
    /**
     * Objeto do tipo MaquinaController
     */
    private MaquinaController maquinaController;

    /**
     * Construtor da classe
     */
    private FuncionarioController() {
        funcionarioDAO = new FuncionarioDAO();
        historicoController = HistoricoController.getInstance();
        pacienteController = PacienteController.getInstance();
        responsavelController = ResponsavelController.getInstance();
        localizacaoController = LocalizacaoController.getInstance();
        atividadeController = AtividadeController.getInstance();
        planoController = PlanoController.getInstance();
        agendamentoController = AgendamentoController.getInstance();
        orcamentoController = OrcamentoController.getInstance();
        parcelaController = ParcelaController.getInstance();
        permissaoController = PermissaoController.getInstance();
        maquinaController = MaquinaController.getInstance();
    }

    /**
     * Método responsável por invocar a tela de FrmFuncionarioIncAlt
     *
     * @param f funcionário de edição, caso a tela seja invocada para inserção o
     * parâmetro pode ser null
     * @param visible visibilidade da tela, pode ser true ou false
     */
    public void frmFuncionarioIncAltSetVisible(Funcionario f, boolean visible) {
        frmFuncionarioIncAlt = FrmFuncionarioIncAlt.getInstance(f);
        this.frmFuncionarioIncAlt.setVisible(visible);
    }

    /**
     * Método responsável por invocar a tela de FrmFuncionarioLista
     *
     * @param visible visibilidade da tela, pode ser true ou false
     */
    public void frmFuncionarioListaSetVisible(boolean visible) {
        frmFuncionarioLista = FrmFuncionarioLista.getInstance();
        this.frmFuncionarioLista.setVisible(visible);
    }

    /**
     * Método responsável por invocar a tela de FrmHistoricoLista
     *
     * @param visible visibilidade da tela, pode ser true ou false
     */
    public void frmHistoricoListaSetVisible(boolean visible) {
        frmHistoricoLista = FrmHistoricoFuncionario.getInstance();
        this.frmHistoricoLista.setVisible(visible);
    }

    /**
     * Método responsável por invocar a tela de FrmPessoaPesquisar
     *
     * @param iPesquisarUsuario objeto que implementa a interface
     * 'IPesquisarFuncionario' que está chamando esse método
     * @param visible visibilidade da tela, pode ser true ou false
     * @param funcionario  filtro para pesquisa de funcionário
     * @param paciente filtro para pesquisa de paciente
     * @param responsavel filtro para pesquisa de responsável
     */
    public void FrmPesquisarPessoaSetVisible(IPesquisarPessoa iPesquisarUsuario, boolean visible, Boolean funcionario, Boolean paciente, Boolean responsavel) {
        frmPessoaPesquisar = FrmPessoaPesquisar.getInstance(iPesquisarUsuario, funcionario, paciente, responsavel);
        this.frmPessoaPesquisar.setVisible(visible);
    }

    /**
     * Método responsável por invocar a tela de FrmFuncionarioLogin
     *
     * @param visible visibilidade da tela, pode ser true ou false
     */
    public void frmFuncionarioLoginSetVisible(boolean visible) {
        frmFuncionarioLogin = FrmFuncionarioLogin.getInstance();
        this.frmFuncionarioLogin.setVisible(visible);
    }

    /**
     * Método responsável por invocar a tela de FrmTelaPrincipal
     *
     * @param visible visibilidade da tela, pode ser true ou false
     */
    public void frmTelaPrincipalSetVisible(boolean visible) {
        frmTelaPrincipal = FrmTelaPrincipal.getInstance();
        this.frmTelaPrincipal.setVisible(visible);
    }

    /**
     * Método responsável por retornar uma instância da própria classe
     *
     * @return retorna um objeto do tipo FuncionarioController
     */
    public static FuncionarioController getInstance() {
        if (funcionarioController == null) {
            funcionarioController = new FuncionarioController();
        }
        return funcionarioController;
    }

    /**
     * Método responsável por validar os atributos normais de um funcionário
     *
     * @param f funcionário a ser validado
     * @throws Exception disparada caso algum atributo não seja válido
     */
    private void validarAtributosNormais(Funcionario f) throws Exception {
        if (f.getStatus().equals("")) {
            frmFuncionarioIncAlt.getTxtNome().requestFocus();
            throw new Exception("Selecione um status válido");
        }

        if (f.getNome().equals("")) {
            frmFuncionarioIncAlt.getTxtNome().requestFocus();
            throw new Exception("Preencha o campo de nome");
        }

        if (f.getRg().length() != 9 && f.getRg().length() != 10) {
            frmFuncionarioIncAlt.getTxtrg().requestFocus();
            throw new Exception("RG inválido");
        }

        if (!this.isCPF(f.getCpf())) {
            frmFuncionarioIncAlt.getTxtcpf().requestFocus();
            throw new Exception("O CPF é inválido");
        }

        if (f.getTelefone1().length() != 10 && f.getTelefone1().length() != 11) {
            frmFuncionarioIncAlt.getTxttelefone1().requestFocus();
            throw new Exception("O telefone 1 é inválido");
        }

        if (f.getTelefone2().length() != 10 && f.getTelefone2().length() != 11
                && !f.getTelefone2().equals("")) {
            frmFuncionarioIncAlt.getTxttelefone2().requestFocus();
            throw new Exception("O telefone 2 é inválido");
        }

        if (f.getTelefone1().equals(f.getTelefone2())) {
            frmFuncionarioIncAlt.getTxttelefone1().requestFocus();
            throw new Exception("O telefone 1 não pode ser igual ao telefone 2");
        }

        if (f.getCidade().equals("")) {
            frmFuncionarioIncAlt.getTxtCidade().requestFocus();
            throw new Exception("Preencha o campo de cidade");
        }

        if (f.getBairro().equals("")) {
            frmFuncionarioIncAlt.getTxtBairro().requestFocus();
            throw new Exception("Preencha o campo de bairro");
        }

        if (f.getLogradouro().equals("")) {
            frmFuncionarioIncAlt.getTxtLogradouro().requestFocus();
            throw new Exception("Preencha o campo de logradouro");
        }

        if (f.getEmail().equals("")) {
            frmFuncionarioIncAlt.getTxtEmail().requestFocus();
            throw new Exception("Preencha o campo de e-mail");
        }

        if (f.getNomeLogin().equals("")) {
            frmFuncionarioIncAlt.getTxtNomeLogin().requestFocus();
            throw new Exception("Preencha o campo de nome login");
        }

        if (f.getSenha().equals("") || (f.getSenha().length() % 2) != 0) {
            frmFuncionarioIncAlt.getTxtSenha1().requestFocus();
            throw new Exception("Ambos os campos de senha devem ser preenchidos igualmente");
        }

        String s1 = f.getSenha().substring(0, f.getSenha().length() / 2);
        String s2 = f.getSenha().substring((f.getSenha().length() / 2), f.getSenha().length());

        if (!s1.equals(s2)) {
            frmFuncionarioIncAlt.getTxtSenha1().requestFocus();
            throw new Exception("Ambos os campos de senha devem ser preenchidos igualmente");
        }
    }

    /**
     * Método responsável por validar os atributos de um funcionário
     *
     * @param f funcionário a ser validado
     * @param fAntesEdicao funcionário a ser validado antes de ser editado, caso
     * seja um funcionário novo, pode-se colocar null
     * @throws Exception disparada caso algum atributo seja inválido
     */
    public void validarAtributos(Funcionario f, Funcionario fAntesEdicao) throws Exception {
        validarAtributosNormais(f);

        if (funcionarioDAO.existeRG(f.getRg()) && (((fAntesEdicao == null)) || (fAntesEdicao != null && !fAntesEdicao.getRg().equals(f.getRg())))) {
            frmFuncionarioIncAlt.getTxtrg().requestFocus();
            throw new Exception("O RG já existe no sistema");
        }

        if (funcionarioDAO.existeCPF(f.getCpf()) && (((fAntesEdicao == null)) || (fAntesEdicao != null && !fAntesEdicao.getCpf().equals(f.getCpf())))) {
            frmFuncionarioIncAlt.getTxtrg().requestFocus();
            throw new Exception("O CPF já existe no sistema");
        }

        if (funcionarioDAO.existeTelefone(f.getTelefone1()) && (((fAntesEdicao == null)) || (fAntesEdicao != null && !fAntesEdicao.getTelefone1().equals(f.getTelefone1())))) {
            frmFuncionarioIncAlt.getTxttelefone1().requestFocus();
            throw new Exception("O telefone 1 já existe no sistema");
        }

        if (!f.getTelefone2().equals("")
                && funcionarioDAO.existeTelefone(f.getTelefone2()) && (((fAntesEdicao == null)) || (fAntesEdicao != null && !fAntesEdicao.getTelefone2().equals(f.getTelefone2())))) {
            frmFuncionarioIncAlt.getTxttelefone2().requestFocus();
            throw new Exception("O telefone 2 já existe no sistema");
        }

        if (funcionarioDAO.existeEmail(f.getEmail()) && (((fAntesEdicao == null)) || (fAntesEdicao != null && !fAntesEdicao.getEmail().equals(f.getEmail())))) {
            frmFuncionarioIncAlt.getTxtEmail().requestFocus();
            throw new Exception("O nome e-mail já existe no sistema");
        }

        if (funcionarioDAO.existeUsuarioLogin(f.getNomeLogin()) && (((fAntesEdicao == null)) || (fAntesEdicao != null && !fAntesEdicao.getNomeLogin().equals(f.getNomeLogin())))) {
            frmFuncionarioIncAlt.getTxtNomeLogin().requestFocus();
            throw new Exception("O nome login já existe no sistema");
        }
    }

    /**
     * Método responsável por validar apenas o login e a senha do usuário
     *
     * @param f funcionário a ser validado
     * @throws Exception disparada caso algum atributo seja inválido
     */
    public void validarAtributos(Funcionario f) throws Exception {
        if (f.getNomeLogin().equals("")) {
            frmFuncionarioLogin.getTxtNomeLogin().requestFocus();
            throw new Exception("Preencha o campo de nome");
        }

        if (f.getSenha().equals("")) {
            frmFuncionarioLogin.getTxtSenha().requestFocus();
            throw new Exception("Preencha o campo de senha");
        }
    }

    /**
     * Método responsável por inserir um funcionário
     *
     * @param f funcionário que será inserido
     * @throws Exception disparada caso ocorra algum erro
     */
    public void inserirFuncionario(Funcionario f) throws Exception {
        funcionarioDAO.inserirFuncionario(f);
    }

    /**
     * Método responsável por editar um funcionário
     *
     * @param f funcionário que será editado
     * @param senhaAntiga senha antes de realizar a atualização, importante por
     * conta da criptografia MD5
     * @throws Exception disparada caso ocorra algum erro
     */
    public void editarFuncionario(Funcionario f, String senhaAntiga) throws Exception {
        funcionarioDAO.editarFuncionario(f, senhaAntiga);
    }

    /**
     * Método responsável por pesquisar funcionário
     *
     * @param nome nome do funcionário, pode ser "" caso esse parâmetro não seja
     * adequado na pesquisa em questão
     * @param rg rg do funcionário, pode ser "" caso esse parâmetro não seja
     * adequado na pesquisa em questão
     * @param status status do funcionário
     * @return retorna um ArrayList de funcionarios
     * @throws Exception disparada caso ocorra algum erro durante a pesquisa
     */
    public ArrayList pesquisarFuncionario(String nome, String rg, String status) throws Exception {
        return funcionarioDAO.pesquisar(nome, rg, status);
    }

    /**
     * Método responsável por inserir um histórico
     *
     * @param h histórico a ser inserido
     * @throws Exception disparada caso ocorra algum erro durante a inserção
     */
    public void inserirHistorico(Historico h) throws Exception {
        historicoController.inserirHistorico(h);
    }

    /**
     * Método responsável por pesquisar os históricos do funcionário
     *
     * @param idPessoa pode ser "", caso esse parâmetro não seja adequado na
     * pesquisa em questão
     * @param dtInicio data de início de pesquisa, pode ser "" caso esse
     * parâmetro não seja adequado na pesquisa em questão
     * @param dtFinal data final de pesquisa, pode ser "" caso esse parâmetro
     * não seja adequado na pesquisa em questão
     * @param ordemDados ORDER BY referente a pesquisa no banco de dados
     * @return retorna um ArrayList de historico de funcionário
     * @throws Exception disparada caso a ocorra algum erro na pesquisa
     */
    public ArrayList pesquisarHistorico(String idPessoa, Date dtInicio, Date dtFinal, String ordemDados) throws Exception {
        return historicoController.pesquisarHistorico(idPessoa, dtInicio, dtFinal, ordemDados);
    }

    /**
     * Método responsável por autenticar um funcionário
     *
     * @param f funcionário a ser autenticado
     * @return null se não for validado ou um objeto do tipo Funcionario caso
     * seja validado
     * @throws Exception disparada caso a ocorra algum erro na autenticação
     */
    public Funcionario autenticarFuncionario(Funcionario f) throws Exception {
        return funcionarioDAO.autenticarFuncionario(f);
    }

    /**
     * Método responsável por retornar um objeto do tipo FuncionarioController
     *
     * @return retorna um objeto do tipo FuncionarioController
     */
    public static FuncionarioController getFuncionarioController() {
        return funcionarioController;
    }

    /**
     * Método responsável por retornar um objeto do tipo PacienteController
     *
     * @return retorna um objeto do tipo PacienteController
     */
    public PacienteController getPacienteController() {
        return pacienteController;
    }

    /**
     * Método responsável por retornar um objeto do tipo ResponsavelController
     *
     * @return retorna um objeto do tipo ResponsavelController
     */
    public ResponsavelController getResponsavelController() {
        return responsavelController;
    }

    /**
     * Método responsável por retornar um objeto do tipo LocalizacaoController
     *
     * @return retorna um objeto do tipo LocalizacaoController
     */
    public LocalizacaoController getLocalizacaoController() {
        return localizacaoController;
    }

    /**
     * Método responsável por retornar um objeto do tipo AtividadeController
     *
     * @return retorna um objeto do tipo AtividadeController
     */
    public AtividadeController getAtividadeController() {
        return atividadeController;
    }

    /**
     * Método responsável por retornar um objeto do tipo HistoricoController
     *
     * @return retorna um objeto do tipo HistoricoController
     */
    public HistoricoController getHistoricoController() {
        return historicoController;
    }

    /**
     * Método responsável por retornar um objeto do tipo PlanoController
     *
     * @return retorna um objeto do tipo PlanoController
     */
    public PlanoController getPlanoController() {
        return planoController;
    }

    /**
     * Método responsável por retornar um objeto do tipo AgendamentoController
     *
     * @return retorna um objeto do tipo AgendamentoController
     */
    public AgendamentoController getAgendamentoController() {
        return agendamentoController;
    }

    /**
     * Método responsável por retornar um objeto do tipo OrcamentoController
     *
     * @return retorna um objeto do tipo OrcamentoController
     */
    public OrcamentoController getOrcamentoController() {
        return orcamentoController;
    }

    /**
     * Método responsável por retornar um objeto do tipo ParcelaController
     *
     * @return retorna um objeto do tipo ParcelaController
     */
    public ParcelaController getParcelaController() {
        return parcelaController;
    }

    /**
     * Método responsável por retornar um objeto do tipo PermissaoController
     *
     * @return retorna um objeto do tipo PermissaoController
     */
    public PermissaoController getPermissaoController() {
        return permissaoController;
    }

    /**
     * Método responsável por retornar um objeto do tipo MaquinaController
     *
     * @return retorna um objeto do tipo MaquinaController
     */
    public MaquinaController getMaquinaController() {
        return maquinaController;
    }

    /**
     * Método responsável por retorna o usuário logado no sistema
     *
     * @return retorna o usuário logado no sistema
     */
    public static Funcionario getFuncionarioLogado() {
        return funcionarioLogado;
    }

    /**
     * Método responsável por setar o usuário logado no sistema
     *
     * @param funcionarioLogado funcionário que será setado como logado
     */
    public static void setFuncionarioLogado(Funcionario funcionarioLogado) {
        FuncionarioController.funcionarioLogado = funcionarioLogado;
    }

    /**
     * Método responsável por verificar se o usuário logado possui ou não uma
     * permissão
     *
     * @param nomePermissao permissão que será verificada
     * @return true caso o usuário possua uma permissão e false caso não tenha
     */
    public Boolean possuiPermissao(String nomePermissao) {
        for (Permissao p : funcionarioLogado.getPermissoes()) {
            if (p.getDescricao().equals(nomePermissao)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Método responsável por retornar uma pessoa de acordo com o id
     *
     * @param id id da pessoa que será pesquisada
     * @return retorna uma pessoa
     * @throws Exception disparada caso ocorra algum erro durante a pesquisa
     */
    public Pessoa pesquisaPessoa(String id) throws Exception {
        return funcionarioDAO.retornaPessoa(id);
    }

    /**
     * Método responsável por realizar uma pesquisa geral de pessoa,
     * possibilitando trazer funcionário, paciente ou responsável
     *
     * @param nome nome da pessoa, será utilizado na consulta, pode ser "" caso
     * tal campo não seja relevante na pesquisa em questão
     * @param rg rg da pessoa, será utilizado na consulta, pode ser "" caso tal
     * campo não seja relevante na pesquisa em questão
     * @param status status da pessoa, será utilizado na consulta, pode ser ""
     * caso tal campo não seja relevante na pesquisa em questão
     * @param paciente true caso deseje pesquisar pacientes ou falso para o
     * inverso
     * @param funcionario true caso deseje pesquisar funcionários ou falso para
     * o inverso
     * @param responsavel true caso deseje pesquisar responsável ou falso para o
     * inverso
     * @return retorna um ArrayList com as informações encontradas
     * @throws Exception disparada caso algum erro ocorra
     */
    public ArrayList pesquisarPessoas(String nome, String rg, String status, Boolean paciente, Boolean funcionario, Boolean responsavel) throws Exception {
        return funcionarioDAO.pesquisarPessoas(nome, rg, status, paciente, funcionario, responsavel);
    }
}
