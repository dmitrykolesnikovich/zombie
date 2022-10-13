package zombie.features;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class RendererDebug extends InputAdapter {

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.O) {
            Renderer.isDebugEnabled = !Renderer.isDebugEnabled;
            return true;
        }
        return false;
    }

}
