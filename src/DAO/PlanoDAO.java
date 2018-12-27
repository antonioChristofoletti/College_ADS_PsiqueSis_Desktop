package DAO;

import ConexaoDB.ConexaoBD;
import Model.Plano;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Classe do tipo DAO relacionada aos planos da aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public class PlanoDAO {

    /**
     * Método responsável por inserir um plano
     *
     * @param p plano que será inserido
     * @throws Exception disparada durante o processo de inserção
     */
    public void inserir(Plano p) throws Exception {
        try {
            ConexaoBD.AbrirConexao();
            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("INSERT INTO plano (nomeInstituicao, quantiConsulta, descricao, status) VALUES(?,?,?,?)");
            pst.setString(1, p.getNomeInstituicao());
            pst.setInt(2, p.getQuantiConsultas());
            pst.setString(3, p.getDescricao());
            pst.setString(4, p.getStatus().substring(0, 1));

            pst.execute();
        } catch (Exception ex) {
            throw new Exception("Erro ao inserir plano. Erro: " + ex.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método responsável por editar um plano
     *
     * @param p plano que será editado
     * @throws Exception disparada durante o processo de edição
     */
    public void editar(Plano p) throws Exception {
        try {
            ConexaoBD.AbrirConexao();
            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("UPDATE plano SET nomeInstituicao=?, quantiConsulta=?, descricao=?, status=? WHERE id=?");
            pst.setString(1, p.getNomeInstituicao());
            pst.setInt(2, p.getQuantiConsultas());
            pst.setString(3, p.getDescricao());
            pst.setString(4, p.getStatus().substring(0, 1));
            pst.setInt(5, p.getId());

            pst.execute();
        } catch (Exception ex) {
            throw new Exception("Erro ao inserir plano. Erro: " + ex.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
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
    public ArrayList pesquisar(String nome, String status) throws Exception {
        try {
            ArrayList<Plano> atividades = new ArrayList<>();
            ConexaoBD.AbrirConexao();

            PreparedStatement stmt = ConexaoBD.getConnection().prepareStatement("SELECT * FROM plano " + geraWherePesquisar(nome, status));;
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Plano p = new Plano();
                p.setId(Integer.parseInt(rs.getString("id")));
                p.setQuantiConsultas(Integer.parseInt(rs.getString("quantiConsulta")));

                p.setDescricao(rs.getString("descricao"));

                if (rs.getString("status").equals("A")) {
                    p.setStatus("Ativo");
                } else {
                    p.setStatus("Inativo");
                }

                p.setNomeInstituicao(rs.getString("nomeInstituicao"));

                atividades.add(p);
            }

            rs.close();
            stmt.close();
            return (atividades);
        } catch (Exception ex) {
            throw new Exception("Erro ao pesquisar plano. Erro: " + ex.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método responsável por retornar uma string com o WHERE para a consulta
     * SQL
     *
     * @param nome nome do plano, pode ser "" caso esse parâmetro não seja
     * adequado na pesquisa em questão
     * @param status status do plano
     * @return retorna uma string com o WHERE para a consulta SQL
     */
    public String geraWherePesquisar(String nome, String status) {
        String where = "WHERE ";
        if (!nome.equals("")) {
            where += "nomeInstituicao LIKE '%" + nome + "%' AND ";
        }

        if (!status.equals("Todos")) {
            where += "status='" + status.substring(0, 1) + "' AND ";
        }

        return where += "1=1";
    }

    /**
     * Método responsável por verificar se um nome de instituição de plano já existe no
     * sistema ou não
     *
     * @param nome nome de instituição de plano que será validado
     * @return retorna true ou false
     * @throws Exception disparada durante o processo de validação
     */
    public boolean existeNomeInstituicao(String nome) throws Exception {
        try {
            ConexaoBD.AbrirConexao();
            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("SELECT COUNT(*) FROM plano WHERE nomeInstituicao=?");
            pst.setString(1, nome);
            ResultSet rs = pst.executeQuery();
            rs.next();

            if (rs.getInt(1) > 0) {
                pst.close();
                rs.close();
                return true;
            } else {
                pst.close();
                rs.close();
                return false;
            }
        } catch (Exception e) {
            throw new Exception("Erro ao pesquisar nome plano. Erro: " + e.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
    }
}
