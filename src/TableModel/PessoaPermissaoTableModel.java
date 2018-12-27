/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModel;

import Geral.CoresTabela;
import Model.Funcionario;
import Model.Paciente;
import Model.Pessoa;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Classe que herda de AbstractTableModel e tem como objeto gerenciar as
 * informações de uma JTable relacionada as permissões das pessoas da aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public class PessoaPermissaoTableModel extends AbstractTableModel {

    /**
     * ArrayList que armazena todos os objetos de permissão da JTable
     */
    private ArrayList<Pessoa> listaPessoa;
    /**
     * ArrayList que armazena as colunas da JTable
     */
    private String[] colunas = {"ID", "Nome Usuário", "Nome Login", "Tipo Usuário", "Possui Permissão"};

    /**
     * JTable relacionada com a PessoaPermissaoTableModel
     */
    private JTable tabela;

    /**
     * Construtor da classe
     */
    public PessoaPermissaoTableModel() {
        this.listaPessoa = new ArrayList<>();
        fireTableDataChanged();
    }

    /**
     * Método responsável por adicionar uma pessoa na JTable
     *
     * @param p pessoa que será inserida
     */
    public void addPessoa(Pessoa p) {
        this.listaPessoa.add(p);
        fireTableDataChanged();
    }

    /**
     * Método responsável por adicionar um ArrayList
     *
     * @param listaPessoa arrayList que será adicionado
     */
    public void addAll(ArrayList<Pessoa> listaPessoa) {
        this.listaPessoa.addAll(listaPessoa);
        fireTableDataChanged();
    }

    /**
     * Método responsável por retornar os objetos selecionados na JTable
     *
     * @param possuiPermissao parâmetro para selecionar os usuários com ou sem
     * permissão
     * @return retorna um ArrayList
     */
    public ArrayList<Pessoa> retornaLinhasSelecionadas(Boolean possuiPermissao) {
        int[] linhas = tabela.getSelectedRows();
        ArrayList<Pessoa> linhasSelecionadas = new ArrayList();

        for (int i = 0; i < linhas.length; i++) {
            if (listaPessoa.get(linhas[i]).getPermissoes().isEmpty() && !possuiPermissao) {
                linhasSelecionadas.add(listaPessoa.get(linhas[i]));
            } else if (!listaPessoa.get(linhas[i]).getPermissoes().isEmpty() && possuiPermissao) {
                linhasSelecionadas.add(listaPessoa.get(linhas[i]));
            }
        }
        return linhasSelecionadas;
    }

    /**
     * Método responsável por remover uma pessoa
     *
     * @param p pessoa em questão
     */
    public void removerPessoa(Pessoa p) {
        listaPessoa.remove(p);
        fireTableDataChanged();
    }

    /**
     * Método responsável por remover todos os elementos da JTable
     */
    public void removeAll() {
        listaPessoa.removeAll(listaPessoa);
        fireTableDataChanged();
    }

    /**
     * Método responsável por retornar um objeto
     *
     * @param rowIndex posição do objeto
     * @return retorna uma permissão
     */
    public Pessoa getPessoa(int rowIndex) {
        return this.listaPessoa.get(rowIndex);
    }

    /**
     * Método responsável por retornar a quantidade total de linhas na tabela
     *
     * @return quantidade total de linhas na tabela
     */
    @Override
    public int getRowCount() {
        return listaPessoa.size();
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
                return listaPessoa.get(rowIndex).getIdPessoa();
            case 1:
                return listaPessoa.get(rowIndex).getNome();
            case 2:
                return listaPessoa.get(rowIndex).getNomeLogin();
            case 3: {
                if (listaPessoa.get(rowIndex) instanceof Funcionario) {
                    return "Funcionário";
                } else if (listaPessoa.get(rowIndex) instanceof Paciente) {
                    return "Paciente";
                } else {
                    return "Responsável";
                }
            }
            case 4: {
                if (listaPessoa.get(rowIndex).getPermissoes().isEmpty()) {
                    return "Não Possui";
                }
                return "Possui";
            }
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
                return listaPessoa.get(rowIndex).getIdPessoa();
            case "Nome Usuário":
                return listaPessoa.get(rowIndex).getNome();
            case "Nome Login":
                return listaPessoa.get(rowIndex).getNomeLogin();
            case "Tipo Usuário": {
                if (listaPessoa.get(rowIndex) instanceof Funcionario) {
                    return "Funcionário";
                } else if (listaPessoa.get(rowIndex) instanceof Paciente) {
                    return "Paciente";
                } else {
                    return "Responsável";
                }
            }
            case "Possui Permissão": {
                if (listaPessoa.get(rowIndex).getPermissoes().isEmpty()) {
                    return "Não Possui";
                }
                return "Possui";
            }
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
                listaPessoa.get(rowIndex).setIdPessoa((Integer.parseInt(value.toString())));
                break;
            }
            case "Nome Usuário": {
                listaPessoa.get(rowIndex).setNome(value.toString());
                break;
            }
            case "Nome Login": {
                listaPessoa.get(rowIndex).setNomeLogin(value.toString());
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
    public void UpdateObject(Pessoa p1) {
        int contador = 0;
        for (Pessoa p2 : listaPessoa) {
            if (p1.getIdPessoa() == p2.getIdPessoa()) {
                listaPessoa.set(contador, p1);
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
    public void configurarTableModelCliente(JTable tbl) {         //cada Caracter tem 7,3125 de width.
        tbl.doLayout();
        this.tabela = tbl;
        for (int i = 0; i < tbl.getColumnCount(); i++) {

            switch (tbl.getColumnModel().getColumn(i).getHeaderValue().toString()) {
                case "ID": {
                    tbl.getColumnModel().getColumn(i).setMaxWidth((int) Math.round(6 * 7.3125));
                    break;
                }

                case "Possui Permissão": {
                    tbl.getColumnModel().getColumn(i).setMinWidth((int) Math.round(16 * 7.3125));
                    tbl.getColumnModel().getColumn(i).setMaxWidth((int) Math.round(16 * 7.3125));
                    break;
                }

                case "Tipo Usuário": {
                    tbl.getColumnModel().getColumn(i).setMinWidth((int) Math.round(13 * 7.3125));
                    tbl.getColumnModel().getColumn(i).setMaxWidth((int) Math.round(13 * 7.3125));
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

                String status = ((PessoaPermissaoTableModel) table.getModel()).getValueAt(row, "Possui Permissão").toString();

                if (isSelected) {
                    return c;
                }

                if ("Possui".equals(status) && column == 4) {
                    c.setBackground(CoresTabela.corVerde);
                    return c;
                }

                if ("Não Possui".equals(status) && column == 4) {
                    c.setBackground(CoresTabela.corVermelha);
                } else {
                    setBackground(table.getBackground());
                    setForeground(table.getForeground());
                }

                return this;
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

    /**
     * Método responsável por selecionar todos os elementos da JTable
     */
    public void selecionarTodos() {
        if (listaPessoa.size() == 0) {
            return;
        }
        tabela.addRowSelectionInterval(0, listaPessoa.size() - 1);
        tabela.repaint();
    }

    /**
     * Método responsável por desselecionar todos os elementos da JTable
     */
    public void desselecionarTodos() {
        tabela.getSelectionModel().clearSelection();
    }
}
