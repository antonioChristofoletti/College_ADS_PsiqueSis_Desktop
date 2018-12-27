package View;

import Controller.FuncionarioController;
import Geral.Imagens;
import TableModel.PacienteLocalizacoesTableModel;
import TableModel.PacienteResponsavelTableModel;
import Geral.Uteis;
import Interfaces_Inversao_Controle.IPesquisarAtividade;
import Interfaces_Inversao_Controle.IPesquisarLocalizacao;
import Interfaces_Inversao_Controle.IPesquisarPlano;
import Interfaces_Inversao_Controle.IPesquisarResponsavel;
import Model.Atividade;
import Model.Historico;
import Model.Localizacao;
import Model.Paciente;
import Model.Plano;
import Model.Responsavel;
import TableModel.PacienteAtividadeTableModel;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/**
 * JFrame relacionada a paciente Inc/Alt
 *
 * @author Antonio Lucas Christofoletti
 */
public class FrmPacienteIncAlt extends javax.swing.JFrame implements IPesquisarPlano, IPesquisarResponsavel, IPesquisarLocalizacao, IPesquisarAtividade {

    /**
     * Controller do tipo funcionário
     */
    private FuncionarioController funcionarioController;
    /**
     * Instância da própria classe em questão, por conta do padrão de projeto
     * Sington
     */
    private static FrmPacienteIncAlt frmPacienteIncAlt;
    /**
     * TableModel que gerencia as informações da JTable de responsáveis
     */
    private PacienteResponsavelTableModel pacienteResponsaveisTableModel;
    /**
     * TableModel que gerencia as informações da JTable de localizações
     */
    private PacienteLocalizacoesTableModel pacienteLocalizacoesTableModel;
    /**
     * TableModel que gerencia as informações da JTable de atividades
     */
    private PacienteAtividadeTableModel pacienteAtividadeTableModel;
    /**
     * Plano de saúde selecionado na tela
     */
    private Plano planoSelecionado;
    /**
     * Paciente de edição, caso a tela tenha sido invocada para edição
     */
    public Paciente pacienteEdicao;

    /**
     * Construtor da classe
     */
    private FrmPacienteIncAlt() {
        initComponents();
        configuraTela();
        this.funcionarioController = FuncionarioController.getInstance();
    }

    /**
     * Método responsável por retornar uma instância da própria classe
     *
     * @param pacienteEdicao paciente que poderá ser editado, caso a mesma tela
     * tenha sido aberta para esse motivo
     * @return retorna uma instância da própria classe
     */
    public static FrmPacienteIncAlt getInstance(Paciente pacienteEdicao) {
        if (frmPacienteIncAlt == null) {
            frmPacienteIncAlt = new FrmPacienteIncAlt();
        }

        frmPacienteIncAlt.pacienteEdicao = pacienteEdicao;

        if (frmPacienteIncAlt.pacienteEdicao != null) {
            frmPacienteIncAlt.carregarCampos(frmPacienteIncAlt.pacienteEdicao);
        } else {
            frmPacienteIncAlt.limparCampos();
        }

        frmPacienteIncAlt.setLocationRelativeTo(null);
        return frmPacienteIncAlt;
    }

    /**
     * Método responsável por carregar um paciente para os campos
     *
     * @param p paciente que será setado na tela
     */
    public void carregarCampos(Paciente p) {
        txtID.setText(String.valueOf(p.getIdEspecifico()));
        cmbStatus.setSelectedItem(p.getStatus());
        txtNomePaciente.setText(p.getNome());
        txtrg.setText(p.getRg());
        txtcpf.setText(p.getCpf());
        txttelefone1.setText(p.getTelefone1());
        txttelefone2.setText(p.getTelefone2());
        txtCidade.setText(p.getCidade());
        txtBairro.setText(p.getBairro());
        txtLogradouro.setText(p.getLogradouro());
        txtNomeLogin.setText(p.getNomeLogin());
        txtSenha1.setText(p.getSenha());
        txtSenha2.setText(p.getSenha());

        pacienteAtividadeTableModel.removeAll();
        pacienteLocalizacoesTableModel.removeAll();
        pacienteResponsaveisTableModel.removeAll();

        pacienteAtividadeTableModel.addAll(p.getPacienteAtividades());
        pacienteLocalizacoesTableModel.addAll(p.getLocalizacoes());
        pacienteResponsaveisTableModel.addAll(p.getResponsaveis());

        planoSelecionado = p.getPlano();
        txtPlano.setText(planoSelecionado.getNomeInstituicao());
        txtDataInicio.setDate(p.getDataInicio());
        cmbResponsavel.setSelectedItem(p.getCapaz());
        txtNumeroPasta.setText(p.getNumeroPasta());
    }

