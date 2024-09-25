package housebet;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import model.Bet;
import model.Event;
import services.BetService;
import services.UserService;
import utils.Global.CurrentUser;
import viewmodel.BetDetailsViewModel;

public class NewMyBets {

    private JFrame frame;
    List<BetDetailsViewModel> apostasViewModelList = new ArrayList();
    private UserService _userService = new UserService();
    private BetService _betService = new BetService();

    /**
     * Launch the application.
     */
    // public static void main(String[] args) {
    // EventQueue.invokeLater(new Runnable() {
    // public void run() {
    // try {
    // NewMyBets window = new NewMyBets();
    // window.frame.setVisible(true);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }
    // });
    // }

    /**
     * Create the application.
     */
    public NewMyBets() {
        apostasViewModelList = getApostas();
        frame = new JFrame();
        initialize();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(false);
    }

    public void setVisible(Boolean value) {
        frame.setVisible(value);
    }

    private List<BetDetailsViewModel> getApostas() {
        try {
            return _betService.getBetDetailsById(CurrentUser.getId());
        } catch (Exception ex) {
            throw ex;
        }

    }

    private void initialize() {
        frame.getContentPane().setBackground(new Color(0, 0, 0));
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 0 };
        gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 1.0 };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
        frame.getContentPane().setLayout(gridBagLayout);

        // Cabeçalho
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(128, 0, 255));
        GridBagConstraints gbc_headerPanel = new GridBagConstraints();
        gbc_headerPanel.anchor = GridBagConstraints.NORTH;
        gbc_headerPanel.fill = GridBagConstraints.HORIZONTAL;
        gbc_headerPanel.insets = new Insets(0, 0, 5, 0);
        gbc_headerPanel.gridx = 0;
        gbc_headerPanel.gridy = 0;
        frame.getContentPane().add(headerPanel, gbc_headerPanel);

        GridBagLayout gbl_headerPanel = new GridBagLayout();
        gbl_headerPanel.columnWidths = new int[] { 0, 0, 0, 0 };
        gbl_headerPanel.rowHeights = new int[] { 66 };
        gbl_headerPanel.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
        gbl_headerPanel.rowWeights = new double[] { 0.0 };
        headerPanel.setLayout(gbl_headerPanel);

        JLabel lblAdmin = new JLabel("Olá " + CurrentUser.getUsername());
        lblAdmin.setHorizontalAlignment(SwingConstants.LEFT);
        lblAdmin.setForeground(Color.BLACK);
        lblAdmin.setFont(new Font("Tw Cen MT", Font.BOLD, 40));
        GridBagConstraints gbc_lblAdmin = new GridBagConstraints();
        gbc_lblAdmin.anchor = GridBagConstraints.WEST;
        gbc_lblAdmin.insets = new Insets(0, 5, 0, 5);
        gbc_lblAdmin.gridx = 0;
        gbc_lblAdmin.gridy = 0;
        headerPanel.add(lblAdmin, gbc_lblAdmin);

        JLabel lblTitle = new JLabel("CLTBET");
        lblTitle.setForeground(Color.BLACK);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Tw Cen MT", Font.BOLD, 60));
        GridBagConstraints gbc_lblTitle = new GridBagConstraints();
        gbc_lblTitle.insets = new Insets(0, 0, 0, 80);
        gbc_lblTitle.gridx = 1;
        gbc_lblTitle.gridy = 0;
        headerPanel.add(lblTitle, gbc_lblTitle);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                redirectHomeuser();
            }
        });

        btnVoltar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnVoltar.setBackground(Color.LIGHT_GRAY);
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnVoltar.setBackground(Color.WHITE);
            }
        });

        btnVoltar.setOpaque(true);
        btnVoltar.setForeground(Color.BLACK);
        btnVoltar.setBackground(Color.WHITE);
        btnVoltar.setFont(new Font("Tw Cen MT", Font.BOLD, 18));
        btnVoltar.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        btnVoltar.setFocusPainted(false);
        GridBagConstraints gbc_btnVoltar = new GridBagConstraints();
        gbc_btnVoltar.anchor = GridBagConstraints.EAST;
        gbc_btnVoltar.gridx = 2;
        gbc_btnVoltar.gridy = 0;
        headerPanel.add(btnVoltar, gbc_btnVoltar);

        JLabel lblGerenciamentoEventos = new JLabel("Minhas Apostas");
        lblGerenciamentoEventos.setFont(new Font("Tw Cen MT", Font.BOLD, 30));
        lblGerenciamentoEventos.setForeground(new Color(128, 0, 255));
        GridBagConstraints gbc_lblGerenciamentoEventos = new GridBagConstraints();
        gbc_lblGerenciamentoEventos.insets = new Insets(0, 0, 5, 0);
        gbc_lblGerenciamentoEventos.gridx = 0;
        gbc_lblGerenciamentoEventos.gridy = 1;
        frame.getContentPane().add(lblGerenciamentoEventos, gbc_lblGerenciamentoEventos);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setLayout(new GridBagLayout());

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 2;
        frame.getContentPane().add(scrollPane, gbc_scrollPane);

        if (apostasViewModelList != null) {
            int row = 0;

            int maxPanels = apostasViewModelList.size();
            for (int i = 0; i < apostasViewModelList.size(); i++) {
                if (i >= maxPanels && row >= maxPanels) {

                    break;
                }

                BetDetailsViewModel apostaVM = apostasViewModelList.get(i);
                JPanel apostaPanel = new JPanel();
                apostaPanel.setBackground(new Color(128, 0, 255));
                apostaPanel.setBorder(BorderFactory.createTitledBorder(null, "Aposta: " + apostaVM.getBet().getId(),
                        TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                        new Font("Tw Cen MT", Font.BOLD, 20), Color.WHITE));
                apostaPanel.setLayout(new GridBagLayout());

                // Adiciona detalhes da aposta
                addLabelAndValue(apostaPanel, "Valor Apostado:", "R$ " + apostaVM.getBet().getAmountBet(), 0, true);
                addLabelAndValue(apostaPanel, "Possível Retorno:", "R$ " + apostaVM.getBet().getStakeReturn(), 1, true);

                JButton btnFinalizarAposta = new JButton("Finalizar Aposta");
                btnFinalizarAposta.setFont(new Font("Tw Cen MT", Font.BOLD, 18));
                btnFinalizarAposta.setForeground(Color.BLACK);
                btnFinalizarAposta.setBackground(Color.WHITE);
                btnFinalizarAposta.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Mostrar um dialogo de confirmação antes de remover a aposta
                        int resposta = JOptionPane.showConfirmDialog(
                                null,
                                "DEU RED! Finalize sua aposta.",
                                "Confirmação de Finalização",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.WARNING_MESSAGE);

                        if (resposta == JOptionPane.YES_OPTION) {
                            try {

                                _betService.endBet(apostaVM.getBet().getId(), false);

                                JOptionPane.showMessageDialog(null, "Aposta finalizada com sucesso!");

                                apostasViewModelList.remove(apostaVM);

                                mainPanel.remove(apostaPanel);
                                mainPanel.revalidate();
                                mainPanel.repaint();
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, "Ocorreu um erro ao cancelar a aposta");
                            }
                        }
                    }
                });

                JButton btnCreditarAposta = new JButton("Creditar Aposta");
                btnCreditarAposta.setFont(new Font("Tw Cen MT", Font.BOLD, 18));
                btnCreditarAposta.setForeground(Color.BLACK);
                btnCreditarAposta.setBackground(Color.WHITE);
                btnCreditarAposta.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        int resposta = JOptionPane.showConfirmDialog(
                                null,
                                "DEU GREEN! Confirme para creditar os ganhos!",
                                "Creditar aposta",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.PLAIN_MESSAGE);

                        if (resposta == JOptionPane.YES_OPTION) {
                            try {

                                Double lucro = apostaVM.getBet().getStakeReturn();
                                _betService.endBet(apostaVM.getBet().getId(), true);
                                _userService.depositMoneyUserById(CurrentUser.getId(), lucro);
                                CurrentUser.setBalance(CurrentUser.getBalance() + lucro);
                                JOptionPane.showMessageDialog(null, "Ganhos créditados com sucesso!");

                                apostasViewModelList.remove(apostaVM);

                                mainPanel.remove(apostaPanel);
                                mainPanel.revalidate();
                                mainPanel.repaint();
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, "Ocorreu um erro ao creditar a aposta");
                            }
                        }
                    }
                });

                // Botão Cancelar Aposta
                JButton btnCancelarAposta = new JButton("Cancelar Aposta");
                btnCancelarAposta.setFont(new Font("Tw Cen MT", Font.BOLD, 18));
                btnCancelarAposta.setForeground(Color.BLACK);
                btnCancelarAposta.setBackground(Color.WHITE);

                btnCancelarAposta.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        int resposta = JOptionPane.showConfirmDialog(
                                null,
                                "Tem certeza que deseja cancelar esta aposta? O valor estornado será metade do valor apostado!",
                                "Confirmação de Cancelamento",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.INFORMATION_MESSAGE);

                        if (resposta == JOptionPane.YES_OPTION) {
                            try {
                                // Remover a aposta da lista e do banco de dados
                                Double estorno = apostaVM.getBet().getAmountBet() / 2;
                                _betService.deleteBet(apostaVM.getBet().getId());
                                _userService.depositMoneyUserById(CurrentUser.getId(), estorno);
                                CurrentUser.setBalance(CurrentUser.getBalance() + estorno);
                                JOptionPane.showMessageDialog(null, "Aposta cancelada com sucesso!");

                                apostasViewModelList.remove(apostaVM);

                                // Atualizar o painel principal
                                mainPanel.remove(apostaPanel);
                                mainPanel.revalidate();
                                mainPanel.repaint();
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, "Ocorreu um erro ao cancelar a aposta");
                            }
                        }
                    }
                });

                // Adiciona eventos
                List<Event> events = apostaVM.getEvents();
                JPanel eventsPanel = new JPanel();
                eventsPanel.setBackground(new Color(70, 70, 70));
                eventsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Layout para eventos lado a lado
                GridBagConstraints gbc_eventPanel = new GridBagConstraints();
                gbc_eventPanel.fill = GridBagConstraints.BOTH;
                gbc_eventPanel.insets = new Insets(5, 5, 5, 5);
                gbc_eventPanel.gridx = 0;
                gbc_eventPanel.gridy = 3;
                gbc_eventPanel.gridwidth = 2; // Para cobrir toda a largura do painel de aposta
                int contadorGreen = 0;
                int contadorAndamento = 0;
                for (Event event : events) {

                    if (event.getOption().equals(event.getResult())) {
                        contadorGreen++;
                    } else if (event.getResult().equals("Em andamento")) {
                        contadorAndamento++;
                    }
                    JPanel eventPanel = new JPanel();
                    eventPanel.setBackground(new Color(0, 0, 0));
                    eventPanel.setLayout(new GridBagLayout());
                    eventPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                    addLabelAndValue(eventPanel, "Nome do Evento:", event.getName(), 0, false);
                    addLabelAndValue(eventPanel, "Time da Casa:", event.getHomeTeam(), 1, false);
                    addLabelAndValue(eventPanel, "Time de Fora:", event.getAwayTeam(), 2, false);
                    addLabelAndValue(eventPanel, "Resultado:", event.getResult(), 3, false);
                    addLabelAndValue(eventPanel, "Aposta:", event.getOption(), 4, false);

                    eventsPanel.add(eventPanel);
                }

                if (contadorGreen == events.size()) {
                    GridBagConstraints gbc_btnCreditarAposta = new GridBagConstraints();
                    gbc_btnCreditarAposta.gridx = 0;
                    gbc_btnCreditarAposta.gridy = 2;
                    gbc_btnCreditarAposta.gridwidth = 2;
                    gbc_btnCreditarAposta.insets = new Insets(10, 0, 10, 0);
                    gbc_btnCreditarAposta.anchor = GridBagConstraints.CENTER;
                    apostaPanel.add(btnCreditarAposta , gbc_btnCreditarAposta);
                } else {
                    if (contadorAndamento == 0) {
                        GridBagConstraints gbc_btnFinalizarAposta = new GridBagConstraints();
                        gbc_btnFinalizarAposta.gridx = 0;
                        gbc_btnFinalizarAposta.gridy = 2;
                        gbc_btnFinalizarAposta.gridwidth = 2;
                        gbc_btnFinalizarAposta.insets = new Insets(10, 0, 10, 0);
                        gbc_btnFinalizarAposta.anchor = GridBagConstraints.CENTER;
                        apostaPanel.add(btnFinalizarAposta, gbc_btnFinalizarAposta);
                    } else {
                        GridBagConstraints gbc_btnCancelarAposta = new GridBagConstraints();
                        gbc_btnCancelarAposta.gridx = 0;
                        gbc_btnCancelarAposta.gridy = 2;
                        gbc_btnCancelarAposta.gridwidth = 2;
                        gbc_btnCancelarAposta.insets = new Insets(10, 0, 10, 0);
                        gbc_btnCancelarAposta.anchor = GridBagConstraints.CENTER;
                        apostaPanel.add(btnCancelarAposta, gbc_btnCancelarAposta);
                    }

                }

                apostaPanel.add(eventsPanel, gbc_eventPanel);

                // Adiciona o painel de aposta ao painel principal
                GridBagConstraints gbc_panel = new GridBagConstraints();
                gbc_panel.fill = GridBagConstraints.BOTH;
                gbc_panel.insets = new Insets(10, 10, 10, 10);
                gbc_panel.gridx = 0;
                gbc_panel.gridy = row++;
                gbc_panel.weightx = 1.0;
                gbc_panel.weighty = 1.0;
                mainPanel.add(apostaPanel, gbc_panel);
            }
        } 
            
      
    }

    private void addLabelAndValue(JPanel panel, String labelText, String valueText, int row, boolean isImportant) {
        JLabel label = new JLabel(labelText);
        label.setForeground(isImportant ? Color.WHITE : new Color(128, 0, 255));
        label.setFont(new Font("Tw Cen MT", Font.BOLD, 18));
        GridBagConstraints gbc_label = new GridBagConstraints();
        gbc_label.anchor = GridBagConstraints.WEST;
        gbc_label.insets = new Insets(0, 0, 5, 5);
        gbc_label.gridx = 0;
        gbc_label.gridy = row;
        panel.add(label, gbc_label);

        JLabel value = new JLabel(valueText);
        value.setForeground(isImportant ? Color.WHITE : new Color(255, 255, 255));
        value.setFont(new Font("Tw Cen MT", Font.BOLD, 18)); // Fonte aumentada e negrito para detalhes importantes
        GridBagConstraints gbc_value = new GridBagConstraints();
        gbc_value.anchor = GridBagConstraints.WEST;
        gbc_value.insets = new Insets(0, 0, 5, 0);
        gbc_value.gridx = 1;
        gbc_value.gridy = row;
        panel.add(value, gbc_value);
    }

    private void redirectHomeuser() {
        try {

            NewHome home = new NewHome();
            home.setVisible(true);
            frame.dispose();
        } catch (Exception ex) {
            throw ex;
        }

    }

}
