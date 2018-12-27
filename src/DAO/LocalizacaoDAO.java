package DAO;

import ConexaoDB.ConexaoBD;
import Model.Localizacao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Classe do tipo DAO relacionada as localizações dos funcionários da aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public class LocalizacaoDAO {

    /**
     * Método responsável por inserir uma localização no banco de dados
     *
     * @param l localização que será inserida
     * @throws Exception disparado caso ocorra algum erro ao inserir histórico
     */
    public void inserir(Localizacao l) throws Exception {
        try {
            ConexaoBD.AbrirConexao();
            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("INSERT INTO localizacao (bairro, telefone1,telefone2, nome, cidade, logradouro, descricao, status) VALUES(?,?,?,?,?,?,?,?)");
            pst.setString(1, l.getBairro());
            pst.setString(2, l.getTelefone1());
            pst.setString(3, l.getTelefone2());
            pst.setString(4, l.getNome());
            pst.setString(5, l.getCidade());
            pst.setString(6, l.getLogradouro());
            pst.setString(7, l.getDescricao());
            pst.setString(8, l.getStatus().substring(0, 1));

            pst.execute();
        } catch (Exception ex) {
            throw new Exception("Erro ao inserir localização. Erro: " + ex.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método responsável por editar uma localização
     *
     * @param l localização que será editada
     * @throws Exception disparado caso ocorra algum erro
     */
    public void editar(Localizacao l) throws Exception {
        try {
            ConexaoBD.AbrirConexao();
            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("UPDATE localizacao SET bairro=?, telefone1=?,telefone2=?, nome=?, cidade=?, logradouro=?, descricao=?, status=? WHERE id=?");
            pst.setString(1, l.getBairro());
            pst.setString(2, l.getTelefone1());
            pst.setString(3, l.getTelefone2());
            pst.setString(4, l.getNome());
            pst.setString(5, l.getCidade());
            pst.setString(6, l.getLogradouro());
            pst.setString(7, l.getDescricao());
            pst.setString(8, l.getStatus().substring(0, 1));
            pst.setInt(9, l.getId());

            pst.execute();
        } catch (Exception ex) {
            throw new Exception("Erro ao remover a localização. Erro: " + ex.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método responsável por pesquisar localizações
     *
     * @param nome nome da localização, pode ser "" caso esse parâmetro não seja
     * adequado na pesquisa em questão
     * @param status status da localização
     * @return retorna um ArrayList de localizações encontradas
     * @throws Exception disparada caso ocorra algum erro durante a pesquisa
     */
    public ArrayList pesquisar(String nome, String status) throws Exception {
        try {
            ArrayList<Localizacao> localizacoes = new ArrayList<>();
            ConexaoBD.AbrirConexao();

            PreparedStatement stmt = ConexaoBD.getConnection().prepareStatement("SELECT * FROM localizacao " + geraWherePesquisar(nome, status));;
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Localizacao l = new Localizacao();
                l.setBairro(rs.getString("bairro"));
                l.setTelefone1(rs.getString("telefone1"));
                l.setTelefone2(rs.getString("telefone2"));
                l.setNome(rs.getString("nome"));
                l.setCidade(rs.getString("cidade"));
                l.setLogradouro(rs.getString("logradouro"));
                l.setDescricao(rs.getString("descricao"));
                l.setId(rs.getInt("id"));

                if (rs.getString("status").equals("A")) {
                    l.setStatus("Ativo");
                } else {
                    l.setStatus("Inativo");
                }

                localizacoes.add(l);
            }

            rs.close();
            stmt.close();
            return (localizacoes);
        } catch (Exception ex) {
            throw new Exception("Erro ao pesquisar localização. Erro: " + ex.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método responsável por retornar uma string com o WHERE para a consulta
     * SQL
     *
     * @param nome nome da localização, pode ser "" caso esse parâmetro não seja
     * adequado na pesquisa em questão
     * @param status status da localização
     * @return retorna uma string com o WHERE para a consulta SQL
     */
    public String geraWherePesquisar(String nome, String status) {
        String where = "WHERE ";
        if (!nome.equals("")) {
            where += "nome LIKE '%" + nome + "%' AND ";
        }

        if (!status.equals("Todos")) {
            where += "status='" + status.substring(0, 1) + "' AND ";
        }

        return where += "1=1";
    }

    /**
     * Método responsável por verificar se um telefone de localização existe ou não
     *
     * @param telefone a ser verificado
     * @return retorna true ou false
     * @throws Exception disparado caso ocorra algum erro durante a pesquisa das
     * informações
     */
    public boolean existeTelefone(String telefone) throws Exception {
        try {
            ConexaoBD.AbrirConexao();
            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("SELECT COUNT(*) FROM localizacao WHERE telefone1=? OR telefone2=?");
            pst.setString(1, telefone);
            pst.setString(2, telefone);
            ResultSet rs = pst.executeQuery();
            rs.next();

            if (rs.getInt(1) > 0) {
                pst.close();
                rs.close();
                return true;
            } else {
                pst.close();
                rs.close();
                return false;
            }
        } catch (Exception e) {
            throw new Exception("Erro ao verificar telefone. Erro: " + e.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método responsável por verificar se um nome de localização existe ou não
     *
     * @param nome a ser verificado
     * @return retorna true ou false
     * @throws Exception disparada caso ocorra algum erro durante a pesquisa das
     * informações
     */
    public boolean existeNomeLocalizacao(String nome) throws Exception {
        try {
            ConexaoBD.AbrirConexao();
            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("SELECT COUNT(*) FROM localizacao WHERE nome=?");
            pst.setString(1, nome);
            ResultSet rs = pst.executeQuery();
            rs.next();

            if (rs.getInt(1) > 0) {
                pst.close();
                rs.close();
                return true;
            } else {
                pst.close();
                rs.close();
                return false;
            }
        } catch (Exception e) {
            throw new Exception("Erro ao verificar nome localização. Erro: " + e.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
    }
}
