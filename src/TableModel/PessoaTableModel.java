/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModel;

import Geral.CoresTabela;
import Geral.Uteis;
import Model.Funcionario;
import Model.Paciente;
import Model.Pessoa;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Classe que herda de AbstractTableModel e tem como objeto gerenciar as
 * informações de uma JTable relacionada aos funcionários, responsável ou
 * paciente
 *
 * @author Antonio Lucas Christofoletti
 */
public class PessoaTableModel extends AbstractTableModel {

    /**
     * ArrayList que armazena todos os objetos de pessoa da JTable
     */
    private ArrayList<Pessoa> listaPessoa;

    /**
     * ArrayList que armazena as colunas da JTable
     */
    private String[] colunas = {"ID", "RG", "CPF", "Nome", "Nome Login", "Telefone 1", "Cidade", "Logradouro", "Bairro", "Tipo Usuário", "Status"};

    /**
     * Construtor da classe
     */
    public PessoaTableModel() {
        this.listaPessoa = new ArrayList<>();
        fireTableDataChanged();
    }

    /**
     * Método responsável por adicionar uma pessoa na JTable
     *
     * @param p pessoa que será inserido
     */
    public void addFuncionario(Pessoa p) {
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
     * Método responsável por remover uma pessoa
     *
     * @param p pessoa em questão
     */
    public void removerFuncionario(Pessoa p) {
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
     * @return retorna uma pessoa
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
     * @return retornar um valor da tabela
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0: {
                return listaPessoa.get(rowIndex).getIdEspecifico();
            }
            case 1: {
                return Uteis.setaMascara(listaPessoa.get(rowIndex).getRg(), "##.###.###.##");
            }
            case 2:
                return Uteis.setaMascara(listaPessoa.get(rowIndex).getCpf(), "###.###.###.##");
            case 3:
                return listaPessoa.get(rowIndex).getNome();
            case 4:
                return listaPessoa.get(rowIndex).getNomeLogin();
            case 5:
                return Uteis.setaMascara(listaPessoa.get(rowIndex).getTelefone1(), "(##) #### - #####");
            case 6:
                return listaPessoa.get(rowIndex).getCidade();
            case 7:
                return listaPessoa.get(rowIndex).getLogradouro();
            case 8:
                return listaPessoa.get(rowIndex).getBairro();
            case 9: {
                if (listaPessoa.get(rowIndex) instanceof Funcionario) {
                    return "Funcionário";
                } else if (listaPessoa.get(rowIndex) instanceof Paciente) {
                    return "Paciente";
                } else {
                    return "Responsável";
                }
            }
            case 10:
                return listaPessoa.get(rowIndex).getStatus();
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
                return listaPessoa.get(rowIndex).getIdEspecifico();
            }
            case "RG": {
                return Uteis.setaMascara(listaPessoa.get(rowIndex).getRg(), "##.###.###.##");
            }
            case "CPF":
                return Uteis.setaMascara(listaPessoa.get(rowIndex).getCpf(), "###.###.###.##");
            case "Nome":
                return listaPessoa.get(rowIndex).getNome();
            case "Nome Login":
                return listaPessoa.get(rowIndex).getNomeLogin();
            case "Telefone 1":
                return Uteis.setaMascara(listaPessoa.get(rowIndex).getTelefone1(), "(##) #### - #####");
            case "Cidade":
                return listaPessoa.get(rowIndex).getCidade();
            case "Logradouro":
                return listaPessoa.get(rowIndex).getLogradouro();
            case "Bairro":
                return listaPessoa.get(rowIndex).getBairro();
            case "Tipo Usuário": {
                if (listaPessoa.get(rowIndex) instanceof Funcionario) {
                    return "Funcionário";
                } else if (listaPessoa.get(rowIndex) instanceof Paciente) {
                    return "Paciente";
                } else {
                    return "Responsável";
                }
            }
            case "Status":
                return listaPessoa.get(rowIndex).getStatus();
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
                listaPessoa.get(rowIndex).setIdEspecifico((int) value);
                break;
            }
            case "RG": {
                listaPessoa.get(rowIndex).setRg((String) value);
                break;
            }
            case "CPF": {
                listaPessoa.get(rowIndex).setCpf((String) value);
                break;
            }
            case "Nome": {
                listaPessoa.get(rowIndex).setNome((String) value);
                break;
            }
            case "Nome Login": {
                listaPessoa.get(rowIndex).setNomeLogin((String) value);
                break;
            }
            case "Telefone 1": {
                listaPessoa.get(rowIndex).setTelefone1((String) value);
                break;
            }
            case "Cidade": {
                listaPessoa.get(rowIndex).setCidade((String) value);
                break;
            }
            case "Logradouro": {
                listaPessoa.get(rowIndex).setLogradouro((String) value);
                break;
            }
            case "Bairro": {
                listaPessoa.get(rowIndex).setBairro((String) value);
                break;
            }
            case "Status": {
                listaPessoa.get(rowIndex).setStatus((String) value);
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
            if (p1.getIdEspecifico() == p2.getIdEspecifico()) {
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

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                String status = ((PessoaTableModel) table.getModel()).getValueAt(row, "Status").toString();

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
