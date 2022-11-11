<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:useBean id="points" class="com.dora.weblab2.session.PointsTable" scope="session" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/pagestyle.css">
    <link rel="shortcut icon" type="image/png" href="img/favicon.ico"/>
    <link rel="apple-touch-icon" href="img/favicon.ico">
    <title>Web 2</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/jsxgraph/distrib/jsxgraphcore.js" type="text/javascript" charset="UTF-8"></script>
</head>
<body>
    <header class="header">
        <div class="inf_block_1">Voronina Darya Sergeevna
            <div class="inf_block_2">
                group: P32311<br>
                number: 3004
            </div>
        </div>
        <div>
            <img src="img/logo.png" class="logo" alt="y(.)">
        </div>
    </header>
    <div class="main_content">
        <div class="graph" id="graph">
            <div id="jxgbox" class="jxgbox"></div>
        </div>
        <div class="data">
            <div class="reset">
                <button class="reset_button" type="reset" value="RESET">Reset</button>
            </div>
            <form class="form" id="form_for_sending" action="ControllerServlet" method="get" >
                <p class="title_of_form">Enter values to calculate</p>
                <div class="Error_text">
                    <span class="tip" id="X_error"></span>
                    <span class="tip" id="Y_error"></span>
                    <span class="tip" id="R_error"></span>
                </div>
                <p class="blocks_of_data">
                    Input x value:
                    <input type="text" name="coordinate_x" id="x_value" class="x" placeholder="from -5 to 5" maxlength="10">
                </p>
                <p class="y_value blocks_of_data"> Choose y value:
                    <input type="checkbox" name="coordinate_y" value="-3">
                    <label>-3</label>
                    <input type="checkbox" name="coordinate_y" value="-2">
                    <label>-2</label>
                    <input type="checkbox" name="coordinate_y" value="-1">
                    <label>-1</label>
                    <input type="checkbox" name="coordinate_y" value="0">
                    <label>0</label>
                    <input type="checkbox" name="coordinate_y" value="1">
                    <label>1</label>
                    <input type="checkbox" name="coordinate_y" value="2">
                    <label>2</label>
                    <input type="checkbox" name="coordinate_y" value="3">
                    <label>3</label>
                    <input type="checkbox" name="coordinate_y" value="4">
                    <label>4</label>
                    <input type="checkbox" name="coordinate_y" value="5">
                    <label>5</label>
                </p>
                <p class="blocks_of_data">Choose r value:
                    <select size="0" required id="select" name="coordinate_r">
                        <option disabled>Choose r value</option>
                        <option value="1" class="r">1</option>
                        <option value="1.5" class="r">1.5</option>
                        <option value="2" class="r">2</option>
                        <option value="2.5" class="r">2.5</option>
                        <option value="3" class="r">3</option>
                    </select>
                </p>
                <button class="submitButton" type="submit" value="SUBMIT" >Send</button>
            </form>
        </div>
        <div class="results" >
            <table id="result_table" >
                <thead>
                <th>X</th>
                <th>Y</th>
                <th>R</th>
                <th>Hitting the area</th>
                <th>Current time</th>
                <th>Execution time, ms</th>
                </thead>
                <tbody class="content">
                    <c:forEach var="point" items="${points.getPoints()}" >
                        <tr>
                            <td>${point.x}</td>
                            <td>${point.y}</td>
                            <td>${point.r}</td>
                            <td>${point.hit}</td>
                            <td>${point.date}</td>
                            <td>${point.executionTime}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <footer class="footer">
            ITMO, 2022
        </footer>
    </div>
    <script src="js/graph.js"></script>
    <script src="js/main.js"></script>
</body>
</html>