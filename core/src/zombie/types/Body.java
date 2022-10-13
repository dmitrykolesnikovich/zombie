package zombie.types;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import zombie.features.Animator;

import java.util.ArrayList;
import java.util.List;

public class Body {

    public final Level level;
    public int id;
    public String name;

    /*geometry*/

    public final Vector2 position = new Vector2();
    public final Vector2 pivot = new Vector2();
    private final Rectangle bounds = new Rectangle();
    public final Transform transform = new Transform(this);
    public float speed = 16;

    /*animation*/

    private Animation animation;
    public Face face;
    public boolean isVisible = true;

    /*cells*/

    public int rows;
    public int columns;
    private final List<Cell> cells = new ArrayList<>();

    public Body(Level level) {
        this.level = level;
    }

    public void update(float deltaTime) {
        transform.update(deltaTime); // 1. update state
        Animator.update(this); // 2. sync animation with state
        if (animation != null) animation.update(deltaTime); // 3. update animation
    }

    /*geometry*/

    public Rectangle getBounds() {
        TextureRegion image = getImageOrNull();
        if (image == null) return bounds.setPosition(0, 0).setSize(0, 0);

        // position
        float w = image.getRegionWidth();
        float h = image.getRegionHeight();
        float x = position.x - w / 2;
        float y = position.y - h / 2;

        // position offset
        float dx = face != null && face.isLookingRight() ? pivot.x : -pivot.x;
        float dy = -pivot.y;

        return bounds.setPosition(x + dx, y + dy).setSize(w, h);
    }

    /*animation*/

    public void setAnimation(String animationName, boolean flipped) {
        Animation animation = AnimationBuilder.buildAnimation(animationName, flipped);
        setAnimation(animation);
    }

    public void setAnimation(Animation animation) {
        if (this.animation == animation) return;
        if (this.animation != null) this.animation.stop();
        this.animation = animation;
    }

    public Animation getAnimation() {
        return animation;
    }

    public TextureRegion getImageOrNull() {
        if (animation == null) return null;
        return animation.getImage();
    }

    /*cells*/

    public void updateCells() {
        // reset
        for (Cell cell : cells) cell.body = null;
        cells.clear();

        // setup
        Cell[][] grid = level.physics.grid;
        Cell firstCell = getCellOrNull();
        for (int row = firstCell.i; row < firstCell.i + rows; row++) {
            for (int column = firstCell.j; column < firstCell.j + columns; column++) {
                Cell cell = grid[row][column];
                cell.body = this;
                cells.add(cell);
            }
        }
    }

    public Cell getCellOrNull() {
        return level.findCellOrNull(position);
    }

    public Cell getMinCell() {
        return cells.get(0);
    }

    public Cell getMaxCell() {
        return cells.get(cells.size() - 1);
    }

    @Override
    public String toString() {
        return "Body('" + name + "', position = " + position + ")";
    }

}
