<html>
<body>

<form action="test/loops/generateForm" method="post">
    <table>
        <!-- x size-->
        <tr>
            <td>X Size: </td>
            <td><input type="number" name="xSize" id="xSize" /> </td>
        </tr>

        <!-- y size-->
        <tr>
            <td>Y Size: </td>
            <td><input type="number" name="ySize" id="ySize" /></td>
        </tr>

        <!-- route distance-->
        <tr>
            <td>Route Distance: </td>
            <td><input type="number" name="routeDistance" id="routeDistance" /></td>
        </tr>

        <!-- leg size-->
        <tr>
            <td>Leg Size: </td>
            <td><input type="number" name="legSize" id="legSize" /></td>
        </tr>

        <!-- num loops-->
        <tr>
            <td>Number Of Loops: </td>
            <td><input type="number" name="numLoops" id="numLoops" /></td>
        </tr>

        <!-- same fail count-->
        <tr>
            <td>Same Fail Count: </td>
            <td><input type="number" name="smeFailCount" id="smeFailCount" /></td>
        </tr>

        <!-- fail count-->
        <tr>
            <td>Fail Count: </td>
            <td><input type="number" name="failCount" id="failCount" /></td>
        </tr>

        <!-- allow double back-->
        <tr>
            <td>Allow Double Back: </td>
            <td><input type="checkbox" name="allowDoubleBack" id="allowDoubleBack" /></td>
        </tr>

        <!-- allow same coordinates-->
        <tr>
            <td>Allow Same Coordinates: </td>
            <td><input type="checkbox" name="allowSameCoordinates" id="allowSameCoordinates" /></td>
        </tr>

        <!-- allow through start-->
        <tr>
            <td>Allow Through Start: </td>
            <td><input type="checkbox" name="allowThroughStart" id="allowThroughStart" /></td>
        </tr>

        <!--Variable leg size-->
        <tr>
            <td>Variable Leg Size: </td>
            <td><input type="checkbox" name="variableLegSize" id="variableLegSize" /></td>
        </tr>

        <!-- return type-->
        <tr>
            <td>Return Type: </td>
            <td><input type="text" name="returnType" id="returnType" /></td>
        </tr>
    </table>
    <button type="submit" value="Submit Form">Submit</button>

</form>
<br/>
<form action="test/loops/getWithID" method="post">
    <!-- adding search for old loops once database functionality is added-->
    <h2>Find Loop By Id(in progress)</h2>
    ID: <input type="number" name="Id" />
    <br/>
    <button type="submit" value="Submit Form">Submit</button>
</form>
</body>
</html>
