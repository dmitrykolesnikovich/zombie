package zombie;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import zombie.features.Input;
import zombie.types.Animation;
import zombie.types.Level;
import zombie.types.Tile;

import java.io.FileNotFoundException;

public class Context extends ApplicationAdapter {

    private static final Color backgroundColor = new Color(0x7AAAC9FF);
    public Level level;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    public final Vector2 origin = new Vector2();
    public final OrthographicCamera camera = new OrthographicCamera();
    public Animation heroAnimation;
    private float totalTime;

    @Override
    public void create() {
        Gdx.input.setInputProcessor(new Input(this));
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        try {
            level = Level.createLevel("main_island");
            heroAnimation = Animation.createAnimation("anim_woodcutter_stand");
            origin.set(level.offsetPoint.x, -level.offsetPoint.y);
            updateCamera();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render() {
        totalTime += Gdx.graphics.getDeltaTime();
        ScreenUtils.clear(backgroundColor);
        batch.begin();

        // tiles
        for (Tile tile : level.tiles) {
            float x = tile.x;
            float y = Gdx.graphics.getHeight() - tile.y;
            float width = tile.width;
            float height = tile.height;
            batch.draw(tile.texture, x, y, width, height);
        }

        // hero
        TextureRegion heroFrame = heroAnimation.delegate.getKeyFrame(totalTime, true);
        int heroX = 1080;
        int heroY = Gdx.graphics.getHeight() + 436;
        batch.draw(heroFrame, heroX, heroY);
        batch.end();

        // outline
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(new Color(0.25f, 0.25f, 1, 1));
        for (Tile tile : level.tiles) {
            float x = tile.x;
            float y = Gdx.graphics.getHeight() - tile.y;
            float width = tile.width;
            float height = tile.height;
            shapeRenderer.rect(x, y, width, height);
        }
        shapeRenderer.setColor(new Color(1, 0.25f, 0.25f, 1));
        shapeRenderer.rect(heroX, heroY, heroFrame.getRegionWidth(), heroFrame.getRegionHeight());
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        level.dispose();
    }

    @Override
    public void resize(int width, int height) {
        updateCamera();
    }

    /*convenience*/

    public void updateCamera() {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.position.set(origin.x + width / 2, origin.y - height / 2, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
    }

}
