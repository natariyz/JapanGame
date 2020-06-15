package NoName;

import java.util.ArrayList;

public class TileSet {

    private String path;
    private ArrayList<Tile> tiles = new ArrayList<>();

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void addTile(Tile tile){
        tiles.add(tile);
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }
}
