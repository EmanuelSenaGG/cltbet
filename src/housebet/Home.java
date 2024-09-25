package housebet;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Frame;
import java.awt.Window;
import java.awt.Color;
import java.awt.Component;

import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import model.Event;
import model.User;
import services.EventService;
import services.UserService;

import javax.swing.JEditorPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JPanel;

import utils.Global.CurrentUser;
import javax.swing.SwingUtilities;
import javax.swing.JDialog;
import java.awt.Cursor;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;

public class Home {
    private JFrame frame;
    private boolean menuVisible = false;
    private JEditorPane editorPane;
    private JSeparator[] separators = new JSeparator[5];
    private JLabel[] labels = new JLabel[6];
    private JTextField textField;
    private JPasswordField passwordField;
    private JPasswordField passwordField_1;
    private JPasswordField repeatPassword;
    private JTextField textField_1;
    
    EventService eventService = new EventService(); 
    public List<Event> events = eventService.getAllEvents();

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Home window = new Home();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Home() {
        initialize();
    }

    public void eventsScreen() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String[][] data = new String[events.size()][9];
        for (int i = 0; i < events.size(); i++) {
        	Event event = events.get(i);
        	
        	if (event.getIsActive()) {
                data[i][0] = event.getName();
                data[i][1] = event.getHomeTeam();
                data[i][2] = event.getAwayTeam();
                data[i][3] = String.valueOf(event.getHomeOdds());
                data[i][4] = String.valueOf(event.getAwayOdds());
                data[i][5] = String.valueOf(event.getDrawOdds());
                data[i][6] = event.getDescription();
                data[i][7] = event.getDate().format(formatter);
                data[i][8] = "Apostar";  // Adding the "Bet" button text
        	}  
        }

