package zombie;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import zombie.types.Cell;
import zombie.types.Hero;
import zombie.types.Level;
import zombie.types.Tile;

public class Renderer {

    private static final float[] RECTANGLE_2D = new float[8];
    private static final float[] RECTANGLE_ISO = new float[8];
    private static final Vector2 VECTOR_ISO = new Vector2();

    public static void drawBackground(Level level) {
        ScreenUtils.clear(level.backgroundColor);
    }

    public static void drawTiles(Level level) {
        SpriteBatch renderer = level.tilesRenderer;

        renderer.begin();
        for (Tile tile : level.tiles) {
            drawImage(renderer, tile.image, tile.x, tile.y, tile.width, tile.height);
        }
        renderer.end();
    }

    public static void drawHero(Level level) {
        SpriteBatch renderer = level.heroRenderer;
        Hero hero = level.hero;
        TextureRegion image = hero.getImageOrNull();
        Rectangle bounds = hero.getBounds();

        renderer.begin();
        drawImage(renderer, image, bounds);
        renderer.end();
    }

    public static void drawTilesOutline(Level level, Color color) {
        ShapeRenderer renderer = level.tilesOutlineRenderer;

        renderer.begin(ShapeRenderer.ShapeType.Line);
        for (Tile tile : level.tiles) {
            drawRectangle2d(renderer, tile.x, tile.y, tile.width, tile.height, color);
        }
        renderer.end();
    }

    public static void drawHeroOutline(Level level, Color color) {
        ShapeRenderer renderer = level.heroOutlineRenderer;
        Hero hero = level.hero;
        Rectangle bounds = hero.getBounds();

        renderer.begin(ShapeRenderer.ShapeType.Line);
        drawRectangle2d(renderer, bounds, color);
        renderer.end();
    }

    public static void drawCells(Level level) {
        ShapeRenderer renderer = level.cellsRenderer;

        renderer.begin(ShapeRenderer.ShapeType.Line);
        for (Cell cell : level.physics.cells) {
            float x = cell.j * level.cellSide;
            float y = cell.i * level.cellSide;
            drawRectangleIso(renderer, x, y, level.cellSide, level.cellSide, cell.zone.color);
        }
        renderer.end();
    }

    /*internals*/

    private static void drawImage(SpriteBatch renderer, TextureRegion image, Rectangle rectangle) {
        drawImage(renderer, image, rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
    }

    private static void drawImage(SpriteBatch renderer, TextureRegion image, float x, float y, float width, float height) {
        renderer.draw(image, x, y, width / 2, height / 2, width, height, 1, -1, 0);
    }

    private static void drawRectangle2d(ShapeRenderer renderer, Rectangle rectangle, Color color) {
        drawRectangle2d(renderer, rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), color);
    }

    private static void drawRectangle2d(ShapeRenderer renderer, float x, float y, float width, float height, Color color) {
        renderer.setColor(color);
        renderer.rect(x, y, width, height);
    }

    private static void drawRectangleIso(ShapeRenderer renderer, float x, float y, float width, float height, Color color) {
        renderer.setColor(color);

        // convert 2d to iso
        Utils.convert2dToIso(VECTOR_ISO.set(x, y));
        x = VECTOR_ISO.x;
        y = VECTOR_ISO.y;

        Utils.initializeRectangle2d(RECTANGLE_2D, x, y, width, height);
        Utils.convert2dToIso(RECTANGLE_2D, RECTANGLE_ISO);
        renderer.polygon(RECTANGLE_ISO);
    }

}
