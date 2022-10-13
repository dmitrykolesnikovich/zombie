package zombie.types;

import com.badlogic.gdx.math.Vector2;
import zombie.features.Isometry;

public class Cell {

    public int i;
    public int j;
    public Zone zone;

    public Physics physics;
    public Body body;
    private Vector2 center;

    public boolean isPassable() {
        return zone.passable && body == null;
    }

    // iso
    public Vector2 getCenter() {
        float side = physics.cellSide;
        if (center == null) {
            center = new Vector2(j * side, i * side).add(side * 0.5f, side * 0.5f);
            Isometry.convertOrthoToIso(center);
        }
        return center;
    }

    @Override
    public String toString() {
        return "Cell(" + i + ", " + j + ", '" + zone.name + "')";
    }

}
