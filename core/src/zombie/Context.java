package zombie;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import zombie.features.HeroMovement;
import zombie.features.LevelInput;
import zombie.types.*;

import java.io.FileNotFoundException;

public class Context extends ApplicationAdapter {

    private static final Color backgroundColor = new Color(/*0x7AAAC9FF*/0x000000FF);
    public Level level;
    public Physics physics;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    public final Vector2 cameraOrigin = new Vector2();
    public final OrthographicCamera camera = new OrthographicCamera();
    public final Hero hero = new Hero();
    private float totalTime;
    public final Vector2 heroOrigin = new Vector2();
    private final HeroMovement heroMovement = new HeroMovement(this, 16f);
    private final Vector2 _convert2dToIso = new Vector2();
    private final Vector2 _convert2dToIsoOrigin = new Vector2();
    private Texture exampleTexture;
    private TextureRegion exampleTextureRegion;

    @Override
    public void create() {
        Gdx.input.setInputProcessor(new InputMultiplexer(new LevelInput(this)));
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        camera.setToOrtho(true);
        exampleTexture = new Texture("example.jpg");
        exampleTextureRegion = new TextureRegion(exampleTexture);

        try {
            level = Level.createLevel("main_island");
            physics = Physics.createPhysics("main_island");
            cameraOrigin.set(level.offsetPoint.x, level.offsetPoint.y);
            // heroOrigin.set(level.offsetPoint.x, level.offsetPoint.y);
            updateCamera();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render() {
        totalTime += Gdx.graphics.getDeltaTime();
        ScreenUtils.clear(backgroundColor);

        batch.begin();

        // tiles
        for (Tile tile : level.tiles) {
            float x = tile.x;
            float y = tile.y;
            float width = tile.width;
            float height = tile.height;
            Utils.drawFrame(batch, tile.texture, x, y, width, height);
        }

        // hero
        heroMovement.update();
        TextureRegion heroFrame = hero.animation.delegate.getKeyFrame(totalTime, true);
        Utils.drawFrame(batch, heroFrame, heroOrigin.x, heroOrigin.y, heroFrame.getRegionWidth(), heroFrame.getRegionHeight());
        batch.end();

        // outline
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(new Color(1, 0.25f, 0.25f, 1));
        for (Tile tile : level.tiles) {
            float x = tile.x;
            float y = tile.y;
            float width = tile.width;
            float height = tile.height;
            // Utils.drawRectangle(shapeRenderer, x, y, width, height);
        }
        shapeRenderer.setColor(new Color(1, 1, 0.25f, 1));
        // Utils.drawRectangle(shapeRenderer, heroOrigin.x, heroOrigin.y, heroFrame.getRegionWidth(), heroFrame.getRegionHeight());
        shapeRenderer.end();

        // test iso with physics config
        float cellWidth = 16;
        float cellHeight = -16;
        Vector2 physicsOffset = new Vector2(-5 * cellWidth, 2 * cellHeight);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (Cell cell : physics.cells) {
            float x = cell.j * cellWidth + physicsOffset.x;
            float y = cell.i * cellHeight + physicsOffset.y;
            Utils.convert2dToIso(_convert2dToIso.set(x, y), _convert2dToIsoOrigin.set(0, 0));
            x = _convert2dToIso.x;
            y = _convert2dToIso.y;

            float ox = x;
            float oy = y;
            float x1 = x;
            float y1 = y;
            float x2 = x + cellWidth;
            float y2 = y;
            float x3 = x + cellWidth;
            float y3 = y + cellHeight;
            float x4 = x;
            float y4 = y + cellHeight;

            Utils.convert2dToIso(_convert2dToIso.set(x1, y1), _convert2dToIsoOrigin.set(ox, oy));
            x1 = _convert2dToIso.x;
            y1 = _convert2dToIso.y;

            Utils.convert2dToIso(_convert2dToIso.set(x2, y2), _convert2dToIsoOrigin.set(ox, oy));
            x2 = _convert2dToIso.x;
            y2 = _convert2dToIso.y;

            Utils.convert2dToIso(_convert2dToIso.set(x3, y3), _convert2dToIsoOrigin.set(ox, oy));
            x3 = _convert2dToIso.x;
            y3 = _convert2dToIso.y;

            Utils.convert2dToIso(_convert2dToIso.set(x4, y4), _convert2dToIsoOrigin.set(ox, oy));
            x4 = _convert2dToIso.x;
            y4 = _convert2dToIso.y;

            shapeRenderer.setColor(cell.zone.color);
            Utils.drawPolygon(shapeRenderer, new float[]{x1, y1, x2, y2, x3, y3, x4, y4});
        }
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        level.dispose();
    }

    @Override
    public void resize(int width, int height) {
        updateCamera();
    }

    /*convenience*/

    public void updateCamera() {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.position.set(cameraOrigin.x + width / 2, cameraOrigin.y + height / 2, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
    }

}
