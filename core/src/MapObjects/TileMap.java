package MapObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class TileMap {
    private TileSet tileSet = new TileSet();

    private Cell[][] cells;

    private int mapWidth, mapHeight;
    private int tileWidth, tileHeight;
    private Vector2 startPoint, endPoint;
    private int startCellX, startCellY, endCellX, endCellY;
    private ArrayList<Vector2> path;

    public static final long flippedHorizontallyFlag = 0x80000000L;
    public static final long flippedVerticallyFlag = 0x40000000L;
    public static final long flippedDiagonallyFlag = 0x20000000L;

    public void initializeCells(String mapData){
        cells = new Cell[mapWidth][mapHeight];

        boolean isFlippedHorizontally, isFlippedVertically, isFlippedDiagonally;

        mapData = mapData.replaceAll("\n", "");
        String [] mapDataArray = mapData.split(",");

        for(int x = 0; x < mapWidth; x++){
            for(int y = 0; y < mapHeight; y++){
                long id = Long.parseLong(mapDataArray[y * mapWidth + x]);
                long cleanId = id & ~(flippedHorizontallyFlag | flippedVerticallyFlag | flippedDiagonallyFlag);

                cells[x][y] = new Cell();
                cells[x][y].setX(x);
                cells[x][y].setY(y);

                Tile currentTile = tileSet.getTiles().get((int)cleanId - 1);

                Texture texture = new Texture(currentTile.getTexturePath());
                Sprite sprite = new Sprite(texture);

                sprite.setBounds(x * tileWidth,(mapHeight - y - 1) * tileHeight, tileWidth, tileHeight);
                sprite.setOrigin(tileWidth / 2, tileHeight / 2);

                isFlippedHorizontally = (id & flippedHorizontallyFlag) == flippedHorizontallyFlag;
                isFlippedVertically = (id & flippedVerticallyFlag) == flippedVerticallyFlag;
                isFlippedDiagonally = (id & flippedDiagonallyFlag) == flippedDiagonallyFlag;

                if(isFlippedDiagonally) sprite.rotate(90);
                sprite.flip(isFlippedHorizontally, isFlippedVertically);

                cells[x][y].setSprite(sprite);

                flipPoints(currentTile, cells[x][y], isFlippedDiagonally, isFlippedHorizontally, isFlippedVertically);
            }
        }
        findStartAndEndPoint();
        path = buildPath();
    }

    public void flipPoints(Tile tile, Cell cell, boolean isFlippedDiagonally, boolean isFlippedHorizontally, boolean isFlippedVertically){
        ArrayList<Vector2> points = new ArrayList<>();
        Sprite sprite = cell.getSprite();

        for(int point = 0; point < tile.getPoints().size(); point++){

            Vector2 pointCoords = new Vector2(tile.getPoints().get(point).x, tile.getPoints().get(point).y);

            if(isFlippedDiagonally){
                if(pointCoords.x < tileWidth / 2 && pointCoords.y < tileHeight / 2){
                    pointCoords = new Vector2(tileHeight - pointCoords.y, pointCoords.x);
                }

                else if(pointCoords.x < tileWidth / 2 && pointCoords.y > tileHeight / 2){
                    pointCoords = new Vector2(tileHeight - pointCoords.y, pointCoords.x);
                }

                else if(pointCoords.x > tileWidth / 2 && pointCoords.y > tileHeight / 2){
                    pointCoords = new Vector2(
                            tileWidth - (tileHeight - pointCoords.y),
                            tileWidth - pointCoords.x
                    );
                }

                else if(pointCoords.x > tileWidth / 2 && pointCoords.y < tileHeight / 2){
                    pointCoords = new Vector2(tileHeight - pointCoords.y, pointCoords.x);
                }
            }

            if(isFlippedHorizontally){
                pointCoords.x = tileWidth - pointCoords.x;
            }

            if (isFlippedVertically){
                pointCoords.y = tileHeight - pointCoords.y;
            }

            points.add(new Vector2(
                    sprite.getX() + pointCoords.x,
                    sprite.getY() + (tileHeight - pointCoords.y)
            ));
        }
        if(!points.isEmpty()) cell.setPoints(points);
    }

    public ArrayList<Vector2> buildPath(){
        ArrayList<Vector2> path = new ArrayList<>();

        findNextPoint(path, null, startCellX, startCellY);

        findNextCell(startCellX, startCellY, path);

        return path;
    }

    public void findNextPoint(ArrayList<Vector2> path, ArrayList<Vector2> currentCellPoints, int cellX, int cellY){
        if(cellX == startCellX && cellY == startCellY){
            currentCellPoints = new ArrayList<>(cells[startCellX][startCellY].getPoints());
            path.add(startPoint);
            currentCellPoints.remove(startPoint);
        }

        Vector2 currentPoint = null;
        double shortestDistance = 0;

        for(int point = 0; point < currentCellPoints.size(); point++){
            if(currentPoint == null){
                currentPoint = currentCellPoints.get(point);
                shortestDistance = currentPoint.dst(path.get(path.size() - 1));
            }
            else {
                if(shortestDistance > currentCellPoints.get(point).dst(path.get(path.size() - 1))){
                    currentPoint = currentCellPoints.get(point);
                    shortestDistance = currentPoint.dst(path.get(path.size() - 1));
                }
            }
        }
        path.add(currentPoint);
        currentCellPoints.remove(currentPoint);

        if(!currentCellPoints.isEmpty()) findNextPoint(path, currentCellPoints, cellX, cellY);
        findNextCell(cellX, cellY, path);
    }

    public void findNextCell(int currentCellX, int currentCellY, ArrayList<Vector2> path){
        ArrayList<Cell> borderingCells = new ArrayList<>();

        if(currentCellX - 1 >= 0){
            Cell cell = cells[currentCellX-1][currentCellY];
            if(cell.getPoints() != null && !path.contains(cell.getPoints().get(0))){
                borderingCells.add(cell);
            }
        }
        if(currentCellX + 1 < mapWidth){
            Cell cell = cells[currentCellX + 1][currentCellY];
            if(cell.getPoints() != null && !path.contains(cell.getPoints().get(0))){
                borderingCells.add(cell);
            }
        }
        if(currentCellY - 1 >= 0){
            Cell cell = cells[currentCellX][currentCellY - 1];
            if(cell.getPoints() != null && !path.contains(cell.getPoints().get(0))){
                borderingCells.add(cell);
            }
        }
        if(currentCellY + 1 < mapHeight){
            Cell cell = cells[currentCellX][currentCellY + 1];
            if(cell.getPoints() != null && !path.contains(cell.getPoints().get(0))){
                borderingCells.add(cell);
            }
        }

        if(borderingCells.isEmpty()) return;

        Cell newCell = borderingCells.get(0);
        Vector2 newPoint = newCell.getPoints().get(0);
        double shortestDistance = newPoint.dst(path.get(path.size() - 1));

        for(int cell = 0; cell < borderingCells.size(); cell++){//проходим по всем точкам соседних клеток и ищем самую ближнюю к последней точке пути
            for(int point = 0; point < borderingCells.get(cell).getPoints().size(); point++){
                Vector2 currentPoint = borderingCells.get(cell).getPoints().get(point);
                if(shortestDistance > currentPoint.dst(path.get(path.size() - 1))){
                    newCell = borderingCells.get(cell);
                    shortestDistance = currentPoint.dst(newPoint);
                    newPoint = currentPoint;
                }
            }
        }

        ArrayList<Vector2> cellPoints = new ArrayList<>(newCell.getPoints());
        path.add(newPoint);
        cellPoints.remove(newPoint);

        while(!cellPoints.isEmpty())
        {
            findNextPoint(path, cellPoints, newCell.getX(), newCell.getY());
        }
    }

    public void findStartAndEndPoint(){
        Cell startCell = cells[startCellX][startCellY], endCell = cells[endCellX][endCellY];

        this.startPoint = findClosestToBorderPoint(startCell);
        this.endPoint = findClosestToBorderPoint(endCell);
    }

    public Vector2 findClosestToBorderPoint(Cell cell){
        Vector2 currentClosestPoint = cell.getPoints().get(0);
        float minDistanceToBorder = cell.getPoints().get(0).x;

        for(int point = 0; point < cell.getPoints().size(); point++){
            Vector2 newPoint = cell.getPoints().get(point);
            if(newPoint.x < minDistanceToBorder){
                currentClosestPoint = newPoint;
                minDistanceToBorder = newPoint.x;
            }
            if(mapWidth * tileWidth - newPoint.x < minDistanceToBorder){ //mapWidth * tileWidth ширина экрана
                currentClosestPoint = newPoint;
                minDistanceToBorder = mapWidth * tileWidth - newPoint.x;
            }
            if(newPoint.y < minDistanceToBorder){
                currentClosestPoint = newPoint;
                minDistanceToBorder = newPoint.y;
            }
            if(mapHeight * tileHeight - newPoint.y < minDistanceToBorder){//а тут длина
                currentClosestPoint = newPoint;
                minDistanceToBorder = mapHeight * tileHeight - newPoint.y;
            }
        }
        return  currentClosestPoint;
    }

    public void draw(SpriteBatch batch){
        for (int x = 0; x < mapWidth; x++){
            for(int y = 0; y < mapHeight; y++){
                cells[x][y].getSprite().draw(batch);
            }
        }
    }

    public ArrayList<Vector2> getPath() {
        return path;
    }

    public void setPath(ArrayList<Vector2> path) {
        this.path = path;
    }

    public void setStartCellX(int startCellX) {
        this.startCellX = startCellX;
    }

    public void setStartCellY(int startCellY) {
        this.startCellY = startCellY;
    }

    public void setEndCellX(int endCellX) {
        this.endCellX = endCellX;
    }

    public void setEndCellY(int endCellY) {
        this.endCellY = endCellY;
    }

    public Vector2 getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Vector2 startPoint) {
        this.startPoint = startPoint;
    }

    public Vector2 getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Vector2 endPoint) {
        this.endPoint = endPoint;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public void setTileWidth(int tileWidth) {
        this.tileWidth = tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public void setTileHeight(int tileHeight) {
        this.tileHeight = tileHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }

    public TileSet getTileSet() {
        return tileSet;
    }

    public Cell[][] getCells() {
        return cells;
    }
}
