package zombie.features;

import zombie.types.Body;
import zombie.types.Cell;

public class PathFinding {

    public static void update(Body body) {
        // reset
        for (Cell cell : body.placementCells) cell.body = null;
        body.placementCells.clear();

        // setup
        Cell[][] grid = body.level.physics.grid;
        Cell firstCell = body.getCellOrNull();
        for (int row = firstCell.i; row < firstCell.i + body.rows; row++) {
            for (int column = firstCell.j; column < firstCell.j + body.columns; column++) {
                Cell cell = grid[row][column];
                cell.body = body;
                body.placementCells.add(cell);
            }
        }
    }

}
