package MODEL.Bubbles;

import MODEL.Enemy.Enemy;
import VIEW.MainFrame;
import VIEW.SpawnedBubbleView;

import javax.swing.*;
public class LightningBubble extends SpawnedBubble{
    int lightningSpeed = 8;
    {skinsPath = "/Resources/Bubble Bobble Resources/Bubbles/lightning";}
    public LightningBubble(){
        super();

        bubbleView = new SpawnedBubbleView(this);
        hitbox.setSize(44, 45);
    }

    @Override
    public void startEffect() {
        if (x <= MainFrame.FRAME_WIDTH/2) facingRight = true;
        else facingRight = false;
        bubbleView.setLightningIMG();
        hitbox.setSize(30,30);
        effect = true;
    }

    @Override
    public void updateEffectLocation() {
        if (facingRight) x += lightningSpeed;
        else x -= lightningSpeed;
        hitbox.setLocation(x, y);

        if (isSolidTile(x, y)) {
            hitWall = true;
            effect = false;
            explode();

        }

        for (Enemy enemy : currentLevel.getEnemies()) {
            if (enemy != null && hitbox.intersects(enemy.getHitbox())) {
                bubbledEnemy = enemy;
                enemy.updateAction(MODEL.Entity.Action.DIE);
                effect = false;
            }
        }
    }


    @Override
    public Bubble newInstance() {
        return new LightningBubble();
    }
}

//fixxa immagini e hitboxx