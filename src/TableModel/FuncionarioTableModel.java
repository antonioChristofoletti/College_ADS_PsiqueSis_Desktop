/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModel;

import Geral.CoresTabela;
import Geral.Uteis;
import Model.Funcionario;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Classe que herda de AbstractTableModel e tem como objeto gerenciar as
 * informações de uma JTable relacionada aos funcionários
 *
 * @author Antonio Lucas Christofoletti
 */
public class FuncionarioTableModel extends AbstractTableModel {

    /**
     * ArrayList que armazena todos os objetos de agendamento da JTable
     */
    private ArrayList<Funcionario> listaFuncionario;

    /**
     * ArrayList que armazena as colunas da JTable
     */
    private String[] colunas = {"ID", "RG", "CPF", "Nome", "Nome Login", "Telefone 1", "E-mail", "Cidade", "Logradouro", "Bairro", "Status"};

    /**
     * Construtor da classe
     */
    public FuncionarioTableModel() {
        this.listaFuncionario = new ArrayList<>();
        fireTableDataChanged();
    }

    /**
     * Método responsável por adicionar um funcionário na JTable
     *
     * @param f funcionário que será inserido
     */
    public void addFuncionario(Funcionario f) {
        this.listaFuncionario.add(f);
        fireTableDataChanged();
    }

    /**
     * Método responsável por adicionar um ArrayList
     *
     * @param listaFuncionario arrayList que será adicionado
     */
    public void addAll(ArrayList<Funcionario> listaFuncionario) {
        this.listaFuncionario.addAll(listaFuncionario);
        fireTableDataChanged();
    }

    /**
     * Método responsável por remover um funcionário
     *
     * @param f funcionário em questão
     */
    public void removerFuncionario(Funcionario f) {
        listaFuncionario.remove(f);
        fireTableDataChanged();
    }

    /**
     * Método responsável por remover todos os elementos da JTable
     */
    public void removeAll() {
        listaFuncionario.removeAll(listaFuncionario);
        fireTableDataChanged();
    }

    /**
     * Método responsável por retornar um objeto
     *
     * @param rowIndex posição do objeto
     * @return retorna um funcionário
     */
    public Funcionario getFuncionario(int rowIndex) {
        return this.listaFuncionario.get(rowIndex);
    }

