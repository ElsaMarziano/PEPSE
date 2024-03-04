package pepse.util.pepse.world.trees;

import danogl.GameObject;
import danogl.util.Vector2;
import pepse.util.pepse.world.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;


public class Trees {

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
}
