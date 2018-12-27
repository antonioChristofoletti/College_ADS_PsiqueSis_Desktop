/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import ConexaoDB.ConexaoBD;
import Geral.Uteis;
import Model.Funcionario;
import Model.Orcamento;
import Model.Parcela;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * Classe do tipo DAO relacionada aos orçamentos da aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public class OrcamentoDAO {

    /**
     * Objeto do tipo DAO relacionados a Parcela do sistema
     */
    private ParcelaDAO parcelaDAO;

    /**
     * Construtor da classe
     */
    public OrcamentoDAO() {
        parcelaDAO = new ParcelaDAO();
    }

    /**
     * Método responsável por inserir um orçamento
     *
     * @param o orçamento que será inserido
     * @throws Exception disparada durante o processo de inserção
     */
    public void inserir(Orcamento o) throws Exception {
        try {
            ConexaoBD.AbrirConexao();
            ConexaoBD.setAutoCommit(false);

            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("INSERT INTO orcamento (data, descricao, status, tipo, idFuncionario) VALUES(?,?,?,?,?)");
            pst.setTimestamp(1, new Timestamp(o.getDataVencimento().getTime()));
            pst.setString(2, o.getDescricao());
            pst.setString(3, o.getStatus().substring(0, 1));
            pst.setString(4, o.getTipo().substring(0, 1));
            pst.setInt(5, o.getFuncionarioResponsavel().getIdEspecifico());
            pst.execute();

            int codigoOrcamento = retornaUltimoCodigoOrcamento();
            o.setId(codigoOrcamento);

            for (Parcela p : o.getParcelas()) {
                p.setOrcamento(o);
                parcelaDAO.inserir(p);
            }

            ConexaoBD.getConnection().commit();
        } catch (Exception ex) {
            ConexaoBD.getConnection().rollback();
            throw new Exception("Erro ao inserir Orçamento. Erro: " + ex.getMessage());
        } finally {
            ConexaoBD.setAutoCommit(true);
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método responsável por editar um orçamento
     *
     * @param o orçamento que será editado
     * @throws Exception disparada durante o processo de atualização
     */
    public void editar(Orcamento o) throws Exception {
        try {
            ConexaoBD.AbrirConexao();
            ConexaoBD.setAutoCommit(false);

            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("UPDATE orcamento SET data=?, descricao=?, status=?, tipo=? WHERE id=?");
            pst.setTimestamp(1, new Timestamp(o.getDataVencimento().getTime()));
            pst.setString(2, o.getDescricao());
            pst.setString(3, o.getStatus().substring(0, 1));
            pst.setString(4, o.getTipo().substring(0, 1));
            pst.setInt(5, o.getId());
            pst.execute();

            for (Parcela p1 : parcelaDAO.pesquisar(o.getId())) {
                boolean encontrou = false;
                for (Parcela p2 : o.getParcelas()) {
                    if (p1.getId() == p2.getId()) {
                        encontrou = true;
                        break;
                    }
                }

                if (!encontrou) {
                    parcelaDAO.remover(p1.getId());
                }
            }

            for (Parcela p : o.getParcelas()) {
                p.setOrcamento(o);

                if (p.getId() == 0) {
                    parcelaDAO.inserir(p);
                } else {
                    parcelaDAO.editar(p);
                }
            }

            ConexaoBD.getConnection().commit();
        } catch (Exception ex) {
            ConexaoBD.getConnection().rollback();
            throw new Exception("Erro ao editar Orçamento. Erro: " + ex.getMessage());
        } finally {
            ConexaoBD.setAutoCommit(true);
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método responsável por pesquisar orçamento
     *
     * @param descricao descrição do orçamento, pode ser "" caso esse parâmetro
     * não seja adequado na pesquisa em questão
     * @param tipo tipo do orçamento, pode ser "" caso esse parâmetro não seja
     * adequado na pesquisa em questão
     * @param dtInicio data de início do orçamento, pode ser null caso esse
     * parâmetro não seja adequado na pesquisa em questão
     * @param dtFinal data final do orçamento pode ser null caso esse parâmetro
     * não seja adequado na pesquisa em questão
     * @return retorna um ArrayList de orçamentos pesquisados
     * @throws Exception disparada durante o processo de pesquisa
     */
    public ArrayList pesquisar(String descricao, Date dtInicio, Date dtFinal, String tipo) throws Exception {
        try {
            ArrayList<Orcamento> orcamentos = new ArrayList<>();
            ConexaoBD.AbrirConexao();

            PreparedStatement stmt = ConexaoBD.getConnection().prepareStatement("SELECT o.*, p.id idPessoa, f.id idFuncionario, p.nome nomePessoa FROM orcamento o "
                    + "INNER JOIN funcionario f ON o.idFuncionario = f.id "
                    + "INNER JOIN pessoa p ON p.id = f.idPessoa " + geraWhere(descricao, dtInicio, dtFinal, tipo) + " ORDER BY o.data");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Orcamento o = new Orcamento();
                o.setId(Integer.parseInt(rs.getString("id")));
                o.setDescricao(rs.getString("descricao"));
                o.setDataVencimento(rs.getDate("data"));

                switch (rs.getString("status")) {
                    case "C": {
                        o.setStatus("Cancelado");
                        break;
                    }

                    case "N": {
                        o.setStatus("Normal");
                        break;
                    }
                }

                if (rs.getString("tipo").equals("E")) {
                    o.setTipo("Entrada");
                } else {
                    o.setTipo("Saída");
                }

                o.setParcelas(parcelaDAO.pesquisar(o.getId()));

                Funcionario f = new Funcionario();
                f.setIdPessoa(rs.getInt("idPessoa"));
                f.setNome(rs.getString("nomePessoa"));
                f.setIdEspecifico(rs.getInt("idFuncionario"));

                o.setFuncionarioResponsavel(f);
                orcamentos.add(o);
            }

            rs.close();
            stmt.close();
            return (orcamentos);
        } catch (Exception ex) {
            throw new Exception("Erro ao pesquisar orçamentos. Erro: " + ex.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método responsável por retornar uma string com o WHERE para a consulta
     * SQL
     *
     * @param descricao descrição do orçamento, pode ser "" caso esse parâmetro
     * não seja adequado na pesquisa em questão
     * @param tipo tipo do orçamento, pode ser "" caso esse parâmetro não seja
     * adequado na pesquisa em questão
     * @param dtInicio data de início do orçamento, pode ser null caso esse
     * parâmetro não seja adequado na pesquisa em questão
     * @param dtFinal data final do orçamento pode ser null caso esse parâmetro
     * não seja adequado na retorna uma string com o WHERE para a consulta
     * SQLpesquisa em questão
     * @return retorna uma string com o WHERE para a consulta SQL
     */
    private String geraWhere(String descricao, Date dtInicio, Date dtFinal, String tipo) {
        String where = "WHERE ";

        if (!descricao.equals("")) {
            where += "o.descricao like '%" + descricao + "%' AND ";
        }

        if (dtInicio != null) {
            where += " o.data >= '" + Uteis.converteData("yyyy-MM-dd", dtInicio) + " 00:00:00' AND ";
        }

        if (dtFinal != null) {
            where += " o.data <= '" + Uteis.converteData("yyyy-MM-dd", dtFinal) + " 23:59:59' AND ";
        }

        if (!tipo.equals("Todos")) {
            where += "o.tipo='" + tipo.substring(0, 1) + "' AND ";
        }

        return where += " 1=1";
    }

    /**
     * Método responsável por retornar o último código de orçamento inserido no
     * banco de dados
     *
     * @return retorna o último código de orçamento inserido no banco de dados
     * @throws Exception disparado caso ocorra algum erro na consulta
     */
    private int retornaUltimoCodigoOrcamento() throws Exception {
        try {
            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("SELECT MAX(id) codigoOrcamento FROM orcamento");
            pst.execute();

            ResultSet rs = pst.executeQuery();
            int codigoOrcamento = 1;
            if (rs.next()) {
                codigoOrcamento = rs.getInt("codigoOrcamento");
            }

            return codigoOrcamento;
        } catch (Exception ex) {
            throw new Exception("Erro ao pesquisar código Orçamento. Erro: " + ex.getMessage());
        }
    }
}
