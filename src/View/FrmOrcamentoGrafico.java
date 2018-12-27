package View;

import Controller.FuncionarioController;
import Geral.Imagens;
import Geral.Uteis;
import Model.Orcamento;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.TextAnchor;

/**
 * JFrame relacionada a aos gráficos dos orçamentos
 *
 * @author Antonio Lucas Christofoletti
 */
public class FrmOrcamentoGrafico extends javax.swing.JFrame {

    /**
     * Instância da própria classe em questão, por conta do padrão de projeto
     * Sington
     */
    private static FrmOrcamentoGrafico frmOrcamentoGraficoRelacaoRegimeCaixaCompetencia;

    /**
     * Controller do tipo funcionário
     */
    private FuncionarioController funcionarioController;

    /**
     * Construtor da classe
     */
    private FrmOrcamentoGrafico() {
        initComponents();
        configuraTela();
        funcionarioController = FuncionarioController.getFuncionarioController();
    }

    /**
     * Método responsável por retornar uma instância da própria classe
     *
     * @return retorna uma instância da própria classe
     */
    public static FrmOrcamentoGrafico getInstance() {
        if (frmOrcamentoGraficoRelacaoRegimeCaixaCompetencia == null) {
            frmOrcamentoGraficoRelacaoRegimeCaixaCompetencia = new FrmOrcamentoGrafico();
        }

        frmOrcamentoGraficoRelacaoRegimeCaixaCompetencia.setSize(frmOrcamentoGraficoRelacaoRegimeCaixaCompetencia.getPreferredSize());
        frmOrcamentoGraficoRelacaoRegimeCaixaCompetencia.setLocationRelativeTo(null);
        frmOrcamentoGraficoRelacaoRegimeCaixaCompetencia.limparTela();
        return frmOrcamentoGraficoRelacaoRegimeCaixaCompetencia;
    }

    /**
     * Método responsável por limpar todos os campos
     */
    public void limparTela() {
        txtDtInicial.setDate(null);
        txtDataFinal.setDate(null);
        cmbTipoGrafico.setSelectedItem("Relação Orçamento");
        JPanelGrafico.setLayout(new java.awt.BorderLayout());
        JPanelGrafico.removeAll();
    }

    /**
     * Método responsável por gerar o gráfico de colunas no JPanel da tela
     *
     * @param lo orçamento que serão levados em consideração na geração do
     * gráfico
     * @return retorna o gráfico
     */
    private ChartPanel geraGraficoColunas(ArrayList<Orcamento> lo) {
        CategoryDataset dataset1 = funcionarioController.getOrcamentoController().geraBaseDeDadosGraficoColuna(lo);

        // create the chart...
        JFreeChart chart = ChartFactory.createBarChart3D(
                "Orçamento", // chart title
                "", // domain axis label
                "Valores em Reais", // range axis label
                dataset1, // data
                PlotOrientation.VERTICAL,
                true, // include legend
                true,
                false
        );

        chart.setBackgroundPaint(Color.white);
        chart.setBackgroundImageAlpha(0);

        chart.setTitle(new TextTitle("Relação de Orçamento", new Font("Serif", java.awt.Font.BOLD, 18)));

        CategoryPlot plot = chart.getCategoryPlot();

        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setBackgroundPaint(Color.white);
        plot.setShadowGenerator(null);

        CategoryItemRenderer renderer1 = plot.getRenderer();
        renderer1.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer1.setBaseItemLabelsVisible(true);
        ItemLabelPosition position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.TOP_CENTER);
        renderer1.setBasePositiveItemLabelPosition(position);

