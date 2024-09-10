package GAMESTATEMANAGER;


import VIEW.LosePanel;
import VIEW.MainFrame;
import VIEW.MenuPanel;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * LoseState gestisce lo stato di gioco in cui il giocatore ha perso la partita.
 * Mostra il pannello di sconfitta e gestisce l'input dell'utente per navigare
 * tra le opzioni del menu, come tornare al menu principale o visualizzare la classifica.
 */

public class LoseState extends GameState{

        private final GameStateManager gsm;  // Gestore degli stati di gioco
        private final LosePanel view= MainFrame.getLosePanel(); // Vista del pannello di sconfitta


        /**
         * Costruttore della classe LoseState.
         * Inizializza il gestore degli stati di gioco (gsm).
         *
         * @param gsm Il gestore degli stati di gioco.
         */
        public LoseState(GameStateManager gsm){this.gsm=gsm;}


        /**
         * Metodo per aggiornare lo stato di gioco.
         * In questo stato non è necessario aggiornare alcun componente.
         */
        public void update() {}


        /**
         * Disegna il pannello di sconfitta, impostandolo come pannello attivo.
         */
        public void draw() {
            MainFrame.setPanel(view);
        }



        @Override
        public void keyTyped(KeyEvent k) {

        }

        /**
         * Gestisce gli eventi quando un tasto viene premuto.
         * Naviga tra le opzioni del menu usando i tasti freccia su e giù,
         * e seleziona l'opzione corrente con il tasto Invio.
         *
         * @param k L'evento della pressione del tasto.
         */
        @Override
        public void keyPressed(KeyEvent k) {

                if (k.getKeyCode() == KeyEvent.VK_UP) {
                        view.cursorUp();  // Muove il cursore verso l'alto nel menu
                }
                if (k.getKeyCode() == KeyEvent.VK_DOWN) {
                        view.cursorDown();  // Muove il cursore verso il basso nel menu
                }
                if (k.getKeyCode() == KeyEvent.VK_ENTER) {
                        // Esegue l'azione in base all'opzione selezionata
                        switch (view.getSelectedOption()) {
                                case 0:
                                        gsm.resetGame(); // Torna al menu principale
                                        MainFrame.stopSound();
                                        gsm.setState(GameStateManager.menuState);
                                        break;

                                case 1:
                                        gsm.resetGame();
                                        MainFrame.stopSound();
                                        gsm.setState(GameStateManager.leaderboardState); // Mostra la classifica
                                        break;
                                case 2:
                                        System.exit(0);
                                        break;

                        }
                }

        }

        @Override
        public void keyReleased(KeyEvent k) {

        }

        @Override
        public void actionPerformed(ActionEvent k) {

        }

        @Override
        public void mouseClicked(MouseEvent k) {

        }

        @Override
        public void mousePressed(MouseEvent k) {

        }

        @Override
        public void mouseReleased(MouseEvent k) {

        }

        @Override
        public void mouseEntered(MouseEvent k) {

        }

        @Override
        public void mouseExited(MouseEvent k) {

        }
    }



