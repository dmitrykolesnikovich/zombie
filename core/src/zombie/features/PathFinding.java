package zombie.features;

import zombie.types.Cell;

public class PathFinding {

    public static Cell[] findPath(Cell[][] grid, Cell source, Cell target) {
        Cell[] path;
        Cell middleCell = grid[source.i][target.j];
        if (middleCell == null) {
            path = new Cell[]{source, target};
        } else if (middleCell != source && middleCell != target) {
            path = new Cell[]{source, middleCell, target};
        } else {
            path = new Cell[]{source, target};
        }
        return path;
    }

}
