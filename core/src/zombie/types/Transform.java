package zombie.types;

public class Transform {

    private final Body body;
    public Movement movement;

    public Transform(Body body) {
        this.body = body;
    }

    public void update(float deltaTime) {
        if (movement != null) {
            float cellProgressDelta = body.movementSpeed / body.level.cellSide * deltaTime;
            boolean running = movement.update(cellProgressDelta);
            body.position.set(movement.getCurrentPosition());
            if (!running) {
                movement = null;
            }
        }
    }

    public void placeTo(int i, int j) {
        Level level = body.level;
        Cell cell = level.physics.grid[i][j];
        placeTo(cell);
    }

    public void placeTo(Cell cell) {
        Level level = body.level;
        body.position.set(cell.getCenterIso());
        level.onUpdateBody(body);
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
