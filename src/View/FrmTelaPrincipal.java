/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.FuncionarioController;
import Geral.Imagens;
import Geral.Sons;
import Geral.Uteis;
import Model.Funcionario;
import Model.Historico;
import Model.Maquina;
import Model.Pessoa;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

/**
 * JFrame relacionado a tela principal do sistema
 *
 * @author Gabriel Almeida Borges
 */
public class FrmTelaPrincipal extends javax.swing.JFrame {

    /**
     * Objeto que controla os avisos pelo sistema operacional
     */
    SystemTray tray = SystemTray.getSystemTray();
    /**
     * Objeto que armazena o icone para o objeto tray
     */
    TrayIcon trayIcon = new TrayIcon(Imagens.iconeSistema, "PsiqueSis");

    /**
     * Controller do tipo funcionário
     */
    private FuncionarioController funcionarioController;
    /**
     * Instância da própria classe em questão, por conta do padrão de projeto
     * Sington
     */
    private static FrmTelaPrincipal FrmTelaPrincipal;

    /**
     * Construtor da classe
     */
    private FrmTelaPrincipal() {
        initComponents();

        try {
            tray.add(trayIcon);
            trayIcon.setImageAutoSize(true);

            trayIcon.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    FrmTelaPrincipal frm = FrmTelaPrincipal.getInstance();
                    frm.toFront();
                    frm.requestFocusInWindow();
                    frm.requestFocus();
                    frm.setExtendedState(JFrame.NORMAL);
                    getInstance().repaint();
                }
            });
        } catch (AWTException ex) {
        }

        funcionarioController = FuncionarioController.getInstance();
        this.getContentPane().setBackground(Color.white);
        this.setIconImage(Imagens.iconeSistema);

        InputMap inputMap = this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "sair");
        this.getRootPane().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMap);
        this.getRootPane().getActionMap().put("sair", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent arg0) {
                jMenuItem3ActionPerformed(null);
            }
        });

        soqueteServer();
        rodapeTelaUsuario();

        lNovosAgendamentos.setModel(new DefaultListModel());
    }

    /**
     * Método responsável por retornar uma instância da própria classe
     *
     * @return retorna uma instância da própria classe
     */
    public static FrmTelaPrincipal getInstance() {
        if (FrmTelaPrincipal == null) {
            FrmTelaPrincipal = new FrmTelaPrincipal();
        }

        FrmTelaPrincipal.setSize(FrmTelaPrincipal.getPreferredSize());
        FrmTelaPrincipal.setLocationRelativeTo(null);

        return FrmTelaPrincipal;
    }

    /**
     * Método responsável por gerenciar as informações de rodapé da tela
     */
    public void rodapeTelaUsuario() {
        new Thread(() -> {
            while (true) {
                try {
                    Funcionario f = FuncionarioController.getFuncionarioLogado();
                    String conteudoLabelDeBaixo = "PsiqueSis 1.00 - Nome Usuário: " + f.getNome() + " - Nome Login: " + f.getNomeLogin() + " - Telefone 1: " + Uteis.setaMascara(f.getTelefone1(), "(##) ##### - ####") + " - Data Atual: " + Uteis.converteData("dd/MM/yyyy HH:mm:ss", new Date());
                    this.lblFuncionarioLogado.setText(conteudoLabelDeBaixo);
                    Thread.sleep(1000);
                } catch (Exception ex) {
                }
            }
        }).start();
    }

    /**
     * Método responsável por subir o aviso pelo sistema operacional de novo
     * agendamento
     *
     * @param mensagem mensagem que subirá na tela
     */
    private void subirTray(String mensagem) {
        if (SystemTray.isSupported()) {
            trayIcon.displayMessage("Um novo agendamento foi inserido", "PsiqueSis", TrayIcon.MessageType.INFO);

            DefaultListModel model = (DefaultListModel) lNovosAgendamentos.getModel();
            model.addElement(mensagem);
            lNovosAgendamentos.ensureIndexIsVisible(model.getSize() - 1);
            try {

                if (!chkMenuItemSemSom.isSelected()) {

                    if (chkMenuItemLightSong.isSelected()) {
                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Sons.lightSongAgendamento);
                        Clip clip = AudioSystem.getClip();
                        clip.open(audioInputStream);
                        clip.start();

                        clip.loop(2);
                    } else if (chkMenuItemAlarmSong.isSelected()) {
                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Sons.alarmSongAgendamento);
                        Clip clip = AudioSystem.getClip();
                        clip.open(audioInputStream);
                        clip.start();
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * Método responsável por iniciar o serviço de soquete, no qual o mesmo
     * receberá as mensagens de novos agendamentos criados
     */
    private void soqueteServer() {
        new Thread(() -> {
            while (true) {
                try {
                    String mensagemSite = "";

                    ServerSocket serverSocket = new ServerSocket(Integer.parseInt(funcionarioController.getFuncionarioLogado().getMaquina().getPorta()));
                    Socket connectionSocket = serverSocket.accept();

                    BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

                    mensagemSite = inFromClient.readLine();

                    String mensagemParticionada[] = mensagemSite.split("/");  //A|NOVO|IDPESSOACRIADOR|IDFUNCIONARIODESTINO|MOTIVO

                    if (mensagemParticionada.length != 5) {
                        continue;
                    }

                    if (!mensagemParticionada[0].equals("A") || !mensagemParticionada[1].equals("NOVO")) {
                        continue;
                    }

                    if (!funcionarioController.possuiPermissao("Visualizar Todos os Avisos Agendamentos") && !mensagemParticionada[3].equals(String.valueOf(FuncionarioController.getFuncionarioLogado().getIdPessoa()))) {
                        continue;
                    }

                    Pessoa pCriador = funcionarioController.pesquisaPessoa(mensagemParticionada[2]);
                    Pessoa pDestino = funcionarioController.pesquisaPessoa(mensagemParticionada[3]);

                    if (pCriador == null || pDestino == null) {
                        continue;
                    }

                    String mensagemTray = "Inserido na data: " + Uteis.converteData("dd/MM/yyyy HH:mm:ss", new Date()) + " - Criador: " + pCriador.getNome() + " - Destino: " + pDestino.getNome();

                    subirTray(mensagemTray);

                } catch (Exception ex) {
                }
            }
        }).start();
    }

    /**
     * Método responsável por setar as propriedades da tela
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jPopupMenuLimparAgendamentos = new javax.swing.JPopupMenu();
        jMenuItemApagarNovosAgendamentos = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        lblFuncionarioLogado = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lNovosAgendamentos = new javax.swing.JList<>();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenu10 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenu8 = new javax.swing.JMenu();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenu9 = new javax.swing.JMenu();
        jMenuItem19 = new javax.swing.JMenuItem();
        jMenuItem20 = new javax.swing.JMenuItem();
        jMenu12 = new javax.swing.JMenu();
        jMenuItem21 = new javax.swing.JMenuItem();
        jMenu11 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        chkMenuItemLightSong = new javax.swing.JCheckBoxMenuItem();
        chkMenuItemAlarmSong = new javax.swing.JCheckBoxMenuItem();
        chkMenuItemSemSom = new javax.swing.JCheckBoxMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        jLabel2.setText("jLabel2");

        jMenuItemApagarNovosAgendamentos.setText("Limpar Novos Agendamentos da Tela");
        jMenuItemApagarNovosAgendamentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemApagarNovosAgendamentosActionPerformed(evt);
            }
        });
        jPopupMenuLimparAgendamentos.add(jMenuItemApagarNovosAgendamentos);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("PsiqueSis - Tela Principal");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        lblFuncionarioLogado.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblFuncionarioLogado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFuncionarioLogado.setText("Informações de Rodapé");

        jLabel1.setBackground(new java.awt.Color(102, 204, 255));
        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("PsiqueSis");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Novos Agendamentos Inseridos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 18))); // NOI18N
        jPanel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        lNovosAgendamentos.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        lNovosAgendamentos.setToolTipText("Clique com o botão direito para remover os novos agendamentos");
        lNovosAgendamentos.setComponentPopupMenu(jPopupMenuLimparAgendamentos);
        jScrollPane1.setViewportView(lNovosAgendamentos);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 839, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                .addContainerGap())
        );

        jMenuBar1.setBackground(new java.awt.Color(255, 255, 255));

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/funcionarioIcone.png"))); // NOI18N
        jMenu1.setText("Funcionário");

        jMenuItem1.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/funcionarioIcone.png"))); // NOI18N
        jMenuItem1.setText("Inserir Funcionário");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem4.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/funcionarioIcone.png"))); // NOI18N
        jMenuItem4.setText("Gerenciar Funcionários");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuItem2.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/IconeHistoricoFuncionario.png"))); // NOI18N
        jMenuItem2.setText("Pesquisar Histórico");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/IconePaciente.png"))); // NOI18N
        jMenu7.setText("Paciente");

        jMenuItem5.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/IconePaciente.png"))); // NOI18N
        jMenuItem5.setText("Inserir Paciente");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem5);

        jMenuItem6.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/IconePaciente.png"))); // NOI18N
        jMenuItem6.setText("Gerenciar Pacientes");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem6);

        jMenuBar1.add(jMenu7);

        jMenu10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/IconeResponsavel.png"))); // NOI18N
        jMenu10.setText("Responsável");

        jMenuItem7.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/IconeResponsavel.png"))); // NOI18N
        jMenuItem7.setText("Inserir Responsável");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem7);

        jMenuItem8.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItem8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/IconeResponsavel.png"))); // NOI18N
        jMenuItem8.setText("Gerenciar Responsáveis");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem8);

        jMenuBar1.add(jMenu10);

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/IconeAgendamento.png"))); // NOI18N
        jMenu3.setText("Agendamento");

        jMenuItem9.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItem9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/IconeAgendamento.png"))); // NOI18N
        jMenuItem9.setText("Inserir Agendamento");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem9);

        jMenuItem10.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItem10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/IconeAgendamento.png"))); // NOI18N
        jMenuItem10.setText("Gerenciar Agendamentos");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem10);

        jMenuBar1.add(jMenu3);

        jMenu6.setBackground(new java.awt.Color(255, 255, 255));
        jMenu6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/IconeOrcamento.png"))); // NOI18N
        jMenu6.setText("Orçamento");

        jMenuItem11.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItem11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/IconeOrcamento.png"))); // NOI18N
        jMenuItem11.setText("Inserir Orçamento");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem11);

        jMenuItem12.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItem12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/IconeOrcamento.png"))); // NOI18N
        jMenuItem12.setText("Gerenciar Orçamentos");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem12);

        jMenuBar1.add(jMenu6);

        jMenu4.setBackground(new java.awt.Color(255, 255, 255));
        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/IconeAtividade.png"))); // NOI18N
        jMenu4.setText("Atividade");

        jMenuItem13.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItem13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/IconeAtividade.png"))); // NOI18N
        jMenuItem13.setText("Inserir Atividade");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem13);

        jMenuItem14.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItem14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/IconeAtividade.png"))); // NOI18N
        jMenuItem14.setText("Gerenciar Atividades");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem14);

        jMenuBar1.add(jMenu4);

        jMenu5.setBackground(new java.awt.Color(255, 255, 255));
        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/IconeLocalizacao.png"))); // NOI18N
        jMenu5.setText("Localização");

        jMenuItem15.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItem15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/IconeLocalizacao.png"))); // NOI18N
        jMenuItem15.setText("Inserir Localização");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem15);

        jMenuItem16.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItem16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/IconeLocalizacao.png"))); // NOI18N
        jMenuItem16.setText("Gerenciar Localizações");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem16);

        jMenuBar1.add(jMenu5);

        jMenu8.setBackground(new java.awt.Color(255, 255, 255));
        jMenu8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/IconePermissao.png"))); // NOI18N
        jMenu8.setText("Permissão");

        jMenuItem17.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItem17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/IconePermissao.png"))); // NOI18N
        jMenuItem17.setText("Inserir Permissão");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem17);

        jMenuItem18.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItem18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/IconePermissao.png"))); // NOI18N
        jMenuItem18.setText("Gerenciar Permissões");
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem18);

        jMenuBar1.add(jMenu8);

        jMenu9.setBackground(new java.awt.Color(255, 255, 255));
        jMenu9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/IconePlano.png"))); // NOI18N
        jMenu9.setText("Plano");

        jMenuItem19.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItem19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/IconePlano.png"))); // NOI18N
        jMenuItem19.setText("Inserir Plano");
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem19);

        jMenuItem20.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItem20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/IconePlano.png"))); // NOI18N
        jMenuItem20.setText("Gerenciar Planos");
        jMenuItem20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem20ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem20);

        jMenuBar1.add(jMenu9);

        jMenu12.setBackground(new java.awt.Color(255, 255, 255));
        jMenu12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/IconeMaquinas.png"))); // NOI18N
        jMenu12.setText("Máquina");

        jMenuItem21.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItem21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/IconeMaquinas.png"))); // NOI18N
        jMenuItem21.setText("Gerenciar Máquinas");
        jMenuItem21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem21ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem21);

        jMenuBar1.add(jMenu12);

        jMenu11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/IconeEngrenagem.png"))); // NOI18N
        jMenu11.setText("Geral");

        jMenu2.setBackground(new java.awt.Color(255, 255, 255));
        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/IconeSom.png"))); // NOI18N
        jMenu2.setText("Sons Agendamento");
        jMenu2.setOpaque(true);

        chkMenuItemLightSong.setBackground(new java.awt.Color(255, 255, 255));
        chkMenuItemLightSong.setSelected(true);
        chkMenuItemLightSong.setText("Light Song");
        chkMenuItemLightSong.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkMenuItemLightSongStateChanged(evt);
            }
        });
        jMenu2.add(chkMenuItemLightSong);

        chkMenuItemAlarmSong.setBackground(new java.awt.Color(255, 255, 255));
        chkMenuItemAlarmSong.setText("Alarm Song");
        chkMenuItemAlarmSong.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkMenuItemAlarmSongStateChanged(evt);
            }
        });
        jMenu2.add(chkMenuItemAlarmSong);

        chkMenuItemSemSom.setBackground(new java.awt.Color(255, 255, 255));
        chkMenuItemSemSom.setText("Sem Som");
        chkMenuItemSemSom.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkMenuItemSemSomStateChanged(evt);
            }
        });
        jMenu2.add(chkMenuItemSemSom);

        jMenu11.add(jMenu2);

        jMenuItem3.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/IconeSair1.png"))); // NOI18N
        jMenuItem3.setText("Sair");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem3);

        jMenuBar1.add(jMenu11);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(lblFuncionarioLogado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator3)
            .addComponent(jSeparator2)
            .addComponent(jSeparator4)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblFuncionarioLogado, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        if (!funcionarioController.possuiPermissao("Inserir Funcionário")) {
            Uteis.mensagemAviso(this, "O usuário não possui a permissão 'Inserir Funcionário'", "Aviso");
        } else {
            funcionarioController.frmFuncionarioIncAltSetVisible(null, true);
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        if (!funcionarioController.possuiPermissao("Gerenciar Funcionário")) {
            Uteis.mensagemAviso(this, "O usuário não possui a permissão 'Gerenciar Funcionário'", "Aviso");
        } else {
            funcionarioController.frmFuncionarioListaSetVisible(true);
        }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        if (!funcionarioController.possuiPermissao("Inserir Paciente")) {
            Uteis.mensagemAviso(this, "O usuário não possui a permissão 'Inserir Paciente'", "Aviso");
        } else {
            funcionarioController.getPacienteController().frmPacienteIncAltSetVisible(null, true);
        }
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        if (!funcionarioController.possuiPermissao("Gerenciar Paciente")) {
            Uteis.mensagemAviso(this, "O usuário não possui a permissão 'Gerenciar Paciente'", "Aviso");
        } else {
            funcionarioController.getPacienteController().FrmPacienteListaSetVisible(true);
        }
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        if (!funcionarioController.possuiPermissao("Inserir Responsável")) {
            Uteis.mensagemAviso(this, "O usuário não possui a permissão 'Inserir Responsável'", "Aviso");
        } else {
            funcionarioController.getResponsavelController().frmResponsavelIncAltSetVisible(null, true);
        }
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        if (!funcionarioController.possuiPermissao("Inserir Responsável")) {
            Uteis.mensagemAviso(this, "O usuário não possui a permissão 'Gerenciar Permissão'", "Aviso");
        } else {
            funcionarioController.getResponsavelController().frmResponsavelListaSetVisible(true);
        }
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        if (!funcionarioController.possuiPermissao("Inserir Agendamento")) {
            Uteis.mensagemAviso(this, "O usuário não possui a permissão 'Inserir Agendamento'", "Aviso");
        } else {
            funcionarioController.getAgendamentoController().frmAgendamentoIncAlt(null, true);
        }
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        if (!funcionarioController.possuiPermissao("Gerenciar Agendamento")) {
            Uteis.mensagemAviso(this, "O usuário não possui a permissão 'Gerenciar Agendamento'", "Aviso");
        } else {
            funcionarioController.getAgendamentoController().frmAgendamentoLista(true);
        }
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        if (!funcionarioController.possuiPermissao("Inserir Agendamento")) {
            Uteis.mensagemAviso(this, "O usuário não possui a permissão 'Inserir Agendamento'", "Aviso");
        } else {
            funcionarioController.getOrcamentoController().frmOrcamentoIncAltSetVisible(null, true);
        }
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        if (!funcionarioController.possuiPermissao("Gerenciar Orçamento")) {
            Uteis.mensagemAviso(this, "O usuário não possui a permissão 'Gerenciar Orçamento'", "Aviso");
        } else {
            funcionarioController.getOrcamentoController().frmOrcamentoListaSetVisible(true);
        }
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        if (!funcionarioController.possuiPermissao("Inserir Atividade")) {
            Uteis.mensagemAviso(this, "O usuário não possui a permissão 'Inserir Atividade'", "Aviso");
        } else {
            funcionarioController.getAtividadeController().frmAtividadeIncAltSetVisible(null, true);
        }
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        if (!funcionarioController.possuiPermissao("Gerenciar Atividade")) {
            Uteis.mensagemAviso(this, "O usuário não possui a permissão 'Gerenciar Atividade'", "Aviso");
        } else {
            funcionarioController.getAtividadeController().frmAtividadeListaSetVisible(true);
        }
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        if (!funcionarioController.possuiPermissao("Inserir Localização")) {
            Uteis.mensagemAviso(this, "O usuário não possui a permissão 'Inserir Localização'", "Aviso");
        } else {
            funcionarioController.getLocalizacaoController().frmLocalizacaoIncAltSetVisible(null, true);
        }
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        if (!funcionarioController.possuiPermissao("Gerenciar Localização")) {
            Uteis.mensagemAviso(this, "O usuário não possui a permissão 'Gerenciar Localização'", "Aviso");
        } else {
            funcionarioController.getLocalizacaoController().frmLocalizacaoListaSetVisible(true);
        }
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
        /*      if (!funcionarioController.possuiPermissao("Inserir Permissão")) {
            Uteis.mensagemAviso(this, "O usuário não possui a permissão 'Inserir Permissão'", "Aviso");
        } else {*/
        funcionarioController.getPermissaoController().frmPermissaoIncAltSetVisible(null, true);
        // }
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed
        /*  if (!funcionarioController.possuiPermissao("Gerenciar Permissão")) {
            Uteis.mensagemAviso(this, "O usuário não possui a permissão 'Gerenciar Permissão'", "Aviso");
        } else {*/
        funcionarioController.getPermissaoController().frmPermissaoListaSetVisible(true);
        //   }
    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
        if (!funcionarioController.possuiPermissao("Inserir Plano")) {
            Uteis.mensagemAviso(this, "O usuário não possui a permissão 'Inserir Plano'", "Aviso");
        } else {
            funcionarioController.getPlanoController().frmPlanoIncAltSetVisible(null, true);
        }
    }//GEN-LAST:event_jMenuItem19ActionPerformed

    private void jMenuItem20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem20ActionPerformed
        if (!funcionarioController.possuiPermissao("Gerenciar Plano")) {
            Uteis.mensagemAviso(this, "O usuário não possui a permissão 'Gerenciar Plano'", "Aviso");
        } else {
            funcionarioController.getPlanoController().FrmPlanoListaSetVisible(true);
        }
    }//GEN-LAST:event_jMenuItem20ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        if (!funcionarioController.possuiPermissao("Pesquisar Histórico")) {
            Uteis.mensagemAviso(this, "O usuário não possui a permissão 'Pesquisar Histórico'", "Aviso");
        } else {
            funcionarioController.frmHistoricoListaSetVisible(true);
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            if (Uteis.mensagemCondicional(this, "Deseja realmente sair?", "Confirmação") == 1) {
                return;
            }
            Maquina m = funcionarioController.getFuncionarioLogado().getMaquina();
            m.setStatus("I");
            funcionarioController.getMaquinaController().editarMaquina(m);
            funcionarioController.inserirHistorico(new Historico("Realizou logout", new Date(), funcionarioController.getFuncionarioLogado()));
            System.exit(0);
        } catch (Exception ex) {
            Logger.getLogger(FrmTelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosing

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        formWindowClosing(null);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void chkMenuItemSemSomStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkMenuItemSemSomStateChanged
        if (chkMenuItemSemSom.isSelected()) {
            chkMenuItemAlarmSong.setSelected(false);
            chkMenuItemLightSong.setSelected(false);
        }

        if (!chkMenuItemLightSong.isSelected() && !chkMenuItemAlarmSong.isSelected() && !chkMenuItemSemSom.isSelected()) {
            chkMenuItemSemSom.setSelected(true);
        }
    }//GEN-LAST:event_chkMenuItemSemSomStateChanged

    private void chkMenuItemAlarmSongStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkMenuItemAlarmSongStateChanged
        if (chkMenuItemAlarmSong.isSelected()) {
            chkMenuItemLightSong.setSelected(false);
            chkMenuItemSemSom.setSelected(false);
        }

        if (!chkMenuItemLightSong.isSelected() && !chkMenuItemAlarmSong.isSelected() && !chkMenuItemSemSom.isSelected()) {
            chkMenuItemSemSom.setSelected(true);
        }
    }//GEN-LAST:event_chkMenuItemAlarmSongStateChanged

    private void chkMenuItemLightSongStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkMenuItemLightSongStateChanged
        if (chkMenuItemLightSong.isSelected()) {
            chkMenuItemAlarmSong.setSelected(false);
            chkMenuItemSemSom.setSelected(false);
        }

        if (!chkMenuItemLightSong.isSelected() && !chkMenuItemAlarmSong.isSelected() && !chkMenuItemSemSom.isSelected()) {
            chkMenuItemSemSom.setSelected(true);
        }
    }//GEN-LAST:event_chkMenuItemLightSongStateChanged

    private void jMenuItem21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem21ActionPerformed
        if (!funcionarioController.possuiPermissao("Gerenciar Máquina")) {
            Uteis.mensagemAviso(this, "O usuário não possui a permissão 'Gerenciar Máquina'", "Aviso");
        } else {
            funcionarioController.getMaquinaController().frmMaquinaListaSetVisible(true);
        }
    }//GEN-LAST:event_jMenuItem21ActionPerformed

    private void jMenuItemApagarNovosAgendamentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemApagarNovosAgendamentosActionPerformed
        if (Uteis.mensagemCondicional(this, "Deseja limpar a lista de novos agendamentos?", "Confirmação") == 0) {
            DefaultListModel model = (DefaultListModel) lNovosAgendamentos.getModel();
            model.removeAllElements();
        }
    }//GEN-LAST:event_jMenuItemApagarNovosAgendamentosActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem chkMenuItemAlarmSong;
    private javax.swing.JCheckBoxMenuItem chkMenuItemLightSong;
    private javax.swing.JCheckBoxMenuItem chkMenuItemSemSom;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu10;
    private javax.swing.JMenu jMenu11;
    private javax.swing.JMenu jMenu12;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JMenuItem jMenuItemApagarNovosAgendamentos;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenuLimparAgendamentos;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JList<String> lNovosAgendamentos;
    private javax.swing.JLabel lblFuncionarioLogado;
    // End of variables declaration//GEN-END:variables
}
