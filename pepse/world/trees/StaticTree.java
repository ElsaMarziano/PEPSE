package pepse.util.pepse.world.trees;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.pepse.world.Block;

import java.awt.*;

public class StaticTree extends GameObject {

    public StaticTree(Vector2 topLeftCorner, float size) {
        super(topLeftCorner, new Vector2(Block.SIZE, size), new RectangleRenderable(new Color(100, 50, 20)
        ));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }

}
