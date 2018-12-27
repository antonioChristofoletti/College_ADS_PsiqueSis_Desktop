/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModel;

import Geral.CoresTabela;
import Geral.Uteis;
import Model.Localizacao;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Classe que herda de AbstractTableModel e tem como objeto gerenciar as
 * informações de uma JTable relacionada as localizações dos funcionários
 *
 * @author Antonio Lucas Christofoletti
 */
public class LocalizacaoTableModel extends AbstractTableModel {

    /**
     * ArrayList que armazena todos os objetos de localização da JTable
     */
    private ArrayList<Localizacao> listaLocalizacao;
    /**
     * ArrayList que armazena as colunas da JTable
     */
    private String[] colunas = {"ID", "Nome", "Telefone 1", "Descrição", "Cidade", "Logradouro", "Bairro", "Status"};

    /**
     * Construtor da classe
     */
    public LocalizacaoTableModel() {
        this.listaLocalizacao = new ArrayList<>();
        fireTableDataChanged();
    }

    /**
     * Método responsável por adicionar um localização na JTable
     *
     * @param l localização que será inserido
     */
    public void addLocalizacao(Localizacao l) {
        this.listaLocalizacao.add(l);
        fireTableDataChanged();
    }

    /**
     * Método responsável por adicionar um ArrayList
     *
     * @param listaLocalizacao arrayList que será adicionado
     */
    public void addAll(ArrayList<Localizacao> listaLocalizacao) {
        this.listaLocalizacao.addAll(listaLocalizacao);
        fireTableDataChanged();
    }

    /**
     * Método responsável por remover um localização
     *
     * @param l localização em questão
     */
    public void removerLocalizacao(Localizacao l) {
        listaLocalizacao.remove(l);
        fireTableDataChanged();
    }

    /**
     * Método responsável por remover todos os elementos da JTable
     */
    public void removeAll() {
        listaLocalizacao.removeAll(listaLocalizacao);
        fireTableDataChanged();
    }

    /**
     * Método responsável por retornar todos os objetos
     *
     * @return retorna um ArrayList de objetos
     */
    public ArrayList<Localizacao> getAll() {
        return listaLocalizacao;
    }

    /**
     * Método responsável por retornar um objeto
     *
     * @param rowIndex posição do objeto
     * @return retorna uma localização
     */
    public Localizacao getLocalizacao(int rowIndex) {
        return this.listaLocalizacao.get(rowIndex);
    }

    /**
     * Método responsável por retornar a quantidade total de linhas na tabela
     *
     * @return quantidade total de linhas na tabela
     */
    @Override
    public int getRowCount() {
        return listaLocalizacao.size();
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
                return listaLocalizacao.get(rowIndex).getId();
            case 1:
                return listaLocalizacao.get(rowIndex).getNome();
            case 2:
                return Uteis.setaMascara(listaLocalizacao.get(rowIndex).getTelefone1(), "(##) #### - #####");
            case 3:
                return listaLocalizacao.get(rowIndex).getDescricao();
            case 4:
                return listaLocalizacao.get(rowIndex).getCidade();
            case 5:
                return listaLocalizacao.get(rowIndex).getLogradouro();
            case 6:
                return listaLocalizacao.get(rowIndex).getBairro();
            case 7:
                return listaLocalizacao.get(rowIndex).getStatus();
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
                return listaLocalizacao.get(rowIndex).getId();
            }
            case "Nome":
                return listaLocalizacao.get(rowIndex).getNome();
            case "Telefone 1":
                return Uteis.setaMascara(listaLocalizacao.get(rowIndex).getTelefone1(), "(##) #### - #####");
            case "Descrição":
                return listaLocalizacao.get(rowIndex).getDescricao();
            case "Cidade":
                return listaLocalizacao.get(rowIndex).getCidade();
            case "Logradouro":
                return listaLocalizacao.get(rowIndex).getLogradouro();
            case "Bairro":
                return listaLocalizacao.get(rowIndex).getBairro();
            case "Status":
                return listaLocalizacao.get(rowIndex).getStatus();
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
                listaLocalizacao.get(rowIndex).setId((int) value);
                break;
            }
            case "Nome": {
                listaLocalizacao.get(rowIndex).setNome((String) value);
                break;
            }
            case "Telefone 1": {
                listaLocalizacao.get(rowIndex).setTelefone1((String) value);
                break;
            }
            case "Descrição": {
                listaLocalizacao.get(rowIndex).setDescricao((String) value);
                break;
            }
            case "Cidade": {
                listaLocalizacao.get(rowIndex).setCidade((String) value);
                break;
            }
            case "Logradouro": {
                listaLocalizacao.get(rowIndex).setLogradouro((String) value);
                break;
            }
            case "Bairro": {
                listaLocalizacao.get(rowIndex).setBairro((String) value);
                break;
            }
            case "Status": {
                listaLocalizacao.get(rowIndex).setStatus((String) value);
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
     * @param l1 objeto que substituirá o antigo
     */
    public void UpdateObject(Localizacao l1) {
        int contador = 0;
        for (Localizacao l2 : listaLocalizacao) {
            if (l1.getId() == l2.getId()) {
                listaLocalizacao.set(contador, l1);
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

                String status = ((LocalizacaoTableModel) table.getModel()).getValueAt(row, "Status").toString();

                if (isSelected) {
                    return c;
                }

                if (column == 7) {
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
