package zombie.types;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import zombie.features.Animator;
import zombie.features.Transform;

import java.util.ArrayList;
import java.util.List;

public class Body {

    public final Level level;
    public final int id;
    public final String name;

    /*mechanics*/

    public final Vector2 position = new Vector2();
    public final Vector2 pivot = new Vector2();
    private final Rectangle bounds = new Rectangle();
    public final Transform transform = new Transform(this);
    public float speed = 16;

    /*physics*/

    public int rows;
    public int columns;
    private final List<Cell> cells = new ArrayList<>();

    /*graphics*/

    private Animation animation;
    public Face face;
    public boolean isVisible = true;

    public Body(Level level, int id, String name) {
        this.level = level;
        this.id = id;
        this.name = name;
    }

    public void update(float deltaTime) {
        transform.update(deltaTime); // 1. update state
        Animator.update(this); // 2. sync animation with state
        if (animation != null) animation.update(deltaTime); // 3. update animation
    }

    /*mechanics*/

    public Rectangle getBounds() {
        TextureRegion image = getImageOrNull();
        if (image == null) return bounds.setPosition(0, 0).setSize(0, 0);

        // position
        float w = image.getRegionWidth();
        float h = image.getRegionHeight();
        float x = position.x - w / 2;
        float y = position.y - h / 2;

        // position offset
        float dx = isLookingRight() ? pivot.x : -pivot.x;
        float dy = -pivot.y;

        return bounds.setPosition(x + dx, y + dy).setSize(w, h);
    }

    /*physics*/

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

    /*graphics*/

    public boolean setAnimation(String name) {
        return setAnimation(name, false);
    }

    public boolean setAnimation(String name, boolean flippedHorizontally) {
        Animation animation = AnimationBuilder.buildAnimation(name, flippedHorizontally);
        return setAnimation(animation);
    }

    public boolean setAnimation(Animation animation) {
        if (this.animation == animation) return false;
        if (this.animation != null) {
            this.animation.stop();
        }
        this.animation = animation;
        return true;
    }

    public Animation getAnimation() {
        return animation;
    }

    public TextureRegion getImageOrNull() {
        if (animation == null) return null;
        return animation.getImage();
    }

    public boolean isLookingLeft() {
        return face == Face.LOOKING_LEFT;
    }

    public boolean isLookingRight() {
        return face == Face.LOOKING_RIGHT;
    }

    @Override
    public String toString() {
        return "Body('" + name + "', position = " + position + ")";
    }

}
