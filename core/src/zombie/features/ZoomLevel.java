package zombie.features;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.MathUtils;
import zombie.types.Level;

public class ZoomLevel extends InputAdapter {

    private final Level level;

    public ZoomLevel(Level level) {
        this.level = level;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        level.camera.zoom += amountY * 0.025;
        level.camera.zoom = MathUtils.clamp(level.camera.zoom, level.minScale, level.maxScale * 10);
        level.resize();
        return true;
    }

}
