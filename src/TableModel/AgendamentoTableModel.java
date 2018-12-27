/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModel;

import Geral.CoresTabela;
import Geral.Uteis;
import Model.Agendamento;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Classe que herda de AbstractTableModel e tem como objeto gerenciar as
 * informações de uma JTable relacionada aos agendamentos
 *
 * @author Antonio Lucas Christofoletti
 */
public class AgendamentoTableModel extends AbstractTableModel {

    /**
     * ArrayList que armazena todos os objetos de agendamento da JTable
     */
    private ArrayList<Agendamento> listaAgendamentos;
    /**
     * ArrayList que armazena as colunas da JTable
     */
    private String[] colunas = {"ID", "Motivo", "Duração", "Dt. Criação", "Dt. Agendada", "Dt. Prev. Término", "Criador", "Responsável", "Atendido", "Situação"};

    /**
     * Construtor da classe
     */
    public AgendamentoTableModel() {
        this.listaAgendamentos = new ArrayList<>();
        fireTableDataChanged();
    }

    /**
     * Método responsável por adicionar um agendamento na JTable
     *
     * @param a agendamento que será inserido
     */
    public void addAgendamento(Agendamento a) {
        this.listaAgendamentos.add(a);
        fireTableDataChanged();
    }

    /**
     * Método responsável por adicionar um ArrayList
     *
     * @param la arrayList que será adicionado
     */
    public void addAll(ArrayList<Agendamento> la) {
        this.listaAgendamentos.addAll(la);
        fireTableDataChanged();
    }

    /**
     * Método responsável por retornar todos os objetos
     *
     * @return retorna um ArrayList de objetos
     */
    public ArrayList<Agendamento> getAll() {
        return listaAgendamentos;
    }

    /**
     * Método responsável por remover um agendamento
     *
     * @param a agendamento em questão
     */
    public void removerAgendamento(Agendamento a) {
        listaAgendamentos.remove(a);
        fireTableDataChanged();
    }

    /**
     * Método responsável por remover todos os elementos da JTable
     */
    public void removeAll() {
        listaAgendamentos.removeAll(listaAgendamentos);
        fireTableDataChanged();
    }

    /**
     * Método responsável por retornar um objeto
     *
     * @param rowIndex posição do objeto
     * @return retorna um Agendamento
     */
    public Agendamento getAgendamento(int rowIndex) {
        return this.listaAgendamentos.get(rowIndex);
    }

    /**
     * Método responsável por retornar a quantidade total de linhas na tabela
     *
     * @return quantidade total de linhas na tabela
     */
    @Override
    public int getRowCount() {
        return listaAgendamentos.size();
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
                return listaAgendamentos.get(rowIndex).getId();
            case 1:
                return listaAgendamentos.get(rowIndex).getMotivo();
            case 2:
                return listaAgendamentos.get(rowIndex).getTempoDuracao();
            case 3:
                return Uteis.converteData("dd/MM/yyyy HH:mm", listaAgendamentos.get(rowIndex).getDataCriacao());
            case 4:
                return Uteis.converteData("dd/MM/yyyy HH:mm", listaAgendamentos.get(rowIndex).getDataAgendada());
            case 5: {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(listaAgendamentos.get(rowIndex).getDataAgendada());
                calendar.add(Calendar.MINUTE, listaAgendamentos.get(rowIndex).getTempoDuracao());
                return Uteis.converteData("dd/MM/yyyy HH:mm", calendar.getTime());
            }
            case 6:
                return listaAgendamentos.get(rowIndex).getPessoaCriadora().getNome();
            case 7:
                return listaAgendamentos.get(rowIndex).getFuncionarioResponsavel().getNome();
            case 8:
                return listaAgendamentos.get(rowIndex).getPessoaAtendida().getNome();
            case 9:
                return listaAgendamentos.get(rowIndex).getStatus();
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
                return listaAgendamentos.get(rowIndex).getId();
            case "Motivo":
                return listaAgendamentos.get(rowIndex).getMotivo();
            case "Duração":
                return listaAgendamentos.get(rowIndex).getTempoDuracao();
            case "Observação":
                return listaAgendamentos.get(rowIndex).getObservacao();
            case "Dt. Criação":
                return Uteis.converteData("dd/MM/yyyy HH:mm", listaAgendamentos.get(rowIndex).getDataCriacao());
            case "Dt. Agendada":
                return Uteis.converteData("dd/MM/yyyy HH:mm", listaAgendamentos.get(rowIndex).getDataAgendada());
            case "Dt. Prev. Término": {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(listaAgendamentos.get(rowIndex).getDataAgendada());
                calendar.add(Calendar.MINUTE, listaAgendamentos.get(rowIndex).getTempoDuracao());
                return Uteis.converteData("dd/MM/yyyy HH:mm", calendar.getTime());
            }
            case "Criador":
                return listaAgendamentos.get(rowIndex).getPessoaCriadora().getNome();
            case "Responsável":
                return listaAgendamentos.get(rowIndex).getFuncionarioResponsavel().getNome();
            case "Atendido":
                return listaAgendamentos.get(rowIndex).getPessoaAtendida().getNome();
            case "Situação":
                return listaAgendamentos.get(rowIndex).getStatus();
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
                listaAgendamentos.get(rowIndex).setId((Integer.parseInt(value.toString())));
                break;
            }
            case "Motivo": {
                listaAgendamentos.get(rowIndex).setMotivo(value.toString());
                break;
            }

