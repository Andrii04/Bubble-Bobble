package VIEW;

import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;

    public class UserCreationPanel extends JPanel {

        private JLabel username = new JLabel("ENTER YOUR NAME::");
        private JTextField usernameField = new JTextField();
        public UserCreationPanel() {
            this.setSize(MainFrame.FRAME_WIDTH,MainFrame.FRAME_HEIGHT);
            this.setBackground(Color.BLACK);
            this.setVisible(true);
            this.setLayout(null);
            this.setFocusable(true);

            username.setBounds(MainFrame.TILE_SIZE*2 , MainFrame.TILE_SIZE, 500, 50);
            username.setForeground(Color.WHITE);
            username.setHorizontalAlignment(SwingConstants.CENTER);
            username.setFont(MainFrame.getPixelFont().deriveFont(20f));
            username.setVisible(true);

            this.add(username);

            usernameField.setBounds(username.getX(), username.getY()+MainFrame.TILE_SIZE, 500, 50);
            usernameField.setVisible(true);

            usernameField.setHorizontalAlignment(SwingConstants.CENTER);
            usernameField.setFont(MainFrame.getPixelFont().deriveFont(20f));
            usernameField.setBackground(Color.BLACK);
            usernameField.setForeground(Color.WHITE);


            this.add(usernameField);
        }

        public String getUsername() {
            return usernameField.getText();
        }
        public JTextField getUsernameField() {
            return usernameField;
        }
    }