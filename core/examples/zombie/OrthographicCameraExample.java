package zombie;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class OrthographicCameraExample implements ApplicationListener {

    private OrthographicCamera camera;
    private SpriteBatch batch;

    private Texture mapSprite;
    private float rotationSpeed;
    private final Vector2 origin = new Vector2();

    @Override
    public void create() {
        rotationSpeed = 0.5f;
        mapSprite = new Texture(Gdx.files.internal("badlogic.jpg"));
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(w, h);
        batch = new SpriteBatch();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        handleInput();

        batch.begin();
        batch.draw(mapSprite, 0, -256, 256, 256);
        batch.end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.zoom += 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            camera.zoom -= 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            origin.x -= 3;
            // cam.translate(-3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            origin.x += 3;
            // cam.translate(3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            origin.y -= 3;
            // cam.translate(0, -3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            origin.y += 3;
            // cam.translate(0, 3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.rotate(-rotationSpeed, 0, 0, 1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            camera.rotate(rotationSpeed, 0, 0, 1);
        }

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        camera.position.set(origin.x + width / 2, origin.y - height / 2, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;

        camera.position.set(origin.x + (float) width / 2, origin.y - (float) height / 2, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause() {
        // no op
    }

    @Override
    public void resume() {
        // no op
    }

    @Override
    public void dispose() {
        mapSprite.dispose();
        batch.dispose();
    }

}
