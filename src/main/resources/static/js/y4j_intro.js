//쿠키 이름, 쿠키 값 조회
var cookieArr = document.cookie.split("; ");

var cnt = 0;
for (i in cookieArr) {
  if (cookieArr[i].split("=")[0] == "y4jPopupPage") {
    cnt++;
  }
}
if (cnt == 0) {
  window.open("popup", "y4jPopupPage", "width=360px, height=325px");
}

// 메뉴바 다운
var menuTableOpenBtn = document.querySelector(".menuTableOpenBtn");
menuTableOpenBtn.onclick = function () {
  document.querySelector(".menuTable-outer").style.height = window.innerHeight + "px";
  document.querySelector(".menuTable").style.height = window.innerHeight + "px";
  document.querySelector(".menuTable-outer").style.opacity = 0.8;
};

// 메뉴바 업
var menuTableCloseBtn = document.querySelector(".menuTableCloseBtn");
menuTableCloseBtn.onclick = function () {
  document.querySelector(".menuTable-outer").style.height = "0px";
  document.querySelector(".menuTable").style.height = "0px";
  document.querySelector(".menuTable-outer").style.opacity = 1;
};
