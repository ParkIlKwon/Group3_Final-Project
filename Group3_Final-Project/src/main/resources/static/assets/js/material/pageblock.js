history.pushState(null, null, location.href); //현재 페이지 고정적으로 주고 
window.onpopstate = function(event) { //뒤로가기 키를 누를시 .
    history.go(1); //현재 페이지에 머물게 함
};