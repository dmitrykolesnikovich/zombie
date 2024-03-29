package zombie.types;

import com.badlogic.gdx.utils.XmlReader;

public class CellBuilder {

    public static Cell buildCell(XmlReader.Element cellElement, Physics physics) {
        Cell cell = new Cell();
        cell.i = Integer.parseInt(cellElement.getAttribute("i"));
        cell.j = Integer.parseInt(cellElement.getAttribute("j"));
        cell.zone = physics.zones.get(Integer.parseInt(cellElement.getAttribute("zone")));
        cell.physics = physics;
        return cell;
    }

}
