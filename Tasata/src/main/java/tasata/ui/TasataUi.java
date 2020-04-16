package tasata.ui;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import javafx.application.Application;
import javafx.stage.Stage;
import tasata.dao.FileLevelDao;
import tasata.dao.FilePackDao;
import tasata.domain.Game;
import tasata.domain.EventListener;
import tasata.domain.GameEvent;
import tasata.domain.State;
import tasata.domain.Tile;


public class TasataUi extends Application implements EventListener {
    
    private static int WIDTH;
    private static int HEIGHT;
    
    private Stage window;
    private Game game;
    private GameScene gameScene;
    private MenuScene menuScene; 
    private FileLevelDao levelDao;
    private FilePackDao packDao;
    private String startingPack;
    
    public void levelLoaded() {     
        gameScene.setConnections(game.getCurrentLevel().getConnections());
        gameScene.createTiles(game.getCurrentLevel().getTileSet());
        window.setScene(gameScene.getScene());   
    }
    
    @Override
    public void init() throws Exception {
        Properties properties = new Properties();

        properties.load(new FileInputStream("config.properties"));
        
        String levelFile = properties.getProperty("levelFile");
        String packFile = properties.getProperty("packFile");
        String progressFile = properties.getProperty("progressFile");
        WIDTH = Integer.parseInt(properties.getProperty("screenWidth"));
        HEIGHT = Integer.parseInt(properties.getProperty("screenHeight"));
        startingPack = properties.getProperty("staringPack");
        
        levelDao = new FileLevelDao(levelFile);
        packDao = new FilePackDao(packFile, progressFile, levelDao);
    }
    
    @Override
    public void start(Stage stage) throws Exception { 
        
        window = stage;
        
        game = new Game(levelDao, packDao);
        game.addListener(this);
        game.loadLevelPack(startingPack);
        
        gameScene = new GameScene(WIDTH, HEIGHT);
        gameScene.addListener(this);
        gameScene.addListener(game);
        
        menuScene = new MenuScene(WIDTH, HEIGHT, game.getCurrentLevels());
        menuScene.addListener(this);
        menuScene.addListener(game);
        menuScene.updateLevelList(game.getLevelsState());
        
        window.setScene(menuScene.getScene());
        window.show();
        
    }
    
    @Override
    public void onEvent(GameEvent event, Object attribute) {
        
        switch (event) {
            case MENU_SCENE:
                window.setScene(menuScene.getScene());
                break;
            case NEXT_LEVEL:
                break;
            case LEVEL_LOADED:
                levelLoaded();
                break;
            case TILE_CHANGE:
                gameScene.updateTiles((ArrayList<Tile>) attribute);
                break;
            case LEVEL_SOLVED:
                gameScene.levelSolved();
                break;
            case LEVEL_STATE_CHANGE:
                menuScene.updateLevelList((Map<String, State>)attribute);
                break;
            default:
                break;
        }
        
    }
    
}
