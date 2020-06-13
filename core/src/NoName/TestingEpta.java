package NoName;

import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class TestingEpta {
    public static void main(String args[]) throws ParserConfigurationException, SAXException, IOException {
        TileMap map = new TileMap();
        SParser parser = new SParser();
        parser.readMap("core/assets/tilemaps/test_tilemap.tmx");
    }
}


