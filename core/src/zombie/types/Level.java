package zombie.types;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import zombie.features.Isometry;

import java.util.ArrayList;
import java.util.List;

public class Level {

    public String name;
    public String dirPath;
    public String filePath;
    public float tilesPerAtlasRow;
    public float tilesPerAtlasColumn;
    public float tileWidth;
    public float tileHeight;
    public float tileBorderSize;
    public float tileMapWidth;
    public float tileMapHeight;
    public float defaultScale;
    public float maxScale;
    public float minScale;
    public Vector2 offsetPoint;
    public String image;

    /*mechanics*/

    public final Vector2 pivot = new Vector2();
    public Body hero;
    public InputMultiplexer inputController;

    /*physics*/

    public Physics physics;
    public final List<Body> bodies = new ArrayList<>();

    /*graphics*/

    public Animation wave;
    public TileAtlas tiles;
    public final OrthographicCamera camera = new OrthographicCamera();
    public final SpriteBatch tilesRenderer = new SpriteBatch();
    public final SpriteBatch bodiesRenderer = new SpriteBatch();
    public final SpriteBatch heroRenderer = new SpriteBatch();
    public final ShapeRenderer tilesOutlineRenderer = new ShapeRenderer();
    public final ShapeRenderer heroOutlineRenderer = new ShapeRenderer();
    public final ShapeRenderer cellsRenderer = new ShapeRenderer();
    public final SpriteBatch waveRenderer = new SpriteBatch();

    public Level() {
        camera.setToOrtho(true);
    }

    public void update(float deltaTime) {
        for (Body body : bodies) {
            body.update(deltaTime);
        }
        if (wave != null && !wave.update(deltaTime)) wave = null;
    }

    /*physics*/

    public Cell findCellOrNull(Vector2 point) {
        int[] location = Isometry.findCellLocationOrNull(point, physics.cellSide, physics.cellSide);
        if (location == null) return null;
        int i = location[0];
        int j = location[1];
        return physics.grid[i][j];
    }

    public Body addBody(int id, String name) {
        Body body = new Body(this, id, name);
        World.initializeBody(body);
        bodies.add(body);
        return body;
    }

    /*graphics*/

    public void updateCamera() {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        camera.position.set(pivot.x + width / 2f, pivot.y + height / 2f, 0);
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();

        tilesRenderer.setProjectionMatrix(camera.combined);
        bodiesRenderer.setProjectionMatrix(camera.combined);
        heroRenderer.setProjectionMatrix(camera.combined);
        tilesOutlineRenderer.setProjectionMatrix(camera.combined);
        heroOutlineRenderer.setProjectionMatrix(camera.combined);
        cellsRenderer.setProjectionMatrix(camera.combined);
        waveRenderer.setProjectionMatrix(camera.combined);
    }

    public void dispose() {
        tiles.dispose();
        tilesRenderer.dispose();
        bodiesRenderer.dispose();
        heroRenderer.dispose();
        tilesOutlineRenderer.dispose();
        heroOutlineRenderer.dispose();
        cellsRenderer.dispose();
        waveRenderer.dispose();
    }

    @Override
    public String toString() {
        return "Level('" + name + "')";
    }

}
