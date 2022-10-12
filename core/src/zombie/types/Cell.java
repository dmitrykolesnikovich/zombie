package zombie.types;

public class Cell {

    public int i;
    public int j;
    public Zone zone;

    @Override
    public String toString() {
        return "Cell{" + "i=" + i + ", j=" + j + ", zone=" + zone.id + '}';
    }

}

