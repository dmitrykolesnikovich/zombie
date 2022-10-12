package zombie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Utils {

    private static final float[] RECTANGLE_2D = new float[8];
    private static final float[] RECTANGLE_ISO = new float[8];
    private static final Vector2 VECTOR_ISO = new Vector2();

    /*renderer*/

    public static void drawImage(SpriteBatch renderer, TextureRegion image, float x, float y, float width, float height) {
        renderer.draw(image, x, y, width / 2, height / 2, width, height, 1, -1, 0);
    }

    public static void drawRectangle2d(ShapeRenderer renderer, float x, float y, float width, float height, Color color) {
        renderer.setColor(color);
        renderer.rect(x, y, width, height);
    }

    public static void drawRectangleIso(ShapeRenderer renderer, float x, float y, float width, float height, Color color) {
        renderer.setColor(color);

        // convert to iso
        VECTOR_ISO.set(x, y);
        convert2dToIso(VECTOR_ISO);
        x = VECTOR_ISO.x;
        y = VECTOR_ISO.y;

        initializeRectangle2d(RECTANGLE_2D, x, y, width, height);
        convert2dToIso(RECTANGLE_2D, RECTANGLE_ISO);
        renderer.polygon(RECTANGLE_ISO);
    }

    /*isometry*/

    public static void convert2dToIso(Vector2 vector) {
        float x = vector.x + vector.y;
        float y = (vector.x - vector.y) / 2;
        vector.set(x, y);
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

        // convert to iso
        VECTOR_ISO.set(x, y);
        VECTOR_ISO.sub(ox, oy);
        convert2dToIso(VECTOR_ISO);
        VECTOR_ISO.add(ox, oy);

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

    private static void initializeRectangle2d(float[] rectangle, float x, float y, float width, float height) {
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