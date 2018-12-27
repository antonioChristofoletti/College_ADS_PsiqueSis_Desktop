/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import ConexaoDB.ConexaoBD;
import Model.Atividade;
import Model.Localizacao;
import Model.Paciente;
import Model.Plano;
import Model.Responsavel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Classe do tipo DAO relacionada aos pacientes da aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public class PacienteDAO extends PessoaDAO {

    /**
     * Método responsável por inserir um paciente
     *
     * @param p paciente que será inserido
     * @throws Exception disparada durante o processo de inserção
     */
    public void inserirPaciente(Paciente p) throws Exception {
        try {
            ConexaoBD.AbrirConexao();
            ConexaoBD.setAutoCommit(false);

            this.inserirPessoa(p);

            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("INSERT INTO paciente (numeroPasta, capaz, dataInicio, idPlano, idPessoa) VALUES (?,?,?,?,?)");
            pst.setString(1, p.getNumeroPasta());
            pst.setString(2, p.getCapaz().substring(0, 1));
            pst.setTimestamp(3, new Timestamp(p.getDataInicio().getTime()));
            pst.setInt(4, p.getPlano().getId());
            pst.setInt(5, this.retornaUltimoCodigoPessoa());
            pst.execute();

            p.setIdEspecifico(retornaUltimoCodigoPaciente());

            for (int i = 0; i < p.getLocalizacoes().size(); i++) {
                pst = ConexaoBD.getConnection().prepareStatement("INSERT INTO pacientepossuilocalizacao (idLocalizacao,idPaciente) VALUES (?,?)");
                pst.setInt(1, p.getLocalizacoes().get(i).getId());
                pst.setInt(2, p.getIdEspecifico());
                pst.execute();
            }

            for (int i = 0; i < p.getPacienteAtividades().size(); i++) {
                pst = ConexaoBD.getConnection().prepareStatement("INSERT INTO pacienterealizaatividade (idPaciente,idAtividade,dataAtividade) VALUES (?,?,?)");
                pst.setInt(1, p.getIdEspecifico());
                pst.setInt(2, p.getPacienteAtividades().get(i).getId());
                pst.setTimestamp(3, new Timestamp(p.getPacienteAtividades().get(i).getDataAtividade().getTime()));
                pst.execute();
            }

            for (int i = 0; i < p.getResponsaveis().size(); i++) {
                pst = ConexaoBD.getConnection().prepareStatement("INSERT INTO pacientepossuiresponsavel (idPaciente,idResponsavel, parentesco) VALUES (?,?,?)");
                pst.setInt(1, p.getIdEspecifico());
                pst.setInt(2, p.getResponsaveis().get(i).getIdEspecifico());
                pst.setString(3, p.getResponsaveis().get(i).getParentesco());
                pst.execute();
            }

            ConexaoBD.getConnection().commit();
        } catch (Exception e) {
            ConexaoBD.getConnection().rollback();
            throw new Exception("Erro ao inserir paciente. Erro: " + e.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
            ConexaoBD.setAutoCommit(true);
        }
    }

    /**
     * Método responsável por editar um paciente
     *
     * @param p paciente que será editado
     * @param senhaAntiga senha antes de realizar a atualização, importante por
     * conta da criptografia MD5
     * @throws Exception disparada durante o processo de atualização
     */
    public void editarPaciente(Paciente p, String senhaAntiga) throws Exception {
        try {
            ConexaoBD.AbrirConexao();
            ConexaoBD.setAutoCommit(false);
            this.editarPessoa(p, senhaAntiga);

            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("UPDATE paciente SET numeroPasta=?, capaz=?, dataInicio=?, idPlano=? WHERE id=?");
            pst.setString(1, p.getNumeroPasta());
            pst.setString(2, p.getCapaz().substring(0, 1));
            pst.setTimestamp(3, new Timestamp(p.getDataInicio().getTime()));
            pst.setInt(4, p.getPlano().getId());
            pst.setInt(5, p.getIdEspecifico());
            pst.execute();

            pst = ConexaoBD.getConnection().prepareStatement("DELETE FROM pacientepossuilocalizacao WHERE idPaciente=?");
            pst.setInt(1, p.getIdEspecifico());
            pst.execute();

            pst = ConexaoBD.getConnection().prepareStatement("DELETE FROM pacienterealizaatividade WHERE idPaciente=?");
            pst.setInt(1, p.getIdEspecifico());
            pst.execute();

            pst = ConexaoBD.getConnection().prepareStatement("DELETE FROM pacientepossuiresponsavel WHERE idPaciente=?");
            pst.setInt(1, p.getIdEspecifico());
            pst.execute();

            for (int i = 0; i < p.getLocalizacoes().size(); i++) {
                pst = ConexaoBD.getConnection().prepareStatement("INSERT INTO pacientepossuilocalizacao (idLocalizacao,idPaciente) VALUES (?,?)");
                pst.setInt(1, p.getLocalizacoes().get(i).getId());
                pst.setInt(2, p.getIdEspecifico());
                pst.execute();
            }

            for (int i = 0; i < p.getPacienteAtividades().size(); i++) {
                pst = ConexaoBD.getConnection().prepareStatement("INSERT INTO pacienterealizaatividade (idPaciente,idAtividade,dataAtividade) VALUES (?,?,?)");
                pst.setInt(1, p.getIdEspecifico());
                pst.setInt(2, p.getPacienteAtividades().get(i).getId());
                pst.setTimestamp(3, new Timestamp(p.getPacienteAtividades().get(i).getDataAtividade().getTime()));

                pst.execute();
            }

            for (int i = 0; i < p.getResponsaveis().size(); i++) {
                pst = ConexaoBD.getConnection().prepareStatement("INSERT INTO pacientepossuiresponsavel (idPaciente,idResponsavel, parentesco) VALUES (?,?,?)");
                pst.setInt(1, p.getIdEspecifico());
                pst.setInt(2, p.getResponsaveis().get(i).getIdEspecifico());
                pst.setString(3, p.getResponsaveis().get(i).getParentesco());
                pst.execute();
            }

            ConexaoBD.getConnection().commit();
        } catch (Exception e) {
            ConexaoBD.getConnection().rollback();
            throw new Exception("Erro ao editar paciente. Erro: " + e.getMessage());
        } finally {
            ConexaoBD.setAutoCommit(true);
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método responsável por pesquisar pacientes
     *
     * @param nome nome do paciente, pode ser "" caso esse parâmetro não seja
     * adequado na pesquisa em questão
     * @param rg rg do paciente, pode ser "" caso esse parâmetro não seja
     * adequado na pesquisa em questão
     * @param status status do paciente
     * @param r responsável que será utilizado como parâmetr, caso não seja
     * adequado na pesquisa em questão pode-se utilizar null
     * @return retorna um ArrayList de pacientes pesquisados
     * @throws Exception disparada durante o processo de pesquisa
     */
    public ArrayList pesquisar(String nome, String rg, String status, Responsavel r) throws Exception {
        try {
            ArrayList<Paciente> pacientes = new ArrayList<>();
            ConexaoBD.AbrirConexao();
            
            String query = "SELECT DISTINCT pe.*,pa.*, pl.*, pe.id idPessoa, pa.id idPaciente, pl.id idPlano FROM pessoa pe "
                                                                                        + "INNER JOIN paciente pa ON pa.idPessoa = pe.id "
                                                                                        + "INNER JOIN pacientepossuiresponsavel ppr ON ppr.idPaciente = pa.id "
                                                                                        + "INNER JOIN plano pl ON pl.id = pa.idPlano " + geraWhere(nome, rg, status, r);

            PreparedStatement stmt = ConexaoBD.getConnection().prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Paciente p = new Paciente();
                p.setIdPessoa(Integer.parseInt(rs.getString("idPessoa")));
                p.setIdEspecifico(Integer.parseInt(rs.getString("idPaciente")));
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

                switch (rs.getString("status")) {
                    case "A": {
                        p.setStatus("Ativo");
                        break;
                    }

                    case "I": {
                        p.setStatus("Inativo");
                        break;
                    }
                }

                switch (rs.getString("capaz")) {
                    case "S": {
                        p.setCapaz("Sim");
                        break;
                    }

                    case "N": {
                        p.setCapaz("Não");
                        break;
                    }
                }

                p.setDataInicio(rs.getDate("dataInicio"));
                p.setNumeroPasta(rs.getString("numeroPasta"));

                Plano plano = new Plano();
                plano.setId(Integer.parseInt(rs.getString("idPlano")));
                plano.setQuantiConsultas(Integer.parseInt(rs.getString("quantiConsulta")));

                plano.setDescricao(rs.getString("descricao"));

                if (rs.getString("status").equals("A")) {
                    plano.setStatus("Ativo");
                } else {
                    plano.setStatus("Inativo");
                }

                plano.setNomeInstituicao(rs.getString("nomeInstituicao"));

                p.setPlano(plano);

                carregarInformacoesAtividadesPaciente(p);
                carregarInformacoesLocalizacoesPaciente(p);
                carregarInformacoesResponsaveisPaciente(p);
                pacientes.add(p);
            }

            rs.close();
            stmt.close();
            return (pacientes);
        } catch (Exception ex) {
            throw new Exception("Erro ao pesquisar paciente. Erro: " + ex.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método responsável por retornar uma string com o WHERE para a consulta
     * SQL
     *
     * @param nome nome dp paciente, pode ser "" caso esse parâmetro não seja
     * adequado na pesquisa em questão
     * @param rg rg do paciente, pode ser "" caso esse parâmetro não seja
     * adequado na pesquisa em questão
     * @param status status do paciente
     * @param r responsável que será utilizado como parâmetr, caso não seja
     * adequado na pesquisa em questão pode-se utilizar null
     * @return retorna uma string com o WHERE para a consulta SQL
     */
    public String geraWhere(String nome, String rg, String status, Responsavel r) {
        String where = "WHERE ";

        if (!nome.equals("")) {
            where += "pe.nome LIKE '%" + nome + "%' AND ";
        }

        if (!rg.equals("")) {
            where += "pe.rg LIKE '%" + rg + "%' AND ";
        }

        if (!status.equals("Todos")) {
            where += "pe.status='" + status.substring(0, 1) + "' AND ";
        }
        
        if(r != null)
        {
            where += " ppr.idResponsavel=" + String.valueOf(r.getIdEspecifico()) + " AND ";
        }
        
        return where += " 1=1";
    }

    /**
     * Método responsável por retornar o último código de paciente inserido no
     * banco de dados
     *
     * @return retorna o último código de paciente inserido no banco de dados
     * @throws Exception disparado caso ocorra algum erro na consulta
     */
    private int retornaUltimoCodigoPaciente() throws Exception {
        try {
            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("SELECT MAX(id) codigoPaciente FROM paciente");
            pst.execute();

            ResultSet rs = pst.executeQuery();
            int codigoOrcamento = 1;
            if (rs.next()) {
                codigoOrcamento = rs.getInt("codigoPaciente");
            }

            return codigoOrcamento;
        } catch (Exception ex) {
            throw new Exception("Erro ao pesquisar código paciente. Erro: " + ex.getMessage());
        }
    }

    /**
     * Método responsável por pesquisar e inserir as atividades executadas pelo
     * paciente
     *
     * @param p paciente que receberá as atividade
     * @throws SQLException disparada durante a fase de pesquisa
     */
    private void carregarInformacoesAtividadesPaciente(Paciente p) throws SQLException {
        ArrayList<Atividade> listaAtividades = new ArrayList();

        PreparedStatement stmt = ConexaoBD.getConnection().prepareStatement("SELECT * FROM pacienterealizaatividade pra INNER JOIN atividade a ON a.id = pra.idAtividade WHERE idPaciente=?");
        stmt.setInt(1, p.getIdEspecifico());
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Atividade a = new Atividade();
            a.setId(Integer.parseInt(rs.getString("id")));
            a.setDescricao(rs.getString("descricao"));
            a.setNome(rs.getString("nome"));
            a.setDataAtividade(rs.getDate("dataAtividade"));

            if (rs.getString("status").equals("A")) {
                a.setStatus("Ativo");
            } else {
                a.setStatus("Inativo");
            }

            listaAtividades.add(a);
        }

        p.setPacienteAtividades(listaAtividades);
    }

    /**
     * Método responsável por pesquisar e inserir as localizações de um paciente
     *
     * @param p paciente que receberá as localizações
     * @throws SQLException disparada durante a fase de pesquisa
     */
    private void carregarInformacoesLocalizacoesPaciente(Paciente p) throws SQLException {
        ArrayList<Localizacao> listaLocalizacoes = new ArrayList();

        PreparedStatement stmt = ConexaoBD.getConnection().prepareStatement("SELECT * FROM pacientepossuilocalizacao psl "
                + "INNER JOIN localizacao l ON l.id = psl.idLocalizacao AND psl.idPaciente=?");
        stmt.setInt(1, p.getIdEspecifico());
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

            listaLocalizacoes.add(l);
        }
        p.setLocalizacoes(listaLocalizacoes);
    }

    /**
     * Método responsável por pesquisar e inserir os responsáveis de um paciente
     *
     * @param p paciente que receberá os responsáveis
     * @throws SQLException disparada durante a fase de pesquisa
     */
    private void carregarInformacoesResponsaveisPaciente(Paciente p) throws SQLException {
        ArrayList<Responsavel> listaResponsaveis = new ArrayList();
        PreparedStatement stmt = ConexaoBD.getConnection().prepareStatement("SELECT * FROM pacientepossuiresponsavel psr "
                + "INNER JOIN responsavel r ON r.id = psr.idResponsavel "
                + "INNER JOIN pessoa p ON p.id = r.idPessoa AND psr.idPaciente=?");
        stmt.setInt(1, p.getIdEspecifico());
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Responsavel r = new Responsavel();
            r.setIdPessoa(Integer.parseInt(rs.getString("idPessoa")));
            r.setIdEspecifico(Integer.parseInt(rs.getString("idResponsavel")));
            r.setBairro(rs.getString("bairro"));
            r.setCidade(rs.getString("cidade"));
            r.setCpf(rs.getString("cpf"));
            r.setLogradouro(rs.getString("logradouro"));
            r.setNome(rs.getString("nome"));
            r.setNomeLogin(rs.getString("nomeLogin"));
            r.setRg(rs.getString("rg"));
            r.setSenha(rs.getString("senha"));
            r.setTelefone1(rs.getString("telefone1"));
            r.setTelefone2(rs.getString("telefone2"));
            r.setParentesco(rs.getString("parentesco"));

            switch (rs.getString("status")) {
                case "A": {
                    r.setStatus("Ativo");
                    break;
                }

                case "I": {
                    r.setStatus("Inativo");
                    break;
                }
            }
            listaResponsaveis.add(r);
        }

        p.setResponsaveis(listaResponsaveis);
    }
}
