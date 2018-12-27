/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iterator_Pessoa;

import Model.Pessoa;
import java.util.ArrayList;

/**
 * Classe que implementa o padrão de projeto Iterator para a classe Pessoa e aos
 * seus herdeiros
 *
 * @author Antonio
 */
public class IteratorPessoa implements IteratorInterface {
    /**
     * Lista de objetos que compoem o iterator
     */
    
    protected ArrayList<Pessoa> listaPessoa;
    
    /**
     * Objeto que armazena a posição atual do iterator dentro da sua lista de objetos
     */
    protected int contador;

    /**
     * Construtor da classe
     * @param listaPessoa lista de objetos que compusera o Iterator
     */
    public IteratorPessoa(ArrayList<Pessoa> listaPessoa) {
        this.listaPessoa = listaPessoa;
    }

    /**
     * Método responsável por retornar o contador ao primeiro elemento
     */
    @Override
    public void first() {
        contador = 0;
    }

    /**
     * Método responsável por avançar um elemento na lista
     */
    @Override
    public void next() {
        contador++;
    }

    /**
     * Método responsável por informar se a lista já está no último elemento ou
     * não
     *
     * @return retorna true ou false
     */
    @Override
    public boolean isDone() {
        return contador == listaPessoa.size();
    }

    /**
     * Método responsável por retornar o objeto do ciclo atual
     *
     * @return retorna um objeto
     */
    @Override
    public Pessoa currentItem() {
        if (isDone()) {
            contador = listaPessoa.size() - 1;
        } else if (contador < 0) {
            contador = 0;
        }
        return listaPessoa.get(contador);
    }
}
