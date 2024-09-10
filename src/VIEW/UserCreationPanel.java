package VIEW;

import CONTROLLER.UserCreationC;
import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * Pannello per la creazione dell'utente.
 *
 * Questo pannello consente all'utente di inserire un nome e selezionare un avatar
 * prima di confermare la creazione del profilo. Include un campo di testo per il nome utente,
 * una selezione di avatar, e una conferma per avviare la creazione del profilo utente.
 */
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

    /**
     * Costruttore del pannello di creazione utente.
     *
     * Inizializza il pannello impostando la dimensione, il colore di sfondo, e il layout.
     * Configura i componenti dell'interfaccia utente, inclusi il campo di testo per il nome utente,
     * la selezione di avatar e il cursore.
     */
    public UserCreationPanel() {
        this.setSize(MainFrame.FRAME_WIDTH, MainFrame.FRAME_HEIGHT);
        this.setBackground(Color.BLACK);
        this.setVisible(true);
        this.setLayout(null);
        this.setFocusable(true);
        usernameField.addActionListener(e -> confirmUserCreation());

        // Configura il campo di testo per il nome utente
        username.setBounds(MainFrame.TILE_SIZE * 2, MainFrame.TILE_SIZE, 500, 50);
        username.setForeground(Color.WHITE);
        username.setHorizontalAlignment(SwingConstants.CENTER);
        username.setFont(MainFrame.getPixelFont().deriveFont(20f));
        username.setVisible(true);

        this.add(username);

        // Imposta il campo di testo per l'inserimento del nome utente
        usernameField.setBounds(username.getX(), username.getY() + MainFrame.TILE_SIZE, 500, 50);
        usernameField.setVisible(true);
        usernameField.setHorizontalAlignment(SwingConstants.CENTER);
        usernameField.setFont(MainFrame.getPixelFont().deriveFont(20f));
        usernameField.setBackground(Color.BLACK);
        usernameField.setForeground(Color.WHITE);
        this.add(usernameField);

        usernameField.setFocusable(true);
        usernameField.requestFocusInWindow();

        // Configura la selezione dell'avatar
        avatar.setFont(MainFrame.getPixelFont().deriveFont(20f));
        avatar.setForeground(Color.WHITE);
        avatar.setHorizontalAlignment(SwingConstants.CENTER);
        avatar.setBounds(MainFrame.TILE_SIZE * 2, MainFrame.TILE_SIZE * 4, 500, 50);
        avatar.setVisible(true);
        this.add(avatar);

        // Imposta le fonti delle immagini degli avatar
        avatarSources.put(0, "src/Resources/Bubble Bobble Resources/Character/Run/Run2.png"); // bub
        avatarSources.put(1, "src/Resources/Bubble Bobble Resources/Enemies/Benzo/Walk/Enemy1.png"); // benzo
        avatarSources.put(2, "src/Resources/Bubble Bobble Resources/Enemies/Blubba/Walk/Enemy39.png"); // blubba
        avatarSources.put(3, "src/Resources/Bubble Bobble Resources/Enemies/BoaBoa/Walk/Enemy55.png"); // boaboa
        avatarSources.put(4, "src/Resources/Bubble Bobble Resources/Enemies/Invader/Idle/idle1.png"); // Invader
        avatarSources.put(5, "src/Resources/Bubble Bobble Resources/Enemies/Boris/Walk/Enemy17.png"); // boris
        avatarSources.put(6, "src/Resources/Bubble Bobble Resources/Enemies/Incendio/Walk/Walk1.png"); // incendio
        avatarSources.put(7, "src/Resources/Bubble Bobble Resources/Enemies/Superdrunk/Walk/NES - Bubble Bobble - Boss & Final Scene - Super Drunk5.png"); // superdrunk

        int avatarWidth = 54;
        int avatarHeight = 54;
        int spacing = 20;
        int totalWidth = 8 * avatarWidth + 7 * spacing;
        int startX = (MainFrame.FRAME_WIDTH - totalWidth) / 2;

        // Aggiunge le etichette degli avatar al pannello
        for (int i = 0; i < avatarSources.size(); i++) {
            JLabel avatar = new JLabel("");
            ImageIcon img = new ImageIcon(avatarSources.get(i));
            Image imgScaled = img.getImage().getScaledInstance(54, 54, Image.SCALE_SMOOTH);
            avatar.setIcon(new ImageIcon(imgScaled));
            avatar.setBounds(startX + i * (avatarWidth + spacing), MainFrame.TILE_SIZE * 6, avatarWidth, avatarHeight);
            avatar.setVisible(true);
            this.add(avatar);
        }

        // Configura il cursore dell'avatar
        cursor.setIcon(new ImageIcon("src/Resources/Bubble Bobble Resources/Bubbles/GreenBubble4.png"));
        cursor.setBounds(startX + selectedAvatar * (avatarWidth + spacing) + 20, MainFrame.TILE_SIZE * 5, 50, 50);
        cursor.setVisible(true);
        this.add(cursor);

        // Configura il pulsante di conferma
        confirm.setFont(MainFrame.getPixelFont().deriveFont(20f));
        confirm.setBounds(MainFrame.TILE_SIZE * 2, MainFrame.TILE_SIZE * 8, 500, 50);
        confirm.setForeground(Color.WHITE);
        confirm.setHorizontalAlignment(SwingConstants.CENTER);
        confirm.setVisible(true);
        this.add(confirm);
    }

    /**
     * Sposta il cursore dell'avatar a sinistra.
     *
     * Riduce l'indice dell'avatar selezionato e aggiorna la posizione del cursore.
     */
    public void cursorLeft() {
        if (selectedAvatar > 0) {
            selectedAvatar--;
            cursor.setBounds(cursor.getX() - 74, cursor.getY(), cursor.getWidth(), cursor.getHeight());
        }
    }

    /**
     * Sposta il cursore dell'avatar a destra.
     *
     * Aumenta l'indice dell'avatar selezionato e aggiorna la posizione del cursore.
     */
    public void cursorRight() {
        if (selectedAvatar < 7) {
            selectedAvatar++;
            cursor.setBounds(cursor.getX() + 74, cursor.getY(), cursor.getWidth(), cursor.getHeight());
        }
    }

    /**
     * Conferma la creazione dell'utente.
     *
     * Recupera il nome utente e l'avatar selezionato, quindi chiama il metodo
     * del controller per salvare i dati dell'utente. Mostra un messaggio di successo
     * o di errore a seconda che il nome utente sia valido o meno.
     */
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

    /**
     * Restituisce il nome utente inserito.
     *
     * @return Il testo del campo di input del nome utente.
     */
    public String getUsername() {
        return usernameField.getText();
    }

    /**
     * Restituisce il campo di testo per l'inserimento del nome utente.
     *
     * @return Il campo di testo per l'inserimento del nome utente.
     */
    public JTextField getUsernameField() {
        return usernameField;
    }

    /**
     * Restituisce l'indice dell'avatar selezionato.
     *
     * @return L'indice dell'avatar selezionato.
     */
    public int getSelectedAvatar() {
        return selectedAvatar;
    }

    /**
     * Restituisce il conteggio dei caratteri inseriti nel nome utente.
     *
     * @return Il conteggio dei caratteri.
     */
    public int getCharCount() {
        return charCount;
    }

    /**
     * Imposta il conteggio dei caratteri inseriti nel nome utente.
     *
     * @param charCount Il nuovo conteggio dei caratteri.
     */
    public void setCharCount(int charCount) {
        this.charCount = charCount;
    }
}