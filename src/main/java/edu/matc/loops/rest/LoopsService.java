package edu.matc.loops.rest;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.matc.loops.daos.CoordinateDao;
import edu.matc.loops.daos.LoopInfoDao;
import edu.matc.loops.daos.LoopsDao;
import edu.matc.loops.enitity.*;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/loops")
public class LoopsService {

    private final static int X_MIN = 50;
    private final static int X_MAX = 100;
    private final static int Y_MIN = 50;
    private final static int Y_MAX = 100;

    private final Logger logger = Logger.getLogger(this.getClass());

    LoopGenerator loopGenerator;

    //Testing URL
    //http://localhost:8080/service/loops/generateQuery?xSize=100&ySize=100&routeDistance=100&legSize=5&numLoops=2&returnType=HTML

    @GET
    @Path("/generateQuery")
    public Response getLoopsQuery(
            @DefaultValue("0") @QueryParam("xSize") int xSize,
            @DefaultValue("0") @QueryParam("ySize") int ySize,
            @DefaultValue("0") @QueryParam("routeDistance") int routeDistance,
            @DefaultValue("0") @QueryParam("legSize") int legSize,
            @DefaultValue("1") @QueryParam("numLoops") int numLoops,

            @DefaultValue("50") @QueryParam("sameFailCount") int sameFailCount,
            @DefaultValue("500") @QueryParam("failCount") int failCount,

            @DefaultValue("false") @QueryParam("allowDoubleBack") boolean allowDoubleBack,
            @DefaultValue("false") @QueryParam("allowSameCoordinates") boolean allowSameCoordinates,
            @DefaultValue("false") @QueryParam("allowThroughStart") boolean allowThroughStart,

            @DefaultValue("false") @QueryParam("variableLegLength") boolean variableLegLength,

            @DefaultValue("") @QueryParam("returnType") String returnType) {

        Response queryResponse = getLoopsResponse(xSize,ySize,routeDistance,legSize,numLoops,sameFailCount,failCount,
                allowDoubleBack, allowSameCoordinates,allowThroughStart,
                variableLegLength, returnType);
        return queryResponse;

    }

    @POST
    @Path("/getWithIDParams")
    public Response findLoopByIDParams(@QueryParam("Id") int id,
                                     @DefaultValue("JSON") @QueryParam("returnType") String returnType) {

        LoopsDao lDao = new LoopsDao();
        CoordinateDao cDao = new CoordinateDao();

        LoopsObj lo = new LoopsObj();
        List<CoordinateObj> coords = new ArrayList<CoordinateObj>();
        lo = lDao.getLoopsObj(id);
        logger.info(lo.getLoopId());
        coords = cDao.searchCoordinateObj("loopId", lo.getLoopId());

        String returner = lo.toString().substring(0, lo.toString().length() - 1);
        returner += ",Coordinates=[";
        for(CoordinateObj c : coords) {
            if(coords.indexOf(c)+1 != coords.size()) {
                returner += c.toString() + ", ";
            } else {
                returner += c.toString();
            }
        }
        returner += "]}";
        //Return response
        return Response
                .status(200)
                .entity(returner).build();
    }

    @POST
    @Path("/getWithID")
    public Response findLoopByIDform(@FormParam("Id") int id,
                                 @DefaultValue("JSON") @FormParam("returnType") String returnType) {

        LoopsDao lDao = new LoopsDao();
        CoordinateDao cDao = new CoordinateDao();

        LoopsObj lo = new LoopsObj();
        List<CoordinateObj> coords = new ArrayList<CoordinateObj>();
        lo = lDao.getLoopsObj(id);
        //logger.info(lo.getLoopId());
        coords = cDao.searchCoordinateObj("loopId", lo.getLoopId());

        String returner = lo.toString().substring(0, lo.toString().length() - 1);
        returner += ",Coordinates=[";
        for(CoordinateObj c : coords) {
            if(coords.indexOf(c)+1 != coords.size()) {
                returner += c.toString() + ", ";
            } else {
                returner += c.toString();
            }
        }
        returner += "]}";
        //Return response
        return Response
                .status(200)
                .entity(returner).build();
    }

