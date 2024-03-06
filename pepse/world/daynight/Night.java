package pepse.util.pepse.world.daynight;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import danogl.GameObject;

import java.awt.*;

/**
 * This class handles the day and night animation
 */
public class Night {
    private static final Float MIDNIGHT_OPACITY = 0.5f;

    /**
     * Creates a day background that turns to night
     * @param windowDimensions dimensions of window
     * @param cycleLength how much time the animation has to be
     * @return a game object to be added to the gameobjects()
     */
    public static GameObject create(Vector2 windowDimensions,
                                    float cycleLength){
        GameObject night = new GameObject(
                Vector2.ZERO, windowDimensions,
                new RectangleRenderable(Color.BLACK));
        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        night.setTag("night");
        new Transition<Float>(
                night, // the game object being changed
                night.renderer()::setOpaqueness, // the method to call
                0f, // initial transition value
                MIDNIGHT_OPACITY, // final transition value
                Transition.CUBIC_INTERPOLATOR_FLOAT,// use a cubic interpolator
                cycleLength / 2, // transition fully over half a day
        Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
        null);// nothing further to execute upon reaching final value
        return night;
    }

}
