package zombie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Utils {

    // replaces `context.camera.unproject(initialDraggingPoint)`
    public static void unproject(Camera camera, Vector3 screenCoords, Matrix4 invProjectionView) {
        float x = screenCoords.x, y = Gdx.graphics.getHeight() - screenCoords.y;
        screenCoords.x = (2 * x) / camera.viewportWidth - 1;
        screenCoords.y = (2 * y) / camera.viewportHeight - 1;
        screenCoords.z = 2 * screenCoords.z - 1;
        screenCoords.prj(invProjectionView);
    }

    public static Vector2 convertToScreenPoint(Vector2 source, Vector2 target, Context context) {
        target.x = context.level.offsetPoint.x + source.x;
        target.y = -context.level.offsetPoint.y + Gdx.graphics.getHeight() - source.y;
        return target;
    }

    public static Vector2 convertIsoTo2d(Vector2 pt) {
        Vector2 tempPt = new Vector2(0, 0);
        tempPt.x = (2 * pt.y + pt.x) / 2;
        tempPt.y = (2 * pt.y - pt.x) / 2;
        return tempPt;
    }

    public static void convert2dToIso(Vector2 point) {
        float x = point.x;
        float y = point.y;
        point.x = x - y;
        point.y = (x + y) / 2;
    }

}
