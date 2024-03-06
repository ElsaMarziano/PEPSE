package pepse.util.pepse.world.daynight;

import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import danogl.GameObject;
import java.awt.*;

/**
 * This class creates a halo for the sun hat moves with it
 */
public class SunHalo {
    private static final float HALO_SIZE = 100;
    private static final Color COLOR_OF_SUN_HALO = new Color(255, 255, 0, 20);

    /**
     * Creates a new halo for the sun
     * @param sun The sun object
     * @return A halo to add toe the game objects
     */
    public static GameObject create(GameObject sun){
        Vector2 initialPosition = sun.getCenter();
        float haloSize = HALO_SIZE;
        OvalRenderable haloRenderable = new OvalRenderable(COLOR_OF_SUN_HALO);
        GameObject sunHalo = new GameObject(initialPosition, new Vector2(haloSize, haloSize),
                haloRenderable);
        sunHalo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sunHalo.setTag("sunHalo");

        sunHalo.addComponent(deltaTime -> sunHalo.setCenter(sun.getCenter()));

        return sunHalo;
    }

}
