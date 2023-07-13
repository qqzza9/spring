<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../includes/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>

<link rel="stylesheet" href="/resources/css/login.css">
<script type="text/javascript">

$(function(){
	$(".login_button").on("click", function(e) {
		e.preventDefault();
		if(isValid()) {
			$("form").submit();
		}
	});
	
	function isValid(){
		let userid = $("#userid").val();
		let userpw = $("#userpw").val();
		
		$("#error_userid").text("");
		$("#error_userpw").text("");
		
		if(userid === "") {
			$("#error_userid").text("이메일이 올바르지 않습니다.");
			$("#userid").focus();
			return false;
		}
		if(userpw === "") {
			$("#error_userpw").text("비밀번호를 입력해 주세요.");
			$("#userpw").focus();
			return false;
		}
		return true;
		};
		
		let error ='<c:out value="${error}"/>';
		console.log(error);
		if(error === ""){
			return;
		} if(error === "nonuser") {
			$("#userid").focus();
		} else{
			$("#userpw").focus();
		}
		let msg=(error === "nonuser") ? "존재하지 않는 email입니다." : "비밀번호가 일치하지 않습니다.";
				alert(msg);
});
</script>
</head>
<body>
 <div id="wrap">
 	<div id ="logo">
	 	<h1>로그인</h1>
 	</div>
 	<div id = "content">
 	
	<div class="wrapped_login">
	
        <div class="header"  >
            <img src="/resources/image/loginLogo.png" alt="#">
        </div>

		<form action="/member/login" class="login_form" method="post">
			<div class="form_id">
				<label for ="username"></label>
				<input type="email" class="id_control" id ="userid" name ="userid" placeholder="E-mail을 입력하세요."
					value="${memberVO.userid}"/>
				<div class="error" id="error_userid"></div>
			</div>
			<div class="form_pw">
				<label for ="password"></label>
				<input type="password" class="pw_control" id ="userpw" name="userpw" placeholder="비밀번호를 입력하세요."/>
                <!-- <button onclick="myFunc()" id ="btn" >비밀번호 표시</button> <br><br> -->
				<div class="error" id="error_userpw"></div>
			</div>
			
			<div class="form_login_button">
				<button class="login_button" type="submit">로그인</button>
			</div>
			
		</form>

        <div class="find">
            <a href="#">아이디 찾기 |</a>
            <a href="#">비밀번호 재발급 |</a>
            <a href="/member/signup">회원가입</a>
        </div>
        <div class="content">
	        	<span>▶ 로그인이 안되시는 경우 한/영키와 Caps Lock키가 눌러져 있지 않은지 확인하세요.</span><br>
	            <span>▶ 계속해서 로그인이 안되는 경우 관리자에게 문의하시기 바랍니다.</span>
        </div>
	</div>
	</div>
</div>
<%@include file ="../includes/footer.jsp" %>
	
</body>
</html>