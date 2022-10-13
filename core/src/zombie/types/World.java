package zombie.types;

import static zombie.types.Face.LOOKING_RIGHT;

// todo replace `World.initializeBody` with `BodyBuilder.buildBody` in production
// todo instead of `World.initializeLevel` use `LevelBuilder.buildLevel` in production
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
        }
        throw new IllegalStateException("body: " + body);
    }

    public static void initializeLevel(Level level) {
        switch (level.name) {
            case "main_island": {
                // camera
                level.pivot.y += 500;
                level.updateCamera();

                // hero
                level.hero.transform.placeTo(50, 28);
                level.hero.speed = 4;

                // buildings
                level.addBody(1, "sklep").transform.placeTo(50, 30);
                level.addBody(2, "tower").transform.placeTo(50, 40);

                // trees
                level.addBody(3, "tropic_palm").transform.placeTo(50, 50);
                level.addBody(4, "oak").transform.placeTo(50, 60);
                level.addBody(5, "palm").transform.placeTo(50, 70);
                break;
            }
        }
        throw new IllegalStateException("level: " + level);
    }

}