        String[] columnNames = {
            "NOME", "CASA", "FORA", "ODD CASA", "ODD FORA", "ODD EMPATE", "DESCRIÇÃO", "DATA", ""
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        JTable table = new JTable(model);

        // Set column widths
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(150); // Name column
        columnModel.getColumn(6).setPreferredWidth(160); // Description column
        columnModel.getColumn(7).setPreferredWidth(70); // Event Date column
        columnModel.getColumn(8).setPreferredWidth(100); // Action column (Bet button)

        // Add button renderer and editor for the "Bet" button
        TableColumn actionColumn = table.getColumnModel().getColumn(8);
        actionColumn.setCellRenderer(new ButtonRenderer());
                
                JPanel panel = new JPanel();
                panel.setBackground(new Color(128, 0, 255));
                panel.setBorder(null);
                panel.setBounds(1032, -4, 28, 33);
                frame.getContentPane().add(panel);
                panel.setLayout(null);
                
                        JSeparator item2MenuHamburguer = new JSeparator();
                        item2MenuHamburguer.setBounds(6, 20, 16, 7);
                        panel.add(item2MenuHamburguer);
                        item2MenuHamburguer.setForeground(UIManager.getColor("Button.foreground"));
                        item2MenuHamburguer.setBackground(new Color(0, 0, 0));
                        
                                JSeparator item1MenuHamburguer = new JSeparator();
                                item1MenuHamburguer.setBounds(6, 16, 16, 7);
                                panel.add(item1MenuHamburguer);
                                item1MenuHamburguer.setForeground(Color.BLACK);
                                item1MenuHamburguer.setBackground(Color.BLACK);
                                
                                        JSeparator item3MenuHamburguer = new JSeparator();
                                        item3MenuHamburguer.setBounds(6, 24, 16, 10);
                                        panel.add(item3MenuHamburguer);
                                        item3MenuHamburguer.setForeground(new Color(0, 0, 0));
                                        item3MenuHamburguer.setBackground(new Color(0, 0, 0));
                                        
                                                panel.addMouseListener(new MouseAdapter() {
                                                    @Override
                                                    public void mousePressed(MouseEvent e) {
                                                        if (menuVisible) {
                                                            removeMenu();
                                                        } else {
                                                            addMenu();
                                                        }
                                                        menuVisible = !menuVisible;
                                                        frame.repaint();
                                                    }
                                        
                                                    private void addMenu() {
                                                        int[][] separatorBounds = {
                                                            {948, 52, 106, 2},
                                                            {947, 78, 106, 2},
                                                            {948, 103, 106, 2},
                                                            {948, 131, 106, 2},
                                                            {948, 159, 106, 2},
                                                        };
                                                        String[] labelTexts = {"Banca", "Gerenciar Apostas", "Minhas Apostas", "Ver histórico", "Perfil", "Sair"};
                                                        int[] labelBounds = {33, 59, 84, 111, 140, 170};
                                        
                                                        for (int i = 0; i < separatorBounds.length; i++) {
                                                            separators[i] = new JSeparator();
                                                            separators[i].setBackground(new Color(204, 0, 204));
                                                            separators[i].setBounds(separatorBounds[i][0], separatorBounds[i][1], separatorBounds[i][2], separatorBounds[i][3]);
                                                            frame.getContentPane().add(separators[i]);
                                                        }
                                        
                                                        for (int i = 0; i < labelTexts.length; i++) {
                                                            labels[i] = new JLabel(labelTexts[i]);
                                                            labels[i].setFont(new Font("SansSerif", Font.BOLD, 9));
                                                            labels[i].setBounds(959, labelBounds[i], 92, 14);
                                                            frame.getContentPane().add(labels[i]);
                                                            
                                                            if (labelTexts[i].equals("Sair")) {
                                                                labels[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                                                labels[i].addMouseListener(new MouseAdapter() {
                                                                    @Override
                                                                    public void mousePressed(MouseEvent e) {
                                                                        logOut();
                                                                    }
                                                                });
                                                            }
                                                            
                                                            if (labelTexts[i].equals("Perfil")) {
                                                                labels[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                                                labels[i].addMouseListener(new MouseAdapter() {
                                                                    @Override
                                                                    public void mousePressed(MouseEvent e) {
                                                                        changeProfile();
                                                                    }
                                                                });
                                                            }
                                                            
                                                            if (labelTexts[i].equals("Banca")) {
                                                                labels[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                                                labels[i].addMouseListener(new MouseAdapter() {
                                                                    @Override
                                                                    public void mousePressed(MouseEvent e) {
                                                                        setBalanceValues();
                                                                    }
                                                                });
                                                            }
                                                            
                                                            if (labelTexts[i].equals("Gerenciar Apostas")) {
                                                                labels[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                                                labels[i].addMouseListener(new MouseAdapter() {
                                                                    @Override
                                                                    public void mousePressed(MouseEvent e) {
                                                                    	redirectBetsFrame();
                                                                    }
                                                                });
                                                            }
                                                            
                                                            if (labelTexts[i].equals("Minhas Apostas")) {
                                                                labels[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                                                labels[i].addMouseListener(new MouseAdapter() {
                                                                    @Override
                                                                    public void mousePressed(MouseEvent e) {
                                                                    	redirectBetsFrame();
                                                                    }
                                                                });
                                                            }
                                                        }
                                                        
                                                        editorPane = new JEditorPane();
                                                        editorPane.setBounds(948, 27, 107, 165);
                                                        frame.getContentPane().add(editorPane);
                                                    }
                                        
                                                    private void removeMenu() {
                                                        frame.getContentPane().remove(editorPane);
                                                        for (JSeparator separator : separators) {
                                                            frame.getContentPane().remove(separator);
                                                        }
                                                        for (JLabel label : labels) {
                                                            frame.getContentPane().remove(label);
                                                        }
                                                    }
                                                });
        
                JEditorPane bgNav = new JEditorPane();
                bgNav.setForeground(new Color(128, 0, 255));
                bgNav.setBackground(new Color(128, 0, 255));
                bgNav.setBounds(0, 0, 1064, 33);
                frame.getContentPane().add(bgNav);
        actionColumn.setCellEditor(new ButtonEditor(new JCheckBox(), events, table));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 83, 931, 364);
        frame.getContentPane().add(scrollPane);
    }
    
    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setBackground(new Color(0, 0, 0));
        frame.setForeground(new Color(0, 0, 0));
        frame.setBackground(new Color(0, 0, 0));
        frame.setBounds(100, 100, 1080, 560);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
                
                        JLabel saldoValor = new JLabel(this.getUserBalance());
                        saldoValor.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));
                        saldoValor.setBounds(985, 10, 37, 14);
                        frame.getContentPane().add(saldoValor);
                
    	
                JLabel saldo = new JLabel("Saldo");
                saldo.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));
                saldo.setBounds(924, 10, 37, 14);
                frame.getContentPane().add(saldo);
        
