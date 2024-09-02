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

    public void addProfile(UserProfile profile) {
        profiles.add(profile);
        sortProfiles();
    }

    private void sortProfiles() {
        Collections.sort(profiles, Comparator.comparingInt(UserProfile::getPunteggio).reversed());
    }

    public List<UserProfile> getSortedLeaderboard() {
        sortProfiles();
        return profiles;
    }

    public void saveToFile(String filename) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename))) {
            for (UserProfile profile : profiles) {
                bufferedWriter.write(profile.getUsername() + "," +
                        profile.getPunteggio() + "," +
                        profile.getRound() + "," +
                        profile.getAvatarChosen() + "," +
                        profile.getPartiteVinte() + "," +
                        profile.getPartitePerse() + "," +
                        profile.getPartiteTot());
                bufferedWriter.newLine();
            }
            System.out.println("Leaderboard saved on " + filename);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error while saving leaderboard.");
        }
    }

    public static LeaderboardM loadFromFile(String filename) throws IOException {
        LeaderboardM leaderboard = new LeaderboardM();
        File file = new File(filename);
        if (!file.exists()) {
            file.createNewFile();
            return leaderboard;
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 8) {
                    int num = Integer.parseInt(parts[0]);
                    String avatarChosen = parts[1];
                    String username = parts[2];
                    int round = Integer.parseInt(parts[3]);
                    int punteggio = Integer.parseInt(parts[4]);
                    int partiteVinte = Integer.parseInt(parts[5]);
                    int partitePerse = Integer.parseInt(parts[6]);
                    int partiteTot = Integer.parseInt(parts[7]);

                    UserProfile profile = new UserProfile(username, punteggio, round, 0);
                    profile.setAvatarChosen(avatarChosen);
                    profile.setPartiteVinte(partiteVinte);
                    profile.setPartitePerse(partitePerse);
                    profile.setPartiteTot(partiteTot);

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