    @POST
    @Path("/searchWithLoopInfo")
    public Response searchByLoopInfo(@FormParam("x_size") int xSize,
                                     @FormParam("y_size") int ySize,
                                     @FormParam("num_loops") int numLoops,
                                     @FormParam("fail_count") int failCount,
                                     @FormParam("allow_double_back") boolean allowDoubleBack,
                                     @FormParam("allow_same_coordinates") boolean allowSameCoordinates,
                                     @FormParam("allow_through_start") boolean allowThroughStart,
                                     @FormParam("variable_leg_size") boolean variableLegSize) {

        LoopInfoDao lid = new LoopInfoDao();
        LoopsDao ld = new LoopsDao();
        CoordinateDao cd = new CoordinateDao();

        Map<String, String> pushMap = new HashMap<String, String>();

        if(xSize > 0){
            pushMap.put("xSize", String.valueOf(xSize));
        }
        if(ySize > 0){
            pushMap.put("ySize", String.valueOf(ySize));
        }
        if(numLoops > 0){
            pushMap.put("numLoops", String.valueOf(numLoops));
        }
        if(failCount > 0){
            pushMap.put("failCount", String.valueOf(failCount));
        }
        pushMap.put("allowDoubleBack", String.valueOf(allowDoubleBack));
        pushMap.put("allowSameCoordinates", String.valueOf(allowSameCoordinates));
        pushMap.put("allowThroughStart", String.valueOf(allowThroughStart));
        pushMap.put("variableLegSize", String.valueOf(variableLegSize));

        List<LoopInfoObj> lios = lid.searchLoopInfoMultipleRestrictions(pushMap);
        List<Integer> loopInfoIds = new ArrayList<Integer>();
        for(LoopInfoObj l : lios) {
            loopInfoIds.add(l.getId());
        }

        List<LoopsObj> los = new ArrayList<LoopsObj>();
        if(loopInfoIds.size() != 0) {
            los = ld.getLoopsFromLoopInfo(loopInfoIds);
        }
        List<CoordinateObj> coords = new ArrayList<CoordinateObj>();
        if(los.size() != 0) {
            List<Integer> loIds = new ArrayList<Integer>();
            for (LoopsObj lo : los) {
                loIds.add(lo.getLoopId());
            }
            coords = cd.searchInClause(loIds);
        }
        return Response
                .status(200)
                .entity(jsonReturnOfLoopInfo(lios, los, coords)).build();



    }

    public String jsonReturnOfLoopInfo(List<LoopInfoObj> lios, List<LoopsObj> los,
                                       List<CoordinateObj> cos) {
        String totalString = "";
        for(LoopInfoObj lio : lios) {
            String returner = lio.toString().substring(0, lio.toString().length() -1);
            returner += ", Loops=[";
            for(LoopsObj lo : los) {
                returner += lo.toString().substring(0, lo.toString().length()-1);
                returner += ",Coordinates=[";
                for(CoordinateObj co : cos) {
                    if(co.getPosition() != (lo.getRouteDistance() / lo.getLeglength())) {
                        returner += co.toString() + ",";
                    } else {
                        returner += co.toString();
                    }
                }
                returner += "]";
                returner += "}";
            }
            returner += "]";
            returner += "}";
            totalString += returner + " ";
        }


        return totalString;
    }



