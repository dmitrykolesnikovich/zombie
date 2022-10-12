package zombie.types;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Animation {

    public String name;
    public String texturePath;
    public Texture texture;

    public com.badlogic.gdx.graphics.g2d.Animation<TextureRegion> delegate;
    private float totalTime;
    public float lifeTime = -1;
    public final Vector2 position = new Vector2();
    private final Rectangle bounds = new Rectangle();

    public boolean update(float deltaTime) {
        totalTime += deltaTime;
        if (lifeTime != -1 && totalTime > lifeTime) return false;
        return true;
    }

    public Animation reset() {
        totalTime = 0;
        return this;
    }

    public TextureRegion getImage() {
        return delegate.getKeyFrame(totalTime, true);
    }

    public Rectangle getBounds() {
        TextureRegion image = getImage();

        float w = image.getRegionWidth();
        float h = image.getRegionHeight();
        float x = position.x - w / 2;
        float y = position.y - h / 2;
        return bounds.setPosition(x, y).setSize(w, h);
    }

    public void setFps(float fps) {
        delegate.setFrameDuration(1 / fps);
    }

}
