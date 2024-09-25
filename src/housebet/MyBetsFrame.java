package housebet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Bet;
import model.User;
import model.Event;
import java.awt.*;
import java.util.List;

public class MyBetsFrame {
    private JFrame frameMyBets;
    private JTable betTable;
    private DefaultTableModel tableModel;
    private User user;
    private JLabel lblTotalApostado;
    private JLabel lblPossivelRetorno;

    public MyBetsFrame(User user) {
        this.user = user;
        
        frameMyBets = new JFrame();
        
        initialize();
        
        frameMyBets.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frameMyBets.setUndecorated(false);
    }

	public void setVisible(boolean isvisible) {
		frameMyBets.setVisible(isvisible);
	}

    private void initialize() {
        frameMyBets.getContentPane().setBackground(new Color(0, 0, 0));
        frameMyBets.setTitle("CLTBET");
        frameMyBets.setSize(1280, 720);
        frameMyBets.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameMyBets.getContentPane().setLayout(null);

        JLabel lbCLTBET = new JLabel("CLTBET");
        lbCLTBET.setBounds(566, 26, 204, 75);
        lbCLTBET.setForeground(new Color(128, 0, 255));
        lbCLTBET.setFont(new Font("Tw Cen MT", Font.BOLD, 68));
        lbCLTBET.setHorizontalAlignment(SwingConstants.CENTER);
        frameMyBets.getContentPane().add(lbCLTBET);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(10, 11, 80, 23);
        frameMyBets.getContentPane().add(btnVoltar);

        JLabel lblMinhasApostas = new JLabel("Minhas Apostas");
        lblMinhasApostas.setHorizontalAlignment(SwingConstants.LEFT);
        lblMinhasApostas.setForeground(new Color(128, 0, 255));
        lblMinhasApostas.setFont(new Font("Tw Cen MT", Font.BOLD, 68));
        lblMinhasApostas.setBounds(25, 115, 458, 75);
        frameMyBets.getContentPane().add(lblMinhasApostas);

        String[] columnNames = {"Aposta ID", "Evento", "Descrição", "Data", "Time da Casa", "Time Visitante", "Odds Casa", "Odds Visitante", "Odds Empate", "Valor Aposta", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        betTable = new JTable(tableModel);
        betTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane scrollPane = new JScrollPane(betTable);
        scrollPane.setBounds(25, 201, 1150, 427);
        frameMyBets.getContentPane().add(scrollPane);

        lblTotalApostado = new JLabel("Total Apostado: R$ 0.00");
        lblTotalApostado.setForeground(new Color(255, 255, 255));
        lblTotalApostado.setBounds(25, 632, 200, 30);
        frameMyBets.getContentPane().add(lblTotalApostado);

        lblPossivelRetorno = new JLabel("Possível Retorno: R$ 0.00");
        lblPossivelRetorno.setForeground(new Color(255, 255, 255));
        lblPossivelRetorno.setBounds(235, 632, 200, 30);
        frameMyBets.getContentPane().add(lblPossivelRetorno);

        loadUserBets(user);

        JButton btnConfirmarAlteracoes = new JButton("Confirmar Alterações");
        btnConfirmarAlteracoes.setBounds(1181, 636, 133, 23);
        btnConfirmarAlteracoes.setEnabled(false);
        frameMyBets.getContentPane().add(btnConfirmarAlteracoes);

        JCheckBox chckbxConfirmar = new JCheckBox("Confirmar");
        chckbxConfirmar.setBounds(1181, 605, 83, 23);
        chckbxConfirmar.addItemListener(e -> btnConfirmarAlteracoes.setEnabled(chckbxConfirmar.isSelected()));
        frameMyBets.getContentPane().add(chckbxConfirmar);

        JButton btnDeletarSelecionados = new JButton("Deletar");
        btnDeletarSelecionados.setBounds(1185, 204, 79, 23);
        frameMyBets.getContentPane().add(btnDeletarSelecionados);

        btnDeletarSelecionados.addActionListener(e -> {
            int[] selectedRows = betTable.getSelectedRows();
            if (selectedRows.length > 0) {
                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    tableModel.removeRow(selectedRows[i]);
                }
                updateTotals();
            } else {
                JOptionPane.showMessageDialog(frameMyBets, "Nenhum evento selecionado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        updateTotals();
    }

    private void loadUserBets(User user) {
    	if (user != null) {
    		List<Bet> bets = user.getBets();
            tableModel.setRowCount(0);

            for (Bet bet : bets) {
                for (Event event : bet.getEvents()) {
                    Object[] rowData = {
                        bet.getId(),
                        event.getName(),
                        event.getDescription(),
                        event.getDate().toString(),
                        event.getHomeTeam(),
                        event.getAwayTeam(),
                        event.getHomeOdds(),
                        event.getAwayOdds(),
                        event.getDrawOdds(),
                        event.getAmountBet(),
                        bet.getStatus()
                    };
                    tableModel.addRow(rowData);
                }
            }
            updateTotals();
    	}
    }

    private void updateTotals() {
        double totalAmount = 0.0;
        double possibleAmount = 0.0;

        int rowCount = tableModel.getRowCount();
        
        if (user != null) {
        	List<Bet> bets = user.getBets();

            int tableRowIndex = 0;
            for (Bet bet : bets) {
                for (Event event : bet.getEvents()) {
                    if (tableRowIndex < rowCount) {
                        double eventAmount = (double) tableModel.getValueAt(tableRowIndex, 9);
                        String optionChosen = event.getOption();
                        double eventOdds;
                        switch (optionChosen.toLowerCase()) {
                            case "casa":
                                eventOdds = event.getHomeOdds();
                                break;
                            case "fora":
                                eventOdds = event.getAwayOdds();
                                break;
                            case "empate":
                                eventOdds = event.getDrawOdds();
                                break;
                            default:
                                eventOdds = 0.0;
                                break;
                        }

                        totalAmount += eventAmount;
                        possibleAmount += eventAmount * eventOdds;

                        tableRowIndex++;
                    }
                }
            }

            lblTotalApostado.setText(String.format("Total Apostado: R$ %.2f", totalAmount));
            lblPossivelRetorno.setText(String.format("Possível Retorno: R$ %.2f", possibleAmount));
        }
    }

    private void redirectHome() {
		Home home = new Home();
		home.setVisible(true);
		frameMyBets.dispose();
	}

    public JFrame getFrame() {
        return frameMyBets;
    }
}
