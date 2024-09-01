package MODEL;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class LeaderboardM {
    private List<UserProfile> profiles;

    public LeaderboardM() {
        profiles = new ArrayList<>();
    }

    // Metodo per aggiungere un profilo utente alla leaderboard
    public void addProfile(UserProfile profile) {
        profiles.add(profile);
    }

    // Metodo per ottenere la leaderboard ordinata per punteggio massimo

    public List<UserProfile> getSortedLeaderboard() {
        Collections.sort(profiles, Comparator.comparingInt(UserProfile::getPunteggio).reversed());
        return profiles;
    }

    // Metodo per salvare la leaderboard su file
    public void saveToFile(String filename) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filename);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            for (UserProfile profile : profiles) {
                bufferedWriter.write(profile.getUsername() + "," + profile.getPunteggio() + "," + profile.getRound());
                bufferedWriter.newLine();
            }

            System.out.println("Leaderboard saved on " + filename);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error while saving leaderboard.");
        }
    }


    // Metodo per caricare la leaderboard da file
    public static LeaderboardM loadFromFile(String filename) throws IOException {
        LeaderboardM leaderboard = new LeaderboardM();

        File file = new File(filename);
        if (!file.exists()) {
            // Crea un file vuoto se non esiste
            file.createNewFile();
            return leaderboard;
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String username = parts[0];
                    int punteggio = Integer.parseInt(parts[1]);
                    int round = Integer.parseInt(parts[2]);
                    UserProfile profile = new UserProfile(username, punteggio, round, 0); // 0 come valore di default per l'avatarIndex
                    leaderboard.addProfile(profile);
                }
            }
            System.out.println("Leaderboard loaded from " + filename);
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            System.err.println("Error while loading leaderboard.");
        }

        return leaderboard;
    }
}
