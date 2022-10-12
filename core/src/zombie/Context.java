package zombie;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import zombie.features.LevelInput;
import zombie.types.Level;

import java.io.FileNotFoundException;

public class Context extends ApplicationAdapter {

    public Level level;

    @Override
    public void create() {
        try {
            level = Level.createLevel("main_island");
            Gdx.input.setInputProcessor(new InputMultiplexer(new LevelInput(level)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render() {
        level.update(Gdx.graphics.getDeltaTime());

        Renderer.drawBackground(level);
        Renderer.drawTiles(level);
        Renderer.drawHero(level);
        Renderer.drawTilesOutline(level);
        Renderer.drawHeroOutline(level);
        Renderer.drawCells(level);
    }

    @Override
    public void resize(int width, int height) {
        level.resize(width, height);
    }

    @Override
    public void dispose() {
        level.dispose();
    }

}
