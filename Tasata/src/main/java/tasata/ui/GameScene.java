package tasata.ui;

import tasata.domain.EventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import tasata.domain.GameEvent;
import tasata.domain.State;

public class GameScene {

    private static final double TILEMAXSIZE = 65;
    private static final double LINEWIDTH = 5;
    private static final double[][] DIR = new double[][]{
        {}, {0.5, -0.87}, {1, 0}, {0.5, 0.87}, {-0.5, 0.87}, {-1, 0}, {-0.5, -0.87}};
    private final StackPane gameSegment;
    private String[][] connections;
    private HashMap<String, Button> uiTiles;
    private final List<EventListener> listeners = new ArrayList<>();
    private final Group gameTiles;
    private final GridPane titleSegment;
    private final BorderPane root;
    private final Scene scene;
    private final VBox popMenu;
    private final Text numberOfMovesText;
    private final Button resetLevel;
    private final Button displayMenu;
    private final HBox controls;
    private Button popReset;
    private Button enablePopMenu;
    private Button popNext;
    private Text popScoreText;
    private State state = State.AVAILABLE;
    private final int WIDTH;
    private final int HEIGHT;

    public GameScene(int width, int height) {
        this.HEIGHT = height;
        this.WIDTH = width;
        
        // Area for puzzle
        gameSegment = new StackPane();
        gameTiles = new Group();
        gameTiles.prefHeight(WIDTH);
        gameTiles.prefWidth(WIDTH);
        gameSegment.getChildren().add(gameTiles);
        
        // Title segment
        titleSegment = new GridPane();
        titleSegment.setPadding(new Insets(10, 10, 10, 10));
        titleSegment.setMinSize(width, 30);
        titleSegment.setHgap(20);
        titleSegment.setAlignment(Pos.CENTER);
        Text movesTitle = new Text(10, 90, "Moves");
        numberOfMovesText = new Text(10, 90, "0");
        titleSegment.add(movesTitle, 0, 0);
        titleSegment.add(numberOfMovesText, 2, 0);
        GridPane.setHalignment(movesTitle, HPos.LEFT);
        GridPane.setHalignment(numberOfMovesText, HPos.RIGHT);

        root = new BorderPane();
        scene = new Scene(root, width, height);

        // Popup menu
        popMenu = createPopupMenu();
        popMenu.setVisible(false);
        gameSegment.getChildren().add(popMenu);
        
        // Botton controls
        resetLevel = new Button("Restart");
        resetLevel.setOnAction(e -> {
            notifyListeners(GameEvent.RESET_LEVEL, "");
        });

        displayMenu = new Button("Menu");
        displayMenu.setOnAction(e-> {
            notifyListeners(GameEvent.MENU_SCENE, "");
        });
        
        controls = new HBox();
        controls.getChildren().add(resetLevel);
        controls.getChildren().add(displayMenu);

        // Window layout
        root.setCenter(gameSegment);
        root.setTop(titleSegment);
        root.setBottom(controls);
    }

    public Scene getScene() {
        return this.scene;
    }
    
    public void setConnections(String[][] connections) {
        this.connections = connections;
    }
    
    public void setMoves(String moves) {
        this.numberOfMovesText.setText(moves);
    }
    
    public void setLevelState(State state) {
        this.state = state;
    }
    
    public void levelSolved() {
        setScoreText();
        switchMenuFocus();
    }
    
    private void switchMenuFocus() {
        popMenu.setVisible(!popMenu.visibleProperty().getValue());
        gameTiles.setDisable(!gameTiles.disableProperty().getValue());
        controls.setDisable(!controls.disableProperty().getValue());
    }

    private VBox createPopupMenu() {
        popReset = new Button("Retry");
        popReset.setOnAction(e ->{
            notifyListeners(GameEvent.RESET_LEVEL, "");
            switchMenuFocus();
        });
        
        enablePopMenu = new Button("Back to menu");
        enablePopMenu.setOnAction(e-> {
            notifyListeners(GameEvent.MENU_SCENE, "");
            switchMenuFocus();
        });
        
        popNext = new Button("Next");
        popNext.setOnAction(e -> {
            notifyListeners(GameEvent.NEXT_LEVEL, null);
            switchMenuFocus();
        });

        HBox buttons = new HBox();
        buttons.getChildren().addAll(popReset, enablePopMenu, popNext);
        buttons.setSpacing(10);
        buttons.setAlignment(Pos.CENTER);

        VBox background = new VBox();
        background.setMaxWidth(WIDTH * 0.65);
        background.setMaxHeight(WIDTH * 0.3);
        background.setStyle("-fx-background-color: rgba(0, 102, 204, 0.7); -fx-background-radius: 10;");
        background.setAlignment(Pos.CENTER);
        
        popScoreText = new Text();
        popScoreText.setTranslateY(-5);
        Text title = new Text("Level Solved!");
        title.setStyle("-fx-font: 22px Tahoma");
        title.setFill(Color.WHITESMOKE);
        title.setTranslateY(-10);

        background.getChildren().add(popScoreText);
        background.getChildren().add(title);
        background.getChildren().add(buttons);

        return background;
    }
    
