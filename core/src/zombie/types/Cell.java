package zombie.types;

import com.badlogic.gdx.math.Vector2;
import zombie.features.Isometry;

public class Cell {

    public int i;
    public int j;
    public Zone zone;

    public Level level;
    public Body body;
    private Vector2 centerIso;

    public boolean isPassable() {
        return zone.passable && body == null;
    }

    public Vector2 getCenterIso() {
        float side = level.physics.cellSide;
        if (centerIso == null) {
            centerIso = new Vector2(j * side, i * side).add(side * 0.5f, side * 0.5f);
            Isometry.convertOrthoToIso(centerIso);
        }
        return centerIso;
    }

    @Override
    public String toString() {
        return "Cell(" + i + ", " + j + ", '" + zone.name + "')";
    }

}
