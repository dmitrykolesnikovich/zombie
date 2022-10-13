package zombie.types;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;

public class TileAtlasBuilder {

    public static TileAtlas buildTileAtlas(XmlReader.Element tileMapElement, Level level) {
        int atlasSize = (int) (level.tilesPerAtlasRow * level.tilesPerAtlasColumn);

        // images
        TileAtlas tileAtlas = new TileAtlas(atlasSize);
        for (int row = 0; row < level.tilesPerAtlasRow; row++) {
            for (int column = 0; column < level.tilesPerAtlasColumn; column++) {
                int index = (int) (row * level.tilesPerAtlasColumn + column);
                float x = (2 * column + 1) * level.tileBorderSize + column * level.tileWidth;
                float y = (2 * row + 1) * level.tileBorderSize + row * level.tileHeight;
                TextureRegion image = new TextureRegion(level.texture, (int) x, (int) y, (int) level.tileWidth, (int) level.tileHeight);
                tileAtlas.images[index] = image;
            }
        }

        // tiles
        Array<XmlReader.Element> tileElements = tileMapElement.getChildByName("items").getChildByName("list").getChildrenByName("Tile");
        for (XmlReader.Element tileElement : tileElements) {
            Tile tile = TileBuilder.buildTile(tileElement);
            tile.image = tileAtlas.findImage(tile);
            tileAtlas.addTile(tile);
        }

        return tileAtlas;
    }

}
