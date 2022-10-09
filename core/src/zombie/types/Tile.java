package zombie.types;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.XmlReader;

public class Tile {

    public float x;
    public float y;
    public float height;
    public float width;
    public boolean flipHorizontal;
    public boolean flipVertical;
    public int index;
    public TextureRegion texture;

    public static Tile createTile(Level level, XmlReader.Element element) {
        Tile tile = new Tile();
        tile.x = Float.parseFloat(element.getAttribute("x"));
        tile.y = Float.parseFloat(element.getAttribute("y"));
        tile.width = Float.parseFloat(element.getAttribute("width"));
        tile.height = Float.parseFloat(element.getAttribute("height"));
        tile.flipHorizontal = Boolean.parseBoolean(element.getAttribute("flipHorizontal"));
        tile.flipVertical = Boolean.parseBoolean(element.getAttribute("flipVertical"));
        tile.index = Integer.parseInt(element.getAttribute("index"));
        tile.texture = level.findTextureRegion(tile.index - 1, tile.flipHorizontal, tile.flipVertical);
        return tile;
    }

}
