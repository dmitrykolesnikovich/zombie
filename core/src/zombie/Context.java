package zombie;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import zombie.features.LevelInput;
import zombie.types.Cell;
import zombie.types.Hero;
import zombie.types.Level;
import zombie.types.Tile;

import java.io.FileNotFoundException;

public class Context extends ApplicationAdapter {

    private static final float CELL_SIDE = 16;
    private static final Color BACKGROUND_COLOR = new Color(0x000000FF); // todo replace with 0x7AAAC9FF
    private static final Color TILE_OUTLINE_COLOR = new Color(1, 0.25f, 0.25f, 1);
    private static final Color HERO_OUTLINE_COLOR = new Color(1, 1, 0.25f, 1);

    public Level level;
    private SpriteBatch tilesRenderer;
    private SpriteBatch heroRenderer;
    private ShapeRenderer tilesOutlineRenderer;
    private ShapeRenderer heroOutlineRenderer;
    private ShapeRenderer cellsRenderer;

    @Override
    public void create() {
        tilesRenderer = new SpriteBatch();
        heroRenderer = new SpriteBatch();
        tilesOutlineRenderer = new ShapeRenderer();
        heroOutlineRenderer = new ShapeRenderer();
        cellsRenderer = new ShapeRenderer();

        try {
            level = Level.createLevel("main_island");
            Gdx.input.setInputProcessor(new InputMultiplexer(new LevelInput(level)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render() {
        ScreenUtils.clear(BACKGROUND_COLOR);

        // tiles
        tilesRenderer.begin();
        for (Tile tile : level.tiles) {
            float x = tile.x;
            float y = tile.y;
            float width = tile.width;
            float height = tile.height;
            Utils.drawImage(tilesRenderer, tile.texture, x, y, width, height);
        }
        tilesRenderer.end();

        // hero
        Hero hero = level.hero;
        hero.update(Gdx.graphics.getDeltaTime());
        heroRenderer.begin();
        TextureRegion frame = hero.getAnimationFrame();
        Utils.drawImage(heroRenderer, frame, hero.origin.x, hero.origin.y, frame.getRegionWidth(), frame.getRegionHeight());
        heroRenderer.end();

        // tiles outline
        tilesOutlineRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (Tile tile : level.tiles) {
            Utils.drawRectangle2d(tilesOutlineRenderer, tile.x, tile.y, tile.width, tile.height, TILE_OUTLINE_COLOR);
        }
        tilesOutlineRenderer.end();

        // hero outline
        heroOutlineRenderer.begin(ShapeRenderer.ShapeType.Line);
        Utils.drawRectangle2d(heroOutlineRenderer, hero.origin.x, hero.origin.y, frame.getRegionWidth(), frame.getRegionHeight(), HERO_OUTLINE_COLOR);
        heroOutlineRenderer.end();

        // physics cells
        cellsRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (Cell cell : level.physics.cells) {
            float x = cell.j * CELL_SIDE;
            float y = cell.i * CELL_SIDE;
            Utils.drawRectangleIso(cellsRenderer, x, y, CELL_SIDE, CELL_SIDE, cell.zone.color);
        }
        cellsRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        level.resize(width, height);
        tilesRenderer.setProjectionMatrix(level.camera.combined);
        heroRenderer.setProjectionMatrix(level.camera.combined);
        tilesOutlineRenderer.setProjectionMatrix(level.camera.combined);
        heroOutlineRenderer.setProjectionMatrix(level.camera.combined);
        cellsRenderer.setProjectionMatrix(level.camera.combined);
    }

    @Override
    public void dispose() {
        level.dispose();
        tilesRenderer.dispose();
        heroRenderer.dispose();
        tilesOutlineRenderer.dispose();
        heroOutlineRenderer.dispose();
        cellsRenderer.dispose();
    }

}
