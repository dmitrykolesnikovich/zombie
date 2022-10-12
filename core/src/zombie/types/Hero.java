package zombie.types;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Hero {

    private final Level level;
    public final Vector2 position = new Vector2();
    public float movementSpeed = 16;
    private Animation animation;
    private final Rectangle bounds = new Rectangle();
    private Movement movement;

    public Hero(Level level) {
        this.level = level;
    }

    public void update(float deltaTime) {
        if (animation != null) animation.update(deltaTime);
        if (movement != null) {
            float cellProgressDelta = movementSpeed / level.cellSide * deltaTime;
            boolean running = movement.update(cellProgressDelta);
            position.set(movement.getCurrentPosition());
            if (!running) {
                movement = null;
            }
        }
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

        // quickfix todo conceptualize
        x -= 4;
        y -= 20;
        // <<

        return bounds.setPosition(x, y).setSize(w, h);
    }

    public void placeTo(int i, int j) {
        Cell cell = level.physics.grid[i][j];
        placeTo(cell);
    }

    public void placeTo(Cell cell) {
        position.set(cell.getCenterIso());
    }

    public void moveTo(int i, int j) {
        Cell cell = level.physics.grid[i][j];
        moveTo(cell);
    }

    public void moveTo(Cell cell) {
        Cell[][] grid = level.physics.grid;
        Cell currentCell = level.findCellOrNull(position);
        if (currentCell == null) return;
        if (cell == null) return;

        Cell[] path;
        Cell middleCell = grid[currentCell.i][cell.j];
        if (middleCell == null) {
            path = new Cell[]{currentCell, cell};
        } else if (middleCell != currentCell && middleCell != cell) {
            path = new Cell[]{currentCell, middleCell, cell};
        } else {
            path = new Cell[]{currentCell, cell};
        }
        move(path);
    }

    public void move(Cell[] path) {
        movement = new Movement(path);
    }

}
