package zombie.types;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.XmlReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Level implements Disposable {

    // config
    public float defaultScale;
    public String image;
    public float maxScale;
    public float minScale;
    public float tileBorderSize;
    public float tileHeight;
    public float tileMapHeight;
    public float tileMapWidth;
    public float tileWidth;
    public float tilesPerAtlasColumn;
    public float tilesPerAtlasRow;
    public List<Tile> tiles = new ArrayList<>();
    public Vector2 offsetPoint;

    // graphics
    public Texture texture;
    public TextureRegion[] atlas;
    public TextureRegion[] atlasFlippedHorizontallyOnly;
    public TextureRegion[] atlasFlippedVerticallyOnly;
    public TextureRegion[] atlasFlippedBoth;

    public Physics physics;
    public final Vector2 origin = new Vector2();

    @Override
    public void dispose() {
        texture.dispose();
    }

    public static Level createLevel(String name) throws FileNotFoundException {
        String dirPath = "maps/" + name;
        String filePath = dirPath + "/" + name + "_map_config.xml";
        XmlReader xml = new XmlReader();
        XmlReader.Element tileMapElement = xml.parse(new FileReader(filePath));

        // attributes
        Level result = new Level();
        result.image = tileMapElement.getAttribute("image");
        result.tileWidth = Float.parseFloat(tileMapElement.getAttribute("tileWidth"));
        result.tileHeight = Float.parseFloat(tileMapElement.getAttribute("tileHeight"));
        result.tileBorderSize = Float.parseFloat(tileMapElement.getAttribute("tileBorderSize"));
        result.tileMapWidth = Float.parseFloat(tileMapElement.getAttribute("tileMapWidth"));
        result.tileMapHeight = Float.parseFloat(tileMapElement.getAttribute("tileMapHeight"));
        result.defaultScale = Float.parseFloat(tileMapElement.getAttribute("defaultScale"));
        result.maxScale = Float.parseFloat(tileMapElement.getAttribute("maxScale"));
        result.minScale = Float.parseFloat(tileMapElement.getAttribute("minScale"));
        result.tilesPerAtlasRow = Float.parseFloat(tileMapElement.getAttribute("tilesPerAtlasRow"));
        result.tilesPerAtlasColumn = Float.parseFloat(tileMapElement.getAttribute("tilesPerAtlasColumn"));

        XmlReader.Element pointElement = tileMapElement.getChildByName("offset").getChildByName("Point");
        result.offsetPoint = new Vector2(Float.parseFloat(pointElement.getAttribute("x")), Float.parseFloat(pointElement.getAttribute("y")));

        // graphics
        int atlasSize = (int) (result.tilesPerAtlasRow * result.tilesPerAtlasColumn);
        result.atlas = new TextureRegion[atlasSize];
        result.texture = new Texture(dirPath + "/" + result.image);
        for (int row = 0; row < result.tilesPerAtlasRow; row++) {
            for (int column = 0; column < result.tilesPerAtlasColumn; column++) {
                float x = (2 * column + 1) * result.tileBorderSize + column * result.tileWidth;
                float y = (2 * row + 1) * result.tileBorderSize + row * result.tileHeight;
                TextureRegion atlasTexture = new TextureRegion(result.texture, (int) x, (int) y, (int) result.tileWidth, (int) result.tileHeight);
                result.atlas[(int) (row * result.tilesPerAtlasColumn + column)] = atlasTexture;
            }
        }
        result.atlasFlippedHorizontallyOnly = new TextureRegion[atlasSize];
        result.atlasFlippedVerticallyOnly = new TextureRegion[atlasSize];
        result.atlasFlippedBoth = new TextureRegion[atlasSize];

        // children
        Array<XmlReader.Element> tileElements = tileMapElement.getChildByName("items").getChildByName("list").getChildrenByName("Tile");
        for (XmlReader.Element tileElement : tileElements) {
            Tile tile = Tile.createTile(result, tileElement);
            result.tiles.add(tile);
        }

        result.physics = Physics.createPhysics(name);
        return result;
    }

    TextureRegion findTextureRegion(int index, boolean flipHorizontal, boolean flippedVertical) {
        boolean isFlippedNone = !flipHorizontal && !flippedVertical;
        boolean isFlippedHorizontallyOnly = flipHorizontal && !flippedVertical;
        boolean isFlippedVerticallyOnly = !flipHorizontal && flippedVertical;
        boolean isFlippedBoth = flipHorizontal && flippedVertical;
        if (isFlippedNone) return atlas[index];

        // read from cache
        TextureRegion result = null;
        if (isFlippedHorizontallyOnly) result = atlasFlippedHorizontallyOnly[index];
        if (isFlippedVerticallyOnly) result = atlasFlippedVerticallyOnly[index];
        if (isFlippedBoth) result = atlasFlippedBoth[index];

        // write to cache
        if (result == null) {
            result = new TextureRegion(atlas[index]);
            result.flip(isFlippedHorizontallyOnly, isFlippedVerticallyOnly);
        }
        if (isFlippedHorizontallyOnly) atlasFlippedHorizontallyOnly[index] = result;
        if (isFlippedVerticallyOnly) atlasFlippedVerticallyOnly[index] = result;
        if (isFlippedBoth) atlasFlippedBoth[index] = result;

        // validate
        if (result == null) throw new IllegalStateException("index: " + index);
        return result;
    }

}
