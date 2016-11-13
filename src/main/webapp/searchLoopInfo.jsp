<%--
  Created by IntelliJ IDEA.
  User: Tim
  Date: 11/12/2016
  Time: 1:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search Loop Info</title>
</head>
<body>

<form method="post" action="/service/loops/searchWithLoopInfo">
    <table>
        <tr>
            <td>X Size</td>
            <td><input type="text" id="xSizeInput" name="x_size"/></td>
        </tr>

        <tr>
            <td>Y Size</td>
            <td><input type="text" id="ySizeInput" name="y_size"/></td>
        </tr>

        <tr>
            <td>Number Of Loops</td>
            <td><input type="text" id="numLoopsInput" name="num_loops"/></td>
        </tr>

        <tr>
            <td>Fail Count</td>
            <td><input type="text" id="failCOuntInput" name="fail_count"/></td>
        </tr>

        <tr>
            <td>Allow Double Back</td>
            <td>
                <label for="allowDoubleBackInputYes">Yes</label>
                <input type="radio" name="allow_double_back" id="allowDoubleBackInputYes" value="true"/>
                <label for="allowDoubleBackInputNo">No</label>
                <input type="radio" name="allow_double_back" id="allowDoubleBackInputNo" value="false" checked/>

            </td>
        </tr>

        <tr>
            <td>Allow Same Coordinates</td>
            <td>
                <label for="allowSameCoordInputYes">Yes</label>
                <input type="radio" name="allow_same_coordinates" id="allowSameCoordInputYes" value="true"/>
                <label for="allowSameCoordInputNo">No</label>
                <input type="radio" name="allow_same_coordinates" id="allowSameCoordInputNo" value="false" checked/>
            </td>
        </tr>

        <tr>
            <td>Allow Through Start</td>
            <td><label for="allow_through_startYes">Yes</label>
                <input type="radio" name="allow_through_start" id="allow_through_startYes" value="true"/>
                <label for="allow_through_startNo">No</label>
                <input type="radio" name="allow_through_start" id="allow_through_startNo" value="false" checked/>
            </td>
        </tr>

        <tr>
            <td>Variable Leg Size</td>
            <td><label for="variabelYes">Yes</label>
                <input type="radio" name="variable_leg_size" id="variabelYes" value="true"/>
                <label for="variableNo">No</label>
                <input type="radio" name="variable_leg_size" id="variableNo" value="false" checked/>
            </td>
        </tr>

    </table>
</form>

</body>
</html>
