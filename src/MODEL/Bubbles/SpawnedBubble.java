package MODEL.Bubbles;

import GAMESTATEMANAGER.GameStateManager;
import MODEL.Block;
import MODEL.Player;
import VIEW.MainFrame;
import VIEW.SpawnedBubbleView;

import java.util.Random;

//classe per le bolle che spawnano automaticamente (non vengono sparate dal Player)
public abstract class SpawnedBubble extends Bubble {

    public SpawnedBubble(Player player) {
        super(player);
        shootingSpeed = 7;
        floatingSpeed = 1;
        //this.bubbleView = new SpawnedBubbleView(this); ricordare di settarla nelle sottoclassi
        floating = true;
        Random random = new Random();
        x = random.nextInt(Block.WIDTH + 30, MainFrame.FRAME_HEIGHT - Block.WIDTH - 30);
        y = random.nextInt(Block.HEIGHT + 30, MainFrame.FRAME_HEIGHT - Block.HEIGHT - 30);

        //updateLocation che setta le coordinate dove spawnano (prob random)
    }

    public abstract void startEffect();
    public abstract void updateEffectLocation();

    @Override
    public void updateLocation(int newX, int newY) {
        x = newX;
        y = newY;
        hitbox.setLocation(x, y);

        if (player == null) player = GameStateManager.getInstance().getCurrentPlayer();

        if (floating && player != null) {
            if (hitbox.intersects(player.getHitbox())) explode();
        }
    }
}
