
import static org.hamcrest.CoreMatchers.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import tasata.dao.FileLevelDao;
import tasata.dao.FilePackDao;
import tasata.dao.LevelDao;
import tasata.dao.PackDao;
import tasata.domain.Game;
import tasata.domain.GameEvent;

public class GameTest {
    
    private Game game;
    
    @Before
    public void setUp() throws Exception {
        LevelDao fld = new FileLevelDao("assets/Levels.json");
        PackDao fpd = new FilePackDao("assets/Packs.json", "assets/Progress.json", fld);
        game = new Game(fld, fpd);
        game.loadLevelPack("TestPack");
    }
    
    @Test
    public void loadLevelIsWorkingProperly() {
        boolean testVariableA = game.loadLevel("XXXXUIOAPEJEG");
        boolean testVariableB = game.loadLevel("TestData");
        
        assertThat(testVariableA, is(equalTo(false)));
        assertThat(testVariableB, is(equalTo(true)));
    }
    
    @Test
    public void isSolvedIsWorkingProperly() {
        game.loadLevel("TestData");
        
        game.getCurrentLevel().getTile("A").disperseTile();
        boolean solvedA = game.isSolved();
        
        game.getCurrentLevel().getTile("A").disperseTile();
        boolean solvedB = game.isSolved();
        
        game.getCurrentLevel().getTile("A").disperseTile();
        boolean solvedC = game.isSolved();
        
        game.getCurrentLevel().getTile("B").disperseTile();
        boolean solvedD = game.isSolved();
        
        assertThat(solvedA, is(equalTo(false)));
        assertThat(solvedB, is(equalTo(false)));
        assertThat(solvedC, is(equalTo(false)));
        assertThat(solvedD, is(equalTo(true)));
        
    }
    
}
