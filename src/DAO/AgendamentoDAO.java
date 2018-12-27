package DAO;

import ConexaoDB.ConexaoBD;
import Geral.Uteis;
import Model.Agendamento;
import Model.Funcionario;
import Model.Pessoa;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * Classe do tipo DAO relacionada aos agendamentos da aplicação
 *
 * @author Antonio Lucas Christofoletti
 */
public class AgendamentoDAO {

    /**
     * Método responsável por inserir um novo agendamento
     *
     * @param a agedamento que será inserido
     * @throws Exception caso ocorra algum erro durante a fase de inserção dos
     * dados no banco de dados
     */
    public void inserir(Agendamento a) throws Exception {
        try {
            ConexaoBD.AbrirConexao();
            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("INSERT INTO agendamento (motivo, observacao, dataAgendada, dataCriacao, status, idUsuarioCriador, idFuncionarioDestino, tempoDuracao, idPessoaAtendida) VALUES(?,?,?,?,?,?,?,?,?)");
            pst.setString(1, a.getMotivo());
            pst.setString(2, a.getObservacao());
            pst.setTimestamp(3, new Timestamp(a.getDataAgendada().getTime()));
            pst.setTimestamp(4, new Timestamp(a.getDataCriacao().getTime()));
            pst.setString(5, a.getStatus().substring(0, 1));
            pst.setInt(6, a.getPessoaCriadora().getIdPessoa());
            pst.setInt(7, a.getFuncionarioResponsavel().getIdEspecifico());
            pst.setInt(8, a.getTempoDuracao());
            pst.setInt(9, a.getPessoaAtendida().getIdPessoa());
            pst.execute();
        } catch (Exception ex) {
            throw new Exception("Erro ao inserir agendamento. Erro: " + ex.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método responsável por editar um agendamento
     *
     * @param a agedamento que será editado
     * @throws Exception caso ocorra algum erro durante a fase de edição dos
     * dados no banco de dados
     */
    public void editar(Agendamento a) throws Exception {
        try {
            ConexaoBD.AbrirConexao();
            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("UPDATE agendamento SET motivo=?, observacao=?, dataAgendada=?, dataCriacao=?, status=?, idUsuarioCriador=?, idFuncionarioDestino=?, tempoDuracao=?, idPessoaAtendida=? WHERE id=?");
            pst.setString(1, a.getMotivo());
            pst.setString(2, a.getObservacao());
            pst.setTimestamp(3, new Timestamp(a.getDataAgendada().getTime()));
            pst.setTimestamp(4, new Timestamp(a.getDataCriacao().getTime()));
            pst.setString(5, a.getStatus().substring(0, 1));
            pst.setInt(6, a.getPessoaCriadora().getIdPessoa());
            pst.setInt(7, a.getFuncionarioResponsavel().getIdEspecifico());
            pst.setInt(8, a.getTempoDuracao());
            pst.setInt(9, a.getPessoaAtendida().getIdPessoa());
            pst.setInt(10, a.getId());

            pst.execute();
        } catch (Exception ex) {
            throw new Exception("Erro ao editar agendamento. Erro: " + ex.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método responsável por realizar uma pesquisa de agendamentos
     *
     * @param idFuncionario idFuncionario relacionado a pesquisa, pode ser ""
     * caso esse parâmetro não seja adequado na pesquisa em questão
     * @param dtInicial Data inicial em que o agendamento foi criado, pode ser
     * null caso esse parâmetro não seja adequado na pesquisa em questão
     * @param dtFinal Data Final em que o agendamento foi criado, pode ser null
     * caso esse parâmetro não seja adequado na pesquisa em questão
     * @param situacao situação do agendamento, pode ser "" caso esse parâmetro
     * não seja adequado na pesquisa em questão
     * @param ordemDados referente ao ORDER BY da consulta
     * @return retorna um ArrayList com os agendamentos encontrados
     * @throws Exception gerado caso ocorra algum erro durante a pesquisa
     */
    public ArrayList pesquisar(String idFuncionario, Date dtInicial, Date dtFinal, String situacao, String ordemDados) throws Exception {
        try {
            ArrayList<Agendamento> agendamentos = new ArrayList<>();
            ConexaoBD.AbrirConexao();

            /*
            SELECT * FROM (SELECT a.*, 
       a.id idAgendamento,
       fDestino.id idDestino, 
       pDestino.nome nomeDestino,
       pCriacao.id idCriacao,
       pCriacao.nome nomeCriacao,
       'Funcionário' tipoUsuarioCriacao
		 FROM agendamento a
		 INNER JOIN funcionario fDestino ON fDestino.idPessoa = a.idFuncionarioDestino
		 INNER JOIN pessoa pDestino ON pDestino.id = fDestino.idPessoa
		 INNER JOIN pessoa pCriacao ON a.idUsuarioCriador = pCriacao.id
		 INNER JOIN funcionario fCriacao ON fCriacao.idPessoa = pCriacao.id

UNION

SELECT a.*, 
       a.id idAgendamento,
       fDestino.id idDestino, 
       pDestino.nome nomeDestino,
       pCriacao.id idCriacao,
       pCriacao.nome nomeCriacao,
       'Responsavel' tipoUsuarioCriacao
		 FROM agendamento a
		 INNER JOIN funcionario fDestino ON fDestino.idPessoa = a.idFuncionarioDestino
		 INNER JOIN pessoa pDestino ON pDestino.id = fDestino.idPessoa
		 INNER JOIN pessoa pCriacao ON a.idUsuarioCriador = pCriacao.id
		 INNER JOIN responsavel rCriacao ON rCriacao.idPessoa = pCriacao.id

UNION

SELECT a.*, 
       a.id idAgendamento,
       fDestino.id idDestino, 
       pDestino.nome nomeDestino,
       pCriacao.id idCriacao,
       pCriacao.nome nomeCriacao,
       'Paciente' tipoUsuarioCriacao
		 FROM agendamento a
		 INNER JOIN funcionario fDestino ON fDestino.idPessoa = a.idFuncionarioDestino
		 INNER JOIN pessoa pDestino ON pDestino.id = fDestino.idPessoa
		 INNER JOIN pessoa pCriacao ON a.idUsuarioCriador = pCriacao.id
		 INNER JOIN paciente pacienteCriacao ON pacienteCriacao.idPessoa = pCriacao.id) a
             */
            String query = "SELECT * FROM (SELECT a.*, \n"
                    + "       a.id idAgendamento,\n"
                    + "       fDestino.id idDestino, \n"
                    + "       pDestino.nome nomeDestino,\n"
                    + "       pCriacao.id idCriacao,\n"
                    + "       pCriacao.nome nomeCriacao,\n"
                    + "       'Funcionário' tipoUsuarioCriacao,\n"
                    + "       pAtendida.nome nomePessoaAtendida\n"
                    + "		 FROM agendamento a\n"
                    + "		 INNER JOIN funcionario fDestino ON fDestino.id = a.idFuncionarioDestino\n"
                    + "		 INNER JOIN pessoa pDestino ON pDestino.id = fDestino.idPessoa\n"
                    + "		 INNER JOIN pessoa pCriacao ON a.idUsuarioCriador = pCriacao.id\n"
                    + "		 INNER JOIN funcionario fCriacao ON fCriacao.idPessoa = pCriacao.id\n"
                    + "		 INNER JOIN pessoa pAtendida ON a.idPessoaAtendida = pAtendida.id\n"
                    + "\n"
                    + "UNION\n"
                    + "\n"
                    + "SELECT a.*, \n"
                    + "       a.id idAgendamento,\n"
                    + "       fDestino.id idDestino, \n"
                    + "       pDestino.nome nomeDestino,\n"
                    + "       pCriacao.id idCriacao,\n"
                    + "       pCriacao.nome nomeCriacao,\n"
                    + "       'Responsável' tipoUsuarioCriacao,\n"
                    + "       pAtendida.nome nomePessoaAtendida\n"
                    + "		 FROM agendamento a\n"
                    + "		 INNER JOIN funcionario fDestino ON fDestino.id = a.idFuncionarioDestino\n"
                    + "		 INNER JOIN pessoa pDestino ON pDestino.id = fDestino.idPessoa\n"
                    + "		 INNER JOIN pessoa pCriacao ON a.idUsuarioCriador = pCriacao.id\n"
                    + "		 INNER JOIN responsavel rCriacao ON rCriacao.idPessoa = pCriacao.id\n"
                    + "		 INNER JOIN pessoa pAtendida ON a.idPessoaAtendida = pAtendida.id\n"
                    + "\n"
                    + "UNION\n"
                    + "\n"
                    + "SELECT a.*, \n"
                    + "       a.id idAgendamento,\n"
                    + "       fDestino.id idDestino, \n"
                    + "       pDestino.nome nomeDestino,\n"
                    + "       pCriacao.id idCriacao,\n"
                    + "       pCriacao.nome nomeCriacao,\n"
                    + "       'Paciente' tipoUsuarioCriacao,\n"
                    + "       pAtendida.nome nomePessoaAtendida\n"
                    + "		 FROM agendamento a\n"
                    + "		 INNER JOIN funcionario fDestino ON fDestino.id = a.idFuncionarioDestino\n"
                    + "		 INNER JOIN pessoa pDestino ON pDestino.id = fDestino.idPessoa\n"
                    + "		 INNER JOIN pessoa pCriacao ON a.idUsuarioCriador = pCriacao.id\n"
                    + "		 INNER JOIN paciente pacienteCriacao ON pacienteCriacao.idPessoa = pCriacao.id\n"
                    + "		 INNER JOIN pessoa pAtendida ON a.idPessoaAtendida = pAtendida.id) a " + geraWherePesquisar(idFuncionario, dtInicial, dtFinal, situacao, ordemDados);

                        
            PreparedStatement stmt = ConexaoBD.getConnection().prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Agendamento a = new Agendamento();
                a.setId(Integer.parseInt(rs.getString("id")));
                a.setDataAgendada(rs.getTimestamp("dataAgendada"));
                a.setDataCriacao(rs.getTimestamp("dataCriacao"));
                a.setMotivo(rs.getString("motivo"));
                a.setObservacao(rs.getString("observacao"));
                a.setTempoDuracao(rs.getInt("tempoDuracao"));

                if (!existeConflitoData(String.valueOf(a.getId())).isEmpty()) {
                    a.setStatus("Conflito");
                } else if (rs.getString("status").equals("P")) {
                    a.setStatus("Pendente");
                } else if (rs.getString("status").equals("C")) {
                    a.setStatus("Cancelado");
                } else {
                    a.setStatus("Aprovado");
                }

                Pessoa p = Pessoa.factory(rs.getString("tipoUsuarioCriacao"));

                p.setIdPessoa(rs.getInt("idCriacao"));
                p.setNome(rs.getString("nomeCriacao"));

                a.setPessoaCriadora(p);

                Funcionario f = new Funcionario();

                f.setIdEspecifico(rs.getInt("idDestino"));
                f.setNome(rs.getString("nomeDestino"));

                a.setFuncionarioResponsavel(f);

                p = new Funcionario();

                p.setIdPessoa(rs.getInt("idPessoaAtendida"));
                p.setNome(rs.getString("nomePessoaAtendida"));

                a.setPessoaAtendida(p);

                agendamentos.add(a);
            }

            rs.close();
            stmt.close();
            return (agendamentos);
        } catch (Exception ex) {
            throw new Exception("Erro ao pesquisar agendamentos. Erro: " + ex.getMessage());
        } finally {
            ConexaoBD.FecharConexao();
        }
    }

    /**
     * Método privado que gera o WHERE da consulta SQL
     *
     * @param idFuncionario idFuncionario relacionado a pesquisa, pode ser ""
     * caso esse parâmetro não seja adequado na pesquisa em questão
     * @param dtInicial Data inicial em que o agendamento foi criado, pode ser
     * null caso esse parâmetro não seja adequado na pesquisa em questão
     * @param dtFinal Data Final em que o agendamento foi criado, pode ser null
     * caso esse parâmetro não seja adequado na pesquisa em questão
     * @param situacao situação do agendamento, pode ser "" caso esse parâmetro
     * não seja adequado na pesquisa em questão
     * @param ordemDados referente ao ORDER BY da consulta
     * @return retorna uma String com o WHERE formatado de acordo com os
     * parâmetros
     */
    private String geraWherePesquisar(String idFuncionario, Date dtInicial, Date dtFinal, String situacao, String ordemDados) {
        String where = "WHERE ";

        if (!idFuncionario.equals("")) {
            where += "a.idFuncionarioDestino=" + idFuncionario + " AND ";
        }

        if (!situacao.equals("Todos")) {
            where += "a.status='" + situacao.substring(0, 1) + "' AND ";
        }

        if (dtInicial != null) {
            where += "a.dataAgendada >= '" + Uteis.converteData("yyyy-MM-dd", dtInicial) + " 00:00:00' AND ";
        }

        if (dtFinal != null) {
            where += "a.dataAgendada <= '" + Uteis.converteData("yyyy-MM-dd", dtFinal) + " 23:59:59' AND ";
        }

        where += "1=1 ";

        if (ordemDados.equals("Data Decrescente")) {
            where += " ORDER BY a.dataAgendada DESC";
        } else {
            where += " ORDER BY a.dataAgendada ASC";
        }

        return where;
    }

    /**
     * Método responsável por verificar se um agendamento está com conflito com
     * outro agendamento
     *
     * @param id id do agendamento
     * @return retorna um ArrayList com os 'ids' dos agendamentos que estão
     * causando os conflitos
     * @throws Exception exceção gerada durante a fase de pesquisa ou análise
     */
    public ArrayList existeConflitoData(String id) throws Exception {
        try {
            if (ConexaoBD.getConnection().isClosed()) {
                ConexaoBD.AbrirConexao();
            }

            PreparedStatement pst = ConexaoBD.getConnection().prepareStatement("select a2.id from agendamento a1 \n"
                    + "INNER JOIN agendamento a2 ON\n"
                    + "(a1.dataAgendada <= a2.dataAgendada AND \n"
                    + "DATE_ADD(a1.dataAgendada, interval a1.tempoDuracao minute) >= \n"
                    + "DATE_ADD(a2.dataAgendada, interval a2.tempoDuracao minute) OR\n"
                    + "a1.dataAgendada >= a2.dataAgendada AND \n"
                    + "DATE_ADD(a1.dataAgendada, interval a1.tempoDuracao minute) <= \n"
                    + "DATE_ADD(a2.dataAgendada, interval a2.tempoDuracao minute)) AND a1.id <> a2.id and a1.id=?");
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            ArrayList agendamentosConflitos = new ArrayList();

            while (rs.next()) {
                agendamentosConflitos.add(rs.getInt("id"));
            }
            rs.close();
            return agendamentosConflitos;
        } catch (Exception e) {
            throw new Exception("Erro ao verificar conflito entre datas no agendamento. Erro: " + e.getMessage());
        }
    }
}
