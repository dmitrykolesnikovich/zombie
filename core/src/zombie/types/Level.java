package zombie.types;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.XmlReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Level implements Disposable {

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

    public float cellSide = 16;
    public Color backgroundColor = new Color(0x000000FF); // todo replace with 0x7AAAC9FF;

    /*model*/

    public Physics physics;
    public final Vector2 origin = new Vector2();
    public final OrthographicCamera camera = new OrthographicCamera();
    public Hero hero;

    /*render*/

    public SpriteBatch tilesRenderer;
    public SpriteBatch heroRenderer;
    public ShapeRenderer tilesOutlineRenderer;
    public ShapeRenderer heroOutlineRenderer;
    public ShapeRenderer cellsRenderer;

    /*textures*/

    public Texture texture;
    public TextureRegion[] atlas;
    public TextureRegion[] atlasFlippedHorizontallyOnly;
    public TextureRegion[] atlasFlippedVerticallyOnly;
    public TextureRegion[] atlasFlippedBoth;

    public void update(float deltaTime) {
        hero.update(deltaTime);
    }

    public void resize() {
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void resize(int width, int height) {
        camera.position.set(origin.x + width / 2f, origin.y + height / 2f, 0);
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();

        tilesRenderer.setProjectionMatrix(camera.combined);
        heroRenderer.setProjectionMatrix(camera.combined);
        tilesOutlineRenderer.setProjectionMatrix(camera.combined);
        heroOutlineRenderer.setProjectionMatrix(camera.combined);
        cellsRenderer.setProjectionMatrix(camera.combined);
    }

    @Override
    public void dispose() {
        texture.dispose();

        tilesRenderer.dispose();
        heroRenderer.dispose();
        tilesOutlineRenderer.dispose();
        heroOutlineRenderer.dispose();
        cellsRenderer.dispose();
    }

    TextureRegion findImage(int index, boolean flipHorizontal, boolean flippedVertical) {
        boolean isFlippedNone = !flipHorizontal && !flippedVertical;
        boolean isFlippedHorizontallyOnly = flipHorizontal && !flippedVertical;
        boolean isFlippedVerticallyOnly = !flipHorizontal && flippedVertical;
        boolean isFlippedBoth = flipHorizontal && flippedVertical;
        if (isFlippedNone) return atlas[index];

        // read from cache
        TextureRegion image = null;
        if (isFlippedHorizontallyOnly) image = atlasFlippedHorizontallyOnly[index];
        if (isFlippedVerticallyOnly) image = atlasFlippedVerticallyOnly[index];
        if (isFlippedBoth) image = atlasFlippedBoth[index];

        // write to cache
        if (image == null) {
            image = new TextureRegion(atlas[index]);
            image.flip(isFlippedHorizontallyOnly, isFlippedVerticallyOnly);
        }
        if (isFlippedHorizontallyOnly) atlasFlippedHorizontallyOnly[index] = image;
        if (isFlippedVerticallyOnly) atlasFlippedVerticallyOnly[index] = image;
        if (isFlippedBoth) atlasFlippedBoth[index] = image;

        // validate
        if (image == null) throw new IllegalStateException("index: " + index);
        return image;
    }

    public static Level createLevel(String name) throws FileNotFoundException {
        String dirPath = "maps/" + name;
        String filePath = dirPath + "/" + name + "_map_config.xml";
        XmlReader xml = new XmlReader();
        XmlReader.Element tileMapElement = xml.parse(new FileReader(filePath));

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
            Tile tile = Tile.createTile(level, tileElement);
            level.tiles.add(tile);
        }

        // model
        level.physics = Physics.createPhysics(name);
        level.origin.set(level.offsetPoint.x, level.offsetPoint.y);
        level.camera.setToOrtho(true);
        level.hero = new Hero();

        // renderer
        level.tilesRenderer = new SpriteBatch();
        level.heroRenderer = new SpriteBatch();
        level.tilesOutlineRenderer = new ShapeRenderer();
        level.heroOutlineRenderer = new ShapeRenderer();
        level.cellsRenderer = new ShapeRenderer();

        return level;
    }

}
