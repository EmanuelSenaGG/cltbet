package housebet;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import housebet.Styles.RoundedPanelBorder;
import services.UserService;
import model.User;

public class CadastrarFrame {
	private JFrame frameCadastrar;
	private JTextField textFieldEmail = new JTextField();;
	private JPasswordField textFieldSenha = new JPasswordField();;
	private JPasswordField textFieldConfirmarSenha = new JPasswordField();;
	private JTextField textFieldNome = new JTextField();;
	private UserService _userService = new UserService();

	public CadastrarFrame() {
	    frameCadastrar = new JFrame();
		initialize();
        
        frameCadastrar.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frameCadastrar.setUndecorated(false);
	}
	
	public void setVisible(boolean isvisible) {
		frameCadastrar.setVisible(isvisible);
	}
	
	private List<String> formIsValid() {
	    List<String> erros = new ArrayList<>();
	    char[] senha =  textFieldSenha.getPassword();
        char[] confirmarSenha = textFieldConfirmarSenha.getPassword();
      
	    if (textFieldNome.getText().isEmpty()) {
	        erros.add("O campo nome é obrigatório.");
	    } else if(textFieldNome.getText().length() < 10) {
	        erros.add("O campo nome deve conter no mínimo 10 caracteres.");
	    }
	    
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
	    
	    if(senha.length == 0 || confirmarSenha.length == 0) {
	    	erros.add("Os campos de senha são obrigatórios");
	    } else if (!Arrays.equals(senha, confirmarSenha)) {
	        erros.add("As senhas devem ser iguais.");	         
        }  
	  
	    return erros;
	}

	private boolean registerUser(User user) {
		try {
			_userService.insertUser(user);
			return true;
		}
		catch(Exception ex) {
			return false;
		}
	}

	private User getFormUser() {
		User user = new User();
		user.setBalance(0.0);
		user.setName(textFieldNome.getText());
		user.setEmail(textFieldEmail.getText());
		user.setPassword(new String (textFieldConfirmarSenha.getPassword()));
		return user;
	}
	
	private void redirectLoginFrame() {
		LoginFrame loginFrame = new LoginFrame();
		loginFrame.setVisible(true);
	}
	
