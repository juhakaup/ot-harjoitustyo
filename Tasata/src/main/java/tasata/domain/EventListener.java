package tasata.domain;

import tasata.domain.GameEvent;

public interface EventListener {
    
    void onEvent(GameEvent event, String param);
    
}
