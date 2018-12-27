/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.FuncionarioController;
import Geral.Imagens;
import Geral.Uteis;
import Iterator_Pessoa.IteratorPessoa;
import Model.Historico;
import Model.Permissao;
import TableModel.PessoaPermissaoTableModel;
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
 * JFrame relacionada a permissão usuários lista
 *
 * @author Antonio Lucas Christofoletti
 */
public class FrmPermissoesUsuario extends javax.swing.JFrame {

    /**
     * Controller do tipo funcionário
     */
    private FuncionarioController funcionarioController;
    /**
     * TableModel que gerencia as informações da JTable de pessoas permissões
     */
    private PessoaPermissaoTableModel pessoaPermissaoTableModel;
    /**
     * Instância da própria classe em questão, por conta do padrão de projeto
     * Sington
     */
    private static FrmPermissoesUsuario frmUsuarioPermissoes;

    /**
     * Objeto que armazena a permissão que está sendo gerenciado na tela
     */
    private Permissao permissaoGerenciar;

    /**
     * Construtor da classe
     */
    private FrmPermissoesUsuario() {
        initComponents();
        this.funcionarioController = FuncionarioController.getInstance();
        configuraTela();
    }

    /**
     * Método responsável por retornar uma instância da própria classe
     *
     * @param p permissão que será gerenciada na tela
     * @return retorna uma instância da própria classe
     */
    public static FrmPermissoesUsuario getInstance(Permissao p) {
        if (frmUsuarioPermissoes == null) {
            frmUsuarioPermissoes = new FrmPermissoesUsuario();
        }

        frmUsuarioPermissoes.setSize(frmUsuarioPermissoes.getPreferredSize());
        frmUsuarioPermissoes.setLocationRelativeTo(null);
        frmUsuarioPermissoes.permissaoGerenciar = p;
        frmUsuarioPermissoes.limparTela();
        
        return frmUsuarioPermissoes;
    }

    /**
     * Método responsável por limpar todos os campos
     */
    public void limparTela() {
        txtNomeLogin.setText("");
        txtNomeUsuario.setText("");
        cmbPossuiPermissao.setSelectedItem("Todos");
        pessoaPermissaoTableModel.removeAll();
    }

