package zombie;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import zombie.features.DragLevelFeature;
import zombie.features.MoveHeroFeature;
import zombie.features.DebugFeature;
import zombie.features.ZoomLevelFeature;
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
                new DragLevelFeature(level),
                new ZoomLevelFeature(level),
                new MoveHeroFeature(level)
        ));

        // hero
        level.hero.animate("anim_woodcutter_walkwood_down", false);
        level.hero.placeTo(40, 40);
        level.hero.movementSpeed = 4;

        // bodies
        // todo
    }

    @Override
    public void render() {
        level.update(Gdx.graphics.getDeltaTime());

        Renderer.drawBackground(level);
        Renderer.drawTiles(level);
        Renderer.drawWave(level);
        Renderer.drawHero(level);
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
