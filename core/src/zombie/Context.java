package zombie;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import zombie.features.Input;
import zombie.types.Level;
import zombie.types.Tile;

import java.io.FileNotFoundException;

public class Context extends ApplicationAdapter {

    private static final Color backgroundColor = new Color(0x7AAAC9FF);
    public Level level;
    private SpriteBatch batch;
    private final Vector2 origin = new Vector2();
    public final OrthographicCamera camera = new OrthographicCamera();

    @Override
    public void create() {
        Gdx.input.setInputProcessor(new Input(this));

        batch = new SpriteBatch();
        try {
            level = Level.createLevel("main_island");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render() {
        ScreenUtils.clear(backgroundColor);
        batch.begin();
        for (Tile tile : level.tiles) {
            float x = tile.x - level.offsetPoint.x + level.origin.x;
            float y = tile.y - level.offsetPoint.y + level.origin.y;
            float width = tile.width;
            float height = tile.height;
            batch.draw(tile.texture, x, Gdx.graphics.getHeight() - y, width, height);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
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
        camera.position.set(origin.x + width / 2, origin.y - height / 2, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }

}
