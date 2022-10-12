package zombie.types;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animation {

    public String name;
    public String texturePath;
    public Texture texture;

    public com.badlogic.gdx.graphics.g2d.Animation<TextureRegion> delegate;
    public float totalTime;

}
