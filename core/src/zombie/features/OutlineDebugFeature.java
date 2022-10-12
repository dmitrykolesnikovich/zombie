package zombie.features;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import zombie.Context;

public class OutlineDebugFeature extends InputAdapter {

    private final Context context;

    public OutlineDebugFeature(Context context) {
        this.context = context;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.O) {
            context.isOutlineDebugEnabled = !context.isOutlineDebugEnabled;
            return true;
        }
        return false;
    }

}
