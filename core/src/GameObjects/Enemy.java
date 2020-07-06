package GameObjects;

import MapObjects.TileMap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Enemy {
    private String id;
    private int hp, moveSpeed;
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

    public boolean move(ArrayList<Vector2> path, float time){
        if(movingToPoint == path.size() - 1) return true; //если последняя точка достигнута возвращаем тру и объект удалится
        Vector2 from = path.get(movingToPoint), to = path.get(movingToPoint + 1);
        float distance = time * moveSpeed;

        double sin = from.dst(to) / Math.abs(from.x - to.x);//отношение гипотенузы к противолежащему катету, прямоугольника гипотенуза которого отрезок от первой точки ко второй
        double cos = from.dst(to) / Math.abs(from.y - to.y);//тоже самое только прилежащий катет

        double distanceToPoint = TileMap.findPointDistance(sprite.getX(), sprite.getY(), to.x, to.y);//TODO asd

        if(distance > distanceToPoint){//если расстояние, которое может пройти враг больше, чем расстояние до точки, то перемещаем его на точку и вызываем move для оставшегося времени

            float timeLeft = (float)(distance - distanceToPoint) / moveSpeed;

            sprite.setPosition(to.x, to.y);

            movingToPoint += 1;

            move(path, timeLeft);

            return false;
        }

        sprite.translate(
                to.x - from.x > 0 ? (float)(distance / sin) : -(float)(distance / sin), // если точка к которой идет враг слева, то перемещаем с отрицательным значением и наоборот
                to.y - from.y > 0 ? (float)(distance / cos) : -(float)(distance / cos));

        return false;
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
