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

/**
 * Represents a leaf GameObject that reacts to the avatar's jump action.
 */
public class Leaf extends GameObject implements AvatarObserver {
    private static final Color LEAVE_COLOR = new Color(50, 200, 30);

    /**
     * Constructs a Leaf object at the specified position.
     * The leaf will sway back and forth periodically.
     *
     * @param pos The position of the leaf.
     */
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
    /**
     * Notifies the leaf about the avatar's jump action.
     * This method rotates the leaf vertically when the avatar jumps.
     */
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
