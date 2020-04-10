package tasata.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import tasata.dao.FileLevelDao;
import tasata.domain.Game;
import tasata.domain.EventListener;
import tasata.domain.GameEvent;


public class TasataUi extends Application implements EventListener {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 600;
    
    private Stage window;
    private Game game;
    private GameScene gameScene;
    private MenuScene menuScene; 
    private String currentLevel = "A01";
    
    public void loadLevel(String levelId) {
        if (game.loadLevel(levelId)) {
            gameScene.setConnections(game.getCurrentLevel().getConnections());
            gameScene.createTiles(game.getCurrentLevel().getTileSet());
            window.setScene(gameScene.getScene());
        } else {
            System.out.println("Error loading level");
        }
    }
    
    @Override
    public void start(Stage stage) throws Exception { 
        window = stage;
        FileLevelDao dao = new FileLevelDao("assets/Levels.json");
        //FakeLevelDao dao = new FakeLevelDao();
        game = new Game(dao);
        game.addListener(this);
        
        gameScene = new GameScene(WIDTH, HEIGHT);
        gameScene.addListener(this);
        gameScene.addListener(game);
        
        menuScene = new MenuScene(WIDTH, HEIGHT, game.getCurrentLevels());
        menuScene.addListener(this);
        
        window.setScene(menuScene.getScene());
        window.show();
    }
    
    @Override
    public void onEvent(GameEvent event, String attribute) {
        switch (event) {
            case MENU_SCENE:
                System.out.println("Back to menu");
                window.setScene(menuScene.getScene());
                break;
            case NEXT_LEVEL:
                System.out.println("Next Level Please");
                break;
            case LOAD_LEVEL:
                System.out.println("Load Level " + attribute + " Please");
                currentLevel = attribute;
                loadLevel(currentLevel);
                break;
            case STATE_CHANGE:
                gameScene.updateTiles(game.getCurrentLevel().getTileSet());
                if (game.isSolved()) {
                    gameScene.levelSolved();
                }
                break;
            default:
                break;
        }
    }
    
}
