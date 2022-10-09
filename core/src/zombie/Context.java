package zombie;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import zombie.features.Input;
import zombie.types.Level;
import zombie.types.Tile;

import java.io.FileNotFoundException;

public class Context extends ApplicationAdapter {

    public Level level;
    private SpriteBatch batch;

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
        ScreenUtils.clear(1f, 0.25f, 0.25f, 1);

        batch.begin();
        for (Tile tile : level.tiles) {
            float x = (tile.x - level.offsetPoint.x + level.origin.x);
            float y = (tile.y - level.offsetPoint.y + level.origin.y);
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

}
