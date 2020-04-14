package tasata.ui;

import java.util.ArrayList;
import java.util.Map;
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
    
    private static final int WIDTH = 400;
    private static final int HEIGHT = 600;
    
    private Stage window;
    private Game game;
    private GameScene gameScene;
    private MenuScene menuScene; 
    private String currentLevel = "A01";
    
    public void levelLoaded() {     
        gameScene.setConnections(game.getCurrentLevel().getConnections());
        gameScene.createTiles(game.getCurrentLevel().getTileSet());
        window.setScene(gameScene.getScene());
        
    }
    
    @Override
    public void start(Stage stage) throws Exception { 
        
        window = stage;
        FileLevelDao levelDao = new FileLevelDao("assets/Levels.json");
        FilePackDao packDao = new FilePackDao("assets/Packs.json", levelDao);
        
        game = new Game(levelDao, packDao);
        game.addListener(this);
        game.loadLevelPack("StarterPack");
        
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
                currentLevel = (String) attribute;
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
