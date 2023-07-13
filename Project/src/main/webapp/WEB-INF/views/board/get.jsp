<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="../includes/header.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 읽기</title>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="/resources/js/replyService.js" defer></script>

<!-- <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">  -->
<link rel="stylesheet" href="/resources/css/get.css">
<link rel="stylesheet" href="/resources/css/replyBootstrap.css">

<script>
	$(function() {
		const operForm = $("#operForm");
		$("#list_btn").on("click", function(e) {
			operForm.find("#bno").remove();
			operForm.attr("action","/board/list");
			operForm.submit();
		});
		$("#modify_btn").on("click", function(e) {
			operForm.attr("action","/board/modify").submit();
		});
		
		$("#addReplyBtn").on("click", function(){
			$("#reply").val("");
			$("#modalModBtn").hide();
			$("#modalRegisterBtn").show();
			$("#modalCloseBtn").show();
			$(".modal").modal("show");
		});
		//modal
		$("#modalCloseBtn").on("click",function(){
			$(".modal").modal("hide");
		});
		$("#modalRegisterBtn").on("click", function() {
			let reply={reply:$("#reply").val(),
				replyer:$("#replyer").val(),	
				bno:'<c:out value="${board.bno}"/>'
			};
			ReplyService.add(reply, function(result){
				alert(result);
				$(".modal").modal("hide");
				getReplyListWithPaing(pageNum);
				},
				function(error){
					alert(error);
			});
		});
		//reply list
		getReplyList();
		function getReplyList(){
			ReplyService.getList(
				{bno:'<c:out value="${board.bno}"/>'},
				function(list) {
					console.log("list:" +list);
					showReplyList(list);
				},
				function(error){
					alert(error);
			});
		}
		
		function showReplyList(list){
			let str = "";
			let replyUL = $(".reply");
			if(list == null || list.length == 0) {
				replyUL.html(str);
				return;
			}
			for(var i = 0, len = list.length || 0; i<len; i++){
				str +="<li>";
				str +="	<div>";
				str +="		<div class='header'><strong class='primary-font'>"+list[i].replyer+"</strong>";
				str +="			<small class='pull-right text-muted'>"+ReplyService.displayTime(list[i].regdate)+"</small>";
				str +="		</div>";
				str +="		<div data-rno='" + list[i].rno + "' data-replyer='" + list[i].replyer + "'>";
				str +="		 <strong id = 'modify' class='primary-font'>"+list[i].reply+"</strong>";
				if("${auth.userid}" === list[i].replyer) {
				str +="		<button id='remove' type='button' class='close' data-rno='" + list[i].rno+"'>";
				str +="			<span>X</span></button>";
				}
				str +="		</div>";
				str +="	</div>";
				str +="</li>";
			}
			replyUL.html(str);
		}
		
		//modify the reply
		$(".reply").on("click", "li #modify", function(e){
			let replyer = $(this).parent().closest('div').data("replyer");
			let rno = $(this).parent().closest('div').data("rno");
			let auth = "${auth.userid}";
			if(auth !== replyer) {
				return;
			} 
			//alert('modify ' + auth + ' ' + replyer + ' ' + rno);
			ReplyService.get(rno, function(reply) {
				$("#reply").val(reply.reply);
				$("#replyer").val(reply.replyer);
				$(".modal").data("rno", reply.rno);
				$("#modalModBtn").show();
				$("#modalRegisterBtn").hide();
				$(".modal").modal("show");
			});
		});		
		
		$("#modalModBtn").on("click", function(e){
			let reply={reply:$("#reply").val(),
					rno:$(".modal").data("rno")
			};
			ReplyService.update(reply, function(result){
				alert(result);
				$(".modal").modal("hide");
				getReplyListWithPaing(pageNum);
				},
				function(error){
					alert(error);
			});
		});

		$(".reply").on("click", "li #remove", function(e){
			let rno = $(this).parent().closest('div').data("rno");
			alert('remove ' + rno);
			ReplyService.remove(rno, function(result) {
					alert(result);
					getReplyList();
				},
				function(error){
					alert(error);
			});
		});		
		
		//reply list with paging
		let pageNum = 1;
		let replyPageFooter = $(".panel-footer");
		
		getReplyListWithPaging(pageNum);
		function getReplyListWithPaging(pageNum) {
			ReplyService.getListWithpaging(
				{bno:'<c:out value="${board.bno}"/>', page: pageNum},
				function(replyCnt, list) {
					console.log("list: " + list);
					showReplyList(list);
					showReplyPaging(replyCnt);
				},
				function(error){
					alert(error);
			});
		}
		
		function showReplyPaging(replyCnt) {
			let endNum = Math.ceil(pageNum / 10.0) *10;
			let startNum = endNum - 9;
			let prev = startNum != 1;
			let next = false;
			let str = "";
			if(endNum * 10 >=replyCnt){
				endNum = Math.ceil(replyCnt/10.0);
			}
			if(endNum * 10 < replyCnt) {
				next = true;
			}
			
			str += "<ul class='pagination'>";
			if(prev) {
				str+= "<li class='paginate_button'><a href='" + (startNum -1)+"'>Previous</a></li>";
			}
			for(let i =startNum; i <= endNum; i++) {
				let active = pageNum == i? "active_list":"";
				str+="<li class= 'paginate_button" + active+" '><a href='"+i+"'>"+i+"</a></li>";
			}
			if(next) {
				str+= "<li class='paginate_button'><a href='"+(endNum +1)+"'>Next</a></li>";
			}
			str += "</ul></div>";
			console.log(str);
			replyPageFooter.html(str);
 		}
		//paging
		replyPageFooter.on("click", "li a", function(e){
			e.preventDefault();
			console.log("page click");
			let targetPageNum = $(this).attr("href");
			console.log("targetPageNum: " + targetPageNum);
			pageNum = targetPageNum;
			getReplyListWithPaging(pageNum);
		});
		
	
		let bno='<c:out value="${board.bno}"/>';
		$.getJSON("/board/getAttachList/" + bno, function(attachList) {
			console.log(attachList);
			let str="";
			$(attachList).each(function(i, attach){
				if(attach.filetype) {
					let fileCallPath = encodeURIComponent(attach.uploadpath+"\\s_"+attach.uuid+"_"+attach.filename);
					str += "<li data-path='"+attach.uploadpath+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.filename + "'";
					str += " 	data-type='"+attach.filetype+"'>";
					str += " <div>";
					str += "	<span> " +attach.filename+"</span>"; 
					str += "	<img src='/display?filename="+fileCallPath+"'>";
					str += " </div>";
					str += "</li>";
				}else {
					str += "<li data-path='"+attach.uploadpath+"' data-uuid='"+attach.uuid+"'";
					str += " data-filename='"+attach.filename+"' data-type='"+attach.filetype+"'>";
					str += " <div>";
					str += "	<span> " +attach.filename+"</span>"; 
					str += "	<img src='/resources/image/attach.png'>";
					str += " </div>";
					str += "</li>";
				}
			});
			$(".uploadResult ul").html(str);	
		});
	
		//이미지 파일 커짐
		
		function showImage(fileCallPath){
			$(".bigPictureWrapper").css("display","flex").show();
			$(".bigPicture").html("<img src='/display?filename="+fileCallPath+"'>").animate({width:'100%', top:'0'}, 600);
		}
		$(".uploadResult").on("click", "li", function(e){
			console.log("view image");
			let liObj = $(this);
			let path = encodeURIComponent(liObj.data("path") + "\\" + liObj.data("uuid") + "_" + liObj.data("filename"));
			if(liObj.data("type")){
				showImage(path);
			}else {
				if (path.toLowerCase().endsWith('pdf')) {
					window.open("/pdfviewer?filename="+path);
				}else{
					self.location= "/downloadFile?filename="+path;
				}
			}
		});
		$(".bigPictureWrapper").on("click", function(e){
			$(".bigPictureWrapper").hide();
			$(".bigPicture").css("top","15%");
		});
	});	
	
	
	/* 	console.log(test.language);
		console.log(test.add(2,4));
		
		const test={
				language:"java",
				level:"middle",
				limit:23
		};
		const test={
				language:"java",
				add:function(a,b){
					return a+b;
				}
		};
		
		const square = new Rectangle(10,10);
		console.log(square.area);
		
		class Rectangle{
			construtor(height, width) {
				this.height = height;
				this.width =width;
			}
			get area(){
				return this.calcArea();
			}
			calcArea(){
				return this.height * this.width;
			}
		}
		 */

	
		
