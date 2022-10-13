package zombie.features;

import com.badlogic.gdx.math.Vector2;

// orthographic Y axis increases downwards
// isometry Y axis increases upwards
public class Isometry {

    private static final Vector2 VECTOR_ISO = new Vector2();
    private static final Vector2 VECTOR_2D = new Vector2();

    public static Vector2 convertOrthoToIso(Vector2 vector) {
        float x = vector.x + vector.y;
        float y = (vector.x - vector.y) / 2;
        vector.set(x, y);
        return vector;
    }

    public static Vector2 convertIsoToOrtho(Vector2 vector) {
        float x = (vector.x + 2 * vector.y) / 2;
        float y = (vector.x - 2 * vector.y) / 2;
        vector.set(x, y);
        return vector;
    }

    public static float[] convertOrthoToIso(float[] rectangleOrtho, float[] rectangleIso) {
        for (int index = 0; index < rectangleOrtho.length; index += 2) {
            Isometry.convertOrthoToIso(rectangleOrtho, rectangleIso, index);
        }
        return rectangleIso;
    }

    public static float[] convertOrthoToIso(float[] rectangleOrtho, float[] rectangleIso, int offset) {
        float ox = rectangleOrtho[0];
        float oy = rectangleOrtho[1];
        float x = rectangleOrtho[offset];
        float y = rectangleOrtho[offset + 1];

        // convert ortho to iso
        convertOrthoToIso(VECTOR_ISO.set(x, y).sub(ox, oy)).add(ox, oy);
        rectangleIso[offset] = VECTOR_ISO.x;
        rectangleIso[offset + 1] = VECTOR_ISO.y;
        return rectangleIso;
    }

    public static int[] findCellLocationOrNull(Vector2 point, float cellWidth, float cellHeight) {
        Isometry.convertIsoToOrtho(VECTOR_2D.set(point));
        if (VECTOR_2D.y < 0 || VECTOR_2D.x < 0) return null;

        int i = (int) (VECTOR_2D.y / cellHeight);
        int j = (int) (VECTOR_2D.x / cellWidth);
        return new int[]{i, j};
    }

}
