package housebet;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import model.Bet;
import model.Event;
import services.BetService;
import services.EventService;
import utils.Global.CurrentUser;

import javax.print.attribute.AttributeSet;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;

public class GerenciarApostasFrame {
	private JFrame frameGerenciar;
	private List<Event> selectedEvents = new ArrayList<>();
	private JButton btnVerSelecionados;

	private EventService _eventService = new EventService();
	private BetService _betService = new BetService();

	// public static void main(String[] args) {
	// 	EventQueue.invokeLater(new Runnable() {
	// 		public void run() {
	// 			try {
	// 				GerenciarApostasFrame frameGerenciar = new GerenciarApostasFrame();
	// 				frameGerenciar.setVisible(true);
	// 			} catch (Exception e) {
	// 				e.printStackTrace();
	// 			}
	// 		}
	// 	});
	// }

	/**
	 * Create the application.
	 */
	public GerenciarApostasFrame() {

		frameGerenciar = new JFrame();

		initialize();
		frameGerenciar.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frameGerenciar.setUndecorated(false);
	}

	public void setVisible(boolean isvisible) {
		frameGerenciar.setVisible(isvisible);
	}

	

	private void addRowsToTable(DefaultTableModel table, List<Event> events) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		for (Event event : events) {
			Object[] rowData = {

					event.getName(),
					event.getHomeTeam(),
					event.getAwayTeam(),
					event.getHomeOdds(),
					event.getAwayOdds(),
					event.getDrawOdds(),
					event.getDescription(),
					event.getDate().format(formatter),
					event.getOption(),
					"Remover",
					"Selecionar",
					"Cancelar",
					event.getId()
			};
			table.addRow(rowData);
		}
	}

	private void redirectHomeuser() {
		try {

			NewHome home = new NewHome();
			home.setVisible(true);
			frameGerenciar.dispose();
		} catch (Exception ex) {
			throw ex;
		}

	}

	private void initialize() {
		frameGerenciar.getContentPane().setBackground(new Color(0, 0, 0));
		frameGerenciar.setBounds(100, 100, 1330, 797);
		frameGerenciar.getContentPane().setLayout(new GridBagLayout());
	
		JPanel panel = new JPanel();
		panel.setBackground(new Color(128, 0, 255));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		gbc_panel.gridwidth = GridBagConstraints.REMAINDER;
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		frameGerenciar.getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{100, 500, 100}; // Largura das colunas
		gbl_panel.columnWeights = new double[]{0.5, 1.0, 0.5}; // Peso das colunas
		panel.setLayout(gbl_panel);
	
		// Label para o nome do Admin, alinhado à esquerda
		JLabel lblAdmin = new JLabel("Olá " + CurrentUser.getUsername());
		lblAdmin.setHorizontalAlignment(SwingConstants.LEFT);
		lblAdmin.setForeground(new Color(0, 0, 0));
		lblAdmin.setFont(new Font("Tw Cen MT", Font.BOLD, 40));
		GridBagConstraints gbc_lblAdmin = new GridBagConstraints();
		gbc_lblAdmin.insets = new Insets(10, 10, 10, 10);
		gbc_lblAdmin.gridx = 0;
		gbc_lblAdmin.gridy = 0;
		gbc_lblAdmin.anchor = GridBagConstraints.WEST;
		panel.add(lblAdmin, gbc_lblAdmin);
	
		// Label para o título "CLTBET", centralizado
		JLabel lblTitle = new JLabel("CLTBET");
		lblTitle.setForeground(new Color(0, 0, 0));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tw Cen MT", Font.BOLD, 60));
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.insets = new Insets(10, 0, 10, 0);
		gbc_lblTitle.gridx = 1;
		gbc_lblTitle.gridy = 0;
		gbc_lblTitle.anchor = GridBagConstraints.CENTER;
		panel.add(lblTitle, gbc_lblTitle);
	
		// Botão "Voltar", alinhado à direita
		JButton btnGerenciarEventos = new JButton("Voltar");
		btnGerenciarEventos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				redirectHomeuser();
			}
		});
	
		btnGerenciarEventos.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				btnGerenciarEventos.setBackground(Color.LIGHT_GRAY);
			}
	
			public void mouseReleased(java.awt.event.MouseEvent evt) {
				btnGerenciarEventos.setBackground(new Color(255, 255, 255));
			}
		});
	
		btnGerenciarEventos.setOpaque(true);
		btnGerenciarEventos.setForeground(new Color(0, 0, 0));
		btnGerenciarEventos.setBackground(new Color(255, 255, 255));
		btnGerenciarEventos.setFont(new Font("Tw Cen MT", Font.BOLD, 18));
		btnGerenciarEventos.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		btnGerenciarEventos.setFocusPainted(false);
		GridBagConstraints gbc_btnGerenciarEventos = new GridBagConstraints();
		gbc_btnGerenciarEventos.insets = new Insets(10, 10, 10, 10);
		gbc_btnGerenciarEventos.gridx = 2;
		gbc_btnGerenciarEventos.gridy = 0;
		gbc_btnGerenciarEventos.anchor = GridBagConstraints.EAST;
		panel.add(btnGerenciarEventos, gbc_btnGerenciarEventos);

		JLabel lblGerenciamentoEventos = new JLabel("Gerenciamento de Apostas");
		lblGerenciamentoEventos.setFont(new Font("Tw Cen MT", Font.BOLD, 30));
		lblGerenciamentoEventos.setForeground(new Color(128, 0, 255));
		GridBagConstraints gbc_lblGerenciamentoEventos = new GridBagConstraints();
		gbc_lblGerenciamentoEventos.gridx = 0;
		gbc_lblGerenciamentoEventos.gridy = 1;
		gbc_lblGerenciamentoEventos.gridwidth = GridBagConstraints.REMAINDER;
		gbc_lblGerenciamentoEventos.insets = new Insets(10, 0, 10, 0);
		gbc_lblGerenciamentoEventos.anchor = GridBagConstraints.CENTER;
		frameGerenciar.getContentPane().add(lblGerenciamentoEventos, gbc_lblGerenciamentoEventos);

		String[] columnNames = {
				"NOME", "CASA", "FORA", "ODD CASA", "ODD FORA", "ODD EMPATE",
				"DESCRIÇÃO", "DATA DO JOGO", "OPÇÃO", "Remover", "Selecionar", "Cancelar", "ID"
		};
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);
		JTable table = new JTable(model);
		table.setForeground(new Color(0, 0, 0));

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		// Configurações de renderizador e editor de botão para as colunas "Remover",
		// "Editar" e "Finalizar"
		for (int i = 9; i <= 11; i++) {
			TableColumn buttonColumn = table.getColumnModel().getColumn(i);
			buttonColumn.setCellRenderer(new ButtonRenderer());
			buttonColumn
					.setCellEditor(
							new ButtonEditor(new JCheckBox(), CurrentUser.getEventsSelected(), table, _eventService));
		}

		table.setRowHeight(30);

		JTableHeader header = table.getTableHeader();
		header.setFont(new Font("Tw Cen MT", Font.BOLD, 16));
		header.setForeground(new Color(128, 0, 255));
		header.setBackground(new Color(0, 0, 0));

		table.setFont(new Font("Tw Cen MT", Font.BOLD, 16));

		TableColumnModel columnModel = table.getColumnModel();

		columnModel.getColumn(0).setPreferredWidth(150); // Nome
		columnModel.getColumn(1).setPreferredWidth(80); // Casa
		columnModel.getColumn(2).setPreferredWidth(80); // Fora
		columnModel.getColumn(3).setPreferredWidth(10); // OddCasa
		columnModel.getColumn(4).setPreferredWidth(10); // OddFora
		columnModel.getColumn(5).setPreferredWidth(0); // OddEmpate
		columnModel.getColumn(6).setPreferredWidth(400); // Descrição
		columnModel.getColumn(7).setPreferredWidth(20); // Data do Jogo
		columnModel.getColumn(8).setPreferredWidth(10); // Remover
		columnModel.getColumn(9).setPreferredWidth(10); // Editar
		columnModel.getColumn(10).setPreferredWidth(10); // Finalizar
		columnModel.getColumn(11).setPreferredWidth(10); // Finalizar

		// Ocultar a coluna ID
		TableColumn idColumn = table.getColumnModel().getColumn(12);
		idColumn.setMinWidth(0);
		idColumn.setMaxWidth(0);
		idColumn.setPreferredWidth(0);

		JScrollPane scrollPane = new JScrollPane(table);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 2;
		gbc_scrollPane.gridwidth = GridBagConstraints.REMAINDER;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.weightx = 1.0;
		gbc_scrollPane.weighty = 1.0;
		frameGerenciar.getContentPane().add(scrollPane, gbc_scrollPane);

		btnVerSelecionados = new JButton("Ver Selecionados");
		btnVerSelecionados.setFont(new Font("Tw Cen MT", Font.BOLD, 18));
		btnVerSelecionados.setForeground(Color.WHITE);
		btnVerSelecionados.setBackground(new Color(0, 128, 0));
		btnVerSelecionados.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		btnVerSelecionados.setFocusPainted(false);
		btnVerSelecionados.setVisible(false); // Começa invisível

		btnVerSelecionados.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showSelectedEvents();
			}
		});

		GridBagConstraints gbc_btnVerSelecionados = new GridBagConstraints();
		gbc_btnVerSelecionados.insets = new Insets(10, 10, 10, 5);
		gbc_btnVerSelecionados.gridx = 14;
		gbc_btnVerSelecionados.gridy = 0;
		gbc_btnVerSelecionados.anchor = GridBagConstraints.EAST;
		panel.add(btnVerSelecionados, gbc_btnVerSelecionados);

		addRowsToTable(model, CurrentUser.getEventsSelected());
	}

	JLabel createStyledLabel(String text, Color color, Font font) {
		JLabel label = new JLabel(text);
		label.setFont(font);
		label.setForeground(color);
		return label;
	}

	private void showSelectedEvents() {
		// Cria um painel principal para exibir os eventos selecionados
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10); // Espaçamento entre componentes
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;

		if (selectedEvents.isEmpty()) {
			JOptionPane.showMessageDialog(frameGerenciar, "Nenhum evento selecionado.");
			return;
		}

		// Define a fonte e a cor para os rótulos
		Font boldFont = new Font("Tw Cen MT", Font.BOLD, 16);
		Color labelColor = new Color(34, 45, 50); // Cor para o texto do evento
		Color valueColor = new Color(0, 102, 204); // Cor para os valores dos dados

		// Adiciona cada evento ao painel principal
		for (Event event : selectedEvents) {
			JPanel eventPanel = new JPanel();
			eventPanel.setLayout(new GridBagLayout());
			eventPanel.setBorder(BorderFactory.createTitledBorder("Evento")); // Adiciona uma borda com título

			GridBagConstraints eventGbc = new GridBagConstraints();
			eventGbc.insets = new Insets(5, 10, 5, 10); // Espaçamento interno para labels
			eventGbc.fill = GridBagConstraints.HORIZONTAL;
			eventGbc.gridx = 0;
			eventGbc.gridy = 0;
			eventGbc.anchor = GridBagConstraints.WEST;

			// Adiciona informações do evento com estilo
			eventPanel.add(createStyledLabel("Nome:", labelColor, boldFont), eventGbc);
			eventGbc.gridx = 1;
			eventPanel.add(createStyledLabel(event.getName(), valueColor, boldFont), eventGbc);

			eventGbc.gridx = 0;
			eventGbc.gridy++;
			eventPanel.add(createStyledLabel("Casa:", labelColor, boldFont), eventGbc);
			eventGbc.gridx = 1;
			eventPanel.add(createStyledLabel(event.getHomeTeam(), valueColor, boldFont), eventGbc);

			eventGbc.gridx = 0;
			eventGbc.gridy++;
			eventPanel.add(createStyledLabel("Fora:", labelColor, boldFont), eventGbc);
			eventGbc.gridx = 1;
			eventPanel.add(createStyledLabel(event.getAwayTeam(), valueColor, boldFont), eventGbc);

			eventGbc.gridx = 0;
			eventGbc.gridy++;
			eventPanel.add(createStyledLabel("Aposta:", labelColor, boldFont), eventGbc);
			eventGbc.gridx = 1;
			eventPanel.add(createStyledLabel(event.getOption(), valueColor, boldFont), eventGbc);

			eventGbc.gridx = 0;
			eventGbc.gridy++;
			eventPanel.add(createStyledLabel("Odd Aposta:", labelColor, boldFont), eventGbc);
			eventGbc.gridx = 1;
			eventPanel.add(createStyledLabel(String.valueOf(event.getSelectedOdd()), valueColor, boldFont), eventGbc);

			// Adiciona o painel de evento ao painel principal
			gbc.gridx = 0;
			gbc.gridy++;
			gbc.weightx = 1.0; // Faz o painel de evento expandir horizontalmente
			gbc.weighty = 0.0; // Define o peso vertical como zero para não expandir verticalmente
			mainPanel.add(eventPanel, gbc);
		}

		// Campos de entrada independentes para valor de aposta e possível retorno
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.weightx = 0.0; // Remove o peso horizontal do painel de entrada
		gbc.weighty = 0.0; // Define o peso vertical como zero para não expandir verticalmente
		gbc.anchor = GridBagConstraints.WEST;
		mainPanel.add(new JLabel("Valor da Aposta:"), gbc);
		gbc.gridx = 1;
		JTextField betAmountField = new JTextField(10);
		mainPanel.add(betAmountField, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		mainPanel.add(new JLabel("Possível Retorno:"), gbc);
		gbc.gridx = 1;
		JTextField potentialReturnField = new JTextField(10);
		potentialReturnField.setEditable(false);
		mainPanel.add(potentialReturnField, gbc);

		// Listener para atualizar o possível retorno quando o valor da aposta mudar
		betAmountField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				updatePotentialReturn();
			}

			public void removeUpdate(DocumentEvent e) {
				updatePotentialReturn();
			}

			public void insertUpdate(DocumentEvent e) {
				updatePotentialReturn();
			}

			public void updatePotentialReturn() {
				String betText = betAmountField.getText();
				if (!betText.isEmpty()) {
					try {
						double betValue = Double.parseDouble(betText);

						double totalOdds = selectedEvents.stream()
								.mapToDouble(Event::getSelectedOdd)
								.sum();
						double potentialReturn = betValue * totalOdds;
						potentialReturnField.setText(String.format("%.2f", potentialReturn));
					} catch (NumberFormatException ex) {
						potentialReturnField.setText("Valor inválido");
					}
				} else {
					potentialReturnField.setText("");
				}
			}
		});

		// Cria o botão "Finalizar Aposta"
		JButton finalizeBetButton = new JButton("Finalizar Aposta");
		finalizeBetButton.addActionListener(e -> {
			int option = JOptionPane.showConfirmDialog(frameGerenciar,
					"Tem certeza de que deseja efetuar a aposta?",
					"Confirmação",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);

			if (option == JOptionPane.YES_OPTION) {
				if(Double.parseDouble(betAmountField.getText().replace(",", ".")) > CurrentUser.getBalance() || CurrentUser.getBalance() == 0){
					JOptionPane.showMessageDialog(null, "Saldo Insuficiente");
				}else{
					try {
						Bet bet = new Bet();
						bet.setDate(LocalDate.now());
						bet.setUserId(CurrentUser.getId());
						bet.setStatus("Pendente");
						bet.setStakeReturn(Double.parseDouble(potentialReturnField.getText().replace(",", ".")));
						bet.setAmountBet(Double.parseDouble(betAmountField.getText().replace(",", ".")));
						
						_betService.insertBet(bet, selectedEvents);
						JOptionPane.showMessageDialog(null, "Aposta realizada com sucesso!");
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Ocorreu um erro ao efetuar a aposta: " + ex.getMessage());
					}
				}

				

			}
		});

		gbc.gridx = 0;
		gbc.gridy++;
		gbc.gridwidth = 2; // Ocupa duas colunas
		gbc.anchor = GridBagConstraints.CENTER;
		mainPanel.add(finalizeBetButton, gbc);

		// Exibir o painel principal em um JOptionPane ou outro container
		JOptionPane.showMessageDialog(frameGerenciar, mainPanel, "Eventos Selecionados", JOptionPane.PLAIN_MESSAGE);
	}

	class ButtonEditor extends DefaultCellEditor {
		private JButton button;
		private String label;
		private boolean isPushed;
		private List<Event> events;
		private JTable table;
		private EventService _service; // Supondo que você tenha uma classe EventService

		public ButtonEditor(JCheckBox checkBox, List<Event> events, JTable table, EventService service) {
			super(checkBox);
			this.events = events;
			this.table = table;
			this._service = service;
			button = new JButton();
			button.setOpaque(true);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
					handleButtonClick();
				}
			});
		}

		private void handleButtonClick() {
			int row = table.getSelectedRow();
			Event event = events.get(row);

			// Determinar a ação com base no texto do botão
			if (label.equals("Selecionar")) {
				if (selectedEvents.contains(event))
					JOptionPane.showMessageDialog(null, "Evento já foi selecionado!");
				else
					selectedEvents.add(event);

				// Torna o botão "Ver Selecionados" visível se não estiver
				if (!btnVerSelecionados.isVisible()) {
					btnVerSelecionados.setVisible(true);
				}
			} else if (label.equals("Remover")) {
				removeEvent(event, row);
			} else if (label.equals("Cancelar")) {
				selectedEvents.remove(event);
				JOptionPane.showMessageDialog(null, "Seleção do evento cancelada!");
			}

		}

		// Método para adicionar a validação que permite apenas números e ponto

		private void removeEvent(Event event, int row) {
			SwingUtilities.invokeLater(() -> {
				int dialogResult = JOptionPane.showConfirmDialog(null,
						"Deseja realmente remover este evento?",
						"Confirmação",
						JOptionPane.YES_NO_OPTION);

				if (dialogResult == JOptionPane.YES_OPTION) {
					try {

						JOptionPane.showMessageDialog(null, "Evento removido com sucesso!");
						// Remover o evento da lista e da tabela
						events.remove(row);
						((DefaultTableModel) table.getModel()).removeRow(row);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Erro ao remover o evento: " + ex.getMessage(), "Erro",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			label = (value == null) ? "" : value.toString();
			button.setText(label);
			isPushed = true;
			return button;
		}

		@Override
		public Object getCellEditorValue() {
			isPushed = false;
			return label;
		}

		@Override
		public boolean stopCellEditing() {
			isPushed = false;
			return super.stopCellEditing();
		}

		@Override
		protected void fireEditingStopped() {
			super.fireEditingStopped();
		}
	}

	class ButtonRenderer extends JButton implements TableCellRenderer {
		public ButtonRenderer() {
			setOpaque(true);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			setText((value == null) ? "" : value.toString());
			return this;
		}
	}
}
