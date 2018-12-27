/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import ConexaoDB.ConexaoBD;
import Model.Responsavel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Classe do tipo DAO relacionada aos responsáveis da aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public class ResponsavelDAO extends PessoaDAO {

    /**
     * Método responsável por inserir um responsável
     *
     * @param r responsável que será inserido
     * @throws Exception disparada durante o processo de inserção
     */
    public void inserirResponsavel(Responsavel r) throws Exception {
        try {
            ConexaoBD.AbrirConexao();
            ConexaoBD.setAutoCommit(false);
            this.inserirPessoa(r);

            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("INSERT INTO responsavel (idPessoa) VALUES (?)");
            pst.setInt(1, this.retornaUltimoCodigoPessoa());
            pst.execute();
            ConexaoBD.getConnection().commit();
        } catch (Exception e) {
            ConexaoBD.getConnection().rollback();
            throw new Exception("Erro ao inserir responsável. Erro: " + e.getMessage());
        } finally {
            ConexaoBD.setAutoCommit(true);
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método responsável por editar um responsável
     *
     * @param r responsável que será editado
     * @param senhaAntiga senha antes de realizar a atualização, importante por conta da criptografia MD5
     * @throws Exception disparada durante o processo de edição
     */
    public void editartResponsavel(Responsavel r, String senhaAntiga) throws Exception {
        try {
            ConexaoBD.AbrirConexao();
            ConexaoBD.setAutoCommit(false);
            this.editarPessoa(r, senhaAntiga);

            ConexaoBD.getConnection().commit();
        } catch (Exception e) {
            ConexaoBD.getConnection().rollback();
            throw new Exception("Erro ao editar responsável. Erro: " + e.getMessage());
        } finally {
            ConexaoBD.setAutoCommit(true);
            ConexaoBD.FecharConexao();
        }
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
    public ArrayList pesquisar(String nome, String rg, String status) throws Exception {
        try {
            ArrayList<Responsavel> responsaveis = new ArrayList<>();
            ConexaoBD.AbrirConexao();

            PreparedStatement stmt = ConexaoBD.getConnection().prepareStatement("SELECT *, p.id idPessoa, r.id idResponsavel FROM pessoa p INNER JOIN responsavel r ON r.idPessoa = p.id " + geraWhere(nome, rg, status));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Responsavel r = new Responsavel();
                r.setIdPessoa(Integer.parseInt(rs.getString("idPessoa")));
                r.setIdEspecifico(Integer.parseInt(rs.getString("idResponsavel")));
                r.setBairro(rs.getString("bairro"));
                r.setCidade(rs.getString("cidade"));
                r.setCpf(rs.getString("cpf"));
                r.setLogradouro(rs.getString("logradouro"));
                r.setNome(rs.getString("nome"));
                r.setNomeLogin(rs.getString("nomeLogin"));
                r.setRg(rs.getString("rg"));
                r.setSenha(rs.getString("senha"));
                r.setTelefone1(rs.getString("telefone1"));
                r.setTelefone2(rs.getString("telefone2"));

                switch (rs.getString("status")) {
                    case "A": {
                        r.setStatus("Ativo");
                        break;
                    }

                    case "I": {
                        r.setStatus("Inativo");
                        break;
                    }
                }
                responsaveis.add(r);
            }

            rs.close();
            stmt.close();
            return (responsaveis);
        } catch (Exception ex) {
            throw new Exception("Erro ao pesquisar funcionário. Erro: " + ex.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método responsável por retornar uma string com o WHERE para a consulta
     * SQL
     * @param nome nome do responsável, pode ser "" caso esse parâmetro não seja
     * adequado na pesquisa em questão
     * @param status status do responsável
     * @param rg rg do responsável, pode ser "" caso esse parâmetro não seja
     * adequado na pesquisa em questão
     * @return retorna uma String com o WHERE gerado
     */
    public String geraWhere(String nome, String rg, String status) {
        String where = "WHERE ";

        if (!nome.equals("")) {
            where += "p.nome LIKE '%" + nome + "%' AND ";
        }

        if (!rg.equals("")) {
            where += "p.rg LIKE '%" + rg + "%' AND ";
        }

        if (!status.equals("Todos")) {
            where += "p.status='" + status.substring(0, 1) + "' AND ";
        }

        return where += " 1=1";
    }
}
