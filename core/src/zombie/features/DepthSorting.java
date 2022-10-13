package zombie.features;

import zombie.types.Body;
import zombie.types.Cell;

import java.util.ArrayList;
import java.util.List;

public class DepthSorting {

    private static final List<Body> SORTED_BODIES = new ArrayList<>();
    private static final int UNDER = 1;
    private static final int ABOVE = -1;

    public static List<Body> sortBodies(List<Body> bodies) {
        SORTED_BODIES.clear();
        SORTED_BODIES.addAll(bodies);
        SORTED_BODIES.sort((body1, body2) -> {

            Cell min = body1.getMinCell();
            Cell max = body1.getMaxCell();
            Cell current = body2.getMinCell();
            if (current.j > max.j) return ABOVE;
            if (current.j < min.j) return UNDER;
            if (current.i > min.i) return UNDER;
            if (current.i < min.i) return ABOVE;
            return 0;
        });

        return SORTED_BODIES;
    }

}
