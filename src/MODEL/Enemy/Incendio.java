package MODEL.Enemy;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Bubbles.IncendioFireball;
import MODEL.Entity;

/**
 * La classe {@code Incendio} rappresenta un nemico nel gioco che si muove velocemente, ha capacità limitate di salto e spara palle di fuoco.
 */
public class Incendio extends Enemy {

    //cammina veloce
    //scarso nel salto
    //spara palle di fuoco
    //continua a camminare quando spara

    private final int points=3000;

    /**
     * Crea un'istanza di {@code Incendio} con la posizione e la direzione specificate.
     *
     * @param x La posizione x del nemico.
     * @param y La posizione y del nemico.
     * @param facingRight Indica se il nemico sta guardando verso destra.
     * @param gsm Il gestore dello stato del gioco.
     */
    public Incendio(int x, int y, boolean facingRight, GameStateManager gsm) {
        super(x, y, facingRight, gsm);
        speed = 3;
    }

    /**
     * Crea un'istanza di {@code Incendio} con valori predefiniti per la posizione e la direzione.
     *
     * @param gsm Il gestore dello stato del gioco.
     */
    public Incendio(GameStateManager gsm) {
        super(gsm);
    }

    /**
     * Gestisce lo stato del nemico quando è all'interno di una bolla.
     * Ferma il timer di attacco, imposta il nemico come bubbled, avvia il timer di rabbia e notifica gli osservatori.
     */
    /**
     * La classe LeaderboardState gestisce lo stato della leaderboard nel gioco.
     * Mostra i punteggi dei giocatori e offre la possibilità di tornare al menu principale o uscire dal gioco.
     */
    public class LeaderboardState extends GameState {

        // Gestore degli stati del gioco
        private GameStateManager gsm;

        // Controller della leaderboard
        private Leaderboard leaderboardController;

        // Modello della leaderboard
        private LeaderboardM model;

        // Pannello grafico della leaderboard
        private LeaderboardPanel leaderboardPanel;

        /**
         * Costruttore che inizializza lo stato della leaderboard.
         * Recupera il controller, il modello e il pannello della leaderboard, e aggiunge i listener ai pulsanti del pannello.
         *
         * @param gsm il gestore degli stati del gioco
         */
        public LeaderboardState(GameStateManager gsm) {
            this.gsm = gsm;
            leaderboardController = gsm.getLeaderboard();
            model = leaderboardController.getModel();
            leaderboardPanel = MainFrame.getLeaderboardPanel();
            addListeners(); // Aggiunge i listener ai pulsanti
        }

        /**
         * Aggiunge i listener agli elementi del pannello della leaderboard.
         * Gestisce le azioni per uscire dall'applicazione o tornare al menu principale.
         */
        private void addListeners() {
            leaderboardPanel.getExitLabel().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Logica per uscire dall'applicazione
                    System.exit(0); // Uscita dall'applicazione
                }
            });

            leaderboardPanel.getMenuLabel().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Logica per tornare al menu principale
                    gsm.setState(gsm.menuState); // Torna al menu principale
                }
            });
        }

        /**
         * Metodo di aggiornamento che viene chiamato ad ogni frame di gioco.
         * Attualmente vuoto, ma può essere usato per aggiornare la logica della leaderboard.
         */
        @Override
        public void update() {
            // Implementa la logica di aggiornamento se necessaria
        }

        /**
         * Disegna il pannello della leaderboard nella finestra principale.
         */
        @Override
        public void draw() {
            MainFrame.setPanel(leaderboardPanel);
        }

        /**
         * Gestisce gli eventi ActionEvent.
         * Attualmente non utilizzato, ma disponibile per eventuali esigenze future.
         *
         * @param e l'evento ActionEvent
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // Gestione degli eventi ActionEvent se necessario
        }

        /**
         * Gestisce gli eventi di digitazione dei tasti.
         * Attualmente non utilizzato.
         *
         * @param e l'evento KeyEvent
         */
        @Override
        public void keyTyped(KeyEvent e) {
            // Gestione degli eventi KeyEvent se necessario
        }

        /**
         * Gestisce gli eventi di pressione dei tasti.
         * Attualmente non utilizzato.
         *
         * @param e l'evento KeyEvent
         */
        @Override
        public void keyPressed(KeyEvent e) {
            // Gestione degli eventi KeyEvent se necessario
        }

        /**
         * Gestisce gli eventi di rilascio dei tasti.
         * Attualmente non utilizzato.
         *
         * @param e l'evento KeyEvent
         */
        @Override
        public void keyReleased(KeyEvent e) {
            // Gestione degli eventi KeyEvent se necessario
        }

        /**
         * Gestisce gli eventi di clic del mouse.
         * Attualmente non utilizzato.
         *
         * @param e l'evento MouseEvent
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            // Gestione degli eventi MouseEvent se necessario
        }

        /**
         * Gestisce l'evento di pressione del mouse.
         * Attualmente non utilizzato.
         *
         * @param e l'evento MouseEvent
         */
        @Override
        public void mousePressed(MouseEvent e) {
            // Gestione degli eventi MouseEvent se necessario
        }

        /**
         * Gestisce l'evento di rilascio del mouse.
         * Attualmente non utilizzato.
         *
         * @param e l'evento MouseEvent
         */
        @Override
        public void mouseReleased(MouseEvent e) {
            // Gestione degli eventi MouseEvent se necessario
        }

        /**
         * Gestisce l'evento di ingresso del mouse nel componente.
         * Attualmente non utilizzato.
         *
         * @param e l'evento MouseEvent
         */
        @Override
        public void mouseEntered(MouseEvent e) {
            // Gestione degli eventi MouseEvent se necessario
        }

        /**
         * Gestisce l'evento di uscita del mouse dal componente.
         * Attualmente non utilizzato.
         *
         * @param e l'evento MouseEvent
         */
        @Override
        public void mouseExited(MouseEvent e) {
            // Gestione degli eventi MouseEvent se necessario
        }
    }

    }
/**
 * Gestisce il movimento del nemico.
 * Se il nemico è bubbled o morto, notifica gli osservatori con le azioni appropriate.
 * Altrimenti, se il nemico è vivo e non bubbled, gestisce l'attacco e l'inseguimento del giocatore.
 */
    public void move(){
        if(player.getLives()<=0){
            stop();
        }
        if(isBubbled()) {
            notifyObservers(Action.BUBBLED);
        }
        else if(isDead() &&!isBubbled()){
            notifyObservers(Action.DIE);
        }
        if(!isBubbled() && !isDead()){
            if(player.getY() != y && attackTimer.isRunning()){
                attackTimer.stop();
            }
            else if(player.getY() == y){
                updateAction(Action.ATTACK);
            }
            onPlayer();
            chasePlayer();
        }
    }

    /**
     * Il nemico spara una palla di fuoco.
     * Crea un'istanza di {@code IncendioFireball}, la aggiunge al livello corrente e la spara.
     */
    void shoot() {
        // to do
        System.out.println("Incendio shoot");
        IncendioFireball fireball = new IncendioFireball(player, this);
        currentLevel.addBubble(fireball);
        fireball.fireBubble();
    }

    /**
     * Attiva lo stato di rabbia del nemico.
     * Imposta il nemico come enraged, aumenta la velocità e modifica il ritardo tra gli attacchi.
     */
    void rage() {
        enraged = true;
        bubbled = false;
        speed += 1;
        attackTimer.setDelay(800);
    }
}


