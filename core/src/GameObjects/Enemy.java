package GameObjects;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Enemy {
    private String id;
    int hp, moveSpeed;
    private Sprite sprite;

    public Enemy copy(){
        Enemy enemy = new Enemy();
        enemy.setId(id);
        enemy.setHp(hp);
        enemy.setMoveSpeed(moveSpeed);
        enemy.setSprite(new Sprite(sprite));
        return enemy;
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
