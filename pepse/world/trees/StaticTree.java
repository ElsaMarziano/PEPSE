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
            new Color(139, 69, 19),
            new Color(160, 82, 45),
            new Color(139, 115, 85)
    };

    public StaticTree(Vector2 topLeftCorner, float size) {
        super(topLeftCorner, new Vector2(Block.SIZE, size),
                new RectangleRenderable(new Color(100, 50, 20)
        ));
        this.treeColor = new Color(100, 50, 20);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }


    @Override
    public void notifyJump() {
        Random random = new Random();
        this.treeColor = brownShades[random.nextInt(brownShades.length)];
        this.renderer().setRenderable(new RectangleRenderable(this.treeColor));

    }

}
