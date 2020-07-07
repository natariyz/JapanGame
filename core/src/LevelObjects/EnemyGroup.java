package LevelObjects;

import GameObjects.Enemy;

public class EnemyGroup {
    private Enemy enemy;
    private int count;

    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
