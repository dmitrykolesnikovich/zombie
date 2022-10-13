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

    public static boolean isDebugEnabled = false;

    private static final Color BACKGROUND_COLOR = new Color(0x7AAAC9FF);
    private static final Color PASSABLE_CELL_COLOR = new Color(0.8f, 0.8f, 0.2f, 1);
    private static final Color NOT_PASSABLE_CELL_COLOR = new Color(0.2f, 0.2f, 0.2f, 1);

    public static void clearBackground() {
        ScreenUtils.clear(BACKGROUND_COLOR);
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

    public static void drawCells(Level level) {
        ShapeRenderer renderer = level.cellsRenderer;
        Body hero = level.hero;

        renderer.begin(ShapeRenderer.ShapeType.Line);
        for (Cell cell : level.physics.cells) {
            Color color = cell.isPassable() ? PASSABLE_CELL_COLOR : NOT_PASSABLE_CELL_COLOR;
            drawCell(renderer, cell, level.physics.cellSide, color);
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

        Utils.assignRectangle(RECTANGLE_ORTHO, x, y, width, height);
        Isometry.convertOrthoToIso(RECTANGLE_ORTHO, RECTANGLE_ISO);
        renderer.polygon(RECTANGLE_ISO);
    }

    // quickfix todo replace to Isometry
    private static final float[] RECTANGLE_ISO = new float[8];
    private static final float[] RECTANGLE_ORTHO = new float[8];
    private static final Vector2 VECTOR_ISO = new Vector2();

}
