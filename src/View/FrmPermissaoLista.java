package View;

import Controller.FuncionarioController;
import TableModel.PermissaoTableModel;
import Geral.Imagens;
import Geral.Uteis;
import Model.Historico;
import Model.Permissao;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 * JFrame relacionada a permissão lista
 *
 * @author Antonio Lucas Christofoletti
 */
public class FrmPermissaoLista extends javax.swing.JFrame {

    /**
     * Controller do tipo funcionário
     */
    private FuncionarioController funcionarioController;
    /**
     * TableModel que gerencia as informações da JTable de permissões
     */
    private PermissaoTableModel permissaoTableModel;
    /**
     * Instância da própria classe em questão, por conta do padrão de projeto
     * Sington
     */
    private static FrmPermissaoLista frmPermissaoLista;

    /**
     * Construtor da classe
     */
    private FrmPermissaoLista() {
        initComponents();
        this.funcionarioController = FuncionarioController.getInstance();
        configuraTela();
    }

    /**
     * Método responsável por retornar uma instância da própria classe
     *
     * @return retorna uma instância da própria classe
     */
    public static FrmPermissaoLista getInstance() {
        if (frmPermissaoLista == null) {
            frmPermissaoLista = new FrmPermissaoLista();
        }

        frmPermissaoLista.setSize(frmPermissaoLista.getPreferredSize());
        frmPermissaoLista.setLocationRelativeTo(null);
        frmPermissaoLista.limparTela();
        return frmPermissaoLista;
    }

    /**
     * Método responsável por limpar todos os campos
     */
    public void limparTela() {
        txtDescricao.setText("");
        cmbStatus.setSelectedItem("Ativo");
        permissaoTableModel.removeAll();
    }

