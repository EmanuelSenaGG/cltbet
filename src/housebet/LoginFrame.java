package housebet;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import housebet.Styles.RoundedPanelBorder;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import services.UserService;
import utils.Global.CurrentUser;

import java.util.ArrayList;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginFrame {

	private UserService _userService = new UserService();
	private JFrame frameLogin;
	private JTextField textFieldEmail = new JTextField();
	private JPasswordField textFieldSenha = new JPasswordField();

	public LoginFrame() {
		frameLogin = new JFrame();
		initialize();
		frameLogin.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frameLogin.setUndecorated(false);

	}

	public void setVisible(boolean visible) {
		frameLogin.setVisible(visible);
	}

	private void redirectCadastrarFrame() {

		CadastrarFrame cadastrarFrame = new CadastrarFrame();
		cadastrarFrame.setVisible(true);
		frameLogin.dispose();

	}

	private List<String> formIsValid() {
		List<String> erros = new ArrayList<>();
		char[] senha = textFieldSenha.getPassword();

		if (!textFieldEmail.getText().equals("admin") && !new String(textFieldSenha.getPassword()).equals("admin")) {
			if (textFieldEmail.getText().isEmpty()) {
				erros.add("O campo e-mail é obrigatório.");
			} else {
				String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
				Pattern pattern = Pattern.compile(emailRegex);
				Matcher matcher = pattern.matcher(textFieldEmail.getText());
				if (!matcher.matches()) {
					erros.add("O e-mail fornecido é inválido.");
				}
			}

			if (senha.length == 0) {
				erros.add("O campo senha é obrigatório");
			}
		}

		return erros;
	}

	private void loginUser() {
		try {
			if (textFieldEmail.getText().equals("admin") && new String(textFieldSenha.getPassword()).equals("admin")) {
				// Execute o redirecionamento na EDT
				SwingUtilities.invokeLater(() -> redirectAdminHomeFrame());
			} else {
				boolean result = _userService.login(textFieldEmail.getText(), new String(textFieldSenha.getPassword()));
				if (result) {
					// Execute o redirecionamento na EDT
					SwingUtilities.invokeLater(() -> redirectHomeFrame());
				} else {
					JOptionPane.showMessageDialog(frameLogin,
							"Email ou Senha incorretos, verifique suas credenciais e tente novamente.",
							"Erro ao Logar", JOptionPane.ERROR_MESSAGE);
				}
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(frameLogin, ex.getMessage(), "Erro ao Logar", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void redirectHomeFrame() {
		// Execute a lógica de redirecionamento na EDT
		SwingUtilities.invokeLater(() -> {
			NewHome newHome = new NewHome();
			newHome.setVisible(true);
			frameLogin.dispose(); 
		});
	}

	private void redirectAdminHomeFrame() {
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
			}finally{
				loadingFrame.dispose(); // Fecha o frame de loading
				frameLogin.dispose();
			}
		}).start();
	
	}

	private void initialize() {

		frameLogin.getContentPane().setBackground(new Color(0, 0, 0));
		frameLogin.setTitle("CLTBET");
		frameLogin.setSize(1280, 720);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0 };
		frameLogin.getContentPane().setLayout(gridBagLayout);

		GridBagConstraints gbcLabel = new GridBagConstraints();
		gbcLabel.gridwidth = GridBagConstraints.REMAINDER;
		gbcLabel.gridx = 0;
		gbcLabel.gridy = 0;
		gbcLabel.anchor = GridBagConstraints.NORTH;
		gbcLabel.weightx = 1.0;

		JLabel lbCLTBET = new JLabel("CLTBET");
		lbCLTBET.setForeground(new Color(128, 0, 255));
		lbCLTBET.setFont(new Font("Tw Cen MT", Font.BOLD, 68));
		lbCLTBET.setHorizontalAlignment(SwingConstants.CENTER);
		frameLogin.getContentPane().add(lbCLTBET, gbcLabel);

		GridBagConstraints gbcPanel = new GridBagConstraints();
		gbcPanel.gridwidth = GridBagConstraints.REMAINDER;
		gbcPanel.gridx = 0;
		gbcPanel.gridy = 1;
		gbcPanel.anchor = GridBagConstraints.CENTER;
		gbcPanel.fill = GridBagConstraints.NONE;
		gbcPanel.weightx = 1.0;
		gbcPanel.weighty = 1.0;

		JPanel pnlLogin = new JPanel();
		pnlLogin.setPreferredSize(new Dimension(500, 600));
		pnlLogin.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		pnlLogin.setBackground(new Color(128, 0, 255));
		pnlLogin.setBorder(new RoundedPanelBorder(new Color(255, 255, 255), 2, 20));
		pnlLogin.setLayout(null);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setForeground(new Color(0, 0, 0));
		lblEmail.setBounds(31, 122, 76, 20);
		lblEmail.setBackground(new Color(255, 255, 255));
		lblEmail.setFont(new Font("Tw Cen MT", Font.BOLD, 25));
		pnlLogin.add(lblEmail);

		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setForeground(new Color(0, 0, 0));
		lblSenha.setBounds(31, 218, 76, 26);
		lblSenha.setBackground(new Color(0, 0, 0));
		lblSenha.setFont(new Font("Tw Cen MT", Font.BOLD, 25));
		pnlLogin.add(lblSenha);

		textFieldEmail.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldEmail.setBounds(31, 153, 443, 40);
		textFieldEmail.setFont(new Font("Tw Cen MT", Font.BOLD, 18));
		textFieldEmail.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		pnlLogin.add(textFieldEmail);
		textFieldEmail.setColumns(10);

		textFieldSenha.setBounds(31, 256, 359, 38);
		textFieldSenha.setBackground(new Color(255, 255, 255));
		textFieldSenha.setFont(new Font("Tw Cen MT", Font.BOLD, 18));
		textFieldSenha.setColumns(10);
		textFieldSenha.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		textFieldSenha.setEchoChar('*');
		pnlLogin.add(textFieldSenha);

		JButton btnMostrarSenha = new JButton("Mostrar");
		btnMostrarSenha.setBounds(400, 255, 74, 38);
		btnMostrarSenha.setForeground(new Color(0, 0, 0));
		btnMostrarSenha.setBackground(new Color(255, 255, 255));
		btnMostrarSenha.setFont(new Font("Tw Cen MT", Font.BOLD, 18));
		btnMostrarSenha.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		btnMostrarSenha.setFocusPainted(false);
		btnMostrarSenha.setContentAreaFilled(false);
		btnMostrarSenha.setOpaque(true);
		pnlLogin.add(btnMostrarSenha);

		JButton btnLogar = new JButton("Logar");
		btnLogar.setBounds(163, 358, 163, 38);
		btnLogar.setForeground(new Color(0, 0, 0));
		btnLogar.setBackground(new Color(255, 255, 255));
		btnLogar.setFont(new Font("Tw Cen MT", Font.BOLD, 20));
		btnLogar.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		btnLogar.setFocusPainted(false);
		btnLogar.setContentAreaFilled(false);
		btnLogar.setOpaque(true);
		pnlLogin.add(btnLogar);

		JButton btnCadastrar = new JButton("Criar Conta");
		btnCadastrar.setBounds(163, 434, 163, 38);
		btnCadastrar.setForeground(new Color(0, 0, 0));
		btnCadastrar.setBackground(new Color(255, 255, 255));
		btnCadastrar.setFont(new Font("Tw Cen MT", Font.BOLD, 20));
		btnCadastrar.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		btnCadastrar.setFocusPainted(false);
		btnCadastrar.setContentAreaFilled(false);
		btnCadastrar.setOpaque(true);
		pnlLogin.add(btnCadastrar);

		frameLogin.getContentPane().add(pnlLogin, gbcPanel);

		JLabel lblEntreAgora = new JLabel("Faça a sua Aposta!");
		lblEntreAgora.setForeground(Color.BLACK);
		lblEntreAgora.setFont(new Font("Tw Cen MT", Font.BOLD, 36));
		lblEntreAgora.setBackground(Color.WHITE);
		lblEntreAgora.setBounds(106, 21, 310, 38);
		pnlLogin.add(lblEntreAgora);

		btnMostrarSenha.addActionListener(new ActionListener() {
			private boolean mostrarSenha = false;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (mostrarSenha) {
					textFieldSenha.setEchoChar('*');
					btnMostrarSenha.setText("Mostrar");
				} else {
					textFieldSenha.setEchoChar((char) 0);
					btnMostrarSenha.setText("Ocultar");
				}
				mostrarSenha = !mostrarSenha;
			}
		});

		btnLogar.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				btnLogar.setBackground(Color.LIGHT_GRAY);
			}

			public void mouseReleased(java.awt.event.MouseEvent evt) {
				btnLogar.setBackground(new Color(255, 255, 255));

				List<String> erros = formIsValid();
				if (!erros.isEmpty()) {

					StringBuilder mensagemErro = new StringBuilder("Erros no preenchimento do formulário:\n");
					for (String erro : erros) {
						mensagemErro.append("- ").append(erro).append("\n");
					}

					JOptionPane.showMessageDialog(frameLogin, mensagemErro.toString(), "Campos Inválidos",
							JOptionPane.ERROR_MESSAGE);
				} else
					loginUser();
			}
		});

		btnCadastrar.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				btnCadastrar.setBackground(Color.LIGHT_GRAY);
			}

			public void mouseReleased(java.awt.event.MouseEvent evt) {
				btnCadastrar.setBackground(new Color(255, 255, 255));

				redirectCadastrarFrame();

			}
		});

		btnMostrarSenha.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				btnMostrarSenha.setBackground(Color.LIGHT_GRAY);
			}

			public void mouseReleased(java.awt.event.MouseEvent evt) {
				btnMostrarSenha.setBackground(new Color(255, 255, 255));
			}
		});

	}
}
