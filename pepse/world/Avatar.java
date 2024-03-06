package pepse.util.pepse.world;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * This class represents an avatar in the Pepse game
 */
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
    private final ArrayList<AvatarObserver> observers = new ArrayList<AvatarObserver>();

    /**
     * Default constructor for the avatar class, where the avatar is a gray round
     * @param pos initial position of the avatar
     * @param inputListener Input listener to know whether to jump, go right or left etc
     */
    public Avatar(Vector2 pos, UserInputListener inputListener) {
        super(pos, Vector2.ONES.mult(AVATAR_DIMENSIONS), new OvalRenderable(AVATAR_COLOR));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);
        this.inputListener = inputListener;
    }

    /**
     * Constructor for an avatar with an image
     * @param pos Initial position
     * @param inputListener Input listener to know whether to jump, go right or left etc
     * @param imageReader Image reader to be able to pass defaut picture to the avatar
     */
    public Avatar(Vector2 pos, UserInputListener inputListener, ImageReader imageReader) {
        super(pos, Vector2.ONES.mult(AVATAR_DIMENSIONS),
                imageReader.readImage("pepse/util/idleImages/idle_0.png", true));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);
        this.inputListener = inputListener;
        // Initializes the different animations
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

    /**
     * Updates animation, make avatar go right, left or jump depending on user input. Updates energy
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     *                  pos += deltaTime*velocity
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float xVel = 0;
        if(this.energy <0.5) transform().setVelocityX(0);
// Go left
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT) && this.energy >=0.5) {
            xVel -= VELOCITY_X;
            this.energy -= 0.5;
            transform().setVelocityX(xVel);
            if(this.runAnimation != null) {
                this.renderer().setRenderable(this.runAnimation);
                this.renderer().setIsFlippedHorizontally(true);
            }
        }
        // Go right
        else if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT) && this.energy >= 0.5) {
            xVel += VELOCITY_X;
            this.energy -= 0.5;
            transform().setVelocityX(xVel);
            if(this.runAnimation != null) {
                this.renderer().setRenderable(this.runAnimation);
                this.renderer().setIsFlippedHorizontally(false);
            }
            // Jump
        } else if (inputListener.isKeyPressed(KeyEvent.VK_SPACE) && getVelocity().y() == 0 && this.energy >= 10) {
                transform().setVelocityY(VELOCITY_Y);
                this.energy -= 10;
                this.notifyObservers();
            if(this.jumpAnimation != null) this.renderer().setRenderable(this.jumpAnimation);
            // Stay still
        } else if (inputListener.pressedKeys().isEmpty() ){
            if(this.energy <=100 && getVelocity().y() == 0) {
                this.energy = Math.min(100, this.energy + 1);
                if(this.idleAnimation != null) this.renderer().setRenderable(this.idleAnimation);
            }
            transform().setVelocityX(xVel);
        }
    }

    /**
     * Get current energy for the energy counter to show
     * @return energy
     */
    public Double getCurrentEnergy(){
        return this.energy;
    }

    /**
     * Adds energy to the avatar. This function is passed to the fruits
     */
    public void add10Energy() {
        this.energy = Math.min(this.energy + 10, 100);
    }

    /**
     * Changes avatar tag to return "Avatar"
     * @return "Avatar"
     */
    @Override
    public String getTag() {
        return "Avatar";
    }

    /**
     * Add new observer to the list of observers.
     * @param observer - some GameObject that affects by jumping the Avatar.
     */
    public void registerObserver(GameObject observer) {
        observers.add((AvatarObserver) observer);
    }

    /*
     * Affects the observers when the Avatar jump.
     */
    private void notifyObservers() {
        for (AvatarObserver observer : observers) {
            observer.notifyJump();
        }
    }


}
