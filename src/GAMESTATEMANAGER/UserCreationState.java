package GAMESTATEMANAGER;

import MODEL.Player;
import MODEL.UserProfile;
import VIEW.MainFrame;
import VIEW.PlayerView;
import VIEW.UserCreationPanel;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class UserCreationState extends GameState {
    private UserCreationPanel view = MainFrame.getUserCreationPanel();
    private GameStateManager gsm;
    private UserProfile userProfile;

    public UserCreationState(GameStateManager gsm) {
        this.gsm = gsm;
        view.getUsernameField().addKeyListener(this);
        view.getUsernameField().setFocusable(true);
        view.getUsernameField().requestFocusInWindow();
    }

    @Override
    public void update() {

    }

    @Override
    public void draw() {
        MainFrame.setPanel(MainFrame.getUserCreationPanel());
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (Character.isLetter(c) && view.getCharCount() < 10) {
            view.setCharCount(view.getCharCount() + 1);
            e.setKeyChar(Character.toUpperCase(c));
        } else {
            e.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && view.getCharCount() > 0) {
            view.setCharCount(view.getCharCount() - 1);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            view.cursorRight();
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            view.cursorLeft();
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (view.getUsername() == null || (view.getUsername().equals(""))) {
                e.consume();
                return;
            } else if (!searchUser(view.getUsername(), "src/MODEL/leaderboard.txt")) {
                userProfile = new UserProfile(view.getUsername(), 0, 1, view.getSelectedAvatar());
                // aggiornamento della leaderboard avverrà dopo ( WinState e LoseState )
            } else {
                // fetch data from leaderboard
                //  MainFrame.setUserProfile();
            }
            gsm.startGame(new Player(userProfile));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private boolean searchUser(String username, String path) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            while (reader.readLine() != null) {
                if (reader.readLine().contains(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}




