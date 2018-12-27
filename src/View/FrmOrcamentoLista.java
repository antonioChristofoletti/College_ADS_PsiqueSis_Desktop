/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.FuncionarioController;
import Geral.Imagens;
import Geral.Uteis;
import Model.Historico;
import Model.Orcamento;
import Model.Parcela;
import TableModel.OrcamentoTableModel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 * JFrame relacionada a orçamento lista
 *
 * @author Antonio Lucas Christofoletti
 */
public class FrmOrcamentoLista extends javax.swing.JFrame {

    /**
     * Controller do tipo funcionário
     */
    private FuncionarioController funcionarioController;
    /**
     * TableModel que gerencia as informações da JTable de orçamentos
     */
    private OrcamentoTableModel orcamentoTableModel;
    /**
     * Instância da própria classe em questão, por conta do padrão de projeto
     * Sington
     */
    private static FrmOrcamentoLista frmOrcamentoLista;

    /**
     * Construtor da classe
     */
    private FrmOrcamentoLista() {
        initComponents();
        this.funcionarioController = FuncionarioController.getInstance();
        configuraTela();
    }

    /**
     * Método responsável por retornar uma instância da própria classe
     *
     * @return retorna uma instância da própria classe
     */
    public static FrmOrcamentoLista getInstance() {
        if (frmOrcamentoLista == null) {
            frmOrcamentoLista = new FrmOrcamentoLista();
        }

        frmOrcamentoLista.setSize(frmOrcamentoLista.getPreferredSize());
        frmOrcamentoLista.setLocationRelativeTo(null);
        frmOrcamentoLista.limparTela();
        return frmOrcamentoLista;
    }

    /**
     * Método responsável por limpar todos os campos
     */
    public void limparTela() {
        txtDescricao.setText("");
        txtDtInicial.setDate(null);
        txtDataFinal.setDate(null);
        cmbStatus.setSelectedItem("Todos");
        cmbTipo.setSelectedItem("Todos");
        orcamentoTableModel.removeAll();
    }

