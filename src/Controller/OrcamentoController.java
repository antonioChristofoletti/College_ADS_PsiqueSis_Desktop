/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.OrcamentoDAO;
import Model.Orcamento;
import Model.Parcela;
import View.FrmOrcamentoGrafico;
import View.FrmOrcamentoIncAlt;
import View.FrmOrcamentoLista;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Classe do tipo controller relacionada aos orçamentos da aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public class OrcamentoController {

    /**
     * Tela de orçamento lista
     */
    private FrmOrcamentoLista frmOrcamentoLista;
    /**
     * Tela de orçamento Inc/Alt
     */
    private FrmOrcamentoIncAlt frmOrcamentoIncAlt;
    /**
     * Tela de orçamento relacionada aos gráficos do mesmo
     */
    private FrmOrcamentoGrafico frmOrcamentoGrafico;

    /**
     * Objeto DAO referente ao orçamento
     */
    private OrcamentoDAO orcamentoDAO;
    /**
     * Objeto da própria classe OrcamentoController por conta do padrão de
     * projeto Sington
     */
    private static OrcamentoController orcamentoController;
    /**
     * Objeto do tipo controller referente a parcela
     */
    private static ParcelaController parcelaControler;

    /**
     * Construtor da classe
     */
    private OrcamentoController() {
        orcamentoDAO = new OrcamentoDAO();
        parcelaControler = ParcelaController.getInstance();
    }

    /**
     * Método responsável por invocar a tela de FrmOrcamentoLista
     *
     * @param visible visibilidade da tela, pode ser true ou false
     */
    public void frmOrcamentoListaSetVisible(boolean visible) {
        frmOrcamentoLista = FrmOrcamentoLista.getInstance();
        this.frmOrcamentoLista.setVisible(visible);
    }

    /**
     * Método responsável por invocar a tela de FrmOrcamentoIncAlt
     *
     * @param o orçamento de edição, pode ser um parâmetro null caso a ideia
     * seja inserir um novo registro
     * @param visible visibilidade da tela, pode ser true ou false
     */
    public void frmOrcamentoIncAltSetVisible(Orcamento o, boolean visible) {
        frmOrcamentoIncAlt = FrmOrcamentoIncAlt.getInstance(o);
        this.frmOrcamentoIncAlt.setVisible(visible);
    }

    /**
     * Retorna uma instância da própria classe
     *
     * @return retorna uma instância da própria classe
     */
    public static OrcamentoController getInstance() {
        if (orcamentoController == null) {
            orcamentoController = new OrcamentoController();
        }
        return orcamentoController;
    }

    /**
     * Método responsável por invocar a tela FrmParcelaIncAlt
     *
     * @param parent parent do JDialog
     * @param modal modal do JDialog
     * @param p parcela que será editada, null caso a intenção seja inserir uma
     * nova parcela
     * @param visible visibilidade da tela, pode ser true ou false
     * @return retorna a parcela editada ou inserida após a tela ser fechada
     */
    public Parcela frmParcelaIncAltSetVisible(java.awt.Frame parent, boolean modal, Parcela p, boolean visible) {
        return parcelaControler.frmParcelaIncAltSetVisible(parent, modal, p, visible);
    }

    /**
     * Método responsável por invocar a tela FrmOrcamentoGrafico
     * @param visible visibilidade da tela, pode ser true ou false
     */
    public void FrmOrcamentoGraficoSetVisible(boolean visible) {
        frmOrcamentoGrafico = FrmOrcamentoGrafico.getInstance();
        this.frmOrcamentoGrafico.setVisible(visible);
    }

    /**
     * Método responsável por retornar o valor devendo e valor pago total
     *
     * @param listaParcela parcelas que farão parte do calculo
     * @return Array de Double com os valores: 0 = Valor Devendo | 1 = Valor
     * Pago | 2 = Valor Total
     */
    public Double[] retornaValorDevendoPagoTotal(ArrayList<Parcela> listaParcela) {
        return parcelaControler.retornaValorDevendoPagoTotal(listaParcela);
    }

    /**
     * Método responsável por inserir um orçamento
     *
     * @param o orçamento que será inserido
     * @throws Exception disparada durante o processo de inserção
     */
    public void inserirOrcamento(Orcamento o) throws Exception {
        orcamentoDAO.inserir(o);
    }

    /**
     * Método responsável por editar um orçamento
     *
     * @param o orçamento que será editado
     * @throws Exception disparada durante o processo de atualização
     */
    public void editarOrcamento(Orcamento o) throws Exception {
        if (o.getStatus().equals("Cancelado")) {
            throw new Exception("Não é possível editar um orçamento cancelado");
        }
        orcamentoDAO.editar(o);
    }

    /**
     * Método responsável por pesquisar orçamento
     *
     * @param descricao descrição do orçamento, pode ser "" caso esse parâmetro
     * não seja adequado na pesquisa em questão
     * @param status status do orçamento
     * @param dtInicio data de início do orçamento, pode ser null caso esse
     * parâmetro não seja adequado na pesquisa em questão
     * @param dtFinal data final do orçamento, pode ser null caso esse parâmetro
     * não seja adequado na pesquisa em questão
     * @param tipo tipo do orçamento, pode ser "" caso esse parâmetro não seja
     * adequado na pesquisa em questão
     * @return retorna um ArrayList de orçamentos pesquisados
     * @throws Exception disparada durante o processo de pesquisa
     */
    public ArrayList pesquisarOrcamento(String descricao, String status, Date dtInicio, Date dtFinal, String tipo) throws Exception {
        ArrayList<Orcamento> orcamentos = orcamentoDAO.pesquisar(descricao, dtInicio, dtFinal, tipo);

        for (int j = 0; j < orcamentos.size(); j++) {
            Orcamento o = orcamentos.get(j);

            o.setStatus(this.retornaStatusOrcamento(o));

            if (!status.equals("Todos") && !o.getStatus().equals(status)) {
                orcamentos.remove(j);
                j--;
            }
        }

        return orcamentos;
    }

    /**
     * Método responsável por validar os atributos de um orçamento
     *
     * @param o orçamento que será validado
     * @throws Exception disparada caso algum atributo esteja inválido
     */
    public void validarAtributos(Orcamento o) throws Exception {
        if (o.getStatus().equals("Cancelado")) {
            throw new Exception("Não é possível editar um orçamento com status 'Cancelado'");
        }

        if (o.getDataVencimento() == null) {
            frmOrcamentoIncAlt.getTxtDataVencimento().requestFocus();
            frmOrcamentoIncAlt.getTxtDataVencimento().requestFocusInWindow();
            throw new Exception("Data Inválida");
        }

        if (o.getStatus().equals("")) {
            throw new Exception("Status inválido");
        }

        if (o.getTipo().equals("")) {
            frmOrcamentoIncAlt.getCmbTipo().requestFocus();
            throw new Exception("Tipo inválido");
        }

        if (o.getFuncionarioResponsavel() == null) {
            frmOrcamentoIncAlt.getTxtResponsavel().requestFocus();
            throw new Exception("Responsável inválido");
        }

        if (o.getDescricao().equals("")) {
            frmOrcamentoIncAlt.getTxtDescricao().requestFocus();
            throw new Exception("Descrição inválida");
        }

        if (o.getParcelas().isEmpty()) {
            throw new Exception("Necessário haver no mínimo uma parcela");
        }

        /*int i = 1;
        for (Parcela p : o.getParcelas()) {
            if(p.getDataVencimento().)
            if (p.getDataVencimento().getTime() > o.getDataVencimento().getTime() && o.getDataVencimento().compareTo(p.getDataVencimento()) != 0) {
                throw new Exception("A data de vencimento da parcela da posição'" + i + "' deve ser menor que a data de vencimento do orçamento");
            }
            i++;
        }*/
    }

    /**
     * Método responsável por retornar o status do orçamento Analisa e retorna o
     * status atual relacionado a um orçamento
     *
     * @param o orçamento que será analisado
     * @return retorna uma string com o status
     */
    public String retornaStatusOrcamento(Orcamento o) {
        if (o.getStatus().equals("Cancelado")) {
            return "Cancelado";
        }

        int quantiQuitado = 0;

        for (Parcela p : o.getParcelas()) {
            if (p.getStatus().equals("Pago")) {
                quantiQuitado++;
            } else if (p.getDataVencimento().getTime() < new Date().getTime()) {
                if (o.getDataVencimento().getTime() < new Date().getTime()) {
                    return "Vencido";
                }
                return "Atrasado";
            }
        }

        if (quantiQuitado == o.getParcelas().size()) {
            return "Quitado";
        }

        if (o.getDataVencimento().getTime() < new Date().getTime()) {
            return "Vencido";
        }

        return "Normal";
    }

    /**
     * Método responsável por gera a base de dados para o gráfico de relação de
     * orçmaneto
     *
     * @param lo orçamento que serão levados em consideração para a geração do
     * gráfico
     * @return retorna a base de dados para o gráfico
     */
    public DefaultCategoryDataset geraBaseDeDadosGraficoLinhas(ArrayList<Orcamento> lo) {
        String valorRegCaixa = "Saldo Reg. Caixa";
        String valorRegCompetencia = "Saldo Reg. Competência";

        Calendar cal = Calendar.getInstance();

        int mes = -1, ano = -1;

        DefaultCategoryDataset datasetlinhas = new DefaultCategoryDataset();

        double valorRegCaixav = 0, valorRegCompetenciav = 0;

        for (Orcamento o : lo) {
            cal.setTime(o.getDataVencimento());

            if (o.getStatus().equals("Cancelado")) {
                continue;
            }

            if (mes == -1 && ano == -1) {
                mes = cal.get(Calendar.MONTH) + 1;
                ano = cal.get(Calendar.YEAR);
            }

            if ((cal.get(Calendar.MONTH) + 1 != mes || cal.get(Calendar.YEAR) != ano)) {
                datasetlinhas.addValue(valorRegCompetenciav, valorRegCompetencia, mes + "/" + ano);
                datasetlinhas.addValue(valorRegCaixav, valorRegCaixa, mes + "/" + ano);

                mes = cal.get(Calendar.MONTH) + 1;
                ano = cal.get(Calendar.YEAR);
            }

            for (Parcela p : o.getParcelas()) {
                if (o.getTipo().equals("Entrada") && p.getStatus().equals("Pago")) {
                    valorRegCaixav += p.getValor();
                } else if (o.getTipo().equals("Saída") && p.getStatus().equals("Pago")) {
                    valorRegCaixav -= p.getValor();
                }

                if (o.getTipo().equals("Entrada")) {
                    valorRegCompetenciav += p.getValor();
                } else if (o.getTipo().equals("Saída")) {
                    valorRegCompetenciav -= p.getValor();
                }
            }
        }

        datasetlinhas.addValue(valorRegCompetenciav, valorRegCompetencia, mes + "/" + ano);
        datasetlinhas.addValue(valorRegCaixav, valorRegCaixa, mes + "/" + ano);

        return datasetlinhas;
    }

    /**
     * Método responsável por gera a base de dados para o gráfico de saldo
     *
     * @param lo orçamento que serão levados em consideração para a geração do
     * gráfico
     * @return retorna a base de dados para o gráfico
     */
    public CategoryDataset geraBaseDeDadosGraficoColuna(ArrayList<Orcamento> lo) {

        String vlrRegCaixaEntrada = "Saldo Reg. Caixa Entrada";
        String vlrRegCompEntrada = "Saldo Reg. Competência Entrada";
        String diferencaEntrada = "Diferença Saldo Reg. Competência/Saldo Reg. Caixa Entrada";

        String vlrRegCaixaSaida = "Saldo Reg. Caixa Saída";
        String vlrRegCompSaida = "Saldo Reg. Competência Saída";
        String diferencaSaida = "Diferença Saldo Reg. Competência/Saldo Reg. Caixa Saída";

        Calendar cal = Calendar.getInstance();

        int mes = -1, ano = -1;

        DefaultCategoryDataset datasetColunas = new DefaultCategoryDataset();

        double valorTotalRegCaixaEntrada = 0, valorTotalRegCompetenciaEntrada = 0, valorTotalRegCaixaSaida = 0, valorTotalRegCompetenciaSaida = 0;

        for (Orcamento o : lo) {
            cal.setTime(o.getDataVencimento());

            if (o.getStatus().equals("Cancelado")) {
                continue;
            }

            if (mes == -1 && ano == -1) {
                mes = cal.get(Calendar.MONTH) + 1;
                ano = cal.get(Calendar.YEAR);
            }

            if ((cal.get(Calendar.MONTH) + 1 != mes || cal.get(Calendar.YEAR) != ano)) {
                datasetColunas.addValue(valorTotalRegCaixaEntrada, vlrRegCaixaEntrada, mes + "/" + ano);
                datasetColunas.addValue(valorTotalRegCompetenciaEntrada, vlrRegCompEntrada, mes + "/" + ano);
                datasetColunas.addValue(valorTotalRegCompetenciaEntrada - valorTotalRegCaixaEntrada, diferencaEntrada, mes + "/" + ano);

                datasetColunas.addValue(valorTotalRegCaixaSaida, vlrRegCaixaSaida, mes + "/" + ano);
                datasetColunas.addValue(valorTotalRegCompetenciaSaida, vlrRegCompSaida, mes + "/" + ano);
                datasetColunas.addValue(valorTotalRegCompetenciaSaida - valorTotalRegCaixaSaida, diferencaSaida, mes + "/" + ano);

                mes = cal.get(Calendar.MONTH) + 1;
                ano = cal.get(Calendar.YEAR);

                valorTotalRegCaixaEntrada = 0;
                valorTotalRegCompetenciaEntrada = 0;

                valorTotalRegCaixaSaida = 0;
                valorTotalRegCompetenciaSaida = 0;
            }

            if (o.getTipo().equals("Entrada")) {
                valorTotalRegCaixaEntrada += o.getValorRegimeCaixa();
                valorTotalRegCompetenciaEntrada += o.getValorRegimeCompetencia();
            } else {
                valorTotalRegCaixaSaida += o.getValorRegimeCaixa();
                valorTotalRegCompetenciaSaida += o.getValorRegimeCompetencia();
            }
        }

        datasetColunas.addValue(valorTotalRegCaixaEntrada, vlrRegCaixaEntrada, mes + "/" + ano);
        datasetColunas.addValue(valorTotalRegCompetenciaEntrada, vlrRegCompEntrada, mes + "/" + ano);
        datasetColunas.addValue(valorTotalRegCompetenciaEntrada - valorTotalRegCaixaEntrada, diferencaEntrada, mes + "/" + ano);

        datasetColunas.addValue(valorTotalRegCaixaSaida, vlrRegCaixaSaida, mes + "/" + ano);
        datasetColunas.addValue(valorTotalRegCompetenciaSaida, vlrRegCompSaida, mes + "/" + ano);
        datasetColunas.addValue(valorTotalRegCompetenciaSaida - valorTotalRegCaixaSaida, diferencaSaida, mes + "/" + ano);

        return datasetColunas;
    }
}
