
package tasata.ui;

import tasata.domain.EventListener;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import tasata.domain.GameEvent;
import tasata.domain.Level;

public class MenuScene {

    private final Scene scene;
    private final BorderPane root;
    private final List<EventListener> listeners = new ArrayList<>();
    private final ArrayList<Level> levels;
    private final VBox levelList;

    public MenuScene(int width, int height, ArrayList<Level> levels) {
        root = new BorderPane();
        scene = new Scene(root, width, height);
        this.levels = levels;
        this.levelList = new VBox();
        this.levelList.setAlignment(Pos.CENTER);
        
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
        for (Level level : levels) {
            Button button = new Button(level.getId());
            button.setUserData(level.getId());
            
            button.setOnAction(e -> {
                notifyListeners(GameEvent.LOAD_LEVEL, level.getId());
            });
            
            levelList.getChildren().add(button);
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
