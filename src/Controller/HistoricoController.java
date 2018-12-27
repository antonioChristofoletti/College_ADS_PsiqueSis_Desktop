package Controller;

import DAO.HistoricoDAO;
import Model.Historico;
import java.util.ArrayList;
import java.util.Date;

/**
 * Classe do tipo controller relacionada aos históricos da aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public class HistoricoController {

    /**
     * Objeto do tipo HistoricoDAO
     */
    HistoricoDAO historicoDAO;

    /**
     * Objeto referente a própria classe
     */
    private static HistoricoController historicoController;

    /**
     * Construtor da classe
     */
    private HistoricoController() {
        historicoDAO = new HistoricoDAO();
    }

    /**
     * Método responsável por retornar uma instância da classe
     *
     * @return Retorna uma instância da classe
     */
    public static HistoricoController getInstance() {
        if (historicoController == null) {
            historicoController = new HistoricoController();
        }

        return historicoController;
    }

    /**
     * Método responsável por inserir um histórico no banco de dados
     *
     * @param h histórico que será inserido
     * @throws Exception disparada caso ocorra algum erro ao inserir histórico
     */
    public void inserirHistorico(Historico h) throws Exception {
        historicoDAO.inserir(h);
    }

    /**
     * Método responsável por pesquisar histório
     *
     * @param idPessoa pode ser "" caso esse parâmetro não seja adequado na
     * pesquisa em questão
     * @param dtInicio data inicial de pesquisa, pode ser "" caso esse parâmetro
     * não seja adequado na pesquisa em ques
     * @param dtFinal data final de pesquisa, pode ser "" caso esse parâmetro
     * não seja adequado na pesquisa em ques
     * @param ordemDados ORDER BY de pesquisa no banco de dados
     * @return retorna ArrayList dos históricos encontrados
     * @throws Exception disparada caso a ocorra algum erro na pesquisa
     */
    public ArrayList pesquisarHistorico(String idPessoa, Date dtInicio, Date dtFinal, String ordemDados) throws Exception {
        return historicoDAO.pesquisar(idPessoa, dtInicio, dtFinal, ordemDados);
    }
}
