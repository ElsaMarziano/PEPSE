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
        float centerY = 2 * (windowDimensions.y() - SUN_SIZE) / 3;

        Vector2 startTopLeftCorner = new Vector2(centerX, centerY);
        Vector2 dimensions = new Vector2(SUN_SIZE, SUN_SIZE);
        GameObject sun = new GameObject(startTopLeftCorner, dimensions,
                new OvalRenderable(Color.YELLOW));

        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag("sun");


        float initialAngle = 0f;
        float finalAngle = 360f;

        float angularVelocity = finalAngle / cycleLength;

        new Transition<Float>(
                sun,
                value -> {
                    // Calculate the new position based on the current time
                    double angle = initialAngle + (angularVelocity * value) % 360;
                    double angleRad = Math.toRadians(angle);
                    double radius = Math.min(windowDimensions.x() / 2, centerY) - SUN_SIZE / 2;
                    double newX = centerX + radius * Math.cos(angleRad);
                    double newY = centerY + radius * Math.sin(angleRad);
                    sun.setCenter(new Vector2((float)newX, (float)newY));
                },
                initialAngle, finalAngle,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                cycleLength * 10,
                Transition.TransitionType.TRANSITION_LOOP,
                null);


        return sun;
    }


}
