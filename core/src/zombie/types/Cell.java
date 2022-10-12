package zombie.types;

import com.badlogic.gdx.math.Vector2;

public class Cell {

    public int i;
    public int j;
    public Zone zone;

    public Vector2 centerIso; // used by MoveHeroFeature

    @Override
    public String toString() {
        return "Cell(" + i + ", " + j + ", '" + zone.name + "')";
    }

}
