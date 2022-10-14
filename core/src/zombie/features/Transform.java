package zombie.features;

import com.badlogic.gdx.math.Vector2;
import zombie.types.*;

import java.util.List;

public class Transform {

    private final Body body;
    private Movement movement;

    public Transform(Body body) {
        this.body = body;
    }

    public void update(float deltaTime) {
        if (isMoving()) {
            float cellProgressDelta = body.speed / body.level.physics.cellSide * deltaTime;
            boolean running = movement.updateCurrentPosition(cellProgressDelta);
            placeTo(movement.getCurrentPosition());
            if (running) {
                body.face = isMovingRight() ? Face.LOOKING_RIGHT : Face.LOOKING_LEFT;
            } else {
                stop();
            }
        }
    }

    public boolean start(Cell source, Cell target) {
        List<Cell> path = PathFinding.findPath(body.level.physics.graph, source, target);
        if (path.isEmpty()) return false;
        movement = new Movement(path);
        return true;
    }

    public void stop() {
        movement = null;
    }

    public void placeTo(int i, int j) {
        Cell cell = body.level.physics.grid[i][j];
        placeTo(cell);
    }

    public void placeTo(Cell cell) {
        Vector2 position = cell.getCenterIso();
        placeTo(position);
    }

    public void placeTo(Vector2 position) {
        body.position.set(position);
        body.updateCells();
    }

    public boolean moveTo(int i, int j) {
        Level level = body.level;
        Cell cell = level.physics.grid[i][j];
        return moveTo(cell);
    }

    public boolean moveTo(Cell cell) {
        Level level = body.level;
        Cell currentCell = body.getCellOrNull();
        if (cell == null) return false;
        if (currentCell == null) return false;
        return start(currentCell, cell);
    }

    public boolean isMoving() {
        return movement != null;
    }

    public boolean isMovingDown() {
        return movement != null && movement.getCurrentDirection().y > 0;
    }

    public boolean isMovingRight() {
        return movement != null && movement.getCurrentDirection().x > 0;
    }

}
