package DAO;

import ConexaoDB.ConexaoBD;
import Model.Atividade;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Classe do tipo DAO relacionada as atividades da aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public class AtividadeDAO {

    /**
     * Método responsável por inserir uma atividade
     *
     * @param a atividade que será inserida
     * @throws Exception disparada durante o processo de inserção
     */
    public void inserir(Atividade a) throws Exception {
        try {
            ConexaoBD.AbrirConexao();
            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("INSERT INTO atividade (descricao, nome, status) VALUES(?,?,?)");
            pst.setString(1, a.getDescricao());
            pst.setString(2, a.getNome());
            if (a.getStatus().equals("Ativo")) {
                pst.setString(3, "A");
            } else {
                pst.setString(3, "I");
            }
            pst.execute();
        } catch (Exception ex) {
            throw new Exception("Erro ao inserir atividade. Erro: " + ex.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método responsável por editar uma atividade
     *
     * @param a atividade que será editada
     * @throws Exception disparada durante o processo de atualização
     */
    public void editar(Atividade a) throws Exception {
        try {
            ConexaoBD.AbrirConexao();
            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("UPDATE atividade SET status=?, descricao=?, nome=? WHERE id=?");
            pst.setString(1, a.getStatus().substring(0, 1));
            pst.setString(2, a.getDescricao());
            pst.setString(3, a.getNome());
            pst.setInt(4, a.getId());

            pst.execute();
        } catch (Exception ex) {
            throw new Exception("Erro ao remover a atividade. Erro: " + ex.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método responsável por pesquisar atividade
     *
     * @param nome nome da atividade, pode ser "" caso esse parâmetro não seja
     * adequado na pesquisa em questão
     * @param status status da atividade, pode ser "" caso esse parâmetro não
     * seja adequado na pesquisa em questão
     * @return retorna um ArrayList com as atividades encontradas
     * @throws Exception disparada durante o processo de pesquisa
     */
    public ArrayList pesquisar(String nome, String status) throws Exception {
        try {
            ArrayList<Atividade> atividades = new ArrayList<>();
            ConexaoBD.AbrirConexao();

            PreparedStatement stmt = ConexaoBD.getConnection().prepareStatement("SELECT * FROM atividade " + geraWherePesquisar(nome, status));;
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Atividade a = new Atividade();
                a.setId(Integer.parseInt(rs.getString("id")));
                a.setDescricao(rs.getString("descricao"));

                if (rs.getString("status").equals("A")) {
                    a.setStatus("Ativo");
                } else {
                    a.setStatus("Inativo");
                }

                a.setNome(rs.getString("nome"));

                atividades.add(a);
            }

            rs.close();
            stmt.close();
            return (atividades);
        } catch (Exception ex) {
            throw new Exception("Erro ao pesquisar atividade. Erro: " + ex.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método privado que gera o WHERE da consulta SQL
     *
     * @param nome nome da atividade, pode ser "" caso esse parâmetro não seja
     * adequado na pesquisa em questão
     * @param status status da atividade, pode ser "" caso esse parâmetro não
     * seja adequado na pesquisa em questão
     * @return retorna uma String com o WHERE formatado de acordo com os
     * parâmetros
     */
    private String geraWherePesquisar(String nome, String status) {
        String where = "WHERE ";
        if (!nome.equals("")) {
            where += "nome LIKE '%" + nome + "%' AND ";
        }

        if (!status.equals("Todos")) {
            where += "status='" + status.substring(0, 1) + "' AND ";
        }
        
        return where += "1=1";
    }

    /**
     * Método responsável por verificar se um nome de atividade existe ou não
     * @param nome nome da atividade a ser verificada
     * @return retorna true ou false
     * @throws Exception disparada caso ocorra algum erro durante a pesquisa das informações
     */
    public boolean existeNomeAtividade(String nome) throws Exception {
        try {
            ConexaoBD.AbrirConexao();
            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("SELECT COUNT(*) FROM atividade WHERE nome=?");
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
            throw new Exception("Erro ao pesquisar nome atividade. Erro: " + e.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
    }
}
