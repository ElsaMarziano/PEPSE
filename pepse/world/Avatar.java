package pepse.util.pepse.world;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;
import java.awt.event.KeyEvent;

class Avatar extends GameObject  {
    /**
     * Constant field for avatar dimensions
     */
    public static final float AVATAR_DIMENSIONS = 50;
    private static final float VELOCITY_X = 400;
    private static final float VELOCITY_Y = -650;
    private static final float GRAVITY = 600;
    private static final Color AVATAR_COLOR = Color.DARK_GRAY;
    private AnimationRenderable runAnimation = null;
    private AnimationRenderable jumpAnimation = null;
    private AnimationRenderable idleAnimation = null;
    private double energy = 100;
    private final UserInputListener inputListener;

    public Avatar(Vector2 pos, UserInputListener inputListener) {
        super(pos, Vector2.ONES.mult(AVATAR_DIMENSIONS), new OvalRenderable(AVATAR_COLOR));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);
        this.inputListener = inputListener;
    }

    public Avatar(Vector2 pos, UserInputListener inputListener, ImageReader imageReader) {
        super(pos, Vector2.ONES.mult(AVATAR_DIMENSIONS),
                imageReader.readImage("pepse/util/idleImages/idle_0.png", true));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);
        this.inputListener = inputListener;
        this.jumpAnimation = new AnimationRenderable(
        new String[]{"pepse/util/jumpImages/jump_0.png", "pepse/util/jumpImages/jump_1.png", "pepse/util" +
                "/jumpImages/jump_2.png", "pepse/util/jumpImages/jump_3.png"}, imageReader,
                true, 0.2);
        this.idleAnimation = new AnimationRenderable(
                new String[]{"pepse/util/idleImages/idle_0.png", "pepse/util/idleImages/idle_1.png",
                        "pepse/util/idleImages/idle_2.png", "pepse/util/idleImages/idle_3.png"}, imageReader,
                true, 0.5);
        this.runAnimation = new AnimationRenderable(
                new String[]{"pepse/util/runImages/run_0.png", "pepse/util/runImages/run_1.png", "pepse/util" +
                        "/runImages/run_2.png", "pepse/util/runImages/run_3.png", "pepse/util/runImages" +
                        "/run_4.png", "pepse/util/runImages/run_5.png"}, imageReader,
                true, 0.2);
        this.renderer().setRenderable(this.idleAnimation);
    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float xVel = 0;
        if(this.energy <0.5) transform().setVelocityX(0);

        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT) && this.energy >=0.5) {
            xVel -= VELOCITY_X;
            this.energy -= 0.5;
            transform().setVelocityX(xVel);
            if(this.runAnimation != null) {
                this.renderer().setRenderable(this.runAnimation);
                this.renderer().setIsFlippedHorizontally(true);
            }
        }
        else if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT) && this.energy >= 0.5) {
            xVel += VELOCITY_X;
            this.energy -= 0.5;
            transform().setVelocityX(xVel);
            if(this.runAnimation != null) {
                this.renderer().setRenderable(this.runAnimation);
                this.renderer().setIsFlippedHorizontally(false);
            }
        } else if (inputListener.isKeyPressed(KeyEvent.VK_SPACE) && getVelocity().y() == 0 && this.energy >= 10) {
                transform().setVelocityY(VELOCITY_Y);
                this.energy -= 10;
            if(this.jumpAnimation != null) this.renderer().setRenderable(this.jumpAnimation);
        } else if (inputListener.pressedKeys().isEmpty() ){
            if(this.energy <=100 && getVelocity().y() == 0) {
                this.energy = Math.min(100, this.energy + 1);
                if(this.idleAnimation != null) this.renderer().setRenderable(this.idleAnimation);

            }
            transform().setVelocityX(xVel);
        }
    }

    public Double getCurrentEnergy(){
        return this.energy;
    }

    public void addEnergy(double energy) {
        this.energy += energy;
    }

    @Override
    public String getTag() {
        return "Avatar";
    }


}
