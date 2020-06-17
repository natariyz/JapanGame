package NoName;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TileMap {

    private String mapData;
    private TileSet tileSet = new TileSet();

    private int width, height;
    private int tileWidth, tileHeight;
    private Cell[][] cells;

    public void setMap_matrix(){
        cells = new Cell[width][height];

        long horizontally = 0x80000000L;
        long vertically = 0x40000000L;
        long diagonally = 0x20000000L;
        boolean isHorizontally = false, isVertically = false, isDiagonally = false;

        mapData = mapData.replaceAll("\n", "");
        String [] mapDataArray = mapData.split(",");

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                long id = Long.parseLong(mapDataArray[y * width + x]);
                long clearId = id & ~(horizontally | vertically | diagonally);

                cells[x][y] = new Cell();

                Texture texture = new Texture(tileSet.getTiles().get((int)clearId - 1).getTexturePath());
                Sprite sprite = new Sprite(texture);

                sprite.setBounds(x * 50, y * 50, 50, 50);
                sprite.setOrigin(25, 25);

                if((id & horizontally) == horizontally) isHorizontally = true;
                if((id & vertically) == vertically) isVertically = true;
                if((id & diagonally) == diagonally) isDiagonally = true;

                if(isDiagonally) sprite.rotate(90);
                sprite.flip(isHorizontally, isVertically);

                cells[x][y].setSprite(sprite);

                isHorizontally = isVertically = isDiagonally = false;
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

    public void setMapData(String mapData) {
        this.mapData = mapData;
    }

    public TileSet getTileSet() {
        return tileSet;
    }
}
