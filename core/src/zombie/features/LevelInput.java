package zombie.features;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import zombie.Context;
import zombie.Utils;
import zombie.types.Level;

public class LevelInput extends InputAdapter {

    private final Level level;
    private final Vector3 initialPoint = new Vector3();
    private final Vector2 initialLevelOrigin = new Vector2();
    private final Matrix4 initialCameraMatrix = new Matrix4();
    private final Vector3 currentPoint = new Vector3();
    private boolean isDown = false;

    public LevelInput(Level level) {
        this.level = level;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button != com.badlogic.gdx.Input.Buttons.LEFT || pointer > 0) return false;
        initialPoint.set(screenX, screenY, 0);
        initialLevelOrigin.set(level.origin);
        initialCameraMatrix.set(level.camera.invProjectionView);
        Utils.unproject(level.camera, initialPoint, initialCameraMatrix);
        isDown = true;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (isDown) {
            currentPoint.set(screenX, screenY, 0);
            Utils.unproject(level.camera, currentPoint, initialCameraMatrix);
            float dx = currentPoint.x - initialPoint.x;
            float dy = currentPoint.y - initialPoint.y;
            level.origin.x = initialLevelOrigin.x - dx;
            level.origin.y = initialLevelOrigin.y - dy;
            level.resize();
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        isDown = false;
        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        level.camera.zoom += amountY * 0.025;
        level.camera.zoom = MathUtils.clamp(level.camera.zoom, level.minScale, level.maxScale * 10);
        level.resize();
        return true;
    }

}
