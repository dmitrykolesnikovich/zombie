package zombie.types;

import com.badlogic.gdx.utils.XmlReader;

public class TileBuilder {

    public static Tile buildTile(XmlReader.Element tileElement, Level level) {
        Tile tile = new Tile();
        tile.x = Float.parseFloat(tileElement.getAttribute("x"));
        tile.y = Float.parseFloat(tileElement.getAttribute("y"));
        tile.width = Float.parseFloat(tileElement.getAttribute("width"));
        tile.height = Float.parseFloat(tileElement.getAttribute("height"));
        tile.flipHorizontal = Boolean.parseBoolean(tileElement.getAttribute("flipHorizontal"));
        tile.flipVertical = Boolean.parseBoolean(tileElement.getAttribute("flipVertical"));
        tile.index = Integer.parseInt(tileElement.getAttribute("index"));
        tile.image = level.findImage(tile.index - 1, tile.flipHorizontal, tile.flipVertical);
        return tile;
    }

}
