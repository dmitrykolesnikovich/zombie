package zombie.types;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Animation {

    public com.badlogic.gdx.graphics.g2d.Animation<TextureRegion> delegate;
    public String name;
    public String texturePath;
    public Texture texture;
    public float totalTime;

    private static final Map<String, Animation> cacheFlippedNone = new HashMap<>();
    private static final Map<String, Animation> cacheFlippedHorizontally = new HashMap<>();

    public static Animation createAnimation(String name, boolean flipped) throws FileNotFoundException {
        Map<String, Animation> cache = flipped ? cacheFlippedHorizontally : cacheFlippedNone;
        Animation existingAnimation = cache.get(name);
        if (existingAnimation != null) return existingAnimation;

        String dirPath = "animations/" + name;
        String filePath = dirPath + "/" + name + ".xml";
        XmlReader xmlReader = new XmlReader();
        XmlReader.Element animationElement = xmlReader.parse(new FileReader(filePath));

        // attributes
        Animation animation = new Animation();
        animation.name = animationElement.getAttribute("name");
        animation.texturePath = animationElement.getAttribute("animationTexture");
        animation.texture = new Texture(dirPath + "/" + animation.texturePath);

        // children
        Array<XmlReader.Element> frameElements = animationElement.getChildByName("frames").getChildByName("list").getChildrenByName("Frame");
        List<TextureRegion> images = new ArrayList<>();
        for (XmlReader.Element frameElement : frameElements) {
            int x = Integer.parseInt(frameElement.getAttribute("x"));
            int y = Integer.parseInt(frameElement.getAttribute("y"));
            int width = Integer.parseInt(frameElement.getAttribute("width"));
            int height = Integer.parseInt(frameElement.getAttribute("height"));
            int ticks = Integer.parseInt(frameElement.getAttribute("ticks"));
            TextureRegion image = new TextureRegion(animation.texture, x, y, width, height);
            if (flipped) image.flip(true, false);
            for (int i = 0; i < ticks; i++) {
                images.add(image);
            }
        }

        float frameDuration = 1f / frameElements.size;
        animation.delegate = new com.badlogic.gdx.graphics.g2d.Animation<>(frameDuration, images.toArray(new TextureRegion[0]));
        cache.put(name, animation);
        return animation;
    }

}
