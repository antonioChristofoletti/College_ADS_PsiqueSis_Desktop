package DAO;

import ConexaoDB.ConexaoBD;
import Geral.Uteis;
import Model.Funcionario;
import Model.Historico;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * Classe do tipo DAO relacionada aos históricos dos funcionários da aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public class HistoricoDAO {

    /**
     * Método responsável por inserir um histórico no banco de dados
     *
     * @param h histórico que será inserido
     * @throws Exception disparado caso ocorra algum erro ao inserir histórico
     */
    public void inserir(Historico h) throws Exception {
        try {
            ConexaoBD.AbrirConexao();
            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("INSERT INTO historico (descricao, data, idPessoa) VALUES(?,?,?)");
            pst.setString(1, h.getDescricao());
            pst.setTimestamp(2, new Timestamp(h.getData().getTime()));
            pst.setInt(3, h.getPessoa().getIdPessoa());
            pst.execute();
        } catch (Exception ex) {
            throw new Exception("Erro ao inserir histórico. Erro: " + ex.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
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
     * @throws Exception disparado caso a ocorra algum erro na pesquisa
     */
    public ArrayList pesquisar(String idPessoa, Date dtInicio, Date dtFinal, String ordemDados) throws Exception {
        try {
            ArrayList<Historico> historico = new ArrayList<>();
            ConexaoBD.AbrirConexao();

            PreparedStatement stmt = ConexaoBD.getConnection().prepareStatement("SELECT * FROM historico h INNER JOIN pessoa p ON p.id = h.idPessoa " + geraWherePesquisar(idPessoa, dtInicio, dtFinal, ordemDados));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Historico h = new Historico();

                h.setData(rs.getTimestamp("data"));
                h.setDescricao(rs.getString("descricao"));
                h.setId(rs.getInt("id"));

                Funcionario f = new Funcionario();
                f.setIdPessoa(Integer.parseInt(rs.getString("id")));
                f.setBairro(rs.getString("bairro"));
                f.setCidade(rs.getString("cidade"));
                f.setCpf(rs.getString("cpf"));
                f.setLogradouro(rs.getString("logradouro"));
                f.setNome(rs.getString("nome"));
                f.setNomeLogin(rs.getString("nomeLogin"));
                f.setRg(rs.getString("rg"));
                f.setSenha(rs.getString("senha"));
                f.setTelefone1(rs.getString("telefone1"));
                f.setTelefone2(rs.getString("telefone2"));

                switch (rs.getString("status")) {
                    case "A": {
                        f.setStatus("Ativo");
                        break;
                    }

                    case "I": {
                        f.setStatus("Inativo");
                        break;
                    }
                }

                h.setPessoa(f);

                historico.add(h);
            }

            rs.close();
            stmt.close();
            return (historico);
        } catch (Exception ex) {
            throw new Exception("Erro ao pesquisar histórico. Erro: " + ex.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método privado que gera o WHERE da consulta SQL
     *
     * @param idPessoa pode ser "" caso esse parâmetro não seja adequado na
     * pesquisa em questão
     * @param dtInicio data inicial de pesquisa, pode ser "" caso esse parâmetro
     * não seja adequado na pesquisa em ques
     * @param dtFinal data final de pesquisa, pode ser "" caso esse parâmetro
     * não seja adequado na pesquisa em ques
     * @param ordemDados ORDER BY de pesquisa no banco de dados
     * @return retorna uma string com o WHERE montado
     */
    private String geraWherePesquisar(String idPessoa, Date dtInicio, Date dtFinal, String ordemDados) {
        String where = "WHERE ";

        if (!idPessoa.equals("")) {
            where += "p.id=" + idPessoa + " AND ";
        }

        if (dtInicio != null) {
            where += " h.data >= '" + Uteis.converteData("yyyy-MM-dd", dtInicio) + " 00:00:00' AND ";
        }

        if (dtFinal != null) {
            where += " h.data <= '" + Uteis.converteData("yyyy-MM-dd", dtFinal) + " 23:59:59' AND ";
        }

        where += "1=1";

        if (ordemDados.equals("Data Decrescente")) {
            where += " ORDER BY h.data DESC";
        } else {
            where += " ORDER BY h.data ASC";
        }

        return where;
    }
}
