package pepse.util.pepse.world.trees;

import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.pepse.util.ColorSupplier;
import pepse.util.pepse.world.AvatarObserver;
import pepse.util.pepse.world.Block;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Function;


public class Trees {
    private static final int LEAVES_RADIUS = 50;
    private static final Color LEAVE_COLOR = new Color(50, 200, 30);


    public static List<GameObject> createTrees(float window_x,
                                           Function<Float, Float> groundHeightAt) {
        List<GameObject> trees = new ArrayList<>();
        Random random = new Random();
        for(int i =0; i<=window_x; i+= Block.SIZE) {
            if (random.nextFloat() <= 0.1){
                int height = random.nextInt(10*Block.SIZE- 4*Block.SIZE + 1) + 4 *Block.SIZE;
                StaticTree tree = new StaticTree(new Vector2(i,
                        groundHeightAt.apply((float) i) - height), height);
                trees.add(tree);
        }
    }
    return trees;
    }

    public static List<GameObject> createLeaves(Vector2 treeTop) {
        List<GameObject> leaves = new ArrayList<>();
        Random random = new Random();
        for(int i=-LEAVES_RADIUS; i< LEAVES_RADIUS; i+=Block.SIZE / 2) {
            for(int j=-LEAVES_RADIUS; j< LEAVES_RADIUS; j+=Block.SIZE / 2) {
                if (random.nextFloat() <= 0.1) {
                    Vector2 pos = treeTop.add(new Vector2(i, j));
                    GameObject leave = new GameObject(pos,
                            Vector2.ONES.mult((float) Block.SIZE),
                            new RectangleRenderable(ColorSupplier.approximateColor(LEAVE_COLOR)));
//                    new ScheduledTask( leave,  random.nextFloat(),true, leave.transform().)

                    leaves.add(leave);
                }
                }
        }
        return leaves;
    }

}
