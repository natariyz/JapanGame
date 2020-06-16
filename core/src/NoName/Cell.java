package NoName;

import com.badlogic.gdx.graphics.Texture;

public class Cell {
    private Texture texture;
    private boolean vertically, horizontally, diagonally;

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public boolean isVertically() {
        return vertically;
    }

    public void setVertically(boolean vertically) {
        this.vertically = vertically;
    }

    public boolean isHorizontally() {
        return horizontally;
    }

    public void setHorizontally(boolean horizontally) {
        this.horizontally = horizontally;
    }

    public boolean isDiagonally() {
        return diagonally;
    }

    public void setDiagonally(boolean diagonally) {
        this.diagonally = diagonally;
    }
}
