package zombie.types;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Physics {

    public int width;
    public int height;
    public Zone[] zones;
    public final List<Cell> cells = new ArrayList<>();

    public static Physics createPhysics(String name) throws FileNotFoundException {
        String dirPath = "maps/" + name;
        String filePath = dirPath + "/" + name + "_physics_config.xml";
        XmlReader parser = new XmlReader();
        XmlReader.Element physicsElement = parser.parse(new FileReader(filePath));

        // attributes
        Physics physics = new Physics();
        physics.width = Integer.parseInt(physicsElement.getAttribute("width"));
        physics.height = Integer.parseInt(physicsElement.getAttribute("height"));

        // zones
        Array<XmlReader.Element> zoneElements = physicsElement.getChildByName("zones").getChildByName("list").getChildrenByName("PhysicsZone");
        physics.zones = new Zone[zoneElements.size];
        for (XmlReader.Element zoneElement : zoneElements) {
            Zone zone = Zone.createZone(zoneElement);
            physics.zones[zone.id] = zone;
        }

        // cells
        Array<XmlReader.Element> cellElements = physicsElement.getChildByName("cells").getChildByName("list").getChildrenByName("PhysicsCell");
        for (XmlReader.Element cellElement : cellElements) {
            Cell cell = Cell.createCell(cellElement, physics);
            physics.cells.add(cell);
        }

        return physics;
    }

}