    /**
     * Método responsável por configurar algumas propriedades da tela
     */
    public void configuraTela() {
        this.getContentPane().setBackground(Color.WHITE);
        this.setIconImage(Imagens.iconeSistema);

        permissaoTableModel = new PermissaoTableModel();
        tblPermissao.setModel(permissaoTableModel);
        permissaoTableModel.configurarTableModelCliente(tblPermissao);

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
                btnRemover1ActionPerformed(null);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0), "liberarBloquear");
        this.getRootPane().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMap);
        this.getRootPane().getActionMap().put("liberarBloquear", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent arg0) {
                btnLiberarBloquearActionPerformed(null);
            }
        });
    }

    /**
     * Método responsável por atualizar a tabela de permissões
     */
    public void atualizarTabela() {
        try {
            permissaoTableModel.removeAll();
            permissaoTableModel.addAll(funcionarioController.getPermissaoController().pesquisarPermissao(txtDescricao.getText(), cmbStatus.getSelectedItem().toString()));

            if (permissaoTableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Nenhuma Permissão Encontrada", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Mensagem de Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método responsável por editar uma permissão da tabela de permissões
     */
    public void editar() {
        if (tblPermissao.getSelectedRow() == -1) {
            Uteis.mensagemAviso(this, "Selecione uma linha da tabela", "Aviso");
            return;
        }

        if (!funcionarioController.possuiPermissao("Editar Permissão")) {
            Uteis.mensagemAviso(this, "O usuário não possui a permissão 'Editar Permissão'", "Aviso");
            return;
        }

        funcionarioController.getPermissaoController().frmPermissaoIncAltSetVisible(permissaoTableModel.getPermissao(tblPermissao.getSelectedRow()), rootPaneCheckingEnabled);
    }

    /**
     * Método responsável por setar as propriedades da tela
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPermissao = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtDescricao = new javax.swing.JTextField();
        cmbStatus = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        btnSair = new javax.swing.JButton();
        btnLiberarBloquear = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnInserir = new javax.swing.JButton();
        btnAtualizar = new javax.swing.JButton();
        btnRemover1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("PsiqueSis - Permissão - Lista");
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Permissões", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 18))); // NOI18N
        jPanel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        tblPermissao.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        tblPermissao.setModel(new javax.swing.table.DefaultTableModel(
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
        tblPermissao.addHierarchyBoundsListener(new java.awt.event.HierarchyBoundsListener() {
            public void ancestorMoved(java.awt.event.HierarchyEvent evt) {
            }
            public void ancestorResized(java.awt.event.HierarchyEvent evt) {
                tblPermissaoAncestorResized(evt);
            }
        });
        tblPermissao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPermissaoMouseClicked(evt);
            }
        });
        tblPermissao.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                tblPermissaoComponentResized(evt);
            }
        });
        jScrollPane1.setViewportView(tblPermissao);

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
        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Ativo", "Inativo" }));
        cmbStatus.setSelectedIndex(1);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("Status");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(493, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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

        btnLiberarBloquear.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnLiberarBloquear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/liberarBloquearIcone.png"))); // NOI18N
        btnLiberarBloquear.setText("Liberar/Bloquear");
        btnLiberarBloquear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLiberarBloquearActionPerformed(evt);
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

        btnRemover1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnRemover1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/iconeRemover.png"))); // NOI18N
        btnRemover1.setText("Remover");
        btnRemover1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemover1ActionPerformed(evt);
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
                        .addComponent(btnRemover1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLiberarBloquear)
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
                    .addComponent(btnRemover1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnInserir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLiberarBloquear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAtualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void txtDescricaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescricaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescricaoActionPerformed

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarActionPerformed
        atualizarTabela();
    }//GEN-LAST:event_btnAtualizarActionPerformed

    private void btnInserirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInserirActionPerformed
        if (!funcionarioController.possuiPermissao("Inserir Permissão")) {
            Uteis.mensagemAviso(this, "O usuário não possui a permissão 'Inserir Permissão'", "Aviso");
            return;
        }

        funcionarioController.getPermissaoController().frmPermissaoIncAltSetVisible(null, true);
    }//GEN-LAST:event_btnInserirActionPerformed

    private void tblPermissaoComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tblPermissaoComponentResized

    }//GEN-LAST:event_tblPermissaoComponentResized

    private void tblPermissaoAncestorResized(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_tblPermissaoAncestorResized

    }//GEN-LAST:event_tblPermissaoAncestorResized

    private void btnLiberarBloquearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLiberarBloquearActionPerformed
        try {
            if (tblPermissao.getSelectedRow() == -1) {
                Uteis.mensagemAviso(this, "Selecione uma linha da tabela", "Aviso");
                return;
            }

            /*if (!funcionarioController.possuiPermissao("Liberar e Bloquear Permissão Usuário")) {
                Uteis.mensagemAviso(this, "O usuário não possui a permissão 'Liberar e Bloquear Permissão Usuário'", "Aviso");
                return;
            }*/
            funcionarioController.getPermissaoController().frmUsuarioPermissoesSetVisible(permissaoTableModel.getPermissao(tblPermissao.getSelectedRow()), true);
        } catch (Exception ex) {
            Uteis.mensagemErro(this, ex.getMessage(), "Erro");
        }
    }//GEN-LAST:event_btnLiberarBloquearActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        editar();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed

    }//GEN-LAST:event_formKeyPressed

    private void tblPermissaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPermissaoMouseClicked
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            evt.consume();
            editar();
        }
    }//GEN-LAST:event_tblPermissaoMouseClicked

    private void btnRemover1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemover1ActionPerformed
        try {
            if (tblPermissao.getSelectedRow() == -1) {
                Uteis.mensagemAviso(this, "Selecione uma linha da tabela", "Aviso");
                return;
            }

            if (!funcionarioController.possuiPermissao("Remover Permissão")) {
                Uteis.mensagemAviso(this, "O usuário não possui a permissão 'Remover Permissão'", "Aviso");
                return;
            }

            if (Uteis.mensagemCondicional(this, "Deseja realmente remover a permissão de ID '" + permissaoTableModel.getValueAt(tblPermissao.getSelectedRow(), "ID") + "'?", "Confirmação") == 1) {
                return;
            }

            Permissao p = permissaoTableModel.getPermissao(tblPermissao.getSelectedRow());
            p.setStatus("Inativo");
            funcionarioController.getPermissaoController().editarPermissao(p);
            Uteis.mensagemAviso(this, "Permissão removida com sucesso", "Aviso");
            funcionarioController.inserirHistorico(new Historico("Removeu a permissão de código '" + p.getId() + "'", new Date(), funcionarioController.getFuncionarioLogado()));
            atualizarTabela();
        } catch (Exception ex) {
            Uteis.mensagemErro(this, ex.getMessage(), "Erro");
        }
    }//GEN-LAST:event_btnRemover1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnInserir;
    private javax.swing.JButton btnLiberarBloquear;
    private javax.swing.JButton btnRemover1;
    private javax.swing.JButton btnSair;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPermissao;
    private javax.swing.JTextField txtDescricao;
    // End of variables declaration//GEN-END:variables
}
