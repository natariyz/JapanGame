package GameObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class HpBar {
    private Enemy enemy;
    private Sprite currentHpBar;
    private Sprite lostHpBar;

    public HpBar(Enemy enemy) {
        this.enemy = enemy;
        currentHpBar = new Sprite(new Texture("images/bars/health_bar.png"));
        lostHpBar = new Sprite(new Texture("images/bars/empty_bar.png"));
    }

    public void draw(SpriteBatch batch){
        Sprite enemySprite = enemy.getSprite();

        Rectangle hpBounds = new Rectangle(enemySprite.getX(), enemySprite.getY() + enemySprite.getHeight(),
                enemySprite.getWidth(), enemySprite.getHeight() / 5);

        batch.draw(lostHpBar, hpBounds.x, hpBounds.y, hpBounds.width, hpBounds.height);
        batch.draw(currentHpBar, hpBounds.x, hpBounds.y,
                hpBounds.width * (enemy.getCurrentHp() / enemy.getMaxHp()), hpBounds.height);
    }
}
