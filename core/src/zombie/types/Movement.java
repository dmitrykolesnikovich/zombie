package zombie.types;

import com.badlogic.gdx.math.Vector2;

public class Movement {

    public final Cell[] path;

    private int currentCellIndex;
    private float currentCellProgress; // 0..1
    private final Vector2 currentPosition = new Vector2();

    public Movement(Cell[] path) {
        this.path = path;
    }

    public boolean update(float cellProgressDelta) {
        long integer1 = (long) cellProgressDelta;
        double fractional1 = cellProgressDelta - integer1;

        currentCellIndex += integer1;
        currentCellProgress += fractional1;

        long integer2 = (long) currentCellProgress;
        double fractional2 = currentCellProgress - integer2;

        currentCellIndex += integer2;
        currentCellProgress = (float) fractional2;

        if (currentCellIndex >= path.length - 1) {
            currentCellIndex = path.length - 1;
            currentCellProgress = 0;
            return false;
        }

        return true;
    }

    public Cell getCurrentCellOrNull() {
        if (currentCellIndex >= path.length) return null;
        return path[currentCellIndex];
    }

    public Vector2 getCurrentPosition() {
        int index1 = currentCellIndex;
        int index2 = currentCellIndex + 1;
        Cell cell1 = path[index1];
        if (index2 >= path.length) return cell1.getCenterIso();

        Cell cell2 = path[index2];
        Vector2 center1 = cell1.getCenterIso();
        Vector2 center2 = cell2.getCenterIso();
        float deltaX = center2.x - center1.x;
        float deltaY = center2.y - center1.y;

        float currentX = center1.x + deltaX * currentCellProgress;
        float currentY = center1.y + deltaY * currentCellProgress;
        currentPosition.set(currentX, currentY);
        return currentPosition;
    }

    public boolean isRight() {
        Vector2 p1 = path[currentCellIndex].getCenterIso();
        Vector2 p2 = path[currentCellIndex + 1].getCenterIso();
        return p2.x > p1.x;
    }

    public boolean isDown() {
        Vector2 p1 = path[currentCellIndex].getCenterIso();
        Vector2 p2 = path[currentCellIndex + 1].getCenterIso();
        return p2.y > p1.y;
    }

}