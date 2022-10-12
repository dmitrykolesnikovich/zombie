package zombie.features;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import zombie.Utils;
import zombie.types.Hero;

public class HeroInput {

    private final Hero hero;
    private final float speed;
    private boolean isDown = true;
    private boolean isRight = true;

    public HeroInput(Hero hero, float speed) {
        this.hero = hero;
        this.speed = speed;
    }

    public void update() {
        boolean existsMovement = false;

        // direction
        if (Gdx.input.isKeyJustPressed(Input.Keys.DPAD_DOWN)) {
            isDown = true;
            existsMovement = true;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DPAD_UP)) {
            isDown = false;
            existsMovement = true;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DPAD_RIGHT)) {
            isRight = true;
            existsMovement = true;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DPAD_LEFT)) {
            isRight = false;
            existsMovement = true;
        }

        // movement
        Vector2 movement = new Vector2(0, 0);
        if (Gdx.input.isKeyJustPressed(Input.Keys.DPAD_LEFT)) {
            if (isDown) {
                movement.y -= speed;
            } else {
                movement.x -= speed;
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DPAD_RIGHT)) {
            if (isDown) {
                movement.x += speed;
            } else {
                movement.y += speed;
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DPAD_DOWN)) {
            if (isRight) {
                movement.x += speed;
            } else {
                movement.y -= speed;
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DPAD_UP)) {
            if (isRight) {
                movement.y += speed;
            } else {
                movement.x -= speed;
            }
        }

        // convert 2d to iso
        Utils.convert2dToIso(movement);

        // action
        hero.origin.add(movement);

        // animation
        if (existsMovement) {
            if (isDown && !isRight) hero.animate("anim_woodcutter_walk_down", false);
            if (isDown && isRight) hero.animate("anim_woodcutter_walk_down", true);
            if (!isDown && !isRight) hero.animate("anim_woodcutter_walk_up", false);
            if (!isDown && isRight) hero.animate("anim_woodcutter_walk_up", true);
        } else {
            if (!isRight) hero.animate("anim_woodcutter_stand", false);
            if (isRight) hero.animate("anim_woodcutter_stand", true);
        }
    }

}
