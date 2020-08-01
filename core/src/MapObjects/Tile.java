package MapObjects;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Tile {
    private String texturePath;
    private int width, height, id;
    private ArrayList<Vector2> points;
    private ArrayList<Vector2> roadPolygon;

    public ArrayList<Vector2> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Vector2> points) {
        this.points = points;
    }

    public String getTexturePath() {
        return texturePath;
    }

    public void setTexturePath(String texturePath) {
        this.texturePath = texturePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public ArrayList<Vector2> getRoadPolygon() {
        return roadPolygon;
    }

    public void setRoadPolygon(ArrayList<Vector2> roadPolygon) {
        this.roadPolygon = roadPolygon;
    }
}
