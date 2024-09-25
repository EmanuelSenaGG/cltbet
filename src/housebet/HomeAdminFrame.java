package housebet;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import model.Event;
import services.EventService;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;

public class HomeAdminFrame {

	private JFrame frame;
	private List<Event> events;

	private EventService _service = new EventService();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomeAdminFrame window = new HomeAdminFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public HomeAdminFrame() {
		frame = new JFrame();
		getEvents();
		initialize();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(false);
	}

	private void redirectToLogin() {
		LoginFrame frameLogin = new LoginFrame();
		frameLogin.setVisible(true);
		frame.dispose();

	}

	private void redirectToManagement() {
		EventosAtivosFrame frameEventos = new EventosAtivosFrame();
		frameEventos.setVisible(true);
		frame.dispose();

	}

	public void setVisible(boolean isvisible) {
		frame.setVisible(isvisible);
	}

	private void getEvents() {
		try {
			events = _service.getEventsAPI();
		} catch (Exception ex) {
			events = new ArrayList();
		}

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
					"Ativar"
			};
			table.addRow(rowData);
		}
	}

	private void initialize() {

		frame.getContentPane().setBackground(new Color(0, 0, 0));
		frame.setBounds(100, 100, 1330, 797);
		frame.getContentPane().setLayout(new GridBagLayout());

		JPanel panel = new JPanel();
		panel.setBackground(new Color(128, 0, 255));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		gbc_panel.gridwidth = GridBagConstraints.REMAINDER;
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		frame.getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		panel.setLayout(gbl_panel);

		// "Olá Administrador" à esquerda
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

		// "Consultar Eventos Ativos" à direita
		JButton btnConsultarEventos = new JButton("Consultar Eventos Ativos");
		btnConsultarEventos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				redirectToManagement();
			}
		});

		btnConsultarEventos.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				btnConsultarEventos.setBackground(Color.LIGHT_GRAY);
			}

			public void mouseReleased(java.awt.event.MouseEvent evt) {
				btnConsultarEventos.setBackground(new Color(255, 255, 255));
			}
		});
		// "CLTBET" no centro
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
		btnConsultarEventos.setOpaque(true);
		btnConsultarEventos.setForeground(new Color(0, 0, 0));
		btnConsultarEventos.setBackground(new Color(255, 255, 255));
		btnConsultarEventos.setFont(new Font("Tw Cen MT", Font.BOLD, 18));
		btnConsultarEventos.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		btnConsultarEventos.setFocusPainted(false);
		btnConsultarEventos.setContentAreaFilled(false);
		btnConsultarEventos.setOpaque(true);
		GridBagConstraints gbc_btnConsultarEventos = new GridBagConstraints();
		gbc_btnConsultarEventos.insets = new Insets(10, 10, 10, 5);
		gbc_btnConsultarEventos.gridx = 13;
		gbc_btnConsultarEventos.gridy = 0;
		gbc_btnConsultarEventos.anchor = GridBagConstraints.EAST;
		panel.add(btnConsultarEventos, gbc_btnConsultarEventos);

		// "Sair" no canto direito
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

		// Rótulo "Gerenciamento de Eventos" e tabela
		JLabel lblGerenciamentoEventos = new JLabel("Gerenciamento de Eventos");
		lblGerenciamentoEventos.setFont(new Font("Tw Cen MT", Font.BOLD, 30));
		lblGerenciamentoEventos.setForeground(new Color(128, 0, 255));
		GridBagConstraints gbc_lblGerenciamentoEventos = new GridBagConstraints();
		gbc_lblGerenciamentoEventos.gridx = 0;
		gbc_lblGerenciamentoEventos.gridy = 1;
		gbc_lblGerenciamentoEventos.gridwidth = GridBagConstraints.REMAINDER;
		gbc_lblGerenciamentoEventos.insets = new Insets(10, 0, 10, 0);
		gbc_lblGerenciamentoEventos.anchor = GridBagConstraints.CENTER;
		frame.getContentPane().add(lblGerenciamentoEventos, gbc_lblGerenciamentoEventos);

		// Criação da tabela e configuração
		String[] columnNames = { "NOME", "CASA", "FORA", "ODD CASA", "ODD FORA", "ODD EMPATE", "DESCRIÇÃO",
				"DATA DO JOGO",
				"AÇÃO" };
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);
		JTable table = new JTable(model);
		table.setForeground(new Color(0, 0, 0));
		// Definindo as larguras para cada coluna
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		// Aplicar o renderizador para cada coluna, exceto a coluna de botões (se
		// houver)
		for (int i = 0; i < table.getColumnCount(); i++) {
			if (i != 8) { // Assume que a última coluna é a de botões, que não deve ser centralizada
				table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
			}
		}
		// Adicionar botão "Ativar" na última coluna
		TableColumn column = table.getColumnModel().getColumn(8);
		table.setRowHeight(30);

		JTableHeader header = table.getTableHeader();
		header.setFont(new Font("Tw Cen MT", Font.BOLD, 16));
		header.setForeground(new Color(128, 0, 255)); // Define a cor roxa para o texto das colunas
		header.setBackground(new Color(0, 0, 0)); // Define a cor de fundo, se necessário

		// Alterar a fonte do conteúdo das linhas
		table.setFont(new Font("Tw Cen MT", Font.BOLD, 16));
		column.setCellRenderer(new ButtonRenderer());
		column.setCellEditor(new ButtonEditor(new JCheckBox(), events, table));

		TableColumnModel columnModel = table.getColumnModel();

		// Nome
		columnModel.getColumn(0).setPreferredWidth(150);
		// Casa
		columnModel.getColumn(1).setPreferredWidth(80);
		// Fora
		columnModel.getColumn(2).setPreferredWidth(80);
		// OddCasa
		columnModel.getColumn(3).setPreferredWidth(10);
		// OddFora
		columnModel.getColumn(4).setPreferredWidth(10);
		// OddEmpate
		columnModel.getColumn(5).setPreferredWidth(0);
		// Descrição
		columnModel.getColumn(6).setPreferredWidth(400);
		// Data do Jogo
		columnModel.getColumn(7).setPreferredWidth(20);
		// Ativar (botão)
		columnModel.getColumn(8).setPreferredWidth(10);
		JScrollPane scrollPane = new JScrollPane(table);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 2;
		gbc_scrollPane.gridwidth = GridBagConstraints.REMAINDER;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.weightx = 1.0;
		gbc_scrollPane.weighty = 1.0;
		frame.getContentPane().add(scrollPane, gbc_scrollPane);

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

		public ButtonEditor(JCheckBox checkBox, List<Event> events, JTable table) {
			super(checkBox);
			this.events = events;
			this.table = table;
			button = new JButton();
			button.setOpaque(true);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
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
			if (isPushed) {
				int row = table.getSelectedRow();
				Event event = events.get(row);

				// Este código precisa ser executado na EDT
				SwingUtilities.invokeLater(() -> {
					int dialogResult = JOptionPane.showConfirmDialog(null,
							"Deseja realmente ativar este evento?",
							"Confirmação",
							JOptionPane.YES_NO_OPTION);

					if (dialogResult == JOptionPane.YES_OPTION) {
						try {
							event.setIsActive(true);

							_service.insertEvent(event);
							JOptionPane.showMessageDialog(null, "Evento ativado com sucesso!");
							// Remover o evento da lista e da tabela
							events.remove(row);
							((DefaultTableModel) table.getModel()).removeRow(row);
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, "Erro ao ativar o evento: " + ex.getMessage(), "Erro",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				});
			}
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
