package pepse.util.pepse.world.trees;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.pepse.world.AvatarObserver;
import pepse.util.pepse.world.Block;

import java.awt.*;
import java.util.Random;

public class StaticTree extends GameObject  implements AvatarObserver {
    private Color treeColor;

    private Color[] brownShades = {
            new Color(152, 97, 70),
            new Color(160, 82, 45),
            new Color(72, 50, 25)
    };

    public StaticTree(Vector2 topLeftCorner, float size) {
        super(topLeftCorner, new Vector2(Block.SIZE, size),
                new RectangleRenderable(new Color(100, 50, 20)
        ));
        this.treeColor = new Color(100, 50, 20);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }

    /**
     * When the Avatar is jump - the trees change there brown shade.
     */
    @Override
    public void notifyJump() {
        Random random = new Random();
        this.treeColor = brownShades[random.nextInt(brownShades.length)];
        this.renderer().setRenderable(new RectangleRenderable(this.treeColor));

    }

}
