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
    public static GameObject create(Vector2 windowDimensions,
                                    float cycleLength){
        float centerX = (windowDimensions.x() - SUN_SIZE) / 2;
        float centerY = (windowDimensions.y() - SUN_SIZE) / 2;

        Vector2 startTopLeftCorner = new Vector2(centerX, centerY);
        Vector2 dimensions = new Vector2(SUN_SIZE, SUN_SIZE);
        GameObject sun = new GameObject(startTopLeftCorner, dimensions,
                new OvalRenderable(Color.YELLOW));

        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag("sun");


        float initialAngle = 0f;
        float finalAngle = 360f;

        new Transition<Float>(
                sun,
                value -> {
                    // Calculate the new position based on the current time
                    double timeRatio = value / cycleLength;
                    double angle = 2 * Math.PI * timeRatio;
                    double radius = Math.min(windowDimensions.x(),
                            windowDimensions.y()) / 2 - SUN_SIZE / 2;
                    double newX = centerX + radius * Math.cos(angle);
                    double newY = centerY + radius * Math.sin(angle);
                    sun.setCenter(new Vector2((float)newX, (float)newY));
                },
                initialAngle, finalAngle,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                cycleLength,
                Transition.TransitionType.TRANSITION_LOOP,
                null);

        return sun;
    }


}
