/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces_Inversao_Controle;

import Model.Localizacao;

/**
 * Interface que é utiliza para as classes que invocam a tela
 * FrmLocalizacaoPesquisar, por conta do padrão de projeto Inversão de Controle
 *
 * @author Antonio Lucas Christofoletti
 */
public interface IPesquisarLocalizacao {

    /**
     * Método que é chamado pela tela FrmLocalizacaoPesquisar, enviando o
     * conteúdo pesquisado
     *
     * @param l localização encontrada
     */
    public void preencheCampos(Localizacao l);
}