            case "Duração": {
                listaAgendamentos.get(rowIndex).setTempoDuracao((int) value);
                break;
            }
            case "Dt. Criação": {
                listaAgendamentos.get(rowIndex).setDataCriacao((Date) value);
                break;
            }
            case "Dt. Agendada": {
                listaAgendamentos.get(rowIndex).setDataAgendada((Date) value);
                break;
            }
            case "Destino": {
                listaAgendamentos.get(rowIndex).getFuncionarioResponsavel().setNome(value.toString());
                break;
            }
            case "Criador": {
                listaAgendamentos.get(rowIndex).getPessoaCriadora().setNome(value.toString());
                break;
            }
            case "Atendido": {
                listaAgendamentos.get(rowIndex).getPessoaAtendida().setNome(value.toString());
                break;
            }
            case "Situação": {
                listaAgendamentos.get(rowIndex).setStatus(value.toString());
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
    public void UpdateObject(Agendamento a1) {
        int contador = 0;
        for (Agendamento a2 : listaAgendamentos) {
            if (a1.getId() == a2.getId()) {
                listaAgendamentos.set(contador, a1);
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
                case "Dt. Criação": {
                    tbl.getColumnModel().getColumn(i).setMaxWidth((int) Math.round(19 * 7.3125));
                    tbl.getColumnModel().getColumn(i).setMinWidth((int) Math.round(19 * 7.3125));
                    break;
                }
                case "Dt. Agendada": {
                    tbl.getColumnModel().getColumn(i).setMaxWidth((int) Math.round(19 * 7.3125));
                    tbl.getColumnModel().getColumn(i).setMinWidth((int) Math.round(19 * 7.3125));
                    break;
                }
                case "Dt. Prev. Término": {
                    tbl.getColumnModel().getColumn(i).setMaxWidth((int) Math.round(19 * 7.3125));
                    tbl.getColumnModel().getColumn(i).setMinWidth((int) Math.round(19 * 7.3125));
                    break;
                }

                case "Situação": {
                    tbl.getColumnModel().getColumn(i).setMaxWidth((int) Math.round(12 * 7.3125));
                    tbl.getColumnModel().getColumn(i).setMinWidth((int) Math.round(12 * 7.3125));
                    break;
                }

                case "Duração": {
                    tbl.getColumnModel().getColumn(i).setMaxWidth((int) Math.round(8 * 7.3125));
                    tbl.getColumnModel().getColumn(i).setMinWidth((int) Math.round(8 * 7.3125));
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

                String status = ((AgendamentoTableModel) table.getModel()).getValueAt(row, "Situação").toString();

                if (isSelected) {
                    return c;
                }

                if (status.equals("Conflito")) {
                    c.setBackground(CoresTabela.corVencida);
                    return c;
                }

                if (status.equals("Cancelado") && column == 9) {
                    c.setBackground(CoresTabela.corVermelha);
                } else if (status.equals("Pendente") && column == 9) {
                    c.setBackground(CoresTabela.corAmarela);
                } else if (status.equals("Aprovado") && column == 9) {
                    c.setBackground(CoresTabela.corVerde);
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
                return String.class;
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
    public boolean isCellEditable(int row, int col) {
        return false;
    }
}
