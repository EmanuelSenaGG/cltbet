package housebet;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JLabel;

import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import java.awt.event.MouseEvent;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import javax.swing.border.LineBorder;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import model.Event;
import model.User;
import services.EventService;
import services.UserService;
import utils.Global.CurrentUser;

import javax.swing.BorderFactory;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;

public class NewHome {
	private JFrame newHome;
	private List<Event> events;
	private JLabel saldoValor = new JLabel(CurrentUser.getBalance().toString());
	private JLabel lblAdmin = new JLabel("Olá " + CurrentUser.getUsername());
	private EventService _serviceEvent = new EventService();
	private UserService _serviceUser = new UserService();
	DefaultTableModel tempEventTableModel = new DefaultTableModel(
			new String[] { "Nome", "Time da Casa", "Time de Fora", "Odd Casa", "Odd Fora", "Odd Empate",
					"Descrição", "Data do Jogo", "Opção" },
			0);

	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// NewHome window = new NewHome();
	// window.newHome.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// }

	/**
	 * Create the application.
	 */
	public NewHome() {
		newHome = new JFrame();
		getEvents();
		initialize();
		newHome.setExtendedState(JFrame.MAXIMIZED_BOTH);
		newHome.setUndecorated(false);
	}

	public void setVisible(boolean isvisible) {
		newHome.setVisible(isvisible);
	}

	private void getEvents() {
		try {
			events = _serviceEvent.getAllEvents();
		} catch (Exception ex) {
			events = new ArrayList();
		}

	}

