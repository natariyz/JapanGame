package GameObjects;

import MapObjects.TileMap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Enemy {
    private String id;
    int hp, moveSpeed;
    private Sprite sprite;
    private int movingToPoint = 0;

    public Enemy copy(){
        Enemy enemy = new Enemy();
        enemy.setId(id);
        enemy.setHp(hp);
        enemy.setMoveSpeed(moveSpeed);
        enemy.setSprite(new Sprite(sprite));
        return enemy;
    }

    public void move(ArrayList<Vector2> path, double time){
        Vector2 from = path.get(movingToPoint), to = path.get(movingToPoint + 1);
        double distance = time * moveSpeed;

        double sin = TileMap.findPointDistance(from, to) / Math.abs(from.x - to.x);
        double cos = TileMap.findPointDistance(from, to) / Math.abs(from.y - to.y);

        if(distance > TileMap.findPointDistance(new Vector2(sprite.getX(), sprite.getY()), to)){
            movingToPoint += 1;
            move(path, distance - TileMap.findPointDistance(new Vector2(sprite.getX(), sprite.getY()), to) / moveSpeed);
            return;
        }

        sprite.setX(sprite.getX() + (to.x - from.x > 0 ? (float)(distance / sin) : -(float)(distance / sin)));
        sprite.setY(sprite.getY() + (to.y - from.y > 0 ? (float)(distance / cos) : -(float)(distance / cos)));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }
}
