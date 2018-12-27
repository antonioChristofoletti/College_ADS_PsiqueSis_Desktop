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
 * informações de uma JTable relacionada as localizações de pacientes
 *
 * @author Antonio Lucas Christofoletti
 */
public class PacienteLocalizacoesTableModel extends AbstractTableModel {

    /**
     * ArrayList que armazena todos os objetos de localização da JTable
     */
    private ArrayList<Localizacao> listaLocalizacoes;
    /**
     * ArrayList que armazena as colunas da JTable
     */
    private String[] colunas = {"ID", "Nome", "Telefone 1", "Telefone 2", "Cidade", "Bairro", "Logradouro", "status"};

    /**
     * Construtor da classe
     */
    public PacienteLocalizacoesTableModel() {
        this.listaLocalizacoes = new ArrayList<>();
        fireTableDataChanged();
    }

    /**
     * Método responsável por adicionar um localização na JTable
     *
     * @param l localização que será inserido
     * @throws java.lang.Exception disparada caso uma localização já exista na
     * JTable
     */
    public void addLocalizacao(Localizacao l) throws Exception {
        if (localizacaoExiste(l)) {
            throw new Exception("Localização já foi inserido");
        }

        this.listaLocalizacoes.add(l);
        fireTableDataChanged();
    }

    /**
     * Método responsável por adicionar um ArrayList
     *
     * @param lr arrayList que será adicionado
     */
    public void addAll(ArrayList<Localizacao> lr) {
        this.listaLocalizacoes.addAll(lr);
        fireTableDataChanged();
    }

    /**
     * Método responsável por retornar todos os objetos
     *
     * @return retorna um ArrayList de objetos
     */
    public ArrayList<Localizacao> getAll() {
        return listaLocalizacoes;
    }

    /**
     * Método responsável por remover um localização
     *
     * @param l localização em questão
     */
    public void removerLocalizacao(Localizacao l) {
        listaLocalizacoes.remove(l);
        fireTableDataChanged();
    }

    /**
     * Método responsável por remover todos os elementos da JTable
     */
    public void removeAll() {
        listaLocalizacoes.removeAll(listaLocalizacoes);
        fireTableDataChanged();
    }

    /**
     * Método responsável por retornar um objeto
     *
     * @param rowIndex posição do objeto
     * @return retorna uma localização
     */
    public Localizacao getLocalizacao(int rowIndex) {
        return this.listaLocalizacoes.get(rowIndex);
    }

    /**
     * Método responsável por retornar a quantidade total de linhas na tabela
     *
     * @return quantidade total de linhas na tabela
     */
    @Override
    public int getRowCount() {
        return listaLocalizacoes.size();
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
                return listaLocalizacoes.get(rowIndex).getId();
            case 1:
                return listaLocalizacoes.get(rowIndex).getNome();
            case 2:
                return Uteis.setaMascara(listaLocalizacoes.get(rowIndex).getTelefone1(), "(##) #####-####");
            case 3:
                return Uteis.setaMascara(listaLocalizacoes.get(rowIndex).getTelefone2(), "(##) #####-####");
            case 4:
                return listaLocalizacoes.get(rowIndex).getCidade();
            case 5:
                return listaLocalizacoes.get(rowIndex).getBairro();
            case 6:
                return listaLocalizacoes.get(rowIndex).getLogradouro();
            case 7:
                return listaLocalizacoes.get(rowIndex).getStatus();
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
                return listaLocalizacoes.get(rowIndex).getId();
            case "Nome":
                return listaLocalizacoes.get(rowIndex).getNome();
            case "Telefone 1":
                return Uteis.setaMascara(listaLocalizacoes.get(rowIndex).getTelefone1(), "(##) #####-####");
            case "Telefone 2":
                return Uteis.setaMascara(listaLocalizacoes.get(rowIndex).getTelefone2(), "(##) #####-####");
            case "Cidade":
                return listaLocalizacoes.get(rowIndex).getCidade();
            case "Bairro":
                return listaLocalizacoes.get(rowIndex).getBairro();
            case "Logradouro":
                return listaLocalizacoes.get(rowIndex).getLogradouro();
            case "Status":
                return listaLocalizacoes.get(rowIndex).getStatus();
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
                listaLocalizacoes.get(rowIndex).setId((Integer.parseInt(value.toString())));
                break;
            }
            case "Nome": {
                listaLocalizacoes.get(rowIndex).setNome(value.toString());
                break;
            }
            case "Telefone 1": {
                listaLocalizacoes.get(rowIndex).setTelefone1(value.toString());
                break;
            }
            case "Telefone 2": {
                listaLocalizacoes.get(rowIndex).setTelefone2(value.toString());
                break;
            }

            case "Cidade": {
                listaLocalizacoes.get(rowIndex).setNome(value.toString());
                break;
            }
            case "Bairro": {
                listaLocalizacoes.get(rowIndex).setTelefone1(value.toString());
                break;
            }
            case "Logradouro": {
                listaLocalizacoes.get(rowIndex).setTelefone2(value.toString());
                break;
            }

            case "Status": {
                listaLocalizacoes.get(rowIndex).setStatus(value.toString());
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
    public void updateObject(Localizacao l1) {
        int contador = 0;
        for (Localizacao l2 : listaLocalizacoes) {
            if (l1.getId() == l2.getId()) {
                listaLocalizacoes.set(contador, l1);
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

                String status = ((PacienteLocalizacoesTableModel) table.getModel()).getValueAt(row, "Status").toString();

                if (isSelected) {
                    return c;
                }

                if (status.equals("Inativo") && column == 7) {
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
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    /**
     * Método responsável por verificar se uma localização já existe ou não
     *
     * @param l1 localização que será verificada
     * @return retorna true ou false
     */
    public boolean localizacaoExiste(Localizacao l1) {
        for (Localizacao l2 : listaLocalizacoes) {
            if (l1.getId() == l2.getId()) {
                return true;
            }
        }
        return false;
    }
}
