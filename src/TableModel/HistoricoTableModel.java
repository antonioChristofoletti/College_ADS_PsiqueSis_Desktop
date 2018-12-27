/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModel;

import Geral.Uteis;
import Model.Historico;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

/**
 * Classe que herda de AbstractTableModel e tem como objeto gerenciar as
 * informações de uma JTable relacionada aos históricos dos funcionários
 *
 * @author Antonio Lucas Christofoletti
 */
public class HistoricoTableModel extends AbstractTableModel {

    /**
     * ArrayList que armazena todos os objetos de histórico da JTable
     */
    private ArrayList<Historico> listaHistorico;
    /**
     * ArrayList que armazena as colunas da JTable
     */
    private String[] colunas = {"ID", "Descrição", "Data", "Funcionário"};

    /**
     * Construtor da classe
     */
    public HistoricoTableModel() {
        this.listaHistorico = new ArrayList<>();
        fireTableDataChanged();
    }

    /**
     * Método responsável por adicionar um histórico na JTable
     *
     * @param h histórico que será inserido
     */
    public void addAtividade(Historico h) {
        this.listaHistorico.add(h);
        fireTableDataChanged();
    }

    /**
     * Método responsável por adicionar um ArrayList
     *
     * @param lh arrayList que será adicionado
     */
    public void addAll(ArrayList<Historico> lh) {
        this.listaHistorico.addAll(lh);
        fireTableDataChanged();
    }

    /**
     * Método responsável por retornar todos os objetos
     *
     * @return retorna um ArrayList de objetos
     */
    public ArrayList<Historico> getAll() {
        return listaHistorico;
    }

    /**
     * Método responsável por remover um histórico
     *
     * @param h histórico em questão
     */
    public void removerHistorico(Historico h) {
        listaHistorico.remove(h);
        fireTableDataChanged();
    }

    /**
     * Método responsável por remover todos os elementos da JTable
     */
    public void removeAll() {
        listaHistorico.removeAll(listaHistorico);
        fireTableDataChanged();
    }

    /**
     * Método responsável por retornar um objeto
     *
     * @param rowIndex posição do objeto
     * @return retorna um histórico
     */
    public Historico getHistorico(int rowIndex) {
        return this.listaHistorico.get(rowIndex);
    }

    /**
     * Método responsável por retornar a quantidade total de linhas na tabela
     *
     * @return quantidade total de linhas na tabela
     */
    @Override
    public int getRowCount() {
        return listaHistorico.size();
    }

    /**
     * Método responsável por retornar a quantidade total de colunas na tabela
     *
     * @return quantidade total de colunas na tabela
     */
    @Override
    public int getColumnCount() {
        return (colunas.length);
    }

    /**
     * Método responsável por retornar um valor da tabela
     *
     * @param rowIndex linha da tabela
     * @param columnIndex coluna da tabela
     * @return retornar um valor da tabela
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return listaHistorico.get(rowIndex).getId();
            case 1:
                return listaHistorico.get(rowIndex).getDescricao();
            case 2:
                return Uteis.converteData("dd/MM/yyyy HH:mm", listaHistorico.get(rowIndex).getData());
            case 3:
                return listaHistorico.get(rowIndex).getPessoa().getNome();
            default: {
                return null;
            }
        }
    }

    /**
     * Método responsável por retornar um valor da tabela
     *
     * @param rowIndex linha da tabela
     * @param nomeColuna nome da coluna da tabela
     * @return retornar um valor da tabela
     */
    public Object getValueAt(int rowIndex, String nomeColuna) {
        switch (nomeColuna) {
            case "ID":
                return listaHistorico.get(rowIndex).getId();
            case "Descrição":
                return listaHistorico.get(rowIndex).getDescricao();
            case "Data":
                return Uteis.converteData("dd/MM/yyyy HH:mm", listaHistorico.get(rowIndex).getData());
            case "Funcionário":
                return listaHistorico.get(rowIndex).getPessoa().getNome();
            default: {
                return null;
            }
        }
    }

    /**
     * Método responsável por retornar um nome de coluna
     *
     * @param columnIndex número da coluna
     * @return retorna um nome de coluna
     */
    @Override
    public String getColumnName(int columnIndex) {
        return this.colunas[columnIndex];
    }

    /**
     * Método responsável por configurar a JTable
     *
     * @param tbl tabela que sofrerá as configurações
     */
    public void configurarTableModel(JTable tbl) {         //cada Caracter tem 7,3125 de width.
        tbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tbl.doLayout();

        for (int i = 0; i < tbl.getColumnCount(); i++) {

            switch (tbl.getColumnModel().getColumn(i).getHeaderValue().toString()) {
                case "ID": {
                    tbl.getColumnModel().getColumn(i).setMaxWidth((int) Math.round(6 * 7.3125));
                    break;
                }
                case "Data": {
                    tbl.getColumnModel().getColumn(i).setMaxWidth((int) Math.round(18 * 7.3125));
                    tbl.getColumnModel().getColumn(i).setMinWidth((int) Math.round(18 * 7.3125));
                    break;
                }

                case "Funcionário": {
                    tbl.getColumnModel().getColumn(i).setMaxWidth((int) Math.round(30 * 7.3125));
                    tbl.getColumnModel().getColumn(i).setMinWidth((int) Math.round(30 * 7.3125));
                    break;
                }
            }
        }
    }

    /**
     * Método responsável por retornar o tipo de cada coluna
     *
     * @param column número da coluna
     * @return retorna a classe da coluna em questão
     */
    @Override
    public Class getColumnClass(int column) {
        switch (column) {
            case 0:
                return int.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            default:
                return null;
        }
    }

    /**
     * Método responsável por informar se uma célula é editável ou não
     *
     * @param row número da linha
     * @param col número da coluna
     * @return retorna true ou false
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }
}
