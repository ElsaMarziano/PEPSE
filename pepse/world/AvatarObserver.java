package pepse.util.pepse.world;

/**
 * The AvatarObserver interface represents an observer that watches for avatar actions.
 * Classes implementing this interface can be notified when the avatar performs
 * specific actions.
 */
public interface AvatarObserver {
    /**
     * Notifies the observer when the avatar jumps.
     */
    void notifyJump();
}
