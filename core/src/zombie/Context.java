package zombie;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import zombie.features.DragLevelFeature;
import zombie.features.MoveHeroFeature;
import zombie.features.DebugFeature;
import zombie.features.ZoomLevelFeature;
import zombie.types.Body;
import zombie.types.Level;
import zombie.types.LevelBuilder;

public class Context extends ApplicationAdapter {

    private static final Color PASSABLE_CELL_OUTLINE_COLOR = new Color(0.8f, 0.8f, 0.2f, 1);

    public Level level;
    public boolean isDebugEnabled = false;

    @Override
    public void create() {
        level = LevelBuilder.buildLevel("main_island");
        Gdx.input.setInputProcessor(new InputMultiplexer(
                new DebugFeature(this),
                new MoveHeroFeature(level),
                new DragLevelFeature(level),
                new ZoomLevelFeature(level)
        ));

        // hero
        level.hero.animate("anim_woodcutter_walkwood_down", false);
        level.hero.transform.placeTo(50, 28);
        level.hero.movementSpeed = 4;

        // bodies
        level.addBody(1, "sklep").transform.placeTo(50, 30);
        /*level.addBody(2, "tower").transform.placeTo(50, 40);
        level.addBody(3, "tropic_palm").transform.placeTo(50, 50);
        level.addBody(4, "oak").transform.placeTo(50, 60);
        level.addBody(5, "palm").transform.placeTo(50, 70);*/

        // camera
        level.pivot.y += 500;
        level.resize();
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
            Renderer.drawCells(level, PASSABLE_CELL_OUTLINE_COLOR);
        }
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