	public String getUserBalance() {
		try {
			Double balance = CurrentUser.getBalance();
			return balance != null ? balance.toString() : "0";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "0";
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
					"Apostar",
					event.getId()
			};
			table.addRow(rowData);
		}
	}

	private void initialize() {
		newHome.getContentPane().setBackground(new Color(0, 0, 0));
		newHome.setBounds(100, 100, 1330, 797);
		newHome.getContentPane().setLayout(new GridBagLayout());

		JPanel panel = new JPanel();
		panel.setBackground(new Color(128, 0, 255));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		gbc_panel.gridwidth = GridBagConstraints.REMAINDER;
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		newHome.getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		panel.setLayout(gbl_panel);

		// Configurações para o JLabel "CLTBET" (Título)
		JLabel lblTitle = new JLabel("CLTBET");
		lblTitle.setForeground(new Color(0, 0, 0));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tw Cen MT", Font.BOLD, 60));
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.gridx = 1; // Centralizar o título
		gbc_lblTitle.gridy = 0;
		gbc_lblTitle.weightx = 1.0; // Dá espaço extra ao título
		gbc_lblTitle.insets = new Insets(0, 0, 0, 5);
		gbc_lblTitle.anchor = GridBagConstraints.CENTER;
		panel.add(lblTitle, gbc_lblTitle);

		// Configurações para o JLabel "Usuerrrr" (Nome do usuário)

		lblAdmin.setHorizontalAlignment(SwingConstants.LEFT);
		lblAdmin.setForeground(new Color(0, 0, 0));
		lblAdmin.setFont(new Font("Tw Cen MT", Font.BOLD, 40));
		GridBagConstraints gbc_lblAdmin = new GridBagConstraints();
		gbc_lblAdmin.gridx = 0; // Alinha o nome à esquerda
		gbc_lblAdmin.gridy = 0;
		gbc_lblAdmin.insets = new Insets(10, 10, 10, 0); // Espaçamento à esquerda
		gbc_lblAdmin.anchor = GridBagConstraints.WEST;
		panel.add(lblAdmin, gbc_lblAdmin);

		// Criação do rótulo para o valor do saldo

		saldoValor.setFont(new Font("Tw Cen MT", Font.BOLD, 18));
		saldoValor.setForeground(new Color(0, 0, 0));

		// Configurações de GridBagConstraints para o valor do saldo
		GridBagConstraints gbc_saldoValor = new GridBagConstraints();
		gbc_saldoValor.gridx = 1; // Coloca o valor do saldo ao lado do rótulo "Saldo:"
		gbc_saldoValor.gridy = 0;
		gbc_saldoValor.insets = new Insets(10, 0, 10, 10); // Espaçamento à direita do valor do saldo
		gbc_saldoValor.anchor = GridBagConstraints.EAST;
		panel.add(saldoValor, gbc_saldoValor);

		// Configurações para o JLabel "Menu" (Menu)
		JLabel lblMenu = new JLabel("Menu");
		lblMenu.setFont(new Font("Tw Cen MT", Font.BOLD, 40));
		lblMenu.setForeground(new Color(0, 0, 0));
		lblMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
		GridBagConstraints gbc_lblMenu = new GridBagConstraints();
		gbc_lblMenu.gridx = 2; // Alinha o menu à direita
		gbc_lblMenu.gridy = 0;
		gbc_lblMenu.insets = new Insets(10, 0, 10, 10); // Espaçamento à direita
		gbc_lblMenu.anchor = GridBagConstraints.EAST;
		panel.add(lblMenu, gbc_lblMenu);

		// Criação do painel para o menu
		// Criação do painel para o menu
		JPanel menuPanel = new JPanel();
		menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
		menuPanel.setBackground(Color.WHITE);
		menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Adiciona um padding

		// Adiciona botões ao painel
		menuPanel.add(createMenuButton("Minha Conta", "MINHA_CONTA"));
		menuPanel.add(createSeparator());
		menuPanel.add(createMenuButton("Gerenciar Apostas", "GERENCIAR_APOSTAS"));
		menuPanel.add(createSeparator());
		menuPanel.add(createMenuButton("Histórico de Apostas", "HISTORICO_APOSTAS"));
		menuPanel.add(createSeparator());
		menuPanel.add(createMenuButton("Minhas Apostas", "MINHAS_APOSTAS"));
		menuPanel.add(createSeparator());
		menuPanel.add(createMenuButton("Banca", "BANCA"));
		menuPanel.add(createSeparator());
		menuPanel.add(createMenuButton("Sair", "SAIR"));

		// Criação do menu suspenso (popup menu)
		JPopupMenu menu = new JPopupMenu();
		menu.add(menuPanel);
		menu.setBorder(new LineBorder(new Color(128, 0, 255), 2)); // Borda roxa

		// Adiciona o MouseListener para exibir o menu ao clicar no JLabel "Menu"
		lblMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				menu.show(lblMenu, e.getX(), e.getY());
			}
		});

		// Configuração do JLabel "Eventos Disponíveis para Apostar"
		JLabel lblGerenciamentoEventos = new JLabel("Eventos Disponíveis para Apostar");
		lblGerenciamentoEventos.setFont(new Font("Tw Cen MT", Font.BOLD, 30));
		lblGerenciamentoEventos.setForeground(new Color(128, 0, 255));
		GridBagConstraints gbc_lblGerenciamentoEventos = new GridBagConstraints();
		gbc_lblGerenciamentoEventos.gridx = 0;
		gbc_lblGerenciamentoEventos.gridy = 1;
		gbc_lblGerenciamentoEventos.gridwidth = GridBagConstraints.REMAINDER;
		gbc_lblGerenciamentoEventos.insets = new Insets(10, 0, 10, 0);
		gbc_lblGerenciamentoEventos.anchor = GridBagConstraints.CENTER;
		newHome.getContentPane().add(lblGerenciamentoEventos, gbc_lblGerenciamentoEventos);

		// Configuração da tabela
		String[] columnNames = {
				"NOME", "CASA", "FORA", "ODD CASA", "ODD FORA", "ODD EMPATE",
				"DESCRIÇÃO", "DATA DO JOGO", "APOSTAR", "ID"
		};
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);
		JTable table = new JTable(model);
		table.setForeground(new Color(0, 0, 0));

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		// Configurações de renderizador e editor de botão para a coluna "Apostar"
		TableColumn buttonColumn = table.getColumnModel().getColumn(8);
		buttonColumn.setCellRenderer(new ButtonRenderer());
		buttonColumn.setCellEditor(new ButtonEditor(new JCheckBox(), events, table, _serviceEvent));

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
		columnModel.getColumn(8).setPreferredWidth(10); // Apostar

		// Ocultar a coluna ID
		TableColumn idColumn = table.getColumnModel().getColumn(9);
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
		newHome.getContentPane().add(scrollPane, gbc_scrollPane);

		JPanel footerPanel = new JPanel();
		footerPanel.setLayout(new GridBagLayout());
		footerPanel.setBackground(new Color(0, 0, 0));

		GridBagConstraints gbc_footerPanel = new GridBagConstraints();
		gbc_footerPanel.gridx = 0;
		gbc_footerPanel.gridy = 3;
		gbc_footerPanel.gridwidth = GridBagConstraints.REMAINDER;
		gbc_footerPanel.fill = GridBagConstraints.HORIZONTAL;
		newHome.getContentPane().add(footerPanel, gbc_footerPanel);

		JLabel copyrightFooter = new JLabel("Desenvolvido por os TRÊS JAVEIROS");
		copyrightFooter.setFont(new Font("Tahoma", Font.BOLD, 11));
		copyrightFooter.setForeground(Color.WHITE);
		GridBagConstraints gbc_copyrightFooter = new GridBagConstraints();
		gbc_copyrightFooter.gridx = 0;
		gbc_copyrightFooter.gridy = 0;
		gbc_copyrightFooter.insets = new Insets(5, 0, 5, 0);
		footerPanel.add(copyrightFooter, gbc_copyrightFooter);

		JLabel footerTitle = new JLabel("Todos os direitos reservados");
		footerTitle.setFont(new Font("Tahoma", Font.BOLD, 11));
		footerTitle.setForeground(Color.WHITE);
		GridBagConstraints gbc_footerTitle = new GridBagConstraints();
		gbc_footerTitle.gridx = 0;
		gbc_footerTitle.gridy = 1;
		gbc_footerTitle.insets = new Insets(5, 0, 5, 0);
		footerPanel.add(footerTitle, gbc_footerTitle);
		addRowsToTable(model, events);
	}

	public JPanel createSeparator() {
		JPanel separator = new JPanel();
		separator.setPreferredSize(new Dimension(250, 1));
		separator.setBackground(new Color(200, 200, 200)); // Cor da linha separadora
		separator.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0)); // Espaçamento
		return separator;
	}

	// Método auxiliar para criar botões no menu
	private JButton createMenuButton(String text, String actionCommand) {
		JButton button = new JButton(text);
		button.setBackground(Color.WHITE);
		button.setForeground(Color.BLACK);
		button.setFont(new Font("Tw Cen MT", Font.PLAIN, 20));
		button.setBorder(null); // Remove a borda
		button.setFocusPainted(false);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setActionCommand(actionCommand); // Define o ActionCommand

		button.addActionListener(e -> {
			String command = e.getActionCommand();
			switch (command) {
				case "MINHA_CONTA":
					changeProfile();
					break;
				case "GERENCIAR_APOSTAS":
					redirectGerenciarApostasFrame();
					break;
				case "HISTORICO_APOSTAS":
					redirectBetHistoryFrame();
					break;
				case "MINHAS_APOSTAS":
					redirectMyBetsFrame();
					break;
				case "BANCA":
					setBalanceValues();
					break;
				case "SAIR":
					logOut();
					break;
			}
		});
		return button;
	}

	private void redirectBetHistoryFrame() {
		BetHistory history = new BetHistory();
		history.setVisible(true);
		newHome.dispose();
		
	}

	public void redirectBetsFrame() {
		try {
			UserService user = new UserService();
			User userFound = user.getUserById(CurrentUser.getId());

			if (events != null) {
				/*
				 * MyBetsFrame betsFrame = new MyBetsFrame(userFound, events);
				 * betsFrame.setVisible(true);
				 */
			} else {
				MyBetsFrame betsFrame = new MyBetsFrame(userFound);
				betsFrame.setVisible(true);
			}

			newHome.dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void redirectMyBetsFrame() {
		NewMyBets mybets = new NewMyBets();
		mybets.setVisible(true);
		newHome.dispose();
	}

	public void redirectGerenciarApostasFrame() {
		try {
			GerenciarApostasFrame gerenciarApostasFrame = new GerenciarApostasFrame();
			gerenciarApostasFrame.setVisible(true);
			newHome.dispose();

		} catch (Exception ex) {
			throw ex;
		}
	}

	public void setBalanceValues() {
		JDialog dialog = new JDialog((Frame) null, "Banca", true);
		User user = new User();

		JPanel panel_1 = new JPanel(new GridBagLayout());
		panel_1.setBackground(new Color(128, 0, 255));

		// Configuração do GridBagConstraints
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10); // Margens internas
		gbc.anchor = GridBagConstraints.WEST; // Alinhamento à esquerda

		// Adiciona o JLabel para Saldo
		gbc.gridx = 0;
		gbc.gridy = 0;
		JLabel balanceText = new JLabel("Saldo: ");
		balanceText.setForeground(Color.WHITE);
		balanceText.setFont(new Font("Tw Cen MT", Font.BOLD, 16));
		panel_1.add(balanceText, gbc);

		// Adiciona o JLabel para o valor do saldo
		gbc.gridx = 1;
		JLabel balanceTextValue = new JLabel(CurrentUser.getBalance().toString());
		balanceTextValue.setForeground(Color.WHITE);
		balanceTextValue.setFont(new Font("Tw Cen MT", Font.BOLD, 16));
		panel_1.add(balanceTextValue, gbc);

		// Adiciona o JLabel para Quantia
		gbc.gridx = 0;
		gbc.gridy = 1;
		JLabel amount = new JLabel("Quantia:");
		amount.setForeground(Color.WHITE);
		amount.setFont(new Font("Tw Cen MT", Font.BOLD, 16));
		panel_1.add(amount, gbc);

		// Adiciona o JTextField para entrada de valor
		gbc.gridx = 1;
		JTextField textField_1 = new JTextField(20);
		panel_1.add(textField_1, gbc);

		// Adiciona os botões de Saque e Depósito
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2; // Ocupa duas colunas
		gbc.fill = GridBagConstraints.HORIZONTAL; // Preenche a largura
		gbc.weightx = 1.0;

		JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));

		JButton btnBalance = new JButton("Sacar");
		btnBalance.setFont(new Font("Tw Cen MT", Font.BOLD, 16));
		buttonPanel.add(btnBalance);

		JButton btnDeposit = new JButton("Depositar");
		btnDeposit.setFont(new Font("Tw Cen MT", Font.BOLD, 16));
		buttonPanel.add(btnDeposit);

		panel_1.add(buttonPanel, gbc);

		btnBalance.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String amountToSac = textField_1.getText();

					Boolean isConfirmed = confirm("Deseja sacar o valor?", "Banca");

					if (isConfirmed) {
						if (Double.parseDouble(amountToSac) > CurrentUser.getBalance()) {
							JOptionPane.showMessageDialog(null, "Valor Inválido");
						} else {
							_serviceUser.sacMoneyUserById(CurrentUser.getId(), Double.parseDouble(amountToSac));
							CurrentUser.setBalance(CurrentUser.getBalance() - Double.parseDouble(amountToSac));
							saldoValor.setText(CurrentUser.getBalance().toString());
							JOptionPane.showMessageDialog(null, "Saque realizado com sucesso!");

							dialog.setVisible(false);
						}

					}

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Falha ao sacar!");
					throw ex;
				}
			}
		});

		btnDeposit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String amountToDeposit = textField_1.getText();
					Boolean isConfirmed = confirm("Deseja depositar o valor?", "Banca");

					if (isConfirmed) {
						if (Double.parseDouble(amountToDeposit) == 0) {
							JOptionPane.showMessageDialog(null, "O valor precisa ser maior que zero!");
						} else {
							_serviceUser.depositMoneyUserById(CurrentUser.getId(), Double.parseDouble(amountToDeposit));
							CurrentUser.setBalance(CurrentUser.getBalance() + Double.parseDouble(amountToDeposit));
							saldoValor.setText(CurrentUser.getBalance().toString());

							JOptionPane.showMessageDialog(null, "Depósito realizado com sucesso!");

							dialog.setVisible(false);
						}

					}

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Falha ao depositar!");
					throw ex;
				}
			}
		});

		dialog.getContentPane().add(panel_1);
		dialog.setSize(500, 500); // Define o tamanho do dialog
		dialog.setLocationRelativeTo(newHome);
		dialog.setVisible(true);
	}

	public boolean confirm(String title, String type) {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBackground(new Color(153, 0, 204));

		// Configuração do GridBagConstraints
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10); // Margens internas
		gbc.anchor = GridBagConstraints.CENTER; // Alinhamento no centro
		gbc.fill = GridBagConstraints.HORIZONTAL; // Preenche a largura

		// Adiciona o JLabel para Título
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		JLabel logOutTitle = new JLabel(title);
		logOutTitle.setFont(new Font("Tw Cen MT", Font.BOLD, 14));
		logOutTitle.setForeground(Color.WHITE);
		panel.add(logOutTitle, gbc);

		// Adiciona o JLabel para Aceitar
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		JLabel acceptLogout = new JLabel("Sim");
		acceptLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		acceptLogout.setForeground(Color.WHITE);
		panel.add(acceptLogout, gbc);

		// Adiciona o JLabel para Negar
		gbc.gridx = 1;
		JLabel denyLogout = new JLabel("Não");
		denyLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		denyLogout.setForeground(Color.WHITE);
		panel.add(denyLogout, gbc);

		AtomicBoolean isConfirmed = new AtomicBoolean(false);

		acceptLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				isConfirmed.set(true);
				Window window = SwingUtilities.getWindowAncestor(panel);
				if (window != null) {
					window.setVisible(false);
				}
			}
		});

		denyLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Window window = SwingUtilities.getWindowAncestor(panel);
				if (window != null) {
					window.setVisible(false);
				}
			}
		});

		JDialog dialog = new JDialog((Frame) null, type, true);
		dialog.getContentPane().add(panel);
		dialog.setSize(250, 120); // Define o tamanho do dialog
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);

		return isConfirmed.get();
	}

	public void changeProfile() {
		JDialog dialog = new JDialog((Frame) null, "Perfil", true);

		// Configuração do JPanel com GridBagLayout
		JPanel profile = new JPanel(new GridBagLayout());
		profile.setBackground(new Color(0, 0, 0)); // Fundo preto
		profile.setPreferredSize(new Dimension(800, 400)); // Define o tamanho preferido para o JPanel

		// Configuração do GridBagConstraints
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10); // Margens internas
		gbc.anchor = GridBagConstraints.WEST; // Alinhamento à esquerda
		gbc.fill = GridBagConstraints.HORIZONTAL; // Preenche a largura
		gbc.weightx = 1.0; // Permite expansão horizontal

		// Adiciona o JLabel para Título
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.NONE; // Não preencher horizontalmente
		JLabel mainTitleProfile = new JLabel("Alterar Perfil");
		mainTitleProfile.setFont(new Font("Tw Cen MT", Font.BOLD, 24)); // Fonte maior e negrito
		mainTitleProfile.setForeground(new Color(128, 0, 255));
		profile.add(mainTitleProfile, gbc);

		// Adiciona o JLabel para Nome
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL; // Preenche a largura
		JLabel username = new JLabel("Nome:");
		username.setFont(new Font("Tw Cen MT", Font.BOLD, 16)); // Fonte negrito
		username.setForeground(new Color(128, 0, 255)); // Lavanda claro para contraste
		profile.add(username, gbc);

		// Adiciona o JTextField para Nome
		gbc.gridx = 1;
		JTextField textField = new JTextField();
		textField.setFont(new Font("Tw Cen MT", Font.PLAIN, 16)); // Ajusta o tamanho da fonte
		profile.add(textField, gbc);

		// Adiciona o JLabel para Senha Atual
		gbc.gridx = 0;
		gbc.gridy = 2;
		JLabel currentPassword = new JLabel("Senha atual:");
		currentPassword.setFont(new Font("Tw Cen MT", Font.BOLD, 16)); // Fonte negrito
		currentPassword.setForeground(new Color(128, 0, 255)); // Lavanda claro
		profile.add(currentPassword, gbc);

		// Adiciona o JPasswordField para Senha Atual
		gbc.gridx = 1;
		JPasswordField passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tw Cen MT", Font.PLAIN, 16)); // Ajusta o tamanho da fonte
		profile.add(passwordField, gbc);

		// Adiciona o JLabel para Nova Senha
		gbc.gridx = 0;
		gbc.gridy = 3;
		JLabel newPassword = new JLabel("Nova senha:");
		newPassword.setFont(new Font("Tw Cen MT", Font.BOLD, 16)); // Fonte negrito
		newPassword.setForeground(new Color(128, 0, 255)); // Lavanda claro
		profile.add(newPassword, gbc);

		// Adiciona o JPasswordField para Nova Senha
		gbc.gridx = 1;
		JPasswordField passwordField_1 = new JPasswordField();
		passwordField_1.setFont(new Font("Tw Cen MT", Font.PLAIN, 16)); // Ajusta o tamanho da fonte
		profile.add(passwordField_1, gbc);

		// Adiciona o JLabel para Repetir Nova Senha
		gbc.gridx = 0;
		gbc.gridy = 4;
		JLabel repeatNewPassword_1 = new JLabel("Repita nova senha:");
		repeatNewPassword_1.setFont(new Font("Tw Cen MT", Font.BOLD, 16)); // Fonte negrito
		repeatNewPassword_1.setForeground(new Color(128, 0, 255)); // Lavanda claro
		profile.add(repeatNewPassword_1, gbc);

		// Adiciona o JPasswordField para Repetir Nova Senha
		gbc.gridx = 1;
		JPasswordField repeatPassword = new JPasswordField();
		repeatPassword.setFont(new Font("Tw Cen MT", Font.PLAIN, 16)); // Ajusta o tamanho da fonte
		profile.add(repeatPassword, gbc);

		// Adiciona os botões
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL; // Preenche a largura

		JButton btnDeleteUser = new JButton("Deletar");
		btnDeleteUser.setFont(new Font("Tw Cen MT", Font.BOLD, 16)); // Fonte negrito
		btnDeleteUser.setForeground(Color.WHITE);
		btnDeleteUser.setBackground(new Color(139, 0, 0)); // Vermelho escuro
		profile.add(btnDeleteUser, gbc);

		gbc.gridx = 1;
		JButton btnSaveChanges = new JButton("Salvar");
		btnSaveChanges.setFont(new Font("Tw Cen MT", Font.BOLD, 16)); // Fonte negrito
		btnSaveChanges.setForeground(Color.WHITE);
		btnSaveChanges.setBackground(new Color(34, 139, 34)); // Verde escuro
		profile.add(btnSaveChanges, gbc);

		// Adiciona o painel ao diálogo
		dialog.getContentPane().add(profile);
		dialog.pack(); // Ajusta o tamanho do dialog ao conteúdo
		dialog.setLocationRelativeTo(null); // Centraliza o diálogo
		dialog.setVisible(true);

		// Adiciona ação ao botão Deletar
		btnDeleteUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Boolean isConfirmed = confirm("Deseja deletar a conta?", "Deletar");

					if (isConfirmed) {
						_serviceUser.deleteUser(CurrentUser.getId());

						JOptionPane.showMessageDialog(null, "Deletado com sucesso!");
						profile.setVisible(false);
						dialog.setVisible(false);
						LoginFrame loginFrame = new LoginFrame();
						loginFrame.setVisible(true);
						newHome.dispose();
					}

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Falha ao deletar!");
					throw ex;
				}
			}
		});

		// Adiciona ação ao botão Salvar
		btnSaveChanges.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				User user = new User();

				String changedUsername = textField.getText().trim();
				String repeatedPassword = new String(repeatPassword.getPassword()).trim();
				String newPassword = new String(passwordField_1.getPassword()).trim();

				if (changedUsername.isEmpty()) {
					JOptionPane.showMessageDialog(null, "O nome de usuário não pode estar vazio.");
					return;
				}

				if (newPassword.isEmpty() || repeatedPassword.isEmpty()) {
					JOptionPane.showMessageDialog(null, "As senhas não podem estar vazias.");
					return;
				}

				if (newPassword.length() < 8) {
					JOptionPane.showMessageDialog(null, "A senha deve ter pelo menos 8 caracteres.");
					return;
				}

				String validatePasswordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

				if (!newPassword.matches(validatePasswordRegex)) {
					JOptionPane.showMessageDialog(null,
							"A senha deve conter pelo menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial.");
					return;
				}

				if (!repeatedPassword.equals(newPassword)) {
					JOptionPane.showMessageDialog(null, "As senhas não coincidem.");
					return;
				}

				try {
					user.setId(CurrentUser.getId());
					user.setBalance(CurrentUser.getBalance());
					user.setEmail(CurrentUser.getEmail());
					user.setName(changedUsername);
					user.setPassword(newPassword);
					_serviceUser.updateUser(user);
					CurrentUser.setUsername(changedUsername);
					lblAdmin.setText(CurrentUser.getUsername());
					JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso!");

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Falha ao alterar dados!");
					throw ex;
				}
			}
		});

	}

	public void logOut() {
		Boolean isConfirmed = this.confirm("Deseja sair do sistema?", "Sair");

		if (isConfirmed) {
			CurrentUser.setAccessToken(null);
			newHome.dispose();

			LoginFrame loginFrame = new LoginFrame();
			loginFrame.setVisible(true);
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
			if (label.equals("Apostar"))
				addEventToList(event, row);

		}

		private void addEventToList(Event event, int row) {
			SwingUtilities.invokeLater(() -> {
				// Cria campos para exibir as informações do evento
				JTextField nameField = new JTextField(event.getName());
				nameField.setEditable(false);

				JTextField homeTeamField = new JTextField(event.getHomeTeam());
				homeTeamField.setEditable(false);

				JTextField awayTeamField = new JTextField(event.getAwayTeam());
				awayTeamField.setEditable(false);

				JTextField homeOddsField = new JTextField(Double.toString(event.getHomeOdds()));
				homeOddsField.setEditable(false);
				JTextField awayOddsField = new JTextField(Double.toString(event.getAwayOdds()));
				awayOddsField.setEditable(false);
				JTextField drawOddsField = new JTextField(Double.toString(event.getDrawOdds()));
				drawOddsField.setEditable(false);

				JTextField descriptionField = new JTextField(event.getDescription());
				descriptionField.setEditable(false);

				JTextField dateField = new JTextField(
						event.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
				dateField.setEditable(false);

				// Adiciona opções de aposta
				JPanel bettingOptionsPanel = new JPanel(new GridLayout(3, 1));
				JRadioButton homeButton = new JRadioButton("Casa");
				JRadioButton awayButton = new JRadioButton("Fora");
				JRadioButton drawButton = new JRadioButton("Empate");
				ButtonGroup group = new ButtonGroup();
				group.add(homeButton);
				group.add(awayButton);
				group.add(drawButton);

				bettingOptionsPanel.add(homeButton);
				bettingOptionsPanel.add(awayButton);
				bettingOptionsPanel.add(drawButton);

				// Configura o painel de edição
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
				panel.add(new JLabel("Escolha onde apostar:"));
				panel.add(bettingOptionsPanel);

				int result = JOptionPane.showConfirmDialog(null, panel, "Selecionar Evento",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

				if (result == JOptionPane.OK_OPTION) {
					// Valida se uma opção foi selecionada
					if (!homeButton.isSelected() && !awayButton.isSelected() && !drawButton.isSelected()) {
						JOptionPane.showMessageDialog(null,
								"Você deve selecionar uma opção de aposta antes de adicionar o evento.",
								"Erro", JOptionPane.ERROR_MESSAGE);
						return; // Sai do método se nenhuma opção estiver selecionada
					}

					// Confirmação antes de adicionar o evento à lista
					int confirmResult = JOptionPane.showConfirmDialog(null,
							"Tem certeza de que deseja adicionar este evento em suas apostas?",
							"Confirmação", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

					if (confirmResult == JOptionPane.YES_OPTION) {
						try {
							// Determina a opção selecionada
							String selectedOption = null;
							if (homeButton.isSelected()) {
								selectedOption = "Casa";
							} else if (awayButton.isSelected()) {
								selectedOption = "Fora";
							} else if (drawButton.isSelected()) {
								selectedOption = "Empate";
							}

							// Define a opção no evento
							event.setOption(selectedOption);

							// Adiciona o evento à lista temporária
							CurrentUser.putEventsFromList(event);

							// Exibe uma mensagem de sucesso
							JOptionPane.showMessageDialog(null,
									"Evento adicionado à lista com sucesso! Vá até a aba Gerenciar Apostas no menu para confirmar sua aposta!",
									"Sucesso", JOptionPane.INFORMATION_MESSAGE);

						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, "Erro ao adicionar o evento: " + ex.getMessage(),
									"Erro", JOptionPane.ERROR_MESSAGE);
						}
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
}
