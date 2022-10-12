package zombie.features;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import zombie.Utils;
import zombie.types.Cell;
import zombie.types.Level;

public class MoveHeroFeature extends InputAdapter {

    private final Level level;
    private final Vector2 touchPoint = new Vector2();

    public MoveHeroFeature(Level level) {
        this.level = level;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchPoint.set(screenX, screenY);
        Utils.unproject(level.camera, touchPoint);
        Cell cell = level.findCellOrNull(touchPoint);
        System.out.println("cell: " + cell);
        if (cell == null) return false;
        level.hero.placeTo(cell);
        return true;
    }

}
