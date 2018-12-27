/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces_Inversao_Controle;

import Model.Parcela;
import java.util.ArrayList;

/**
 * Interface que é utiliza para as classes que invocam a tela
 * FrmParcelaAutomatico, por conta do padrão de projeto Inversão de Controle
 *
 * @author Antonio Lucas Christofoletti
 */
public interface IGerarParcelaAutomatico {

    /**
     * Método que é chamado pela tela FrmParcelaAutomatico, enviando o conteúdo
     * gerado
     *
     * @param parcelas parcela encontrada
     */
    public void preencheCampos(ArrayList<Parcela> parcelas);
}
