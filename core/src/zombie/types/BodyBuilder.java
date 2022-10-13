package zombie.types;

public class BodyBuilder {

    public static Body buildBody(int id, String name, Level level) {
        float cellSide = level.cellSide;

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
                body.pivot.set(-88 + cellSide, 44);
                body.animate("b_sklep_0", false);
                break;
            }
            case "tower": {
                body.rows = 4;
                body.columns = 4;
                body.pivot.set(-61 + cellSide, 56.5f);
                body.animate("b_tower_0", false);
                break;
            }
            case "tropic_palm": {
                body.rows = 2;
                body.columns = 2;
                body.pivot.set(-28 + cellSide, 208);
                body.animate("d_tropic_palm_01_0", false);
                break;
            }
            case "oak": {
                body.rows = 1;
                body.columns = 1;
                body.pivot.set(-20 + cellSide, 125);
                body.animate("t_oak3_0", false);
                break;
            }
            default: {
                throw new IllegalStateException("name: " + name);
            }
        }
        return body;
    }

}
