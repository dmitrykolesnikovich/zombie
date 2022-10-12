package zombie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

// IMPORTANT for isometry Y increases upwards
public class Utils {

    private static final Vector2 VECTOR_ISO = new Vector2();
    private static final Vector3 VECTOR_UNPROJECT = new Vector3();

    /*isometry*/

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
            Utils.convertOrthoToIso(rectangleOrtho, rectangleIso, index);
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

    /*rectangle*/

    public static void initializeRectangleOrtho(float[] rectangle, float x, float y, float width, float height) {
        float x1 = x;
        float y1 = y;
        float x2 = x + width;
        float y2 = y;
        float x3 = x + width;
        float y3 = y + height;
        float x4 = x;
        float y4 = y + height;
        rectangle[0] = x1;
        rectangle[1] = y1;
        rectangle[2] = x2;
        rectangle[3] = y2;
        rectangle[4] = x3;
        rectangle[5] = y3;
        rectangle[6] = x4;
        rectangle[7] = y4;
    }

    /*camera*/

    // https://github.com/libgdx/libgdx/blob/master/gdx/src/com/badlogic/gdx/graphics/Camera.java#L194
    public static void unproject(Camera camera, Vector2 screenCoords, Matrix4 invProjectionView) {
        VECTOR_UNPROJECT.x = screenCoords.x;
        VECTOR_UNPROJECT.y = screenCoords.y;
        VECTOR_UNPROJECT.z = 0;

        float x = screenCoords.x, y = Gdx.graphics.getHeight() - screenCoords.y;
        VECTOR_UNPROJECT.x = (2 * x) / camera.viewportWidth - 1;
        VECTOR_UNPROJECT.y = (2 * y) / camera.viewportHeight - 1;
        VECTOR_UNPROJECT.z = -1;
        VECTOR_UNPROJECT.prj(invProjectionView);

        screenCoords.x = VECTOR_UNPROJECT.x;
        screenCoords.y = VECTOR_UNPROJECT.y;
    }

}
