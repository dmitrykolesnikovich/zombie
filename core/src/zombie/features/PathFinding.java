package zombie.features;

import com.badlogic.gdx.ai.pfa.*;
import com.badlogic.gdx.utils.Array;
import zombie.Utils;
import zombie.types.Cell;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.ArrayList;
import java.util.List;

// https://happycoding.io/tutorials/libgdx/pathfinding
public class PathFinding {

    private static final List<Cell> CELLS = new ArrayList<>();

    public static List<Cell> findPath(IndexedGraph<Cell> graph, Cell source, Cell target) {
        GraphPath<Cell> path = new DefaultGraphPath<>();
        PathFinder<Cell> pathFinder = new IndexedAStarPathFinder<>(graph);
        pathFinder.searchNodePath(source, target, CellHeuristic.INSTANCE, path);
        Utils.convertPathToCollection(path, CELLS);
        if (CELLS.isEmpty()) {
            System.out.println("breakpoint");
        }
        return CELLS;
    }

    public static IndexedGraph<Cell> buildGraph(Cell[][] grid) {
        CellGraph graph = new CellGraph();
        int rowCount = grid.length;
        int columnCount = grid[0].length;

        // cells
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                Cell cell = grid[row][column];
                graph.addCell(cell);
            }
        }

        // steps
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                Cell cell = grid[row][column];
                int top_row = row - 1;
                int bottom_row = row + 1;
                int left_column = column - 1;
                int right_column = column + 1;
                if (top_row >= 0) graph.addStep(cell, grid[top_row][column]);
                if (bottom_row < rowCount) graph.addStep(cell, grid[bottom_row][column]);
                if (left_column >= 0) graph.addStep(cell, grid[row][left_column]);
                if (right_column < columnCount) graph.addStep(cell, grid[row][right_column]);
            }
        }

        return graph;
    }
}

/*internals*/

class CellHeuristic implements Heuristic<Cell> {

    static final CellHeuristic INSTANCE = new CellHeuristic();

    @Override
    public float estimate(Cell source, Cell target) {
        int cost = Math.abs(target.i - source.i) + Math.abs(target.j - source.j);
        return cost;
    }

}

class CellGraph implements IndexedGraph<Cell> {

    private final Array<Cell> cells = new Array<>();
    private final Array<Step> steps = new Array<>();
    private final ObjectMap<Cell, Array<Connection<Cell>>> stepMap = new ObjectMap<>();

    public void addCell(Cell cell) {
        if (cell == null || !cell.isPassable()) return;

        cell.index = cells.size;
        cells.add(cell);
    }

    public void addStep(Cell source, Cell target) {
        if (source == null || !source.isPassable()) return;
        if (target == null || !target.isPassable()) return;

        Step step = new Step(source, target);
        steps.add(step);
        if (!stepMap.containsKey(source)) stepMap.put(source, new Array<>());
        stepMap.get(source).add(step);
    }

    @Override
    public Array<Connection<Cell>> getConnections(Cell cell) {
        return stepMap.get(cell, new Array<>(0));
    }

    @Override
    public int getIndex(Cell cell) {
        return cell.index;
    }

    @Override
    public int getNodeCount() {
        return cells.size;
    }

}

class Step implements Connection<Cell> {

    private final Cell source;
    private final Cell target;
    private final float cost;

    Step(Cell source, Cell target) {
        this.source = source;
        this.target = target;
        this.cost = CellHeuristic.INSTANCE.estimate(source, target);
    }

    @Override
    public Cell getFromNode() {
        return source;
    }

    @Override
    public Cell getToNode() {
        return target;
    }

    @Override
    public float getCost() {
        return cost;
    }

}
