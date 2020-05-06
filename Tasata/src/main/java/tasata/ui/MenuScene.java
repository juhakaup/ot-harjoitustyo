
package tasata.ui;

import tasata.domain.EventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import tasata.domain.GameEvent;
import tasata.domain.State;

public class MenuScene {

    private final Scene scene;
    private final BorderPane root;
    private final List<EventListener> listeners = new ArrayList<>();
    private final ArrayList<String[]> levels;
    private final Map<String, Node> levelNodes;
    private final VBox levelList;
    private static final double BUTTONSPACING = 5.0;
    private static final double BUTTONWIDTH = 160.0;

    public MenuScene(int width, int height, ArrayList<String[]> levels) {
        root = new BorderPane();
        scene = new Scene(root, width, height);
        this.levels = levels;
        this.levelList = new VBox();
        this.levelList.setAlignment(Pos.CENTER);
        this.levelList.setSpacing(BUTTONSPACING);
        
        levelNodes = new HashMap<>();
        createLevelList();
        
        VBox menuElements = new VBox();
        Text title = new Text("TaSaTa");
        title.setStyle("-fx-font: 22px Tahoma");
        title.setTranslateY(-50);
        Text inst = new Text("Select level to play!");
        inst.setTranslateY(-10);
        menuElements.getChildren().addAll(title, inst, levelList);
        menuElements.setAlignment(Pos.CENTER);
        
        root.setCenter(menuElements);
    }
    
    public Scene getScene() {
        return this.scene;
    }
    
    private void createLevelList() {
        for (String[] level : levels) {
            Button button = new Button(level[1]);
            button.setUserData(level[0]);
            button.setPrefWidth(BUTTONWIDTH);
            
            button.setOnAction(e -> {
                notifyListeners(GameEvent.LOAD_LEVEL, level[0]);
            });
            
            levelList.getChildren().add(button);
            levelNodes.put(level[0], button);
        }
    }
    
    public void updateLevelList(Map<String, State> states) {
        
        for (String id : states.keySet()) {
            if (!levelNodes.containsKey(id)) continue;
            
            Node node = levelNodes.get(id);
            
            State state = states.get(id);
            if (state.ordinal() > 0) {
                node.setDisable(false);
                switch (state) {
                    case BRONZE:
                        node.setStyle("-fx-background-color: radial-gradient(center 50% -40%, radius 180%, WHEAT 46%, GOLDENROD 51%)");
                        break;
                    case SILVER:
                        node.setStyle("-fx-background-color: radial-gradient(center 50% -40%, radius 180%, WHITESMOKE 45%, LIGHTSLATEGRAY 50%, SILVER 70%)");
                        break;
                    case GOLD:
                        node.setStyle("-fx-background-color: radial-gradient(center 50% -40%, radius 180%, YELLOW 46%, GOLD 51%)");
                        break;
                    default:
                        break;
                }
            } else {
                node.setDisable(true);
            }
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
