package MODEL;


/**
 * L'interfaccia {@code Entity} rappresenta un'entità nel gioco. Definisce le costanti
 * comuni a tutte le entità e i metodi che devono essere implementati per gestire
 * le azioni dell'entità.
 */
public interface Entity {

    /** Larghezza predefinita dell'entità, in pixel. */
    static final int WIDTH = 32;

    /** Altezza predefinita dell'entità, in pixel. */
    static final int HEIGHT = 32;

    /**
     * L'enum {@code Action} definisce le possibili azioni che un'entità può compiere
     * nel gioco.
     */
    public enum Action {
        /** L'entità è ferma senza compiere alcuna azione. */
        IDLE,
        /** L'entità sta saltando. */
        JUMP,
        /** L'entità sta camminando. */
        WALK,
        /** L'entità si sta muovendo verticalmente. */
        MOVE_VERTICALLY,
        /** L'entità si sta muovendo verso sinistra. */
        MOVE_LEFT,
        /** L'entità si sta muovendo verso destra. */
        MOVE_RIGHT,
        /** L'entità sta attaccando. */
        ATTACK,
        /** L'entità è stata ferita. */
        HURT,
        /** L'entità sta morendo. */
        DIE,
        /** L'entità è in uno stato di rabbia. */
        RAGE,
        /** L'entità è intrappolata in una bolla. */
        BUBBLED
    }

    /** Velocità di salto predefinita dell'entità. */
    float jumpSpeed = -7f;

    /** Gravità predefinita applicata all'entità. */
    float gravity = 0.3f;

    /**
     * Aggiorna l'azione attuale dell'entità.
     *
     * @param action l'azione che l'entità deve compiere
     */
    public void updateAction(Action action);
}