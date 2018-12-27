package View;

import Controller.FuncionarioController;
import Geral.Imagens;
import Geral.Uteis;
import Model.Agendamento;
import Model.Funcionario;
import Model.Historico;
import TableModel.AgendamentoTableModel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import Interfaces_Inversao_Controle.IPesquisarPessoa;
import Model.Pessoa;

/**
 * JFrame relacionada ao Agendamento Lista
 *
 * @author Antonio Lucas Christofoletti
 */
public class FrmAgendamentoLista extends javax.swing.JFrame implements IPesquisarPessoa {

    /**
     * Controller do tipo funcionário
     */
    private FuncionarioController funcionarioController;
    /**
     * TableModel que gerencia as informações da JTable de agendamentos
     */
    private AgendamentoTableModel agendamentoTableModel;
    /**
     * Instância da própria classe em questão, por conta do padrão de projeto
     * Sington
     */
    private static FrmAgendamentoLista frmAgendamentoLista;
    /**
     * Funcionário escolhido através do campo de busca
     */
    private Funcionario funcionarioEscolhido;

    /**
     * Construtor da classe
     */
    private FrmAgendamentoLista() {
        initComponents();
        this.funcionarioController = FuncionarioController.getInstance();
        configuraTela();
    }

    /**
     * Método responsável por retornar uma instância da própria classe
     *
     * @return retorna uma instância da própria classe
     */
    public static FrmAgendamentoLista getInstance() {
        if (frmAgendamentoLista == null) {
            frmAgendamentoLista = new FrmAgendamentoLista();
        }

        frmAgendamentoLista.setSize(frmAgendamentoLista.getPreferredSize());
        frmAgendamentoLista.setLocationRelativeTo(null);
        frmAgendamentoLista.limparTela();
        frmAgendamentoLista.possuiPermissaoVerificarTodosAgendamentos();
        return frmAgendamentoLista;
    }

    /**
     * Método responsável por limpar todos os campos
     */
    public void limparTela() {
        txtFuncionario.setText("");
        funcionarioEscolhido = null;
        txtDtInicial.setDate(null);
        txtDataFinal.setDate(null);
        cmbSituacao.setSelectedItem("Todos");
        cmbOrdemPesquisa1.setSelectedItem("Data Decrescente");
        agendamentoTableModel.removeAll();
    }

    /**
     * Método responsável por habilitar ou travar o campo de pesquisa por
     * funcionário
     */
    public void possuiPermissaoVerificarTodosAgendamentos() {
        if (!funcionarioController.possuiPermissao("Visualizar Todos os Agendamentos")) {
            btnPesquisarFuncionario.setEnabled(false);
            btnPesquisarFuncionario.setToolTipText("O usuário não possui a permissão 'Visualizar Todos os Agendamentos'");
            txtFuncionario.setToolTipText("O usuário não possui a permissão 'Visualizar Todos os Agendamentos'");

            funcionarioEscolhido = funcionarioController.getFuncionarioLogado();
            txtFuncionario.setText(funcionarioEscolhido.getNome());
            return;
        } else {
            btnPesquisarFuncionario.setEnabled(true);
            btnPesquisarFuncionario.setToolTipText("Clique para Pesquisar Usuário");
            txtFuncionario.setToolTipText("Duplo Clique para Limpar Funcionário");
        }
    }

