package pepse.util.pepse.world.trees;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.pepse.world.Block;

import java.awt.*;

/**
 * This class create the base of a static tree, without leaves
 */
public class StaticTree extends GameObject {

    /**
     * Creates a rectangle of the given size, and make it prevent interaction and be immovable
     * @param topLeftCorner position
     * @param size height of the tree
     */
    public StaticTree(Vector2 topLeftCorner, float size) {
        super(topLeftCorner, new Vector2(Block.SIZE, size), new RectangleRenderable(new Color(100, 50, 20)
        ));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }

}
