package zombie;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import zombie.features.*;
import zombie.types.Level;
import zombie.types.LevelBuilder;

public class Context extends ApplicationAdapter {

    public Level level;
    public boolean isDebugEnabled = false;

    @Override
    public void create() {
        level = LevelBuilder.buildLevel("main_island");
        Gdx.input.setInputProcessor(new InputMultiplexer(
                new Debug(this),
                new MoveHero(level),
                new DragLevel(level),
                new ZoomLevel(level)
        ));
    }

    @Override
    public void render() {
        // mechanics
        level.update(Gdx.graphics.getDeltaTime());

        // graphics
        Renderer.clearBackground();
        Renderer.drawTiles(level);
        Renderer.drawBodies(level);
        Renderer.drawWave(level);
        if (isDebugEnabled) {
            Renderer.drawCells(level);
        }
    }

    @Override
    public void resize(int width, int height) {
        level.updateCamera();
    }

    @Override
    public void dispose() {
        level.dispose();
    }

}
