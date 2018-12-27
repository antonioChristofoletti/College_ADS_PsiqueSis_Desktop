package psiquesis;

import Controller.FuncionarioController;

/**
 * Classe que executa o método main e inicia a aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public class PsiqueSis {

    /**
     * Método iniciado juntamento com uma instância da classe
     *
     * @param args lista de argumentos que podem ser passados juntamente com
     * método, porém, não é necessário nesse cenário
     */
    public static void main(String[] args) {
        FuncionarioController.getInstance().frmFuncionarioLoginSetVisible(true);
    }
}
