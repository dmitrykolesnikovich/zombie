package zombie.types;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import zombie.features.Animator;

import java.util.ArrayList;
import java.util.List;

public class Body {

    public int id;
    public String name;
    public int rows;
    public int columns;

    public final Level level;
    private Animation animation;
    public final Transform transform = new Transform(this);
    public final Vector2 position = new Vector2();
    public final Vector2 pivot = new Vector2();
    public float movementSpeed = 16;
    private final Rectangle bounds = new Rectangle();
    public boolean isVisible = true;
    public final List<Cell> placementCells = new ArrayList<>();
    public Face face;

    public Body(Level level) {
        this.level = level;
    }

    public void update(float deltaTime) {
        transform.update(deltaTime);
        Animator.updateAnimation(this);
        if (animation != null) animation.update(deltaTime);
    }

    public void animate(String animationName, boolean flipped) {
        if (animation != null && animation.name.equals(animationName)) return;
        if (animation != null) animation.reset();
        animation = AnimationBuilder.buildAnimation(animationName, flipped);
    }

    public TextureRegion getImageOrNull() {
        if (animation == null) return null;
        return animation.getImage();
    }

    public Rectangle getBounds() {
        TextureRegion image = getImageOrNull();
        if (image == null) return bounds.setPosition(0, 0).setSize(0, 0);

        float w = image.getRegionWidth();
        float h = image.getRegionHeight();
        float x = position.x - w / 2;
        float y = position.y - h / 2;
        x -= pivot.x;
        y -= pivot.y;
        return bounds.setPosition(x, y).setSize(w, h);
    }

    public Cell getCellOrNull() {
        return level.findCellOrNull(position);
    }

    @Override
    public String toString() {
        return "Body('" + name + "', position = " + position + ")";
    }

}
