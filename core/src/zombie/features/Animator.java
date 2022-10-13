package zombie.features;

import zombie.types.Body;
import zombie.types.Face;
import zombie.types.Movement;

public class Animator {

    public static void syncAnimationWithState(Body body) {
        Movement movement = body.transform.movement;
        if (movement != null) {
            boolean isRight = movement.isRight();
            boolean isDown = movement.isDown();
            animate(body, isRight, isDown, true);
            body.face = isRight ? Face.LOOKING_RIGHT : Face.LOOKING_LEFT;
        } else {
            animate(body, body.face.isLookingRight(), true, false);
        }
    }

    /*internals*/

    private static void animate(Body body, boolean isRight, boolean isDown, boolean isMoving) {
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
                // no op
                break;
            }
        }
    }

}
