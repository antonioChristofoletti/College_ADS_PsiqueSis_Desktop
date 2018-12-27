package View;

import Controller.FuncionarioController;
import Geral.Imagens;
import Geral.Uteis;
import Model.Agendamento;
import Model.Funcionario;
import java.awt.Color;
import Model.Historico;
import Model.Pessoa;
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import Interfaces_Inversao_Controle.IPesquisarPessoa;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Graphics;
import javax.swing.DefaultListCellRenderer;

/**
 * JFrame relacionada ao Agendamento Inc/Alt
 *
 * @author Antonio Lucas Christofoletti
 */
public class FrmAgendamentoIncAlt extends javax.swing.JFrame implements IPesquisarPessoa {

    /**
     * Instância da própria classe em questão, por conta do padrão de projeto
     * Sington
     */
    private static FrmAgendamentoIncAlt frmPacienteIncAlt;
    /**
     * Controller do tipo funcionário
     */
    private FuncionarioController funcionarioController;
    /**
     * Agendamento de edição, caso a tela tenha sido invocada para edição
     */
    private Agendamento agendamentodEdicao;
    /**
     * Funcionário selecionado na tela
     */
    private Pessoa funcionarioEscolhido;
    /**
     * Pessoa atendida selecionada na tela
     */
    private Pessoa pessoaAtendida;
    /**
     * Variável de controle para gerenciar o tipo de pesquisa de pessoa que foi
     * realizada
     */
    private String tipoPesquisaEfetuada = "";

    /**
     * Construtor da classe
     */
    private FrmAgendamentoIncAlt() {
        initComponents();
        configuraTela();
        this.funcionarioController = FuncionarioController.getInstance();
    }

    /**
     * Método responsável por retornar uma instância da própria classe
     *
     * @param agendamentoEdicao agendamento que poderá ser editado, caso a mesma
     * tenha sido aberta para esse motivo
     * @return retorna uma instância da própria classe
     */
    public static FrmAgendamentoIncAlt getInstance(Agendamento agendamentoEdicao) {
        if (frmPacienteIncAlt == null) {
            frmPacienteIncAlt = new FrmAgendamentoIncAlt();
        }

        frmPacienteIncAlt.agendamentodEdicao = agendamentoEdicao;

        if (agendamentoEdicao == null) {
            frmPacienteIncAlt.limparCampos();
        } else {
            frmPacienteIncAlt.carregarCampos(agendamentoEdicao);
        }

        frmPacienteIncAlt.setLocationRelativeTo(null);
        frmPacienteIncAlt.setSize(frmPacienteIncAlt.getPreferredSize());

        return frmPacienteIncAlt;
    }

