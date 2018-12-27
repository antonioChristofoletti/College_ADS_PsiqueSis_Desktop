package View;

import Controller.FuncionarioController;
import Geral.Imagens;
import Geral.Uteis;
import Interfaces_Inversao_Controle.IGerarParcelaAutomatico;
import Model.Historico;
import Model.Orcamento;
import Model.Parcela;
import TableModel.ParcelaTableModel;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/**
 * JFrame relacionada a orçamento Inc/Alt
 *
 * @author Antonio Lucas Christofoletti
 */
public class FrmOrcamentoIncAlt extends javax.swing.JFrame implements IGerarParcelaAutomatico {

    /**
     * Controller do tipo funcionário
     */
    private FuncionarioController funcionarioController;
    /**
     * Instância da própria classe em questão, por conta do padrão de projeto
     * Sington
     */
    private static FrmOrcamentoIncAlt frmOrcamentoIncAlt;
    /**
     * Orçamento de edição, caso a tela tenha sido invocada para edição
     */
    private Orcamento orcamentoEdicao;
    /**
     * TableModel que gerencia as informações da JTable de parcela
     */
    private ParcelaTableModel parcelaTableModel;

    /**
     * Construtor da classe
     */
    private FrmOrcamentoIncAlt() {
        initComponents();
        configuraTela();
        this.funcionarioController = FuncionarioController.getInstance();
    }

    /**
     * Método responsável por retornar uma instância da própria classe
     *
     * @param orcamentoEdicao orçamento que poderá ser editado, caso a mesma
     * tenha sido aberta para esse motivo
     * @return retorna uma instância da própria classe
     */
    public static FrmOrcamentoIncAlt getInstance(Orcamento orcamentoEdicao) {
        if (frmOrcamentoIncAlt == null) {
            frmOrcamentoIncAlt = new FrmOrcamentoIncAlt();
        }

        frmOrcamentoIncAlt.orcamentoEdicao = orcamentoEdicao;

        if (frmOrcamentoIncAlt.orcamentoEdicao != null) {
            frmOrcamentoIncAlt.preencheCampos(frmOrcamentoIncAlt.orcamentoEdicao);
        } else {
            frmOrcamentoIncAlt.limparCampos();
        }

        frmOrcamentoIncAlt.setLocationRelativeTo(null);
        return frmOrcamentoIncAlt;
    }

    /**
     * Método responsável por carregar um orçamento para os campos
     *
     * @param o orçamento que será setado na tela
     */
    public void preencheCampos(Orcamento o) {
        txtID.setText(String.valueOf(o.getId()));
        txtDataVencimento.setDate(o.getDataVencimento());
        cmbTipo.setSelectedItem(o.getTipo());
        txtDescricao.setText(o.getDescricao());
        parcelaTableModel.removeAll();
        parcelaTableModel.addAll(o.getParcelas());
        atualizarCamposValores();
        txtResponsavel.setText(o.getFuncionarioResponsavel().getNome());
    }

    /**
     * Método responsável por limpar os campos da tela
     */
    public void limparCampos() {
        txtID.setText("");
        txtDataVencimento.setDate(null);
        cmbTipo.setSelectedIndex(0);
        txtDescricao.setText("");
        parcelaTableModel.removeAll();
        atualizarCamposValores();
        txtResponsavel.setText(FuncionarioController.getFuncionarioLogado().getNome());
    }

