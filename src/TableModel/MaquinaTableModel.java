/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModel;

import Geral.CoresTabela;
import Geral.Uteis;
import Model.Maquina;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Classe que herda de AbstractTableModel e tem como objeto gerenciar as
 * informações de uma JTable relacionada as máquinas dos funcionários
 *
 * @author Antonio Lucas Christofoletti
 */
public class MaquinaTableModel extends AbstractTableModel {

    /**
     * ArrayList que armazena todos os objetos de máquina da JTable
     */
    private ArrayList<Maquina> listaMaquinas;
    /**
     * ArrayList que armazena as colunas da JTable
     */
    private String[] colunas = {"ID", "IP", "MAC", "Porta", "Dt. Acesso", "Usuário Conectado", "Conectado"};

    /**
     * Construtor da classe
     */
    public MaquinaTableModel() {
        this.listaMaquinas = new ArrayList<>();
        fireTableDataChanged();
    }

    /**
     * Método responsável por adicionar um ArrayList
     *
     * @param lm arrayList que será máquinas
     */
    public void addAll(ArrayList<Maquina> lm) {
        this.listaMaquinas.addAll(lm);
        fireTableDataChanged();
    }

    /**
     * Método responsável por remover todos os elementos da JTable
     */
    public void removeAll() {
        listaMaquinas.removeAll(listaMaquinas);
        fireTableDataChanged();
    }

    /**
     * Método responsável por retornar um objeto
     *
     * @param rowIndex posição do objeto
     * @return retorna uma máquina
     */
    public Maquina getMaquina(int rowIndex) {
        return this.listaMaquinas.get(rowIndex);
    }

    /**
     * Método responsável por retornar a quantidade total de linhas na tabela
     *
     * @return quantidade total de linhas na tabela
     */
    @Override
    public int getRowCount() {
        return listaMaquinas.size();
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
                return listaMaquinas.get(rowIndex).getId();
            case 1:
                return listaMaquinas.get(rowIndex).getIp();
            case 2:
                return listaMaquinas.get(rowIndex).getMac();
            case 3:
                return listaMaquinas.get(rowIndex).getPorta();
            case 4:
                return Uteis.converteData("dd/MM/yyyy hh:mm", listaMaquinas.get(rowIndex).getDataAcesso());
            case 5:
                return listaMaquinas.get(rowIndex).getP().getNome();
            case 6:
                return listaMaquinas.get(rowIndex).getStatus();
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
                return listaMaquinas.get(rowIndex).getId();
            case "IP":
                return listaMaquinas.get(rowIndex).getIp();
            case "MAC":
                return listaMaquinas.get(rowIndex).getMac();
            case "Porta":
                return listaMaquinas.get(rowIndex).getPorta();
            case "Dt. Acesso":
                return Uteis.converteData("dd/MM/yyyy hh:mm", listaMaquinas.get(rowIndex).getDataAcesso());
            case "Conectado":
                return listaMaquinas.get(rowIndex).getStatus();
            case "Usuário Conectado":
                return listaMaquinas.get(rowIndex).getP().getNome();
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

                case "IP": {
                    tbl.getColumnModel().getColumn(i).setMaxWidth((int) Math.round(26 * 7.3125));
                    tbl.getColumnModel().getColumn(i).setMinWidth((int) Math.round(26 * 7.3125));
                    break;
                }

                case "MAC": {
                    tbl.getColumnModel().getColumn(i).setMaxWidth((int) Math.round(21 * 7.3125));
                    tbl.getColumnModel().getColumn(i).setMinWidth((int) Math.round(21 * 7.3125));
                    break;
                }

                case "Porta": {
                    tbl.getColumnModel().getColumn(i).setMaxWidth((int) Math.round(8 * 7.3125));
                    tbl.getColumnModel().getColumn(i).setMinWidth((int) Math.round(8 * 7.3125));
                    break;
                }

                case "Dt. Acesso": {
                    tbl.getColumnModel().getColumn(i).setMaxWidth((int) Math.round(19 * 7.3125));
                    tbl.getColumnModel().getColumn(i).setMinWidth((int) Math.round(19 * 7.3125));
                    break;
                }

                case "Conectado": {
                    tbl.getColumnModel().getColumn(i).setMaxWidth((int) Math.round(10 * 7.3125));
                    tbl.getColumnModel().getColumn(i).setMinWidth((int) Math.round(10 * 7.3125));
                    break;
                }
            }
        }

        tbl.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int column) {

                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                String status = ((MaquinaTableModel) table.getModel()).getValueAt(row, "Conectado").toString();

                if (isSelected) {
                    return c;
                }

                if (status.equals("Inativo") && column == 6) {
                    c.setBackground(CoresTabela.corVermelha);
                } else {
                    setBackground(table.getBackground());
                    setForeground(table.getForeground());
                }

                return c;
            }
        });
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
            case 5:
                return String.class;
            case 6:
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
