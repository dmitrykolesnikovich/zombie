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

    public TileAtlas tiles;
    public Physics physics;
    public Body hero;
    public List<Body> bodies = new ArrayList<>();
    public final OrthographicCamera camera = new OrthographicCamera();
    public final Vector2 pivot = new Vector2();
    public Color backgroundColor = new Color(0x7AAAC9FF);
    public Animation wave;

    /*graphics*/

    public SpriteBatch tilesRenderer;
    public SpriteBatch bodiesRenderer;
    public SpriteBatch heroRenderer;
    public ShapeRenderer tilesOutlineRenderer;
    public ShapeRenderer heroOutlineRenderer;
    public ShapeRenderer cellsRenderer;
    public SpriteBatch waveRenderer;

    public void update(float deltaTime) {
        for (Body body : bodies) body.update(deltaTime);
        if (wave != null && !wave.update(deltaTime)) wave = null;
    }

    public void resize() {
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void resize(int width, int height) {
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

    public Body addBody(int id, String bodyName) {
        Body body = BodyBuilder.buildBody(id, bodyName, this);
        bodies.add(body);
        return body;
    }

    public Cell findCellOrNull(Vector2 point) {
        int[] location = Isometry.findCellLocationOrNull(point, physics.cellSide, physics.cellSide);
        if (location == null) return null;
        int i = location[0];
        int j = location[1];
        return physics.grid[i][j];
    }

}
