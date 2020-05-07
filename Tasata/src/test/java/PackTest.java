
import java.util.Map;
import static org.hamcrest.CoreMatchers.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tasata.dao.FileLevelDao;
import tasata.dao.FilePackDao;
import tasata.dao.LevelDao;
import tasata.dao.PackDao;
import tasata.domain.Pack;
import tasata.domain.State;

public class PackTest {

    @Test
    public void unlockIsWorkingProperly() throws Exception {
        PackDao packDao = new FilePackDao("assets/Packs.json", "assets/Progress.json");
        Pack pack = packDao.findPackById("TestPack");
        
        Map<String, State> states = pack.getPackState();
        State state1 = states.get("TestData");
        State state2 = states.get("A02");
        
        pack.unlock("TestData");
        
        State state3 = states.get("TestData");
        State state4 = states.get("A02");
        
        assertThat(state1, is(equalTo(State.AVAILABLE)));
        assertThat(state2, is(equalTo(State.LOCKED)));
        assertThat(state3, is(equalTo(State.AVAILABLE)));
        assertThat(state4, is(equalTo(State.AVAILABLE)));
    }
}