    public void setScoreText() {
        if (this.state == State.GOLD) {
            popScoreText.setText("٭٭٭");
            popScoreText.setStyle("-fx-font: 42px Tahoma; -fx-fill: radial-gradient(center 50% -40%, radius 180%, YELLOW 46%, GOLD 51%);");
        } else if (this.state == State.SILVER) {
            popScoreText.setText("٭٭");
            popScoreText.setStyle("-fx-font: 42px Tahoma; -fx-fill: radial-gradient(center 50% -40%, radius 180%, WHITESMOKE 45%, SILVER 70%);");
        } else if (this.state == State.BRONZE ) {
            popScoreText.setText("٭");
            popScoreText.setStyle("-fx-font: 42px Tahoma; -fx-fill: radial-gradient(center 50% -40%, radius 180%, WHEAT 46%, GOLDENROD 51%)");
        }
    }
    
    public void createTiles(Map<String, Integer> tiles) {
        gameTiles.getChildren().clear();
        uiTiles = new HashMap<>();

        for (String tile : tiles.keySet()) {
            Button button = new Button(Integer.toString(tiles.get(tile)));
            button.setUserData(tile);
            button.setOnAction(e-> {
                notifyListeners(GameEvent.TILE_PRESS, tile);
            });
            button.setPrefSize(55, 64);
            uiTiles.put(tile, button);
            button.setShape(createHexagon());
            gameTiles.getChildren().add(button);
        }
        setTilePositions();
    }

    private Polygon createHexagon() {
        Polygon hexagon = new Polygon();
        hexagon.getPoints().addAll(new Double[]{
            0.0, 25.0, 43.0, 0.0, 86.0, 25.0,
            86.0, 75.0, 43.0, 100.0, 0.0, 75.0});
        return hexagon;
    }

    public void updateTileValues(Map<String, Integer> tiles) {
        for (String tile : tiles.keySet()) {
            uiTiles.get(tile).setText(Integer.toString(tiles.get(tile)));
        }
    }

    private void setTilePositions() {
        if (uiTiles.size() < 2) {
            return;
        }

        for (String[] connection : connections) {
            Node tile1 = uiTiles.get(connection[0]);
            Node tile2 = uiTiles.get(connection[1]);
            int direction = Integer.parseInt(connection[2]);

            double originX = tile1.getTranslateX();
            double originY = tile1.getTranslateY();
            double newPosX = originX + DIR[direction][0] * TILEMAXSIZE;
            double newPosY = originY + DIR[direction][1] * TILEMAXSIZE;
            tile2.setTranslateX(newPosX);
            tile2.setTranslateY(newPosY);

            Line line = new Line(
                    (tile1.getTranslateX() + 0.5 * TILEMAXSIZE) + DIR[direction][0] * TILEMAXSIZE * 0.45, 
                    (tile1.getTranslateY() + 0.5 * TILEMAXSIZE) + DIR[direction][1] * TILEMAXSIZE * 0.45, 
                    (tile2.getTranslateX() + 0.5 * TILEMAXSIZE) - DIR[direction][0] * TILEMAXSIZE * 0.45,
                    (tile2.getTranslateY() + 0.5 * TILEMAXSIZE) - DIR[direction][1] * TILEMAXSIZE * 0.45
            );
            line.setTranslateX(-LINEWIDTH);            
            line.setStrokeWidth(LINEWIDTH);
            line.setStroke(Color.DARKGRAY);
            
            gameTiles.getChildren().add(line);
        }
    }

    public void addListener(EventListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }
    
    private void notifyListeners(GameEvent event, String attribute) {
        for (EventListener listener : listeners) {
            listener.onEvent(event, attribute);
        }
    }

}
