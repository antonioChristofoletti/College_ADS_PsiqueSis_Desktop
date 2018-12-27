/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces_Inversao_Controle;

import Model.Responsavel;

/**
 * Interface que é utiliza para as classes que invocam a tela
 * FrmResponsavelPesquisar, por conta do padrão de projeto Inversão de Controle
 *
 * @author Antonio Lucas Christofoletti
 */
public interface IPesquisarResponsavel {

    /**
     * Método que é chamado pela tela FrmResponsavelPesquisar, enviando o conteúdo
     * pesquisado
     *
     * @param r responsável encontrado
     */
    public void preencheCampos(Responsavel r);
}
