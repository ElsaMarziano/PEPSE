package pepse.util.pepse.world.daynight;

import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import danogl.GameObject;


import java.awt.*;

/**
 * This class has a static method creating a sun GameObject
 */
public class Sun{
    private final static float SUN_SIZE = 50;

    /**
     * Create the sun
     *
     * @param windowDimensions the dimensions of the window
     * @param cycleLength the length of the sun
     * @return sun that is GameObject
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength){
        float centerX = 1* windowDimensions.x() / 2;
        float centerY = 1 * (windowDimensions.y()) / 3;
        Vector2 startTopLeftCorner = new Vector2(centerX, centerY);
        Vector2 dimensions = new Vector2(SUN_SIZE, SUN_SIZE);
        GameObject sun = new GameObject(startTopLeftCorner, dimensions,
                new OvalRenderable(Color.YELLOW));
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag("sun");
        float initialAngle = 0f;
        float finalAngle = 360f;
// Sun animation
        new Transition<Float>(
                sun,
                (Float angle) -> sun.setCenter
                        (startTopLeftCorner.subtract(new Vector2(windowDimensions.x() * 0.5f,
                                        windowDimensions.y() * ((float) 2 /3)))
                                .rotated(angle)
                                .add(new Vector2(windowDimensions.x() * 0.5f,
                                        windowDimensions.y()* ((float) 2/3)))),

                initialAngle, finalAngle,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                cycleLength,
                Transition.TransitionType.TRANSITION_LOOP,
                null);
        return sun;
    }
}
