package NoName;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TileMap {

    private String mapData;
    private TileSet tileSet = new TileSet();

    private int width, height;
    private int tileWidth, tileHeight;
    private long[][] mapMatrix;
    private Cell[][] cells;

    public void setMap_matrix(){
        mapMatrix = new long[width][height];
        cells = new Cell[width][height];

        mapData = mapData.replaceAll("\n", "");
        String [] mapDataArray = mapData.split(",");

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                mapMatrix[x][y] = Long.parseLong(mapDataArray[y + x * height]);
            }
        }

        long horizontally = 0x80000000L;
        long vertically = 0x40000000L;
        long diagonally = 0x20000000L;

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                long clearId = mapMatrix[x][y] &= ~(horizontally | vertically | diagonally);
                cells[x][y] = new Cell();
                cells[x][y].setTexture(new Texture(tileSet.getTiles().get((int)clearId - 1).getTexturePath()));
                if((mapMatrix[x][y] & horizontally) == horizontally) cells[x][y].setHorizontally(true);
                if((mapMatrix[x][y] & vertically) == vertically) cells[x][y].setVertically(true);
                if((mapMatrix[x][y] & diagonally) == diagonally) cells[x][y].setDiagonally(true);
            }
        }
    }

    public void draw(SpriteBatch batch){
        for (int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
              batch.draw(cells[x][y].getTexture(), x * tileWidth, y * tileHeight);
            }
        }
    }

    public void clear(){
        for (int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                cells[x][y].getTexture().dispose();
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
