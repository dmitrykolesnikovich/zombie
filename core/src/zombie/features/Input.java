package zombie.features;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import zombie.Context;

public class Input extends InputAdapter {

    private final Context context;
    private boolean isDown = false;
    private final Vector3 initialDraggingPoint = new Vector3();
    private final Vector3 currentDraggingPoint = new Vector3();
    private Vector2 currentOrigin = new Vector2();

    public Input(Context context) {
        this.context = context;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button != com.badlogic.gdx.Input.Buttons.LEFT || pointer > 0) return false;
        initialDraggingPoint.set(screenX, screenY, 0);
        context.camera.unproject(initialDraggingPoint);
        currentOrigin.set(context.level.origin);
        isDown = true;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (isDown) {
            currentDraggingPoint.set(screenX, screenY, 0);
            context.camera.unproject(currentDraggingPoint);
            Vector2 origin = context.level.origin;
            float dx = currentDraggingPoint.x - initialDraggingPoint.x;
            float dy = currentDraggingPoint.y - initialDraggingPoint.y;
            origin.x = currentOrigin.x + dx;
            origin.y = currentOrigin.y - dy;
            System.out.println("dragging: " + dx + ", " + dy);
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
        context.camera.zoom += amountX;
        context.camera.zoom = MathUtils.clamp(context.camera.zoom, context.level.minScale, context.level.maxScale);
        context.updateCamera();
        return true;
    }

}
