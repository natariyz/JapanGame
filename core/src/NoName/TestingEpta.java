package NoName;

import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.BitSet;

public class TestingEpta {
    public static void main(String args[]) throws ParserConfigurationException, SAXException, IOException {
//        long a = 3221225474l;
//        String d = Long.toBinaryString(a);
//        System.out.println(Long.toBinaryString(a));
//        if(d.length() > 31 && d.charAt(d.length() - 32) == '1') System.out.println("horizontally");
//        if(d.length() > 30 && d.charAt(d.length() - 31) == '1') System.out.println("vertically");
//        if(d.length() > 29 && d.charAt(d.length() - 30) == '1') System.out.println("diagonally");
        TileMap map = new TileMap();
        SParser parser = new SParser();
        parser.readMap("core/assets/tilemaps/test_tilemap.tmx");
    }
}


