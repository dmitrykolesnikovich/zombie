package zombie.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Physics {

    public int width;
    public int height;
    public Map<Integer, Zone> zones = new HashMap<>();
    public final List<Cell> cells = new ArrayList<>();
    public Cell[][] grid;

}