    @POST
    @Path("/searchWithLoopInfoParams")
    public Response searchByLoopInfoParams(@QueryParam("x_size") int xSize,
                                     @QueryParam("y_size") int ySize,
                                     @QueryParam("num_loops") int numLoops,
                                     @QueryParam("fail_count") int failCount,
                                     @QueryParam("allow_double_back") boolean allowDoubleBack,
                                     @QueryParam("allow_same_coordinates") boolean allowSameCoordinates,
                                     @QueryParam("allow_through_start") boolean allowThroughStart,
                                     @QueryParam("variable_leg_size") boolean variableLegSize) {

        LoopInfoDao lid = new LoopInfoDao();
        LoopsDao ld = new LoopsDao();
        CoordinateDao cd = new CoordinateDao();

        Map<String, String> pushMap = new HashMap<String, String>();

        if(xSize > 0){
            pushMap.put("xSize", String.valueOf(xSize));
        }
        if(ySize > 0){
            pushMap.put("ySize", String.valueOf(ySize));
        }
        if(numLoops > 0){
            pushMap.put("numLoops", String.valueOf(numLoops));
        }
        if(failCount > 0){
            pushMap.put("failCount", String.valueOf(failCount));
        }
        pushMap.put("allowDoubleBack", String.valueOf(allowDoubleBack));
        pushMap.put("allowSameCoordinates", String.valueOf(allowSameCoordinates));
        pushMap.put("allowThroughStart", String.valueOf(allowThroughStart));
        pushMap.put("variableLegSize", String.valueOf(variableLegSize));

        List<LoopInfoObj> lios = lid.searchLoopInfoMultipleRestrictions(pushMap);
        List<Integer> loopInfoIds = new ArrayList<Integer>();
        for(LoopInfoObj l : lios) {
            loopInfoIds.add(l.getId());
        }

        List<LoopsObj> los = new ArrayList<LoopsObj>();
        if(loopInfoIds.size() != 0) {
            los = ld.getLoopsFromLoopInfo(loopInfoIds);
        }
        List<CoordinateObj> coords = new ArrayList<CoordinateObj>();
        if(los.size() != 0) {
            List<Integer> loIds = new ArrayList<Integer>();
            for (LoopsObj lo : los) {
                loIds.add(lo.getLoopId());
            }
            coords = cd.searchInClause(loIds);
        }
        return Response
                .status(200)
                .entity(jsonReturnOfLoopInfo(lios, los, coords)).build();
    }

    @POST
    @Path("/generateForm")
    public Response getLoopsForm(
            @DefaultValue("0") @FormParam("xSize") int xSize,
            @DefaultValue("0") @FormParam("ySize") int ySize,
            @DefaultValue("0") @FormParam("routeDistance") int routeDistance,
            @DefaultValue("0") @FormParam("legSize") int legSize,
            @DefaultValue("1") @FormParam("numLoops") int numLoops,

            @DefaultValue("50") @FormParam("sameFailCount") int sameFailCount,
            @DefaultValue("500") @FormParam("failCount") int failCount,

            @DefaultValue("false") @FormParam("allowDoubleBack") boolean allowDoubleBack,
            @DefaultValue("false") @FormParam("allowSameCoordinates") boolean allowSameCoordinates,
            @DefaultValue("false") @FormParam("allowThroughStart") boolean allowThroughStart,

            @DefaultValue("false") @FormParam("variableLegLength") boolean variableLegLength,

            @DefaultValue("") @FormParam("returnType") String returnType) {

        Response formResponse = getLoopsResponse(xSize,ySize,routeDistance,legSize,numLoops,sameFailCount,failCount,
                allowDoubleBack, allowSameCoordinates,allowThroughStart,
                variableLegLength, returnType);
        return formResponse;
    }

