import edu.matc.loops.daos.LoopInfoDao;
import edu.matc.loops.enitity.LoopInfoObj;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Tim on 11/15/2016.
 */
public class LoopInfoDaoTest {
    private final Logger logger = Logger.getLogger(this.getClass());
    private LoopInfoDao lid;

    @Before
    public void setup() {
        lid = new LoopInfoDao();
    }

    @Test
    public void testGetAll() {
        assert(lid.getAllLoopInfoObj().size() > 0);
    }

    @Test
    public void getById() {
        assert(lid.getLoopInfoObj(1).getId() > 0);
    }

    @Test
    public void testInsertAndDelete() {
        LoopInfoObj lio = new LoopInfoObj(1,1,1,1,1,true,true,true,true);
        LoopInfoObj lio2 = lid.insertLoopInfo(lio);
        assert(lio2.getId() > 1);
        LoopInfoObj lio3 = lid.deleteLoopInfoObj(lio2.getId());
        assert(lio3.getxSize() == lio2.getxSize());
    }

    @Test
    public void testSearch() {
        assert(lid.searchLoopInfoObj("xSize", 20).size() > 0);
    }

}
