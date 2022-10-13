package zombie.types;

import static zombie.types.Face.LOOKING_RIGHT;

// todo replace with BodyBuilder in production
public class World {

    public static void initializeBody(Body body) {
        switch (body.name) {
            case "hero": {
                body.rows = 1;
                body.columns = 1;
                body.pivot.set(4, 20);
                body.face = LOOKING_RIGHT;
                break;
            }
            case "sklep": {
                body.rows = 5;
                body.columns = 8;
                body.pivot.set(-72, 44);
                break;
            }
            case "tower": {
                body.rows = 4;
                body.columns = 4;
                body.pivot.set(-45, 56.5f);
                break;
            }
            case "tropic_palm": {
                body.rows = 2;
                body.columns = 2;
                body.pivot.set(-14, 209);
                break;
            }
            case "oak": {
                body.rows = 1;
                body.columns = 1;
                body.pivot.set(-4, 125);
                break;
            }
            case "palm": {
                body.rows = 1;
                body.columns = 1;
                body.pivot.set(25, 126);
                break;
            }
            default: {
                throw new IllegalStateException("body: " + body);
            }
        }
    }

}
