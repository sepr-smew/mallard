package com.superduckinvaders.game.entity.mob;

import com.badlogic.gdx.math.Vector2;
import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.ai.AI;
import com.superduckinvaders.game.ai.PathfindingAI;
import com.superduckinvaders.game.assets.TextureSet;

/**
 * A Base for a Mob that prefers guns to punches (or rational discussion!). Must be American.
 */
public class RangedMob extends MeleeMob {

    /**
     * The range to stop and shoot at the player.
     * Anywhere outside this range, the mob won't shoot at the player.
     */
    public static float range = 1280 / 4f;

    /**
     * Create a new RangedMob.
     * @param parent     the round parent.
     * @param x          the initial x position.
     * @param y          the initial y position.
     * @param health     the starting health.
     * @param textureSet a TextureSet to use for displaying.
     * @param speed      the speed to approach the player.
     * @param ai         the AI type to use.
     */
    public RangedMob(Round parent, float x, float y, int health, TextureSet textureSet, int speed, AI ai) {
        super(parent, x, y, health, textureSet, speed, ai);
    }

    /**
     * Create a new Mob with a default PathfindingAI.
     * @param parent     the round parent.
     * @param x          the initial x position.
     * @param y          the initial y position.
     * @param health     the starting health.
     * @param textureSet a TextureSet to use for displaying.
     * @param speed      the speed to approach the player.
     */
    public RangedMob(Round parent, float x, float y, int health, TextureSet textureSet, int speed) {
        super(parent, x, y, health, textureSet, speed, new PathfindingAI(parent, 200));
    }
    
    @Override
    public void update(float delta){
        super.update(delta);
        Vector2 playerPos = parent.getPlayer().getCentre();
        if (distanceTo(playerPos) < range && parent.rayCast(getCentre(), playerPos)) {
            rangedAttack(vectorTo(playerPos), 1);
        }
    }
}