    /**
     * Método responsável por configurar algumas propriedades da tela
     */
    public void configuraTela() {
        this.getContentPane().setBackground(Color.WHITE);
        this.setIconImage(Imagens.iconeSistema);

        parcelaTableModel = new ParcelaTableModel();
        tblParcelas.setModel(parcelaTableModel);
        parcelaTableModel.configurarTableModel(tblParcelas);

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
     * Método responsável por atualizar os campos do group control 'Informações
     * Parcela'
     */
    public void atualizarCamposValores() {
        DecimalFormat df = new DecimalFormat("#,###.00");

        Double[] valores = funcionarioController.getOrcamentoController().retornaValorDevendoPagoTotal(parcelaTableModel.getAll());

        String valorPendente = "0", valorPago = "0", valorTotal = "0", parcelasPagas = "0", parcelasNaoPagas = "0";

        if (valores[0] != 0) {
            valorPendente = df.format(valores[0]);
        }

        if (valores[1] != 0) {
            valorPago = df.format(valores[1]);
        }

        if (valores[2] != 0) {
            valorTotal = df.format(valores[2]);
        }

        if (valores[3] != 0) {
            parcelasPagas = df.format(valores[3]);
        }

        if (valores[4] != 0) {
            parcelasNaoPagas = df.format(valores[4]);
        }

        txtValorPendente.setText(valorPendente);
        txtValorPago.setText(valorPago);
        txtValorTotal.setText(valorTotal);
        txtParcelasPagas.setText(parcelasPagas);
        txtparcelasNaoPagas.setText(parcelasNaoPagas);
    }

    /**
     * Método responsável por editar uma parcela da JTable de parcela
     */
    public void editarParcela() {
        if (tblParcelas.getSelectedRow() == -1) {
            Uteis.mensagemAviso(this, "Selecione uma linha da tabela", "Aviso");
            return;
        }

        int linhaSelecionada = tblParcelas.getSelectedRow();

        Parcela p = funcionarioController.getOrcamentoController().frmParcelaIncAltSetVisible(this, true, parcelaTableModel.getParcela(linhaSelecionada), true);

        if (p.getId() == 0) {
            p.setId(-1);
            parcelaTableModel.setValueAt(linhaSelecionada, "ID", -1);
        }

        parcelaTableModel.UpdateObject(p);

        if (p.getId() == -1) {
            p.setId(0);
            parcelaTableModel.setValueAt(linhaSelecionada, "ID", 0);
        }

        atualizarCamposValores();
    }

    /**
     * Método responsável por setar as parcelas pesquisadas, método relacionado
     * com a Interface IGerarParcelas
     *
     */
    @Override
    public void preencheCampos(ArrayList<Parcela> parcelas) {
        parcelaTableModel.addAll(parcelas);
        atualizarCamposValores();
        Uteis.mensagemAviso(this, "Parcelas Inseridas com Sucesso", "Aviso");
    }

    public JComboBox<String> getCmbTipo() {
        return cmbTipo;
    }

    public void setCmbTipo(JComboBox<String> cmbTipo) {
        this.cmbTipo = cmbTipo;
    }

    public JDateChooser getTxtDataVencimento() {
        return txtDataVencimento;
    }

    public void setTxtDataVencimento(JDateChooser txtDataVencimento) {
        this.txtDataVencimento = txtDataVencimento;
    }

    public JTextArea getTxtDescricao() {
        return txtDescricao;
    }

    public void setTxtDescricao(JTextArea txtDescricao) {
        this.txtDescricao = txtDescricao;
    }

    public JTextField getTxtID() {
        return txtID;
    }

    public void setTxtID(JTextField txtID) {
        this.txtID = txtID;
    }

    public JTextField getTxtResponsavel() {
        return txtResponsavel;
    }

    public void setTxtResponsavel(JTextField txtResponsavel) {
        this.txtResponsavel = txtResponsavel;
    }

    /**
     * Método responsável por setar as propriedades da tela
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        btnSair = new javax.swing.JButton();
        btnConfirmar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescricao = new javax.swing.JTextArea();
        txtDataVencimento = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        cmbTipo = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblParcelas = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtValorPago = new javax.swing.JTextField();
        txtValorPendente = new javax.swing.JTextField();
        txtValorTotal = new javax.swing.JTextField();
        txtParcelasPagas = new javax.swing.JTextField();
        txtparcelasNaoPagas = new javax.swing.JTextField();
        btnInserirParcela = new javax.swing.JButton();
        btnEditarParcela = new javax.swing.JButton();
        btnRemoverParcela = new javax.swing.JButton();
        btnGerarParcelaAutomatico = new javax.swing.JButton();
        txtResponsavel = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("PsiqueSis - Orçamento - Inc/Alt");
        setResizable(false);

        btnSair.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/iconeSair.png"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        btnConfirmar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/iconeConfirmar.png"))); // NOI18N
        btnConfirmar.setText("Confirmar");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informações", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 18))); // NOI18N
        jPanel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setText("ID");

        txtID.setEditable(false);
        txtID.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        txtDescricao.setColumns(20);
        txtDescricao.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtDescricao.setRows(5);
        txtDescricao.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Descrição", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14), new java.awt.Color(51, 51, 51))); // NOI18N
        txtDescricao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescricaoKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(txtDescricao);

        txtDataVencimento.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Dt. Vencimento");

        cmbTipo.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cmbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Entrada", "Saída" }));

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Tipo");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Parcelas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 18))); // NOI18N

        tblParcelas.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblParcelas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Dt. Vencimento", "Descrição", "Valor", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblParcelas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblParcelasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblParcelas);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informações Parcelas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("Valor Total");

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText("Valor Pendente");

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText("Valor Pago");

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setText("P. Pagas");

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setText("P. Não Pagas");

        txtValorPago.setEditable(false);
        txtValorPago.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtValorPago.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        txtValorPendente.setEditable(false);
        txtValorPendente.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtValorPendente.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        txtValorTotal.setEditable(false);
        txtValorTotal.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtValorTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        txtParcelasPagas.setEditable(false);
        txtParcelasPagas.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtParcelasPagas.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        txtparcelasNaoPagas.setEditable(false);
        txtparcelasNaoPagas.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtparcelasNaoPagas.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(txtValorPago, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jLabel7))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtValorPendente)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(txtValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(50, 50, 50))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtParcelasPagas)
                                .addGap(6, 6, 6)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                            .addComponent(txtparcelasNaoPagas))))
                .addContainerGap(173, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtValorPago)
                    .addComponent(txtValorPendente)
                    .addComponent(txtValorTotal))
                .addGap(6, 6, 6)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtParcelasPagas)
                    .addComponent(txtparcelasNaoPagas))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnInserirParcela.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnInserirParcela.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/iconeAdicionar.png"))); // NOI18N
        btnInserirParcela.setToolTipText("Inserir Parcela");
        btnInserirParcela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInserirParcelaActionPerformed(evt);
            }
        });

        btnEditarParcela.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnEditarParcela.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/iconeEditar.png"))); // NOI18N
        btnEditarParcela.setToolTipText("Editar Parcela");
        btnEditarParcela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarParcelaActionPerformed(evt);
            }
        });

        btnRemoverParcela.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnRemoverParcela.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/iconeRemover.png"))); // NOI18N
        btnRemoverParcela.setToolTipText("Remover Parcela");
        btnRemoverParcela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverParcelaActionPerformed(evt);
            }
        });

        btnGerarParcelaAutomatico.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnGerarParcelaAutomatico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/iconeReplicar.png"))); // NOI18N
        btnGerarParcelaAutomatico.setToolTipText("Gerar Parcela Automático");
        btnGerarParcelaAutomatico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGerarParcelaAutomaticoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(btnEditarParcela, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(btnInserirParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnRemoverParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnGerarParcelaAutomatico, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnInserirParcela)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditarParcela)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemoverParcela)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGerarParcelaAutomatico)))
                .addContainerGap())
        );

        txtResponsavel.setEditable(false);
        txtResponsavel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("Usuário Responsável");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txtDataVencimento, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtResponsavel)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(jLabel3))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtDataVencimento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtID)))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addGap(7, 7, 7)
                            .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtResponsavel, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnConfirmar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSair)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        if (Uteis.mensagemCondicional(this, "Deseja realmente sair?", "Confirmação") == 0) {
            this.dispose();
        }
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        try {
            Orcamento o = new Orcamento();

            if (!txtID.getText().equals("")) {
                o.setId(Integer.parseInt(txtID.getText()));
                o.setFuncionarioResponsavel(orcamentoEdicao.getFuncionarioResponsavel());
            } else {

                o.setFuncionarioResponsavel(FuncionarioController.getFuncionarioLogado());
            }

            if (txtDataVencimento.getDate() != null) {
                o.setDataVencimento(txtDataVencimento.getDate());
            }

            if (orcamentoEdicao != null && orcamentoEdicao.getStatus().equals("Cancelado")) {
                o.setStatus("Cancelado");
            } else {
                o.setStatus("Normal");
            }

            o.setTipo(cmbTipo.getSelectedItem().toString());
            o.setDescricao(txtDescricao.getText());
            o.setParcelas(parcelaTableModel.getAll());

            funcionarioController.getOrcamentoController().validarAtributos(o);

            if (orcamentoEdicao == null) {
                funcionarioController.getOrcamentoController().inserirOrcamento(o);
                Uteis.mensagemAviso(this, "Orçamento inserido com sucesso", "Aviso");
                funcionarioController.inserirHistorico(new Historico("Inseriu um orçamento", new Date(), funcionarioController.getFuncionarioLogado()));
                this.dispose();
            } else {
                funcionarioController.getOrcamentoController().editarOrcamento(o);
                Uteis.mensagemAviso(this, "Orçamento editado com sucesso", "Aviso");
                funcionarioController.inserirHistorico(new Historico("Editou o orçamento de código '" + o.getId() + "'", new Date(), funcionarioController.getFuncionarioLogado()));
                this.dispose();
            }
        } catch (Exception ex) {
            Uteis.mensagemErro(this, ex.getMessage(), "Mensagem de Erro");
        }
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void txtDescricaoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescricaoKeyTyped
        /*   if (txtDescricao.getText().length() >= 50) {
            getToolkit().beep();
            evt.consume();
        }*/
    }//GEN-LAST:event_txtDescricaoKeyTyped

