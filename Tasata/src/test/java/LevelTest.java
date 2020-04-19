
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import tasata.domain.Level;
import tasata.domain.Tile;

public class LevelTest {

    private Level level;

    @Before
    public void setUp() {
        level = new Level("LevelA");
                
        level.addTile(new Tile("A", 4));
        level.addTile(new Tile("B", 0));
        level.addTile(new Tile("C", 0));
        level.addTile(new Tile("D", 0));

        level.createTileConnections(new String[][]{
            {"A", "B", "3"},
            {"B", "C", "1"},
            {"C", "D", "3"}
        });
    }

    @Test
    public void returnTileWorksProperly() {
        Tile testTileA = level.getTile("A");
        Tile testTileB = level.getTile("X");
        
        assertThat(testTileA.getValue(), is(equalTo(4)));
        assertThat(testTileB, is(equalTo(null)));
    }
    
    @Test 
    public void creatingTileConnectionsWithParametersReturnsCorrectValue() {
        boolean testVariableA = level.createTileConnections(new String[][]{
            {"G", "B", "3"},
            {"B", "C", "1"},
            {"C", "D", "3"}
        });
        boolean testVariableB = level.createTileConnections(new String[][]{
            {"A", "B", "3"},
            {"B", "C", "1"},
            {"C", "D", "3"}
        });
        
        assertThat(testVariableA, is(equalTo(false)));
        assertThat(testVariableB, is(equalTo(true)));
    }
    
    @Test 
    public void creatingTileConnectionsWithoutParametersReturnsCorrectValue() {
        level.setTileConnections(new String[][]{
            {"G", "B", "3"},
            {"B", "C", "1"},
            {"C", "D", "3"}
        });      
        boolean testVariableA = level.createTileConnections();
        
        level.setTileConnections(new String[][]{
            {"A", "B", "3"},
            {"B", "C", "1"},
            {"C", "D", "3"}
        });      
        boolean testVariableB = level.createTileConnections();
        
        assertThat(testVariableA, is(equalTo(false)));
        assertThat(testVariableB, is(equalTo(true)));
    }
}