    /**
     * Método responsável por configurar algumas propriedades da tela
     */
    public void configuraTela() {
        this.getContentPane().setBackground(Color.WHITE);
        this.setIconImage(Imagens.iconeSistema);

        orcamentoTableModel = new OrcamentoTableModel();
        tblOrcamentos.setModel(orcamentoTableModel);
        orcamentoTableModel.configurarTableModel(tblOrcamentos);

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

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0), "remover");
        this.getRootPane().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMap);
        this.getRootPane().getActionMap().put("remover", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent arg0) {
                btnRemoverActionPerformed(null);
            }
        });

        btnEditar.requestFocusInWindow();
        this.requestFocusInWindow();
    }

    /**
     * Método responsável por atualizar a tabela de orçamentos
     */
    public void atualizarTabela() {
        try {
            orcamentoTableModel.removeAll();
            orcamentoTableModel.addAll(funcionarioController.getOrcamentoController().pesquisarOrcamento(txtDescricao.getText(), cmbStatus.getSelectedItem().toString(), txtDtInicial.getDate(), txtDataFinal.getDate(), cmbTipo.getSelectedItem().toString()));

            if (orcamentoTableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Nenhum Orçamento Encontrado", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Mensagem de Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método responsável por editar um orçamento da tabela de orçamentos
     */
    public void editar() {
        if (tblOrcamentos.getSelectedRow() == -1) {
            Uteis.mensagemAviso(this, "Selecione uma linha da tabela", "Aviso");
            return;
        }

        if (!funcionarioController.possuiPermissao("Editar Orçamento")) {
            Uteis.mensagemAviso(this, "O usuário não possui a permissão 'Editar Orçamento'", "Aviso");
            return;
        }

        funcionarioController.getOrcamentoController().frmOrcamentoIncAltSetVisible(orcamentoTableModel.getOrcamento(tblOrcamentos.getSelectedRow()), rootPaneCheckingEnabled);
    }

    /**
     * Método responsável por setar as propriedades da tela
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnSair = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblOrcamentos = new javax.swing.JTable();
        btnInserir = new javax.swing.JButton();
        btnAtualizar = new javax.swing.JButton();
        btnGerarExcel = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtDescricao = new javax.swing.JTextField();
        cmbStatus = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        txtDtInicial = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        txtDataFinal = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        cmbTipo = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        btnRemover = new javax.swing.JButton();
        btnGraficos = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("PsiqueSis - Orçamento - Lista");

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

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Orçamentos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 18))); // NOI18N
        jPanel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        tblOrcamentos.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        tblOrcamentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Descrição", "Dt. Vencimento", "Status", "Tipo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblOrcamentos.addHierarchyBoundsListener(new java.awt.event.HierarchyBoundsListener() {
            public void ancestorMoved(java.awt.event.HierarchyEvent evt) {
            }
            public void ancestorResized(java.awt.event.HierarchyEvent evt) {
                tblOrcamentosAncestorResized(evt);
            }
        });
        tblOrcamentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblOrcamentosMouseClicked(evt);
            }
        });
        tblOrcamentos.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                tblOrcamentosComponentResized(evt);
            }
        });
        jScrollPane1.setViewportView(tblOrcamentos);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

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

        btnGerarExcel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnGerarExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/gerarExcelIcone.png"))); // NOI18N
        btnGerarExcel.setText("Gerar Excel");
        btnGerarExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGerarExcelActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filtros", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Descrição");

        txtDescricao.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtDescricao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescricaoActionPerformed(evt);
            }
        });

        cmbStatus.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Normal", "Atrasado", "Vencido", "Quitado", "Cancelado" }));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("Status");

        txtDtInicial.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Dt.Inicial");

        txtDataFinal.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Dt. Final");

        cmbTipo.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cmbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Entrada", "Saída" }));

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("Tipo");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtDtInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(txtDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(154, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDataFinal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDtInicial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                        .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel2))
                                .addGap(29, 29, 29)))))
                .addContainerGap())
        );

        btnRemover.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/iconeRemover.png"))); // NOI18N
        btnRemover.setText("Remover");
        btnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverActionPerformed(evt);
            }
        });

        btnGraficos.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnGraficos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/iconeGrafico.png"))); // NOI18N
        btnGraficos.setText("Gráficos");
        btnGraficos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGraficosActionPerformed(evt);
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
                        .addComponent(btnRemover)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGraficos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGerarExcel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                    .addComponent(btnRemover, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnGerarExcel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGraficos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(btnAtualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnInserir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        editar();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void tblOrcamentosAncestorResized(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_tblOrcamentosAncestorResized

    }//GEN-LAST:event_tblOrcamentosAncestorResized

    private void tblOrcamentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOrcamentosMouseClicked
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            evt.consume();
            editar();
        }
    }//GEN-LAST:event_tblOrcamentosMouseClicked

    private void tblOrcamentosComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tblOrcamentosComponentResized

    }//GEN-LAST:event_tblOrcamentosComponentResized

    private void btnInserirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInserirActionPerformed
        if (!funcionarioController.possuiPermissao("Inserir Orçamento")) {
            Uteis.mensagemAviso(this, "O usuário não possui a permissão 'Inserir Orçamento'", "Aviso");
            return;
        }

        funcionarioController.getOrcamentoController().frmOrcamentoIncAltSetVisible(null, true);
    }//GEN-LAST:event_btnInserirActionPerformed

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarActionPerformed
        atualizarTabela();
    }//GEN-LAST:event_btnAtualizarActionPerformed

    private void btnGerarExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGerarExcelActionPerformed
        if (!funcionarioController.possuiPermissao("Gerar Excel Orçamento")) {
            Uteis.mensagemAviso(this, "O usuário não possui a permissão 'Gerar Excel Orçamento'", "Aviso");
            return;
        }

        JFileChooser fc = new JFileChooser();
        int option = fc.showSaveDialog(this);
        if (option != JFileChooser.APPROVE_OPTION) {
            return;
        }

        String nomeArquivo = fc.getSelectedFile().getName();
        String caminhoArquivo = fc.getSelectedFile().getParentFile().getPath();
        int tamanho = nomeArquivo.length();
        String extensaoArquivo = "";
        String arquivoSalvar = "";
        if (tamanho > 4) {
            extensaoArquivo = nomeArquivo.substring(tamanho - 4, tamanho);
        }
        if (extensaoArquivo.equals(".xls")) {
            arquivoSalvar = caminhoArquivo + "\\" + nomeArquivo;
        } else {
            arquivoSalvar = caminhoArquivo + "\\" + nomeArquivo + ".xls";
        }

        try {
            PrintWriter excel = new PrintWriter(new OutputStreamWriter(new FileOutputStream(arquivoSalvar), "UTF-8"));

            for (int i = 0; i < orcamentoTableModel.getColumnCount(); i++) {
                excel.write(Uteis.removeAcentos(orcamentoTableModel.getColumnName(i)) + "\t");
            }

            excel.write("\n");

            for (int i = 0; i < orcamentoTableModel.getRowCount(); i++) {
                for (int j = 0; j < orcamentoTableModel.getColumnCount(); j++) {
                    excel.write(Uteis.removeAcentos(orcamentoTableModel.getValueAt(i, j).toString().toUpperCase()) + "\t");
                }
                excel.write("\n");

                Orcamento o = orcamentoTableModel.getOrcamento(i);

                excel.write("\t \t \t \t \t ID \t Descricao \t Dt. Vencimento \t Status \t Valor \n");
                Double valorTotal = 0.0;
                for (Parcela p : o.getParcelas()) {
                    excel.write("\t \t \t \t \t");
                    excel.write(p.getId() + "\t");
                    excel.write(Uteis.removeAcentos(p.getDescricao()) + "\t");
                    excel.write(Uteis.converteData("dd/MM/yyyy", p.getDataVencimento()) + "\t");
                    excel.write(Uteis.removeAcentos(p.getStatus()) + "\t");
                    excel.write(p.getValor().toString() + "\t");
                    excel.write("\n");
                    valorTotal += p.getValor();
                }
                excel.write("\t \t \t \t \t \t \t \t Valor Total:\t" + valorTotal + "\n");
            }

            excel.close();
            Uteis.mensagemAviso(this, "Arquivo gerado com sucesso", "Aviso");
            funcionarioController.inserirHistorico(new Historico("Gerou um excel de orçamento", new Date(), funcionarioController.getFuncionarioLogado()));
        } catch (Exception ex) {
            Uteis.mensagemErro(this, "Erro ao gerar arquivo Excel. Erro: " + ex.getMessage(), "Erro");
        }
    }//GEN-LAST:event_btnGerarExcelActionPerformed

    private void txtDescricaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescricaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescricaoActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        try {
            if (tblOrcamentos.getSelectedRow() == -1) {
                Uteis.mensagemAviso(this, "Selecione uma linha da tabela", "Aviso");
                return;
            }

            if (!funcionarioController.possuiPermissao("Remover Orçamento")) {
                Uteis.mensagemAviso(this, "O usuário não possui a permissão 'Remover  Orçamento'", "Aviso");
                return;
            }

            if (Uteis.mensagemCondicional(this, "Deseja realmente remover o orçamento?", "Confirmação") == 1) {
                return;
            }

            Orcamento o = orcamentoTableModel.getOrcamento(tblOrcamentos.getSelectedRow());

            if (!o.getStatus().equals("Cancelado")) {
                o.setStatus("Cancelado1");
            }

            funcionarioController.getOrcamentoController().editarOrcamento(o);
            Uteis.mensagemAviso(this, "Orçamento removido com sucesso", "Aviso");
            funcionarioController.inserirHistorico(new Historico("Removeu o orçamento de código '" + o.getId() + "'", new Date(), funcionarioController.getFuncionarioLogado()));
            atualizarTabela();
        } catch (Exception ex) {
            Uteis.mensagemErro(this, ex.getMessage(), "Erro");
        }
    }//GEN-LAST:event_btnRemoverActionPerformed

    private void btnGraficosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGraficosActionPerformed
        funcionarioController.getOrcamentoController().FrmOrcamentoGraficoSetVisible(true);
    }//GEN-LAST:event_btnGraficosActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnGerarExcel;
    private javax.swing.JButton btnGraficos;
    private javax.swing.JButton btnInserir;
    private javax.swing.JButton btnRemover;
    private javax.swing.JButton btnSair;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JComboBox<String> cmbTipo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblOrcamentos;
    private com.toedter.calendar.JDateChooser txtDataFinal;
    private javax.swing.JTextField txtDescricao;
    private com.toedter.calendar.JDateChooser txtDtInicial;
    // End of variables declaration//GEN-END:variables
}