	private void initialize() {
	    frameCadastrar.getContentPane().setBackground(new Color(0, 0, 0));
	    frameCadastrar.setTitle("CLTBET");
	    frameCadastrar.setSize(1280, 720); 
	    frameCadastrar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    GridBagLayout gridBagLayout = new GridBagLayout();
	    gridBagLayout.rowWeights = new double[]{0.0, 0.0};
	    frameCadastrar.getContentPane().setLayout(gridBagLayout);

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
	    frameCadastrar.getContentPane().add(lbCLTBET, gbcLabel);

	    GridBagConstraints gbcPanel = new GridBagConstraints();
	    gbcPanel.gridwidth = GridBagConstraints.REMAINDER;
	    gbcPanel.gridx = 0;
	    gbcPanel.gridy = 1;
	    gbcPanel.anchor = GridBagConstraints.CENTER; 
	    gbcPanel.fill = GridBagConstraints.NONE; 
	    gbcPanel.weightx = 1.0; 
	    gbcPanel.weighty = 1.0; 

	    JPanel pnlLogin = new JPanel();
	    pnlLogin.setPreferredSize(new Dimension(500, 800)); 
	    pnlLogin.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2)); 
	    pnlLogin.setBackground(new Color(128, 0, 255));
	    pnlLogin.setBorder(new RoundedPanelBorder(new Color(255, 255, 255), 2, 20));
	    pnlLogin.setLayout(null); 

	    JLabel lblEmail = new JLabel("Email");
	    lblEmail.setForeground(new Color(0, 0, 0));
	    lblEmail.setBounds(32, 236, 76, 20);
	    lblEmail.setBackground(new Color(255, 255, 255));
	    lblEmail.setFont(new Font("Tw Cen MT", Font.BOLD, 25));
	    pnlLogin.add(lblEmail);

	    JLabel lblSenha = new JLabel("Senha");
	    lblSenha.setForeground(new Color(0, 0, 0));
	    lblSenha.setBounds(32, 359, 76, 26);
	    lblSenha.setBackground(new Color(0, 0, 0));
	    lblSenha.setFont(new Font("Tw Cen MT", Font.BOLD, 25));
	    pnlLogin.add(lblSenha);

	    textFieldEmail = new JTextField();
	    textFieldEmail.setHorizontalAlignment(SwingConstants.LEFT);
	    textFieldEmail.setBounds(32, 279, 432, 40);
	    textFieldEmail.setFont(new Font("Tw Cen MT", Font.BOLD, 18));
	    textFieldEmail.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
	    pnlLogin.add(textFieldEmail);
	    textFieldEmail.setColumns(10);

	    textFieldSenha = new JPasswordField();
	    textFieldSenha.setBounds(32, 396, 342, 38);
	    textFieldSenha.setBackground(new Color(255, 255, 255));
	    textFieldSenha.setFont(new Font("Tw Cen MT", Font.BOLD, 18));
	    textFieldSenha.setColumns(10);
	    textFieldSenha.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
	    textFieldSenha.setEchoChar('*'); 
	    pnlLogin.add(textFieldSenha);

	    JButton btnMostrarSenha = new JButton("Mostrar");
	    btnMostrarSenha.setBounds(384, 395, 80, 38); 
	    btnMostrarSenha.setForeground(new Color(0, 0, 0));
	    btnMostrarSenha.setBackground(new Color(255, 255, 255));
	    btnMostrarSenha.setFont(new Font("Tw Cen MT", Font.BOLD, 18));
	    btnMostrarSenha.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
	    btnMostrarSenha.setFocusPainted(false); 
	    btnMostrarSenha.setContentAreaFilled(false); 
	    btnMostrarSenha.setOpaque(true); 
	    pnlLogin.add(btnMostrarSenha);

	    frameCadastrar.getContentPane().add(pnlLogin, gbcPanel);
	    
	    JLabel lblCriarConta = new JLabel("Efetue seu Cadastro!");
	    lblCriarConta.setForeground(Color.BLACK);
	    lblCriarConta.setFont(new Font("Tw Cen MT", Font.BOLD, 36));
	    lblCriarConta.setBackground(Color.WHITE);
	    lblCriarConta.setBounds(106, 21, 310, 38);
	    pnlLogin.add(lblCriarConta);
	    
	    textFieldConfirmarSenha = new JPasswordField();
	    textFieldConfirmarSenha.setFont(new Font("Tw Cen MT", Font.BOLD, 18));
	    textFieldConfirmarSenha.setEchoChar('*');
	    textFieldConfirmarSenha.setColumns(10);
	    textFieldConfirmarSenha.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
	    textFieldConfirmarSenha.setBackground(Color.WHITE);
	    textFieldConfirmarSenha.setBounds(32, 510, 342, 38);
	    pnlLogin.add(textFieldConfirmarSenha);
	    
	    JLabel lblConfirmarSenha = new JLabel("Confirmar Senha");
	    lblConfirmarSenha.setForeground(Color.BLACK);
	    lblConfirmarSenha.setFont(new Font("Tw Cen MT", Font.BOLD, 25));
	    lblConfirmarSenha.setBackground(Color.BLACK);
	    lblConfirmarSenha.setBounds(32, 472, 216, 26);
	    pnlLogin.add(lblConfirmarSenha);
	    
	    JLabel lblNome = new JLabel("Nome");
	    lblNome.setForeground(Color.BLACK);
	    lblNome.setFont(new Font("Tw Cen MT", Font.BOLD, 25));
	    lblNome.setBackground(Color.WHITE);
	    lblNome.setBounds(32, 121, 76, 20);
	    pnlLogin.add(lblNome);
	    
	    textFieldNome = new JTextField();
	    textFieldNome.setHorizontalAlignment(SwingConstants.LEFT);
	    textFieldNome.setFont(new Font("Tw Cen MT", Font.BOLD, 18));
	    textFieldNome.setColumns(10);
	    textFieldNome.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
	    textFieldNome.setBounds(32, 168, 432, 40);
	    pnlLogin.add(textFieldNome);
	    
	    JButton btnMostrarSenha2 = new JButton("Mostrar");
	    btnMostrarSenha2.setBounds(384, 509, 80, 38); 
	    btnMostrarSenha2.setForeground(new Color(0, 0, 0));
	    btnMostrarSenha2.setBackground(new Color(255, 255, 255));
	    btnMostrarSenha2.setFont(new Font("Tw Cen MT", Font.BOLD, 18));
	    btnMostrarSenha2.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
	    btnMostrarSenha2.setFocusPainted(false); 
	    btnMostrarSenha2.setContentAreaFilled(false); 
	    btnMostrarSenha2.setOpaque(true); 
	    pnlLogin.add(btnMostrarSenha2);
	    
	    JButton btnCadastrar = new JButton("Criar Conta");
	    btnCadastrar.setBounds(165, 644, 163, 38);
	    btnCadastrar.setForeground(new Color(0, 0, 0));
	    btnCadastrar.setBackground(new Color(255, 255, 255));
	    btnCadastrar.setFont(new Font("Tw Cen MT", Font.BOLD, 20));
	    btnCadastrar.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
	    btnCadastrar.setFocusPainted(false); 
	    btnCadastrar.setContentAreaFilled(false); 
	    btnCadastrar.setOpaque(true); 
	    pnlLogin.add(btnCadastrar);
	    
	    JButton btnVoltar = new JButton("Voltar");
	    btnVoltar.setBounds(165, 712, 163, 38);
	    btnVoltar.setForeground(new Color(0, 0, 0));
	    btnVoltar.setBackground(new Color(255, 255, 255));
	    btnVoltar.setFont(new Font("Tw Cen MT", Font.BOLD, 20));
	    btnVoltar.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
	    btnVoltar.setFocusPainted(false);
	    btnVoltar.setContentAreaFilled(false); 
	    btnVoltar.setOpaque(true); 
	    pnlLogin.add(btnVoltar);

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

	    btnCadastrar.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mousePressed(java.awt.event.MouseEvent evt) {
	            btnCadastrar.setBackground(Color.LIGHT_GRAY);
	        }
	        public void mouseReleased(java.awt.event.MouseEvent evt) {
	            btnCadastrar.setBackground(new Color(255, 255, 255));

	          List<String> erros = formIsValid();
	          if (!erros.isEmpty()) {
	             
	              StringBuilder mensagemErro = new StringBuilder("Erros no preenchimento do formulário:\n");
	              for (String erro : erros) {
	                  mensagemErro.append("- ").append(erro).append("\n"); 
	              }
	            
	              JOptionPane.showMessageDialog(frameCadastrar, mensagemErro.toString(), "Campos Inválidos", JOptionPane.ERROR_MESSAGE);
	          }
	          else {
	        	 User user = getFormUser();
	        	 
	        	 boolean result = registerUser(user);
	        	 if(result) {
	        		 JOptionPane.showMessageDialog(frameCadastrar, "Cadastro realizado com sucesso", "Feito", JOptionPane.INFORMATION_MESSAGE);
	        		 redirectLoginFrame();
	 	        	 frameCadastrar.dispose();
	        	 }
	          }
	        
	        }
	    });
	    
	    btnVoltar.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mousePressed(java.awt.event.MouseEvent evt) {
	        	btnVoltar.setBackground(Color.LIGHT_GRAY);
	        }
	        public void mouseReleased(java.awt.event.MouseEvent evt) {
	        	btnVoltar.setBackground(new Color(255, 255, 255));
	        	
	        	redirectLoginFrame();
	        	frameCadastrar.dispose();
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

	    btnMostrarSenha2.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mousePressed(java.awt.event.MouseEvent evt) {
	            btnMostrarSenha2.setBackground(Color.LIGHT_GRAY);
	        }
	        public void mouseReleased(java.awt.event.MouseEvent evt) {
	            btnMostrarSenha2.setBackground(new Color(255, 255, 255));
	        }
	    });

	    btnMostrarSenha2.addActionListener(new ActionListener() {
	        private boolean mostrarSenha = false;

	        @Override
	        public void actionPerformed(ActionEvent e) {
	            if (mostrarSenha) {
	                textFieldConfirmarSenha.setEchoChar('*');
	                btnMostrarSenha2.setText("Mostrar");
	            } else {
	                textFieldConfirmarSenha.setEchoChar((char) 0);
	                btnMostrarSenha2.setText("Ocultar");
	            }
	            mostrarSenha = !mostrarSenha;
	        }
	    });
	}
}
