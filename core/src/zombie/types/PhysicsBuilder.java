package zombie.types;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import zombie.features.PathFinding;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class PhysicsBuilder {

    public static Physics buildPhysics(String name, Level level) {
        String dirPath = "maps/" + name;
        String filePath = dirPath + "/" + name + "_physics_config.xml";
        XmlReader parser = new XmlReader();
        XmlReader.Element physicsElement;
        try {
            physicsElement = parser.parse(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(name);
        }

        // physics
        Physics physics = new Physics();
        physics.level = level;
        physics.name = name;
        physics.dirPath = dirPath;
        physics.filePath = filePath;

        // attributes
        physics.width = Integer.parseInt(physicsElement.getAttribute("width"));
        physics.height = Integer.parseInt(physicsElement.getAttribute("height"));

        // zones
        Array<XmlReader.Element> zoneElements = physicsElement.getChildByName("zones").getChildByName("list").getChildrenByName("PhysicsZone");
        physics.zones = new HashMap<>();
        for (XmlReader.Element zoneElement : zoneElements) {
            Zone zone = ZoneBuilder.buildZone(zoneElement);
            physics.zones.put(zone.id, zone);
        }

        // cells
        Array<XmlReader.Element> cellElements = physicsElement.getChildByName("cells").getChildByName("list").getChildrenByName("PhysicsCell");
        physics.cells = new ArrayList<>();
        physics.grid = new Cell[physics.width][physics.height];
        for (XmlReader.Element cellElement : cellElements) {
            Cell cell = CellBuilder.buildCell(cellElement, physics);
            physics.cells.add(cell);
            physics.grid[cell.i][cell.j] = cell;
        }

        physics.graph = PathFinding.buildGraph(physics.grid);
        return physics;
    }

}