    /**
     * Método responsável por configurar algumas propriedades da tela
     */
    public void configuraTela() {
        this.getContentPane().setBackground(Color.WHITE);
        this.setIconImage(Imagens.iconeSistema);

        InputMap inputMap = this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "confirmar");
        this.getRootPane().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMap);
        this.getRootPane().getActionMap().put("confirmar", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent arg0) {
                btnConfirmarActionPerformed(null);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "sair");
        this.getRootPane().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMap);
        this.getRootPane().getActionMap().put("sair", new AbstractAction() {
            private static final long serialVersionUID = 2L;

            @Override
            public void actionPerformed(ActionEvent arg0) {
                btnSairActionPerformed(null);
            }
        });

        ((JTextFieldDateEditor) txtDataPrevTermino.getDateEditor()).setDisabledTextColor(Color.black);
        ((JTextFieldDateEditor) txtDataCriacao.getDateEditor()).setDisabledTextColor(Color.black);

        cmbSituacao.setRenderer(new DefaultListCellRenderer() {
            @Override
            public void paint(Graphics g) {
                setForeground(Color.black);
                super.paint(g);
            }
        });
    }

    /**
     * Método responsável por limpar os campos da tela
     */
    public void limparCampos() {
        txtID.setText("");
        txtDataCriacao.setDate(null);
        txtDataAgendamento.setDate(null);
        txtFuncionarioDestino.setText("");
        txtMotivo.setText("");
        txtObservacoes.setText("");
        txtTempoMinutos.setValue(0);
        funcionarioEscolhido = null;
        txtPessoaAtendida.setText("");
        pessoaAtendida = null;
        txtDataPrevTermino.setDate(null);
        cmbSituacao.setEnabled(false);
        cmbSituacao.setSelectedIndex(0);
    }

    /**
     * Método responsável por carregar um agendamento para os campos
     *
     * @param a agendamento que será setado na tela
     */
    public void carregarCampos(Agendamento a) {
        txtID.setText(String.valueOf(a.getId()));
        txtDataCriacao.setDate(a.getDataCriacao());
        txtDataAgendamento.setDate(a.getDataAgendada());
        txtFuncionarioDestino.setText(a.getFuncionarioResponsavel().getNome());
        funcionarioEscolhido = a.getFuncionarioResponsavel();
        txtPessoaAtendida.setText(a.getPessoaAtendida().getNome());
        pessoaAtendida = a.getPessoaAtendida();
        txtMotivo.setText(a.getMotivo());
        txtObservacoes.setText(a.getObservacao());
        txtTempoMinutos.setValue(a.getTempoDuracao());

        cmbSituacao.setSelectedItem(a.getStatus());
        cmbSituacao.setEnabled(true);
    }

    public Pessoa getFuncionarioEscolhido() {
        return funcionarioEscolhido;
    }

    public void setFuncionarioEscolhido(Funcionario funcionarioEscolhido) {
        this.funcionarioEscolhido = funcionarioEscolhido;
    }

    public Date calculaDataFinal(Date dtInicial, int tempoMinutos) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dtInicial);
        calendar.add(Calendar.MINUTE, tempoMinutos);
        return calendar.getTime();
    }

    public JDateChooser getTxtDataAgendamento() {
        return txtDataAgendamento;
    }

    public void setTxtDataAgendamento(JDateChooser txtDataAgendamento) {
        this.txtDataAgendamento = txtDataAgendamento;
    }

    public JDateChooser getTxtDataCriacao() {
        return txtDataCriacao;
    }

    public void setTxtDataCriacao(JDateChooser txtDataCriacao) {
        this.txtDataCriacao = txtDataCriacao;
    }

    public JDateChooser getTxtDataPrevTermino() {
        return txtDataPrevTermino;
    }

    public void setTxtDataPrevTermino(JDateChooser txtDataPrevTermino) {
        this.txtDataPrevTermino = txtDataPrevTermino;
    }

    public JTextField getTxtFuncionarioDestino() {
        return txtFuncionarioDestino;
    }

    public void setTxtFuncionarioDestino(JTextField txtFuncionarioDestino) {
        this.txtFuncionarioDestino = txtFuncionarioDestino;
    }

    public JTextField getTxtID() {
        return txtID;
    }

    public void setTxtID(JTextField txtID) {
        this.txtID = txtID;
    }

    public JTextArea getTxtMotivo() {
        return txtMotivo;
    }

    public void setTxtMotivo(JTextArea txtMotivo) {
        this.txtMotivo = txtMotivo;
    }

    public JTextArea getTxtObservacoes() {
        return txtObservacoes;
    }

    public void setTxtObservacoes(JTextArea txtObservacoes) {
        this.txtObservacoes = txtObservacoes;
    }

    public JSpinner getTxtTempoMinutos() {
        return txtTempoMinutos;
    }

    public void setTxtTempoMinutos(JSpinner txtTempoMinutos) {
        this.txtTempoMinutos = txtTempoMinutos;
    }

    public JComboBox<String> getCmbSituacao() {
        return cmbSituacao;
    }

    public void setCmbSituacao(JComboBox<String> cmbSituacao) {
        this.cmbSituacao = cmbSituacao;
    }

    public Pessoa getPessoaAtendida() {
        return pessoaAtendida;
    }

    public void setPessoaAtendida(Pessoa pessoaAtendida) {
        this.pessoaAtendida = pessoaAtendida;
    }

    public JTextField getTxtPessoaAtendida() {
        return txtPessoaAtendida;
    }

    public void setTxtPessoaAtendida(JTextField txtPessoaAtendida) {
        this.txtPessoaAtendida = txtPessoaAtendida;
    }

    /**
     * Método responsável por setar um funcionário pesquisado, método
     * relacionado com a Interface IPesquisarFuncionario
     *
     * @param p pessoa que foi pesquisada
     */
    @Override
    public void preencheCampos(Pessoa p) {
        if (p == null) {
            return;
        }

        if ("Pessoa Atendida".equals(tipoPesquisaEfetuada)) {
            pessoaAtendida = p;
            txtPessoaAtendida.setText(p.getNome());
        } else {
            funcionarioEscolhido = p;
            txtFuncionarioDestino.setText(p.getNome());
        }
    }

    /**
     * Método responsável por setar as propriedades da tela
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLocaleChooser1 = new com.toedter.components.JLocaleChooser();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtDataAgendamento = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        txtDataCriacao = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        btnPesquisarDestino = new javax.swing.JButton();
        txtFuncionarioDestino = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cmbSituacao = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txtTempoMinutos = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtObservacoes = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtMotivo = new javax.swing.JTextArea();
        txtDataPrevTermino = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        txtPessoaAtendida = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        btnPesquisarPessoa = new javax.swing.JButton();
        btnConfirmar = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("PsiqueSis - Agendamento - Inc/Alt");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informações", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 18))); // NOI18N
        jPanel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setText("ID");

        txtID.setEditable(false);
        txtID.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        txtDataAgendamento.setDateFormatString("dd/MM/yyyy HH:mm");
        txtDataAgendamento.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtDataAgendamento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDataAgendamentoFocusLost(evt);
            }
        });
        txtDataAgendamento.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtDataAgendamentoPropertyChange(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("Data Agendamento");

        txtDataCriacao.setDateFormatString("dd/MM/yyyy HH:mm");
        txtDataCriacao.setEnabled(false);
        txtDataCriacao.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Data Criação");

        btnPesquisarDestino.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnPesquisarDestino.setText("...");
        btnPesquisarDestino.setToolTipText("Clique para pesquisar um funcionário");
        btnPesquisarDestino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarDestinoActionPerformed(evt);
            }
        });

        txtFuncionarioDestino.setEditable(false);
        txtFuncionarioDestino.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtFuncionarioDestino.setToolTipText("Duplo clique para remover o funcionário");
        txtFuncionarioDestino.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtFuncionarioDestinoMouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText("Funcionário Destino");

        cmbSituacao.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cmbSituacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pendente", "Aprovado", "Cancelado" }));

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Situação");

        txtTempoMinutos.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtTempoMinutos.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        txtTempoMinutos.setToolTipText("");
        txtTempoMinutos.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                txtTempoMinutosStateChanged(evt);
            }
        });
        txtTempoMinutos.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTempoMinutosFocusLost(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("Duração (Minutos:)");

        txtObservacoes.setColumns(20);
        txtObservacoes.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtObservacoes.setRows(5);
        txtObservacoes.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Observações", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14), new java.awt.Color(51, 51, 51))); // NOI18N
        txtObservacoes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtObservacoesKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(txtObservacoes);

        txtMotivo.setColumns(20);
        txtMotivo.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtMotivo.setRows(5);
        txtMotivo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Motivo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14), new java.awt.Color(51, 51, 51))); // NOI18N
        txtMotivo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMotivoKeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(txtMotivo);

        txtDataPrevTermino.setDateFormatString("dd/MM/yyyy HH:mm");
        txtDataPrevTermino.setEnabled(false);
        txtDataPrevTermino.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText("Dt. Prev. Término");

        txtPessoaAtendida.setEditable(false);
        txtPessoaAtendida.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtPessoaAtendida.setToolTipText("Duplo clique para remover o plano");
        txtPessoaAtendida.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPessoaAtendidaMouseClicked(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setText("Pessoa Atendida");

        btnPesquisarPessoa.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnPesquisarPessoa.setText("...");
        btnPesquisarPessoa.setToolTipText("Clique para pesquisar um plano");
        btnPesquisarPessoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarPessoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txtDataCriacao, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(txtDataAgendamento, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(txtTempoMinutos, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDataPrevTermino, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtFuncionarioDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPesquisarDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtPessoaAtendida, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPesquisarPessoa, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(0, 46, Short.MAX_VALUE))
                            .addComponent(cmbSituacao, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtDataAgendamento, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtDataCriacao, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(txtDataPrevTermino, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnPesquisarDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFuncionarioDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txtTempoMinutos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(29, 29, 29))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnPesquisarPessoa, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtPessoaAtendida, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnConfirmar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/iconeConfirmar.png"))); // NOI18N
        btnConfirmar.setText("Confirmar");
        btnConfirmar.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnConfirmar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSair))
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConfirmar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        Agendamento a = new Agendamento();
        Pessoa p = new Funcionario();
        if (!txtID.getText().equals("")) {
            a.setId(Integer.parseInt(txtID.getText()));
            a.setDataCriacao(new Date());
            p = agendamentodEdicao.getPessoaCriadora();
            a.setDataCriacao(agendamentodEdicao.getDataCriacao());
        } else {
            a.setDataCriacao(new Date());
            p.setIdPessoa(FuncionarioController.getFuncionarioLogado().getIdPessoa());
        }

        a.setPessoaCriadora(p);
        a.setDataAgendada(txtDataAgendamento.getDate());
        a.setTempoDuracao(Integer.parseInt(txtTempoMinutos.getValue().toString().replace(".", "")));
        a.setFuncionarioResponsavel((Funcionario) funcionarioEscolhido);
        a.setPessoaAtendida(pessoaAtendida);
        a.setStatus(cmbSituacao.getSelectedItem().toString());
        a.setMotivo(txtMotivo.getText());
        a.setObservacao(txtObservacoes.getText());

        try {
            funcionarioController.getAgendamentoController().validarAtributos(a);

            if (agendamentodEdicao == null) {
                funcionarioController.getAgendamentoController().inserirAgendamento(a);
                Uteis.mensagemAviso(this, "Agendamento inserido com sucesso", "Aviso");
                funcionarioController.inserirHistorico(new Historico("Inseriu um agendamento", new Date(), funcionarioController.getFuncionarioLogado()));
                this.dispose();
            } else {
                funcionarioController.getAgendamentoController().editarAgendamento(a);
                Uteis.mensagemAviso(this, "Agendamento editado com sucesso", "Aviso");
                funcionarioController.inserirHistorico(new Historico("Editou agendamento de código '" + agendamentodEdicao.getId() + "'", new Date(), funcionarioController.getFuncionarioLogado()));
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

    private void btnPesquisarDestinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarDestinoActionPerformed
        funcionarioController.FrmPesquisarPessoaSetVisible(this, true, true, false, false);
        tipoPesquisaEfetuada = "Funcionário Destino";
    }//GEN-LAST:event_btnPesquisarDestinoActionPerformed

    private void txtFuncionarioDestinoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtFuncionarioDestinoMouseClicked
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            evt.consume();
            funcionarioEscolhido = null;
            txtFuncionarioDestino.setText("");
        }
    }//GEN-LAST:event_txtFuncionarioDestinoMouseClicked

    private void txtObservacoesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtObservacoesKeyTyped

    }//GEN-LAST:event_txtObservacoesKeyTyped

    private void txtMotivoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMotivoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMotivoKeyTyped

    private void txtDataAgendamentoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataAgendamentoFocusLost
        if (txtDataAgendamento.getDate() != null) {
            txtDataPrevTermino.setDate(calculaDataFinal(txtDataAgendamento.getDate(), Integer.parseInt(txtTempoMinutos.getValue().toString().replace(".", ""))));
        }
    }//GEN-LAST:event_txtDataAgendamentoFocusLost

    private void txtTempoMinutosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTempoMinutosFocusLost
        if (txtDataAgendamento.getDate() != null) {
            txtDataPrevTermino.setDate(calculaDataFinal(txtDataAgendamento.getDate(), Integer.parseInt(txtTempoMinutos.getValue().toString().replace(".", ""))));
        }
    }//GEN-LAST:event_txtTempoMinutosFocusLost

    private void txtTempoMinutosStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_txtTempoMinutosStateChanged
        if (txtDataAgendamento.getDate() != null) {
            txtDataPrevTermino.setDate(calculaDataFinal(txtDataAgendamento.getDate(), Integer.parseInt(txtTempoMinutos.getValue().toString().replace(".", ""))));
        }
    }//GEN-LAST:event_txtTempoMinutosStateChanged

    private void txtDataAgendamentoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtDataAgendamentoPropertyChange
        if (txtDataAgendamento.getDate() != null) {
            txtDataPrevTermino.setDate(calculaDataFinal(txtDataAgendamento.getDate(), Integer.parseInt(txtTempoMinutos.getValue().toString().replace(".", ""))));
        }
    }//GEN-LAST:event_txtDataAgendamentoPropertyChange

    private void txtPessoaAtendidaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPessoaAtendidaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPessoaAtendidaMouseClicked

    private void btnPesquisarPessoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarPessoaActionPerformed
        funcionarioController.FrmPesquisarPessoaSetVisible(this, true, true, true, true);
        tipoPesquisaEfetuada = "Pessoa Atendida";
    }//GEN-LAST:event_btnPesquisarPessoaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnPesquisarDestino;
    private javax.swing.JButton btnPesquisarPessoa;
    private javax.swing.JButton btnSair;
    private javax.swing.JComboBox<String> cmbSituacao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private com.toedter.components.JLocaleChooser jLocaleChooser1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private com.toedter.calendar.JDateChooser txtDataAgendamento;
    private com.toedter.calendar.JDateChooser txtDataCriacao;
    private com.toedter.calendar.JDateChooser txtDataPrevTermino;
    private javax.swing.JTextField txtFuncionarioDestino;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextArea txtMotivo;
    private javax.swing.JTextArea txtObservacoes;
    private javax.swing.JTextField txtPessoaAtendida;
    private javax.swing.JSpinner txtTempoMinutos;
    // End of variables declaration//GEN-END:variables
}