    private void btnEditarParcelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarParcelaActionPerformed
        editarParcela();
    }//GEN-LAST:event_btnEditarParcelaActionPerformed

    private void btnInserirParcelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInserirParcelaActionPerformed
        Parcela p = funcionarioController.getOrcamentoController().frmParcelaIncAltSetVisible(this, true, null, true);

        if (p != null) {
            parcelaTableModel.addParcela(p);
            atualizarCamposValores();
        }
    }//GEN-LAST:event_btnInserirParcelaActionPerformed

    private void btnRemoverParcelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverParcelaActionPerformed
        if (tblParcelas.getSelectedRow() == -1) {
            Uteis.mensagemAviso(this, "Selecione uma linha da tabela", "Aviso");
            return;
        }

        if (Uteis.mensagemCondicional(null, "Deseja realmente remover a parcela?", "Confirmação") == 1) {
            return;
        }

        parcelaTableModel.removerParcela(parcelaTableModel.getParcela(tblParcelas.getSelectedRow()));
        atualizarCamposValores();
    }//GEN-LAST:event_btnRemoverParcelaActionPerformed

    private void tblParcelasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblParcelasMouseClicked
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            evt.consume();
            editarParcela();
        }
    }//GEN-LAST:event_tblParcelasMouseClicked

    private void btnGerarParcelaAutomaticoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGerarParcelaAutomaticoActionPerformed
        try {
            funcionarioController.getParcelaController().frmParcelaAutomaticoSetVisible(this, true, true, this);
        } catch (Exception ex) {
            Uteis.mensagemErro(this, ex.getMessage(), "Erro");
        }
    }//GEN-LAST:event_btnGerarParcelaAutomaticoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnEditarParcela;
    private javax.swing.JButton btnGerarParcelaAutomatico;
    private javax.swing.JButton btnInserirParcela;
    private javax.swing.JButton btnRemoverParcela;
    private javax.swing.JButton btnSair;
    private javax.swing.JComboBox<String> cmbTipo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tblParcelas;
    private com.toedter.calendar.JDateChooser txtDataVencimento;
    private javax.swing.JTextArea txtDescricao;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtParcelasPagas;
    private javax.swing.JTextField txtResponsavel;
    private javax.swing.JTextField txtValorPago;
    private javax.swing.JTextField txtValorPendente;
    private javax.swing.JTextField txtValorTotal;
    private javax.swing.JTextField txtparcelasNaoPagas;
    // End of variables declaration//GEN-END:variables
}
