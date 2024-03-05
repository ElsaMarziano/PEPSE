package pepse.util.pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.ScheduledTask;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.util.pepse.world.Block;
import pepse.util.pepse.world.PepseGameManager;

import java.awt.*;
import java.util.function.Consumer;

/**
 * This class creates a single fruit that does something to the avatar when colliding with it,
 * according to the function passed as a parameter
 */
public class Fruit extends GameObject {
    private static final Color FRUIT_COLOR = new Color(246, 11, 11);
    private final Runnable onCollidedWithAvatar;

    /**
     * Constructor for the fruit class
     * @param pos position of the fruit
     * @param onCollidedWithAvatar what to do when colliding with avatar
     */
    public Fruit(Vector2 pos, Runnable onCollidedWithAvatar) {
        super(pos, Vector2.ONES.mult(Block.SIZE), new OvalRenderable(FRUIT_COLOR));
        this.onCollidedWithAvatar = onCollidedWithAvatar;
    }

    /**
     * Handles collision with avatar, disappears when collidin with it an reapearring only after
     * 30 seconds
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        // Check we collided with avatar and that the fruit exist
        if(other.getTag().equals("Avatar") && this.renderer().getRenderable() != null){
            this.onCollidedWithAvatar.run();
            this.renderer().setRenderable(null);
            new ScheduledTask(this, PepseGameManager.DAY_CYCLE_LENGTH,false,
                    () -> this.renderer().setRenderable(new OvalRenderable(FRUIT_COLOR)));
        }
    }

}
