package pepse.util.pepse.world;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;
import pepse.util.pepse.EnergyCounter;
import pepse.util.pepse.world.daynight.Night;
import pepse.util.pepse.world.daynight.Sun;
import pepse.util.pepse.world.daynight.SunHalo;
import pepse.util.pepse.world.trees.Flora;

import java.util.List;
import java.util.Map;

/**
 * This class creates a Pepse Game and manages it
 */
public class PepseGameManager extends GameManager {
    /**
     * Default cycle length
     */
    public static final float DAY_CYCLE_LENGTH = 30;
    private Terrain terrain;

    /**
     * Runs game
     * @param args command line arguments
     */
    public static void main(String[] args) {
        new PepseGameManager().run();
    }

    /**
     * This function initializes a game, calls to every needed function that creates something
     * and add it to the gameobjects
     * @param imageReader Contains a single method: readImage, which reads an image from disk.
     *                 See its documentation for help.
     * @param soundReader Contains a single method: readSound, which reads a wav file from
     *                    disk. See its documentation for help.
     * @param inputListener Contains a single method: isKeyPressed, which returns whether
     *                      a given key is currently pressed by the user or not. See its
     *                      documentation.
     * @param windowController Contains an array of helpful, self explanatory methods
     *                         concerning the window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        float window_x = windowController.getWindowDimensions().x();
        GameObject sky = Sky.create(windowController.getWindowDimensions());
        gameObjects().addGameObject(sky, Layer.BACKGROUND);
        createGround(windowController);
        GameObject sun = Sun.create(windowController.getWindowDimensions(), DAY_CYCLE_LENGTH);
        gameObjects().addGameObject(sun, Layer.BACKGROUND);
        GameObject night = Night.create(windowController.getWindowDimensions(), DAY_CYCLE_LENGTH);
        gameObjects().addGameObject(night, Layer.BACKGROUND);
        GameObject sunHalo = SunHalo.create(sun);
        gameObjects().addGameObject(sunHalo, Layer.BACKGROUND);
        Avatar avatar = new Avatar(new Vector2(window_x - Avatar.AVATAR_DIMENSIONS,
                                terrain.groundHeightAt(window_x) - Avatar.AVATAR_DIMENSIONS),
                inputListener, imageReader);
        gameObjects().addGameObject(avatar);
        GameObject energyCounter = new EnergyCounter(Vector2.ONES.mult(50),
                avatar::getCurrentEnergy);
        gameObjects().addGameObject(energyCounter, Layer.STATIC_OBJECTS);
        Flora flora = new Flora(terrain::groundHeightAt, avatar::add10Energy);
        Map<String, List<GameObject>> objectsToAdd = flora.createInRange(0, (int) window_x);
        for (Map.Entry<String, List<GameObject>> objects: objectsToAdd.entrySet()) {
            int layerToAdd = Layer.DEFAULT;
            if(objects.getKey().equals("Trees")) layerToAdd = Layer.STATIC_OBJECTS;
            for (GameObject object: objects.getValue()) {
                gameObjects().addGameObject(object, layerToAdd);
            }
        }
    }

    /*
    Adds to the game object every block of ground
     */
    private void createGround(WindowController windowController) {
        this.terrain = new Terrain(windowController.getWindowDimensions(), 1);
        List<Block> ground = terrain.createInRange(0, (int) windowController.getWindowDimensions().x());
        for (Block block: ground) {
            gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
        }
    }
}
