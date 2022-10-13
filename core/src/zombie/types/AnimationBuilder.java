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

public class AnimationBuilder {

    private static final Map<String, Animation> cachedAnimations = new HashMap<>();
    private static final Map<String, Animation> cachedAnimationsFlippedHorizontally = new HashMap<>();

    public static Animation buildAnimation(String name, boolean flippedHorizontally) {
        Map<String, Animation> cache = flippedHorizontally ? cachedAnimationsFlippedHorizontally : cachedAnimations;
        Animation existingAnimation = cache.get(name);
        if (existingAnimation != null) return existingAnimation;

        String dirPath = "animations/" + name;
        String filePath = dirPath + "/" + name + ".xml";
        XmlReader parser = new XmlReader();
        XmlReader.Element animationElement;
        try {
            animationElement = parser.parse(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(name);
        }

        // animation
        Animation animation = new Animation();
        animation.name = name; // animationElement.getAttribute("name");
        animation.dirPath = dirPath;
        animation.filePath = filePath;

        // attributes
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
            if (flippedHorizontally) image.flip(true, false);
            for (int i = 0; i < ticks; i++) {
                images.add(image);
            }
        }

        // delegate
        float frameDuration = 1f / frameElements.size;
        TextureRegion[] keyFrames = images.toArray(new TextureRegion[0]);
        animation.delegate = new com.badlogic.gdx.graphics.g2d.Animation<>(frameDuration, keyFrames);

        // cache
        cache.put(name, animation);
        return animation;
    }

}
