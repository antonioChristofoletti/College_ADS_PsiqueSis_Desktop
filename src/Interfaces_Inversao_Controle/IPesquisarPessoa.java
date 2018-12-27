/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces_Inversao_Controle;

import Model.Pessoa;

/**
 * Interface que é utiliza para as classes que invocam a tela
 * FrmPessoaPesquisar, por conta do padrão de projeto Inversão de Controle
 *
 * @author Antonio Lucas Christofoletti
 */
public interface IPesquisarPessoa {

    /**
     * Método que é chamado pela tela FrmPessoaPesquisar, enviando o
     * conteúdo pesquisado
     *
     * @param p pessoa encontrado
     */
    public void preencheCampos(Pessoa p);
}
