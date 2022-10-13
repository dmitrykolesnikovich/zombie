package zombie.features;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import zombie.types.Level;

public class RendererDebug extends InputAdapter {

    private final Level level;

    public RendererDebug(Level level) {
        this.level = level;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.O) {
            Renderer.isDebugEnabled = !Renderer.isDebugEnabled;
            return true;
        }
        return false;
    }

}
