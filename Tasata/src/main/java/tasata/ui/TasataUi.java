package tasata.ui;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tasata.dao.FakeLevelDao;
import tasata.domain.Game;
import tasata.domain.Tile;


public class TasataUi extends Application implements EventHandler<ActionEvent> {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 600;
    private static final double TILEMAXSIZE = 65;
    private static final double TILEMINSIZE = 20;
    private static final double[][] DIR = new double[][]{ 
        {},{0.5,-0.87},{1,0},{0.5,0.87},{-0.5,0.87},{-1,0},{-0.5,-0.87} 
    };
    private Game game;
    private StackPane gameSegment;
    private GridPane titleSegment;
    private BorderPane root;
    private Text movesText;
    
    
    public void LoadLevel(String levelId) {
        game.loadLevel("levelId");
        updateTiles();
        movesText.setText("0");
    }
    
    @Override
    public void start(Stage stage) { 
        game = new Game(new FakeLevelDao());
        gameSegment = new StackPane();
        titleSegment = new GridPane();
        root = new BorderPane();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        
        titleSegment.setPadding(new Insets(10, 10, 10, 10));
        titleSegment.setMinSize(WIDTH, 30);
        titleSegment.setHgap(20);
        titleSegment.setAlignment(Pos.CENTER);
        
        Text titleText = new Text(10, 90, "TaSaTa");
        movesText = new Text(10, 90, "0");
        titleSegment.add(titleText, 0, 0);
        titleSegment.add(movesText, 2, 0);
        GridPane.setHalignment(titleText, HPos.LEFT);
        GridPane.setHalignment(movesText, HPos.RIGHT);
        
        Button resetLevel = new Button("Restart");
        resetLevel.setOnAction((ActionEvent e) -> {
            LoadLevel("tmp");
        });

        root.setCenter(gameSegment);
        root.setTop(titleSegment);
        root.setBottom(resetLevel);
        
        LoadLevel("tmp");
        updateTiles();
        
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void handle(ActionEvent event) {
        if(event.getSource() instanceof Button) {
            Button b = (Button) event.getSource();
            Tile t = (Tile)b.getUserData();
            t.disperseTile();
            updateTiles();
            game.incrementMoves();
            movesText.setText(Integer.toString(game.getMoves()));
            if(game.isSolved()) {
                Text text = new Text("You Winz the game!");
                text.setTranslateY(-50.0);
                gameSegment.getChildren().add(text);
            }
        }
    }
    
    private void updateTiles() {
        gameSegment.getChildren().clear();
        ArrayList<Tile> tiles = game.getCurrentLevel().getTileSet();
        ArrayList<Node> uiTiles = new ArrayList<>();
        
        for (Tile tile : tiles) {
            Button button = new Button(Integer.toString(tile.getValue()));
            button.setUserData(tile);
            button.setOnAction(this);
            button.setPrefSize(55, 64);
            
            // todo: convert into class with no hard coded value
            Polygon hexagon = new Polygon();
            hexagon.getPoints().addAll(new Double[]{        
                  0.0, 25.0, 43.0, 0.0, 86.0, 25.0, 
                86.0, 75.0, 43.0, 100.0, 0.0, 75.0, 
            });
            
            button.setShape(hexagon);
            
            uiTiles.add(button);
            gameSegment.getChildren().add(button);
        }
        setTilePositions(uiTiles);
    }
    
    private void setTilePositions(ArrayList<Node> nodes) {
        if (nodes.size() < 2) return;
        
        int[][] connections = game.getCurrentLevel().getConnections();
        
        for (int[] connection : connections) {
            int tile1 = connection[0];
            int tile2 = connection[1];
            int direction = connection[2];
            
            double originX = nodes.get(tile1).getTranslateX();
            double originY = nodes.get(tile1).getTranslateY();
            
            double newPosX = originX + DIR[direction][0] * TILEMAXSIZE;
            double newPosY = originY + DIR[direction][1] * TILEMAXSIZE;
            
            nodes.get(tile2).setTranslateX(newPosX);
            nodes.get(tile2).setTranslateY(newPosY);
            
            Line line = new Line(originX, originY, originX + DIR[direction][0] * 5, originY + DIR[direction][1] * 5);
            line.setStrokeWidth(4.0);
            line.setStroke(Color.DARKGRAY);
            line.setTranslateX(originX + DIR[direction][0] * (TILEMAXSIZE/2));
            line.setTranslateY(originY + DIR[direction][1] * (TILEMAXSIZE/2));
            gameSegment.getChildren().add(line);
        }
    }
    
}
