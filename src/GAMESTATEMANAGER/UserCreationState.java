package GAMESTATEMANAGER;

import MODEL.UserProfile;
import VIEW.MainFrame;
import VIEW.UserCreationPanel;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class UserCreationState extends GameState {
    private UserCreationPanel view = MainFrame.getUserCreationPanel();
    private GameStateManager gsm;
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

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (view.getUsername() != null || !(view.getUsername().equals(""))) {
                if (!searchUser(view.getUsername(),"src/MODEL/leaderboard.txt")){
                    MainFrame.setUserProfile(new UserProfile(view.getUsername(), 0, 1)); ; // aggiornamento della leaderboard avverrà dopo ( WinState e LoseState )
                }
                else{
                    // fetch data from leaderboard
                    //  MainFrame.setUserProfile();
                }
                gsm.setState(GameStateManager.playState);
            }
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
    public static boolean searchUser(String username, String path) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try{
            while(reader.readLine() != null) {
                if (reader.readLine().contains(username)) {
                    return true;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
    }
        return false;
    }
}
