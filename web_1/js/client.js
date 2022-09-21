
var form= document.querySelector('.form');
var validatedButton = document.querySelector('.submitButton');
var x_coordinate=document.querySelector('#select');
var y_coordinate=document.querySelector('.y');
var r_coordinate=document.querySelector('.show_r');
var r_block=document.querySelector('r_value blocks_of_data');


function isNumber(s){
  var n = parseFloat(s.replace(',','.'));
  return !isNaN(n) && isFinite(n);
}


function checkSelectBox(select){
    if (select.value) {
    return true;
    }
    alert("Выберете значение X")
    return false;
    
}

// проверка значения в поле на попадание в заданный диапазон
function validateField(coordinate,min,max){
  if(coordinate.value){
      coordinate.value = coordinate.value.replace(',','.');
      if(coordinate.value<min || coordinate.value>max || !isNumber(coordinate.value)){
         alert("Укажите значении координаты Y числом в интервале [-5;3]")           
          return false;
      }
      else{            
          return true;              
      }
  }
  alert("Поле Y не может быть пустым")
  return false; 
}

function R(arg) {
    var number = document.querySelector('.show_r');
    number.innerHTML = arg;
    document.getElementById('show_r').value = arg;
}

function validR(r) {
    if (r.value){
        return true;
    }
    alert("Выберете значение R")
    return false;
}


// фунция для повторной проверки, что поля заполнены верно, чтобы передать php скрипту
function validateAll(){
  return checkSelectBox(x_coordinate)&&validateField(y_coordinate,-3,5) && validR(r_coordinate);
}  

$(document).ready(function(){
    form.addEventListener("submit", function(event) {
        event.preventDefault();
        console.log("Got data for check!" );
        console.log('x: ', x_coordinate.value);
        console.log('y: ', y_coordinate.value);
        console.log('R: ', r_coordinate.value);
        console.log('time', new Date().getTimezoneOffset());
        if(!validateAll()){
            console.log("get canceled")
            return
          }
          console.log("data sending...")
          $.ajax({
            url: 'php/script.php',
            method: "GET",
            data: {
            "coordinate_x": x_coordinate.value,
            "coordinate_y":y_coordinate.value,
            "coordinate_r":r_coordinate.value,
            "timezone": new Date().getTimezoneOffset(),
            },
            dataType: 'html',
            success: function(data){
              console.log(data);
              $(".content").html(data);
              $(".submitButton").attr("disabled", false);	
            },
            error: function(error){
              console.log(error);
              $(".submitButton").attr("disabled", false);	
            },
      });
    })
})

$(".reset").on("click",function(){
  $.ajax({
    url: 'php/clear.php',
    method: "GET",
    dataType: "html",
    success: function(data){
      console.log(data);
      $(".content").html(data);
    },
    error: function(error){
      console.log(error);	
    },
  })
})