    /**
     * Método responsável por limpar os campos da tela
     */
    public void limparCampos() {
        txtID.setText("");
        cmbStatus.setSelectedIndex(0);
        txtNomePaciente.setText("");
        txtrg.setText("");
        txtcpf.setText("");
        txttelefone1.setText("");
        txttelefone2.setText("");
        txtCidade.setText("");
        txtBairro.setText("");
        txtLogradouro.setText("");
        txtNomeLogin.setText("");
        txtSenha1.setText("");
        txtSenha2.setText("");

        pacienteAtividadeTableModel.removeAll();
        pacienteLocalizacoesTableModel.removeAll();
        pacienteResponsaveisTableModel.removeAll();

        planoSelecionado = null;
        txtPlano.setText("");
        txtDataInicio.setDate(null);
        cmbResponsavel.setSelectedIndex(0);
        txtNumeroPasta.setText("");
    }

    /**
     * Método responsável por configurar algumas propriedades da tela
     */
    public void configuraTela() {
        this.getContentPane().setBackground(Color.WHITE);
        this.setIconImage(Imagens.iconeSistema);

        pacienteResponsaveisTableModel = new PacienteResponsavelTableModel();
        tblResponsaveis.setModel(pacienteResponsaveisTableModel);
        pacienteResponsaveisTableModel.configurarTableModel(tblResponsaveis);

        pacienteLocalizacoesTableModel = new PacienteLocalizacoesTableModel();
        tblLocalizacoes.setModel(pacienteLocalizacoesTableModel);
        pacienteLocalizacoesTableModel.configurarTableModel(tblLocalizacoes);

        pacienteAtividadeTableModel = new PacienteAtividadeTableModel();
        tblAtividades.setModel(pacienteAtividadeTableModel);
        pacienteAtividadeTableModel.configurarTableModel(tblAtividades);

        InputMap inputMap = this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "sair");
        this.getRootPane().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMap);
        this.getRootPane().getActionMap().put("sair", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent arg0) {
                btnSairActionPerformed(null);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "inserir");
        this.getRootPane().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMap);
        this.getRootPane().getActionMap().put("inserir", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent arg0) {
                btnConfirmarActionPerformed(null);
            }
        });
    }

    /**
     * Método responsável por editar um responsável da JTable de responsável
     */
    public void editarResponsavel() {
        try {
            if (tblResponsaveis.getSelectedRow() == -1) {
                Uteis.mensagemAviso(this, "Selecione uma linha da tabela", "Aviso");
                return;
            }
            Responsavel r = pacienteResponsaveisTableModel.getResponsavel(tblResponsaveis.getSelectedRow());

            String parentesco = JOptionPane.showInputDialog(null, "Digite o parentesco do responsável '" + r.getNome() + "' com esse paciente:", "Preenchimento Informações", JOptionPane.INFORMATION_MESSAGE);

            if (null == parentesco || parentesco.equals("")) {
                Uteis.mensagemAviso(this, "Parentesco inválido", "Aviso");
                return;
            }

            r.setParentesco(parentesco);

            pacienteResponsaveisTableModel.updateObject(r);

            Uteis.mensagemAviso(this, "Responsável editado com sucesso", "Aviso");
        } catch (Exception ex) {
            Uteis.mensagemErro(this, ex.getMessage(), "Erro");
        }
    }

    /**
     * Método responsável por editar uma atividade da JTable de atividade
     */
    public void editarAtividade() {
        try {
            if (tblAtividades.getSelectedRow() == -1) {
                Uteis.mensagemAviso(this, "Selecione uma linha da tabela", "Aviso");
                return;
            }
            Atividade a = pacienteAtividadeTableModel.getAtividade(tblAtividades.getSelectedRow());

            JDateChooser jd = new JDateChooser();

            jd.setFont(new java.awt.Font("Arial", 0, 14));
            jd.setDateFormatString("dd/MM/yyyy");
            jd.setDate(new Date());
            jd.setSize(102, 23);
            String message = "Defina a data que a atividade foi executada\n";

            Object[] params = {message, jd};
            int result = JOptionPane.showConfirmDialog(null, params, "Preenchimento Informações", JOptionPane.PLAIN_MESSAGE);

            if (((JDateChooser) params[1]).getDate() != null && result == 0) {

                a.setDataAtividade(((JDateChooser) params[1]).getDate());

                pacienteAtividadeTableModel.UpdateObject(a);

                if (ckDtCresc.isSelected()) {
                    ckDtCrescActionPerformed(null);
                }

                if (ckDtDesc.isSelected()) {
                    ckDtDescActionPerformed(null);
                }

                if (ckOrdeAlfa.isSelected()) {
                    ckOrdeAlfaActionPerformed(null);
                }

                Uteis.mensagemAviso(this, "Atividade editada com sucesso", "Aviso");
            } else {
                Uteis.mensagemAviso(this, "Data inválida", "Aviso");
            }
        } catch (Exception ex) {
            Uteis.mensagemErro(this, ex.getMessage(), "Erro");
        }
    }

    /**
     * Método responsável por setar uma localização pesquisada, método
     * relacionado com a Interface IPesquisarLocalizacao
     *
     * @param l localização que foi pesquisada
     */
    @Override
    public void preencheCampos(Localizacao l) {
        if (l != null) {
            try {
                pacienteLocalizacoesTableModel.addLocalizacao(l);
            } catch (Exception ex) {
                Uteis.mensagemErro(this, ex.getMessage(), "Erro");
            }
        }
    }

    /**
     * Método responsável por setar um plano pesquisado, método relacionado com
     * a Interface IPesquisarPlano
     *
     * @param p plano que foi pesquisado
     */
    @Override
    public void preencheCampos(Plano p) {
        if (p != null) {
            planoSelecionado = p;
            txtPlano.setText(planoSelecionado.getNomeInstituicao());
        }
    }

    /**
     * Método responsável por setar um responsável pesquisado, método
     * relacionado com a Interface IPesquisarResponsável
     *
     * @param r responsável que foi pesquisado
     */
    @Override
    public void preencheCampos(Responsavel r) {
        if (r != null) {
            try {
                String parentesco = JOptionPane.showInputDialog(null, "Digite o parentesco do responsável '" + r.getNome() + "' com esse paciente:", "Preenchimento Informações", JOptionPane.INFORMATION_MESSAGE);

                if (null == parentesco || parentesco.equals("")) {
                    Uteis.mensagemAviso(this, "Parentesco inválido", "Aviso");
                    return;
                }

                r.setParentesco(parentesco);
                pacienteResponsaveisTableModel.addResponsavel(r);
            } catch (Exception ex) {
                Uteis.mensagemErro(this, ex.getMessage(), "Erro");
            }
        }
    }

    /**
     * Método responsável por setar uma atividade pesquisada, método relacionado
     * com a Interface IPesquisarAtividade
     *
     * @param a atividade que foi pesquisada
     */
    @Override
    public void preencheCampos(Atividade a) {
        if (a != null) {
            try {

                JDateChooser jd = new JDateChooser();

                jd.setFont(new java.awt.Font("Arial", 0, 14));
                jd.setDateFormatString("dd/MM/yyyy");
                jd.setDate(new Date());
                jd.setSize(102, 23);
                String message = "Defina a data que a atividade foi executada\n";

                Object[] params = {message, jd};
                int result = JOptionPane.showConfirmDialog(null, params, "Preenchimento Informações", JOptionPane.PLAIN_MESSAGE);
                if (((JDateChooser) params[1]).getDate() != null && result == 0) {
                    a.setDataAtividade(((JDateChooser) params[1]).getDate());
                    pacienteAtividadeTableModel.addAtividade(a);

                    if (ckDtCresc.isSelected()) {
                        ckDtCrescActionPerformed(null);
                    }

                    if (ckDtDesc.isSelected()) {
                        ckDtDescActionPerformed(null);
                    }

                    if (ckOrdeAlfa.isSelected()) {
                        ckOrdeAlfaActionPerformed(null);
                    }

                } else {
                    Uteis.mensagemAviso(this, "Data inválida", "Aviso");
                }
            } catch (Exception ex) {
                Uteis.mensagemErro(this, ex.getMessage(), "Erro");
            }
        }
    }

    public JTable getTblResponsaveis() {
        return tblResponsaveis;
    }

    public JTextField getTxtBairro() {
        return txtBairro;
    }

    public JTextField getTxtCidade() {
        return txtCidade;
    }

    public JDateChooser getTxtDataInicio() {
        return txtDataInicio;
    }

    public JTextField getTxtID() {
        return txtID;
    }

    public JTextField getTxtLogradouro() {
        return txtLogradouro;
    }

    public JTextField getTxtNomePaciente() {
        return txtNomePaciente;
    }

    public JTextField getTxtNumeroPasta() {
        return txtNumeroPasta;
    }

    public JTextField getTxtPlano() {
        return txtPlano;
    }

    public JFormattedTextField getTxtcpf() {
        return txtcpf;
    }

    public JFormattedTextField getTxtrg() {
        return txtrg;
    }

    public JFormattedTextField getTxttelefone1() {
        return txttelefone1;
    }

    public JFormattedTextField getTxttelefone2() {
        return txttelefone2;
    }

    public JComboBox<String> getCmbResponsavel() {
        return cmbResponsavel;
    }

    public JButton getBtnPesquisarPlano() {
        return btnPesquisarPlano;
    }

    public JTextField getTxtNomeLogin() {
        return txtNomeLogin;
    }

    public void setTxtNomeLogin(JTextField txtNomeLogin) {
        this.txtNomeLogin = txtNomeLogin;
    }

    public JPasswordField getTxtSenha1() {
        return txtSenha1;
    }

    public void setTxtSenha1(JPasswordField txtSenha1) {
        this.txtSenha1 = txtSenha1;
    }

    public JPasswordField getTxtSenha2() {
        return txtSenha2;
    }

    public void setTxtSenha2(JPasswordField txtSenha2) {
        this.txtSenha2 = txtSenha2;
    }

    /**
     * Método responsável por setar as propriedades da tela
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnConfirmar = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtNomePaciente = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtrg = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        txtcpf = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txttelefone1 = new javax.swing.JFormattedTextField();
        txttelefone2 = new javax.swing.JFormattedTextField();
        txtCidade = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtBairro = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtLogradouro = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblLocalizacoes = new javax.swing.JTable();
        btnRemoverLocalizacao = new javax.swing.JButton();
        btnInserirLocalizacao = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblResponsaveis = new javax.swing.JTable();
        btnInserirResponsavel = new javax.swing.JButton();
        btnRemoverResponsavel = new javax.swing.JButton();
        btnEditarAtividade = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        txtNomeLogin = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtSenha1 = new javax.swing.JPasswordField();
        txtSenha2 = new javax.swing.JPasswordField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        cmbStatus = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        txtNumeroPasta = new javax.swing.JTextField();
        cmbResponsavel = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        txtDataInicio = new com.toedter.calendar.JDateChooser();
        jLabel26 = new javax.swing.JLabel();
        txtPlano = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btnPesquisarPlano = new javax.swing.JButton();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        btnInserirAtividade = new javax.swing.JButton();
        btnRemoverAtividade = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblAtividades = new javax.swing.JTable();
        btnEditarAtividade2 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        ckOrdeAlfa = new javax.swing.JRadioButton();
        ckDtCresc = new javax.swing.JRadioButton();
        ckDtDesc = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("PsiqueSis - Paciente - Inc/Alt");
        setResizable(false);

        btnConfirmar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/iconeConfirmar.png"))); // NOI18N
        btnConfirmar.setText("Confirmar");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        btnSair.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/iconeSair.png"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        jTabbedPane2.setToolTipText("");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados Pessoais", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 18))); // NOI18N
        jPanel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setText("ID");

        txtID.setEditable(false);
        txtID.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        txtNomePaciente.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Nome");

        try {
            txtrg.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("AA.AAA.AAA-AA")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtrg.setFocusLostBehavior(javax.swing.JFormattedTextField.COMMIT);
        txtrg.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("RG");

        try {
            txtcpf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("AAA.AAA.AAA-AA")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtcpf.setText("");
        txtcpf.setFocusLostBehavior(javax.swing.JFormattedTextField.COMMIT);
        txtcpf.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("CPF");

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText("Telefone 1");

        jLabel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel11.setText("Telefone 2");

        try {
            txttelefone1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) ##### - ####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txttelefone1.setText("");
        txttelefone1.setFocusLostBehavior(javax.swing.JFormattedTextField.COMMIT);
        txttelefone1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        try {
            txttelefone2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) ##### - ####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txttelefone2.setFocusLostBehavior(javax.swing.JFormattedTextField.COMMIT);
        txttelefone2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        txtCidade.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setText("Cidade");

        txtBairro.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setText("Bairro");

        txtLogradouro.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel10.setText("Logradouro");

        jTabbedPane1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        tblLocalizacoes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome", "Telefone 1", "Telefone 2", "Cidade", "Bairro", "Logradouro"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblLocalizacoes);

        btnRemoverLocalizacao.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnRemoverLocalizacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/iconeRemover.png"))); // NOI18N
        btnRemoverLocalizacao.setToolTipText("Remover Localização");
        btnRemoverLocalizacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverLocalizacaoActionPerformed(evt);
            }
        });

        btnInserirLocalizacao.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnInserirLocalizacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/iconeAdicionar.png"))); // NOI18N
        btnInserirLocalizacao.setToolTipText("Inserir Localização");
        btnInserirLocalizacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInserirLocalizacaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 740, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnRemoverLocalizacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnInserirLocalizacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnInserirLocalizacao, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemoverLocalizacao, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 115, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Localizações", jPanel4);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        tblResponsaveis.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome", "Telefone 1", "Telefone 2"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblResponsaveis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblResponsaveisMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblResponsaveis);

        btnInserirResponsavel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnInserirResponsavel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/iconeAdicionar.png"))); // NOI18N
        btnInserirResponsavel.setToolTipText("Inserir Responsável");
        btnInserirResponsavel.setMaximumSize(new java.awt.Dimension(95, 25));
        btnInserirResponsavel.setMinimumSize(new java.awt.Dimension(95, 25));
        btnInserirResponsavel.setPreferredSize(new java.awt.Dimension(95, 25));
        btnInserirResponsavel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInserirResponsavelActionPerformed(evt);
            }
        });

        btnRemoverResponsavel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnRemoverResponsavel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/iconeRemover.png"))); // NOI18N
        btnRemoverResponsavel.setToolTipText("Remover Responsável");
        btnRemoverResponsavel.setMaximumSize(new java.awt.Dimension(95, 25));
        btnRemoverResponsavel.setMinimumSize(new java.awt.Dimension(95, 25));
        btnRemoverResponsavel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverResponsavelActionPerformed(evt);
            }
        });

        btnEditarAtividade.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnEditarAtividade.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/iconeEditar.png"))); // NOI18N
        btnEditarAtividade.setToolTipText("Editar Responsável");
        btnEditarAtividade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarAtividadeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 738, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnEditarAtividade, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                        .addComponent(btnInserirResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(btnRemoverResponsavel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnInserirResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditarAtividade)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemoverResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(118, 118, 118))
        );

        jTabbedPane1.addTab("Responsáveis", jPanel2);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informações Acesso", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 18))); // NOI18N
        jPanel6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jPanel6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPanel6FocusGained(evt);
            }
        });

        txtNomeLogin.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel12.setText("Usuário Login");

        txtSenha1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSenha1FocusGained(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel13.setText("Senha");

        jLabel14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel14.setText("Confirmar Senha");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNomeLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSenha1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(txtSenha2, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNomeLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSenha1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSenha2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cmbStatus.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ativo", "Inativo" }));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("Status");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNomePaciente)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtrg, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(txtcpf, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txttelefone1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(txttelefone2, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtLogradouro))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNomePaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel11))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtrg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtcpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txttelefone1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txttelefone2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addGap(29, 29, 29))))
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(29, 29, 29)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 243, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Paciente", jPanel1);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados Clínicos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 18))); // NOI18N
        jPanel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel24.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel24.setText("Número Pasta");

        txtNumeroPasta.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        cmbResponsavel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cmbResponsavel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Não", "Sim" }));

        jLabel25.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel25.setText("Responsável");

        txtDataInicio.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel26.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel26.setText("Data Início");

        txtPlano.setEditable(false);
        txtPlano.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtPlano.setToolTipText("Duplo clique para remover o plano");
        txtPlano.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPlanoMouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText("Plano");

        btnPesquisarPlano.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnPesquisarPlano.setText("...");
        btnPesquisarPlano.setToolTipText("Clique para pesquisar um plano");
        btnPesquisarPlano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarPlanoActionPerformed(evt);
            }
        });

        jTabbedPane4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        btnInserirAtividade.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnInserirAtividade.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/iconeAdicionar.png"))); // NOI18N
        btnInserirAtividade.setToolTipText("Inserir Atividade");
        btnInserirAtividade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInserirAtividadeActionPerformed(evt);
            }
        });

        btnRemoverAtividade.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnRemoverAtividade.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/iconeRemover.png"))); // NOI18N
        btnRemoverAtividade.setToolTipText("Remover Atividade");
        btnRemoverAtividade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverAtividadeActionPerformed(evt);
            }
        });

        tblAtividades.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome", "Descrição", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAtividades.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAtividadesMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblAtividades);

        btnEditarAtividade2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnEditarAtividade2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/iconeEditar.png"))); // NOI18N
        btnEditarAtividade2.setToolTipText("Editar Atividade");
        btnEditarAtividade2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarAtividade2ActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ordenar Por", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        ckOrdeAlfa.setBackground(new java.awt.Color(255, 255, 255));
        ckOrdeAlfa.setFont(new java.awt.Font("Arial", 0, 9)); // NOI18N
        ckOrdeAlfa.setText("Ordem Alfabética");
        ckOrdeAlfa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckOrdeAlfaActionPerformed(evt);
            }
        });

        ckDtCresc.setBackground(new java.awt.Color(255, 255, 255));
        ckDtCresc.setFont(new java.awt.Font("Arial", 0, 9)); // NOI18N
        ckDtCresc.setText("Dt. Crescente");
        ckDtCresc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckDtCrescActionPerformed(evt);
            }
        });

        ckDtDesc.setBackground(new java.awt.Color(255, 255, 255));
        ckDtDesc.setFont(new java.awt.Font("Arial", 0, 9)); // NOI18N
        ckDtDesc.setText("Dt. Decrescente");
        ckDtDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckDtDescActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ckOrdeAlfa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ckDtCresc, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ckDtDesc)
                .addContainerGap(433, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ckOrdeAlfa)
                    .addComponent(ckDtCresc)
                    .addComponent(ckDtDesc)))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 740, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnRemoverAtividade, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnInserirAtividade, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEditarAtividade2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(btnInserirAtividade)
                        .addGap(5, 5, 5)
                        .addComponent(btnEditarAtividade2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemoverAtividade)
                        .addGap(0, 174, Short.MAX_VALUE))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane4.addTab("Atividades", jPanel7);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNumeroPasta, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25)
                    .addComponent(cmbResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addComponent(txtDataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 402, Short.MAX_VALUE))
                    .addComponent(txtPlano))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPesquisarPlano, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jTabbedPane4)
                    .addContainerGap()))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25)
                    .addComponent(jLabel26)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNumeroPasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmbResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtDataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtPlano, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnPesquisarPlano, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(406, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                    .addContainerGap(62, Short.MAX_VALUE)
                    .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        jTabbedPane2.addTab("Clínica", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnConfirmar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSair))
                    .addComponent(jTabbedPane2))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 522, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSair, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(btnConfirmar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnPesquisarPlanoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarPlanoActionPerformed
        funcionarioController.getPlanoController().FrmPlanoPesquisar(this, true);
    }//GEN-LAST:event_btnPesquisarPlanoActionPerformed

    private void btnRemoverAtividadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverAtividadeActionPerformed
        try {
            if (tblAtividades.getSelectedRow() == -1) {
                Uteis.mensagemAviso(this, "Selecione uma linha da tabela", "Aviso");
                return;
            }

            if (Uteis.mensagemCondicional(this, "Deseja realmente remover a atividade?", "Confirmação") == 1) {
                return;
            }

            pacienteAtividadeTableModel.removerAtividade(pacienteAtividadeTableModel.getAtividade(tblAtividades.getSelectedRow()));
            Uteis.mensagemAviso(this, "Atividade removida com sucesso", "Aviso");
        } catch (Exception ex) {
            Uteis.mensagemErro(this, ex.getMessage(), "Erro");
        }
    }//GEN-LAST:event_btnRemoverAtividadeActionPerformed

    private void btnInserirAtividadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInserirAtividadeActionPerformed
        funcionarioController.getAtividadeController().frmAtividadePesquisarSetVisible(this, true);
    }//GEN-LAST:event_btnInserirAtividadeActionPerformed

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        Paciente p = new Paciente();

        p.setStatus(cmbStatus.getSelectedItem().toString());
        p.setNome(txtNomePaciente.getText());
        p.setRg(Uteis.retiraMascara(txtrg.getText(), ".- ", ""));
        p.setCpf(Uteis.retiraMascara(txtcpf.getText(), ".- ", ""));
        p.setTelefone1(Uteis.retiraMascara(txttelefone1.getText(), "()- ", ""));
        p.setTelefone2(Uteis.retiraMascara(txttelefone2.getText(), "()- ", ""));
        p.setCidade(txtCidade.getText());
        p.setBairro(txtBairro.getText());
        p.setLogradouro(txtLogradouro.getText());
        p.setNomeLogin(txtNomeLogin.getText());
        p.setSenha(new String(txtSenha1.getPassword()) + new String(txtSenha2.getPassword()));

        p.setNumeroPasta(txtNumeroPasta.getText());
        p.setCapaz(cmbResponsavel.getSelectedItem().toString());
        p.setDataInicio(txtDataInicio.getDate());
        p.setPlano(planoSelecionado);

        p.setResponsaveis(pacienteResponsaveisTableModel.getAll());
        p.setLocalizacoes(pacienteLocalizacoesTableModel.getAll());
        p.setPacienteAtividades(pacienteAtividadeTableModel.getAll());

        if (!txtID.getText().equals("")) {
            p.setIdEspecifico(Integer.parseInt(txtID.getText()));
            p.setIdPessoa(pacienteEdicao.getIdPessoa());
        }

        try {
            funcionarioController.getPacienteController().validarAtributos(p, pacienteEdicao);

            if (pacienteEdicao == null) {
                funcionarioController.getPacienteController().inserirPaciente(p);
                Uteis.mensagemAviso(this, "Paciente inserido com sucesso", "Aviso");
                funcionarioController.inserirHistorico(new Historico("Inseriu um paciente", new Date(), funcionarioController.getFuncionarioLogado()));
                this.dispose();
            } else {
                funcionarioController.getPacienteController().editarPaciente(p, pacienteEdicao.getSenha());
                Uteis.mensagemAviso(this, "Paciente editado com sucesso", "Aviso");
                funcionarioController.inserirHistorico(new Historico("Editou o paciente de código '" + p.getIdEspecifico() + "'", new Date(), funcionarioController.getFuncionarioLogado()));
                this.dispose();
            }
        } catch (Exception ex) {
            Uteis.mensagemErro(this, ex.getMessage(), "Mensagem de Erro");
        }
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        if (Uteis.mensagemCondicional(this, "Deseja realmente sair?", "Confirmação") == 0) {
            this.dispose();
        }
    }//GEN-LAST:event_btnSairActionPerformed

    private void txtPlanoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPlanoMouseClicked
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            evt.consume();
            planoSelecionado = null;
            txtPlano.setText("");
        }
    }//GEN-LAST:event_txtPlanoMouseClicked

    private void btnEditarAtividade2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarAtividade2ActionPerformed
        editarAtividade();
    }//GEN-LAST:event_btnEditarAtividade2ActionPerformed

    private void tblAtividadesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAtividadesMouseClicked
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            evt.consume();
            editarAtividade();
        }
    }//GEN-LAST:event_tblAtividadesMouseClicked

    private void btnInserirLocalizacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInserirLocalizacaoActionPerformed
        funcionarioController.getLocalizacaoController().frmLocalizacaoPesquisarSetVisible(this, true);
    }//GEN-LAST:event_btnInserirLocalizacaoActionPerformed

    private void btnRemoverLocalizacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverLocalizacaoActionPerformed
        try {
            if (tblLocalizacoes.getSelectedRow() == -1) {
                Uteis.mensagemAviso(this, "Selecione uma linha da tabela", "Aviso");
                return;
            }

            if (Uteis.mensagemCondicional(this, "Deseja realmente remover a localização?", "Confirmação") == 1) {
                return;
            }

            pacienteLocalizacoesTableModel.removerLocalizacao(pacienteLocalizacoesTableModel.getLocalizacao(tblLocalizacoes.getSelectedRow()));
            Uteis.mensagemAviso(this, "Localização removida com sucesso", "Aviso");
        } catch (Exception ex) {
            Uteis.mensagemErro(this, ex.getMessage(), "Erro");
        }
    }//GEN-LAST:event_btnRemoverLocalizacaoActionPerformed

    private void btnEditarAtividadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarAtividadeActionPerformed
        editarResponsavel();
    }//GEN-LAST:event_btnEditarAtividadeActionPerformed

    private void btnRemoverResponsavelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverResponsavelActionPerformed
        try {
            if (tblResponsaveis.getSelectedRow() == -1) {
                Uteis.mensagemAviso(this, "Selecione uma linha da tabela", "Aviso");
                return;
            }

            if (Uteis.mensagemCondicional(this, "Deseja realmente remover o responsável?", "Confirmação") == 1) {
                return;
            }

            pacienteResponsaveisTableModel.removerResponsavel(pacienteResponsaveisTableModel.getResponsavel(tblResponsaveis.getSelectedRow()));
            Uteis.mensagemAviso(this, "Responsável removido com sucesso", "Aviso");
        } catch (Exception ex) {
            Uteis.mensagemErro(this, ex.getMessage(), "Erro");
        }
    }//GEN-LAST:event_btnRemoverResponsavelActionPerformed

    private void btnInserirResponsavelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInserirResponsavelActionPerformed
        funcionarioController.getResponsavelController().frmResponsavelPesquisarSetVisible(this, true);
    }//GEN-LAST:event_btnInserirResponsavelActionPerformed

    private void tblResponsaveisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblResponsaveisMouseClicked
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            evt.consume();
            editarResponsavel();
        }
    }//GEN-LAST:event_tblResponsaveisMouseClicked

    private void ckDtCrescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckDtCrescActionPerformed
        pacienteAtividadeTableModel.ordernar(ckDtCresc.isSelected(), false, false);
        ckDtDesc.setSelected(false);
        ckOrdeAlfa.setSelected(false);
    }//GEN-LAST:event_ckDtCrescActionPerformed

    private void ckDtDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckDtDescActionPerformed
        pacienteAtividadeTableModel.ordernar(false, ckDtDesc.isSelected(), false);
        ckDtCresc.setSelected(false);
        ckOrdeAlfa.setSelected(false);
    }//GEN-LAST:event_ckDtDescActionPerformed

    private void ckOrdeAlfaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckOrdeAlfaActionPerformed
        pacienteAtividadeTableModel.ordernar(false, false, ckOrdeAlfa.isSelected());
        ckDtCresc.setSelected(false);
        ckDtDesc.setSelected(false);
    }//GEN-LAST:event_ckOrdeAlfaActionPerformed

    private void txtSenha1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSenha1FocusGained
        if (pacienteEdicao != null && pacienteEdicao.getSenha().equals(new String(txtSenha2.getPassword()))) {
            txtSenha1.setText("");
            txtSenha2.setText("");
        }
    }//GEN-LAST:event_txtSenha1FocusGained

    private void jPanel6FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPanel6FocusGained
        if (pacienteEdicao != null && pacienteEdicao.getSenha().equals(new String(txtSenha2.getPassword()))) {
            txtSenha1.setText("");
            txtSenha2.setText("");
        }
    }//GEN-LAST:event_jPanel6FocusGained

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnEditarAtividade;
    private javax.swing.JButton btnEditarAtividade2;
    private javax.swing.JButton btnInserirAtividade;
    private javax.swing.JButton btnInserirLocalizacao;
    private javax.swing.JButton btnInserirResponsavel;
    private javax.swing.JButton btnPesquisarPlano;
    private javax.swing.JButton btnRemoverAtividade;
    private javax.swing.JButton btnRemoverLocalizacao;
    private javax.swing.JButton btnRemoverResponsavel;
    private javax.swing.JButton btnSair;
    private javax.swing.JRadioButton ckDtCresc;
    private javax.swing.JRadioButton ckDtDesc;
    private javax.swing.JRadioButton ckOrdeAlfa;
    private javax.swing.JComboBox<String> cmbResponsavel;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTable tblAtividades;
    private javax.swing.JTable tblLocalizacoes;
    private javax.swing.JTable tblResponsaveis;
    private javax.swing.JTextField txtBairro;
    private javax.swing.JTextField txtCidade;
    private com.toedter.calendar.JDateChooser txtDataInicio;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtLogradouro;
    private javax.swing.JTextField txtNomeLogin;
    private javax.swing.JTextField txtNomePaciente;
    private javax.swing.JTextField txtNumeroPasta;
    private javax.swing.JTextField txtPlano;
    private javax.swing.JPasswordField txtSenha1;
    private javax.swing.JPasswordField txtSenha2;
    private javax.swing.JFormattedTextField txtcpf;
    private javax.swing.JFormattedTextField txtrg;
    private javax.swing.JFormattedTextField txttelefone1;
    private javax.swing.JFormattedTextField txttelefone2;
    // End of variables declaration//GEN-END:variables
}