    public Response getLoopsResponse(int xSize, int ySize, int routeDistance, int legSize, int numLoops,
                                      int sameFailCount, int failCount, boolean allowDoubleBack, boolean allowSameCoordinates,
                                      boolean allowThroughStart, boolean variableLegLength, String returnType){

        //Check for defaulting value. If default, randomize.
        if(xSize == 0){
            xSize = randomMultipleOfFive(X_MIN,X_MAX);
            logger.debug("Random xSize:"+xSize);
        }
        if(ySize == 0){
            ySize = randomMultipleOfFive(Y_MIN,Y_MAX);
            logger.debug("Random ySize:"+ySize);
        }
        if(routeDistance == 0){
            routeDistance = randomMultipleOfFive(xSize,ySize);
            logger.debug("Random routeDistance:"+routeDistance);
        }

        List<Integer> commonFactors = getFactors(routeDistance);
        if(legSize == 0){
            legSize = commonFactors.get(randomInt(1, commonFactors.size()-1));
            logger.debug("Random legSize:"+legSize);
        }

        //Setup loop generator
        loopGenerator = new LoopGenerator();

        loopGenerator.setxSize(xSize);
        loopGenerator.setySize(ySize);
        loopGenerator.setRouteDistance(routeDistance);
        loopGenerator.setLegSize(legSize);
        loopGenerator.setNumLoops(numLoops);
        loopGenerator.setSameFailCount(sameFailCount);
        loopGenerator.setFailCount(failCount);
        loopGenerator.setAllowDoubleBack(allowDoubleBack);
        loopGenerator.setAllowSameCoordinates(allowSameCoordinates);
        loopGenerator.setAllowThroughStart(allowThroughStart);
        loopGenerator.setVariableLegSize(variableLegLength);

        logger.debug("xSize:"+xSize);
        logger.debug("ySize:"+ySize);
        logger.debug("routeDistance:"+routeDistance);
        logger.debug("legSize:"+legSize);
        logger.debug("numLoops:"+numLoops);
        logger.debug("sameFailCount:"+sameFailCount);
        logger.debug("failCount:"+failCount);
        logger.debug("allowDoubleBack:"+allowDoubleBack);
        logger.debug("allowSameCoordinate:"+allowSameCoordinates);
        logger.debug("allowThroughStart:"+allowThroughStart);
        logger.debug("variableLegSize:"+variableLegLength);
        logger.debug("returnType:"+returnType);


        //Generate loops
        loopGenerator.generateLoops();


        String result;

        //Return Type
        if(returnType.equals("JSON")){
            result = convertToJSON(loopGenerator);
        } else if(returnType.equals("HTML")){
            result = convertToHTML(loopGenerator);
        } else {
            result = "Error: Invalid returnType";
        }


        //Return response
        return Response
                .status(200)
                .entity(result).build();

    }



