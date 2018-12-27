/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModel;

import Geral.CoresTabela;
import Model.Plano;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Classe que herda de AbstractTableModel e tem como objeto gerenciar as
 * informações de uma JTable relacionada aos planos da aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public class PlanoTableModel extends AbstractTableModel {

    /**
     * ArrayList que armazena todos os objetos de planos da JTable
     */
    private ArrayList<Plano> listaPlano;
    /**
     * ArrayList que armazena as colunas da JTable
     */
    private String[] colunas = {"ID", "Nome Instituição", "Qtd. Consultas", "Descrição", "Status"};

    /**
     * Construtor da classe
     */
    public PlanoTableModel() {
        this.listaPlano = new ArrayList<>();
        fireTableDataChanged();
    }

    /**
     * Método responsável por adicionar um plano na JTable
     *
     * @param p plano que será inserida
     */
    public void addAtividade(Plano p) {
        this.listaPlano.add(p);
        fireTableDataChanged();
    }

    /**
     * Método responsável por adicionar um ArrayList
     *
     * @param lp arrayList que será adicionado
     */
    public void addAll(ArrayList<Plano> lp) {
        this.listaPlano.addAll(lp);
        fireTableDataChanged();
    }

    /**
     * Método responsável por retornar todos os objetos
     *
     * @return retorna um ArrayList de objetos
     */
    public ArrayList<Plano> getAll() {
        return listaPlano;
    }

    /**
     * Método responsável por remover um plano
     *
     * @param p plano em questão
     */
    public void removerPlano(Plano p) {
        listaPlano.remove(p);
        fireTableDataChanged();
    }

    /**
     * Método responsável por remover todos os elementos da JTable
     */
    public void removeAll() {
        listaPlano.removeAll(listaPlano);
        fireTableDataChanged();
    }

    /**
     * Método responsável por retornar um objeto
     *
     * @param rowIndex posição do objeto
     * @return retorna uma atividade
     */
    public Plano getPlano(int rowIndex) {
        return this.listaPlano.get(rowIndex);
    }

    /**
     * Método responsável por retornar a quantidade total de linhas na tabela
     *
     * @return quantidade total de linhas na tabela
     */
    @Override
    public int getRowCount() {
        return listaPlano.size();
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
                return listaPlano.get(rowIndex).getId();
            case 1:
                return listaPlano.get(rowIndex).getNomeInstituicao();
            case 2:
                return listaPlano.get(rowIndex).getQuantiConsultas();
            case 3:
                return listaPlano.get(rowIndex).getDescricao();
            case 4:
                return listaPlano.get(rowIndex).getStatus();
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
                return listaPlano.get(rowIndex).getId();
            case "Nome Instituição":
                return listaPlano.get(rowIndex).getNomeInstituicao();
            case "Qtd. Consultas":
                return listaPlano.get(rowIndex).getQuantiConsultas();
            case "Descrição":
                return listaPlano.get(rowIndex).getDescricao();
            case "Status":
                return listaPlano.get(rowIndex).getStatus();
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
                listaPlano.get(rowIndex).setId((Integer.parseInt(value.toString())));
                break;
            }
            case "Nome Instituição": {
                listaPlano.get(rowIndex).setNomeInstituicao(value.toString());
                break;
            }
            case "Qtd. Consultas": {
                listaPlano.get(rowIndex).setNomeInstituicao(value.toString());
                break;
            }
            case "Descrição": {
                listaPlano.get(rowIndex).setDescricao(value.toString());
                break;
            }
            case "Status": {
                listaPlano.get(rowIndex).setStatus(value.toString());
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
    public void UpdateObject(Plano p1) {
        int contador = 0;
        for (Plano p2 : listaPlano) {
            if (p1.getId() == p2.getId()) {
                listaPlano.set(contador, p1);
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
                    tbl.getColumnModel().getColumn(i).setMinWidth((int) Math.round(6 * 7.3125));
                    break;
                }
                case "Status": {
                    tbl.getColumnModel().getColumn(i).setMaxWidth((int) Math.round(7 * 7.3125));
                    tbl.getColumnModel().getColumn(i).setMinWidth((int) Math.round(7 * 7.3125));
                    break;
                }
                case "Qtd. Consultas": {
                    tbl.getColumnModel().getColumn(i).setMaxWidth((int) Math.round(12 * 7.3125));
                    tbl.getColumnModel().getColumn(i).setMinWidth((int) Math.round(12 * 7.3125));
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

                String status = ((PlanoTableModel) table.getModel()).getValueAt(row, "Status").toString();

                if (isSelected) {
                    return c;
                }

                if (status.equals("Inativo") && column == 4) {
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
