package zombie.types;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.XmlReader;

public class ZoneBuilder {

    public static Zone buildZone(XmlReader.Element zoneElement) {
        Zone zone = new Zone();
        zone.id = Integer.parseInt(zoneElement.getAttribute("id"));
        zone.name = zoneElement.getAttribute("name");
        zone.passable = Boolean.parseBoolean(zoneElement.getAttribute("passable"));
        zone.locked = Boolean.parseBoolean(zoneElement.getAttribute("locked"));
        zone.availableForBuilding = Boolean.parseBoolean(zoneElement.getAttribute("availableForBuilding"));
        zone.color = new Color(Integer.parseInt(zoneElement.getAttribute("color")));
        return zone;
    }

}
