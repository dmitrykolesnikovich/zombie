package zombie.types;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import zombie.features.Isometry;

import java.util.ArrayList;
import java.util.List;

public class Level implements Disposable {

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
    public Texture texture;

    /*mechanics*/

    public final OrthographicCamera camera = new OrthographicCamera();
    public TileAtlas tiles;
    public Physics physics;
    public Body hero;
    public List<Body> bodies = new ArrayList<>();
    public final Vector2 pivot = new Vector2();
    public Color backgroundColor = new Color(0x7AAAC9FF);
    public Animation wave; // quickfix todo improve

    /*graphics*/

    public SpriteBatch tilesRenderer = new SpriteBatch();
    public SpriteBatch bodiesRenderer = new SpriteBatch();
    public SpriteBatch heroRenderer = new SpriteBatch();
    public ShapeRenderer tilesOutlineRenderer = new ShapeRenderer();
    public ShapeRenderer heroOutlineRenderer = new ShapeRenderer();
    public ShapeRenderer cellsRenderer = new ShapeRenderer();
    public SpriteBatch waveRenderer = new SpriteBatch();

    public void update(float deltaTime) {
        for (Body body : bodies) body.update(deltaTime);
        if (wave != null && !wave.update(deltaTime)) wave = null;
    }

    @Override
    public void dispose() {
        texture.dispose();

        tilesRenderer.dispose();
        bodiesRenderer.dispose();
        heroRenderer.dispose();
        tilesOutlineRenderer.dispose();
        heroOutlineRenderer.dispose();
        cellsRenderer.dispose();
        waveRenderer.dispose();
    }

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

    public Cell findCellOrNull(Vector2 point) {
        int[] location = Isometry.findCellLocationOrNull(point, physics.cellSide, physics.cellSide);
        if (location == null) return null;
        int i = location[0];
        int j = location[1];
        return physics.grid[i][j];
    }

    public Body addBody(int id, String bodyName) {
        Body body = BodyBuilder.buildBody(id, bodyName, this);
        bodies.add(body);
        return body;
    }

}
