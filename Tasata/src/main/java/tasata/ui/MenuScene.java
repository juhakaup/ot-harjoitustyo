
package tasata.ui;

import java.util.ArrayList;
import java.util.List;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MenuScene implements EventHandler {

    private Scene scene;
    private final BorderPane root;
    private final Button level1;
    private final Button level2;
    private final List<UiEventListener> listeners = new ArrayList<>();

    public MenuScene(int width, int height) {
        System.out.println("this is menu scene");
        root = new BorderPane();
        scene = new Scene(root, width, height);
        
        level1 = new Button("Level 1");
        level1.setOnAction(this);
        level2 = new Button("Level 2");
        level2.setOnAction(this);
        
        VBox vBox = new VBox();
        Text title = new Text("TaSaTa");
        title.setStyle("-fx-font: 22px Tahoma");
        title.setTranslateY(-50);
        Text inst = new Text("Select level to play!");
        inst.setTranslateY(-10);
        
        vBox.getChildren().add(title);
        vBox.getChildren().add(inst);
        vBox.getChildren().add(level1);
        vBox.getChildren().add(level2);
        vBox.setAlignment(Pos.CENTER);
        
        root.setCenter(vBox);
    }
    
    public Scene getScene() {
        return this.scene;
    }
    
    public void addListener(UiEventListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }
    
    @Override
    public void handle(Event event) {
        if (event.getSource() instanceof Button) {
            Button button = (Button) event.getSource();
            String[] args = new String[2];
            if(button == level1) {
                args[0] = "LoadLevel";
                args[1] = "A01";
            }
            if(button == level2) {
                args[0] = "LoadLevel";
                args[1] = "A02";
            }
            
            for (UiEventListener listener : listeners) {
                listener.onUiEvent(args);
            }
        }
    }
    
}
