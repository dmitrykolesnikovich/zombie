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
        XmlReader parser = new XmlReader();
        XmlReader.Element levelElement;
        try {
            levelElement = parser.parse(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(name);
        }
        XmlReader.Element pointElement = levelElement.getChildByName("offset").getChildByName("Point");

        // attributes
        Level level = new Level();
        level.tilesPerAtlasRow = Float.parseFloat(levelElement.getAttribute("tilesPerAtlasRow"));
        level.tilesPerAtlasColumn = Float.parseFloat(levelElement.getAttribute("tilesPerAtlasColumn"));
        level.tileWidth = Float.parseFloat(levelElement.getAttribute("tileWidth"));
        level.tileHeight = Float.parseFloat(levelElement.getAttribute("tileHeight"));
        level.tileBorderSize = Float.parseFloat(levelElement.getAttribute("tileBorderSize"));
        level.tileMapWidth = Float.parseFloat(levelElement.getAttribute("tileMapWidth"));
        level.tileMapHeight = Float.parseFloat(levelElement.getAttribute("tileMapHeight"));
        level.defaultScale = Float.parseFloat(levelElement.getAttribute("defaultScale"));
        level.maxScale = Float.parseFloat(levelElement.getAttribute("maxScale"));
        level.minScale = Float.parseFloat(levelElement.getAttribute("minScale"));
        level.offsetPoint = new Vector2(Float.parseFloat(pointElement.getAttribute("x")), Float.parseFloat(pointElement.getAttribute("y")));
        level.image = levelElement.getAttribute("image");
        level.texture = new Texture(dirPath + "/" + level.image);

        // mechanics
        level.tiles = TileAtlasBuilder.buildTileAtlas(levelElement, level);
        level.physics = PhysicsBuilder.buildPhysics(name, level);
        level.hero = level.addBody(0, "hero");
        level.camera.setToOrtho(true);
        level.pivot.set(level.offsetPoint.x, level.offsetPoint.y);

        return level;
    }

}
