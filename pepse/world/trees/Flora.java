package pepse.util.pepse.world.trees;

import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.pepse.util.ColorSupplier;
import pepse.util.pepse.world.Block;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * This class is responsible for creating trees, leaves, fruits and is responsible for their
 * behavior.
 */
public class Flora {
    private static final int LEAVES_RADIUS = 50;
    private final Function<Float, Float> getGroundHeight;
    private final Runnable onAvatarCollision;

    /**
     * Create a new Flora instance
     * @param getGroundHeight Function returning the height of the ground at a given point
     * @param onAvatarCollision Function that is supposed to happen when a fruit collides with
     *                          the avatar
     */
    public Flora(Function<Float, Float> getGroundHeight, Runnable onAvatarCollision) {
        this.getGroundHeight = getGroundHeight;
        this.onAvatarCollision = onAvatarCollision;
    }

    /**
     * This function is responsible for creating trees, leaves and fruits ina  given range.
     * @param minX Min x we want the creation to start at
     * @param maxX Max X we want the creation to stop at
     * @return A map where each entry is a list of objects we created, to be added to the right
     * layer
     */
    public Map<String, List<GameObject>> createInRange(int minX, int maxX) {
        Map<String, List<GameObject>> objectsToAdd = new HashMap<>();
        List<GameObject> trees = this.createTrees(minX, maxX,
                getGroundHeight);
        List<GameObject> leaves = new ArrayList<>();
        List<GameObject> fruits = new ArrayList<>();
        for(GameObject tree: trees) {
            this.createLeaves(tree.getTopLeftCorner(), leaves);
            this.createFruits(tree.getTopLeftCorner(), this.onAvatarCollision, fruits);
            
        }
        objectsToAdd.put("Trees", trees);
        objectsToAdd.put("Leaves", leaves);
        objectsToAdd.put("Fruits", fruits);
    return objectsToAdd;

    }
    /*
    Create trees and returns them in a list
     */
    private List<GameObject> createTrees(int minX, int maxX,
                                         Function<Float, Float> groundHeightAt) {
        List<GameObject> trees = new ArrayList<>();
        Random random = new Random();
        for(int i =minX; i<=maxX; i+= Block.SIZE) {
            if (random.nextFloat() <= 0.1){
                int height = random.nextInt(10*Block.SIZE- 4*Block.SIZE + 1) + 4 *Block.SIZE;
                StaticTree tree = new StaticTree(new Vector2(i,
                        groundHeightAt.apply((float) i) - height), height);
                trees.add(tree);
            }
        }
        return trees;
    }

    /*
    Create and add leaves to the leaves list
     */
    private void createLeaves(Vector2 treeTop, List<GameObject> leaves) {
        Random random = new Random();
        for(int i=-LEAVES_RADIUS; i< LEAVES_RADIUS; i+=Block.SIZE) {
            for(int j=-LEAVES_RADIUS; j< LEAVES_RADIUS; j+=Block.SIZE) {
                if (random.nextFloat() <= 0.1) {
                    Vector2 pos = treeTop.add(new Vector2(i, j));
                    GameObject leaf = new Leaf(pos);
                    leaves.add(leaf);
                }
            }
        }
    }

    /*
    Create and add fruits to the fruit list
     */
    private void createFruits(Vector2 treeTop, Runnable onAvatarCollision,
                                          List<GameObject> fruits) {
        Random random = new Random();
        for(int i=-LEAVES_RADIUS; i< LEAVES_RADIUS; i+=Block.SIZE ) {
            for(int j=-LEAVES_RADIUS; j< LEAVES_RADIUS; j+=Block.SIZE ) {
                if (random.nextFloat() <= 0.3) {
                    GameObject fruit = new Fruit(treeTop.add(new Vector2(i, j)), onAvatarCollision);
                    fruits.add(fruit);
                }
            }
        }
    }


}
