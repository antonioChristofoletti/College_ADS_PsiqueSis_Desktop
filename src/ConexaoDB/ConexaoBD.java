package ConexaoDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por gerenciar a conexão com o banco de dados, realizando a
 * abertura e o encerramento da conexão com o mesmo
 *
 * @author Antonio Lucas Christofoletti
 */
public class ConexaoBD {

    /**
     * Objecto do tipo Connection que será utilizado durante as operações com o
     * banco de dados
     */
    private static Connection connection;

    /**
     * Objecto do tipo String que armazena o tipo do driver de acesso ao banco
     * de dados
     */
    private static String Driver = "com.mysql.jdbc.Driver";

    /**
     * Objecto do String que armazena o caminho para o banco de dados
     */
    private static String localizacaoBanco = "jdbc:mysql://localhost/siste601_psiquesis";

    /**
     * Objecto do tipo String que armazena o usuário para se conectar ao banco
     * de dados
     */
    private static String usuario = "root";

    /**
     * Objeto do tipo String que armazena a senha de acesso ao banco de dados
     */
    private static String senha = "";

    /**
     * Método responsável por realizar a conexão com o banco de dados
     *
     * @throws Exception Exceção disparada caso algum emprevisto ocorra na
     * abertura da conexão
     */
    public static void AbrirConexao() throws Exception {
        try {
            if (connection == null) {
                Class.forName(Driver);
                ConexaoBD.connection = (Connection) DriverManager.getConnection(localizacaoBanco, usuario, senha);
                return;
            }

            if (ConexaoBD.connection.isClosed()) {
                Class.forName(Driver);
                ConexaoBD.connection = (Connection) DriverManager.getConnection(localizacaoBanco, usuario, senha);
                return;
            }
        } catch (Exception ex) {
            throw new Exception("Erro ao abrir a conexão. Erro: " + ex.getMessage());
        }
    }

    /**
     * Método responsável por alterar a propriedade autoCommit do banco de dados
     *
     * @param valor variável do tipo Boolean que definirá se o autoCommit do
     * banco de dados estará ativo ou não
     * @throws SQLException Exceção disparada caso algum emprevisto ocorra
     * durante o método
     */
    public static void setAutoCommit(Boolean valor) throws SQLException {
        if (!ConexaoBD.connection.isClosed()) {
            ConexaoBD.connection.setAutoCommit(valor);
        }
    }

    /**
     * Método responsável por realizar o encerramento da conexão com o banco de
     * dados
     *
     * @throws Exception Exceção disparada caso algum imprevisto ocorra no
     * encerramento da conexão com o banco de dados
     */
    public static void FecharConexao() throws Exception {
        try {
            if (!ConexaoBD.connection.isClosed()) {
                ConexaoBD.connection.close();
            }
        } catch (Exception ex) {
            throw new Exception("Ocorreu um erro ao tentar desconectar com o banco de dados. Erro: " + ex + ".");
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection connection) {
        ConexaoBD.connection = connection;
    }
}
