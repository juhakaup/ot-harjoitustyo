package tasata.ui;

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

public class TasataUi extends Application implements EventListener {
    private static int width;
    private static int height;
    private Stage window;
    private Game game;
    private GameScene gameScene;
    private MenuScene menuScene; 
    private FileLevelDao levelDao;
    private FilePackDao packDao;
    private String startingPack;
    
    public void prepareAndLauchLevel() {     
        gameScene.setConnections(game.getCurrentLevel().getConnections());
        gameScene.createTiles(game.getCurrentLevel().getValues());
        window.setScene(gameScene.getScene());   
    }
    
    @Override
    public void init() throws Exception {
        Properties properties = new Properties();

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        properties.load(classLoader.getResourceAsStream("config.properties"));       
        String levelFile = properties.getProperty("levelFile");
        String packFile = properties.getProperty("packFile");
        String progressFile = properties.getProperty("progressFile");
        width = Integer.parseInt(properties.getProperty("screenWidth"));
        height = Integer.parseInt(properties.getProperty("screenHeight"));
        startingPack = properties.getProperty("startingPack");
        
        levelDao = new FileLevelDao(levelFile);
        packDao = new FilePackDao(packFile, progressFile, levelDao);
    }
    
    @Override
    public void start(Stage stage) throws Exception {   
        window = stage;
        window.setTitle("TaSaTa");
        
        game = new Game(levelDao, packDao);
        game.addListener(this);
        game.loadLevelPack(startingPack);
        
        gameScene = new GameScene(width, height);
        gameScene.addListener(this);
        gameScene.addListener(game);
        
        menuScene = new MenuScene(width, height, game.getCurrentLevels());
        menuScene.addListener(this);
        menuScene.addListener(game);
        menuScene.updateLevelList(game.getPackState());
        
        window.setScene(menuScene.getScene());
        window.setResizable(false);
        window.show();
    }
    
    @Override
    public void onEvent(GameEvent event, Object attribute) {
        
        switch (event) {
            case MENU_SCENE:
                window.setScene(menuScene.getScene());
                break;
            case LEVEL_LOADED:
                prepareAndLauchLevel();
                break;
            case TILE_CHANGE:
                gameScene.updateTileValues((Map<String, Integer>) attribute);
                break;
            case LEVEL_SOLVED:
                gameScene.levelSolved();
                break;
            case PACK_STATE_UPDATE:
                menuScene.updateLevelList((Map<String, State>) attribute);
                break;
            case MOVE_COUNT_UPDATED:
                if (attribute instanceof String[]) {
                    String[] att = (String[]) attribute;
                    gameScene.setMoves(att[0]);
                    gameScene.setLevelState(State.valueOf(att[1]));
                }
                break;
            default:
                break;
        }
        
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
