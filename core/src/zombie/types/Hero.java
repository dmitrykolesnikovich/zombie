package zombie.types;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import zombie.features.HeroMovementFeature;

public class Hero {

    public final Vector2 position = new Vector2();
    public float movementSpeed = 16;
    private final HeroMovementFeature movementFeature = new HeroMovementFeature(this);
    private Animation animation;

    public void update(float deltaTime) {
        if (animation != null) {
            animation.totalTime += deltaTime;
        }
        movementFeature.update(movementSpeed);
    }

    public void animate(String animationName, boolean flipped) {
        if (animation != null) {
            animation.totalTime = 0;
        }
        animation = AnimationBuilder.buildAnimation(animationName, flipped);
    }

    public TextureRegion getImageOrNull() {
        if (animation == null) return null;
        return animation.delegate.getKeyFrame(animation.totalTime, true);
    }

}
