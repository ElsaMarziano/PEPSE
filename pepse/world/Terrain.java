package pepse.util.pepse.world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.pepse.util.ColorSupplier;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class creates the ground and tells other classes where the ground is
 */
public class Terrain {
    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    private static final int TERRAIN_DEPTH = 20;


    private float groundHeightAtX0;

    /**
     * Initialize the terrain
     * @param windowDimensions window size
     * @param seed something
     */
    public Terrain(Vector2 windowDimensions, int seed) {
        this.groundHeightAtX0 = windowDimensions.x() * 0.6f;
    }

    /**
     * This function return the height of the terrain at the given x coordinates
     * @param x x coordinate
     * @return ground height at X
     */
    public float groundHeightAt(float x) { return groundHeightAtX0; }

    public List<Block> createInRange(int minX, int maxX) {
        RectangleRenderable renderable =
                new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR));
        List<Block> blocks = new ArrayList<>();
        double YFirstBlock = Math.floor(groundHeightAt(60) / Block.SIZE) * Block.SIZE;
        int numOfBlocks = (maxX - minX) / Block.SIZE;
        System.out.println(numOfBlocks);
        for (int i = 0; i < numOfBlocks; i++) {
            for (int j = 0; j < TERRAIN_DEPTH; j++) {
                Block block = new Block(new Vector2(i*Block.SIZE,
                        (float) (YFirstBlock + j* Block.SIZE)),
                        renderable);
                block.setTag("ground");
                blocks.add(block);
            }
        }

        return blocks;
    }



}
