package zombie.types;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import zombie.features.HeroMovement;

import java.io.FileNotFoundException;

public class Hero {

    public final Vector2 origin = new Vector2();
    private Animation animation;
    private final HeroMovement movement = new HeroMovement(this, 16f);

    public void update(float deltaTime) {
        if (animation != null) {
            animation.totalTime += deltaTime;
        }
        movement.update();
    }

    public void animate(String animationName, boolean flipped) {
        try {
            if (animation != null) {
                animation.totalTime = 0;
            }
            animation = Animation.createAnimation(animationName, flipped);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public TextureRegion getAnimationFrame() {
        if (animation == null) return null;
        return animation.delegate.getKeyFrame(animation.totalTime, true);
    }

}
