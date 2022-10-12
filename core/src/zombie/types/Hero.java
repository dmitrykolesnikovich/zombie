package zombie.types;

import java.io.FileNotFoundException;

public class Hero {

    public Animation animation;

    public void animate(String animationName, boolean flipped) {
        try {
            animation = Animation.createAnimation(animationName, flipped);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
