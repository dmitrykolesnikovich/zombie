package zombie.features;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import zombie.Utils;
import zombie.types.Level;

public class DragLevelFeature extends InputAdapter {

    private final Level level;
    private final Vector3 initialTouchPoint = new Vector3();
    private final Vector2 initialLevelPivot = new Vector2();
    private final Matrix4 initialTouchMatrix = new Matrix4();
    private final Vector3 currentTouchPoint = new Vector3();
    private boolean isDown = false;

    public DragLevelFeature(Level level) {
        this.level = level;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button != com.badlogic.gdx.Input.Buttons.LEFT || pointer > 0) return false;

        initialTouchPoint.set(screenX, screenY, 0);
        Utils.unproject(level.camera, initialTouchPoint, initialTouchMatrix.set(level.camera.invProjectionView));
        initialLevelPivot.set(level.pivot);
        isDown = true;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (!isDown) return false;

        currentTouchPoint.set(screenX, screenY, 0);
        Utils.unproject(level.camera, currentTouchPoint, initialTouchMatrix);
        float dx = currentTouchPoint.x - initialTouchPoint.x;
        float dy = currentTouchPoint.y - initialTouchPoint.y;
        level.pivot.x = initialLevelPivot.x - dx;
        level.pivot.y = initialLevelPivot.y - dy;
        level.resize();
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        isDown = false;
        return true;
    }

}
