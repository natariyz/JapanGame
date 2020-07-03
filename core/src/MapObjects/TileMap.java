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

                ArrayList<Vector2> points = new ArrayList<Vector2>();

                for(int point = 0; point < currentTile.getPoints().size(); point++){

                    Vector2 pointCoords = new Vector2(
                            currentTile.getPoints().get(point).x + currentTile.getMainPoint().x,
                            currentTile.getPoints().get(point).y + currentTile.getMainPoint().y
                    );

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
                if(!points.isEmpty()) cells[x][y].setPoints(points);
            }
        }
        findStartAndEndPoint();
        path = findAllPath();
    }

    public ArrayList<Vector2> findAllPath(){
        ArrayList<Vector2> path = new ArrayList<Vector2>();

        ArrayList<Vector2> currentCellPoints = new ArrayList<Vector2>();
        currentCellPoints.addAll(cells[startCellX][startCellY].getPoints());
        path.add(startPoint);
        currentCellPoints.remove(startPoint);

        while(!currentCellPoints.isEmpty()){
            findClosestTilePoint(path, currentCellPoints);
        }

        findClosestToPointTile(startCellX, startCellY, path);

        return path;
    }

    public void findClosestToPointTile(int currentCellX, int currentCellY, ArrayList<Vector2> path){
        Cell newCell;
        Vector2 newPoint;
        double shortestDistance;
        ArrayList<Cell> possibleCells = new ArrayList<Cell>();

        if(currentCellX - 1 >= 0){
            Cell cell = cells[currentCellX-1][currentCellY];
            if(cell.getPoints() != null && !path.contains(cell.getPoints().get(0))){
                possibleCells.add(cell);
            }
        }
        if(currentCellX + 1 < mapWidth){
            Cell cell = cells[currentCellX + 1][currentCellY];
            if(cell.getPoints() != null && !path.contains(cell.getPoints().get(0))){
                possibleCells.add(cell);
            }
        }
        if(currentCellY - 1 >= 0){
            Cell cell = cells[currentCellX][currentCellY - 1];
            if(cell.getPoints() != null && !path.contains(cell.getPoints().get(0))){
                possibleCells.add(cell);
            }
        }
        if(currentCellY + 1 < mapHeight){
            Cell cell = cells[currentCellX][currentCellY + 1];
            if(cell.getPoints() != null && !path.contains(cell.getPoints().get(0))){
                possibleCells.add(cell);
            }
        }

        if(possibleCells.isEmpty()) return;

        newCell = possibleCells.get(0);
        newPoint = newCell.getPoints().get(0);
        shortestDistance = findPointDistance(path.get(path.size() - 1), newPoint);

        for(int cell = 0; cell < possibleCells.size(); cell++){
            for(int point = 0; point < possibleCells.get(cell).getPoints().size(); point++){
                if(shortestDistance > findPointDistance(path.get(path.size() - 1), possibleCells.get(cell).getPoints().get(point))){
                    newCell = possibleCells.get(cell);
                    shortestDistance = findPointDistance(newPoint, possibleCells.get(cell).getPoints().get(point));
                    newPoint = possibleCells.get(cell).getPoints().get(point);
                }
            }
        }

        ArrayList<Vector2> currentCellPoints = new ArrayList<Vector2>();
        currentCellPoints.addAll(newCell.getPoints());
        path.add(newPoint);
        currentCellPoints.remove(newPoint);
        while(!currentCellPoints.isEmpty())
        {
            findClosestTilePoint(path, currentCellPoints);
        }

        findClosestToPointTile(newCell.getX(), newCell.getY(), path);
    }

    public void findClosestTilePoint(ArrayList<Vector2> path, ArrayList<Vector2> currentCellPoints){
        Vector2 currentPoint = null;
        double shortestDistance = 0;

        for(int point = 0; point < currentCellPoints.size(); point++){
            if(currentPoint == null){
                currentPoint = currentCellPoints.get(point);
                shortestDistance = findPointDistance(path.get(path.size() - 1), currentPoint);
            }
            else {
                if(shortestDistance > findPointDistance(path.get(path.size() - 1), currentCellPoints.get(point))){
                    currentPoint = currentCellPoints.get(point);
                    shortestDistance = findPointDistance(path.get(path.size() - 1), currentPoint);
                }
            }
        }
        path.add(currentPoint);
        currentCellPoints.remove(currentPoint);
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
            if(mapWidth * tileWidth - newPoint.x < minDistanceToBorder){
                currentClosestPoint = newPoint;
                minDistanceToBorder = mapWidth * tileWidth - newPoint.x;
            }
            if(newPoint.y < minDistanceToBorder){
                currentClosestPoint = newPoint;
                minDistanceToBorder = newPoint.y;
            }
            if(mapHeight * tileHeight - newPoint.y < minDistanceToBorder){
                currentClosestPoint = newPoint;
                minDistanceToBorder = mapHeight * tileHeight - newPoint.y;
            }
        }
        return  currentClosestPoint;
    }

    public static double findPointDistance(Vector2 p1, Vector2 p2){
        return Math.sqrt(Math.pow((p2.x - p1.x), 2) + Math.pow((p2.y - p1.y),2));
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
