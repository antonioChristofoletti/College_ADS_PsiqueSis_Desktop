/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces_Inversao_Controle;

import Model.Atividade;

/**
 * Interface que é utiliza para as classes que invocam a tela FrmAtividadePesquisar, por conta do padrão de projeto Inversão de Controle
 * @author Antonio Lucas Christofoletti
 */
public interface IPesquisarAtividade {
    /**
     * Método que é chamado pela tela FrmAtividadePesquisar, enviando o conteúdo pesquisado
     * @param a atividde encontrada
     */
    public void preencheCampos(Atividade a);
}
