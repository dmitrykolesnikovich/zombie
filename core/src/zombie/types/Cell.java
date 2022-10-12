package zombie.types;

import com.badlogic.gdx.math.Vector2;
import zombie.Utils;

public class Cell {

    public int i;
    public int j;
    public Zone zone;

    // used by features
    public Level level;
    private Vector2 centerIso;
    public Object body;

    public Vector2 getCenterIso() {
        float side = level.cellSide;
        if (centerIso == null) {
            centerIso = new Vector2(j * side, i * side).add(side * 0.5f, side * 0.5f);
            Utils.convertOrthoToIso(centerIso);
        }
        return centerIso;
    }

    public boolean isPassable() {
        return zone.passable && body == null;
    }

    @Override
    public String toString() {
        return "Cell(" + i + ", " + j + ", '" + zone.name + "')";
    }

}
