package NoName;

public class Tile {
    private String tilePath;
    private int width, height, id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTilePath() {
        return tilePath;
    }

    public void setTilePath(String tilePath) {
        this.tilePath = tilePath;
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
}
