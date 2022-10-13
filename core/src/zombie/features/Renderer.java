package zombie.features;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import zombie.Utils;
import zombie.types.*;

import java.util.List;

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

    public static void drawBodies(Level level) {
        SpriteBatch renderer = level.bodiesRenderer;
        List<Body> bodies = DepthSorting.sortBodies(level.bodies);

        renderer.begin();
        for (Body body : bodies) {
            if (!body.isVisible) continue;
            TextureRegion image = body.getImageOrNull();
            Rectangle bounds = body.getBounds();
            drawImage(renderer, image, bounds);
        }
        renderer.end();
    }

    public static void drawTilesOutline(Level level, Color color) {
        ShapeRenderer renderer = level.tilesOutlineRenderer;

        renderer.begin(ShapeRenderer.ShapeType.Line);
        for (Tile tile : level.tiles) {
            drawRectangleOrtho(renderer, tile.x, tile.y, tile.width, tile.height, color);
        }
        renderer.end();
    }

    public static void drawHeroOutline(Level level, Color color) {
        ShapeRenderer renderer = level.heroOutlineRenderer;
        Body hero = level.hero;
        Rectangle bounds = hero.getBounds();

        renderer.begin(ShapeRenderer.ShapeType.Line);
        drawRectangleOrtho(renderer, bounds, color);
        renderer.end();
    }

    public static void drawCells(Level level, Color color) {
        ShapeRenderer renderer = level.cellsRenderer;
        Body hero = level.hero;

        renderer.begin(ShapeRenderer.ShapeType.Line);
        for (Cell cell : level.physics.cells) {
            drawCell(renderer, cell, level.physics.cellSide, cell.isPassable() ? color : Color.DARK_GRAY);
        }
        if (hero != null) {
            Cell heroCell = hero.getCellOrNull();
            drawCell(renderer, heroCell, level.physics.cellSide, Color.RED);
        }
        renderer.end();
    }

    public static void drawWave(Level level) {
        SpriteBatch renderer = level.waveRenderer;
        Animation wave = level.wave;
        if (wave == null) return;
        TextureRegion image = wave.getImage();

        renderer.begin();
        drawImage(renderer, image, wave.getBounds());
        renderer.end();
    }

    /*internals*/

    private static void drawCell(ShapeRenderer renderer, Cell cell, float cellSide, Color color) {
        float x = cell.j * cellSide;
        float y = cell.i * cellSide;
        drawRectangleIso(renderer, x, y, cellSide, cellSide, color);
    }

    private static void drawImage(SpriteBatch renderer, TextureRegion image, Rectangle rectangle) {
        drawImage(renderer, image, rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
    }

    private static void drawImage(SpriteBatch renderer, TextureRegion image, float x, float y, float width, float height) {
        renderer.draw(image, x, y, width / 2, height / 2, width, height, 1, -1, 0);
    }

    private static void drawRectangleOrtho(ShapeRenderer renderer, Rectangle rectangle, Color color) {
        drawRectangleOrtho(renderer, rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), color);
    }

    private static void drawRectangleOrtho(ShapeRenderer renderer, float x, float y, float width, float height, Color color) {
        renderer.setColor(color);
        renderer.rect(x, y, width, height);
    }

    private static void drawRectangleIso(ShapeRenderer renderer, float x, float y, float width, float height, Color color) {
        renderer.setColor(color);

        // convert ortho to iso
        Isometry.convertOrthoToIso(VECTOR_ISO.set(x, y));
        x = VECTOR_ISO.x;
        y = VECTOR_ISO.y;

        Utils.initializeRectangleOrtho(RECTANGLE_2D, x, y, width, height);
        Isometry.convertOrthoToIso(RECTANGLE_2D, RECTANGLE_ISO);
        renderer.polygon(RECTANGLE_ISO);
    }

}