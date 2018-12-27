package DAO;

import ConexaoDB.ConexaoBD;
import Iterator_Pessoa.IteratorPessoa;
import Model.Permissao;
import Model.Pessoa;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Classe do tipo DAO relacionada as permissões da aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public class PermissaoDAO {

    /**
     * Método responsável por inserir uma permissão
     *
     * @param p permissão que será inserido
     * @throws Exception disparada durante o processo de inserção
     */
    public void inserir(Permissao p) throws Exception {
        try {
            ConexaoBD.AbrirConexao();
            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("INSERT INTO permissao (descricao, status) VALUES(?,?)");
            pst.setString(1, p.getDescricao());
            if (p.getStatus().equals("Ativo")) {
                pst.setString(2, "A");
            } else {
                pst.setString(2, "I");
            }
            pst.execute();
        } catch (Exception ex) {
            throw new Exception("Erro ao inserir Permissão. Erro: " + ex.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método responsável por editar uma permissão
     *
     * @param p permissão que será editada
     * @throws Exception disparada durante o processo de edição
     */
    public void editar(Permissao p) throws Exception {
        try {
            ConexaoBD.AbrirConexao();
            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("UPDATE permissao SET status=?, descricao=? WHERE id=?");
            pst.setString(1, p.getStatus().substring(0, 1));
            pst.setString(2, p.getDescricao());
            pst.setInt(3, p.getId());

            pst.execute();
        } catch (Exception ex) {
            throw new Exception("Erro ao remover a permissão. Erro: " + ex.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método responsável por pesquisar permissões
     *
     * @param descricao descrição da permissão, pode ser "" caso esse parâmetro
     * não seja adequado na pesquisa em questão
     * @param status status da permissão
     * @return retorna um ArrayList de permissões pesquisadas
     * @throws Exception disparada durante o processo de pesquisa
     */
    public ArrayList pesquisar(String descricao, String status) throws Exception {
        try {
            ArrayList<Permissao> permissoes = new ArrayList<>();
            ConexaoBD.AbrirConexao();

            PreparedStatement stmt = ConexaoBD.getConnection().prepareStatement("SELECT * FROM permissao " + geraWherePesquisar(descricao, status));;
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Permissao p = new Permissao();
                p.setId(Integer.parseInt(rs.getString("id")));
                p.setDescricao(rs.getString("descricao"));

                if (rs.getString("status").equals("A")) {
                    p.setStatus("Ativo");
                } else {
                    p.setStatus("Inativo");
                }
                permissoes.add(p);
            }

            rs.close();
            stmt.close();
            return (permissoes);
        } catch (Exception ex) {
            throw new Exception("Erro ao pesquisar permissões. Erro: " + ex.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método responsável por retornar uma string com o WHERE para a consulta
     * SQL
     *
     * @param descricao descrição da permissão, pode ser "" caso esse parâmetro
     * não seja adequado na pesquisa em questão
     * @param status status da permissão
     * @return retorna uma string com o WHERE para a consulta SQL
     */
    public String geraWherePesquisar(String descricao, String status) {
        String where = "WHERE ";
        if (!descricao.equals("")) {
            where += "descricao LIKE '%" + descricao + "%' AND ";
        }

        if (!status.equals("Todos")) {
            where += "status='" + status.substring(0, 1) + "' AND ";
        }

        return where += "1=1";
    }

    /**
     * Método responsável por verificar se uma descrição de permissão já existe
     * ou não
     *
     * @param permissao permissão que será validada
     * @return retorna true ou false
     * @throws Exception disparada durante o processo de pesquisa
     */
    public boolean existeDescricaoPermissao(String permissao) throws Exception {
        try {
            ConexaoBD.AbrirConexao();
            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("SELECT COUNT(*) FROM permissao WHERE descricao=?");
            pst.setString(1, permissao);
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
            throw new Exception("Erro ao pesquisar descrição da permissão. Erro: " + e.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método responsável por pesquisar usuários por permissão
     *
     * @param p permissão que será utilizada na pesquisa, pode-se passar null
     * caso esse parâmetro não seja adequado na pesquisa em questão
     * @param nomeUsuario que será utilizado na pesquisa, pode-se passar "" caso
     * esse parâmetro não seja adequado na pesquisa em questão
     * @param nomeLogin que será utilizado na pesquisa pode-se passar "" caso
     * esse parâmetro não seja adequado na pesquisa em questão
     * @param possuiPermissao parâmetro que verificará se o usuário possui ou
     * não há permissão
     * @return retorna uma lista com todos usuários encontrados
     * @throws Exception disparada caso ocorra algum erro durante a pesquisa
     */
    public ArrayList<Pessoa> pesquisarPessoaPorPermissao(Permissao p, String nomeUsuario, String nomeLogin, String possuiPermissao) throws Exception {
        try {
            ArrayList<Pessoa> pessoas = new ArrayList<>();
            ConexaoBD.AbrirConexao();

            /*SELECT * FROM
(SELECT pe.*,ppp.idPermissao,'Funcionário' tipoPessoa FROM pessoa pe
		 LEFT JOIN pessoapossuipermissao ppp ON pe.id = ppp.idPessoa
         INNER JOIN funcionario f ON f.idPessoa = pe.id  AND pe.status = 'A'
	     UNION
SELECT pe.*,ppp.idPermissao,'Paciente' tipoPessoa FROM pessoa pe
		 LEFT JOIN pessoapossuipermissao ppp ON pe.id = ppp.idPessoa 
         INNER JOIN paciente pa ON pa.idPessoa = pe.id AND pe.status = 'A'
		 UNION
SELECT pe.*,ppp.idPermissao,'Responsável' tipoPessoa FROM pessoa pe
		 LEFT JOIN pessoapossuipermissao ppp ON pe.id = ppp.idPessoa 
         INNER JOIN responsavel r ON r.idPessoa = pe.id AND pe.status = 'A') a 
         WHERE a.idPermissao IS NULL OR a.idPermissao=1*/
            String query = "SELECT * FROM\n"
                    + "(SELECT pe.*,ppp.idPermissao,'Funcionário' tipoPessoa, f.id idFuncao FROM pessoa pe\n"
                    + "		 LEFT JOIN pessoapossuipermissao ppp ON pe.id = ppp.idPessoa AND ppp.idPermissao=@@IDPERMISSAO\n"
                    + "         INNER JOIN funcionario f ON f.idPessoa = pe.id  AND pe.status = 'A'\n"
                    + "	     UNION\n"
                    + "SELECT pe.*,ppp.idPermissao,'Paciente' tipoPessoa, pa.id idFuncao FROM pessoa pe\n"
                    + "		 LEFT JOIN pessoapossuipermissao ppp ON pe.id = ppp.idPessoa AND ppp.idPermissao=@@IDPERMISSAO\n"
                    + "         INNER JOIN paciente pa ON pa.idPessoa = pe.id AND pe.status = 'A'\n"
                    + "		 UNION\n"
                    + "SELECT pe.*,ppp.idPermissao,'Responsável' tipoPessoa, r.id idFuncao FROM pessoa pe\n"
                    + "		 LEFT JOIN pessoapossuipermissao ppp ON pe.id = ppp.idPessoa AND ppp.idPermissao=@@IDPERMISSAO\n"
                    + "         INNER JOIN responsavel r ON r.idPessoa = pe.id AND pe.status = 'A') a " + geraWherePesquisarPessoaPorPermissao(nomeUsuario, nomeLogin, possuiPermissao);

            query = query.replace("@@IDPERMISSAO", String.valueOf(p.getId()));
            
            PreparedStatement stmt = ConexaoBD.getConnection().prepareStatement(query);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Pessoa pessoa = Pessoa.factory(rs.getString("tipoPessoa"));

                pessoa.setIdEspecifico(rs.getInt("idFuncao"));
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
                pessoa.setStatus("Ativo");

                if (rs.getString("idPermissao") != null) {
                    pessoa.getPermissoes().add(p);
                }

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
     * Método responsável por retornar uma string com o WHERE para a consulta
     * SQL
     *
     * @param nomeUsuario que será utilizado na pesquisa, pode-se passar "" caso
     * esse parâmetro não seja adequado na pesquisa em questão
     * @param nomeLogin que será utilizado na pesquisam pode-se passar "" caso
     * esse parâmetro não seja adequado na pesquisa em questão
     * @param possuiPermissao parâmetro que verificará se o usuário possui ou
     * não há permissão
     * @return retorna uma string com o WHERE para a consulta SQL
     */
    private String geraWherePesquisarPessoaPorPermissao(String nomeUsuario, String nomeLogin, String possuiPermissao) {
        String where = "";

        if (!nomeUsuario.equals("")) {
            where += " a.nome like '%" + nomeUsuario + "%' AND ";
        }

        if (!nomeLogin.equals("")) {
            where += " a.nomeLogin like '%" + nomeLogin + "%' AND ";
        }

        if (possuiPermissao.equals("Possui")) {
            where += " a.idPermissao IS NOT NULL AND ";
        } else if (possuiPermissao.equals("Não Possui")) {
            where += " a.idPermissao IS NULL AND ";
        }

        where = "WHERE " + where + " 1=1";

        return where;
    }

    /**
     * Método responsável por liberar uma determinada permissão a determinados
     * usuários
     *
     * @param listaPessoa usuários que sofrerão a alteração
     * @param p permissão em questão
     * @throws Exception disparada durante o processo de pesquisa ou atualização
     */
    public void liberarPermissao(ArrayList<Pessoa> listaPessoa, Permissao p) throws Exception {
        try {
            ConexaoBD.AbrirConexao();
            ConexaoBD.setAutoCommit(false);
            for (IteratorPessoa ip = new IteratorPessoa(listaPessoa); !ip.isDone();ip.next()) {
                PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("INSERT INTO pessoapossuipermissao (idPessoa, idPermissao) VALUES(?,?)");
                pst.setInt(1, ip.currentItem().getIdPessoa());
                pst.setInt(2, p.getId());
                pst.execute();
            }

            ConexaoBD.getConnection().commit();
        } catch (Exception ex) {
            ConexaoBD.getConnection().rollback();
            throw new Exception("Erro ao inserir permissão ao(s) usuário(s). Erro: " + ex.getMessage());
        } finally {
            ConexaoBD.setAutoCommit(true);
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método responsável por retirar uma determinada permissão a determinados
     * usuários
     *
     * @param listaPessoa usuários que sofrerão a alteração
     * @param p permissão em questão
     * @throws Exception disparada durante o processo de pesquisa ou atualização
     */
    public void retirarPermissao(ArrayList<Pessoa> listaPessoa, Permissao p) throws Exception {
        try {
            ConexaoBD.AbrirConexao();
            ConexaoBD.setAutoCommit(false);
            for (IteratorPessoa ip = new IteratorPessoa(listaPessoa); !ip.isDone();ip.next()) {
                PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("DELETE FROM pessoapossuipermissao WHERE idPessoa=? AND idPermissao=?");
                pst.setInt(1, ip.currentItem().getIdPessoa());
                pst.setInt(2, p.getId());
                pst.execute();
            }

            ConexaoBD.getConnection().commit();
        } catch (Exception ex) {
            ConexaoBD.getConnection().rollback();
            throw new Exception("Erro ao remover permissão do(s) usuário(s). Erro: " + ex.getMessage());
        } finally {
            ConexaoBD.setAutoCommit(true);
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método responsável por retornar as permissões que estão liberadas a
     * determinado usuário
     *
     * @param idPessoa usuário em questão
     * @return retorna um ArrayList com as permissões liberadas para tal usuário
     * @throws Exception disparada durante o processo de pesquisa
     */
    public ArrayList<Permissao> pesquisarPermissoesPorPessoa(String idPessoa) throws Exception {
        try {
            ArrayList<Permissao> permissoes = new ArrayList<>();
            ConexaoBD.AbrirConexao();

            PreparedStatement stmt = ConexaoBD.getConnection().prepareStatement("SELECT per.* FROM permissao per "
                    + "INNER JOIN pessoapossuipermissao ppp ON per.id = ppp.idPermissao "
                    + "INNER JOIN pessoa pe ON ppp.idPessoa = pe.id AND pe.id=?");
            stmt.setString(1, idPessoa);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Permissao p = new Permissao();
                p.setId(Integer.parseInt(rs.getString("id")));
                p.setDescricao(rs.getString("descricao"));

                if (rs.getString("status").equals("A")) {
                    p.setStatus("Ativo");
                } else {
                    p.setStatus("Inativo");
                }
                permissoes.add(p);
            }

            rs.close();
            stmt.close();
            return (permissoes);
        } catch (Exception ex) {
            throw new Exception("Erro ao pesquisar permissões. Erro: " + ex.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
    }
}
