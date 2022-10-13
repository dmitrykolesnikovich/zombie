package zombie.types;

import java.util.List;
import java.util.Map;

public class Physics {

    public int width;
    public int height;
    public Map<Integer, Zone> zones;
    public float cellSide = 16;
    public Cell[][] grid;
    public List<Cell> cells;

}
