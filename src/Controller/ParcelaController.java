/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.ParcelaDAO;
import Interfaces_Inversao_Controle.IGerarParcelaAutomatico;
import Model.Parcela;
import View.FrmParcelaAutomatico;
import View.FrmParcelaIncAlt;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Classe do tipo controller relacionada as parcelas dos orçamentos da aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public class ParcelaController {

    /**
     * Tela de parcela Inc/Alt
     */
    private FrmParcelaIncAlt frmParcelaIncAlt;
    /**
     * Objeto DAO referente a parcela
     */
    private ParcelaDAO parcelaDAO;
    /**
     * Objeto do tipo controller referente a parcela
     */
    private static ParcelaController parcelaController;
    /**
     * Tela de parcela que gera as mesmas automaticamente
     */
    private FrmParcelaAutomatico frmParcelaAutomatico;

    /**
     * Construtor da classe
     */
    private ParcelaController() {
        parcelaDAO = new ParcelaDAO();
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
        frmParcelaIncAlt = FrmParcelaIncAlt.getInstance(parent, modal, p);
        this.frmParcelaIncAlt.setVisible(visible);

        return frmParcelaIncAlt.getParcelaEdicaoInsercao();
    }

    /**
     * Método responsável por invocar a tela FrmParcelaAutomatico
     *
     * @param parent parent do JDialog
     * @param modal modal do JDialog
     * @param visible visibilidade da tela, pode ser true ou false
     * @param iGerarParcelaAutomatico tela que está chamando
     */
    public void frmParcelaAutomaticoSetVisible(java.awt.Frame parent, boolean modal, boolean visible, IGerarParcelaAutomatico iGerarParcelaAutomatico) {
        frmParcelaAutomatico = FrmParcelaAutomatico.getInstance(parent, modal, iGerarParcelaAutomatico);
        this.frmParcelaAutomatico.setVisible(visible);
    }

    /**
     * Método responsável por retornar uma instância da própria classe
     *
     * @return retorna uma instância da própria classe
     */
    public static ParcelaController getInstance() {
        if (parcelaController == null) {
            parcelaController = new ParcelaController();
        }

        return parcelaController;
    }

    /**
     * Método responsável por retornar o valor devendo e valor pago total
     *
     * @param listaParcela parcelas que farão parte do calculo
     * @return Array de Double com os valores: 0 = Valor Devendo | 1 = Valor
     * Pago | 2 = Valor Total
     */
    public Double[] retornaValorDevendoPagoTotal(ArrayList<Parcela> listaParcela) {
        Double devendo = 0.0, pago = 0.0, total = 0.0, parcelasPagas = 0.0, parcelasNaoPagas = 0.0;

        for (Parcela p : listaParcela) {
            if (p.getStatus().equals("Pago")) {
                pago += p.getValor();
                parcelasPagas++;
            } else {
                devendo += p.getValor();
                parcelasNaoPagas++;
            }

            total += p.getValor();
        }

        Double[] valores = new Double[5];
        valores[0] = devendo;
        valores[1] = pago;
        valores[2] = total;
        valores[3] = parcelasPagas;
        valores[4] = parcelasNaoPagas;
        return valores;
    }

    /**
     * Método responsável por validar os atributos de uma determinada parcela
     *
     * @param p parcela que será validada
     * @throws Exception disparada caso algum atributo esteja inválido
     */
    public void validarAtributos(Parcela p) throws Exception {
        if (p.getStatus().equals("")) {
            frmParcelaIncAlt.getCmbStatus().requestFocus();
            throw new Exception("status Inválido");
        }

        if (p.getValor() <= 0) {
            frmParcelaIncAlt.getTxtvalor().requestFocus();
            throw new Exception("Valor inválido");
        }

        if (p.getDataVencimento() == null) {
            frmParcelaIncAlt.getTxtDataVencimento().requestFocus();
            frmParcelaIncAlt.getTxtDataVencimento().requestFocusInWindow();
            throw new Exception("Data vencimento inválida");
        }
    }

    /**
     * Método responsável por validar os campos da tela FrmParcelaAutomatico
     * @param frmParcelaAutomatico tela que será validada
     * @throws Exception disparada caso algum campo esteja inválido
     */
    public void validarAtributosTelaFrmParcelaAutomatico(FrmParcelaAutomatico frmParcelaAutomatico) throws Exception {
        if (frmParcelaAutomatico.getTxtDataInicial().getDate() == null) {
            frmParcelaAutomatico.getTxtDataInicial().requestFocus();
            frmParcelaAutomatico.getTxtDataInicial().requestFocusInWindow();
            throw new Exception("Data inicial é inválida");
        }

        if (((int) frmParcelaAutomatico.getTxtQtdParcelas().getValue()) <= 0) {
            frmParcelaAutomatico.getTxtQtdParcelas().requestFocus();
            frmParcelaAutomatico.getTxtQtdParcelas().requestFocusInWindow();
            throw new Exception("Quantidade de parcelas é inválida");
        }
        
        double valorParcela = Double.parseDouble(frmParcelaAutomatico.getTxtValorParcelas().getValue().toString());
        
        if (valorParcela <= 0) {
            frmParcelaAutomatico.getTxtValorParcelas().requestFocus();
            frmParcelaAutomatico.getTxtValorParcelas().requestFocusInWindow();
            throw new Exception("Valor das parcelas é inválida");
        }

        if (((int) frmParcelaAutomatico.getTxtSaltoHoras().getValue()) <= 0) {
            frmParcelaAutomatico.getTxtSaltoHoras().requestFocus();
            frmParcelaAutomatico.getTxtSaltoHoras().requestFocusInWindow();
            throw new Exception("A quantidade de dias para saldo é inválida");
        }
    }

    /**
     * Método responsável por gerar as parcelas de forma automática
     * @param dtInicial data inicial, na qual as parcelas serão geradas
     * @param qtdParcelas quantidade de parcelas que serão geradas
     * @param valorParcelas o valor de cada parcela
     * @param saltoDias salto em dias entre cada parcela
     * @param motivoPadrao descrição padrão que será gerada para cada parcela
     * @param status status da parcela, por padrão será 'Não Paho'
     * @return retorna a lista de parcelas que foi criada.
     */
    public ArrayList<Parcela> geraParcelas(Date dtInicial, int qtdParcelas, double valorParcelas, int saltoDias, String motivoPadrao, String status) {
        ArrayList<Parcela> parcelas = new ArrayList<>();

        Date proximaData = dtInicial;
        Calendar calendar = Calendar.getInstance();

        for (int i = 0; i < qtdParcelas; i++) {
            Parcela p = new Parcela();

            calendar.setTime(proximaData);
            calendar.add(Calendar.HOUR, saltoDias*24);
            proximaData = calendar.getTime();

            p.setDataVencimento(proximaData);
            p.setDescricao(motivoPadrao);
            p.setValor(valorParcelas);
            p.setStatus(status);
            parcelas.add(p);
        }

        return parcelas;
    }

}
