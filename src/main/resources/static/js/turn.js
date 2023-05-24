function turnLightLivingRoom(){
    turn = document.getElementById("turnLightLivingRoom").innerHTML;
    alert(turn)
    if(turn === "Off"){
        document.getElementById("turnLightLivingRoom").innerHTML = "On";
    }
    else {
        document.getElementById("turnLightLivingRoom").innerHTML = "Off";
    }


    // livingRoom = document.getElementById("livingRoom");
    // livingRoom.style.opacity = 1;
}