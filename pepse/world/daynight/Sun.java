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
public class Sun {
    private static final float SUN_DIAMETER = 50;
    private static final float SUN_POSITION_X_RATIO = 0.5f;
    private static final float SUN_POSITION_Y_RATIO = 1 / 3f;
    private static final float SUN_ANIMATION_START_ANGLE = 0f;
    private static final float SUN_ANIMATION_END_ANGLE = 360f;

    /**
     * Create the sun
     *
     * @param windowDimensions the dimensions of the window
     * @param cycleLength      the length of the sun
     * @return sun that is GameObject
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength) {
        float centerX = SUN_POSITION_X_RATIO * windowDimensions.x();
        float centerY = SUN_POSITION_Y_RATIO * windowDimensions.y();
        Vector2 startTopLeftCorner = new Vector2(centerX, centerY);
        Vector2 dimensions = new Vector2(SUN_DIAMETER, SUN_DIAMETER);
        GameObject sun = new GameObject(startTopLeftCorner, dimensions,
                new OvalRenderable(Color.YELLOW));
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag("sun");

        // Sun animation
        new Transition<Float>(
                sun,
                (Float angle) -> sun.setCenter(startTopLeftCorner
                        .subtract(new Vector2(windowDimensions.x() * SUN_POSITION_X_RATIO,
                                2* windowDimensions.y() * SUN_POSITION_Y_RATIO))
                        .rotated(angle)
                        .add(new Vector2(windowDimensions.x() * SUN_POSITION_X_RATIO,
                                2 * windowDimensions.y() * SUN_POSITION_Y_RATIO))),
                SUN_ANIMATION_START_ANGLE, SUN_ANIMATION_END_ANGLE,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                cycleLength,
                Transition.TransitionType.TRANSITION_LOOP,
                null);

        return sun;
    }
}
