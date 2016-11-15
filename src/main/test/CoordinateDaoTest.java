import edu.matc.loops.daos.CoordinateDao;
import edu.matc.loops.enitity.Coordinate;
import edu.matc.loops.enitity.CoordinateObj;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by Tim on 11/15/2016.
 */
public class CoordinateDaoTest {
    private final Logger logger = Logger.getLogger(this.getClass());
    private CoordinateDao cd;

    @Before
    public void setup() {
        cd = new CoordinateDao();
    }

    @Test
    public void testGetAll() {
        List<CoordinateObj> coords = cd.getAllCoordinateObj();
        assert(coords.size() > 0);
    }

    @Test
    public void testGetOneById() {
        CoordinateObj co = cd.getCoordinateObj(1);
        assert(co.getPosition() > 0);
    }

    @Test
    public void testInsertAndDelete() {
        CoordinateObj co = new CoordinateObj(1,1,1,1,1);
        CoordinateObj co1 = cd.insertCoordinate(co);
        assert(co1.getPosition() == 1);
        CoordinateObj co2 = cd.deleteCoordinate(co1.getCoordinate_id());
        assert(co2.getPosition() == co1.getPosition());
    }

    @Test
    public void testSearch() {
        List<CoordinateObj> coords = cd.searchCoordinateObj("xCoord", 10);
        assert(coords.size() > 0);
    }
}
