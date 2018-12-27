/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModel;

import Geral.CoresTabela;
import Model.Atividade;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Classe que herda de AbstractTableModel e tem como objeto gerenciar as
 * informações de uma JTable relacionada as atividades
 *
 * @author Antonio Lucas Christofoletti
 */
public class AtividadeTableModel extends AbstractTableModel {

    /**
     * ArrayList que armazena todos os objetos de atividades da JTable
     */
    private ArrayList<Atividade> listaAtividades;
    /**
     * ArrayList que armazena as colunas da JTable
     */
    private String[] colunas = {"ID", "Nome", "Descrição", "Status"};

    /**
     * Construtor da classe
     */
    public AtividadeTableModel() {
        this.listaAtividades = new ArrayList<>();
        fireTableDataChanged();
    }

    /**
     * Método responsável por adicionar uma atividade na JTable
     *
     * @param a atividade que será inserida
     * @throws java.lang.Exception disparada caso a atividade já exista na
     * tabela
     */
    public void addAtividade(Atividade a) throws Exception {
        if (atividadeExiste(a)) {
            throw new Exception("Atividade já foi inserida");
        }
        this.listaAtividades.add(a);
        fireTableDataChanged();
    }

    /**
     * Método responsável por adicionar um ArrayList
     *
     * @param la arrayList que será adicionado
     */
    public void addAll(ArrayList<Atividade> la) {
        this.listaAtividades.addAll(la);
        fireTableDataChanged();
    }

    /**
     * Método responsável por retornar todos os objetos
     *
     * @return retorna um ArrayList de objetos
     */
    public ArrayList<Atividade> getAll() {
        return listaAtividades;
    }

    /**
     * Método responsável por remover uma atividade
     *
     * @param a atividade em questão
     */
    public void removerAtividade(Atividade a) {
        listaAtividades.remove(a);
        fireTableDataChanged();
    }

    /**
     * Método responsável por remover todos os elementos da JTable
     */
    public void removeAll() {
        listaAtividades.removeAll(listaAtividades);
        fireTableDataChanged();
    }

    /**
     * Método responsável por retornar um objeto
     *
     * @param rowIndex posição do objeto
     * @return retorna uma atividade
     */
    public Atividade getAtividade(int rowIndex) {
        return this.listaAtividades.get(rowIndex);
    }

    /**
     * Método responsável por retornar a quantidade total de linhas na tabela
     *
     * @return quantidade total de linhas na tabela
     */
    @Override
    public int getRowCount() {
        return listaAtividades.size();
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
                return listaAtividades.get(rowIndex).getId();
            case 1:
                return listaAtividades.get(rowIndex).getNome();
            case 2:
                return listaAtividades.get(rowIndex).getDescricao();
            case 3:
                return listaAtividades.get(rowIndex).getStatus();
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
                return listaAtividades.get(rowIndex).getId();
            case "Nome":
                return listaAtividades.get(rowIndex).getDescricao();
            case "Descrição":
                return listaAtividades.get(rowIndex).getDescricao();
            case "Status":
                return listaAtividades.get(rowIndex).getStatus();
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
                listaAtividades.get(rowIndex).setId((Integer.parseInt(value.toString())));
                break;
            }
            case "Nome": {
                listaAtividades.get(rowIndex).setNome(value.toString());
                break;
            }
            case "Descrição": {
                listaAtividades.get(rowIndex).setDescricao(value.toString());
                break;
            }
            case "Status": {
                listaAtividades.get(rowIndex).setStatus(value.toString());
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
     * @param a1 objeto que substituirá o antigo
     */
    public void UpdateObject(Atividade a1) {
        int contador = 0;
        for (Atividade a2 : listaAtividades) {
            if (a1.getId() == a2.getId()) {
                listaAtividades.set(contador, a1);
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
                    tbl.getColumnModel().getColumn(i).setMaxWidth((int) Math.round(7 * 7.3125));
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

                String status = ((AtividadeTableModel) table.getModel()).getValueAt(row, "Status").toString();

                if (isSelected) {
                    return c;
                }

                if (status.equals("Inativo") && column == 3) {
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

    /**
     * Método responsável por verificar se uma atividade já foi inserida na
     * tabela
     *
     * @param a1 atividade que será verificada
     * @return retorna true ou false
     */
    public boolean atividadeExiste(Atividade a1) {
        for (Atividade l2 : listaAtividades) {
            if (a1.getId() == l2.getId()) {
                return true;
            }
        }
        return false;
    }
}
