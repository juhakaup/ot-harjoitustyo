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
    private Scene menuScene; 
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
        loadLevel(currentLevel);
        window.show();
    }
    
    @Override
    public void onUiEvent(String[] args) {
        if (args[0].equals("TilePressed")) {
            game.getCurrentLevel().getTile(args[1]).disperseTile();
            gameScene.updateTiles(game.getCurrentLevel().getTileSet());
            if (game.isSolved()) {
                System.out.println("level solved");
            }
        } else if (args[0].equals("ResetPressed")) {
            loadLevel(currentLevel);
            ArrayList<Tile> ts = game.getCurrentLevel().getTileSet();
            gameScene.updateTiles(game.getCurrentLevel().getTileSet());
        }
    }
    
}
