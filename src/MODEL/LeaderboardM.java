package MODEL;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;


public class LeaderboardM {
    private static final String path = "leaderboard.ser";
    private Map<String,UserProfile> users;

    public LeaderboardM() {
        users = loadUsers();
    }

    // prende hashmap di tutti i profili utente
    public Map<String, UserProfile> loadUsers(){
        File file = new File(path);
        if (!file.exists() || file.length() == 0) {
            // File does not exist or is empty, return a new empty HashMap
            return new HashMap<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Map<String, UserProfile>) ois.readObject();
        } catch (EOFException e) {
            // Handle EOFException specifically if file is partially written
            System.err.println("File is empty or corrupted.");
            return new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }
    // aggiunge un utente alla hashmap ( se gia esiste e il punteggio è minore di quello attuale, lo sovrascrive, se no continua)
    public void addUser(UserProfile profile){
        if(users.get(profile.getUsername()) == null){
            if(users.size() >= 10){ // se ci sono già 10 utenti, rimuove l'utente con il punteggio minore
                users.remove(users.entrySet().stream().min(Comparator.comparingInt(e -> e.getValue().getPunteggio())).get().getKey());
            }UserProfile oldUser = users.get(profile.getUsername());
            users.put(profile.getUsername(), profile);
            saveUsers();
        }
        else{
            // se l'utente esiste già, controlla se il punteggio è minore di quello attuale se si lo sovrascrive
            if(users.get(profile.getUsername()).getPunteggio() > profile.getPunteggio()){
                if(users.get(profile.getUsername()).getRound() < profile.getRound()){
                    profile.setRound(users.get(profile.getUsername()).getRound());
                }
                profile.setPunteggio(users.get(profile.getUsername()).getPunteggio());
            }
                profile.setPartiteVinte(users.get(profile.getUsername()).getPartiteVinte()+ profile.getPartiteVinte());
                profile.setPartitePerse(users.get(profile.getUsername()).getPartitePerse()+ profile.getPartitePerse());
                profile.setPartiteTot(users.get(profile.getUsername()).getPartiteTot()+ profile.getPartiteTot());
                users.put(profile.getUsername(), profile);
                saveUsers();
        }
    }

    // salva la hashmap su file
    public void saveUsers(){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))){
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public List<UserProfile> getSortedUsers() {
        users = loadUsers();
        if (users != null) {
            return users.values().stream()
                    .sorted(Comparator.comparingInt(UserProfile::getPunteggio)
                            .reversed()).toList();
        }
        return null;
    }

}
