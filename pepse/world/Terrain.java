package pepse.util.pepse.world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.pepse.util.ColorSupplier;
import pepse.util.pepse.util.NoiseGenerator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class creates the ground and tells other classes where the ground is
 */
public class Terrain {
    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    private static final int TERRAIN_DEPTH = 20;
    private final NoiseGenerator noiseGenerator;
    private float groundHeightAtX0;

    /**
     * Initialize the terrain
     * @param windowDimensions window size
     * @param seed something
     */
    public Terrain(Vector2 windowDimensions, int seed) {
        this.groundHeightAtX0 = windowDimensions.y() * 0.6f;
        this.noiseGenerator = new NoiseGenerator((double) seed, (int) groundHeightAtX0);
    }

    /**
     * This function return the height of the terrain at the given x coordinates
     * @param x x coordinate
     * @return ground height at X
     */
    public float groundHeightAt(float x) {
        float noise = (float) noiseGenerator.noise(x, Block.SIZE *7);
        return groundHeightAtX0 + noise;
    }

    public List<Block> createInRange(int minX, int maxX) {
        RectangleRenderable renderable =
                new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR));
        List<Block> blocks = new ArrayList<>();
        for (int i = minX; i <= maxX; i+=Block.SIZE) {
            float height = groundHeightAt(i);
            int top = (int) (height / Block.SIZE) * Block.SIZE;
            for (int j = top; j <= top + TERRAIN_DEPTH * Block.SIZE; j+=Block.SIZE) {
                Block block = new Block(new Vector2(i, j), renderable);
                block.setTag("ground");
                blocks.add(block);
            }
        }

        return blocks;
    }



}