</script>
</head>
<body>
<div class="wrapper_read">
	<div class="read_head">
		<h1>게시글 읽기</h1>
	</div>
	<div class="read_line"></div>
	<table class="read_table" >
		<thead>
			<tr class="read_table_title">
				<th>제목</th>
				<td>${board.title}</td>
			</tr>
			<tr class="read_table_title">
				<th>작성자</th>
				<td>${board.writer}</td>
			</tr>
			<tr class="read_table_title">
				<th>작성일</th>
				<td>
				<c:choose>
					<c:when test="${board.regdate } == ${board.updatedate }">
					<fmt:formatDate pattern="YY-MM-dd hh:mm" value="${board.regdate}"/>
					</c:when>
					<c:otherwise>
					<fmt:formatDate pattern="YY-MM-dd hh:mm" value="${board.updatedate}"/>
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
		</thead>
	</table>
	<div class="read_table_content">
		<textarea class="read_content" name="content" readonly="readonly">${board.content}</textarea>
	</div>
	<div class="article-bottom">
		<div class="field3 get-th field-style" >
			<p><b>첨부파일</b></p>
		</div>
		<div class="field3 get-td">
			<div class="uploadResult">
				<ul></ul>
			</div>
		</div>
	</div>
	<div class="read_bottom">
		<button class="read_button" id="list_btn">목록</button>
		<c:if test="${auth.userid eq board.writer}">
		<button class="read_button" id='modify_btn'>수정</button>
		</c:if>
		<form id='operForm' action="/board/modify" method="get">
			<input type='hidden' id='bno' name='bno' value='<c:out value="${board.bno}"/>'>
			<input type='hidden' name='pageNum' value='<c:out value="${criteria.pageNum}"/>'>
			<input type='hidden' name='amount' value='<c:out value="${criteria.amount}"/>'>
			<input type='hidden' name='type' value='<c:out value="${criteria.type}"/>'>
			<input type='hidden' name='keyword' value='<c:out value="${criteria.keyword}"/>'>
			
		</form>
	</div>
	<div class="read_reply">
		<c:if test="${not empty auth}">
		<button id='addReplyBtn' class='btn btn-primary btn-xs pull-right'>댓글 작성</button>
		</c:if>
	</div>
	<div>
		<h4>댓글 목록</h4>
	</div>
	<div class="reply_list">
		<ul class="reply"></ul>
		<div class="panel-footer"></div>
	</div>
	
	<!-- Modal -->
	<div class="modal fade" tabindex="-1" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">REPLY MODAL</h4>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label>Reply</label>
						<input class="form-control" id='reply' name='reply'>
					</div>
					<div class="form-group">
						<label>Replyer</label>
						<input class="form-control" id='replyer' name='replyer' value="${auth.userid}" readonly="readonly">
					</div>
				</div>
					<div class="modal-footer">
						<button id='modalModBtn' type="button" class="btn btn-warning">Modify</button>
						<button id='modalRegisterBtn' type="button" class="btn btn-primary">register</button>
						<button id='modalCloseBtn' type="button" class="btn btn-info">Close</button>
					</div>
				</div>
			</div>
		</div>
		<div class="bigPictureWrapper">
			<div class="bigPicture"></div>
		</div>
</div>
<%@include file ="../includes/footer.jsp" %>
</body>
</html>