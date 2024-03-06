package pepse.util.pepse.world.trees;

import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.pepse.util.ColorSupplier;
import pepse.util.pepse.world.AvatarObserver;
import pepse.util.pepse.world.Block;

import java.awt.*;
import java.util.Random;

public class Leaf extends GameObject implements AvatarObserver {
    private static final Color LEAVE_COLOR = new Color(50, 200, 30);

    public Leaf(Vector2 pos){
        super(pos, Vector2.ONES.mult(Block.SIZE),
                new RectangleRenderable(ColorSupplier.approximateColor(LEAVE_COLOR)));
        Random random = new Random();
        new ScheduledTask( this,  random.nextFloat(),false,
                () -> new Transition<Float>(
                        this, this.renderer()::setRenderableAngle,
                        175f, 195f,
                        Transition.CUBIC_INTERPOLATOR_FLOAT, 4f,
                        Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                        null));
        ;
    }
    @Override
    public void notifyJump() {
        new Transition<Float>(
            this, this.renderer()::setRenderableAngle,
            0f, 90f,
            Transition.CUBIC_INTERPOLATOR_FLOAT, 4f,
            Transition.TransitionType.TRANSITION_ONCE,
            null);
    }
}
