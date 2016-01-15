package com.superduckinvaders.game.objective;

import com.superduckinvaders.game.entity.Entity;
import com.superduckinvaders.game.round.Round;

/**
 * Represents an objective involving collecting a specific item.
 */
public class CollectObjective extends Objective {

    /**
     * The entity that needs to be collected.
     */
    private Entity target;

    /**
     * Initialises this CollectObjective.
     *
     * @param parent the round this CollectObjective belongs to
     * @param target the entity that needs to be collected
     */
    public CollectObjective(Round parent, Entity target) {
        super(parent);

        this.target = target;
    }

    /**
     * Gets a string describing this CollectObjective to be printed on screen.
     *
     * @return a string describing this CollectObjective
     */
    @Override
    public String getObjectiveString() {
        return "Collect the object";
    }

    /**
     * Updates the status towards this CollectObjective.
     *
     * @param delta how much time has passed since the last update
     */
    @Override
    public void update(float delta) {
        if (parent.getPlayer().intersects(target.getX(), target.getY(), target.getWidth(), target.getHeight())) {
            status = OBJECTIVE_COMPLETED;
        }
    }
}