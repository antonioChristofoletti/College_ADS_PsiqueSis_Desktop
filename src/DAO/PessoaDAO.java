/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import ConexaoDB.ConexaoBD;
import Model.Permissao;
import Model.Pessoa;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Classe do tipo DAO relacionada a classe Pessoa da aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public abstract class PessoaDAO {

    /**
     * Método responsável por inserir uma pessoa
     *
     * @param p pessoa que será inserida
     * @throws Exception disparada durante o processo de inserção
     */
    protected void inserirPessoa(Pessoa p) throws Exception {
        try {
            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("INSERT INTO pessoa (rg,cpf,nome, telefone1, telefone2, nomeLogin, senha, cidade, logradouro,bairro, status) VALUES (?,?,?,?,?,?,MD5(?),?,?,?,?)");
            pst.setString(1, p.getRg());
            pst.setString(2, p.getCpf());
            pst.setString(3, p.getNome());
            pst.setString(4, p.getTelefone1());
            pst.setString(5, p.getTelefone2());

            pst.setString(6, p.getNomeLogin());
            pst.setString(7, p.getSenha().substring(0, p.getSenha().length() / 2));
            pst.setString(8, p.getCidade());
            pst.setString(9, p.getLogradouro());
            pst.setString(10, p.getBairro());
            pst.setString(11, p.getStatus().substring(0, 1));

            pst.execute();
        } catch (Exception e) {
            throw new Exception("Erro ao inserir pessoa. Erro: " + e.getMessage());
        }
    }

    /**
     * Método responsável por editar uma pessoa
     *
     * @param p pessoa que será editada
     * @param senhaAntiga senha antes de realizar a atualização, importante por
     * conta da criptografia MD5
     * @throws Exception disparada durante o processo de edição
     */
    protected void editarPessoa(Pessoa p, String senhaAntiga) throws Exception {
        try {
            PreparedStatement pst = null;

            if (!p.getSenha().equals(senhaAntiga + senhaAntiga)) {
                pst = ConexaoBD.getConnection().prepareStatement("UPDATE pessoa SET rg=?,cpf=?,nome=?, telefone1=?, telefone2=?, nomeLogin=?, senha=MD5(?), cidade=?, logradouro=?,bairro=?, status=? WHERE id=?");
            } else {
                pst = ConexaoBD.getConnection().prepareStatement("UPDATE pessoa SET rg=?,cpf=?,nome=?, telefone1=?, telefone2=?, nomeLogin=?, cidade=?, logradouro=?,bairro=?, status=? WHERE id=?");
            }

            int posicaoAtributo = 1;
            pst.setString(posicaoAtributo++, p.getRg());
            pst.setString(posicaoAtributo++, p.getCpf());
            pst.setString(posicaoAtributo++, p.getNome());
            pst.setString(posicaoAtributo++, p.getTelefone1());
            pst.setString(posicaoAtributo++, p.getTelefone2());
            pst.setString(posicaoAtributo++, p.getNomeLogin());

            if (!p.getSenha().equals(senhaAntiga + senhaAntiga)) {
                pst.setString(posicaoAtributo++, p.getSenha().substring(0, p.getSenha().length() / 2));
            }

            pst.setString(posicaoAtributo++, p.getCidade());
            pst.setString(posicaoAtributo++, p.getLogradouro());
            pst.setString(posicaoAtributo++, p.getBairro());
            pst.setString(posicaoAtributo++, p.getStatus().substring(0, 1));
            pst.setInt(posicaoAtributo++, p.getIdPessoa());

            pst.execute();
        } catch (Exception e) {
            throw new Exception("Erro ao editar pessoa. Erro: " + e.getMessage());
        }
    }

    /**
     * Método responsável por verificar se um RG já existe no sistema ou não
     *
     * @param rg rg que será validado
     * @return retorna true ou false
     * @throws Exception disparada durante o processo de validação
     */
    public boolean existeRG(String rg) throws Exception {
        try {
            ConexaoBD.AbrirConexao();
            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("SELECT COUNT(*) FROM pessoa WHERE rg=?");
            pst.setString(1, rg);
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
            throw new Exception("Erro ao verificar RG. Erro: " + e.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método responsável por verificar se um CPF já existe no sistema ou não
     *
     * @param cpf que será validado
     * @return retorna true ou false
     * @throws Exception disparada durante o processo de validação
     */
    public boolean existeCPF(String cpf) throws Exception {
        try {
            ConexaoBD.AbrirConexao();
            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("SELECT COUNT(*) FROM pessoa WHERE cpf=?");
            pst.setString(1, cpf);
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
            throw new Exception("Erro ao verificar CPF. Erro: " + e.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método responsável por verificar se um telefone já existe no sistema ou
     * não
     *
     * @param telefone que será validado
     * @return retorna true ou false
     * @throws Exception disparada durante o processo de validação
     */
    public boolean existeTelefone(String telefone) throws Exception {
        try {
            ConexaoBD.AbrirConexao();
            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("SELECT COUNT(*) FROM pessoa WHERE telefone1=? OR telefone2=?");
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
     * Método responsável por verificar se um nome de usuário já existe no
     * sistema ou não
     *
     * @param nomeUsuario nome de usuário que será validado
     * @return retorna true ou false
     * @throws Exception disparada durante o processo de validação
     */
    public boolean existeUsuarioLogin(String nomeUsuario) throws Exception {
        try {
            ConexaoBD.AbrirConexao();
            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("SELECT COUNT(*) FROM pessoa WHERE nomeLogin=?");
            pst.setString(1, nomeUsuario);
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
            throw new Exception("Erro ao verificar nome usuário. Erro: " + e.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método responsável por retornar o último código de pessoa inserida no
     * banco de dados
     *
     * @return retorna o último código de pessoa inserida no banco de dados
     * @throws Exception disparado caso ocorra algum erro na consulta
     */
    public int retornaUltimoCodigoPessoa() throws Exception {
        try {
            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("SELECT MAX(id) codigoPessoa FROM pessoa");
            pst.execute();

            ResultSet rs = pst.executeQuery();
            int codigoOrcamento = 1;
            if (rs.next()) {
                codigoOrcamento = rs.getInt("codigoPessoa");
            }

            return codigoOrcamento;
        } catch (Exception ex) {
            throw new Exception("Erro ao pesquisar código pessoa. Erro: " + ex.getMessage());
        }
    }

    /**
     * Método responsável por retornar uma pessoa de acordo com o id
     *
     * @param idPessoa id da pessoa que será pesquisada
     * @return retorna uma pessoa
     * @throws Exception disparada caso ocorra algum erro durante a pesquisa
     */
    public Pessoa retornaPessoa(String idPessoa) throws Exception {
        try {
            ConexaoBD.AbrirConexao();

            String query = "SELECT * FROM"
                    + "(SELECT  pe.*,'Funcionário' tipoPessoa,f.id idEspecifico FROM pessoa pe "
                    + "         INNER JOIN funcionario f ON f.idPessoa = pe.id  AND pe.status = 'A' "
                    + "	      UNION "
                    + "SELECT pe.*,'Paciente' tipoPessoa, pa.id idEspecifico FROM pessoa pe "
                    + "         INNER JOIN paciente pa ON pa.idPessoa = pe.id AND pe.status = 'A'"
                    + "         UNION "
                    + "SELECT pe.*,'Responsável' tipoPessoa, r.id idEspecifico FROM pessoa pe "
                    + "         INNER JOIN responsavel r ON r.idPessoa = pe.id AND pe.status = 'A') a WHERE a.id = ?";

            PreparedStatement stmt = ConexaoBD.getConnection().prepareStatement(query);
            stmt.setString(1, idPessoa);
            ResultSet rs = stmt.executeQuery();

            Pessoa p = null;
            while (rs.next()) {
                p = Pessoa.factory(rs.getString("tipoPessoa"));
                p.setIdPessoa(Integer.parseInt(rs.getString("id")));
                p.setIdEspecifico(Integer.parseInt(rs.getString("idEspecifico")));
                p.setBairro(rs.getString("bairro"));
                p.setCidade(rs.getString("cidade"));
                p.setCpf(rs.getString("cpf"));
                p.setLogradouro(rs.getString("logradouro"));
                p.setNome(rs.getString("nome"));
                p.setNomeLogin(rs.getString("nomeLogin"));
                p.setRg(rs.getString("rg"));
                p.setSenha(rs.getString("senha"));
                p.setTelefone1(rs.getString("telefone1"));
                p.setTelefone2(rs.getString("telefone2"));
            }

            rs.close();
            stmt.close();
            return p;
        } catch (Exception ex) {
            throw new Exception("Erro ao pesquisar pessoa. Erro: " + ex.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método responsável por realizar uma pesquisa geral de pessoa,
     * possibilitando trazer funcionário, paciente ou responsável
     *
     * @param nome nome da pessoa, será utilizado na consulta, pode ser "" caso
     * tal campo não seja relevante na pesquisa em questão
     * @param rg rg da pessoa, será utilizado na consulta, pode ser "" caso tal
     * campo não seja relevante na pesquisa em questão
     * @param status status da pessoa, será utilizado na consulta, pode ser ""
     * caso tal campo não seja relevante na pesquisa em questão
     * @param paciente true caso deseje pesquisar pacientes ou falso para o
     * inverso
     * @param funcionario true caso deseje pesquisar funcionários ou falso para
     * o inverso
     * @param responsavel true caso deseje pesquisar responsável ou falso para o
     * inverso
     * @return retorna um ArrayList com as informações encontradas
     * @throws Exception disparada caso algum erro ocorra
     */
    public ArrayList<Pessoa> pesquisarPessoas(String nome, String rg, String status, Boolean paciente, Boolean funcionario, Boolean responsavel) throws Exception {
        try {
            ArrayList<Pessoa> pessoas = new ArrayList<>();
            ConexaoBD.AbrirConexao();

            /*SELECT * FROM
(SELECT pe.*,'Funcionário' tipoPessoa, f.id idEspecifico FROM pessoa pe
         INNER JOIN funcionario f ON f.idPessoa = pe.id
	     UNION
SELECT pe.*,'Paciente' tipoPessoa, pa.id idEspecifico FROM pessoa pe
         INNER JOIN paciente pa ON pa.idPessoa = pe.id
		 UNION
SELECT pe.*,'Responsável' tipoPessoa, r.id idEspecifico FROM pessoa pe
         INNER JOIN responsavel r ON r.idPessoa = pe.id) a */
            String query = "SELECT * FROM\n"
                    + "(SELECT pe.*,'Funcionário' tipoPessoa, f.id idEspecifico FROM pessoa pe\n"
                    + "         INNER JOIN funcionario f ON f.idPessoa = pe.id\n"
                    + "	     UNION\n"
                    + "SELECT pe.*,'Paciente' tipoPessoa, pa.id idEspecifico FROM pessoa pe\n"
                    + "         INNER JOIN paciente pa ON pa.idPessoa = pe.id\n"
                    + "		 UNION\n"
                    + "SELECT pe.*,'Responsável' tipoPessoa, r.id idEspecifico FROM pessoa pe\n"
                    + "         INNER JOIN responsavel r ON r.idPessoa = pe.id) a " + geraWherePesquisarPessoas(nome, rg, status, paciente, funcionario, responsavel) + " ORDER BY a.idEspecifico";

            PreparedStatement stmt = ConexaoBD.getConnection().prepareStatement(query);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Pessoa pessoa = Pessoa.factory(rs.getString("tipoPessoa"));

                pessoa.setIdEspecifico(rs.getInt("idEspecifico"));
                pessoa.setIdPessoa(Integer.parseInt(rs.getString("id")));
                pessoa.setBairro(rs.getString("bairro"));
                pessoa.setCidade(rs.getString("cidade"));
                pessoa.setCpf(rs.getString("cpf"));
                pessoa.setLogradouro(rs.getString("logradouro"));
                pessoa.setNome(rs.getString("nome"));
                pessoa.setNomeLogin(rs.getString("nomeLogin"));
                pessoa.setRg(rs.getString("rg"));
                pessoa.setSenha(rs.getString("senha"));
                pessoa.setTelefone1(rs.getString("telefone1"));
                pessoa.setTelefone2(rs.getString("telefone2"));
                pessoa.setStatus(rs.getString("status").equals("A") ? "Ativo" : "Inativo");

                pessoas.add(pessoa);
            }

            rs.close();
            stmt.close();
            return (pessoas);
        } catch (Exception ex) {
            throw new Exception("Erro ao pesquisar pessoas. Erro: " + ex.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método responsável por montar a cláusula where
     *
     * @param nome nome da pessoa, será utilizado na consulta, pode ser "" caso
     * tal campo não seja relevante na pesquisa em questão
     * @param rg rg da pessoa, será utilizado na consulta, pode ser "" caso tal
     * campo não seja relevante na pesquisa em questão
     * @param status status da pessoa, será utilizado na consulta, pode ser ""
     * caso tal campo não seja relevante na pesquisa em questão
     * @param paciente true caso deseje pesquisar pacientes ou falso para o
     * inverso
     * @param funcionario true caso deseje pesquisar funcionários ou falso para
     * o inverso
     * @param responsavel true caso deseje pesquisar responsável ou falso para o
     * inverso
     * @return retorna um where para a pesquisa
     */
    private String geraWherePesquisarPessoas(String nome, String rg, String status, Boolean paciente, Boolean funcionario, Boolean responsavel) {
        String where = "WHERE ";

        if (!nome.equals("")) {
            where += "a.nome LIKE '%" + nome + "%' AND ";
        }

        if (!rg.equals("")) {
            where += "a.rg LIKE '%" + rg + "%' AND ";
        }

        if (!status.equals("Todos")) {
            where += "a.status ='" + status.substring(0, 1) + "' AND ";
        }

        if (!paciente) {
            where += "a.tipoPessoa <> 'Paciente' AND ";
        }

        if (!funcionario) {
            where += "a.tipoPessoa <> 'Funcionário'  AND ";
        }

        if (!responsavel) {
            where += "a.tipoPessoa <> 'Responsável'  AND ";
        }

        return where += " 1=1";
    }
}