    /**
     * Método responsável por configurar algumas propriedades da tela
     */
    public void configuraTela() {
        this.getContentPane().setBackground(Color.WHITE);
        this.setIconImage(Imagens.iconeSistema);
        agendamentoTableModel = new AgendamentoTableModel();
        tblAgendamentos.setModel(agendamentoTableModel);
        agendamentoTableModel.configurarTableModel(tblAgendamentos);

        InputMap inputMap = this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "atualizarTela");
        this.getRootPane().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMap);
        this.getRootPane().getActionMap().put("atualizarTela", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent arg0) {
                atualizarTabela();
            }
        });

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
                btnInserirActionPerformed(null);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "editar");
        this.getRootPane().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMap);
        this.getRootPane().getActionMap().put("editar", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent arg0) {
                btnEditarActionPerformed(null);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0), "aprovar");
        this.getRootPane().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMap);
        this.getRootPane().getActionMap().put("aprovar", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent arg0) {
                btnAprovarActionPerformed(null);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0), "cancelar");
        this.getRootPane().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMap);
        this.getRootPane().getActionMap().put("cancelar", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent arg0) {
                btnCancelarActionPerformed(null);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0), "replicar");
        this.getRootPane().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMap);
        this.getRootPane().getActionMap().put("replicar", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent arg0) {
                btnReplicarActionPerformed(null);
            }
        });
    }

    /**
     * Método responsável por atualizar a tabela de agendamentos
     */
    public void atualizarTabela() {
        try {
            agendamentoTableModel.removeAll();
            String idFuncionario = "";

            if (funcionarioEscolhido != null) {
                idFuncionario = String.valueOf(funcionarioEscolhido.getIdEspecifico());
            }

            agendamentoTableModel.addAll(funcionarioController.getAgendamentoController().pesquisarAgendamento(idFuncionario, txtDtInicial.getDate(), txtDataFinal.getDate(), cmbSituacao.getSelectedItem().toString(), cmbOrdemPesquisa1.getSelectedItem().toString()));

            if (agendamentoTableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Nenhum agendamento encontrado", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Mensagem de Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método responsável por editar um agendamento da tabela de agendamentos
     */
    public void editar() {
        if (tblAgendamentos.getSelectedRow() == -1) {
            Uteis.mensagemAviso(this, "Selecione uma linha da tabela", "Aviso");
            return;
        }

        if (!funcionarioController.possuiPermissao("Editar Agendamento")) {
            Uteis.mensagemAviso(this, "O usuário não possui a permissão 'Editar Agendamento'", "Aviso");
            return;
        }

        funcionarioController.getAgendamentoController().frmAgendamentoIncAlt(agendamentoTableModel.getAgendamento(tblAgendamentos.getSelectedRow()), true);
    }

    /**
     * Método responsável por setar um funcionário pesquisado, método
     * relacionado com a Interface IPesquisarFuncionario
     *
     * @param p pessoa que foi pesquisada
     */
    @Override
    public void preencheCampos(Pessoa p) {
        if (p != null) {
            funcionarioEscolhido = (Funcionario) p;
            txtFuncionario.setText(p.getNome());
        }
    }

    /**
     * Método responsável por setar as propriedades da tela
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmbOrdemPesquisa = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jPopupMenuConflitoAgendamentos = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAgendamentos = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        cmbSituacao = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        txtDataFinal = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        txtDtInicial = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        txtFuncionario = new javax.swing.JTextField();
        btnPesquisarFuncionario = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        cmbOrdemPesquisa1 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        btnSair = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnInserir = new javax.swing.JButton();
        btnAtualizar = new javax.swing.JButton();
        btnAprovar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnReplicar = new javax.swing.JButton();

        cmbOrdemPesquisa.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cmbOrdemPesquisa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Data Decrescente", "Data Crescente" }));

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("Ordenar Por");

        jMenuItem1.setText("Verificar Conflitos");
        jMenuItem1.setToolTipText("");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenuConflitoAgendamentos.add(jMenuItem1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("PsiqueSis - Agendamento - Lista");
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Agendamentos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 18))); // NOI18N
        jPanel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        tblAgendamentos.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        tblAgendamentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Descrição", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAgendamentos.setComponentPopupMenu(jPopupMenuConflitoAgendamentos);
        tblAgendamentos.addHierarchyBoundsListener(new java.awt.event.HierarchyBoundsListener() {
            public void ancestorMoved(java.awt.event.HierarchyEvent evt) {
            }
            public void ancestorResized(java.awt.event.HierarchyEvent evt) {
                tblAgendamentosAncestorResized(evt);
            }
        });
        tblAgendamentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAgendamentosMouseClicked(evt);
            }
        });
        tblAgendamentos.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                tblAgendamentosComponentResized(evt);
            }
        });
        jScrollPane1.setViewportView(tblAgendamentos);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filtros", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        cmbSituacao.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cmbSituacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Aprovado", "Cancelado", "Pendente" }));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("Situação");

        txtDataFinal.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Dt. Final Agenda.");

        txtDtInicial.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Dt.Inicial Agenda.");

        txtFuncionario.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtFuncionario.setToolTipText("Duplo Clique para Limpar Funcionário");
        txtFuncionario.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtFuncionario.setEnabled(false);
        txtFuncionario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtFuncionarioMouseClicked(evt);
            }
        });
        txtFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFuncionarioActionPerformed(evt);
            }
        });

        btnPesquisarFuncionario.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnPesquisarFuncionario.setText("...");
        btnPesquisarFuncionario.setToolTipText("Clique para Pesquisar Usuário");
        btnPesquisarFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarFuncionarioActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Funcionário ");

        cmbOrdemPesquisa1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cmbOrdemPesquisa1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Data Decrescente", "Data Crescente" }));

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText("Ordenar Por Dt. Agenda.");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPesquisarFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDtInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbOrdemPesquisa1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(265, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbOrdemPesquisa1)
                            .addComponent(cmbSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(27, 27, 27))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(4, 4, 4)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnPesquisarFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(txtDataFinal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtDtInicial, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );

        btnSair.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/iconeSair.png"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        btnEditar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/iconeEditar.png"))); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnInserir.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnInserir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/iconeAdicionar.png"))); // NOI18N
        btnInserir.setText("Inserir");
        btnInserir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInserirActionPerformed(evt);
            }
        });

        btnAtualizar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnAtualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/IconeSair_1.png"))); // NOI18N
        btnAtualizar.setText("Atualizar");
        btnAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarActionPerformed(evt);
            }
        });

        btnAprovar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnAprovar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/iconeAprovar.png"))); // NOI18N
        btnAprovar.setText("Aprovar");
        btnAprovar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAprovarActionPerformed(evt);
            }
        });

        btnCancelar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/iconeRemover.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnReplicar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnReplicar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/iconeReplicar.png"))); // NOI18N
        btnReplicar.setText("Replicar");
        btnReplicar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReplicarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnInserir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAprovar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReplicar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAtualizar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSair)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSair, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnInserir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAtualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAprovar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnReplicar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        if (Uteis.mensagemCondicional(this, "Deseja realmente sair?", "Confirmação") == 0) {
            this.dispose();
        }
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarActionPerformed
        atualizarTabela();
    }//GEN-LAST:event_btnAtualizarActionPerformed

    private void btnInserirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInserirActionPerformed
        if (!funcionarioController.possuiPermissao("Inserir Agendamento")) {
            Uteis.mensagemAviso(this, "O usuário não possui a permissão 'Inserir Agendamento'", "Aviso");
            return;
        }

        funcionarioController.getAgendamentoController().frmAgendamentoIncAlt(null, true);
    }//GEN-LAST:event_btnInserirActionPerformed

    private void tblAgendamentosComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tblAgendamentosComponentResized

    }//GEN-LAST:event_tblAgendamentosComponentResized

    private void tblAgendamentosAncestorResized(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_tblAgendamentosAncestorResized

    }//GEN-LAST:event_tblAgendamentosAncestorResized

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        editar();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed

    }//GEN-LAST:event_formKeyPressed

    private void tblAgendamentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAgendamentosMouseClicked
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            evt.consume();
            editar();
        }
    }//GEN-LAST:event_tblAgendamentosMouseClicked

    private void txtFuncionarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtFuncionarioMouseClicked
        if (btnPesquisarFuncionario.isEnabled()) {
            if (evt.getClickCount() == 2 && !evt.isConsumed()) {
                evt.consume();
                funcionarioEscolhido = null;
                txtFuncionario.setText("");
            }
        }
    }//GEN-LAST:event_txtFuncionarioMouseClicked

    private void txtFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFuncionarioActionPerformed

    }//GEN-LAST:event_txtFuncionarioActionPerformed

    private void btnPesquisarFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarFuncionarioActionPerformed
        funcionarioController.FrmPesquisarPessoaSetVisible(this, true, true, false, false);
    }//GEN-LAST:event_btnPesquisarFuncionarioActionPerformed

    private void btnAprovarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAprovarActionPerformed
        try {
            if (tblAgendamentos.getSelectedRow() == -1) {
                Uteis.mensagemAviso(this, "Selecione uma linha da tabela", "Aviso");
                return;
            }

            Agendamento a = agendamentoTableModel.getAgendamento(tblAgendamentos.getSelectedRow());

            if (a.getStatus().equals("Conflito")) {
                Uteis.mensagemAviso(this, "O agendamento não pode ser aprovado com o status 'conflito' ", "Aviso");
                return;
            }

            if (!funcionarioController.possuiPermissao("Aprovar Agendamento")) {
                Uteis.mensagemAviso(this, "O usuário não possui a permissão 'Aprovar Agendamento'", "Aviso");
                return;
            }

            if (Uteis.mensagemCondicional(this, "Deseja realmente aprovar o agendamento de ID '" + agendamentoTableModel.getValueAt(tblAgendamentos.getSelectedRow(), "ID") + "'?", "Confirmação") == 1) {
                return;
            }

            a.setStatus("Aprovado");
            funcionarioController.getAgendamentoController().editarAgendamento(a);
            Uteis.mensagemAviso(this, "Agendamento aprovado com sucesso", "Aviso");
            funcionarioController.inserirHistorico(new Historico("Aprovou o agendamento de código '" + a.getId() + "'", new Date(), funcionarioController.getFuncionarioLogado()));
            atualizarTabela();
        } catch (Exception ex) {
            Uteis.mensagemErro(this, ex.getMessage(), "Erro");
        }
    }//GEN-LAST:event_btnAprovarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        try {
            if (tblAgendamentos.getSelectedRow() == -1) {
                Uteis.mensagemAviso(this, "Selecione uma linha da tabela", "Aviso");
                return;
            }

            Agendamento a = agendamentoTableModel.getAgendamento(tblAgendamentos.getSelectedRow());

            if (a.getStatus().equals("Conflito")) {
                Uteis.mensagemAviso(this, "O agendamento não pode ser cancelado com o status 'conflito' ", "Aviso");
                return;
            }

            if (!funcionarioController.possuiPermissao("Cancelar Agendamento")) {
                Uteis.mensagemAviso(this, "O usuário não possui a permissão 'Cancelar Agendamento'", "Aviso");
                return;
            }

            if (Uteis.mensagemCondicional(this, "Deseja realmente cancelar o agendamento de ID '" + agendamentoTableModel.getValueAt(tblAgendamentos.getSelectedRow(), "ID") + "'?", "Confirmação") == 1) {
                return;
            }

            a.setStatus("Cancelado");
            funcionarioController.getAgendamentoController().editarAgendamento(a);
            Uteis.mensagemAviso(this, "Agendamento cancelado com sucesso", "Aviso");
            funcionarioController.inserirHistorico(new Historico("Cancelou o agendamento de código '" + a.getId() + "'", new Date(), funcionarioController.getFuncionarioLogado()));
            atualizarTabela();
        } catch (Exception ex) {
            Uteis.mensagemErro(this, ex.getMessage(), "Erro");
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnReplicarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReplicarActionPerformed
        try {
            if (tblAgendamentos.getSelectedRow() == -1) {
                Uteis.mensagemAviso(this, "Selecione uma linha da tabela", "Aviso");
                return;
            }

            Agendamento a = agendamentoTableModel.getAgendamento(tblAgendamentos.getSelectedRow());

            if (a.getStatus().equals("Conflito")) {
                Uteis.mensagemAviso(this, "O agendamento não pode ser replicado com o status 'conflito' ", "Aviso");
                return;
            }

            if (!funcionarioController.possuiPermissao("Replicar Agendamento")) {
                Uteis.mensagemAviso(this, "O usuário não possui a permissão 'Replicar Agendamento'", "Aviso");
                return;
            }

            if (Uteis.mensagemCondicional(this, "Deseja realmente replicar o agendamento de ID '" + agendamentoTableModel.getValueAt(tblAgendamentos.getSelectedRow(), "ID") + "'?", "Confirmação") == 1) {
                return;
            }

            JSpinner js = new JSpinner();
            js.setFont(new java.awt.Font("Arial", 0, 14));
            js.setModel(new SpinnerNumberModel(0, 0, 999999999, 1));
            Object[] params1 = {"Defina a quantidade de horas entre cada novo agendamento\n", js};
            int result = JOptionPane.showConfirmDialog(null, params1, "Preenchimento Informações", JOptionPane.PLAIN_MESSAGE);
            if (((JSpinner) params1[1]).getValue() == null || result != 0) {
                Uteis.mensagemAviso(this, "Quantidade de horas inválida", "Aviso");
                return;
            }

            int quantiHoras = (int) ((JSpinner) params1[1]).getValue();

            if (quantiHoras == 0) {
                Uteis.mensagemAviso(this, "A quantidade de horas deve ser superior a 0", "Aviso");
                return;
            }

            js.setValue(0);
            Object[] params2 = {"Define a quantidade de agendamentos que deverão ser criados\n", js};

            result = JOptionPane.showConfirmDialog(null, params2, "Preenchimento Informações", JOptionPane.PLAIN_MESSAGE);

            if (((JSpinner) params1[1]).getValue() == null || result != 0) {
                Uteis.mensagemAviso(this, "Quantidade de agendamentos inválida", "Aviso");
                return;
            }

            int quantiAgendamentos = (int) ((JSpinner) params1[1]).getValue();

            if (quantiAgendamentos == 0) {
                Uteis.mensagemAviso(this, "A quantidade de agendamentos deve ser superior a 0", "Aviso");
                return;
            }

            funcionarioController.getAgendamentoController().replicarAgendamento(a, quantiAgendamentos, quantiHoras);
            Uteis.mensagemAviso(this, "Agendamento replicado com sucesso", "Aviso");
            funcionarioController.inserirHistorico(new Historico("Replicou o agendamento de código '" + a.getId() + "' " + quantiAgendamentos + " vezes com o intervalo de " + quantiHoras + " hora(s)", new Date(), funcionarioController.getFuncionarioLogado()));
            atualizarTabela();
        } catch (Exception ex) {
            Uteis.mensagemErro(this, ex.getMessage(), "Erro");
        }
    }//GEN-LAST:event_btnReplicarActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        try {
            if (tblAgendamentos.getSelectedRow() == -1) {
                Uteis.mensagemAviso(this, "Selecione uma linha da tabela", "Aviso");
                return;
            }

            if (!agendamentoTableModel.getAgendamento(tblAgendamentos.getSelectedRow()).getStatus().equals("Conflito")) {
                Uteis.mensagemAviso(this, "Agendamento sem conflitos", "Aviso");
                return;
            }
            ArrayList agendamentos = funcionarioController.getAgendamentoController().existeConflitoData(String.valueOf(agendamentoTableModel.getAgendamento(tblAgendamentos.getSelectedRow()).getId()));

            String mensagem = "ID(S) Agendamento(s) em conflito: ";
            for (int i = 0; i < agendamentos.size(); i++) {
                mensagem += agendamentos.get(i).toString() + ", ";
            }

            mensagem = mensagem.substring(0, mensagem.length() - 2) + ".";
            Uteis.mensagemAviso(null, mensagem, "Aviso");
        } catch (Exception ex) {
            Logger.getLogger(FrmAgendamentoLista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAprovar;
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnInserir;
    private javax.swing.JButton btnPesquisarFuncionario;
    private javax.swing.JButton btnReplicar;
    private javax.swing.JButton btnSair;
    private javax.swing.JComboBox<String> cmbOrdemPesquisa;
    private javax.swing.JComboBox<String> cmbOrdemPesquisa1;
    private javax.swing.JComboBox<String> cmbSituacao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenuConflitoAgendamentos;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblAgendamentos;
    private com.toedter.calendar.JDateChooser txtDataFinal;
    private com.toedter.calendar.JDateChooser txtDtInicial;
    private javax.swing.JTextField txtFuncionario;
    // End of variables declaration//GEN-END:variables
}
