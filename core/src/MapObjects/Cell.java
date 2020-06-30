package MapObjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Cell {
    private int x, y;
    private Sprite sprite;
    private ArrayList<Vector2> points;

    public ArrayList<Vector2> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Vector2> points) {
        this.points = points;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