    private String convertToJSON(Object o){
        //Convert loops to JSON string
        try {
            ObjectMapper mapper = new ObjectMapper();

            // Convert object to JSON string
            String JSON = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);

            return JSON;
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (com.fasterxml.jackson.databind.JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String convertToHTML(LoopGenerator loopGenerator){
        //Test loop generator:
        String html = "";
        html += getHTMLInfo();
        html += getHTMLCoordinatesAll(loopGenerator.getLoopAndCoord());
        html += getHTMLGrids(loopGenerator.getLoopAndCoord());
        return html;
    }





    public String getHTMLInfo(){
        //Loop Info
        String html = "";
        html += "<div>";
        html += "<h2>LOOPS INFO</h2>";
        html += "<table>";
        html += "<tr><th>Option</th><th>Value</th></tr>";
        html += "<tr><td>X Size:</td>" + "<td>" + loopGenerator.getxSize() + "</td></tr>";
        html += "<tr><td>Y Size:</td>" + "<td>" + loopGenerator.getySize() + "</td></tr>";
        html += "<tr><td>Route Distance:</td>" + "<td>" + loopGenerator.getRouteDistance() + "</td></tr>";
        html += "<tr><td>Leg Size:</td>" + "<td>" + loopGenerator.getLegSize() + "</td></tr>";
        html += "<tr><td>Number of Loops:</td>" + "<td>" + loopGenerator.getNumLoops() + "</td></tr>";
        html += "<tr><td>Same Fail Count:</td>" + "<td>" + loopGenerator.getSameFailCount() + "</td></tr>";
        html += "<tr><td>Fail Count:</td>" + "<td>" + loopGenerator.getFailCount() + "</td></tr>";
        html += "<tr><td>Allow Double Back?</td>" + "<td>" + loopGenerator.getAllowDoubleBack() + "</td></tr>";
        html += "<tr><td>Allow Same Coordinates?</td>" + "<td>" + loopGenerator.getAllowSameCoordinates() + "</td></tr>";
        //html += "<tr><td>Allow Through Start?</td>" + "<td>" + loopGenerator.getAllowThroughStart() + "</td></tr>";
        html += "<tr><td>Variable Number of Legs?</td>" + "<td>" + loopGenerator.getVariableNumLegs() + "</td></tr>";
        html += "</table>";
        html += "</div>";
        return html;
    }

    public String getHTMLCoordinatesAll(Map<LoopsObj, List<CoordinateObj>> loopsAndCoords){
        String html="";
        //Coordinates
        html += "<div>";
        html += "<h2>Coordinates</h2>";
        for(LoopsObj l: loopsAndCoords.keySet()){
            html += l.getLoopId() + " " + getHTMLCoordinates(l, loopsAndCoords);
        }
        html += "</div>";
        return html;
    }

    public String getHTMLCoordinates(LoopsObj l, Map<LoopsObj, List<CoordinateObj>> lAndC){
        String html = "";
        html += "<table>";
        for(CoordinateObj c : lAndC.get(l)){
            html += "("+c.getxCoord()+","+c.getyCoord()+")";
        }
        html += "</td></tr>";
        html += "</table>";
        return html;
    }

    public String getHTMLGrids(Map<LoopsObj, List<CoordinateObj>> loopsAndCoords){
        String html="";
        String[][] grid;

        //Coordinates
        html += "<div>";
        html += "<style>" +
                "td.grid{height: 5px; width: 15px; text-align: center; vertical-align: center}" +
                "table.grid{border-collapse: collapse;}" +
                "</style>";
        html += "<h2>Grids</h2>";

        int i = 1;
        for(LoopsObj l: loopsAndCoords.keySet()){
            html += getHTMLCoordinates(l, loopsAndCoords);

            grid = getHTMLGridForLoop(l);

            html += "<table class='grid'>";
            for(int y = 0; y < loopGenerator.getySize(); y++){
                html += "<tr>";
                for(int x = 0; x < loopGenerator.getxSize(); x++){
                    html += ("<td class='grid'>" + grid[x][y] + "</td>");
                }
                html += "</tr>";
            }
            html += "</table>";

            i++;
        }

        html += "</table>";
        return html;
    }

    public String[][] getHTMLGridForLoop(LoopsObj loop){

        int xSize = loopGenerator.getxSize();
        int ySize = loopGenerator.getySize();

        //Set up initial grid
        String[][] routeGrid = new String[xSize][ySize];
        for(int y = 0; y < ySize; y++){
            for(int x = 0; x < xSize; x++){
                routeGrid[x][y] = ".";
            }
        }

        //Add in coordinate points
        List<CoordinateObj> coordinates = loopGenerator.getLoopAndCoord().get(loop);
        for(int i = 0; i<coordinates.size(); i++) {
            routeGrid[coordinates.get(i).getxCoord()][coordinates.get(i).getyCoord()] = String.valueOf(coordinates.get(i).getPosition());
        }
        return routeGrid;
    }



    /**
     * Return a random multiple of 5 between the min and max
     * @param min
     * @param max
     * @return
     */
    public int randomMultipleOfFive(int min, int max) {
        int randomNum = ((min + (int)(Math.random() * ((max - min) + 1)))/5)*5;
        return randomNum;
    }

    public int randomInt(int min, int max) {
        int randomNum = (min + (int)(Math.random() * ((max - min) + 1)));
        return randomNum;
    }

    public List<Integer> getFactors(int a){
        List<Integer> commonFactors = new ArrayList<Integer>();
        for(int i = 1; i<a; i++){
            if((double)a/i == a/i){
                commonFactors.add(i);
                logger.debug("Common Factor:"+i);
            }
        }
        return commonFactors;
    }


}