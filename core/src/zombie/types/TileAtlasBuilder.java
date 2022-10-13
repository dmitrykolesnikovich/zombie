package zombie.types;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;

public class TileAtlasBuilder {

    public static TileAtlas buildTileAtlas(XmlReader.Element levelElement, Level level) {
        int size = (int) (level.tilesPerAtlasRow * level.tilesPerAtlasColumn);

        // images
        Texture texture = new Texture(level.dirPath + "/" + level.image);
        TileAtlas tileAtlas = new TileAtlas(texture, size);
        for (int row = 0; row < level.tilesPerAtlasRow; row++) {
            for (int column = 0; column < level.tilesPerAtlasColumn; column++) {
                int index = (int) (row * level.tilesPerAtlasColumn + column);
                float x = (2 * column + 1) * level.tileBorderSize + column * level.tileWidth;
                float y = (2 * row + 1) * level.tileBorderSize + row * level.tileHeight;
                TextureRegion image = new TextureRegion(texture, (int) x, (int) y, (int) level.tileWidth, (int) level.tileHeight);
                tileAtlas.images[index] = image;
            }
        }

        // tiles
        Array<XmlReader.Element> tileElements = levelElement.getChildByName("items").getChildByName("list").getChildrenByName("Tile");
        for (XmlReader.Element tileElement : tileElements) {
            Tile tile = TileBuilder.buildTile(tileElement);
            tile.image = tileAtlas.findImage(tile);
            tileAtlas.addTile(tile);
        }

        return tileAtlas;
    }

}
