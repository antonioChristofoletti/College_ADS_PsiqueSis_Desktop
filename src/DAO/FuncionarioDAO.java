/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import ConexaoDB.ConexaoBD;
import Model.Funcionario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Classe do tipo DAO relacionada aos funcionário da aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public class FuncionarioDAO extends PessoaDAO {

    /**
     * Método responsável por inserir um funcionário
     *
     * @param f funcionário que será inserido
     * @throws Exception disparado caso ocorra algum erro
     */
    public void inserirFuncionario(Funcionario f) throws Exception {
        try {
            ConexaoBD.AbrirConexao();
            ConexaoBD.setAutoCommit(false);
            this.inserirPessoa(f);

            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("INSERT INTO funcionario (email,idPessoa) VALUES (?,?)");
            pst.setString(1, f.getEmail());
            pst.setInt(2, this.retornaUltimoCodigoPessoa());
            pst.execute();
            ConexaoBD.getConnection().commit();
        } catch (Exception e) {
            ConexaoBD.getConnection().rollback();
            throw new Exception("Erro ao inserir funcionário. Erro: " + e.getMessage());
        } finally {
            ConexaoBD.setAutoCommit(true);
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método responsável por editar um funcionário
     *
     * @param f funcionário que será editado
     * @param senhaAntiga senha antes de realizar a atualização, importante por conta da criptografia MD5
     * @throws Exception disparado caso ocorra algum erro
     */
    public void editarFuncionario(Funcionario f, String senhaAntiga) throws Exception {
        try {
            ConexaoBD.AbrirConexao();
            ConexaoBD.setAutoCommit(false);
            this.editarPessoa(f, senhaAntiga);

            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("UPDATE funcionario SET email=? WHERE ID=?");
            pst.setString(1, f.getEmail());
            pst.setInt(2, f.getIdEspecifico());
            pst.execute();
            ConexaoBD.getConnection().commit();
        } catch (Exception e) {
            ConexaoBD.getConnection().rollback();
            throw new Exception("Erro ao editar funcionário. Erro: " + e.getMessage());
        } finally {
            ConexaoBD.setAutoCommit(true);
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método responsável por pesquisar funcionário
     *
     * @param nome nome do responsável, pode ser "" caso esse parâmetro não seja
     * adequado na pesquisa em questão
     * @param rg rg do responsável, pode ser "" caso esse parâmetro não seja
     * adequado na pesquisa em questão
     * @param status status do responsável
     * @return retorna um ArrayList de funcionarios
     * @throws Exception disparado caso ocorra algum erro durante a pesquisa
     */
    public ArrayList pesquisar(String nome, String rg, String status) throws Exception {
        try {
            ArrayList<Funcionario> funcionarios = new ArrayList<>();
            ConexaoBD.AbrirConexao();

            PreparedStatement stmt = ConexaoBD.getConnection().prepareStatement("SELECT *,f.id idFuncionario FROM pessoa p INNER JOIN funcionario f ON f.idPessoa = p.id " + geraWhere(nome, rg, status));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Funcionario f = new Funcionario();
                f.setIdPessoa(Integer.parseInt(rs.getString("idPessoa")));
                f.setIdEspecifico(Integer.parseInt(rs.getString("idFuncionario")));
                f.setBairro(rs.getString("bairro"));
                f.setCidade(rs.getString("cidade"));
                f.setCpf(rs.getString("cpf"));
                f.setEmail(rs.getString("email"));
                f.setLogradouro(rs.getString("logradouro"));
                f.setNome(rs.getString("nome"));
                f.setNomeLogin(rs.getString("nomeLogin"));
                f.setRg(rs.getString("rg"));
                f.setSenha(rs.getString("senha"));
                f.setTelefone1(rs.getString("telefone1"));
                f.setTelefone2(rs.getString("telefone2"));

                switch (rs.getString("status")) {
                    case "A": {
                        f.setStatus("Ativo");
                        break;
                    }

                    case "I": {
                        f.setStatus("Inativo");
                        break;
                    }
                }
                funcionarios.add(f);
            }

            rs.close();
            stmt.close();
            return (funcionarios);
        } catch (Exception ex) {
            throw new Exception("Erro ao pesquisar funcionário. Erro: " + ex.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método responsável por retornar uma string com o WHERE para a consulta SQL
     * @param nome nome do responsável, pode ser "" caso esse parâmetro não seja
     * adequado na pesquisa em questão
     * @param rg rg do responsável, pode ser "" caso esse parâmetro não seja
     * adequado na pesquisa em questão
     * @param status  status do responsável
     * @return retorna uma string com o WHERE para a consulta SQL
     */
    public String geraWhere(String nome, String rg, String status) {
        String where = "WHERE ";

        if (!nome.equals("")) {
            where += "p.nome LIKE '%" + nome + "%' AND ";
        }

        if (!rg.equals("")) {
            where += "p.rg LIKE '%" + rg + "%' AND ";
        }

        if (!status.equals("Todos")) {
            where += "p.status='" + status.substring(0, 1) + "' AND ";
        }

        return where += " 1=1";
    }

    /**
     * Método responsável por verificar se um email de funcionário existe ou não
     *
     * @param email a ser verificado
     * @return retorna true ou false
     * @throws Exception disparada caso ocorra algum erro durante a pesquisa das
     * informações
     */
    public boolean existeEmail(String email) throws Exception {
        try {
            ConexaoBD.AbrirConexao();
            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("SELECT COUNT(*) FROM funcionario WHERE email=?");
            pst.setString(1, email);
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
            throw new Exception("Erro ao verificar nome e-mail usuário. Erro: " + e.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método responsável por autenticar um funcionário
     *
     * @param f funcionário a ser autenticado
     * @return null se não for validado ou Funcionario caso se ja validado
     * @throws Exception disparada caso a ocorra algum erro na autenticação
     */
    public Funcionario autenticarFuncionario(Funcionario f) throws Exception {
        try {
            ConexaoBD.AbrirConexao();
            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("SELECT *,f.id idFuncionario FROM pessoa p INNER JOIN funcionario f ON f.idPessoa = p.id AND p.status='A' AND p.senha =MD5(?) AND p.nomeLogin=?");
            pst.setString(1, f.getSenha());
            pst.setString(2, f.getNomeLogin());
            ResultSet rs = pst.executeQuery();

            f = null;
            while (rs.next()) {
                f = new Funcionario();
                f.setIdPessoa(Integer.parseInt(rs.getString("idPessoa")));
                f.setIdEspecifico(Integer.parseInt(rs.getString("idFuncionario")));
                f.setBairro(rs.getString("bairro"));
                f.setCidade(rs.getString("cidade"));
                f.setCpf(rs.getString("cpf"));
                f.setEmail(rs.getString("email"));
                f.setLogradouro(rs.getString("logradouro"));
                f.setNome(rs.getString("nome"));
                f.setNomeLogin(rs.getString("nomeLogin"));
                f.setRg(rs.getString("rg"));
                f.setSenha(rs.getString("senha"));
                f.setTelefone1(rs.getString("telefone1"));
                f.setTelefone2(rs.getString("telefone2"));

                switch (rs.getString("status")) {
                    case "A": {
                        f.setStatus("Ativo");
                        break;
                    }

                    case "I": {
                        f.setStatus("Inativo");
                        break;
                    }
                }
                pst.close();
                rs.close();
                return f;
            }

            pst.close();
            rs.close();
            throw new Exception("A senha ou o nome do funcionário informado está incorreta");
        } catch (Exception e) {
            throw new Exception("Erro ao autenticar o usuário. Erro: " + e.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
    }
}
