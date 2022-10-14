package zombie.features;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import zombie.Utils;
import zombie.types.Cell;
import zombie.types.Level;

public class MoveHero extends InputAdapter {

    private final Level level;
    private final Vector2 touchDownPoint = new Vector2();
    private final Vector2 touchUpPoint = new Vector2();

    public MoveHero(Level level) {
        this.level = level;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (level.hero.transform.isMoving()) return false;
        touchDownPoint.set(screenX, screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (level.hero.transform.isMoving()) return false;
        touchUpPoint.set(screenX, screenY);
        if (touchUpPoint.dst(touchDownPoint) >= 2) return false;

        Utils.unproject(level.camera, touchUpPoint);
        Cell cell = level.findCellOrNull(touchUpPoint);
        System.out.println("cell: " + cell);
        if (cell == null) return false;
        if (!cell.isPassable()) return false;

        boolean success = level.hero.transform.moveTo(cell);
        if (success) {
            Wave.animateWave(cell);
            return true;
        }
        return false;
    }

}
