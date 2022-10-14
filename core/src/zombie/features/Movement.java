package zombie.features;

import com.badlogic.gdx.math.Vector2;
import zombie.types.Cell;

import java.util.List;

public class Movement {

    private final List<Cell> path;
    private int currentCellIndex; // 0..path.length - 1
    private float currentCellProgress; // 0..1
    private final Vector2 currentPosition = new Vector2();
    private final Vector2 currentDirection = new Vector2();

    public Movement(List<Cell> path) {
        this.path = path;
    }

    public boolean updateCurrentPosition(float cellProgressDelta) {
        // calculate before
        long integerBefore = (long) cellProgressDelta;
        double fractionalBefore = cellProgressDelta - integerBefore;

        // update before
        currentCellIndex += integerBefore;
        currentCellProgress += fractionalBefore;

        // calculate after
        long integerAfter = (long) currentCellProgress;
        double fractionalAfter = currentCellProgress - integerAfter;

        // update after
        currentCellIndex += integerAfter;
        currentCellProgress = (float) fractionalAfter;

        // stop
        if (currentCellIndex >= path.size() - 1) {
            currentCellIndex = path.size() - 1;
            currentCellProgress = 0;
            return false;
        }

        return true;
    }

    public Vector2 getCurrentPosition() {
        int index1 = currentCellIndex;
        int index2 = currentCellIndex + 1;
        Cell cell1 = path.get(index1);
        if (index2 >= path.size()) return cell1.getCenterIso();

        Cell cell2 = path.get(index2);
        Vector2 center1 = cell1.getCenterIso();
        Vector2 center2 = cell2.getCenterIso();
        float deltaX = center2.x - center1.x;
        float deltaY = center2.y - center1.y;

        float currentX = center1.x + deltaX * currentCellProgress;
        float currentY = center1.y + deltaY * currentCellProgress;
        currentPosition.set(currentX, currentY);
        return currentPosition;
    }

    public Vector2 getCurrentDirection() {
        Vector2 p1 = path.get(currentCellIndex).getCenterIso();
        Vector2 p2 = path.get(currentCellIndex + 1).getCenterIso();
        currentDirection.set(p2.x - p1.x, p2.y - p1.y);
        return currentDirection;
    }

}
