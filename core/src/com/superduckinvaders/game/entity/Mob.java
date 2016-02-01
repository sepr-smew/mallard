
package com.superduckinvaders.game.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.superduckinvaders.game.Round;
import com.superduckinvaders.game.ai.AI;
import com.superduckinvaders.game.ai.DummyAI;
import com.superduckinvaders.game.ai.PathfindingAI;
import com.superduckinvaders.game.assets.TextureSet;

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
     * checks whether mob should be updated
     */
    private boolean active = false;
    /**
     * speed of the mob in pixels per second
     */
    private float speed;
    
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    public Mob(Round parent, float x, float y, int health, TextureSet textureSet, int speed, AI ai) {
        super(parent, x, y, health);

        this.textureSet = textureSet;
        this.speed = speed;
        this.ai = ai;
        
        this.categoryBits = MOB_BITS;
        
        createDynamicBody(MOB_BITS, ALL_BITS, MOB_GROUP, false);
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
     * @param newSpeed the updated speed
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
    
    /**
     * change where the given mob moves to according to its speed and a new direction vector
     * @param dirX x component of the direction vector
     * @param dirY y component of the direction vector
     */

    @Override
    public void update(float delta) {
        ai.update(this, delta);

        // Chance of spawning a random powerup.
        if (isDead()) {
            float random = MathUtils.random();
            Player.Powerup powerup = null;

            if (random < 0.05) {
                powerup = Player.Powerup.SCORE_MULTIPLIER;
            } else if (random >= 0.05 && random < 0.1) {
                powerup = Player.Powerup.INVULNERABLE;
            } else if (random >= 0.1 && random < 0.15) {
                powerup = Player.Powerup.SUPER_SPEED;
            } else if (random >= 0.15 && random < 0.2) {
                powerup = Player.Powerup.RATE_OF_FIRE;
            }

            if (powerup != null) {
                parent.createPowerup(getX(), getY(), powerup, 10);
            }
        }

        super.update(delta);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(textureSet.getTexture(facing, stateTime), getX(), getY());
        
        if (ai instanceof PathfindingAI) {
            Vector2 nodePos = ((PathfindingAI)ai).nodePos;
            if (nodePos != null) {
                spriteBatch.end();
                shapeRenderer.setProjectionMatrix(new Matrix4(spriteBatch.getProjectionMatrix()));
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(1, 1, 0, 1);
                shapeRenderer.x(nodePos, 10);
                shapeRenderer.end();
                spriteBatch.begin();
            }            
        }
    }
}
