package zombie.features;

import zombie.types.Body;
import zombie.types.Face;
import zombie.types.Movement;

// currently switching animations works without any performance optimizations
public class Animator {

    public static boolean update(Body body) {
        Face face = body.face;
        Movement movement = body.transform.movement;
        boolean isMoving = movement != null;
        boolean isMovingDown = isMoving && movement.isDown();

        switch (body.name) {
            case "hero":
                String name = isMoving ? (isMovingDown ? "anim_woodcutter_walk_down" : "anim_woodcutter_walk_up") : "anim_woodcutter_stand";
                return body.setAnimation(name, face.isLookingRight());
            case "sklep":
                return body.setAnimation("b_sklep_0");
            case "tower":
                return body.setAnimation("b_tower_0");
            case "tropic_palm":
                return body.setAnimation("d_tropic_palm_01_0");
            case "oak":
                return body.setAnimation("t_oak3_0");
            case "palm":
                return body.setAnimation("t_palm_01_0");
        }
        throw new IllegalStateException("body: " + body);
    }

}
