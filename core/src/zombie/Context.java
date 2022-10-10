package zombie;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import zombie.features.HeroMovement;
import zombie.features.LevelInput;
import zombie.types.Animation;
import zombie.types.Level;
import zombie.types.Tile;

import java.io.FileNotFoundException;

public class Context extends ApplicationAdapter {

    private static final Color backgroundColor = new Color(0x7AAAC9FF);
    public Level level;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    public final Vector2 cameraOrigin = new Vector2();
    public final OrthographicCamera camera = new OrthographicCamera();
    public Animation heroAnimation;
    private float totalTime;
    public final Vector2 heroOrigin = new Vector2();
    public final Vector2 heroOriginTarget = new Vector2();
    private final HeroMovement heroMovement = new HeroMovement(this, 2.5f);

    @Override
    public void create() {
        Gdx.input.setInputProcessor(new InputMultiplexer(new LevelInput(this)));
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        try {
            level = Level.createLevel("main_island");
            heroAnimation = Animation.createAnimation("anim_woodcutter_stand");
            cameraOrigin.set(level.offsetPoint.x, -level.offsetPoint.y);
            heroOrigin.set(7 * 100, 10 * 100);
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
        Utils.convertToScreenPoint(heroOrigin, heroOriginTarget, this);
        batch.draw(heroFrame, heroOriginTarget.x, heroOriginTarget.y);
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
        shapeRenderer.rect(heroOriginTarget.x, heroOriginTarget.y, heroFrame.getRegionWidth(), heroFrame.getRegionHeight());
        shapeRenderer.end();

        heroMovement.update();
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
        camera.position.set(cameraOrigin.x + width / 2, cameraOrigin.y - height / 2, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
    }

}
