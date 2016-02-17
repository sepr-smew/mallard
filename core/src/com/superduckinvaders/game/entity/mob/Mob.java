
package com.superduckinvaders.game.entity.mob;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.ai.AI;
import com.superduckinvaders.game.ai.DummyAI;
import com.superduckinvaders.game.assets.TextureSet;
import com.superduckinvaders.game.entity.Character;
import com.superduckinvaders.game.entity.Player;

public class Mob extends Character {

    // TODO: finish me
    /**
     * The texture set to use for this Mob.
     */
    private TextureSet textureSet;
    
    /**
     * AI class for the mob
     */
    private AI ai;

    /**
     * speed of the mob in pixels per second
     */
    private float speed;

    public Mob(Round parent, float x, float y, int health, TextureSet textureSet, int speed, AI ai) {
        super(parent, x, y, health);

        MELEE_RANGE = 30f;

        this.textureSet = textureSet;
        this.speed = speed;
        this.ai = ai;
        
        this.categoryBits = MOB_BITS;
        this.enemyBits = PLAYER_BITS;
        
        createDynamicBody(MOB_BITS, (short)(ALL_BITS & (~MOB_BITS)), MOB_GROUP, false);
        this.body.setLinearDamping(20f);
    }

    public Mob(Round parent, int x, int y, int health, TextureSet textureSet, int speed) {
        this(parent, x, y, health, textureSet, speed, new DummyAI(parent));
    }
    
    /**
     * Sets the AI for this Mob.
     * @param ai the new AI to use
     */
    public void setAI(AI ai) {
        this.ai = ai;
    }
    
    /**
     * Sets the speed of the mob
     * @param speed the updated speed
     */
    public void setSpeed(float speed){
        this.speed = speed;
    }
    public float getSpeed(){
        return this.speed;
    }
    
    @Override
    public float getWidth() {
        return textureSet.getHeight();
    }

    @Override
    public float getHeight() {
        return textureSet.getHeight();
    }

    @Override
    public void update(float delta) {
        ai.update(this, delta);

        // Chance of spawning a random powerup.
        if (isDead()) {
            Player.Pickup pickup = Player.Pickup.random();
            if (pickup != null) {
                parent.createPickup(getX(), getY(), pickup);
            }
        }

        super.update(delta);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(textureSet.getTexture(facing, stateTime), getX(), getY());
    }

    public void applyVelocity(Vector2 destination){
        Vector2 velocity = destination.sub(getCentre())
                               .nor().scl(getSpeed());
        if (isStunned()){
            velocity.scl(0.4f);
        }
        setVelocityClamped(velocity);
    }
}
