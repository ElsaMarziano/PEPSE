package pepse.util.pepse;

import danogl.GameObject;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.util.function.Supplier;

/**
 * Represents an energy counter GameObject that displays the current energy value.
 */
public class EnergyCounter extends GameObject {
    private final Supplier<Double> callback;


    /**
     * Constructs an EnergyCounter object with the specified position and energy value
     * callback.
     *
     * @param leftCorner The position of the energy counter.
     * @param callback   A supplier function that provides the current energy value.
     */
    public EnergyCounter(Vector2 leftCorner, Supplier<Double> callback) {
        super(leftCorner, Vector2.ONES.mult(50),
                new TextRenderable(Double.toString(callback.get())));
        this.callback = callback;
    }

    /**
     * Updates the energy counter's displayed value.
     *
     * @param deltaTime The time elapsed since the last update, in seconds.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        double currentEnergy = this.callback.get();
        TextRenderable renderedText = new TextRenderable(Double.toString(currentEnergy));
        this.renderer().setRenderable(renderedText);
    }
}
