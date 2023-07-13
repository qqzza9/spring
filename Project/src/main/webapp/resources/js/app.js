$(document).ready(function(){
  slide()
  book()
  popupCheck()
})

function slide(){
  setInterval(slideMove,3000)
}

var slideIndex=0;
function slideMove(){
  slideIndex++
  var curLeft= -1200*slideIndex;
  if(curLeft < -2400) {
    curLeft=0
      slideIndex=0
  }
  $("#slide_contents").animate({"left":curLeft}) 
  

}

function book(){
  $(".bookImg").bxSlider({
      auto:true,
      autoHover:true, // 마우스 가져가면 멈춤
      slideWidth:140,
      slideMargin:15,
      minSlides:5,
      maxSlides:5,
  })
}

function popupCheck(){//함수 선언,정의
    $("#popup-chk").click(function(){
        alert("7일동안 보지 않음")
        //쿠키 생성!
        $.cookie('popup_java','ok',{ //쿠기 name, value
            expires:7,
            path:'/'
        })
        $("#popup").hide()
        //이름이 popup_java 값이 ok이고
        //현재경로에 7일뒤에 만료되는 쿠키를 생성하겠다.

        //name,value,expires,path

        //name,value,domain,expires
      

    })
}