package zombie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Utils {

    private static final Vector3 VECTOR_UNPROJECT = new Vector3();

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

    public static void unproject(Camera camera, Vector2 screenCoords) {
        unproject(camera, screenCoords, camera.invProjectionView);
    }

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
