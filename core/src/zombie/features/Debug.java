package zombie.features;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import zombie.Context;

public class Debug extends InputAdapter {

    public static final Color PASSABLE_CELL_OUTLINE_COLOR = new Color(0.8f, 0.8f, 0.2f, 1);

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
