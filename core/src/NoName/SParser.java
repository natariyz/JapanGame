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

    public TileMap readMap(String path) throws IOException, SAXException {

        TileMap map = new TileMap();

        MapXMLHandler mapXMLHandler = new MapXMLHandler(map);

        parser.parse(new File(path), mapXMLHandler);

        return new TileMap();
    }

    public static class MapXMLHandler extends DefaultHandler {

        TileMap map;

        public MapXMLHandler(TileMap map){
            this.map = map;
        }

        String thisElement = "";

        @Override
        public void startElement(String s, String s1, String s2, Attributes attributes) {
            thisElement = s2;
            if(s2.equals("map")) map.setHeight(Integer.parseInt(attributes.getValue("height")));
            if(s2.equals("map")) map.setWidth(Integer.parseInt(attributes.getValue("width")));
        }

        @Override
        public void characters(char[] chars, int i, int i1) {

            if(thisElement.equals("data")){
                map.setMap_matrix(chars, i, i1);
            }
        }

        @Override
        public void endElement(String s, String s1, String s2) {
            thisElement = "";
        }
    }
}
