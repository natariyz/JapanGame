package MapObjects;

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
                map.setHeight(Integer.parseInt(attributes.getValue("height")));
                map.setWidth(Integer.parseInt(attributes.getValue("width")));
                map.setTileHeight(Integer.parseInt(attributes.getValue("tileheight")));
                map.setTileWidth(Integer.parseInt(attributes.getValue("tilewidth")));
            }
            if(qName.equals("tileset")) map.getTileSet().setPath(attributes.getValue("source"));
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
