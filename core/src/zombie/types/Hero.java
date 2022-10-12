package zombie.types;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import zombie.features.HeroMovementFeature;

public class Hero {

    public final Vector2 position = new Vector2();
    public float movementSpeed = 16;
    private final HeroMovementFeature movementFeature = new HeroMovementFeature(this);
    private Animation animation;
    private final Rectangle bounds = new Rectangle();

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

    public Rectangle getBounds() {
        TextureRegion image = getImageOrNull();
        float w = image.getRegionWidth();
        float h = image.getRegionHeight();
        float x = position.x - w / 2;
        float y = position.y - h / 2;
        bounds.setPosition(x, y);
        bounds.setSize(w, h);
        return bounds;
    }

}
