
    function bg_change(param, color){
    switch (color){
    case "white":
    document.getElementById(param).style.backgroundColor = "white";
    break;
    case "black":
    document.getElementById(param).style.backgroundColor = "black";
}
}

    document.addEventListener("keydown", function (event){
    switch (event.code){
    case "KeyA":
    bg_change("a","black");
    let A = new Audio("A.mp3");
    A.play();
    document.addEventListener("keyup", function(){bg_change("a","white");});
    break;
    case "KeyS":
    let S = new Audio("S.mp3");
    S.play();
    bg_change("s","black");
    document.addEventListener("keyup", function(){bg_change("s","white");});
    break;
    case "KeyD":
    let D = new Audio("D.mp3");
    D.play();
    bg_change("d","black");
    document.addEventListener("keyup", function(){bg_change("d","white");});
    break;
    case "KeyF":
    let F = new Audio("F.mp3");
    F.play();
    bg_change("f","black");
    document.addEventListener("keyup", function(){bg_change("f","white");});
    break;
    case "KeyG":
    let G = new Audio("G.mp3");
    G.play();
    bg_change("g","black");
    document.addEventListener("keyup", function(){bg_change("g","white");});
    break;
    case "KeyH":
    let H = new Audio("H.mp3");
    H.play();
    bg_change("h","black");
    document.addEventListener("keyup", function(){bg_change("h","white");});
    break;
    case "KeyJ":
    let J = new Audio("J.mp3");
    J.play();
    bg_change("j","black");
    document.addEventListener("keyup", function(){bg_change("j","white");});
    break;
    case "KeyW":
    let W = new Audio("W.mp3");
    W.play();
    bg_change("W","white");
    document.addEventListener("keyup", function(){bg_change("W","black");});
    break;
    case "KeyE":
    let E = new Audio("E.mp3");
    E.play();
    bg_change("E","white");
    document.addEventListener("keyup", function(){bg_change("E","black");});
    break;

    case "KeyT":
    let T = new Audio("T.mp3");
    T.play();
    bg_change("T","white");
    document.addEventListener("keyup", function(){bg_change("T","black");});
    break;
    case "KeyY":
    let Y = new Audio("Y.mp3");
    Y.play();
    bg_change("Y","white");
    document.addEventListener("keyup", function(){bg_change("Y","black");});
    break;
    case "KeyU":
    let U = new Audio("U.mp3");
    U.play();
    bg_change("U","white");
    document.addEventListener("keyup", function(){bg_change("U","black");});
    break;
    default:
    console.log("Press A,S,D,F,G,H or J");

}
})