/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var today = new Date();
var day = today.getDate();
var month = today.getMonth() +1;
var year = today.getFullYear();
var sDay = day.toString();
var sMonth = month.toString();
var sYear = year.toString();
var sDate;
var sPeriod;


var leapYear = false;




function dateChange(){
    
    
    switch (document.getElementById("period").selectedIndex){
        case 0:
            sPeriod = "AM ";
            break;
        case 1:
            sPeriod = "PM ";
            break;
        case 2:
            sPeriod = "ALL";
            break;
    }
    
    
    
    if (day <10 && sDay.length !== 2){
        sDay = '0' + sDay;
    }
    if (month <10 && sMonth.length !== 2){
        sMonth = '0' + sMonth;
    }
    sDate = sYear + '-' + sMonth + '-' + sDay;
    
    var numBooked = new Array();
    for (var j = 0;j<bikeType.length;j++){
        numBooked[j] = numBikeType[j];
    }
    
   
    
    
    for (var j =0;j<booking_date.length;j++){
        
        if (booking_date[j] === sDate && (booking_period[j] === sPeriod || booking_period[j] === "ALL" || sPeriod === "ALL" )){

            
            for (var k=0;k<bikeType.length;k++){
                
                if (bikeType[k] === booking_bike_type[j]){
                    
                    numBooked[k]= numBooked[k]-1;
                }
            }      
        }
        
        
    }
    document.getElementById("day").value = sDay;
    document.getElementById("month").value = sMonth;
    document.getElementById("year").value = sYear;
    
    
    for (var j =0;j<bikeType.length;j++){
        if (numBooked[j] > 0){
           document.getElementById("numberOf"+bikeType[j]).innerHTML=numBooked[j]; 
        }else{
           document.getElementById("numberOf"+bikeType[j]).innerHTML=0; 
        }
        
    
    }
    
}

function dateDayChange(){
    
    
    if (checkInput(2,"day")){
        sDay = document.getElementById("day").value;
        day = parseInt(sDay);
    }
    
    if (day === 0){
        document.getElementById("day").value = '1';
        day = 1;
        sDay = '1';
    }
    
    if (month === 1 || month === 3 || month ===5 || month ===7 || month ===8 || month === 10 || month === 12){
        if (day > 31){
            document.getElementById("day").value = '31';
            day = 31;
            sDay = '31';
        }
    }else if (month === 2){
        if (leapYear){            
            if (day > 29){
                document.getElementById("day").value = '29';
                day = 29;
                sDay = '29';
            }                        
        }else{
            if (day > 28){
                document.getElementById("day").value = '28';
                day = 28;
                sDay = '28';
            }  
        }
    }else{
        if (day > 30){
            document.getElementById("day").value = '30';
            day = 30;
            sDay = '30';
        }
    }
    dateChange();
    
    
    
}

function dateMonthChange(){
    
    
    if (checkInput(2,"month")){
        sMonth = document.getElementById("month").value;
        month = parseInt(sMonth);
    }
    
    if (month === 0){
        document.getElementById("month").value = '1';
        month = 1;
        sMonth = '1';
    }
    if (month > 12){
        document.getElementById("month").value = '12';
        month = 12;
        sMonth = '12';
    }
    
    
    
    dateDayChange();
    
}

function dateYearChange(){
      
    if (checkInput(4,"year")){
        sYear = document.getElementById("year").value;
        year = parseInt(sYear);
    }
    
    if (year % 400 === 0){
        leapYear = true;
    }else if (year % 100 ===0){
        leapYear = false;
    }else if (year % 4 ===1){
        leapYear = true;
    }else {
        leapYear = false;
    }
    
    dateDayChange();
}

function checkInput(length,id){
    
    
    var v = document.getElementById(id).value;
    
    if (v.length > length){
        document.getElementById(id).value = '';
        return false;
    }
    
    for (var i = 0;i<v.length;i++){
        if (i>=length){
            document.getElementById(id).value = '';
            return false;
        }
        
        
        if ((v.charAt(i) < '0' || v.charAt(i) > '9') && (v.charAt(i) !== null)){  
            document.getElementById(id).value = '';
            
            return false;
        }        
    }
    return true;
    
    
}

