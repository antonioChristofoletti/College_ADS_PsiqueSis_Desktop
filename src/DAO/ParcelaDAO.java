/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import ConexaoDB.ConexaoBD;
import Model.Parcela;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Classe do tipo DAO relacionada as parcelas da aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public class ParcelaDAO {

    /**
     * Método responsável por inserir uma parcela
     *
     * @param p parcela que será inserida
     * @throws Exception disparada durante o processo de inserção
     */
    public void inserir(Parcela p) throws Exception {
        try {
            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("INSERT INTO parcela (dataVencimento, descricao, situacao, valor, idOrcamento) VALUES(?,?,?,?,?)");
            pst.setTimestamp(1, new Timestamp(p.getDataVencimento().getTime()));
            pst.setString(2, p.getDescricao());
            pst.setString(3, p.getStatus().substring(0, 1));
            pst.setDouble(4, p.getValor());
            pst.setInt(5, p.getorcamento().getId());

            pst.execute();
        } catch (Exception ex) {
            throw new Exception("Erro ao inserir parcela. Erro: " + ex.getMessage());
        }
    }

    /**
     * Método responsável por editar uma parcela
     *
     * @param p parcela que será editada
     * @throws Exception disparada durante o processo de atualização
     */
    public void editar(Parcela p) throws Exception {
        try {
            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("UPDATE parcela SET dataVencimento=?, descricao=?, situacao=?, valor=? WHERE id=?");
            pst.setTimestamp(1, new Timestamp(p.getDataVencimento().getTime()));
            pst.setString(2, p.getDescricao());
            pst.setString(3, p.getStatus().substring(0, 1));
            pst.setDouble(4, p.getValor());
            pst.setInt(5, p.getId());

            pst.execute();
        } catch (Exception ex) {
            throw new Exception("Erro ao editar parcela de ID '" + p.getId() + "'. Erro: " + ex.getMessage());
        }
    }

    /**
     * Método responsável por remover uma parcela
     * @param idParcela id da parcela que será removida
     * @throws Exception disparada durante o processo de remoção
     */
    public void remover(int idParcela) throws Exception {
        try {
            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("DELETE FROM parcela WHERE id=?");
            pst.setInt(1, idParcela);
            pst.execute();
        } catch (Exception ex) {
            throw new Exception("Erro ao remover parcela de ID '" + idParcela + "'. Erro: " + ex.getMessage());
        }
    }

    /**
     * Método responsável por pesquisar parcelas
     * @param idOrcamento id do orçamento que as parcelas estão vinculadas
     * @return retorna um ArrayList com as parcelas encontradas
     * @throws Exception  disparada durante o processo de pesquisa
     */
    public ArrayList<Parcela> pesquisar(int idOrcamento) throws Exception {
        try {
            ArrayList<Parcela> parcelas = new ArrayList<>();

            PreparedStatement stmt = ConexaoBD.getConnection().prepareStatement("SELECT * FROM parcela WHERE idOrcamento=? ORDER BY dataVencimento");
            stmt.setInt(1, idOrcamento);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Parcela p = new Parcela();
                p.setId(Integer.parseInt(rs.getString("id")));
                p.setDescricao(rs.getString("descricao"));

                if (rs.getString("situacao").equals("P")) {
                    p.setStatus("Pago");
                } else {
                    p.setStatus("Não Pago");
                }

                p.setValor(rs.getDouble("valor"));
                p.setDataVencimento(rs.getDate("dataVencimento"));
                parcelas.add(p);
            }
            return (parcelas);
        } catch (Exception ex) {
            throw new Exception("Erro ao pesquisar parcelas. Erro: " + ex.getMessage());
        }
    }
}