package zombie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Utils {

    private static final Vector2 VECTOR_ISO = new Vector2();

    /*isometry*/

    // x is down, y is up
    public static Vector2 convert2dToIso(Vector2 vector) {
        float x = vector.x + vector.y;
        float y = (vector.x - vector.y) / 2;
        vector.set(x, y);
        return vector;
    }

    public static void convert2dToIso(float[] rectangle2d, float[] rectangleIso) {
        for (int index = 0; index < rectangle2d.length; index += 2) {
            Utils.convert2dToIso(rectangle2d, rectangleIso, index);
        }
    }

    public static void convert2dToIso(float[] rectangle2d, float[] rectangleIso, int offset) {
        float ox = rectangle2d[0];
        float oy = rectangle2d[1];
        float x = rectangle2d[offset];
        float y = rectangle2d[offset + 1];

        // convert 2d to iso
        convert2dToIso(VECTOR_ISO.set(x, y).sub(ox, oy)).add(ox, oy);
        rectangleIso[offset] = VECTOR_ISO.x;
        rectangleIso[offset + 1] = VECTOR_ISO.y;
    }

    public static Vector2 convertIsoTo2d(Vector2 pt) {
        Vector2 tempPt = new Vector2(0, 0);
        tempPt.x = (2 * pt.y + pt.x) / 2;
        tempPt.y = (2 * pt.y - pt.x) / 2;
        return tempPt;
    }

    /*rectangle*/

    public static void initializeRectangle2d(float[] rectangle, float x, float y, float width, float height) {
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

    public static void unproject(Camera camera, Vector3 screenCoords, Matrix4 initialMatrix) {
        float x = screenCoords.x, y = Gdx.graphics.getHeight() - screenCoords.y;
        screenCoords.x = (2 * x) / camera.viewportWidth - 1;
        screenCoords.y = (2 * y) / camera.viewportHeight - 1;
        screenCoords.z = 2 * screenCoords.z - 1;
        screenCoords.prj(initialMatrix);
    }

}
