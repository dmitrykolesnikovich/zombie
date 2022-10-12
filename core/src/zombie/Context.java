package zombie;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import zombie.features.LevelInput;
import zombie.types.*;

import java.io.FileNotFoundException;

public class Context extends ApplicationAdapter {

    private static final float CELL_SIDE = 16;
    private static final Color backgroundColor = new Color(0x000000FF); // todo replace with 0x7AAAC9FF
    private static final Color tileOutlineColor = new Color(1, 0.25f, 0.25f, 1);
    private static final Color heroOutlineColor = new Color(1, 1, 0.25f, 1);

    /*model*/

    public Level level;
    public final OrthographicCamera camera = new OrthographicCamera();
    public final Hero hero = new Hero();

    /*renderer*/

    private SpriteBatch tilesRenderer;
    private SpriteBatch heroRenderer;
    private ShapeRenderer tilesOutlineRenderer;
    private ShapeRenderer heroOutlineRenderer;
    private ShapeRenderer cellsRenderer;
    private boolean isTilesDirty = true;
    private boolean isTilesOutlineDirty = true;
    private boolean isCellsDirty = true;

    @Override
    public void create() {
        Gdx.input.setInputProcessor(new InputMultiplexer(new LevelInput(this)));
        camera.setToOrtho(true);
        tilesRenderer = new SpriteBatch();
        heroRenderer = new SpriteBatch();
        tilesOutlineRenderer = new ShapeRenderer();
        heroOutlineRenderer = new ShapeRenderer();
        cellsRenderer = new ShapeRenderer();

        try {
            level = Level.createLevel("main_island");
            level.origin.set(level.offsetPoint.x, level.offsetPoint.y);
            // heroOrigin.set(level.offsetPoint.x, level.offsetPoint.y);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render() {
        ScreenUtils.clear(backgroundColor);

        // tiles
        if (isTilesDirty) {
            tilesRenderer.begin();
            for (Tile tile : level.tiles) {
                float x = tile.x;
                float y = tile.y;
                float width = tile.width;
                float height = tile.height;
                Utils.drawImage(heroRenderer, tile.texture, x, y, width, height);
            }
            tilesRenderer.end();
            isTilesDirty = false;
        } else {
            tilesRenderer.flush();
        }

        // hero
        hero.update(Gdx.graphics.getDeltaTime());
        heroRenderer.begin();
        TextureRegion frame = hero.getAnimationFrame();
        Utils.drawImage(heroRenderer, frame, hero.origin.x, hero.origin.y, frame.getRegionWidth(), frame.getRegionHeight());
        heroRenderer.end();

        // tiles outline
        if (isTilesOutlineDirty) {
            tilesOutlineRenderer.begin(ShapeRenderer.ShapeType.Line);
            for (Tile tile : level.tiles) {
                Utils.drawRectangle2d(tilesOutlineRenderer, tile.x, tile.y, tile.width, tile.height, tileOutlineColor);
            }
            tilesOutlineRenderer.end();
            isTilesOutlineDirty = false;
        } else {
            tilesOutlineRenderer.flush();
        }

        // hero outline
        heroOutlineRenderer.begin(ShapeRenderer.ShapeType.Line);
        Utils.drawRectangle2d(heroOutlineRenderer, hero.origin.x, hero.origin.y, frame.getRegionWidth(), frame.getRegionHeight(), heroOutlineColor);
        heroOutlineRenderer.end();

        // physics cells
        if (isCellsDirty) {
            cellsRenderer.begin(ShapeRenderer.ShapeType.Line);
            for (Cell cell : level.physics.cells) {
                float x = cell.j * CELL_SIDE;
                float y = cell.i * CELL_SIDE;
                Utils.drawRectangleIso(cellsRenderer, x, y, CELL_SIDE, CELL_SIDE, cell.zone.color);
            }
            cellsRenderer.end();
            isCellsDirty = false;
        } else {
            cellsRenderer.flush();
        }
    }

    public void resize() {
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void resize(int width, int height) {
        camera.position.set(level.origin.x + width / 2f, level.origin.y + height / 2f, 0);
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
        heroRenderer.setProjectionMatrix(camera.combined);
        heroOutlineRenderer.setProjectionMatrix(camera.combined);
    }

    @Override
    public void dispose() {
        heroRenderer.dispose();
        heroOutlineRenderer.dispose();
        level.dispose();
    }

}
