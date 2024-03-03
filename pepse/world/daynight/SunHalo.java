package pepse.util.pepse.world.daynight;

import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import danogl.GameObject;
import java.awt.*;


public class SunHalo {
    private static final float HALO_SIZE = 100;
    public static GameObject create(GameObject sun){
        Vector2 initialPosition = sun.getCenter();
        float haloSize = HALO_SIZE;
        OvalRenderable haloRenderable = new OvalRenderable(new Color(255, 255, 0, 20));

        GameObject sunHalo = new GameObject(initialPosition, new Vector2(haloSize, haloSize),
                haloRenderable);

        sunHalo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sunHalo.setTag("sunHalo");

        sunHalo.addComponent(deltaTime -> sunHalo.setCenter(sun.getCenter()));

        return sunHalo;
    }

}
