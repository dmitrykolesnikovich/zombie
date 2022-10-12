package zombie.features;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import zombie.Context;
import zombie.Utils;

public class LevelInput extends InputAdapter {

    private final Context context;
    private final Vector3 initialPoint = new Vector3();
    private final Vector3 currentPoint = new Vector3();
    private final Matrix4 initialCameraMatrix = new Matrix4();
    private final Vector2 initialLevelOrigin = new Vector2();
    private boolean isDown = false;

    public LevelInput(Context context) {
        this.context = context;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button != com.badlogic.gdx.Input.Buttons.LEFT || pointer > 0) return false;
        initialPoint.set(screenX, screenY, 0);
        initialCameraMatrix.set(context.camera.invProjectionView);
        Utils.unproject(context.camera, initialPoint, initialCameraMatrix);
        initialLevelOrigin.set(context.level.origin);
        isDown = true;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (isDown) {
            currentPoint.set(screenX, screenY, 0);
            Utils.unproject(context.camera, currentPoint, initialCameraMatrix);
            float dx = currentPoint.x - initialPoint.x;
            float dy = currentPoint.y - initialPoint.y;
            context.level.origin.x = initialLevelOrigin.x - dx;
            context.level.origin.y = initialLevelOrigin.y - dy;
            context.resize();
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
        context.camera.zoom += amountY * 0.025;
        context.camera.zoom = MathUtils.clamp(context.camera.zoom, context.level.minScale, context.level.maxScale * 10);
        context.resize();
        return true;
    }

}