                JLabel mainTitle = new JLabel("CLT BET");
                mainTitle.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 13));
                mainTitle.setBounds(10, 10, 94, 14);
                frame.getContentPane().add(mainTitle);
        
        JLabel copyrightFooter = new JLabel("Desenvolvido por os TRÊS JAVEIROS");
        copyrightFooter.setFont(new Font("Tahoma", Font.BOLD, 11));
        copyrightFooter.setBackground(Color.BLACK);
        copyrightFooter.setBounds(425, 496, 211, 14);
        frame.getContentPane().add(copyrightFooter);
        
        JLabel footerTitle = new JLabel("Todos os direitos reservados");
        footerTitle.setFont(new Font("Tahoma", Font.BOLD, 11));
        footerTitle.setBackground(new Color(0, 0, 0));
        footerTitle.setBounds(443, 481, 193, 14);
        frame.getContentPane().add(footerTitle);
        
        JLabel events = new JLabel("Eventos");
        events.setForeground(new Color(255, 255, 255));
        events.setFont(new Font("SansSerif", Font.BOLD, 19));
        events.setBounds(10, 50, 170, 33);
        frame.getContentPane().add(events);
        
        this.eventsScreen();
        
        JEditorPane footer = new JEditorPane();
        footer.setForeground(new Color(153, 0, 204));
        footer.setBackground(new Color(255, 255, 255));
        footer.setBounds(-11, 469, 1075, 52);
        frame.getContentPane().add(footer);
        
    }
    
    public void redirectBetsFrame() {
    	try {
    		UserService user = new UserService();
        	User userFound = user.getUserById(CurrentUser.getId());
        	
        	if (events != null) {
        		/*MyBetsFrame betsFrame = new MyBetsFrame(userFound, events);
            	betsFrame.setVisible(true);*/
        	} else {
        		MyBetsFrame betsFrame = new MyBetsFrame(userFound);
            	betsFrame.setVisible(true);
        	}
        	
    		frame.dispose();
    	} catch (Exception e) {
    		 e.printStackTrace(); 
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
    
    public boolean confirm(String title, String type) {
    	JPanel panel = new JPanel();
        panel.setBackground(new Color(153, 0, 204));
        panel.setLayout(null);  
        
        JLabel logOutTitle = new JLabel(title);
        logOutTitle.setFont(new Font("Tahoma", Font.BOLD, 11));
        logOutTitle.setBounds(20, 20, 160, 14);
        panel.add(logOutTitle);
        
        AtomicBoolean isConfirmed = new AtomicBoolean(false);
        
        JLabel acceptLogout = new JLabel("Sim");
        acceptLogout.setBounds(30, 50, 46, 14);
        acceptLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
        panel.add(acceptLogout);
        
        JLabel denyLogout = new JLabel("Não");
        denyLogout.setBounds(100, 50, 46, 14);
        denyLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        denyLogout.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Window window = SwingUtilities.getWindowAncestor(panel);
                if (window != null) {
                    window.setVisible(false);
                }
            }
        });
        panel.add(denyLogout);

        JDialog dialog = new JDialog((Frame) null, "", true);
        dialog.getContentPane().add(panel);
        dialog.setSize(190, 120);
        dialog.setLocationRelativeTo(frame);  
        dialog.setVisible(true);
        
        return isConfirmed.get();
    }
    
    public void setBalanceValues() {
    	JDialog dialog = new JDialog(
        		(Frame) null, "Banca", true);
    	
    	User user = new User();
        UserService service = new UserService();
    	
    	JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(128, 0, 255));
        panel_1.setBounds(430, 180, 200, 150);
        frame.getContentPane().add(panel_1);
        panel_1.setLayout(null);
        
        JLabel balanceText = new JLabel("Saldo: ");
        balanceText.setForeground(Color.WHITE);
        balanceText.setBounds(10, 11, 42, 15);
        panel_1.add(balanceText);
        balanceText.setFont(new Font("Tahoma", Font.BOLD, 12));
        
        JButton btnBalance = new JButton("Sacar");
        btnBalance.setForeground(new Color(0, 0, 0));
        btnBalance.setFont(new Font("Tahoma", Font.BOLD, 9));
        btnBalance.setBounds(10, 100, 67, 23);
        panel_1.add(btnBalance);
        
        JButton btnDeposit = new JButton("Depositar");
        btnDeposit.setFont(new Font("Tahoma", Font.BOLD, 9));
        btnDeposit.setBounds(100, 100, 90, 23);
        panel_1.add(btnDeposit);
        
        String value = this.getUserBalance();
        JLabel balanceTextValue = new JLabel(value);
        balanceTextValue.setForeground(Color.WHITE);
        balanceTextValue.setFont(new Font("Tahoma", Font.BOLD, 12));
        balanceTextValue.setBounds(62, 11, 42, 15);
        panel_1.add(balanceTextValue);
        
        textField_1 = new JTextField();
        textField_1.setBounds(10, 60, 180, 23);
        panel_1.add(textField_1);
        textField_1.setColumns(10);
        
        JLabel amount = new JLabel("Quantia:");
        amount.setForeground(Color.WHITE);
        amount.setFont(new Font("Tahoma", Font.BOLD, 12));
        amount.setBounds(10, 40, 52, 15);
        panel_1.add(amount);
        

        btnBalance.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
                try {
                	String amountToSac = textField_1.getText();
                	
                	user.setId(CurrentUser.getId());
                	user.setBalance(CurrentUser.getBalance() - Double.parseDouble(amountToSac));
                	user.setEmail(CurrentUser.getEmail());
                	user.setName(CurrentUser.getUsername());
                	
                	Boolean isConfirmed = confirm("Deseja sacar o valor?", "Banca");
                	
                	if (isConfirmed) {
                		service.updateUser(user);
                        
                        panel_1.setVisible(false);
                        dialog.setVisible(false);
                        
                        balanceTextValue.setText(CurrentUser.getBalance().toString());
                        
                        JOptionPane.showMessageDialog(null, "Saque realizado com sucesso!");
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
                	
                	user.setId(CurrentUser.getId());
                	user.setBalance(CurrentUser.getBalance() + Double.parseDouble(amountToDeposit));
                	user.setEmail(CurrentUser.getEmail());
                	user.setName(CurrentUser.getUsername());
                	
                    Boolean isConfirmed = confirm("Deseja depositar o valor?", "Banca");
                    
                	if (isConfirmed) {              
                        service.updateUser(user);
                       
                        panel_1.setVisible(false);
                        dialog.setVisible(false);
                        
                        balanceTextValue.setText(CurrentUser.getBalance().toString());
                        
                        JOptionPane.showMessageDialog(null, "Depósito realizado com sucesso!");
                	}
                	
                } catch (Exception ex) {
                	JOptionPane.showMessageDialog(null, "Falha ao depositar!");
                    throw ex;
                }
        	}
        });
        
        dialog.getContentPane().add(panel_1);
        dialog.setSize(215, 180);
        dialog.setLocationRelativeTo(frame);  
        dialog.setVisible(true);
    }
    
    public void changeProfile() {        
        JDialog dialog = new JDialog(
        		(Frame) null, "Perfil", true);
        
    	JPanel profile = new JPanel();
        profile.setBackground(new Color(128, 0, 255));
        profile.setBounds(420, 90, 220, 300);
        frame.getContentPane().add(profile);
        profile.setLayout(null);
        
        JLabel mainTitleProfile = new JLabel("Alterar Perfil");
        mainTitleProfile.setBounds(60, 5, 99, 19);
        mainTitleProfile.setFont(new Font("Tahoma", Font.BOLD, 15));
        profile.add(mainTitleProfile);
        
        JLabel username = new JLabel("Nome");
        username.setBounds(10, 42, 46, 14);
        profile.add(username);
        
        textField = new JTextField();
        textField.setBounds(10, 56, 200, 20);
        profile.add(textField);
        textField.setColumns(10);
        
        JLabel currentPassword = new JLabel("Senha atual");
        currentPassword.setBounds(10, 87, 90, 14);
        profile.add(currentPassword);
        
        passwordField = new JPasswordField();
        passwordField.setBounds(10, 101, 200, 20);
        profile.add(passwordField);
        
        JLabel newPassword = new JLabel("Nova senha\r\n");
        newPassword.setBounds(10, 130, 90, 14);
        profile.add(newPassword);
        
        passwordField_1 = new JPasswordField();
        passwordField_1.setBounds(10, 145, 200, 20);
        profile.add(passwordField_1);
        
        JButton btnDeleteUser = new JButton("Deletar");
        btnDeleteUser.setForeground(new Color(0, 0, 0));
        btnDeleteUser.setBackground(new Color(204, 0, 0));
        btnDeleteUser.setBounds(10, 252, 89, 23);
        profile.add(btnDeleteUser);
        
        JButton btnSaveChanges = new JButton("Salvar");
        btnSaveChanges.setForeground(new Color(0, 0, 0));
        btnSaveChanges.setBackground(new Color(0, 102, 0));
        btnSaveChanges.setBounds(121, 252, 89, 23);
        profile.add(btnSaveChanges);
        
        JLabel repeatNewPassword_1 = new JLabel("Repita nova senha\r\n");
        repeatNewPassword_1.setBounds(10, 171, 110, 14);
        profile.add(repeatNewPassword_1);
        
        repeatPassword = new JPasswordField();
        repeatPassword.setBounds(10, 188, 200, 20);
        profile.add(repeatPassword);
        
        btnDeleteUser.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Boolean isConfirmed = confirm("Deseja deletar a conta?", "Deletar");

                    if (isConfirmed) {
                        UserService service = new UserService();
                        service.deleteUser(CurrentUser.getId());
                        
                        JOptionPane.showMessageDialog(null, "Deletado com sucesso!");
            		
                        profile.setVisible(false);
                        dialog.setVisible(false);

                        frame.dispose();

                        LoginFrame loginFrame = new LoginFrame();
                        loginFrame.setVisible(true);
                    }
                    
                } catch (Exception ex) {
                	JOptionPane.showMessageDialog(null, "Falha ao deletar!");
                    throw ex;
                }
        	}
        });
        
        btnSaveChanges.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = new User();
                UserService service = new UserService();
                
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
                    JOptionPane.showMessageDialog(null, "A senha deve conter pelo menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial.");
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
                    service.updateUser(user);
                 
                    JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso!");
                } catch (Exception ex) {
                	JOptionPane.showMessageDialog(null, "Falha ao alterar dados!");
                    throw ex;
                }
            }
        });

        dialog.getContentPane().add(profile);
        dialog.setSize(235, 330);
        dialog.setLocationRelativeTo(frame);  
        dialog.setVisible(true);
    }
    
    public void logOut() {
    	Boolean isConfirmed = this.confirm("Deseja sair do sistema?", "Sair");

        if (isConfirmed) {
            CurrentUser.setAccessToken(null);
            frame.dispose();

            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        }
    }
    
    public void setVisible(boolean visible) {
		frame.setVisible(visible);
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

    public ButtonEditor(JCheckBox checkBox, List<Event> events, JTable table) {
        super(checkBox);
        this.events = events;
        this.table = table;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(e -> fireEditingStopped());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
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

            SwingUtilities.invokeLater(() -> {
                int dialogResult = JOptionPane.showConfirmDialog(null,
                        "Deseja realmente apostar neste evento?",
                        "Confirmação",
                        JOptionPane.YES_NO_OPTION);

                if (dialogResult == JOptionPane.YES_OPTION) {
                    try {
                        JOptionPane.showMessageDialog(null, "Aposta realizada com sucesso!");

                        events.remove(row);
                        DefaultTableModel model = (DefaultTableModel) table.getModel();
                        model.removeRow(row);

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao realizar a aposta: " + ex.getMessage(), "Erro",
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