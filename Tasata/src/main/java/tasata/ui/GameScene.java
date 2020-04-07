package tasata.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import tasata.domain.Tile;


public class GameScene implements EventHandler {

    private static final double TILEMAXSIZE = 65;
    private static final double[][] DIR = new double[][]{
        {}, {0.5, -0.87}, {1, 0}, {0.5, 0.87}, {-0.5, 0.87}, {-1, 0}, {-0.5, -0.87}};
    private final StackPane gameSegment;
    private final String[][] connections;
    private final HashMap<String, Button> uiTiles;
    private final List<UiEventListener> listeners = new ArrayList<>();

    private final GridPane titleSegment;
    private final BorderPane root;
    private final Scene scene;
    private final Text movesText;
    private int moves = 0;
    private final Button resetLevel;
    private Button displayMenu;
    private Button popReset;
    private Button popMenu;
    private Button popNext;
    private final int WIDTH;
    private final int HEIGHT;

    public GameScene(int width, int height, String[][] connections) {
        this.HEIGHT = height;
        this.WIDTH = width;
        gameSegment = new StackPane();
        this.connections = connections;
        uiTiles = new HashMap<>();

        titleSegment = new GridPane();
        titleSegment.setPadding(new Insets(10, 10, 10, 10));
        titleSegment.setMinSize(width, 30);
        titleSegment.setHgap(20);
        titleSegment.setAlignment(Pos.CENTER);

        root = new BorderPane();
        scene = new Scene(root, width, height);

        Text titleText = new Text(10, 90, "TaSaTa");
        movesText = new Text(10, 90, "0");

        resetLevel = new Button("Restart");
        resetLevel.setUserData("ResetLevel");
        resetLevel.setOnAction(this);
        
        displayMenu = new Button("Menu");
        displayMenu.setOnAction(e -> {
            displayPopMenu();
        });

        titleSegment.add(titleText, 0, 0);
        titleSegment.add(movesText, 2, 0);
        GridPane.setHalignment(titleText, HPos.LEFT);
        GridPane.setHalignment(movesText, HPos.RIGHT);
        
        HBox controls = new HBox();
        controls.getChildren().add(resetLevel);
        controls.getChildren().add(displayMenu);

        root.setCenter(gameSegment);
        root.setTop(titleSegment);
        root.setBottom(controls);
    }

    public Scene getScene() {
        return this.scene;
    }
    
    public void displayPopMenu() {
        disableTiles();
        popReset = new Button("Retry");
        popReset.setOnAction(this);
        popMenu = new Button("Back to menu");
        popMenu.setOnAction(this);
        popNext = new Button("Next");
        popNext.setOnAction(this);
        
        HBox hbox = new HBox();
        hbox.getChildren().add(popReset);
        hbox.getChildren().add(popMenu);
        hbox.getChildren().add(popNext);
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER);
        //hbox.setStyle("-fx-background-color: #ecf2f9;");
        
        VBox vbox = new VBox();
        vbox.setMaxWidth(WIDTH*0.7);
        vbox.setMaxHeight(WIDTH*.25);
        vbox.setStyle("-fx-background-color: rgba(0, 102, 204, 0.5); -fx-background-radius: 10;");
        vbox.setAlignment(Pos.CENTER);
        Text text = new Text("Level Solved!");
        text.setStyle("-fx-font: 22px Tahoma");
        text.setFill(Color.WHITESMOKE);
        text.setTranslateY(-10);
        
        vbox.getChildren().add(text);
        vbox.getChildren().add(hbox);
        
        gameSegment.getChildren().add(vbox);
    }
    
    public void disableTiles() {
        System.out.println("Disable tiles unimplemented");
    }
    
    public void enableTiles() {
        System.out.println("Enable tiles unimplemented");
        
    }

    public void createTiles(ArrayList<Tile> tiles) {
        gameSegment.getChildren().clear();

        for (Tile tile : tiles) {
            Button button = new Button(Integer.toString(tile.getValue()));
            button.setUserData(tile.getId());
            button.setOnAction(this);
            button.setPrefSize(55, 64);
            uiTiles.put(tile.getId(), button);
            button.setShape(createHexagon());
            gameSegment.getChildren().add(button);
        }
        updateTilePositions();
        moves = 0;
    }

    private Polygon createHexagon() {
        Polygon hexagon = new Polygon();
        hexagon.getPoints().addAll(new Double[]{
            0.0, 25.0, 43.0, 0.0, 86.0, 25.0,
            86.0, 75.0, 43.0, 100.0, 0.0, 75.0});
        return hexagon;
    }

    public void updateTiles(ArrayList<Tile> tiles) {
        for (Tile tile : tiles) {
            uiTiles.get(tile.getId()).setText(Integer.toString(tile.getValue()));
        }
        updateTilePositions();
    }

    private void updateTilePositions() {
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

            Line line = new Line(originX, originY, originX + DIR[direction][0] * 5, 
                    originY + DIR[direction][1] * 5);
            line.setStrokeWidth(4.0);
            line.setStroke(Color.DARKGRAY);
            line.setTranslateX(originX + DIR[direction][0] * (TILEMAXSIZE / 2));
            line.setTranslateY(originY + DIR[direction][1] * (TILEMAXSIZE / 2));
            gameSegment.getChildren().add(line);
        }
    }

    public void addListener(UiEventListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }

    @Override
    public void handle(Event event) {
        if (event.getSource() instanceof Button) {
            Button b = (Button) event.getSource();
            String[] args = new String[2];

            if (b == resetLevel || b == popReset) {
                args = new String[]{"ResetPressed", ""};
            } else if (uiTiles.containsKey(String.valueOf(b.getUserData()))) {
                moves++;
                movesText.setText(Integer.toString(moves));
                args = new String[]{"TilePressed", String.valueOf(b.getUserData())};
            } else if (b == popMenu) {
                args = new String[]{"BackToMenu", ""};
            } else if (b == popNext) {
                args = new String[]{"NextLevel", ""};
            }

            for (UiEventListener listener : listeners) {
                listener.onUiEvent(args);
            }
        }
    }

}
