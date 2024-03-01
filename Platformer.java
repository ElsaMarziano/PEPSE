import danogl.*;
import danogl.collisions.Layer;
import danogl.components.*;
import danogl.gui.*;
import danogl.gui.rendering.*;
import danogl.util.Vector2;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * A simple platformer demo with a circle as an avatar and a few platforms.
 * Move with left and right keys, jump with space, down+space to drop down a platform.
 * @author Dan Nirel
 */
public class Platformer extends GameManager {
    private static final Color BACKGROUND_COLOR = Color.decode("#80C6E5");
    private static final Color PLATFORM_COLOR = new Color(212, 123, 74);

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        //background
        var background =
            new GameObject(
                    Vector2.ZERO,
                    windowController.getWindowDimensions(),
                    new RectangleRenderable(BACKGROUND_COLOR)
            );
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);

        placePlatform(Vector2.of(-1024, 1000), Vector2.ONES.mult(2048));
        placePlatform(Vector2.of(-512, 700), Vector2.of(1024, 50));
        placePlatform(Vector2.of(-256, 400), Vector2.of(512, 50));
        placePlatform(Vector2.of(-128, 100), Vector2.of(256, 50));

        var avatar = new Avatar(Vector2.of(0, 900), inputListener);
        setCamera(new Camera(avatar, Vector2.ZERO,
                windowController.getWindowDimensions(), windowController.getWindowDimensions()));
        gameObjects().addGameObject(avatar);
    }

    private void placePlatform(Vector2 pos, Vector2 size) {
        var platform = new GameObject(pos, size, new RectangleRenderable(PLATFORM_COLOR));
        platform.physics().preventIntersectionsFromDirection(Vector2.UP);
        platform.physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
        gameObjects().addGameObject(platform, Layer.STATIC_OBJECTS);
    }

    public static void main(String[] args) {
        new Platformer().run();
    }
}

