package tasata;

import tasata.domain.Game;
import tasata.dao.FakeLevelDao;

public class Main {
    public static void main(String[] args) {
        Game game = new Game(new FakeLevelDao());
        
        //game.loadLevel("0");        
        //game.disperse(0);
        //game.disperse(2);
        
    }
}
