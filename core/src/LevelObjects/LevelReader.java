package LevelObjects;

import MapObjects.MapReader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import GameObjects.Enemy;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LevelReader {

    private SAXParserFactory factory;
    private javax.xml.parsers.SAXParser parser;

    private final String enemiesFilePath = "enemies.xml";

    public LevelReader() throws ParserConfigurationException, SAXException {
        factory = SAXParserFactory.newInstance();
        parser = factory.newSAXParser();
    }

    public Level readLevel(String levelPath) throws IOException, SAXException {
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        Level level = new Level();

        EnemyXmlHandler enemyXmlHandler = new EnemyXmlHandler(enemies);
        File enemiesFile = new File(enemiesFilePath);
        parser.parse(enemiesFile, enemyXmlHandler);

        LevelXmlHandler levelXmlHandler = new LevelXmlHandler(level, enemies);
        File levelFile = new File(levelPath);
        parser.parse(levelFile, levelXmlHandler);


        return level;
    }

    private class EnemyXmlHandler extends DefaultHandler{

        private ArrayList<Enemy> enemies;

        public EnemyXmlHandler(ArrayList<Enemy> enemies){
            this.enemies = enemies;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if(qName.equals("Enemy")){
                Enemy enemy = new Enemy();

                enemy.setId(attributes.getValue("id"));
                enemy.setHp(Integer.parseInt(attributes.getValue("hp")));
                enemy.setMoveSpeed(Integer.parseInt(attributes.getValue("moveSpeed")));
                Texture enemyTexture = new Texture(attributes.getValue("image"));
                enemy.setSprite(new Sprite(enemyTexture));
                enemies.add(enemy);
            }
        }
    }

    private class LevelXmlHandler extends DefaultHandler{

        private Level level;
        private DefenceWave defenceWave;
        private EnemyGroup enemyGroup;
        private ArrayList<Enemy> enemies;

        public LevelXmlHandler(Level level, ArrayList<Enemy> enemies) {
            this.level = level;
            this.enemies = enemies;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            switch (qName){
                case ("DefenceLevel"):
                    readLevel(attributes);
                    break;
                case ("DefenceWave"):
                    readWave(attributes);
                    break;
                case ("EnemyGroup"):
                    readEnemyGroup(attributes);
                    break;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if(qName.equals("EnemyGroup")){
                defenceWave.getEnemyGroups().add(enemyGroup);
            }
            if(qName.equals("DefenceWave")){
                level.getWaves().add(defenceWave);
            }
        }

        private void readLevel(Attributes attributes){
            level.setId(attributes.getValue("id"));
            level.setDefaultSpawnDelay(Integer.parseInt(attributes.getValue("defaultSpawnDelay")));
            try {
                MapReader parser = new MapReader();
                level.setMap(parser.readMap(attributes.getValue("map")));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
        }

        private void readWave(Attributes attributes){
            defenceWave = new DefenceWave();
            String spawnDelay = attributes.getValue("spawnDelay");
            defenceWave.setSpawnDelay(spawnDelay == null ? level.getDefaultSpawnDelay() : Integer.parseInt(spawnDelay));
            String startTimeStringValue = attributes.getValue("startTime");
            if(startTimeStringValue != null) defenceWave.setStartTime(Integer.parseInt(startTimeStringValue));
            else{
                DefenceWave previousDefenceWave = level.getWaves().get(level.getWaves().size() - 1);
                int startTime = previousDefenceWave.getStartTime() + previousDefenceWave.getSpawnDelay();
                for (int groupIndex = 0; groupIndex < previousDefenceWave.getEnemyGroups().size(); groupIndex ++){
                    startTime += previousDefenceWave.getSpawnDelay() * previousDefenceWave.getEnemyGroups().get(groupIndex).getCount();
                }
                defenceWave.setStartTime(startTime);
            }
        }

        private void readEnemyGroup(Attributes attributes){
            enemyGroup = new EnemyGroup();
            enemyGroup.setCount(Integer.parseInt(attributes.getValue("count")));
            for(int enemyIndex = 0; enemyIndex < enemies.size(); enemyIndex++){
                if(attributes.getValue("enemyId").equals(enemies.get(enemyIndex).getId())){
                    enemyGroup.setEnemy(enemies.get(enemyIndex).copy());
                }
            }
        }
    }
}
