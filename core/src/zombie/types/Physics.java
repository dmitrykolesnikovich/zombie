package zombie.types;

import java.util.List;
import java.util.Map;

public class Physics {

    public Level level;
    public String name;
    public String dirPath;
    public String filePath;
    public int width;
    public int height;
    public Map<Integer, Zone> zones;
    public float cellSide = 16;
    public Cell[][] grid;
    public List<Cell> cells;

}
