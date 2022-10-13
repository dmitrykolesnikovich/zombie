package zombie.types;

import com.badlogic.gdx.math.Vector2;
import zombie.features.PathFinding;

public class Transform {

    private final Body body;
    public Movement movement;

    public Transform(Body body) {
        this.body = body;
    }

    public void update(float deltaTime) {
        if (movement != null) {
            float cellProgressDelta = body.movementSpeed / body.level.physics.cellSide * deltaTime;
            boolean running = movement.update(cellProgressDelta);
            body.transform.placeTo(movement.getCurrentPosition());
            if (running) {
                body.face = movement.isRight() ? Face.LOOKING_RIGHT : Face.LOOKING_LEFT;
            } else {
                movement = null;
            }
        }
    }

    public void placeTo(int i, int j) {
        Cell cell = body.level.physics.grid[i][j];
        placeTo(cell);
    }

    public void placeTo(Cell cell) {
        Vector2 centerIso = cell.getCenterIso();
        placeTo(centerIso);
    }

    public void placeTo(Vector2 position) {
        body.position.set(position);
        PathFinding.update(body);
    }

    public void moveTo(int i, int j) {
        Level level = body.level;
        Cell cell = level.physics.grid[i][j];
        moveTo(cell);
    }

    public void moveTo(Cell cell) {
        Level level = body.level;
        Cell[][] grid = level.physics.grid;
        Cell currentCell = level.findCellOrNull(body.position);
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