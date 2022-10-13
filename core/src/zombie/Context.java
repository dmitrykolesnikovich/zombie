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

        // hero
        level.hero.transform.placeTo(50, 28);
        level.hero.speed = 4;

        // bodies
        level.addBody(1, "sklep").transform.placeTo(50, 30);
        level.addBody(2, "tower").transform.placeTo(50, 40);
        level.addBody(3, "tropic_palm").transform.placeTo(50, 50);
        level.addBody(4, "oak").transform.placeTo(50, 60);
        level.addBody(5, "palm").transform.placeTo(50, 70);

        // camera
        level.pivot.y += 500;
        level.updateCamera();
    }

    @Override
    public void render() {
        // mechanics
        level.update(Gdx.graphics.getDeltaTime());

        // graphics
        Renderer.drawBackground(level);
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
