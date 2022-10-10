package zombie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class Utils {

    public static void unproject(Camera camera, Vector3 screenCoords, Matrix4 invProjectionView) {
        float x = screenCoords.x, y = Gdx.graphics.getHeight() - screenCoords.y;
        screenCoords.x = (2 * x) / camera.viewportWidth - 1;
        screenCoords.y = (2 * y) / camera.viewportHeight - 1;
        screenCoords.z = 2 * screenCoords.z - 1;
        screenCoords.prj(invProjectionView);
    }

}
