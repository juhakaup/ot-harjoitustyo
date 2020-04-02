
import static org.hamcrest.CoreMatchers.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tasata.domain.Tile;

public class TileTest {
    
    @Test
    public void disperseIsWorkingPropely() {
        // Create four tiles
        Tile tileA = new Tile("1",4);
        Tile tileB = new Tile("2",0);
        Tile tileC = new Tile("3",0);
        Tile tileD = new Tile("4",0);
        
        // Connect tiles B & C to A and D to C
        // (B)--(A)--(C)--(D)
        tileA.addAdjecentTile(tileB);
        tileA.addAdjecentTile(tileC);
        tileC.addAdjecentTile(tileD);
        
        // Disperse tileA, this should increment tiles B & C by 1 and Decrement A by 2
        tileA.disperseTile();
        
        assertThat(tileA.getValue(), is(equalTo(2)));
        assertThat(tileB.getValue(), is(equalTo(1)));
        assertThat(tileC.getValue(), is(equalTo(1)));
        assertThat(tileD.getValue(), is(equalTo(0)));
    }

}