        BarRenderer.setDefaultShadowsVisible(false);
        ChartUtilities.applyCurrentTheme(chart);
        return new ChartPanel(chart);
    }

    /**
     * Método responsável por gerar o gráfico de linha no JPanel da tela
     *
     * @param lo orçamento que serão levados em consideração na geração do
     * gráfico
     * @return retorna o gráfico
     */
    private ChartPanel geraGraficoLinhas(ArrayList<Orcamento> lo) {

        JFreeChart lineChart = ChartFactory.createLineChart(
                "Relação Saldo Caixa",
                "Mês/Ano", "Saldo em Reais",
                funcionarioController.getOrcamentoController().geraBaseDeDadosGraficoLinhas(lo),
                PlotOrientation.VERTICAL,
                true, true, false);

        CategoryPlot plot = lineChart.getCategoryPlot();
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        DecimalFormat format = new DecimalFormat("#0.##");
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", format));
        renderer.setBaseShapesVisible(true);
        renderer.setBaseItemLabelsVisible(true);
        return new ChartPanel(lineChart);
    }

    /**
     * Método responsável por configurar algumas propriedades da tela
     */
    public void configuraTela() {
        this.getContentPane().setBackground(Color.WHITE);
        this.setIconImage(Imagens.iconeSistema);

        InputMap inputMap = this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "atualizarGrafico");
        this.getRootPane().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMap);
        this.getRootPane().getActionMap().put("atualizarGrafico", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent arg0) {
                btnAtualizarActionPerformed(null);
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
    }

    /**
     * Método responsável por setar as propriedades da tela
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JPanelGrafico = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txtDtInicial = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        txtDataFinal = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        cmbTipoGrafico = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        btnSair = new javax.swing.JButton();
        btnAtualizar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Psiquesis - Orçamento - Gráficos");

        JPanelGrafico.setBackground(new java.awt.Color(255, 255, 255));
        JPanelGrafico.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Gráfico Gerado", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        javax.swing.GroupLayout JPanelGraficoLayout = new javax.swing.GroupLayout(JPanelGrafico);
        JPanelGrafico.setLayout(JPanelGraficoLayout);
        JPanelGraficoLayout.setHorizontalGroup(
            JPanelGraficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        JPanelGraficoLayout.setVerticalGroup(
            JPanelGraficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filtros", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        txtDtInicial.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Dt.Inicial");

        txtDataFinal.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Dt. Final");

        cmbTipoGrafico.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cmbTipoGrafico.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Relação Orçamento", "Relação Saldo" }));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("Tipo Gráfico");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtDtInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(txtDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbTipoGrafico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap(568, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(4, 4, 4)
                        .addComponent(cmbTipoGrafico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDataFinal, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDtInicial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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

        btnAtualizar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnAtualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagem/IconeSair_1.png"))); // NOI18N
        btnAtualizar.setText("Gerar");
        btnAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAtualizar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSair))
                    .addComponent(JPanelGrafico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JPanelGrafico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSair)
                    .addComponent(btnAtualizar))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarActionPerformed
        try {
            ArrayList ol = funcionarioController.getOrcamentoController().pesquisarOrcamento("", "Todos", txtDtInicial.getDate(), txtDataFinal.getDate(), "Todos");

            ChartPanel cp;

            if (cmbTipoGrafico.getSelectedItem().toString().equals("Relação Orçamento")) {
                cp = geraGraficoColunas(ol);
            } else {
                cp = geraGraficoLinhas(ol);
            }

            JPanelGrafico.setLayout(new java.awt.BorderLayout());
            JPanelGrafico.removeAll();
            JPanelGrafico.add(cp, BorderLayout.CENTER);
            JPanelGrafico.validate();
        } catch (Exception ex) {
            Uteis.mensagemErro(this, "Erro ao gerar gráfico", "Erro");
        }
    }//GEN-LAST:event_btnAtualizarActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        if (Uteis.mensagemCondicional(this, "Deseja realmente sair?", "Confirmação") == 0) {
            this.dispose();
        }
    }//GEN-LAST:event_btnSairActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JPanelGrafico;
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnSair;
    private javax.swing.JComboBox<String> cmbTipoGrafico;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private com.toedter.calendar.JDateChooser txtDataFinal;
    private com.toedter.calendar.JDateChooser txtDtInicial;
    // End of variables declaration//GEN-END:variables
}
