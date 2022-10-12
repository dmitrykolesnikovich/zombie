package zombie.types;

import com.badlogic.gdx.utils.XmlReader;

public class Cell {

    public int i;
    public int j;
    public Zone zone;

    public static Cell createCell(XmlReader.Element element, Physics physics) {
        Cell cell = new Cell();
        cell.i = Integer.parseInt(element.getAttribute("i"));
        cell.j = Integer.parseInt(element.getAttribute("j"));
        int zoneId = Integer.parseInt(element.getAttribute("zone"));
        cell.zone = physics.zones[zoneId];
        return cell;
    }

}
