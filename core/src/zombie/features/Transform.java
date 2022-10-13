package zombie.features;

import com.badlogic.gdx.math.Vector2;
import zombie.types.*;

public class Transform {

    private final Body body;
    public Movement movement;

    public Transform(Body body) {
        this.body = body;
    }

    public void update(float deltaTime) {
        if (movement != null) {
            float cellProgressDelta = body.speed / body.level.physics.cellSide * deltaTime;
            boolean running = movement.updateCurrentPosition(cellProgressDelta);
            body.transform.placeTo(movement.getCurrentPosition());
            if (running) {
                body.face = movement.isRight() ? Face.LOOKING_RIGHT : Face.LOOKING_LEFT;
            } else {
                stop();
            }
        }
    }

    public void placeTo(int i, int j) {
        Cell cell = body.level.physics.grid[i][j];
        placeTo(cell);
    }

    public void placeTo(Cell cell) {
        Vector2 position = cell.getCenter();
        placeTo(position);
    }

    public void placeTo(Vector2 position) {
        body.position.set(position);
        body.updateCells();
    }

    public void moveTo(int i, int j) {
        Level level = body.level;
        Cell cell = level.physics.grid[i][j];
        moveTo(cell);
    }

    public void moveTo(Cell cell) {
        if (cell == null) return;
        Level level = body.level;
        Cell currentCell = level.findCellOrNull(body.position);
        if (currentCell == null) return;
        start(currentCell, cell);
    }

    /*internals*/

    private void start(Cell source, Cell target) {
        Cell[][] grid = body.level.physics.grid;
        Cell[] path;
        Cell middleCell = grid[source.i][target.j];
        if (middleCell == null) {
            path = new Cell[]{source, target};
        } else if (middleCell != source && middleCell != target) {
            path = new Cell[]{source, middleCell, target};
        } else {
            path = new Cell[]{source, target};
        }
        movement = new Movement(path);
    }

    private void stop() {
        movement = null;
    }

}
