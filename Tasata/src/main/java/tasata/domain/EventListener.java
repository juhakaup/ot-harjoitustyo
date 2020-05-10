package tasata.domain;

/**
 * Interface for listening to events from observed classes
 */

public interface EventListener {
    
    /**
     * Receives an event from observed class
     * 
     * @param event enumerator for event
     * @param o event payload
     */
    
    void onEvent(GameEvent event, Object o);
    
}
