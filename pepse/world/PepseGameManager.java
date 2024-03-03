package pepse.util.pepse.world;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import pepse.util.pepse.world.daynight.Night;
import pepse.util.pepse.world.daynight.Sun;
import pepse.util.pepse.world.daynight.SunHalo;

import java.util.List;

public class PepseGameManager extends GameManager {
    private static final float DAY_CYCLE_LENGTH = 30;
    public static void main(String[] args) {
        new PepseGameManager().run();
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        GameObject sky = Sky.create(windowController.getWindowDimensions());
        // TODO In pdf they said to add it to "skylayer" but no idea what that is
        gameObjects().addGameObject(sky, Layer.BACKGROUND);
        createGround(windowController);
        GameObject sun = Sun.create(windowController.getWindowDimensions(), DAY_CYCLE_LENGTH);
        gameObjects().addGameObject(sun, Layer.BACKGROUND);
        GameObject night = Night.create(windowController.getWindowDimensions(), DAY_CYCLE_LENGTH);
        gameObjects().addGameObject(night, Layer.BACKGROUND);
        GameObject sunHalo = SunHalo.create(sun);
        gameObjects().addGameObject(sunHalo, Layer.BACKGROUND);


    }

    private void createGround(WindowController windowController) {
        Terrain terrain = new Terrain(windowController.getWindowDimensions(), 0);
        List<Block> ground = terrain.createInRange(0, (int) windowController.getWindowDimensions().x());
        for (Block block: ground) {
            gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
        }
    }
}
