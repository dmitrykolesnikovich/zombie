package zombie.features;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import zombie.Context;
import zombie.Utils;

public class HeroMovement {

    private final Context context;
    private final float speed;
    private boolean isDown = true;
    private boolean isRight = true;

    public HeroMovement(Context context, float speed) {
        this.context = context;
        this.speed = speed;
    }

    public void update() {
        // direction
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) {
            isDown = true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) {
            isDown = false;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
            isRight = true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
            isRight = false;
        }

        // movement
        Vector2 movement = new Vector2(0, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
            if (isDown) {
                movement.y += speed;
            } else {
                movement.x -= speed;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
            if (isDown) {
                movement.x += speed;
            } else {
                movement.y -= speed;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) {
            if (isRight) {
                movement.x += speed;
            } else {
                movement.y += speed;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) {
            if (isRight) {
                movement.y -= speed;
            } else {
                movement.x -= speed;
            }
        }

        // origin
        Utils.convert2dToIso(movement);
        context.heroOrigin.add(movement);
    }

}
