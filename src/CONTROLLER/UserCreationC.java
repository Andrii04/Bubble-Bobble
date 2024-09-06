package CONTROLLER;

import MODEL.UserProfile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class UserCreationC {

    private static final String LEADERBOARD_FILE = "leaderboard.txt";

    public void saveUser(String username, int avatarIndex) {
        UserProfile newUser = new UserProfile(username,0,0, avatarIndex);

        // Salva l'utente nel file leaderboard
        saveUserToFile(newUser);
    }

    private void saveUserToFile(UserProfile user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LEADERBOARD_FILE, true))) {
            writer.write(user.toString());
            writer.newLine(); // Vai a capo per il prossimo utente
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
