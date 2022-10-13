package zombie.types;

public class BodyBuilder {

    public static Body buildBody(int id, String name, Level level) {
        Body body = new Body(level);
        body.id = id;
        body.name = name;
        switch (name) {
            case "hero": {
                body.rows = 1;
                body.columns = 1;
                body.pivot.set(4, 20);
                break;
            }
            case "sklep": {
                body.rows = 5;
                body.columns = 8;
                body.pivot.set(-72, 44);
                body.animate("b_sklep_0", false);
                break;
            }
            case "tower": {
                body.rows = 4;
                body.columns = 4;
                body.pivot.set(-45, 56.5f);
                body.animate("b_tower_0", false);
                break;
            }
            case "tropic_palm": {
                body.rows = 2;
                body.columns = 2;
                body.pivot.set(-14, 209);
                body.animate("d_tropic_palm_01_0", false);
                break;
            }
            case "oak": {
                body.rows = 1;
                body.columns = 1;
                body.pivot.set(-4, 125);
                body.animate("t_oak3_0", false);
                break;
            }
            case "palm": {
                body.rows = 1;
                body.columns = 1;
                body.pivot.set(25, 126);
                body.animate("t_palm_01_0", false);
                break;
            }
            default: {
                throw new IllegalStateException("name: " + name);
            }
        }
        return body;
    }

}
