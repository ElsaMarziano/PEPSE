package pepse.util.pepse.world;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * This class represent a single block of constant size that doesn't move when collided with
 */
public class Block extends GameObject {
    public static final int SIZE = 30;

    /**
     * Create a single block that doesn't move
     * @param topLeftCorner block location
     * @param renderable What the block needs to look like
     */
    public Block(Vector2 topLeftCorner, Renderable renderable) {
        super(topLeftCorner, Vector2.ONES.mult(SIZE), renderable);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }
}

