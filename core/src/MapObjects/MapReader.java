package MapObjects;

import com.badlogic.gdx.math.Vector2;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class MapReader {

    private SAXParserFactory factory;
    private javax.xml.parsers.SAXParser parser;

    private String mapData;

    public MapReader() throws ParserConfigurationException, SAXException {
        factory = SAXParserFactory.newInstance();
        parser = factory.newSAXParser();
    }

    public TileMap readMap(String mapPath) throws IOException, SAXException {

        TileMap map = new TileMap();

        MapXMLHandler mapXMLHandler = new MapXMLHandler(map);
        File mapFile = new File(mapPath);
        parser.parse(mapFile, mapXMLHandler);

        File tileSetFile = new File(mapFile.getParentFile().getAbsolutePath(), map.getTileSet().getPath());
        TileSetXMLHandler tileSetXMLHandler = new TileSetXMLHandler(map.getTileSet(), tileSetFile.getParent());
        parser.parse(tileSetFile, tileSetXMLHandler);

        map.initializeCells(mapData);

        return map;
    }

    public class TileSetXMLHandler extends DefaultHandler {

        private TileSet tileSet;
        private String currentElement = "";
        private Tile tile;
        private String tileSetPath;

        private TileSetXMLHandler(TileSet tileSet, String tileSetPath){
            this.tileSet = tileSet;
            this.tileSetPath = tileSetPath;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            currentElement = qName;

            if(qName.equals("tile")){
                tile = new Tile();
                tile.setId(Integer.parseInt(attributes.getValue("id")));
            }
            if(qName.equals("image")){
                tile.setWidth(Integer.parseInt(attributes.getValue("width")));
                tile.setHeight(Integer.parseInt(attributes.getValue("height")));
                tile.setTexturePath(tileSetPath + "/" + attributes.getValue("source"));
            }
            if(qName.equals("object")){
                tile.setMainPoint(new Vector2(
                        Float.parseFloat(attributes.getValue("x")),
                        Float.parseFloat(attributes.getValue("y"))
                ));
            }
            if(qName.equals("polyline")){
                String allPoints = attributes.getValue("points");
                String [] splitedPoints = allPoints.split(" ");
                for(int index = 0; index < splitedPoints.length; index++){
                    String [] point = splitedPoints[index].split(",");
                    tile.getPoints().add(new Vector2(Float.parseFloat(point[0]), Float.parseFloat(point[1])));
                }
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            currentElement = "";
            if (qName.equals("tile")){
                tileSet.addTile(tile);
            }
        }
    }

    public class MapXMLHandler extends DefaultHandler {

        private TileMap map;
        private StringBuilder mapDataBuilder = new StringBuilder();
        private String currentElement = "";

        public MapXMLHandler(TileMap map){
            this.map = map;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            currentElement = qName;
            if(qName.equals("map")){
                map.setMapHeight(Integer.parseInt(attributes.getValue("height")));
                map.setMapWidth(Integer.parseInt(attributes.getValue("width")));
                map.setTileHeight(Integer.parseInt(attributes.getValue("tileheight")));
                map.setTileWidth(Integer.parseInt(attributes.getValue("tilewidth")));
            }
            if(qName.equals("tileset")) map.getTileSet().setPath(attributes.getValue("source"));
            if(qName.equals("object")){
                if (attributes.getValue("type").equals("startPoint")){
                    map.setStartCellX((int)Float.parseFloat(attributes.getValue("x")) / map.getTileWidth());
                    map.setStartCellY((int)Float.parseFloat(attributes.getValue("y")) / map.getTileHeight());
                }
                if (attributes.getValue("type").equals("endPoint")){
                    map.setEndCellX((int) Float.parseFloat(attributes.getValue("x")) / map.getTileWidth());
                    map.setEndCellY((int) Float.parseFloat(attributes.getValue("y")) / map.getTileHeight());
                }
            }
        }

        @Override
        public void characters(char[] chars, int i, int i1) {
            if(currentElement.equals("data")) mapDataBuilder.append(new String(chars, i, i1));
        }

        @Override
        public void endElement(String s, String s1, String s2) {
            currentElement = "";
        }

        @Override
        public void endDocument() {
            mapData = mapDataBuilder.toString();
        }
    }
}
