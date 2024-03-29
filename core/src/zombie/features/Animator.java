package zombie.features;

import zombie.types.Body;

public class Animator {

    public static boolean update(Body body) {
        Transform transform = body.transform;
        boolean isMoving = transform.isMoving();
        boolean isMovingDown = transform.isMovingDown();

        switch (body.name) {
            case "hero":
                String name = isMoving ? (isMovingDown ? "anim_woodcutter_walk_down" : "anim_woodcutter_walk_up") : "anim_woodcutter_stand";
                return body.setAnimation(name, body.isLookingRight());
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