    /**
     * Método responsável por retornar a quantidade total de linhas na tabela
     *
     * @return quantidade total de linhas na tabela
     */
    @Override
    public int getRowCount() {
        return listaFuncionario.size();
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
            case 0: {
                return listaFuncionario.get(rowIndex).getIdEspecifico();
            }
            case 1: {
                return Uteis.setaMascara(listaFuncionario.get(rowIndex).getRg(), "##.###.###.##");
            }
            case 2:
                return Uteis.setaMascara(listaFuncionario.get(rowIndex).getCpf(), "###.###.###.##");
            case 3:
                return listaFuncionario.get(rowIndex).getNome();
            case 4:
                return listaFuncionario.get(rowIndex).getNomeLogin();
            case 5:
                return Uteis.setaMascara(listaFuncionario.get(rowIndex).getTelefone1(), "(##) #### - #####");
            case 6:
                return listaFuncionario.get(rowIndex).getEmail();
            case 7:
                return listaFuncionario.get(rowIndex).getCidade();
            case 8:
                return listaFuncionario.get(rowIndex).getLogradouro();
            case 9:
                return listaFuncionario.get(rowIndex).getBairro();
            case 10:
                return listaFuncionario.get(rowIndex).getStatus();
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
            case "ID": {
                return listaFuncionario.get(rowIndex).getIdEspecifico();
            }
            case "RG": {
                return Uteis.setaMascara(listaFuncionario.get(rowIndex).getRg(), "##.###.###.##");
            }
            case "CPF":
                return Uteis.setaMascara(listaFuncionario.get(rowIndex).getCpf(), "###.###.###.##");
            case "Nome":
                return listaFuncionario.get(rowIndex).getNome();
            case "Nome Login":
                return listaFuncionario.get(rowIndex).getNomeLogin();
            case "Telefone 1":
                return Uteis.setaMascara(listaFuncionario.get(rowIndex).getTelefone1(), "(##) #### - #####");
            case "E-mail":
                return listaFuncionario.get(rowIndex).getEmail();
            case "Cidade":
                return listaFuncionario.get(rowIndex).getCidade();
            case "Logradouro":
                return listaFuncionario.get(rowIndex).getLogradouro();
            case "Bairro":
                return listaFuncionario.get(rowIndex).getBairro();
            case "Status":
                return listaFuncionario.get(rowIndex).getStatus();
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
                listaFuncionario.get(rowIndex).setIdEspecifico((int) value);
                break;
            }
            case "RG": {
                listaFuncionario.get(rowIndex).setRg((String) value);
                break;
            }
            case "CPF": {
                listaFuncionario.get(rowIndex).setCpf((String) value);
                break;
            }
            case "Nome": {
                listaFuncionario.get(rowIndex).setNome((String) value);
                break;
            }
            case "Nome Login": {
                listaFuncionario.get(rowIndex).setNomeLogin((String) value);
                break;
            }
            case "Telefone 1": {
                listaFuncionario.get(rowIndex).setTelefone1((String) value);
                break;
            }
            case "E-mail": {
                listaFuncionario.get(rowIndex).setEmail((String) value);
                break;
            }
            case "Cidade": {
                listaFuncionario.get(rowIndex).setCidade((String) value);
                break;
            }
            case "Logradouro": {
                listaFuncionario.get(rowIndex).setLogradouro((String) value);
                break;
            }
            case "Bairro": {
                listaFuncionario.get(rowIndex).setBairro((String) value);
                break;
            }
            case "Status": {
                listaFuncionario.get(rowIndex).setStatus((String) value);
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
     * @param f1 objeto que substituirá o antigo
     */
    public void UpdateObject(Funcionario f1) {
        int contador = 0;
        for (Funcionario f2 : listaFuncionario) {
            if (f1.getIdEspecifico() == f2.getIdEspecifico()) {
                listaFuncionario.set(contador, f1);
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
                    tbl.getColumnModel().getColumn(i).setMinWidth((int) Math.round(6 * 7.3125));
                    tbl.getColumnModel().getColumn(i).setMaxWidth((int) Math.round(6 * 7.3125));
                    break;
                }

                case "Status": {
                    tbl.getColumnModel().getColumn(i).setMinWidth((int) Math.round(7 * 7.3125));
                    tbl.getColumnModel().getColumn(i).setMaxWidth((int) Math.round(7 * 7.3125));
                    break;
                }

                case "RG": {
                    tbl.getColumnModel().getColumn(i).setMinWidth((int) Math.round(15 * 7.3125));
                    tbl.getColumnModel().getColumn(i).setMaxWidth((int) Math.round(15 * 7.3125));
                    break;
                }

                case "CPF": {
                    tbl.getColumnModel().getColumn(i).setMinWidth((int) Math.round(16 * 7.3125));
                    tbl.getColumnModel().getColumn(i).setMaxWidth((int) Math.round(16 * 7.3125));
                    break;
                }

                case "Telefone 1": {
                    tbl.getColumnModel().getColumn(i).setMinWidth((int) Math.round(18 * 7.3125));
                    tbl.getColumnModel().getColumn(i).setMaxWidth((int) Math.round(18 * 7.3125));
                    break;
                }

                case "Nome": {
                    tbl.getColumnModel().getColumn(i).setMinWidth((int) Math.round(15 * 7.3125));
                    break;
                }
            }
        }

        tbl.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                String status = ((FuncionarioTableModel) table.getModel()).getValueAt(row, "Status").toString();

                if (isSelected) {
                    return c;
                }

                if (column == 10) {
                    if (status.equals("Inativo")) {
                        setBackground(CoresTabela.corVermelha);
                    }
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
    public Class
            getColumnClass(int column
            ) {
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

            case 7:
                return String.class;
            case 8:
                return int.class;

            case 9:
                return String.class;

            case 10:
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
    public boolean isCellEditable(int row, int col
    ) {
        return false;
    }
}
