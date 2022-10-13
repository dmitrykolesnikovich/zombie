package zombie.types;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TileAtlas implements Iterable<Tile> {

    public final Texture texture;
    public final int size;
    public final TextureRegion[] images;
    public final TextureRegion[] imagesFlippedHorizontallyOnly;
    public final TextureRegion[] imagesFlippedVerticallyOnly;
    public final TextureRegion[] imagesFlippedBoth;
    private final List<Tile> tiles = new ArrayList<>();

    public TileAtlas(Texture texture, int size) {
        this.texture = texture;
        this.size = size;
        this.images = new TextureRegion[size];
        this.imagesFlippedHorizontallyOnly = new TextureRegion[size];
        this.imagesFlippedVerticallyOnly = new TextureRegion[size];
        this.imagesFlippedBoth = new TextureRegion[size];
    }

    public void addTile(Tile tile) {
        tiles.add(tile);
    }

    public TextureRegion findImage(Tile tile) {
        int index = tile.index - 1;
        boolean flipHorizontal = tile.flipHorizontal;
        boolean flippedVertical = tile.flipVertical;
        boolean isFlippedNone = !flipHorizontal && !flippedVertical;
        boolean isFlippedHorizontallyOnly = flipHorizontal && !flippedVertical;
        boolean isFlippedVerticallyOnly = !flipHorizontal && flippedVertical;
        boolean isFlippedBoth = flipHorizontal && flippedVertical;

        // read from images
        if (isFlippedNone) return images[index];

        // read from flipped images
        TextureRegion image = null;
        if (isFlippedHorizontallyOnly) image = imagesFlippedHorizontallyOnly[index];
        if (isFlippedVerticallyOnly) image = imagesFlippedVerticallyOnly[index];
        if (isFlippedBoth) image = imagesFlippedBoth[index];

        // write to flipped images
        if (image == null) {
            image = new TextureRegion(images[index]);
            image.flip(isFlippedHorizontallyOnly, isFlippedVerticallyOnly);
        }
        if (isFlippedHorizontallyOnly) imagesFlippedHorizontallyOnly[index] = image;
        if (isFlippedVerticallyOnly) imagesFlippedVerticallyOnly[index] = image;
        if (isFlippedBoth) imagesFlippedBoth[index] = image;

        // nulls are not allowed by design
        if (image == null) throw new IllegalStateException("index: " + index);
        return image;
    }

    public void dispose() {
        texture.dispose();
    }

    @Override
    public Iterator<Tile> iterator() {
        return tiles.iterator();
    }

}
