package zombie.types;

import com.badlogic.gdx.math.Vector2;
import zombie.features.Isometry;

public class Cell {

    public int i;
    public int j;
    public Zone zone;

    public Physics physics;
    public Body body;
    private Vector2 centerIso;
    public int index; // path finding

    public boolean isPassable() {
        return zone.passable && body == null;
    }

    public Vector2 getCenterIso() {
        float side = physics.cellSide;
        if (centerIso == null) {
            float ox = side / 2;
            float oy = side / 2;
            float x = j * side;
            float y = i * side;
            centerIso = new Vector2(ox + x, oy + y);
            Isometry.convertOrthoToIso(centerIso);
        }
        return centerIso;
    }

    @Override
    public String toString() {
        return "Cell(" + i + ", " + j + ", '" + zone.name + "')";
    }

}
