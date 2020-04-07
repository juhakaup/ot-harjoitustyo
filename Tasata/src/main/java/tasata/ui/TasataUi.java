package tasata.ui;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tasata.dao.FileLevelDao;
import tasata.domain.Game;
import tasata.domain.Tile;


public class TasataUi extends Application implements UiEventListener {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 600;
    
    private Stage window;
    private Game game;
    private GameScene gameScene;
    private MenuScene menuScene; 
    private String currentLevel = "A01";
    
    public void loadLevel(String levelId) {
        if (game.loadLevel(levelId)) {
            gameScene = new GameScene(WIDTH, HEIGHT, game.getCurrentLevel().getConnections());
            gameScene.createTiles(game.getCurrentLevel().getTileSet());
            gameScene.addListener(this);
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
        
        menuScene = new MenuScene(WIDTH, HEIGHT);
        menuScene.addListener(this);
        window.setScene(menuScene.getScene());
        
        //loadLevel(currentLevel);
        window.show();
    }
    
    @Override
    public void onUiEvent(String[] args) {
        switch (args[0]) {
            case "TilePressed":
                game.getCurrentLevel().getTile(args[1]).disperseTile();
                gameScene.updateTiles(game.getCurrentLevel().getTileSet());
                if (game.isSolved()) {
                    System.out.println("level solved");
                    gameScene.displayPopMenu();
                }   break;
            case "ResetPressed":
                loadLevel(currentLevel);
                ArrayList<Tile> ts = game.getCurrentLevel().getTileSet();
                gameScene.updateTiles(game.getCurrentLevel().getTileSet());
                break;
            case "BackToMenu":
                System.out.println("Back to menu");
                window.setScene(menuScene.getScene());
                break;
            case "NextLevel":
                System.out.println("Next Level Please");
                break;
            case "LoadLevel":
                System.out.println("Load Level " + args[1] + " Please");
                currentLevel = args[1];
                loadLevel(currentLevel);
                break;
            default:
                break;
        }
    }
    
}
