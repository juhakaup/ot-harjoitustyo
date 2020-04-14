package tasata.domain;

public interface EventListener {
    
    void onEvent(GameEvent event, Object o);
    
}