    /**
     * Método responsável por configurar algumas propriedades da tela
     */
    public void configuraTela() {
        this.getContentPane().setBackground(Color.WHITE);
        this.setIconImage(Imagens.iconeSistema);

        pessoaPermissaoTableModel = new PessoaPermissaoTableModel();
        tblPermissoesUsuarios.setModel(pessoaPermissaoTableModel);
        pessoaPermissaoTableModel.configurarTableModelCliente(tblPermissoesUsuarios);

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

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "liberar");
        this.getRootPane().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMap);
        this.getRootPane().getActionMap().put("liberar", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent arg0) {
                btnLiberarActionPerformed(null);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "bloquear");
        this.getRootPane().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMap);
        this.getRootPane().getActionMap().put("bloquear", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent arg0) {
                btnBloquearActionPerformed(null);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0), "selecionarTodos");
        this.getRootPane().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMap);
        this.getRootPane().getActionMap().put("selecionarTodos", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent arg0) {
                btnSelecionarTodosActionPerformed(null);
            }
        });
    }

    /**
     * Método responsável por atualizar a tabela de permissões
     */
    public void atualizarTabela() {
        try {
            pessoaPermissaoTableModel.removeAll();
            pessoaPermissaoTableModel.addAll(funcionarioController.getPermissaoController().pesquisarPessoaPorPermissao(permissaoGerenciar, txtNomeUsuario.getText(), txtNomeLogin.getText(), cmbPossuiPermissao.getSelectedItem().toString()));

            if (pessoaPermissaoTableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Nenhum Usuário Encontrado", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Mensagem de Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método responsável por setar as propriedades da tela
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnSair = new javax.swing.JButton();
        btnLiberar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPermissoesUsuarios = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNomeUsuario = new javax.swing.JTextField();
        cmbPossuiPermissao = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        txtNomeLogin = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnBloquear = new javax.swing.JButton();
        btnAtualizar = new javax.swing.JButton();
        btnSelecionarTodos = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("PsiqueSis - Gerenciar Permissões Usuários - Lista");

        btnSair.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/iconeSair.png"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        btnLiberar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnLiberar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/DesbloquearPermissaoIcone.png"))); // NOI18N
        btnLiberar.setText("Liberar");
        btnLiberar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLiberarActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Usuários", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 18))); // NOI18N
        jPanel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        tblPermissoesUsuarios.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        tblPermissoesUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome Usuário", "Nome Login", "RG", "CPF", "Possui Permissão"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPermissoesUsuarios.addHierarchyBoundsListener(new java.awt.event.HierarchyBoundsListener() {
            public void ancestorMoved(java.awt.event.HierarchyEvent evt) {
            }
            public void ancestorResized(java.awt.event.HierarchyEvent evt) {
                tblPermissoesUsuariosAncestorResized(evt);
            }
        });
        tblPermissoesUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPermissoesUsuariosMouseClicked(evt);
            }
        });
        tblPermissoesUsuarios.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                tblPermissoesUsuariosComponentResized(evt);
            }
        });
        jScrollPane1.setViewportView(tblPermissoesUsuarios);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filtros", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Nome Usuário");

        txtNomeUsuario.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtNomeUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeUsuarioActionPerformed(evt);
            }
        });

        cmbPossuiPermissao.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cmbPossuiPermissao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Possui", "Não Possui" }));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("Possui Permissão");

        txtNomeLogin.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtNomeLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeLoginActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Nome Login");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(txtNomeUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtNomeLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbPossuiPermissao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap(133, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNomeLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbPossuiPermissao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtNomeUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        btnBloquear.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnBloquear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/BloquearPermissaoIcone.png"))); // NOI18N
        btnBloquear.setText("Bloquear");
        btnBloquear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBloquearActionPerformed(evt);
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

        btnSelecionarTodos.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnSelecionarTodos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/iconeEscolher.png"))); // NOI18N
        btnSelecionarTodos.setText("Selecionar Todos");
        btnSelecionarTodos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSelecionarTodosMouseClicked(evt);
            }
        });
        btnSelecionarTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecionarTodosActionPerformed(evt);
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
                        .addComponent(btnLiberar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBloquear)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSelecionarTodos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 242, Short.MAX_VALUE)
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
                    .addComponent(btnLiberar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAtualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBloquear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSelecionarTodos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void btnLiberarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLiberarActionPerformed
        try {
            if (tblPermissoesUsuarios.getSelectedRow() == -1) {
                Uteis.mensagemAviso(this, "Selecione ao menos uma linha da tabela", "Aviso");
                return;
            }

            if (Uteis.mensagemCondicional(this, "Deseja realmente liberar a permissão para o(s) usuário(s) selecionado(s) ?", "Confirmação") == 1) {
                return;
            }

            funcionarioController.getPermissaoController().liberarPermissao(pessoaPermissaoTableModel.retornaLinhasSelecionadas(false), permissaoGerenciar);

            for (IteratorPessoa ip = new IteratorPessoa(pessoaPermissaoTableModel.retornaLinhasSelecionadas(false)); !ip.isDone(); ip.next()) {
                funcionarioController.inserirHistorico(new Historico("Retirou a permissão de descrição '" + permissaoGerenciar.getDescricao() + "' para o usuário de código '" + ip.currentItem().getIdEspecifico() + "'", new Date(), FuncionarioController.getFuncionarioLogado()));
            }

            atualizarTabela();
        } catch (Exception ex) {
            Uteis.mensagemErro(this, ex.getMessage(), "Erro");
        }
        btnSelecionarTodos.setText("Selecionar Todos");
    }//GEN-LAST:event_btnLiberarActionPerformed

    private void tblPermissoesUsuariosAncestorResized(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_tblPermissoesUsuariosAncestorResized

    }//GEN-LAST:event_tblPermissoesUsuariosAncestorResized

    private void tblPermissoesUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPermissoesUsuariosMouseClicked
    }//GEN-LAST:event_tblPermissoesUsuariosMouseClicked

    private void tblPermissoesUsuariosComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tblPermissoesUsuariosComponentResized

    }//GEN-LAST:event_tblPermissoesUsuariosComponentResized

    private void txtNomeUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeUsuarioActionPerformed

    private void btnBloquearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBloquearActionPerformed
        try {
            if (tblPermissoesUsuarios.getSelectedRow() == -1) {
                Uteis.mensagemAviso(this, "Selecione ao menos uma linha da tabela", "Aviso");
                return;
            }

            if (Uteis.mensagemCondicional(this, "Deseja realmente remover a permissão para o(s) usuário(s) selecionado(s) ?", "Confirmação") == 1) {
                return;
            }
            funcionarioController.getPermissaoController().retirarPermissao(pessoaPermissaoTableModel.retornaLinhasSelecionadas(true), permissaoGerenciar);

            for (IteratorPessoa ip = new IteratorPessoa(pessoaPermissaoTableModel.retornaLinhasSelecionadas(true)); !ip.isDone(); ip.next()) {
                funcionarioController.inserirHistorico(new Historico("Retirou a permissão de descrição '" + permissaoGerenciar.getDescricao() + "' para o usuário de código '" + ip.currentItem().getIdEspecifico() + "'", new Date(), FuncionarioController.getFuncionarioLogado()));
            }

            atualizarTabela();
        } catch (Exception ex) {
            Uteis.mensagemErro(this, ex.getMessage(), "Erro");
        }
        btnSelecionarTodos.setText("Selecionar Todos");
    }//GEN-LAST:event_btnBloquearActionPerformed

    private void txtNomeLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeLoginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeLoginActionPerformed

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarActionPerformed
        atualizarTabela();
    }//GEN-LAST:event_btnAtualizarActionPerformed

    private void btnSelecionarTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionarTodosActionPerformed
        if (btnSelecionarTodos.getText().equals("Desselecionar Todos")) {
            btnSelecionarTodos.setText("Selecionar Todos");
            pessoaPermissaoTableModel.desselecionarTodos();
        } else {
            btnSelecionarTodos.setText("Desselecionar Todos");
            pessoaPermissaoTableModel.selecionarTodos();
        }
    }//GEN-LAST:event_btnSelecionarTodosActionPerformed

    private void btnSelecionarTodosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSelecionarTodosMouseClicked

    }//GEN-LAST:event_btnSelecionarTodosMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnBloquear;
    private javax.swing.JButton btnLiberar;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnSelecionarTodos;
    private javax.swing.JComboBox<String> cmbPossuiPermissao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPermissoesUsuarios;
    private javax.swing.JTextField txtNomeLogin;
    private javax.swing.JTextField txtNomeUsuario;
    // End of variables declaration//GEN-END:variables
}
