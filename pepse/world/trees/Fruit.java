package pepse.util.pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.Component;
import danogl.components.ScheduledTask;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.util.pepse.world.Block;

import java.awt.*;
import java.util.function.Consumer;

public class Fruit extends GameObject {
    private static final Color FRUIT_COLOR = new Color(246, 11, 11);
    private final Consumer<Integer> onCollidedWithAvatar;

    public Fruit(Vector2 pos, Consumer<Integer> onCollidedWithAvatar) {
        super(pos, Vector2.ONES.mult(Block.SIZE), new OvalRenderable(FRUIT_COLOR));
        this.onCollidedWithAvatar = onCollidedWithAvatar;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if(other.getTag().equals("Avatar") && this.renderer().getRenderable() != null){
            this.onCollidedWithAvatar.accept(10);
            this.renderer().setRenderable(null);
            new ScheduledTask(this,  30f,false,
                    () -> this.renderer().setRenderable(new OvalRenderable(FRUIT_COLOR))
                    );
        }
    }

}
