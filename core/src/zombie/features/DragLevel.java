package zombie.features;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import zombie.Utils;
import zombie.types.Level;
import com.badlogic.gdx.Input;

public class DragLevel extends InputAdapter {

    private final Level level;
    private final Vector2 initialTouchPoint = new Vector2();
    private final Vector2 initialLevelPivot = new Vector2();
    private final Matrix4 initialTouchMatrix = new Matrix4();
    private final Vector2 currentTouchPoint = new Vector2();
    private boolean isDown = false;

    public DragLevel(Level level) {
        this.level = level;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button != Input.Buttons.LEFT || pointer > 0) return false;

        initialTouchPoint.set(screenX, screenY);
        Utils.unproject(level.camera, initialTouchPoint, initialTouchMatrix.set(level.camera.invProjectionView));
        initialLevelPivot.set(level.pivot);
        isDown = true;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (!isDown) return false;

        currentTouchPoint.set(screenX, screenY);
        Utils.unproject(level.camera, currentTouchPoint, initialTouchMatrix);
        float dx = currentTouchPoint.x - initialTouchPoint.x;
        float dy = currentTouchPoint.y - initialTouchPoint.y;
        level.pivot.x = initialLevelPivot.x - dx;
        level.pivot.y = initialLevelPivot.y - dy;
        level.updateCamera();
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        isDown = false;
        return true;
    }

}
