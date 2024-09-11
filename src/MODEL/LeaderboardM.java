package MODEL;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * La classe LeaderboardM gestisce una classifica di profili utente,
 * permettendo il caricamento, l'aggiunta e il salvataggio di profili
 * su un file serializzato. Inoltre, offre la possibilità di ottenere
 * gli utenti ordinati in base al punteggio.
 */
public class LeaderboardM {
    // Percorso del file per la memorizzazione della classifica
    private static final String path = "leaderboard.ser";
    private Map<String, UserProfile> users;

    /**
     * Costruttore della classe LeaderboardM. Carica i profili utente
     * dal file serializzato al momento della creazione dell'oggetto.
     */
    public LeaderboardM() {
        users = loadUsers();
    }

    /**
     * Carica i profili utente da un file serializzato. Se il file non esiste
     * o è vuoto, ritorna una mappa vuota.
     *
     * @return Mappa di profili utente.
     */
    public Map<String, UserProfile> loadUsers() {
        File file = new File(path);
        if (!file.exists() || file.length() == 0) {
            // Il file non esiste o è vuoto, ritorna una mappa vuota
            return new HashMap<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            // Legge e ritorna la mappa di profili utente dal file
            return (Map<String, UserProfile>) ois.readObject();
        } catch (EOFException e) {
            // Gestisce il caso di file vuoto o corrotto
            System.err.println("File is empty or corrupted.");
            return new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    /**
     * Aggiunge un profilo utente alla mappa. Se il profilo esiste già
     * e il nuovo punteggio è inferiore a quello esistente, aggiorna il
     * profilo solo se il round è maggiore. Se ci sono già 10 utenti,
     * rimuove l'utente con il punteggio più basso.
     *
     * @param profile Il profilo utente da aggiungere o aggiornare.
     */
    public void addUser(UserProfile profile) {
        if (users.get(profile.getUsername()) == null) {
            // Aggiunge un nuovo utente, rimuove l'utente con il punteggio più basso se ci sono già 10 utenti
            if (users.size() >= 10) {
                users.remove(users.entrySet().stream().min(Comparator.comparingInt(e -> e.getValue().getPunteggio())).get().getKey());
            }
            users.put(profile.getUsername(), profile);
            saveUsers();
        } else {
            // Aggiorna un utente esistente solo se il punteggio del nuovo profilo è inferiore
            UserProfile existingProfile = users.get(profile.getUsername());
            if (existingProfile.getPunteggio() > profile.getPunteggio()) {
                if (existingProfile.getRound() < profile.getRound()) {
                    profile.setRound(existingProfile.getRound());
                }
                profile.setPunteggio(existingProfile.getPunteggio());
            }
            profile.setPartiteVinte(existingProfile.getPartiteVinte() + profile.getPartiteVinte());
            profile.setPartitePerse(existingProfile.getPartitePerse() + profile.getPartitePerse());
            profile.setPartiteTot(existingProfile.getPartiteTot() + profile.getPartiteTot());
            users.put(profile.getUsername(), profile);
            saveUsers();
        }
    }

    /**
     * Salva la mappa dei profili utente in un file serializzato.
     */
    public void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            // Scrive la mappa di profili utente nel file
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ritorna una lista di profili utente ordinati in base al punteggio,
     * in ordine decrescente. (Se il puntaggio è uguale, ordina per round.)
     *
     * @return Lista di profili utente ordinati per punteggio e round.
     */
    public List<UserProfile> getSortedUsers() {
        users = loadUsers();
        if (users != null) {
            return users.values().stream()
                    .sorted(Comparator.comparingInt(UserProfile::getPunteggio)
                            .reversed()
                            .thenComparing(Comparator.comparingInt(UserProfile::getRound
                            ).reversed()))
                    .collect(Collectors.toList());
        }
        return null;
    }
}
