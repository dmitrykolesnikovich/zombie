package zombie.types;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import zombie.features.HeroInput;

import java.io.FileNotFoundException;

public class Hero {

    public final Vector2 origin = new Vector2();
    private Animation animation;
    private final HeroInput input = new HeroInput(this, 16f);

    public void update(float deltaTime) {
        if (animation != null) {
            animation.totalTime += deltaTime;
        }
        input.update();
    }

    public void animate(String animationName, boolean flipped) {
        try {
            if (animation != null) {
                animation.totalTime = 0;
            }
            animation = AnimationBuilder.buildAnimation(animationName, flipped);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public TextureRegion getImageOrNull() {
        if (animation == null) return null;
        return animation.delegate.getKeyFrame(animation.totalTime, true);
    }

}
