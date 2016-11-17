import edu.matc.loops.daos.LoopsDao;
import edu.matc.loops.enitity.LoopsObj;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by Tim on 11/15/2016.
 */
public class LoopsDaoTest {

    private final Logger logger = Logger.getLogger(this.getClass());
    private LoopsDao ld;

    @Before
    public void setup() {
        ld = new LoopsDao();
    }

    @Test
    public void testGetLoops() {
        assert(ld.getAllLoopsObj().size() > 0);
    }

    @Test
    public void testGetByID() {
        assert(ld.getLoopsObj(1).getRouteDistance() > 0);
    }

    @Test
    public void testInsertDeleteLoops() {
        LoopsObj l = new LoopsObj(1,1,1,1,1);
        LoopsObj l1 = ld.insertLoopsObj(l);
        assert(l1.getLoopId() > 1);
        LoopsObj l2 = ld.deleteLoopsObj(l1.getLoopId());
        assert(l1.getLoopId() == l2.getLoopId());
    }
    


}
