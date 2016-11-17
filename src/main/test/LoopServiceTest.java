import edu.matc.loops.enitity.CoordinateObj;
import edu.matc.loops.enitity.LoopInfoObj;
import edu.matc.loops.enitity.LoopsObj;
import edu.matc.loops.rest.LoopsService;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * Created by Tim on 11/15/2016.
 */
public class LoopServiceTest {

    private final Logger logger = Logger.getLogger(this.getClass());
    private LoopsService ls;

    @Before
    public void setup() {
        ls = new LoopsService();
    }

    @Test
    public void testReturns() {
        assert(ls.jsonReturnOfLoopInfo(new ArrayList<LoopInfoObj>(), new ArrayList<LoopsObj>(), new ArrayList<CoordinateObj>()) == "");
    }

    @Test
    public void badReturn() {
        Response r = ls.getLoopsResponse(1,1,1,1,1,1,1,true,true,true,true, "notHTML");
        Response r2 = Response.status(200).entity("Error: Invalid returnType").build();
        assert(r2.getEntity() == r.getEntity());
    }

    @Test
    public void getHTMLReturn() {

    }

}
