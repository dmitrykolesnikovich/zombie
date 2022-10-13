package zombie.features;

import zombie.types.*;

public class Animator {

    public static void update(Body body) {
        Face face = body.face;
        Movement movement = body.transform.movement;

        boolean isMoving = movement != null;
        boolean isMovingDown = isMoving && movement.isDown();
        switch (body.name) {
            case "hero": {
                if (isMoving) {
                    if (isMovingDown) {
                        body.setAnimation("anim_woodcutter_walk_down", face.isLookingRight());
                    } else {
                        body.setAnimation("anim_woodcutter_walk_up", face.isLookingRight());
                    }
                } else {
                    body.setAnimation("anim_woodcutter_stand", face.isLookingRight());
                }
                break;
            }
            case "sklep": {
                body.setAnimation("b_sklep_0", false);
                break;
            }
            case "tower": {
                body.setAnimation("b_tower_0", false);
                break;
            }
            case "tropic_palm": {
                body.setAnimation("d_tropic_palm_01_0", false);
                break;
            }
            case "oak": {
                body.setAnimation("t_oak3_0", false);
                break;
            }
            case "palm": {
                body.setAnimation("t_palm_01_0", false);
                break;
            }
            default: {
                throw new IllegalStateException("body: " + body);
            }
        }
    }

}
