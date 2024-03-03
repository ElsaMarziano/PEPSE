package pepse.util.pepse;

import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.security.Provider;
import java.util.function.Supplier;

public class EnergyCounter extends GameObject {
    private final Supplier<Double> callback;
    private final Vector2 leftCorner;
    private GameObject textObject;



    public EnergyCounter(Vector2 leftCorner, Supplier<Double> callback) {
        super(leftCorner, Vector2.ONES.mult(50),
                new TextRenderable(Double.toString(callback.get())));
        this.callback = callback;
        this.leftCorner = leftCorner;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        double currentEnergy = this.callback.get();
        TextRenderable renderedText = new TextRenderable(Double.toString(currentEnergy));
        this.renderer().setRenderable(renderedText);
    }


}
