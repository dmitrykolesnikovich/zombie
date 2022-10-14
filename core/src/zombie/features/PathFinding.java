package zombie.features;

import com.badlogic.gdx.ai.pfa.*;
import com.badlogic.gdx.math.Vector2;
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
        return CELLS;
    }

    public static IndexedGraph<Cell> buildGraph(Cell[][] grid) {
        CellGraph graph = new CellGraph();

        return graph;
    }

}

/*internals*/

class CellHeuristic implements Heuristic<Cell> {

    static final CellHeuristic INSTANCE = new CellHeuristic();

    @Override
    public float estimate(Cell source, Cell target) {
        return Vector2.dst(source.i, source.j, target.i, target.j);
    }

}

class CellGraph implements IndexedGraph<Cell> {

    private final Array<Cell> cells = new Array<>();
    private final Array<Step> steps = new Array<>();
    private final ObjectMap<Cell, Array<Connection<Cell>>> stepMap = new ObjectMap<>();

    public void addCell(Cell cell) {
        cell.index = cells.size;
        cells.add(cell);
    }

    public void addStep(Cell source, Cell target) {
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
