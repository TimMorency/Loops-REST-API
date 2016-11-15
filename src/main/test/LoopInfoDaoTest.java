import edu.matc.loops.daos.LoopInfoDao;
import org.apache.log4j.Logger;
import org.junit.Before;

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
}
