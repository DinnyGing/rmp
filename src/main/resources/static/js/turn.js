function turnLightCloset(){
    turn = document.getElementById("turnLightCloset").innerHTML;
    if(turn === "Off"){
        document.getElementById("turnLightCloset").innerHTML = "On";
        document.getElementById("closet").style.opacity = 0;
    }
    else {
        document.getElementById("turnLightCloset").innerHTML = "Off";
        document.getElementById("closet").style.opacity = 1;
    }
    $('#lightCloset').val(parseInt($("#lightCloset").val()) + 1);
    if (parseInt($("#lightCloset").val()) == 2){
        $("#lightCloset").val(document.getElementById("turnLightCloset").innerHTML);
        document.getElementById("buttonLightCloset").click();
    }
}
function turnLightLivingRoom(){
    turn = document.getElementById("turnLightLivingRoom").innerHTML;
    if(turn === "Off"){
        document.getElementById("turnLightLivingRoom").innerHTML = "On";
        document.getElementById("livingRoom").style.opacity = 0;
    }
    else {
        document.getElementById("turnLightLivingRoom").innerHTML = "Off";
        document.getElementById("livingRoom").style.opacity = 1;
    }
    $('#lightLivingRoom').val(parseInt($("#lightLivingRoom").val()) + 1);
    if (parseInt($("#lightLivingRoom").val()) == 2){
        $("#lightLivingRoom").val(document.getElementById("turnLightLivingRoom").innerHTML);
        document.getElementById("buttonLightLivingRoom").click();
    }
}
function turnLightKitchen(){
    turn = document.getElementById("turnLightKitchen").innerHTML;
    if(turn === "Off"){
        document.getElementById("turnLightKitchen").innerHTML = "On";
        document.getElementById("kitchen").style.opacity = 0;
    }
    else {
        document.getElementById("turnLightKitchen").innerHTML = "Off";
        document.getElementById("kitchen").style.opacity = 1;
    }
    $('#lightKitchen').val(parseInt($("#lightKitchen").val()) + 1);
    if (parseInt($("#lightKitchen").val()) == 2){
        $("#lightKitchen").val(document.getElementById("turnLightKitchen").innerHTML);
        document.getElementById("buttonLightKitchen").click();
    }
}
function turnLightBathRoom(){
    turn = document.getElementById("turnLightBathRoom").innerHTML;
    if(turn === "Off"){
        document.getElementById("turnLightBathRoom").innerHTML = "On";
        document.getElementById("bathRoom").style.opacity = 0;
    }
    else {
        document.getElementById("turnLightBathRoom").innerHTML = "Off";
        document.getElementById("bathRoom").style.opacity = 1;
    }
    $('#lightBathRoom').val(parseInt($("#lightBathRoom").val()) + 1);
    if (parseInt($("#lightBathRoom").val()) == 2){
        $("#lightBathRoom").val(document.getElementById("turnLightBathRoom").innerHTML);
        document.getElementById("buttonLightBathRoom").click();
    }
}

function turnLightBedRoom(){
    light = document.getElementById("scrollLightBedRoom")
    lightOpacity = Math.round((1 - light.value / 100)*10)/10;
    $("#lightBedRoom").val(1 - lightOpacity);
    document.getElementById("buttonLightBedRoom").click();
}
function turnLightKidRoom(){
    light = document.getElementById("scrollLightKidRoom")
    lightOpacity = Math.round((1 - light.value / 100)*10)/10;
    $("#lightKidRoom").val(1 - lightOpacity);
    document.getElementById("buttonLightKidRoom").click();
}
