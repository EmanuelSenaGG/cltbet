package housebet;

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

import model.Event;
import services.EventService;

import javax.print.attribute.AttributeSet;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;

public class EventosAtivosFrame {

	private JFrame frameEventosAtivos;
	private List<Event> events;

	private EventService _service = new EventService();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EventosAtivosFrame window = new EventosAtivosFrame();
					window.frameEventosAtivos.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EventosAtivosFrame() {
		frameEventosAtivos = new JFrame();
		getEvents();
		initialize();
		frameEventosAtivos.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frameEventosAtivos.setUndecorated(false);
	}

	public void setVisible(boolean isvisible) {
		frameEventosAtivos.setVisible(isvisible);
	}

	private void getEvents() {
		try {
			events = _service.getAllEventsForAdmin();
		} catch (Exception ex) {
			events = new ArrayList();
		}

	}

	private void redirectToLogin() {
		LoginFrame frameLogin = new LoginFrame();
		frameLogin.setVisible(true);
		frameEventosAtivos.dispose();

	}

	private void redirectAdminHome() {
		// Cria e exibe o frame de loading
		LoadingFrame loadingFrame = new LoadingFrame();

		// Cria uma nova thread para carregar o HomeAdminFrame
		new Thread(() -> {
			try {
				// Inicialize o HomeAdminFrame e carregue os dados da API
				HomeAdminFrame frameAdmin = new HomeAdminFrame();

				// Execute na EDT para garantir que a atualização da UI seja feita corretamente
				frameAdmin.setVisible(true); // Exibe o HomeAdminFrame
				// Fecha o frame de login

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				loadingFrame.dispose(); // Fecha o frame de loading
				frameEventosAtivos.dispose();
			}
		}).start();

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
					"Remover",
					"Editar",
					"Finalizar",
					event.getId()
			};
			table.addRow(rowData);
		}
	}

	private void initialize() {
		frameEventosAtivos.getContentPane().setBackground(new Color(0, 0, 0));
		frameEventosAtivos.setBounds(100, 100, 1330, 797);
		frameEventosAtivos.getContentPane().setLayout(new GridBagLayout());

		JPanel panel = new JPanel();
		panel.setBackground(new Color(128, 0, 255));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		gbc_panel.gridwidth = GridBagConstraints.REMAINDER;
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		frameEventosAtivos.getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		panel.setLayout(gbl_panel);

		JLabel lblAdmin = new JLabel("Olá Administrador");
		lblAdmin.setHorizontalAlignment(SwingConstants.LEFT);
		lblAdmin.setForeground(new Color(0, 0, 0));
		lblAdmin.setFont(new Font("Tw Cen MT", Font.BOLD, 40));
		GridBagConstraints gbc_lblAdmin = new GridBagConstraints();
		gbc_lblAdmin.insets = new Insets(10, 10, 10, 40);
		gbc_lblAdmin.gridx = 0;
		gbc_lblAdmin.gridy = 0;
		gbc_lblAdmin.anchor = GridBagConstraints.WEST;
		panel.add(lblAdmin, gbc_lblAdmin);

		JButton btnGerenciarEventos = new JButton("Gerenciar Eventos");
		btnGerenciarEventos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				redirectAdminHome();
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

		JLabel lblTitle = new JLabel("CLTBET");
		lblTitle.setForeground(new Color(0, 0, 0));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tw Cen MT", Font.BOLD, 60));
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.insets = new Insets(0, 0, 0, 5);
		gbc_lblTitle.gridx = 8;
		gbc_lblTitle.gridy = 0;
		gbc_lblTitle.anchor = GridBagConstraints.CENTER;
		panel.add(lblTitle, gbc_lblTitle);
		btnGerenciarEventos.setOpaque(true);
		btnGerenciarEventos.setForeground(new Color(0, 0, 0));
		btnGerenciarEventos.setBackground(new Color(255, 255, 255));
		btnGerenciarEventos.setFont(new Font("Tw Cen MT", Font.BOLD, 18));
		btnGerenciarEventos.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		btnGerenciarEventos.setFocusPainted(false);
		btnGerenciarEventos.setContentAreaFilled(false);
		btnGerenciarEventos.setOpaque(true);
		GridBagConstraints gbc_btnGerenciarEventos = new GridBagConstraints();
		gbc_btnGerenciarEventos.insets = new Insets(10, 10, 10, 5);
		gbc_btnGerenciarEventos.gridx = 13;
		gbc_btnGerenciarEventos.gridy = 0;
		gbc_btnGerenciarEventos.anchor = GridBagConstraints.EAST;
		panel.add(btnGerenciarEventos, gbc_btnGerenciarEventos);

		JButton btnSair = new JButton("Sair");
		btnSair.setForeground(new Color(0, 0, 0));
		btnSair.setBackground(new Color(255, 255, 255));
		btnSair.setFont(new Font("Tw Cen MT", Font.BOLD, 18));
		btnSair.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		btnSair.setFocusPainted(false);
		btnSair.setContentAreaFilled(false);
		btnSair.setOpaque(true);
		btnSair.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				redirectToLogin();
			}

		});
		btnSair.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				btnSair.setBackground(Color.LIGHT_GRAY);
			}

			public void mouseReleased(java.awt.event.MouseEvent evt) {
				btnSair.setBackground(new Color(255, 255, 255));
			}
		});

		GridBagConstraints gbc_btnSair = new GridBagConstraints();
		gbc_btnSair.insets = new Insets(10, 10, 10, 10);
		gbc_btnSair.gridx = 19;
		gbc_btnSair.gridy = 0;
		gbc_btnSair.anchor = GridBagConstraints.EAST;
		panel.add(btnSair, gbc_btnSair);

		JLabel lblGerenciamentoEventos = new JLabel("Gerenciamento de Eventos Ativos");
		lblGerenciamentoEventos.setFont(new Font("Tw Cen MT", Font.BOLD, 30));
		lblGerenciamentoEventos.setForeground(new Color(128, 0, 255));
		GridBagConstraints gbc_lblGerenciamentoEventos = new GridBagConstraints();
		gbc_lblGerenciamentoEventos.gridx = 0;
		gbc_lblGerenciamentoEventos.gridy = 1;
		gbc_lblGerenciamentoEventos.gridwidth = GridBagConstraints.REMAINDER;
		gbc_lblGerenciamentoEventos.insets = new Insets(10, 0, 10, 0);
		gbc_lblGerenciamentoEventos.anchor = GridBagConstraints.CENTER;
		frameEventosAtivos.getContentPane().add(lblGerenciamentoEventos, gbc_lblGerenciamentoEventos);

		String[] columnNames = {
				"NOME", "CASA", "FORA", "ODD CASA", "ODD FORA", "ODD EMPATE",
				"DESCRIÇÃO", "DATA DO JOGO", "Remover", "Editar", "Finalizar", "ID"
		};
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);
		JTable table = new JTable(model);
		table.setForeground(new Color(0, 0, 0));

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		// Configurações de renderizador e editor de botão para as colunas "Remover",
		// "Editar" e "Finalizar"
		for (int i = 8; i <= 10; i++) {
			TableColumn buttonColumn = table.getColumnModel().getColumn(i);
			buttonColumn.setCellRenderer(new ButtonRenderer());
			buttonColumn.setCellEditor(new ButtonEditor(new JCheckBox(), events, table, _service));
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

		// Ocultar a coluna ID
		TableColumn idColumn = table.getColumnModel().getColumn(11);
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
		frameEventosAtivos.getContentPane().add(scrollPane, gbc_scrollPane);

		addRowsToTable(model, events);
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
			if (label.equals("Editar")) {
				editEvent(event, row);
			} else if (label.equals("Remover")) {
				removeEvent(event, row);
			} else {
				finalizarEvento(event, row);
			}
		}

		private void editEvent(Event event, int row) {
			SwingUtilities.invokeLater(() -> {
				// Exibir um diálogo para editar os detalhes do evento
				JTextField nameField = new JTextField(event.getName());
				nameField.setEditable(false);

				JTextField homeTeamField = new JTextField(event.getHomeTeam());
				homeTeamField.setEditable(false);

				JTextField awayTeamField = new JTextField(event.getAwayTeam());
				awayTeamField.setEditable(false);

				JTextField homeOddsField = new JTextField(Double.toString(event.getHomeOdds()));
				JTextField awayOddsField = new JTextField(Double.toString(event.getAwayOdds()));
				JTextField drawOddsField = new JTextField(Double.toString(event.getDrawOdds()));

				// Adiciona a validação para permitir apenas números e ponto
				addNumericValidation(homeOddsField);
				addNumericValidation(awayOddsField);
				addNumericValidation(drawOddsField);

				JTextField descriptionField = new JTextField(event.getDescription());
				descriptionField.setEditable(false);

				JTextField dateField = new JTextField(
						event.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
				dateField.setEditable(false);

				JCheckBox activeCheckBox = new JCheckBox("Ativo", event.getIsActive());

				JPanel panel = new JPanel(new GridLayout(9, 2));
				panel.add(new JLabel("Nome:"));
				panel.add(nameField);
				panel.add(new JLabel("Time da Casa:"));
				panel.add(homeTeamField);
				panel.add(new JLabel("Time de Fora:"));
				panel.add(awayTeamField);
				panel.add(new JLabel("Odd Casa:"));
				panel.add(homeOddsField);
				panel.add(new JLabel("Odd Fora:"));
				panel.add(awayOddsField);
				panel.add(new JLabel("Odd Empate:"));
				panel.add(drawOddsField);
				panel.add(new JLabel("Descrição:"));
				panel.add(descriptionField);
				panel.add(new JLabel("Data do Jogo:"));
				panel.add(dateField);
				panel.add(new JLabel("Ativo:"));
				panel.add(activeCheckBox);

				int result = JOptionPane.showConfirmDialog(null, panel, "Editar Evento",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

				if (result == JOptionPane.OK_OPTION) {
					// Adiciona um diálogo de confirmação antes de atualizar o evento
					int confirmResult = JOptionPane.showConfirmDialog(null,
							"Tem certeza de que deseja atualizar este evento?",
							"Confirmação", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

					if (confirmResult == JOptionPane.YES_OPTION) {
						try {
							// Atualizar apenas os campos permitidos
							event.setHomeOdds(Double.parseDouble(homeOddsField.getText()));
							event.setAwayOdds(Double.parseDouble(awayOddsField.getText()));
							event.setDrawOdds(Double.parseDouble(drawOddsField.getText()));
							event.setIsActive(activeCheckBox.isSelected());

							// Atualizar o evento no banco de dados e obter o evento atualizado
							Event eventUpdated = _service.updateEvent(event);

							JOptionPane.showMessageDialog(null, "Evento atualizado com sucesso!");

							// Atualizar a tabela com os valores do evento atualizado
							DefaultTableModel model = (DefaultTableModel) table.getModel();
							model.setValueAt(eventUpdated.getName(), row, 0);
							model.setValueAt(eventUpdated.getHomeTeam(), row, 1);
							model.setValueAt(eventUpdated.getAwayTeam(), row, 2);
							model.setValueAt(eventUpdated.getHomeOdds(), row, 3);
							model.setValueAt(eventUpdated.getAwayOdds(), row, 4);
							model.setValueAt(eventUpdated.getDrawOdds(), row, 5);
							model.setValueAt(eventUpdated.getDescription(), row, 6);
							model.setValueAt(eventUpdated.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
									row, 7);

						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, "Erro ao atualizar o evento: " + ex.getMessage(),
									"Erro",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			});
		}

		// Método para adicionar a validação que permite apenas números e ponto
		private void addNumericValidation(JTextField textField) {
			textField.getDocument().addDocumentListener(new DocumentListener() {
				private void validate() {
					String text = textField.getText();
					if (!text.matches("\\d*\\.?\\d*")) {
						SwingUtilities.invokeLater(() -> {
							textField.setText(text.replaceAll("[^\\d.]", ""));
						});
					}
				}

				@Override
				public void insertUpdate(DocumentEvent e) {
					validate();
				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					validate();
				}

				@Override
				public void changedUpdate(DocumentEvent e) {
					validate();
				}
			});
		}

		private void removeEvent(Event event, int row) {
			SwingUtilities.invokeLater(() -> {
				int dialogResult = JOptionPane.showConfirmDialog(null,
						"Deseja realmente remover este evento?",
						"Confirmação",
						JOptionPane.YES_NO_OPTION);

				if (dialogResult == JOptionPane.YES_OPTION) {
					try {
						_service.deleteEvent(event.getId());
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

		private void finalizarEvento(Event event, int row) {

			if (event.getResult() == null) {
				SwingUtilities.invokeLater(() -> {
					// Exibir um diálogo para finalizar o evento
					JLabel homeTeamLabel = new JLabel("Casa: " + event.getHomeTeam());
					JLabel awayTeamLabel = new JLabel(" Fora: " + event.getAwayTeam());

					JCheckBox homeWinCheckBox = new JCheckBox("Vitória Casa");
					JCheckBox awayWinCheckBox = new JCheckBox("Vitória Fora");
					JCheckBox drawCheckBox = new JCheckBox("Empate");

					ButtonGroup group = new ButtonGroup();
					group.add(homeWinCheckBox);
					group.add(awayWinCheckBox);
					group.add(drawCheckBox);

					JPanel panel = new JPanel(new GridLayout(4, 1));
					panel.add(homeTeamLabel);
					panel.add(awayTeamLabel);
					panel.add(homeWinCheckBox);
					panel.add(awayWinCheckBox);
					panel.add(drawCheckBox);

					int result = JOptionPane.showConfirmDialog(null, panel, "Finalizar Evento",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

					if (result == JOptionPane.OK_OPTION) {
						if (!homeWinCheckBox.isSelected() && !awayWinCheckBox.isSelected()
								&& !drawCheckBox.isSelected()) {
							JOptionPane.showMessageDialog(null, "Selecione um resultado para finalizar o evento.",
									"Erro",
									JOptionPane.ERROR_MESSAGE);
							return;
						}

						String resultado;
						if (homeWinCheckBox.isSelected()) {
							resultado = "Casa";
						} else if (awayWinCheckBox.isSelected()) {
							resultado = "Fora";
						} else {
							resultado = "Empate";
						}

						int confirmResult = JOptionPane.showConfirmDialog(null,
								"Você selecionou: " + resultado + ". Confirmar finalização?", "Confirmar Finalização",
								JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

						if (confirmResult == JOptionPane.YES_OPTION) {
							try {
								_service.endEvent(event.getId(), resultado);

								JOptionPane.showMessageDialog(null, "Evento finalizado com sucesso!");
								events.remove(row);
								((DefaultTableModel) table.getModel()).removeRow(row);
							} catch (Exception ex) {
								JOptionPane.showMessageDialog(null, "Erro ao finalizar o evento: " + ex.getMessage(),
										"Erro", JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				});
			} else {
				JOptionPane.showMessageDialog(null, "Esse evento ja foi finalizado. Efetue a remoção.",
						"Erro", JOptionPane.ERROR_MESSAGE);
			}

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
}
