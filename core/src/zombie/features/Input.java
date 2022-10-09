package zombie.features;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import zombie.Context;

public class Input extends InputAdapter {

    private final Context context;

    public Input(Context context) {
        this.context = context;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button != com.badlogic.gdx.Input.Buttons.LEFT || pointer > 0) return false;
        context.level.origin.set(screenX, screenY);
        return true;
    }

}
