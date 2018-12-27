/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces_Inversao_Controle;

import Model.Plano;

/**
 * Interface que é utiliza para as classes que invocam a tela FrmPlanoPesquisar,
 * por conta do padrão de projeto Inversão de Controle
 *
 * @author Antonio Lucas Christofoletti
 */
public interface IPesquisarPlano {

    /**
     * Método que é chamado pela tela FrmPlanoPesquisar, enviando o
     * conteúdo pesquisado
     *
     * @param p plano encontrado
     */
    public void preencheCampos(Plano p);
}
