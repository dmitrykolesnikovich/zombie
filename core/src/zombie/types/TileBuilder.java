package zombie.types;

import com.badlogic.gdx.utils.XmlReader;

public class TileBuilder {

    public static Tile buildTile(Level level, XmlReader.Element element) {
        Tile tile = new Tile();
        tile.x = Float.parseFloat(element.getAttribute("x"));
        tile.y = Float.parseFloat(element.getAttribute("y"));
        tile.width = Float.parseFloat(element.getAttribute("width"));
        tile.height = Float.parseFloat(element.getAttribute("height"));
        tile.flipHorizontal = Boolean.parseBoolean(element.getAttribute("flipHorizontal"));
        tile.flipVertical = Boolean.parseBoolean(element.getAttribute("flipVertical"));
        tile.index = Integer.parseInt(element.getAttribute("index"));
        tile.image = level.findImage(tile.index - 1, tile.flipHorizontal, tile.flipVertical);
        return tile;
    }

}
