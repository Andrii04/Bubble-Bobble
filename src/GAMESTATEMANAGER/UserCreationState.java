package GAMESTATEMANAGER;


import MODEL.UserProfile;
import VIEW.MainFrame;
import VIEW.UserCreationPanel;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * La classe UserCreationState gestisce lo stato di creazione dell'utente nel gioco.
 * Consente all'utente di inserire un nome e selezionare un avatar, creando un profilo di gioco.
 */
public class UserCreationState extends GameState {

    // Pannello della vista per la creazione dell'utente
    private UserCreationPanel view = MainFrame.getUserCreationPanel();

    // Gestore degli stati del gioco
    private GameStateManager gsm;

    // Profilo dell'utente che viene creato
    private UserProfile userProfile;

    /**
     * Costruttore che inizializza lo stato di creazione dell'utente.
     * Aggiunge un KeyListener al campo di testo per l'inserimento del nome utente.
     *
     * @param gsm il gestore degli stati del gioco
     */
    public UserCreationState(GameStateManager gsm) {
        this.gsm = gsm;
        view.getUsernameField().addKeyListener(this);
        view.getUsernameField().setFocusable(true);
        view.getUsernameField().requestFocusInWindow();
    }

    /**
     * Metodo di aggiornamento che viene chiamato ad ogni frame di gioco.
     * Non viene utilizzato attivamente in questo stato.
     */
    @Override
    public void update() {

    }

    /**
     * Disegna il pannello di creazione dell'utente sulla finestra principale.
     */
    @Override
    public void draw() {
        MainFrame.setPanel(MainFrame.getUserCreationPanel());
    }

    /**
     * Gestisce gli eventi di azione. Non viene utilizzato in questo stato.
     *
     * @param e l'evento di azione
     */
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    /**
     * Gestisce gli eventi di digitazione dei tasti. Limita l'input del nome utente a lettere maiuscole e a un massimo di 10 caratteri.
     *
     * @param e l'evento di digitazione del tasto
     */
    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (Character.isLetter(c) && view.getCharCount() < 10) {
            view.setCharCount(view.getCharCount() + 1);
            e.setKeyChar(Character.toUpperCase(c)); // Converte il carattere in maiuscolo
        } else {
            e.consume(); // Impedisce l'inserimento di caratteri non validi
        }
    }

    /**
     * Gestisce gli eventi di pressione dei tasti. Include le azioni per backspace, freccia destra, freccia sinistra e invio.
     *
     * @param e l'evento di pressione del tasto
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && view.getCharCount() > 0) {
            // Rimuove l'ultimo carattere inserito
            view.setCharCount(view.getCharCount() - 1);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            // Sposta il cursore a destra
            view.cursorRight();
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            // Sposta il cursore a sinistra
            view.cursorLeft();
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            // Verifica se è stato inserito un nome utente valido
            if (view.getUsername() == null || (view.getUsername().equals(""))) {
                e.consume(); // Impedisce l'azione se il nome utente non è valido
                return;
            } else {
                // Crea un nuovo profilo utente con il nome e l'avatar selezionato
                userProfile = new UserProfile(view.getUsername(), 0, 1, view.getSelectedAvatar());
                // Aggiornamento della leaderboard verrà gestito successivamente (in WinState e LoseState)
            }
            // Inizia il gioco con il nuovo profilo utente
            gsm.startGame(userProfile);
        }
    }

    /**
     * Gestisce gli eventi di rilascio dei tasti. Non viene utilizzato in questo stato.
     *
     * @param e l'evento di rilascio del tasto
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     * Gestisce gli eventi di clic del mouse. Non viene utilizzato in questo stato.
     *
     * @param e l'evento di clic del mouse
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Gestisce l'evento di pressione del mouse. Non viene utilizzato in questo stato.
     *
     * @param e l'evento di pressione del mouse
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Gestisce l'evento di rilascio del mouse. Non viene utilizzato in questo stato.
     *
     * @param e l'evento di rilascio del mouse
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Gestisce l'evento di ingresso del mouse. Non viene utilizzato in questo stato.
     *
     * @param e l'evento di ingresso del mouse
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Gestisce l'evento di uscita del mouse. Non viene utilizzato in questo stato.
     *
     * @param e l'evento di uscita del mouse
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }
}




