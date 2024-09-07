package VIEW;

import CONTROLLER.UserCreationC;
import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class UserCreationPanel extends JPanel {


    private JLabel username = new JLabel("ENTER YOUR NAME:");
    private JTextField usernameField = new JTextField();
    private JLabel avatar = new JLabel("CHOOSE YOUR AVATAR:");
    private JLabel confirm = new JLabel("PRESS ENTER TO CONFIRM");
    private HashMap<Integer,String> avatarSources = new HashMap<>();
    private JLabel cursor = new JLabel();
    private int charCount = 0;
    private int selectedAvatar = 0;
    private UserCreationC controller = new UserCreationC();


    public UserCreationPanel() {
        this.setSize(MainFrame.FRAME_WIDTH,MainFrame.FRAME_HEIGHT);
        this.setBackground(Color.BLACK);
        this.setVisible(true);
        this.setLayout(null);
        this.setFocusable(true);
        usernameField.addActionListener(e -> confirmUserCreation());

        // Asking for username input
        username.setBounds(MainFrame.TILE_SIZE*2 , MainFrame.TILE_SIZE, 500, 50);
        username.setForeground(Color.WHITE);
        username.setHorizontalAlignment(SwingConstants.CENTER);
        username.setFont(MainFrame.getPixelFont().deriveFont(20f));
        username.setVisible(true);

        this.add(username);

        // Username input field
        usernameField.setBounds(username.getX(), username.getY()+MainFrame.TILE_SIZE, 500, 50);
        usernameField.setVisible(true);

        usernameField.setHorizontalAlignment(SwingConstants.CENTER);
        usernameField.setFont(MainFrame.getPixelFont().deriveFont(20f));
        usernameField.setBackground(Color.BLACK);
        usernameField.setForeground(Color.WHITE);
        this.add(usernameField);

        usernameField.setFocusable(true);
        usernameField.requestFocusInWindow();

        // Asking for avatar selection
        avatar.setFont(MainFrame.getPixelFont().deriveFont(20f));
        avatar.setForeground(Color.WHITE);
        avatar.setHorizontalAlignment(SwingConstants.CENTER);
        avatar.setBounds(MainFrame.TILE_SIZE*2,MainFrame.TILE_SIZE*4,500,50);
        avatar.setVisible(true);
        this.add(avatar);

        // Avatar
        avatarSources.put(0, "src/Resources/Bubble Bobble Resources/Character/Run/Run2.png"); // bub
        avatarSources.put(1,"src/Resources/Bubble Bobble Resources/Enemies/Benzo/Walk/Enemy1.png"); // benzo
        avatarSources.put(2, "src/Resources/Bubble Bobble Resources/Enemies/Blubba/Walk/Enemy39.png"); // blubba
        avatarSources.put(3, "src/Resources/Bubble Bobble Resources/Enemies/BoaBoa/Walk/Enemy55.png"); // boaboa
        avatarSources.put(4,"src/Resources/Bubble Bobble Resources/Enemies/Invader/Idle/idle1.png"); // Invader
        avatarSources.put(5,"src/Resources/Bubble Bobble Resources/Enemies/Boris/Walk/Enemy17.png"); // boris
        avatarSources.put(6,"src/Resources/Bubble Bobble Resources/Enemies/Incendio/Walk/Walk1.png"); // incendio
        avatarSources.put(7,"src/Resources/Bubble Bobble Resources/Enemies/Superdrunk/Walk/NES - Bubble Bobble - Boss & Final Scene - Super Drunk5.png"); // superdrunk

        int avatarWidth = 54;
        int avatarHeight = 54;
        int spacing = 20;
        int totalWidth = 8 * avatarWidth + 7 * spacing;
        int startX = (MainFrame.FRAME_WIDTH - totalWidth )/2;

        for(int i = 0; i < avatarSources.size(); i++){
            JLabel avatar = new JLabel("");
            ImageIcon img = new ImageIcon(avatarSources.get(i));
            Image imgScaled = img.getImage().getScaledInstance(54,54,Image.SCALE_SMOOTH);
            avatar.setIcon(new ImageIcon(imgScaled));
            avatar.setBounds(startX + i * (avatarWidth + spacing), MainFrame.TILE_SIZE * 6, avatarWidth, avatarHeight);
            avatar.setVisible(true);
            this.add(avatar);
        }

        // Avatar cursor default
        cursor.setIcon(new ImageIcon("src/Resources/Bubble Bobble Resources/Bubbles/GreenBubble4.png"));
        cursor.setBounds(startX + selectedAvatar * (avatarWidth + spacing) +20 ,MainFrame.TILE_SIZE*5,50,50);
        cursor.setVisible(true);
        this.add(cursor);

        // Start the game
        confirm.setFont(MainFrame.getPixelFont().deriveFont(20f));
        confirm.setBounds(MainFrame.TILE_SIZE*2,MainFrame.TILE_SIZE*8,500,50);
        confirm.setForeground(Color.WHITE);
        confirm.setHorizontalAlignment(SwingConstants.CENTER);
        confirm.setVisible(true);
        this.add(confirm);

    }

    // move cursor
    public void cursorLeft(){
        if(selectedAvatar > 0){
            selectedAvatar--;
            cursor.setBounds(cursor.getX()-74, cursor.getY(), cursor.getWidth(), cursor.getHeight());
        }
    }
    public void cursorRight(){
        if(selectedAvatar < 7){
            selectedAvatar++;
            cursor.setBounds(cursor.getX()+74, cursor.getY(), cursor.getWidth(), cursor.getHeight());
        }
    }

    private void confirmUserCreation() {
        String username = getUsername();
        int avatar = getSelectedAvatar();

        // Controlla che l'username non sia vuoto
        if (!username.isEmpty()) {
            controller.saveUser(username, avatar);
            System.out.println("Utente salvato con successo!");
        } else {
            System.out.println("Per favore, inserisci un nome.");
        }
    }

    public String getUsername() {
        return usernameField.getText();
    }
    public JTextField getUsernameField() {
        return usernameField;
    }
    public int getSelectedAvatar() {
        return selectedAvatar;
    }

    public int getCharCount() {
        return charCount;
    }
    public void setCharCount(int charCount){
        this.charCount = charCount;
    }

}