<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="./includes/header.jsp" %>

<html>
<head>
<title>Home</title>
<link rel="stylesheet" href="/resources/css/home.css">
<link rel="stylesheet" href="/resources/css/slide.css">
<link rel="stylesheet" href="/resources/css/contents.css">
<link rel="stylesheet" href="/resources/css/jquery.bxslider.css">
<link rel="icon" href="/favicon.ico" type="image/x-icon">

<!-- <script src="/resources/js/jquery.cookie.min.js" ></script>  -->

<script src="/resources/js/jquery.bxslider.min.js" defer></script>
<script src="/resources/js/home_calendar.js" ></script>
<script src="/resources/js/app.js"></script>

<script type="text/javascript">
$(function(){
	let msg ='<c:out value="${msg}"/>';
	if(msg === ""){
		return;
	} 
	let txt;
	if(msg === "logout") {
		txt="로그아웃 되었습니다.";
	}
	alert(txt);
});
</script>
</head>

<!-- <div class="wrapper_main">
	<p><h1>Spring project 메인 화면입니다.</h1>
</div> -->
<body>
   <!--  <div id="popup">
        <div id="popup-img">
            <img src="./images/popup-img.jpg">
        </div>
        <div id="btn-area">
            <input type="checkbox" id="popup-chk" style="width:25px;height:25px;">
            <span  style="font-size: 20px;"> 7일동안 보지 않기</span>
        </div>
    </div>    
         -->
    <div id ="wrap">
        
       

        <div id="slide">
           <div id="slide_contents">
            	<img src="/resources/image/main.jpg" alt="슬라이드1">
                <img src="/resources/image/main.jpg" alt="슬라이드2">
                <img src="/resources/image/main.jpg" alt="슬라이드3">
            </div> 
        </div>

        <div id="content">
            <div id="cont1">
                <ul class="shortcut">
                    <li class="svc1">
                        <a href="#">
                            <i class="ico"></i>
                            "희망도서신청"
                        </a>
                    </li>
                    <li class="svc2">
                        <a href="#">
                            <i class="ico"></i>
                            "전자책"
                        </a>
                    </li>
                    <li class="svc3">
                        <a href="#">
                            <i class="ico"></i>
                            "오디오북"
                        </a>
                    </li>
                    <li class="svc4">
                        <a href="#">
                            <i class="ico"></i>
                            "전자잡지"
                        </a>
                    </li>
                    <li class="svc5">
                        <a href="#">
                            <i class="ico"></i>
                            "스마트도서관"
                        </a>
                    </li>
                    <li class="svc6">
                        <a href="#">
                            <i class="ico"></i>
                            "열람실좌석현황"
                        </a>
                    </li>
                    <li class="svc7">
                        <a href="#">
                            <i class="ico"></i>
                            "문화행사"
                        </a>
                    </li>
                    <li class="svc8">
                        <a href="#">
                            <i class="ico"></i>
                            "수강신청"
                        </a>
                    </li>
                    <li class="svc9">
                        <a href="#">
                            <i class="ico"></i>
                            "북스타트사업"
                        </a>
                    </li>
                    <li class="svc10">
                        <a href="#">
                            <i class="ico"></i>
                            "독서동아리"
                        </a>
                    </li>
                </ul>
            </div> 
            <div id="cont2">
                <div id ="cont2-1">
                    <div id ="cont2-1-1">
                        <div id="time">
                            <div id ="time-btn">
                                <a href="#">
                                    도서관 이용시간 안내
                                </a>
                            </div>
                            <div id ="tab-time">
                                <span>
                                    유아 및 어린이가족열람실<br>
                                    -하절기(3월~10월) 9:00~20:00<br>
                                    -동절기(11월~2월) 9:00~18:00<br>
                                    -주말(연중) 9:00~18:00<br>
                                     일반열람식(평일 및 주말) 09:00~20:00<br>
                                     *1,3주 월요일 및 명절연휴 전체휴관<br>
                                     *매주 월요일 및 법정공휴일 자료실 휴실
                                </span>
                               
                            </div>
                        </div>
                    </div>
                    <div id ="cont2-1-2">
                    	<div class="title">이용문의 안내</div>
                    	<div class="telimg">
							<img src="/resources/image/telephone.png">
						</div>
						<div class="tel">
							<p>
								"Tel:031-729-4770"
								<br>
								"Fax:031-729-4779"
							</p>
						</div>
                    </div>
                    <div id ="cont2-1-3">
                   		<div class="locaption">오시는길</div>
                    	<div class="locaptionimg">
							<img src="/resources/image/locaption.png">
						</div>
                    </div>
                    <div id ="cont2-1-4">
                   		<div class="faq">FAQ</div>
                    	<div class="faqimg">
							<img src="/resources/image/faq.png">
						</div>
                    </div> 
                </div>
                <div id ="cont2-2">
                    <div id="tab">
                        <div id ="tab-btn">
                            <a href="#">
                                공지사항
                            </a>
                        </div>
                        <div id ="tab-contents">
                            <div id ="notice">
                                <ul>
                                    <li>
                                        <a href="#">
                                            <span>동화팩 읽어주기 안내</span>
                                            <span>2023.12.01</span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            <span>무지개도서관 휴관일 안내</span>
                                            <span>2023.11.01</span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            <span>만들기 체험행사</span>
                                            <span>2023.10.01</span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            <span>문화가 있는 날 행사 안내</span>
                                            <span>2023.09.01</span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            <span>문화의 날 행사 안내</span>
                                            <span>2023.08.01</span>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div> 
            <div id="cont3">
                <div id="book">
                    <div class ="books">
                        <span>추천도서</span>
                    </div>
                    
                  
                    <div class="bookImg">
                        <span class="cover">
                            <a href="#">
                                <img src="/resources/image/book1.jpg" alt="베르베르씨"> 
                            </a>
                        </span>
                        <span class="cover">
                            <a href="#">
                                <img src="/resources/image/book2.jpg" alt="나는 왜 이렇게 웃긴가"> 
                            </a>
                        </span>
                        <span class="cover">
                            <a href="#">
                                <img src="/resources/image/book3.jpg" alt="초초 난난"> 
                            </a>
                        </span>
                        <span class="cover">
                            <a href="#">
                                <img src="/resources/image/book4.jpg" alt="세이노의 가르침"> 
                            </a>
                        </span>
                        <span class="cover">
                            <a href="#">
                                <img src="/resources/image/book5.jpg" alt="원피스"> 
                            </a>
                        </span>
                        <span class="cover">
                            <a href="#">
                                <img src="/resources/image/book6.jpg" alt="불편한 편의점"> 
                            </a>
                        </span>
                        <span class="cover">
                            <a href="#">
                                <img src="/resources/image/book7.jpg" alt="우습지 않다"> 
                            </a>
                        </span>
                        <span class="cover">
                            <a href="#">
                                <img src="/resources/image/book8.jpg" alt="역행자"> 
                            </a>
                        </span>
                        <span class="cover">
                            <a href="#">
                                <img src="/resources/image/book9.jpg" alt="채소 과일식"> 
                            </a>
                        </span>
                        <span class="cover">
                            <a href="#">
                                <img src="/resources/image/book10.jpg" alt="미래과거시제"> 
                            </a>
                        </span>
                    </div> 
                    
                </div>
                <div id="cont3-2">
                    <table id="calendar">
                        <thead>
                            <tr>
                                <th><input name="preMon" type="button" value="<"></th>
                                <th colspan="5" class="year_mon"></th>
                                <th><input name="nextMon" type="button" value=">"></th>
                            </tr>
                            <tr>
                                <th>일</th>
                                <th>월</th>
                                <th>화</th>
                                <th>수</th>
                                <th>목</th>
                                <th>금</th>
                                <th>토</th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div> 
            
        </div> 
       
    </div>
<%@include file ="./includes/footer.jsp" %>

</body>

</html>
