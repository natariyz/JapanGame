package NoName;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class SParser {

    private SAXParserFactory factory;
    private javax.xml.parsers.SAXParser parser;

    public SParser() throws ParserConfigurationException, SAXException {
        factory = SAXParserFactory.newInstance();
        parser = factory.newSAXParser();
    }

    public TileMap readMap(String mapPath) throws IOException, SAXException {

        TileMap map = new TileMap();

        MapXMLHandler mapXMLHandler = new MapXMLHandler(map);
        File mapFile = new File(mapPath);
        parser.parse(mapFile, mapXMLHandler);

        File tileSetFile = new File(mapFile.getParentFile().getAbsolutePath(), map.getTileSet().getPath());
        TileSetXMLHandler tileSetXMLHandler = new TileSetXMLHandler(map, tileSetFile.getParent());
        parser.parse(tileSetFile, tileSetXMLHandler);

        return map;
    }

    public static class TileSetXMLHandler extends DefaultHandler {

        private TileMap map;
        private String thisElement = "";
        private Tile tile;
        private String tileSetPath;

        private TileSetXMLHandler(TileMap map, String tileSetPath){
            this.map = map;
            this.tileSetPath = tileSetPath;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            thisElement = qName;

            if(qName.equals("tile")){
                tile = new Tile();
                tile.setId(Integer.parseInt(attributes.getValue("id")));
            }
            if(qName.equals("image")){
                tile.setWidth(Integer.parseInt(attributes.getValue("width")));
                tile.setHeight(Integer.parseInt(attributes.getValue("height")));
                tile.setTexturePath(tileSetPath + "/" + attributes.getValue("source"));
            }
        }

        @Override
        public void endDocument() throws SAXException {
            map.setMap_matrix();
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            thisElement = "";
            if (qName.equals("tile")){
                map.getTileSet().addTile(tile);
            }
        }
    }

    public static class MapXMLHandler extends DefaultHandler {

        private TileMap map;
        private StringBuilder stringBuilder = new StringBuilder();
        private String thisElement = "";

        public MapXMLHandler(TileMap map){
            this.map = map;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            thisElement = qName;
            if(qName.equals("map")) map.setHeight(Integer.parseInt(attributes.getValue("height")));
            if(qName.equals("map")) map.setWidth(Integer.parseInt(attributes.getValue("width")));
            if(qName.equals("map")) map.setTileHeight(Integer.parseInt(attributes.getValue("tileheight")));
            if(qName.equals("map")) map.setTileWidth(Integer.parseInt(attributes.getValue("tilewidth")));
            if(qName.equals("tileset")) map.getTileSet().setPath(attributes.getValue("source"));
        }

        @Override
        public void characters(char[] chars, int i, int i1) {
            if(thisElement.equals("data")) stringBuilder.append(new String(chars, i, i1));
        }

        @Override
        public void endElement(String s, String s1, String s2) {
            thisElement = "";
        }

        @Override
        public void endDocument() {
            map.setMapData(stringBuilder.toString());
        }
    }
}
