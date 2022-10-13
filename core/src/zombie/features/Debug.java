package zombie.features;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import zombie.Context;

public class Debug extends InputAdapter {

    private final Context context;

    public Debug(Context context) {
        this.context = context;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.O) {
            context.isDebugEnabled = !context.isDebugEnabled;
            return true;
        }
        return false;
    }

}
