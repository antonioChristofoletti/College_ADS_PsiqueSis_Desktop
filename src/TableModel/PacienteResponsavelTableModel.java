/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModel;

import Geral.CoresTabela;
import Geral.Uteis;
import Model.Responsavel;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Classe que herda de AbstractTableModel e tem como objeto gerenciar as
 * informações de uma JTable relacionada a responsáveis de pacientes
 *
 * @author Antonio Lucas Christofoletti
 */
public class PacienteResponsavelTableModel extends AbstractTableModel {

    /**
     * ArrayList que armazena todos os objetos de responsáveis da JTable
     */
    private ArrayList<Responsavel> listaResponsaveis;
    /**
     * ArrayList que armazena as colunas da JTable
     */
    private String[] colunas = {"ID", "Nome", "Telefone 1", "Telefone 2", "Parentesco", "Status"};

    /**
     * Construtor da classe
     */
    public PacienteResponsavelTableModel() {
        this.listaResponsaveis = new ArrayList<>();
        fireTableDataChanged();
    }

    /**
     * Método responsável por adicionar um responsável na JTable
     *
     * @param r responsável que será inserido
     * @throws java.lang.Exception disparada caso o responsável já exista
     */
    public void addResponsavel(Responsavel r) throws Exception {
        if (responsavelExiste(r)) {
            throw new Exception("Responsável já foi inserido");
        }

        this.listaResponsaveis.add(r);
        fireTableDataChanged();
    }

    /**
     * Método responsável por adicionar um ArrayList
     *
     * @param lr arrayList que será adicionado
     */
    public void addAll(ArrayList<Responsavel> lr) {
        this.listaResponsaveis.addAll(lr);
        fireTableDataChanged();
    }

    /**
     * Método responsável por retornar todos os objetos
     *
     * @return retorna um ArrayList de objetos
     */
    public ArrayList<Responsavel> getAll() {
        return listaResponsaveis;
    }

    /**
     * Método responsável por remover um responsável
     *
     * @param r responsável em questão
     */
    public void removerResponsavel(Responsavel r) {
        listaResponsaveis.remove(r);
        fireTableDataChanged();
    }

    /**
     * Método responsável por remover todos os elementos da JTable
     */
    public void removeAll() {
        listaResponsaveis.removeAll(listaResponsaveis);
        fireTableDataChanged();
    }

    /**
     * Método responsável por retornar um objeto
     *
     * @param rowIndex posição do objeto
     * @return retorna um responsável
     */
    public Responsavel getResponsavel(int rowIndex) {
        return this.listaResponsaveis.get(rowIndex);
    }

    /**
     * Método responsável por retornar a quantidade total de linhas na tabela
     *
     * @return quantidade total de linhas na tabela
     */
    @Override
    public int getRowCount() {
        return listaResponsaveis.size();
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
            case 0:
                return listaResponsaveis.get(rowIndex).getIdEspecifico();
            case 1:
                return listaResponsaveis.get(rowIndex).getNome();
            case 2:
                return Uteis.setaMascara(listaResponsaveis.get(rowIndex).getTelefone1(), "(##) #####-####");
            case 3:
                return Uteis.setaMascara(listaResponsaveis.get(rowIndex).getTelefone2(), "(##) #####-####");
            case 4:
                return listaResponsaveis.get(rowIndex).getParentesco();
            case 5:
                return listaResponsaveis.get(rowIndex).getStatus();
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
            case "ID":
                return listaResponsaveis.get(rowIndex).getIdEspecifico();
            case "Nome":
                return listaResponsaveis.get(rowIndex).getNome();
            case "Telefone 1":
                return Uteis.setaMascara(listaResponsaveis.get(rowIndex).getTelefone1(), "(##) #####-####");
            case "Telefone 2":
                return Uteis.setaMascara(listaResponsaveis.get(rowIndex).getTelefone2(), "(##) #####-####");
            case "Parentesco":
                return listaResponsaveis.get(rowIndex).getParentesco();
            case "Status":
                return listaResponsaveis.get(rowIndex).getStatus();
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
                listaResponsaveis.get(rowIndex).setIdEspecifico((Integer.parseInt(value.toString())));
                break;
            }
            case "Nome": {
                listaResponsaveis.get(rowIndex).setNome(value.toString());
                break;
            }
            case "Telefone 1": {
                listaResponsaveis.get(rowIndex).setTelefone1(value.toString());
                break;
            }
            case "Telefone 2": {
                listaResponsaveis.get(rowIndex).setTelefone2(value.toString());
                break;
            }
            case "Parentesco": {
                listaResponsaveis.get(rowIndex).setParentesco(value.toString());
                break;
            }
            case "Status": {
                listaResponsaveis.get(rowIndex).setStatus(value.toString());
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
     * @param r1 objeto que substituirá o antigo
     */
    public void updateObject(Responsavel r1) {
        int contador = 0;
        for (Responsavel p2 : listaResponsaveis) {
            if (r1.getIdEspecifico() == p2.getIdEspecifico()) {
                listaResponsaveis.set(contador, r1);
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
                case "Telefone 1": {
                    tbl.getColumnModel().getColumn(i).setMaxWidth((int) Math.round(17 * 7.3125));
                    tbl.getColumnModel().getColumn(i).setMinWidth((int) Math.round(17 * 7.3125));
                    break;
                }
                case "Telefone 2": {
                    tbl.getColumnModel().getColumn(i).setMaxWidth((int) Math.round(17 * 7.3125));
                    tbl.getColumnModel().getColumn(i).setMinWidth((int) Math.round(17 * 7.3125));
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

                String status = ((PacienteResponsavelTableModel) table.getModel()).getValueAt(row, "Status").toString();

                if (isSelected) {
                    return c;
                }

                if (status.equals("Inativo") && column == 5) {
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
     * Método responsável por verificar se um responsável já existe ou não na
     * JTable
     *
     * @param r1 responsável que será verificada
     * @return retorna true ou false
     */
    public boolean responsavelExiste(Responsavel r1) {
        for (Responsavel p2 : listaResponsaveis) {
            if (r1.getIdEspecifico() == p2.getIdEspecifico()) {
                return true;
            }
        }
        return false;
    }
}
