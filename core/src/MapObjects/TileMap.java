package MapObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TileMap {
    private TileSet tileSet = new TileSet();

    private Cell[][] cells;

    private int width, height;
    private int tileWidth, tileHeight;

    public static final long flippedHorizontallyFlag = 0x80000000L;
    public static final long flippedVerticallyFlag = 0x40000000L;
    public static final long flippedDiagonallyFlag = 0x20000000L;

    public void initializeCells(String mapData){
        cells = new Cell[width][height];

        boolean isFlippedHorizontally, isFlippedVertically, isFlippedDiagonally;

        mapData = mapData.replaceAll("\n", "");
        String [] mapDataArray = mapData.split(",");

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                long id = Long.parseLong(mapDataArray[y * width + x]);
                long cleanId = id & ~(flippedHorizontallyFlag | flippedVerticallyFlag | flippedDiagonallyFlag);

                cells[x][y] = new Cell();

                Texture texture = new Texture(tileSet.getTiles().get((int)cleanId - 1).getTexturePath());
                Sprite sprite = new Sprite(texture);

                sprite.setBounds(x * tileWidth,(height - y - 1) * tileHeight, tileWidth, tileHeight); //TODO рисовать тайлы в полном размере
                sprite.setOrigin(tileWidth / 2, tileHeight / 2);

                isFlippedHorizontally = (id & flippedHorizontallyFlag) == flippedHorizontallyFlag;
                isFlippedVertically = (id & flippedVerticallyFlag) == flippedVerticallyFlag;
                isFlippedDiagonally = (id & flippedDiagonallyFlag) == flippedDiagonallyFlag;

                if(isFlippedDiagonally) sprite.rotate(90);
                sprite.flip(isFlippedHorizontally, isFlippedVertically);

                cells[x][y].setSprite(sprite);
            }
        }
    }

    public void draw(SpriteBatch batch){
        for (int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                cells[x][y].getSprite().draw(batch);
            }
        }
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public void setTileWidth(int tileWidth) {
        this.tileWidth = tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public void setTileHeight(int tileHeight) {
        this.tileHeight = tileHeight;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public TileSet getTileSet() {
        return tileSet;
    }
}
