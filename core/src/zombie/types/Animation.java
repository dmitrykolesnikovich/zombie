package zombie.types;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Animation {

    public String name;
    public String texturePath;
    public Texture texture;

    public final Vector2 position = new Vector2();
    private final Rectangle bounds = new Rectangle();
    public float duration = -1;
    private float currentTime;
    public com.badlogic.gdx.graphics.g2d.Animation<TextureRegion> delegate;

    public void setFps(float fps) {
        delegate.setFrameDuration(1 / fps);
    }

    public boolean update(float deltaTime) {
        currentTime += deltaTime;
        if (duration != -1 && currentTime > duration) {
            stop();
            return false;
        }
        return true;
    }

    public void stop() {
        currentTime = 0;
    }

    public TextureRegion getImage() {
        return delegate.getKeyFrame(currentTime, true);
    }

    public Rectangle getBounds() {
        TextureRegion image = getImage();

        float w = image.getRegionWidth();
        float h = image.getRegionHeight();
        float x = position.x - w / 2;
        float y = position.y - h / 2;
        return bounds.setPosition(x, y).setSize(w, h);
    }

}
