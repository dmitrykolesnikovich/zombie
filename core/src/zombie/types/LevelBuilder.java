package zombie.types;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
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
        XmlReader.Element pointElement = tileMapElement.getChildByName("offset").getChildByName("Point");

        // attributes
        Level level = new Level();
        level.tilesPerAtlasRow = Float.parseFloat(tileMapElement.getAttribute("tilesPerAtlasRow"));
        level.tilesPerAtlasColumn = Float.parseFloat(tileMapElement.getAttribute("tilesPerAtlasColumn"));
        level.tileWidth = Float.parseFloat(tileMapElement.getAttribute("tileWidth"));
        level.tileHeight = Float.parseFloat(tileMapElement.getAttribute("tileHeight"));
        level.tileBorderSize = Float.parseFloat(tileMapElement.getAttribute("tileBorderSize"));
        level.tileMapWidth = Float.parseFloat(tileMapElement.getAttribute("tileMapWidth"));
        level.tileMapHeight = Float.parseFloat(tileMapElement.getAttribute("tileMapHeight"));
        level.defaultScale = Float.parseFloat(tileMapElement.getAttribute("defaultScale"));
        level.maxScale = Float.parseFloat(tileMapElement.getAttribute("maxScale"));
        level.minScale = Float.parseFloat(tileMapElement.getAttribute("minScale"));
        level.offsetPoint = new Vector2(Float.parseFloat(pointElement.getAttribute("x")), Float.parseFloat(pointElement.getAttribute("y")));
        level.image = tileMapElement.getAttribute("image");
        level.texture = new Texture(dirPath + "/" + level.image);

        // mechanics
        level.tiles = TileAtlasBuilder.buildTileAtlas(tileMapElement, level);
        level.physics = PhysicsBuilder.buildPhysics(name, level);
        level.hero = level.addBody(0, "hero");
        level.camera.setToOrtho(true);
        level.pivot.set(level.offsetPoint.x, level.offsetPoint.y);

        // graphics
        level.tilesRenderer = new SpriteBatch();
        level.bodiesRenderer = new SpriteBatch();
        level.heroRenderer = new SpriteBatch();
        level.tilesOutlineRenderer = new ShapeRenderer();
        level.heroOutlineRenderer = new ShapeRenderer();
        level.cellsRenderer = new ShapeRenderer();
        level.waveRenderer = new SpriteBatch();

        return level;
    }

}
