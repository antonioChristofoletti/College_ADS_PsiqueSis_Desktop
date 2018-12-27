package Iterator_Pessoa;

/**
 * Interface que implementa os métodos relacionados ao padrão de projeto Iterator
 * @author Antonio Lucas Christofoletti
 */
public interface IteratorInterface {
    /**
     * Método responsável por retornar o contador ao primeiro elemento
     */
    void first();

    /**
     * Método responsável por avançar um elemento na lista
     */
    void next();
    
    
    /**
     * Método responsável por informar se a lista já está no último elemento ou não
     * @return retorna true ou false
     */
    boolean isDone();

    /**
     *  Método responsável por retornar o objeto do ciclo atual
     * @return retorna um objeto
     */
    Object currentItem();
}
