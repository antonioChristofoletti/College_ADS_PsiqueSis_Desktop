package DAO;

import ConexaoDB.ConexaoBD;
import Geral.Uteis;
import Model.Funcionario;
import Model.Maquina;
import Model.Pessoa;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * Classe do tipo DAO relacionada as máquinas da aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public class MaquinaDAO {

    /**
     * Método responsável por inserir uma máquina no banco de dados
     *
     * @param m máquina que será inserida
     * @throws Exception disparada caso ocorra algum erro ao inserir máquina
     */
    public void inserir(Maquina m) throws Exception {
        try {
            ConexaoBD.AbrirConexao();
            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("INSERT INTO maquina (ip, mac, porta, status, dataAcesso, idUsuario) VALUES(?,?,?,?,?,?)");
            pst.setString(1, m.getIp());
            pst.setString(2, m.getMac());
            pst.setString(3, m.getPorta());
            pst.setString(4, m.getStatus());
            pst.setTimestamp(5, new Timestamp(m.getDataAcesso().getTime()));
            pst.setInt(6, m.getP().getIdPessoa());
            pst.execute();
        } catch (Exception ex) {
            throw new Exception("Erro ao inserir máquina. Erro: " + ex.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método responsável por editar uma máquina
     *
     * @param m máquina que será editada
     * @throws Exception disparada caso ocorra algum erro
     */
    public void editar(Maquina m) throws Exception {
        try {
            ConexaoBD.AbrirConexao();
            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("UPDATE maquina SET ip=?, porta=?, status=?, dataAcesso=?, idUsuario=? WHERE mac=?");
            pst.setString(1, m.getIp());
            pst.setString(2, m.getPorta());
            pst.setString(3, m.getStatus());
            pst.setTimestamp(4, new Timestamp(m.getDataAcesso().getTime()));
            pst.setInt(5, m.getP().getIdPessoa());
            pst.setString(6, m.getMac());

            pst.execute();
        } catch (Exception ex) {
            throw new Exception("Erro ao editar a máquina. Erro: " + ex.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método responsável por remover do banco de dados uma máquina
     *
     * @param mac MAC adress que será utilizado para remover o computador, o
     * mesmo trabalha como uma chave primária
     * @throws Exception disparada caso ocorra algum erro durante o processo de
     * remoção
     */
    public void remover(String mac) throws Exception {
        try {
            ConexaoBD.AbrirConexao();
            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("DELETE FROM maquina WHERE mac=?");
            pst.setString(1, mac);

            pst.execute();
        } catch (Exception ex) {
            throw new Exception("Erro ao excluir a máquina. Erro: " + ex.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método responsável por verificar se o usuário já está logado em outra
     * máquina
     *
     * @param p pessoa que será validada
     * @return retorna true ou false
     * @throws Exception disparada caso algum registro já exista
     */
    public boolean usuarioLogado(Pessoa p) throws Exception {
        try {
            ConexaoBD.AbrirConexao();
            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("SELECT COUNT(*) FROM maquina WHERE idUsuario=? and status='A' ");
            pst.setInt(1, p.getIdPessoa());
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
            throw new Exception("Erro ao pesquisar usuário logado em máquinas. Erro: " + e.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método responsável por verificar se uma máquina já está cadastrada
     *
     * @param mac mac que será verificado no banco de dados
     * @return retorna true ou false
     * @throws Exception disparada caso ocorra algum erro
     */
    public boolean maquinaCadastrada(String mac) throws Exception {
        try {
            ConexaoBD.AbrirConexao();
            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("SELECT COUNT(*) FROM maquina WHERE mac=?");
            pst.setString(1, mac);
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
            throw new Exception("Erro ao pesquisar a máquina no banco de dados. Erro: " + e.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método responsável por realizar a pesquisa de máquinas
     *
     * @param dataAcessoInicial data de acesso inicial de pesquisa, pode ser
     * null caso esse parâmetro não seja adequado na pesquisa em questão
     * @param dataAcessoFinal data de acesso final de pesquisa, pode ser null
     * caso esse parâmetro não seja adequado na pesquisa em questão
     * @param status status da máquina, pode ser "" caso esse parâmetro não seja
     * adequado na pesquisa em questão
     * @return retorna um ArrayList de Máquinas encontradas
     * @throws Exception disparada caso ocorra algum erro na pesquisa
     */
    public ArrayList pesquisar(Date dataAcessoInicial, Date dataAcessoFinal, String status) throws Exception {
        try {
            ArrayList<Maquina> maquinas = new ArrayList<>();
            ConexaoBD.AbrirConexao();

            PreparedStatement stmt = ConexaoBD.getConnection().prepareStatement("SELECT m.*, p.id idPessoa, p.nome nomePessoa FROM maquina m INNER JOIN pessoa p ON m.idUsuario = p.id " + geraWherePesquisar(dataAcessoInicial, dataAcessoFinal, status) + " ORDER BY m.dataAcesso");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Maquina m = new Maquina();

                m.setDataAcesso(rs.getTimestamp("dataAcesso"));
                m.setId(rs.getInt("id"));
                m.setIp(rs.getString("ip"));
                m.setMac(rs.getString("mac"));
                m.setPorta(rs.getString("porta"));

                Funcionario f = new Funcionario();
                f.setIdPessoa(rs.getInt("idPessoa"));
                f.setNome(rs.getString("nomePessoa"));
                m.setP(f);

                if (rs.getString("status").equals("A")) {
                    m.setStatus("Ativo");
                } else {
                    m.setStatus("Inativo");
                }

                maquinas.add(m);
            }

            rs.close();
            stmt.close();
            return (maquinas);
        } catch (Exception ex) {
            throw new Exception("Erro ao pesquisar máquina. Erro: " + ex.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método responsável por retornar uma string com o WHERE para a consulta
     * SQL
     *
     * @param dataAcessoInicial data de acesso inicial de pesquisa, pode ser
     * null caso esse parâmetro não seja adequado na pesquisa em questão
     * @param dataAcessoFinal data de acesso final de pesquisa, pode ser null
     * caso esse parâmetro não seja adequado na pesquisa em questão
     * @param status status da máquina
     * @return retorna uma string com o WHERE montado
     */
    private String geraWherePesquisar(Date dataAcessoInicial, Date dataAcessoFinal, String status) {
        String where = "WHERE ";

        if (!status.equals("Todos")) {
            where += "m.status='" + status.substring(0, 1) + "' AND ";
        }

        if (dataAcessoInicial != null) {
            where += "m.dataAcesso >= '" + Uteis.converteData("yyyy-MM-dd", dataAcessoInicial) + " 00:00:00' AND ";
        }

        if (dataAcessoFinal != null) {
            where += "m.dataAcesso <= '" + Uteis.converteData("yyyy-MM-dd", dataAcessoFinal) + " 23:59:59' AND ";
        }

        return where += "1=1";
    }
}
