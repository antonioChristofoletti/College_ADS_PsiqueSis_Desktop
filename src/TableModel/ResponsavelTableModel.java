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
 * informações de uma JTable relacionada aos responsáveis da aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public class ResponsavelTableModel extends AbstractTableModel {

    /**
     * ArrayList que armazena todos os objetos de responsáveis da JTable
     */
    private ArrayList<Responsavel> listaResponsavel;
    /**
     * ArrayList que armazena as colunas da JTable
     */
    private String[] colunas = {"ID", "RG", "CPF", "Nome", "Nome Login", "Telefone 1", "Cidade", "Logradouro", "Bairro", "Status"};

    /**
     * Construtor da classe
     */
    public ResponsavelTableModel() {
        this.listaResponsavel = new ArrayList<>();
        fireTableDataChanged();
    }

    /**
     * Método responsável por adicionar um responsável na JTable
     *
     * @param r responsável que será inserida
     */
    public void addFuncionario(Responsavel r) {
        this.listaResponsavel.add(r);
        fireTableDataChanged();
    }

    /**
     * Método responsável por adicionar um ArrayList
     *
     * @param listaFuncionario arrayList que será adicionado
     */
    public void addAll(ArrayList<Responsavel> listaFuncionario) {
        this.listaResponsavel.addAll(listaFuncionario);
        fireTableDataChanged();
    }

    /**
     * Método responsável por remover um plano
     *
     * @param r responsável em questão
     */
    public void removerFuncionario(Responsavel r) {
        listaResponsavel.remove(r);
        fireTableDataChanged();
    }

    /**
     * Método responsável por remover todos os elementos da JTable
     */
    public void removeAll() {
        listaResponsavel.removeAll(listaResponsavel);
        fireTableDataChanged();
    }

    /**
     * Método responsável por retornar todos os objetos
     *
     * @return retorna um ArrayList de objetos
     */
    public ArrayList<Responsavel> getAll() {
        return listaResponsavel;
    }

    /**
     * Método responsável por retornar um objeto
     *
     * @param rowIndex posição do objeto
     * @return retorna uma atividade
     */
    public Responsavel getResponsavel(int rowIndex) {
        return this.listaResponsavel.get(rowIndex);
    }

    /**
     * Método responsável por retornar a quantidade total de linhas na tabela
     *
     * @return quantidade total de linhas na tabela
     */
    @Override
    public int getRowCount() {
        return listaResponsavel.size();
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
                return listaResponsavel.get(rowIndex).getIdEspecifico();
            }
            case 1: {
                return Uteis.setaMascara(listaResponsavel.get(rowIndex).getRg(), "##.###.###.##");
            }
            case 2:
                return Uteis.setaMascara(listaResponsavel.get(rowIndex).getCpf(), "###.###.###.##");
            case 3:
                return listaResponsavel.get(rowIndex).getNome();
            case 4:
                return listaResponsavel.get(rowIndex).getNomeLogin();
            case 5:
                return Uteis.setaMascara(listaResponsavel.get(rowIndex).getTelefone1(), "(##) #### - #####");
            case 6:
                return listaResponsavel.get(rowIndex).getCidade();
            case 7:
                return listaResponsavel.get(rowIndex).getLogradouro();
            case 8:
                return listaResponsavel.get(rowIndex).getBairro();
            case 9:
                return listaResponsavel.get(rowIndex).getStatus();
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
                return listaResponsavel.get(rowIndex).getIdEspecifico();
            }
            case "RG": {
                return Uteis.setaMascara(listaResponsavel.get(rowIndex).getRg(), "##.###.###.##");
            }
            case "CPF":
                return Uteis.setaMascara(listaResponsavel.get(rowIndex).getCpf(), "###.###.###.##");
            case "Nome":
                return listaResponsavel.get(rowIndex).getNome();
            case "Nome Login":
                return listaResponsavel.get(rowIndex).getNomeLogin();
            case "Telefone 1":
                return Uteis.setaMascara(listaResponsavel.get(rowIndex).getTelefone1(), "(##) #### - #####");
            case "Cidade":
                return listaResponsavel.get(rowIndex).getCidade();
            case "Logradouro":
                return listaResponsavel.get(rowIndex).getLogradouro();
            case "Bairro":
                return listaResponsavel.get(rowIndex).getBairro();
            case "Status":
                return listaResponsavel.get(rowIndex).getStatus();
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
                listaResponsavel.get(rowIndex).setIdEspecifico((int) value);
                break;
            }
            case "RG": {
                listaResponsavel.get(rowIndex).setRg((String) value);
                break;
            }
            case "CPF": {
                listaResponsavel.get(rowIndex).setCpf((String) value);
                break;
            }
            case "Nome": {
                listaResponsavel.get(rowIndex).setNome((String) value);
                break;
            }
            case "Nome Login": {
                listaResponsavel.get(rowIndex).setNomeLogin((String) value);
                break;
            }
            case "Telefone 1": {
                listaResponsavel.get(rowIndex).setTelefone1((String) value);
                break;
            }
            case "Cidade": {
                listaResponsavel.get(rowIndex).setCidade((String) value);
                break;
            }
            case "Logradouro": {
                listaResponsavel.get(rowIndex).setLogradouro((String) value);
                break;
            }
            case "Bairro": {
                listaResponsavel.get(rowIndex).setBairro((String) value);
                break;
            }
            case "Status": {
                listaResponsavel.get(rowIndex).setStatus((String) value);
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
    public void UpdateObject(Responsavel r1) {
        int contador = 0;
        for (Responsavel r2 : listaResponsavel) {
            if (r1.getIdEspecifico() == r2.getIdEspecifico()) {
                listaResponsavel.set(contador, r1);
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

                String status = ((ResponsavelTableModel) table.getModel()).getValueAt(row, "Status").toString();

                if (isSelected) {
                    return c;
                }

                if (column == 9) {
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
    public Class getColumnClass(int column
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
