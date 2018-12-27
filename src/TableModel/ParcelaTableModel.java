/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModel;

import Geral.Uteis;
import Model.Parcela;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

/**
 * Classe que herda de AbstractTableModel e tem como objeto gerenciar as
 * informações de uma JTable relacionada as parcelas da aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public class ParcelaTableModel extends AbstractTableModel {

    /**
     * ArrayList que armazena todos os objetos de parcela da JTable
     */
    private ArrayList<Parcela> listaParcela;
    /**
     * ArrayList que armazena as colunas da JTable
     */
    private String[] colunas = {"ID", "Dt. Vencimento", "Descrição", "Valor", "Status"};

    /**
     * Construtor da classe
     */
    public ParcelaTableModel() {
        this.listaParcela = new ArrayList<>();
        fireTableDataChanged();
    }

    /**
     * Método responsável por adicionar um parcela na JTable
     *
     * @param p parcela que será inserida
     */
    public void addParcela(Parcela p) {
        this.listaParcela.add(p);
        fireTableDataChanged();
    }

    /**
     * Método responsável por adicionar um ArrayList
     *
     * @param listaParcela arrayList que será adicionado
     */
    public void addAll(ArrayList<Parcela> listaParcela) {
        this.listaParcela.addAll(listaParcela);
        fireTableDataChanged();
    }

    /**
     * Método responsável por remover um parcela
     *
     * @param p parcela em questão
     */
    public void removerParcela(Parcela p) {
        listaParcela.remove(p);
        fireTableDataChanged();
    }

    /**
     * Método responsável por remover todos os elementos da JTable
     */
    public void removeAll() {
        listaParcela.removeAll(listaParcela);
        fireTableDataChanged();
    }

    /**
     * Método responsável por retornar todos os objetos
     *
     * @return retorna um ArrayList de objetos
     */
    public ArrayList<Parcela> getAll() {
        return listaParcela;
    }

    /**
     * Método responsável por retornar um objeto
     *
     * @param rowIndex posição do objeto
     * @return retorna uma parcela
     */
    public Parcela getParcela(int rowIndex) {
        return this.listaParcela.get(rowIndex);
    }

    /**
     * Método responsável por retornar a quantidade total de linhas na tabela
     *
     * @return quantidade total de linhas na tabela
     */
    @Override
    public int getRowCount() {
        return listaParcela.size();
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
     * @return retorna um valor da tabela
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0: {
                int id = listaParcela.get(rowIndex).getId();

                if (id <= 0) {
                    return "";
                }

                return id;
            }
            case 1:
                return Uteis.converteData("dd/MM/yyyy", listaParcela.get(rowIndex).getDataVencimento());
            case 2:
                return listaParcela.get(rowIndex).getDescricao();
            case 3:
                return listaParcela.get(rowIndex).getValor();
            case 4:
                return listaParcela.get(rowIndex).getStatus();

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
     * @return retorna um valor da tabela
     */
    public Object getValueAt(int rowIndex, String nomeColuna) {
        switch (nomeColuna) {
            case "ID": {
                int id = listaParcela.get(rowIndex).getId();

                if (id <= 0) {
                    return "";
                }

                return id;
            }
            case "Dt. Vencimento":
                return Uteis.converteData("dd/MM/yyyy", listaParcela.get(rowIndex).getDataVencimento());
            case "Descrição":
                return listaParcela.get(rowIndex).getDescricao();
            case "Valor":
                return listaParcela.get(rowIndex).getValor();
            case "Status":
                return listaParcela.get(rowIndex).getStatus();
            default: {
                return null;
            }
        }
    }

    /**
     * Método responsável por inserir um valor na tabela
     *
     * @param rowIndex linha da tabela
     * @param nomeColuna nome da coluna
     * @param value valor que será inserido
     */
    public void setValueAt(int rowIndex, String nomeColuna, Object value) {
        switch (nomeColuna) {
            case "ID": {
                listaParcela.get(rowIndex).setId((Integer.parseInt(value.toString())));
                break;
            }
            case "Dt. Vencimento": {
                listaParcela.get(rowIndex).setDataVencimento((Date) value);
                break;
            }
            case "Descrição": {
                listaParcela.get(rowIndex).setDescricao(value.toString());
                break;
            }
            case "Status": {
                listaParcela.get(rowIndex).setStatus(value.toString());
                break;
            }
            case "valor": {
                listaParcela.get(rowIndex).setValor((Double) value);
                break;
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
     * Método responsável por realizar um update em um objeto da tabela
     *
     * @param p1 objeto que substituirá o antigo
     */
    public void UpdateObject(Parcela p1) {
        int contador = 0;
        for (Parcela p2 : listaParcela) {
            if (p1.getId() == p2.getId()) {
                listaParcela.set(contador, p1);
                break;
            }
            contador++;
        }
        fireTableDataChanged();
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

                case "Status": {
                    tbl.getColumnModel().getColumn(i).setMaxWidth((int) Math.round(12 * 7.3125));
                    break;
                }

                case "Dt. Vencimento": {
                    tbl.getColumnModel().getColumn(i).setMaxWidth((int) Math.round(15 * 7.3125));
                    break;
                }

                case "Descrição": {
                    tbl.getColumnModel().getColumn(i).setMaxWidth((int) Math.round(50 * 7.3125));
                    break;
                }

                case "Valor": {
                    tbl.getColumnModel().getColumn(i).setMaxWidth((int) Math.round(10 * 7.3125));
                    break;
                }

                default: {
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
            case 4:
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
