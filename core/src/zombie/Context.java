package zombie;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import zombie.features.DragLevelFeature;
import zombie.features.MoveHeroFeature;
import zombie.features.ZoomLevelFeature;
import zombie.types.Cell;
import zombie.types.Level;
import zombie.types.LevelBuilder;
import zombie.types.Movement;

public class Context extends ApplicationAdapter {

    private static final Color TILE_OUTLINE_COLOR = new Color(1, 0.25f, 0.25f, 1);
    private static final Color HERO_OUTLINE_COLOR = new Color(1, 1, 0.25f, 1);

    public Level level;

    @Override
    public void create() {
        level = LevelBuilder.buildLevel("main_island");
        Gdx.input.setInputProcessor(new InputMultiplexer(
                new DragLevelFeature(level),
                new ZoomLevelFeature(level),
                new MoveHeroFeature(level)
        ));

        // debug
        level.hero.animate("anim_woodcutter_walkwood_down", false);
        Cell[][] grid = level.physics.grid;
        level.hero.move(new Cell[]{
                grid[0][30], grid[0][31], grid[0][32], grid[0][33], grid[0][34], grid[0][35], grid[0][36], grid[0][37], grid[0][38], grid[0][39], grid[0][40]
        });
    }

    @Override
    public void render() {
        level.update(Gdx.graphics.getDeltaTime());

        Renderer.drawBackground(level);
        Renderer.drawTiles(level);
        Renderer.drawHero(level);
        Renderer.drawTilesOutline(level, TILE_OUTLINE_COLOR);
        Renderer.drawHeroOutline(level, HERO_OUTLINE_COLOR);
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
