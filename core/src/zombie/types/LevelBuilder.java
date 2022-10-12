package zombie.types;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class LevelBuilder {

    public static Level buildLevel(String name) {
        String dirPath = "maps/" + name;
        String filePath = dirPath + "/" + name + "_map_config.xml";
        XmlReader xml = new XmlReader();
        XmlReader.Element tileMapElement;
        try {
            tileMapElement = xml.parse(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(name);
        }

        // attributes
        Level level = new Level();
        level.image = tileMapElement.getAttribute("image");
        level.tileWidth = Float.parseFloat(tileMapElement.getAttribute("tileWidth"));
        level.tileHeight = Float.parseFloat(tileMapElement.getAttribute("tileHeight"));
        level.tileBorderSize = Float.parseFloat(tileMapElement.getAttribute("tileBorderSize"));
        level.tileMapWidth = Float.parseFloat(tileMapElement.getAttribute("tileMapWidth"));
        level.tileMapHeight = Float.parseFloat(tileMapElement.getAttribute("tileMapHeight"));
        level.defaultScale = Float.parseFloat(tileMapElement.getAttribute("defaultScale"));
        level.maxScale = Float.parseFloat(tileMapElement.getAttribute("maxScale"));
        level.minScale = Float.parseFloat(tileMapElement.getAttribute("minScale"));
        level.tilesPerAtlasRow = Float.parseFloat(tileMapElement.getAttribute("tilesPerAtlasRow"));
        level.tilesPerAtlasColumn = Float.parseFloat(tileMapElement.getAttribute("tilesPerAtlasColumn"));

        XmlReader.Element pointElement = tileMapElement.getChildByName("offset").getChildByName("Point");
        level.offsetPoint = new Vector2(Float.parseFloat(pointElement.getAttribute("x")), Float.parseFloat(pointElement.getAttribute("y")));

        // graphics
        int atlasSize = (int) (level.tilesPerAtlasRow * level.tilesPerAtlasColumn);
        level.atlas = new TextureRegion[atlasSize];
        level.texture = new Texture(dirPath + "/" + level.image);
        for (int row = 0; row < level.tilesPerAtlasRow; row++) {
            for (int column = 0; column < level.tilesPerAtlasColumn; column++) {
                float x = (2 * column + 1) * level.tileBorderSize + column * level.tileWidth;
                float y = (2 * row + 1) * level.tileBorderSize + row * level.tileHeight;
                TextureRegion image = new TextureRegion(level.texture, (int) x, (int) y, (int) level.tileWidth, (int) level.tileHeight);
                level.atlas[(int) (row * level.tilesPerAtlasColumn + column)] = image;
            }
        }
        level.atlasFlippedHorizontallyOnly = new TextureRegion[atlasSize];
        level.atlasFlippedVerticallyOnly = new TextureRegion[atlasSize];
        level.atlasFlippedBoth = new TextureRegion[atlasSize];

        // children
        Array<XmlReader.Element> tileElements = tileMapElement.getChildByName("items").getChildByName("list").getChildrenByName("Tile");
        for (XmlReader.Element tileElement : tileElements) {
            Tile tile = TileBuilder.buildTile(tileElement, level);
            level.tiles.add(tile);
        }

        // model
        level.physics = PhysicsBuilder.buildPhysics(name, level);
        level.pivot.set(level.offsetPoint.x, level.offsetPoint.y);
        level.camera.setToOrtho(true);
        level.hero = new Hero(level);

        // renderer
        level.tilesRenderer = new SpriteBatch();
        level.heroRenderer = new SpriteBatch();
        level.tilesOutlineRenderer = new ShapeRenderer();
        level.heroOutlineRenderer = new ShapeRenderer();
        level.cellsRenderer = new ShapeRenderer();

        return level;
    }

}
