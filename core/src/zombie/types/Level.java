package zombie.types;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

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

    /*model*/

    public float cellSide = 16;
    public Color backgroundColor = new Color(0x000000FF); // todo replace with 0x7AAAC9FF;
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

    /*cache*/

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

    public TextureRegion findImage(int index, boolean flipHorizontal, boolean flippedVertical) {
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

}
