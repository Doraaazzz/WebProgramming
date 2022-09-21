<?php

function validate_timezone($timezone) {
	return isset($timezone);
}

// проверка валидности значения: является ли числом, отправлена ли переменная и входит ли в область значений
function valid_value ($value, $min_value, $max_value){
    return isset($value) && is_numeric($value) &&($value >= $min_value) && ($value <= $max_value);
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

$x = 0+$_GET["coordinate_x"];
$y = 0+$_GET["coordinate_y"];
$r = 0+$_GET["coordinate_r"];
$timezone= 0+$_GET["timezone"];


if (valid_value($x,-2,2) && valid_value($y,-3,5) && valid_value($r,1,3) && validate_timezone($timezone)){
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


echo $current_time;
?>