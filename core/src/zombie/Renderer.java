package zombie;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import zombie.types.Cell;
import zombie.types.Hero;
import zombie.types.Level;
import zombie.types.Tile;

public class Renderer {

    private static final Color TILE_OUTLINE_COLOR = new Color(1, 0.25f, 0.25f, 1);
    private static final Color HERO_OUTLINE_COLOR = new Color(1, 1, 0.25f, 1);

    public static void drawBackground(Level level) {
        ScreenUtils.clear(level.backgroundColor);
    }

    public static void drawTiles(Level level) {
        SpriteBatch renderer = level.tilesRenderer;

        renderer.begin();
        for (Tile tile : level.tiles) {
            Utils.drawImage(renderer, tile.image, tile.x, tile.y, tile.width, tile.height);
        }
        renderer.end();
    }

    public static void drawHero(Level level) {
        SpriteBatch renderer = level.heroRenderer;
        Hero hero = level.hero;
        TextureRegion image = hero.getImage();

        renderer.begin();
        Utils.drawImage(renderer, image, hero.origin.x, hero.origin.y, image.getRegionWidth(), image.getRegionHeight());
        renderer.end();
    }

    public static void drawTilesOutline(Level level) {
        ShapeRenderer renderer = level.tilesOutlineRenderer;

        renderer.begin(ShapeRenderer.ShapeType.Line);
        for (Tile tile : level.tiles) {
            Utils.drawRectangle2d(renderer, tile.x, tile.y, tile.width, tile.height, TILE_OUTLINE_COLOR);
        }
        renderer.end();
    }

    public static void drawHeroOutline(Level level) {
        ShapeRenderer renderer = level.heroOutlineRenderer;
        Hero hero = level.hero;
        TextureRegion image = hero.getImage();

        renderer.begin(ShapeRenderer.ShapeType.Line);
        Utils.drawRectangle2d(renderer, hero.origin.x, hero.origin.y, image.getRegionWidth(), image.getRegionHeight(), HERO_OUTLINE_COLOR);
        renderer.end();
    }

    public static void drawCells(Level level) {
        ShapeRenderer renderer = level.cellsRenderer;

        renderer.begin(ShapeRenderer.ShapeType.Line);
        for (Cell cell : level.physics.cells) {
            float x = cell.j * level.cellSide;
            float y = cell.i * level.cellSide;
            Utils.drawRectangleIso(renderer, x, y, level.cellSide, level.cellSide, cell.zone.color);
        }
        renderer.end();
    }

}
