package Controller;

import DAO.PermissaoDAO;
import Model.Permissao;
import Model.Pessoa;
import View.FrmPermissaoIncAlt;
import View.FrmPermissaoLista;
import View.FrmPermissoesUsuario;
import java.util.ArrayList;

/**
 * Classe do tipo controller relacionada as permissões da aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public class PermissaoController {

    /**
     * Tela de permissão lista
     */
    private FrmPermissaoLista frmPermissaoLista;
    /**
     * Tela de permissão Inc/Alt
     */
    private FrmPermissaoIncAlt frmPermissaoIncAlt;
    /**
     * Tela de permissão usuários. Tela na qual as permissões serão bloqueadas e
     * desbloqueadas aos usuários
     */
    private FrmPermissoesUsuario frmUsuarioPermissoes;
    /**
     * Objeto DAO referente a permissão
     */
    private PermissaoDAO permissaoDAO;
    /**
     * Objeto da própria classe PermissaoController por conta do padrão de
     * projeto Sington
     */
    private static PermissaoController permissaoController;

    /**
     * Construtor da classe
     */
    private PermissaoController() {
        permissaoDAO = new PermissaoDAO();
    }

    /**
     * Método responsável por invocar a tela de FrmPermissaoLista
     *
     * @param visible visibilidade da tela, pode ser true ou false
     */
    public void frmPermissaoListaSetVisible(boolean visible) {
        frmPermissaoLista = FrmPermissaoLista.getInstance();
        this.frmPermissaoLista.setVisible(visible);
    }

    /**
     * Método responsável por invocar a tela de FrmPermissaoIncAlt
     *
     * @param p permissão de edição, pode ser um parâmetro null caso a ideia
     * seja inserir um novo registro
     * @param visible visibilidade da tela, pode ser true ou false
     */
    public void frmPermissaoIncAltSetVisible(Permissao p, boolean visible) {
        frmPermissaoIncAlt = FrmPermissaoIncAlt.getInstance(p);
        this.frmPermissaoIncAlt.setVisible(visible);
    }

    /**
     * Método responsável por invocar a tela de frmUsuarioPermissoes
     *
     * @param p permissão que será gerenciada na tela
     * @param visible visibilidade da tela, pode ser true ou false
     */
    public void frmUsuarioPermissoesSetVisible(Permissao p, boolean visible) {
        frmUsuarioPermissoes = FrmPermissoesUsuario.getInstance(p);
        this.frmUsuarioPermissoes.setVisible(visible);
    }

    /**
     * Método responsável por retornar uma instância da própria classe
     *
     * @return retorna uma instância da própria classe
     */
    public static PermissaoController getInstance() {
        if (permissaoController == null) {
            permissaoController = new PermissaoController();
        }

        return permissaoController;
    }

    /**
     * Método responsável por inserir uma nova permissão
     *
     * @param p permissão que será inserido
     * @throws Exception disparada durante o processo de inserção
     */
    public void inserirPermissao(Permissao p) throws Exception {
        permissaoDAO.inserir(p);
    }

    /**
     * Método responsável por editar uma permissão
     *
     * @param p permissão que será editada
     * @throws Exception disparada durante o processo de edição
     */
    public void editarPermissao(Permissao p) throws Exception {
        permissaoDAO.editar(p);
    }

    /**
     * Método responsável por pesquisar permissões
     *
     * @param descricao descrição da permissão, pode ser "" caso esse parâmetro
     * não seja adequado na pesquisa em questão
     * @param status status da permissão
     * @return retorna um ArrayList de permissões pesquisadas
     * @throws Exception disparada durante o processo de pesquisa
     */
    public ArrayList pesquisarPermissao(String descricao, String status) throws Exception {
        return permissaoDAO.pesquisar(descricao, status);
    }

    /**
     * Método responsável por validar os atributos da permissão
     *
     * @param p permissão que será validada
     * @param pAntesAlterar permissão antes de sofrer as alterações, caso seja
     * uma permissão nova basta passar null
     * @throws Exception disparada caso algum atributo esteja incorreto
     */
    public void validarAtributos(Permissao p, Permissao pAntesAlterar) throws Exception {
        if (p.getDescricao().equals("")) {

            if (frmPermissaoIncAlt != null && frmPermissaoIncAlt.isVisible()) {
                frmPermissaoIncAlt.getTxtDescricao().requestFocus();
            }

            throw new Exception("Descrição Inválida");
        }

        if (pAntesAlterar != null && permissaoDAO.existeDescricaoPermissao(p.getDescricao()) && !p.getDescricao().equals(pAntesAlterar.getDescricao())) {
            frmPermissaoIncAlt.getTxtDescricao().requestFocus();
            throw new Exception("Descrição já existente");
        }

        if (p.getStatus().equals("")) {

            if (frmPermissaoIncAlt != null && frmPermissaoIncAlt.isVisible()) {
                frmPermissaoIncAlt.getCmbStatus().requestFocus();
            }

            throw new Exception("Status Inválido");
        }
    }

    /**
     * Método responsável por pesquisar usuários por permissão
     *
     * @param p permissão que será utilizada na pesquisa, pode-se passar null caso esse parâmetro
     * não seja adequado na pesquisa em questão
     * @param nomeUsuario que será utilizado na pesquisa, pode-se passar "" caso esse parâmetro
     * não seja adequado na pesquisa em questão 
     * @param nomeLogin que será utilizado na pesquisa, pode-se passar "" caso esse parâmetro
     * não seja adequado na pesquisa em questão
     * @param possuiPermissao parâmetro que verificará se o usuário possui ou
     * não a permissão
     * @return retorna uma ArrayList com todos usuários encontrados
     * @throws Exception disparada caso ocorra algum erro durante a pesquisa
     */
    public ArrayList<Pessoa> pesquisarPessoaPorPermissao(Permissao p, String nomeUsuario, String nomeLogin, String possuiPermissao) throws Exception {
        return permissaoDAO.pesquisarPessoaPorPermissao(p, nomeUsuario, nomeLogin, possuiPermissao);
    }

    /**
     * Método responsável por liberar uma determinada permissão a determinados
     * usuários
     *
     * @param listaPessoa usuários que sofrerão a alteração
     * @param p permissão em questão
     * @throws Exception disparada durante o processo de pesquisa ou atualização
     */
    public void liberarPermissao(ArrayList<Pessoa> listaPessoa, Permissao p) throws Exception {
        permissaoDAO.liberarPermissao(listaPessoa, p);
    }

    /**
     * Método responsável por retirar uma determinada permissão de determinados
     * usuários
     *
     * @param listaPessoa usuários que sofrerão a alteração
     * @param p permissão em questão
     * @throws Exception disparada durante o processo de pesquisa ou atualização
     */
    public void retirarPermissao(ArrayList<Pessoa> listaPessoa, Permissao p) throws Exception {
        permissaoDAO.retirarPermissao(listaPessoa, p);
    }

    /**
     * Método responsável por retornar as permissões que estão liberadas a
     * determinado usuário
     *
     * @param idPessoa usuário em questão
     * @return retorna um ArrayList com as permissões liberadas para tal usuário
     * @throws Exception disparada durante o processo de pesquisa
     */
    public ArrayList<Permissao> pesquisarPermissoesPorPessoa(String idPessoa) throws Exception {
        return permissaoDAO.pesquisarPermissoesPorPessoa(idPessoa);
    }
}
