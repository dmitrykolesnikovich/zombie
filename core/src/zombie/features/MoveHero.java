package zombie.features;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import zombie.Utils;
import zombie.types.AnimationBuilder;
import zombie.types.Cell;
import zombie.types.Level;

public class MoveHero extends InputAdapter {

    private final Level level;
    private final Vector2 touchPoint = new Vector2();

    public MoveHero(Level level) {
        this.level = level;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (level.hero == null) return false;

        touchPoint.set(screenX, screenY);
        Utils.unproject(level.camera, touchPoint);
        Cell cell = level.findCellOrNull(touchPoint);
        System.out.println("cell: " + cell);
        if (cell == null) return false;
        if (!cell.isPassable()) return false;

        level.hero.transform.moveTo(cell);
        level.wave = AnimationBuilder.buildAnimation("white_wave", false);
        level.wave.position.set(cell.getCenter());
        level.wave.duration = 0.77f;
        level.wave.setFps(44);
        return true;
    }

}
