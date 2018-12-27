/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModel;

import Geral.CoresTabela;
import Geral.Uteis;
import Model.Funcionario;
import Model.Orcamento;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Classe que herda de AbstractTableModel e tem como objeto gerenciar as
 * informações de uma JTable relacionada aos orçamentos dos funcionários
 *
 * @author Antonio Lucas Christofoletti
 */
public class OrcamentoTableModel extends AbstractTableModel {

    /**
     * ArrayList que armazena todos os objetos de orçamento da JTable
     */
    private ArrayList<Orcamento> listaOrcamentos;
    /**
     * ArrayList que armazena as colunas da JTable
     */
    private String[] colunas = {"ID", "Descrição", "Responsável", "Dt. Vencimento", "Vlr. Reg. Caixa", "Vlr. Reg. Competência", "Status", "Tipo"};

    /**
     * Construtor da classe
     */
    public OrcamentoTableModel() {
        this.listaOrcamentos = new ArrayList<>();
        fireTableDataChanged();
    }

    /**
     * Método responsável por adicionar um orçamento na JTable
     *
     * @param o orçamento que será inserido
     */
    public void addOrcamento(Orcamento o) {
        this.listaOrcamentos.add(o);
        fireTableDataChanged();
    }

    /**
     * Método responsável por adicionar um ArrayList
     *
     * @param listaOrcamento arrayList que será adicionado
     */
    public void addAll(ArrayList<Orcamento> listaOrcamento) {
        this.listaOrcamentos.addAll(listaOrcamento);
        fireTableDataChanged();
    }

    /**
     * Método responsável por remover um histórico
     *
     * @param o orçamento em questão
     */
    public void removerOrcamento(Orcamento o) {
        listaOrcamentos.remove(o);
        fireTableDataChanged();
    }

    /**
     * Método responsável por remover todos os elementos da JTable
     */
    public void removeAll() {
        listaOrcamentos.removeAll(listaOrcamentos);
        fireTableDataChanged();
    }

    /**
     * Método responsável por retornar todos os objetos
     *
     * @return retorna um ArrayList de objetos
     */
    public ArrayList<Orcamento> getAll() {
        return listaOrcamentos;
    }

    /**
     * Método responsável por retornar um objeto
     *
     * @param rowIndex posição do objeto
     * @return retorna um orçamento
     */
    public Orcamento getOrcamento(int rowIndex) {
        return this.listaOrcamentos.get(rowIndex);
    }

    /**
     * Método responsável por retornar a quantidade total de linhas na tabela
     *
     * @return quantidade total de linhas na tabela
     */
    @Override
    public int getRowCount() {
        return listaOrcamentos.size();
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
                int id = listaOrcamentos.get(rowIndex).getId();

                if (id <= 0) {
                    return "";
                }

                return id;
            }
            case 1: {
                return listaOrcamentos.get(rowIndex).getDescricao();
            }
            case 2:
                return listaOrcamentos.get(rowIndex).getFuncionarioResponsavel().getNome();
            case 3:
                return Uteis.converteData("dd/MM/yyyy", listaOrcamentos.get(rowIndex).getDataVencimento());
            case 4:
                return listaOrcamentos.get(rowIndex).getValorRegimeCaixa();
            case 5:
                return listaOrcamentos.get(rowIndex).getValorRegimeCompetencia();
            case 6:
                return listaOrcamentos.get(rowIndex).getStatus();
            case 7:
                return listaOrcamentos.get(rowIndex).getTipo();
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
                int id = listaOrcamentos.get(rowIndex).getId();

                if (id <= 0) {
                    return "";
                }

                return id;
            }
            case "Descrição": {
                return listaOrcamentos.get(rowIndex).getDescricao();
            }
            case "Responsável":
                return listaOrcamentos.get(rowIndex).getFuncionarioResponsavel().getNome();
            case "Dt. Vencimento":
                return Uteis.converteData("dd/MM/yyyy", listaOrcamentos.get(rowIndex).getDataVencimento());
            case "Vlr. Reg. Caixa":
                return listaOrcamentos.get(rowIndex).getValorRegimeCaixa();
            case "Vlr. Reg. Competência":
                return listaOrcamentos.get(rowIndex).getValorRegimeCompetencia();
            case "Status":
                return listaOrcamentos.get(rowIndex).getStatus();
            case "Tipo":
                return listaOrcamentos.get(rowIndex).getTipo();
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
                listaOrcamentos.get(rowIndex).setId((Integer.parseInt(value.toString())));
                break;
            }
            case "Descrição": {
                listaOrcamentos.get(rowIndex).setDescricao(value.toString());
                break;
            }
            case "Responsável": {
                listaOrcamentos.get(rowIndex).setFuncionarioResponsavel((Funcionario) value);
                break;
            }
            case "Dt. Vencimento": {
                listaOrcamentos.get(rowIndex).setDataVencimento((Date) value);
                break;
            }

            case "Status": {
                listaOrcamentos.get(rowIndex).setStatus(value.toString());
                break;
            }
            case "Tipo": {
                listaOrcamentos.get(rowIndex).setTipo(value.toString());
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
     * @param o1 objeto que substituirá o antigo
     */
    public void UpdateObject(Orcamento o1) {
        int contador = 0;
        for (Orcamento o2 : listaOrcamentos) {
            if (o1.getId() == o2.getId()) {
                listaOrcamentos.set(contador, o1);
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

                case "Responsável": {
                    tbl.getColumnModel().getColumn(i).setMaxWidth((int) Math.round(25 * 7.3125));
                    tbl.getColumnModel().getColumn(i).setMinWidth((int) Math.round(25 * 7.3125));
                    break;
                }

                case "Status": {
                    tbl.getColumnModel().getColumn(i).setMaxWidth((int) Math.round(12 * 7.3125));
                    tbl.getColumnModel().getColumn(i).setMinWidth((int) Math.round(12 * 7.3125));
                    break;
                }

                case "Dt. Vencimento": {
                    tbl.getColumnModel().getColumn(i).setMaxWidth((int) Math.round(15 * 7.3125));
                    tbl.getColumnModel().getColumn(i).setMinWidth((int) Math.round(15 * 7.3125));
                    break;
                }

                case "Tipo": {
                    tbl.getColumnModel().getColumn(i).setMaxWidth((int) Math.round(9 * 7.3125));
                    break;
                }

                default: {
                    break;
                }
            }
        }

        tbl.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                String status = ((OrcamentoTableModel) table.getModel()).getValueAt(row, "Status").toString();

                if (isSelected) {
                    return c;
                }

                if (status.equals("Vencido")) {
                    c.setBackground(CoresTabela.corVencida);
                    return c;
                }

                if (status.equals("Atrasado")) {
                    c.setBackground(CoresTabela.corAmarela);
                    return c;
                }

                if (column == 6) {
                    switch (status) {
                        case "Quitado": {
                            c.setBackground(CoresTabela.corAzul);
                            break;
                        }
                        case "Normal": {
                            c.setBackground(CoresTabela.corVerde);
                            break;
                        }
                        case "Cancelado": {
                            c.setBackground(CoresTabela.corVermelha);
                            break;
                        }
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
