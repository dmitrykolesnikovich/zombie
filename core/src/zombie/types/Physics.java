package zombie.types;

import java.util.ArrayList;
import java.util.List;

public class Physics {

    public int width;
    public int height;
    public Zone[] zones;
    public final List<Cell> cells = new ArrayList<>();
    public Cell[][] grid;

}
