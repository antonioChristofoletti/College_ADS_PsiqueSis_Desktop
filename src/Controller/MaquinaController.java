package Controller;

import DAO.MaquinaDAO;
import Model.Maquina;
import Model.Pessoa;
import View.FrmMaquinaLista;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Date;

/**
 * Classe do tipo controller relacionada as máquinas da aplicação
 *
 * @author Antonio Lucas Christofletti
 */
public class MaquinaController {

    /**
     * Objeto DAO referente a máquina
     */
    private MaquinaDAO maquinaDAO;

    /**
     * Objeto da própria classe MaquinaController por conta do padrão de projeto
     * Sington
     */
    private static MaquinaController atividadeController;

    /**
     * Tela de Máquina lista
     */
    private FrmMaquinaLista frmMaquinaLista;

    /**
     * Construtor da classe
     */
    private MaquinaController() {
        maquinaDAO = new MaquinaDAO();
    }

    /**
     * Retorna uma instância da própria classe
     *
     * @return retorna uma instância da própria classe
     */
    public static MaquinaController getInstance() {
        if (atividadeController == null) {
            atividadeController = new MaquinaController();
        }

        return atividadeController;
    }

    /**
     * Método responsável por invocar a tela de FrmMaquinaLista
     *
     * @param visible visibilidade da tela, pode ser true ou false
     */
    public void frmMaquinaListaSetVisible(Boolean visible) {
        FrmMaquinaLista.getInstance().setVisible(visible);
    }

    /**
     * Método responsável por inserir uma máquina
     *
     * @param m máquina que será inserida
     * @throws Exception disparada durante o processo de inserção
     */
    public void inserirMaquina(Maquina m) throws Exception {
        maquinaDAO.inserir(m);
    }

    /**
     * Método responsável por editar uma máquina
     *
     * @param m máquina que será editada
     * @throws Exception disparada durante o processo de atualização
     */
    public void editarMaquina(Maquina m) throws Exception {
        maquinaDAO.editar(m);
    }

    /**
     * Método responsável por remover uma máquina
     * @param mac mac da máquina que será removida
     * @throws Exception disparada caso ocorra algum erro na remoção
     */
    public void removerMaquina(String mac) throws Exception {
        maquinaDAO.remover(mac);
    }

    /**
     * Método responsável por verificar se a porta de soquete é valida
     * @param porta porta que será validada
     * @return retorna true ou falso
     */
    public boolean portaValida(String porta) {
        try {
            ServerSocket socket = null;
            socket = new ServerSocket(Integer.parseInt(porta));
            if (socket != null) {
                socket.close();
                return true;
            }
        } catch (IOException ex) {
        }
        return false;
    }

    /**
     * Método responsável por verificar se o usuário já está logado em outra máquina
     * @param p pessoa que será validada
     * @return retorna true ou false
     * @throws Exception disparada caso algum registro já exista
     */
    public boolean usuarioLogado(Pessoa p) throws Exception {
        return maquinaDAO.usuarioLogado(p);
    }

    /**
     * Método responsável por verificar se uma máquina já está cadastrada
     * @param mac mac que será verificado no banco de dados
     * @return retorna true ou false
     * @throws Exception disparada caso ocorra algum erro
     */
    public boolean maquinaCadastrada(String mac) throws Exception {
        return maquinaDAO.maquinaCadastrada(mac);
    }

    /**
     * Método responsável por realizar a pesquisa  de máquinas 
     * @param dateAcessoInicial data de acesso inicial de pesquisa, pode ser null caso esse parâmetro não seja adequado na pesquisa em questão
     * @param dataAcessoFinal data de acesso final de pesquisa, pode ser null caso esse parâmetro não seja adequado na pesquisa em questão
     * @param status status da máquina
     * @return retorna um ArrayList de Máquinas encontradas
     * @throws Exception disparada caso ocorra algum erro na pesquisa
     */
    public ArrayList pesquisarMaquina(Date dateAcessoInicial, Date dataAcessoFinal, String status) throws Exception {
        return maquinaDAO.pesquisar(dateAcessoInicial, dataAcessoFinal, status);
    }
}
