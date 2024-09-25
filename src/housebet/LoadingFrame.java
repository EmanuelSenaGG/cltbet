package housebet;

import javax.swing.*;
import java.awt.*;

public class LoadingFrame extends JFrame {
    public LoadingFrame() {
        setTitle("Carregando...");
        setSize(300, 100);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setBackground(Color.WHITE);

        JLabel label = new JLabel("Carregando, por favor aguarde...", SwingConstants.CENTER);
        label.setFont(new Font("Tw Cen MT", Font.BOLD, 16));
        getContentPane().add(label, BorderLayout.CENTER);

        setVisible(true);
    }
}

