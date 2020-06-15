package NoName;

public class TileMap {

    private String mapData;
    private TileSet tileSet = new TileSet();

    private int width, height;
    private long[][] map_matrix;

    public void setMap_matrix(){

        map_matrix = new long[width][height];
        mapData = mapData.replaceAll("\n", "");
        String [] mapDataArray = mapData.split(",");

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                map_matrix[x][y] = Long.parseLong(mapDataArray[y + x * height]);
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setMapData(String mapData) {
        this.mapData = mapData;
    }

    public String getMapData() {
        return mapData;
    }

    public TileSet getTileSet() {
        return tileSet;
    }

    public void setTileSet(TileSet tileSet) {
        this.tileSet = tileSet;
    }
}
