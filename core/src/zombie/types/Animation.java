package zombie.types;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Animation {

    public com.badlogic.gdx.graphics.g2d.Animation<TextureRegion> delegate;
    String name;
    String animationTexture;
    Texture texture;

    public static Animation createAnimation(String name) throws FileNotFoundException {
        String dirPath = "animations/" + name;
        String filePath = dirPath + "/" + name + ".xml";
        XmlReader xmlReader = new XmlReader();
        XmlReader.Element animationElement = xmlReader.parse(new FileReader(filePath));

        // attributes
        Animation animation = new Animation();
        animation.name = animationElement.getAttribute("name");
        animation.animationTexture = animationElement.getAttribute("animationTexture");
        animation.texture = new Texture(dirPath + "/" + animation.animationTexture);

        // children
        Array<XmlReader.Element> frameElements = animationElement.getChildByName("frames").getChildByName("list").getChildrenByName("Frame");
        List<TextureRegion> frames = new ArrayList<>();
        for (XmlReader.Element frameElement : frameElements) {
            int x = Integer.parseInt(frameElement.getAttribute("x"));
            int y = Integer.parseInt(frameElement.getAttribute("y"));
            int width = Integer.parseInt(frameElement.getAttribute("width"));
            int height = Integer.parseInt(frameElement.getAttribute("height"));
            int ticks = Integer.parseInt(frameElement.getAttribute("ticks"));
            TextureRegion frame = new TextureRegion(animation.texture, x, y, width, height);
            for (int i = 0; i < ticks; i++) {
                frames.add(frame);
            }
        }

        float frameDuration = 1f / frameElements.size;
        animation.delegate = new com.badlogic.gdx.graphics.g2d.Animation<>(frameDuration, frames.toArray(new TextureRegion[0]));
        return animation;
    }

}
