<?php

// проверка валидности значения: является ли числом, отправлена ли переменная и входит ли в область значений
function valid_value_x ($value, $min_value, $max_value){
	$x_values = array('-2', '-1.5', '-1', '-0.5', '0', '0.5', '1', '1.5', '2');
    return isset($value) && is_numeric($value) &&($value >= $min_value) && ($value <= $max_value) && in_array($value, $x_values) ;
}

function valid_value_R ($value, $min_value, $max_value){
	$r_values =array ('1', '1.5', '2', '2.5' ,'3');
    return isset($value) && is_numeric($value) &&($value >= $min_value) && ($value <= $max_value) && in_array($value, $r_values) ;
}

function valid_value_y ($value, $min_value, $max_value){
	$x_values = array('-2', '-1.5', '-1', '-0.5', '0', '0.5', '1', '1.5', '2');
    return isset($value) && is_numeric($value) &&($value >= $min_value) && ($value <= $max_value)  ;
}


// попадание в квадрат
function check_square ($x, $y, $r){
    return ((0 <= $x) && ($x <= $r/2) && (0 <= $y) && ($y <= $r));
}

// попадание в треугольник
function check_triangle ($x, $y, $r){
    return (($x >= 0) && (0 >= $y) && ($y >= $x/2 - $r/2));
}

// попадание в четверть круга
function check_circle ($x, $y, $r){
    return ((0 <= $x) && ($y >= 0) && (sqrt($x*$x+$y*$y)<=$r/2));
}



// $time = microtime(true) - $_SERVER["REQUEST_TIME_FLOAT"];


session_start();
if (!isset($_SESSION['data'])){
    $_SESSION['data'] = array();
}

$x = $_GET["coordinate_x"];
$y = $_GET["coordinate_y"];
$r = $_GET["coordinate_r"];
$timezone= $_GET["timezone"];


if (valid_value_x($x,-2,2) && valid_value_y($y,-3,5) && valid_value_r($r,1,3)){
    $result = (check_square($x, $y, $r) || check_triangle($x, $y, $r) || check_circle($x, $y, $r)) ? "Point's in the area" : "Point isn't in the area";
    $execution_time = microtime(true) - $_SERVER["REQUEST_TIME_FLOAT"];
    $current_time=date ("H:i:s");
	if ($timezone > 0) {
		$timezone_info = " (GMT " . $timezone / -60 . ")";
	} else {
		$timezone_info = " (GMT +" . $timezone / -60 . ")";
	}
	
    $res_data = array("x"=>$x, "y"=>$y, "r"=>$r, "result"=>$result, "current_time"=>$current_time .$timezone_info, "execution_time"=>$execution_time );
	array_push($_SESSION['data'], $res_data); 
} else {
	http_response_code(400);
	if (!valid_value_x($x,-2,2)){
		echo "x is invalid value\n";
	}
	if (!valid_value_y($y,-3,5)){
		echo "y is invalid value\n";
	}
	if (!valid_value_r($r,1,3)){
		echo "r is invalid value\n";
	}
}



foreach ($_SESSION['data'] as $element){
	echo "<tr>";
	echo "<td>" . $element['x'] . "</td>";
	echo "<td>" . $element['y'] . "</td>";
	echo "<td>" . $element['r'] . "</td>";
	echo "<td>" . $element['result']  . "</td>";
	echo "<td>" . $element['current_time']  . "</td>";
	echo "<td>" . $element['execution_time'] . "</td>";
	echo "</tr>";
}

?>