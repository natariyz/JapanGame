package NoName;

public class TileMap {
    private int width, height;

    private int[][] map_matrix;

    public int[][] getMap_matrix() {
        return map_matrix;
    }

    public void setMap_matrix(char[] chars, int start, int length){
        map_matrix = new int[width][height];

        String map_data = (new String(chars, start, length));

//        String [] delimetres = new String[]{"\n", " "};
//        map_data = map_data.replaceAll("\n", "");
//        map_data = map_data.replaceAll(" ", "");
//        String [] map_data_array = map_data.split(",");
//        System.out.println(map_data);

        map_data = map_data.replace("\n", "");
        map_data = map_data.replace(" ", "");

        String [] map_data_array = map_data.split(",");

        System.out.println(map_data_array);
    }

    public void setMap_matrix(int[][] map_matrix) {
        this.map_matrix = map_matrix;
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
}
