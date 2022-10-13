package zombie;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import zombie.features.*;
import zombie.types.Level;
import zombie.types.LevelBuilder;

public class Context extends ApplicationAdapter {

    private Level level;

    @Override
    public void create() {
        level = LevelBuilder.buildLevel("main_island");

        // 1. input
        Gdx.input.setInputProcessor(level.inputController);
    }

    @Override
    public void render() {
        // 2. update
        level.update(Gdx.graphics.getDeltaTime());

        // 3. draw
        Renderer.clearBackground();
        Renderer.drawTiles(level);
        Renderer.drawBodies(level);
        Renderer.drawWave(level);
        if (Renderer.isDebugEnabled) {
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
