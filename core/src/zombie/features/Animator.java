package zombie.features;

import zombie.types.Body;
import zombie.types.Face;
import zombie.types.Movement;

public class Animator {

    public static void updateAnimation(Body body) {
        Movement movement = body.transform.movement;
        if (movement != null) {
            boolean isDown = movement.isDown();
            boolean isRight = movement.isRight();
            updateAnimation(body, isDown, isRight, true);
            body.face = isRight ? Face.LOOKING_RIGHT : Face.LOOKING_LEFT;
        } else {
            updateAnimation(body, true, body.face.isLookingRight(), false);
        }
    }

    /*internals*/

    private static void updateAnimation(Body body, boolean isDown, boolean isRight, boolean isMoving) {
        switch (body.name) {
            case "hero": {
                if (isMoving) {
                    if (isDown && !isRight) body.animate("anim_woodcutter_walk_down", false);
                    if (isDown && isRight) body.animate("anim_woodcutter_walk_down", true);
                    if (!isDown && !isRight) body.animate("anim_woodcutter_walk_up", false);
                    if (!isDown && isRight) body.animate("anim_woodcutter_walk_up", true);
                } else {
                    body.animate("anim_woodcutter_stand", isRight);
                }
                break;
            }
            default: {
                break;
            }
        }
    }

}
