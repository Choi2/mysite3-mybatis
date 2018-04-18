<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/ejs/ejs.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
//jQuery plugin
(function($) {
	$.fn.hello = function() {
		var $element = $(this);
		console.log($element.attr("id") + ":hello~");
	}
})(jQuery);


var isEnd = false;
var ejsListItem = new EJS({
	url: "${pageContext.request.contextPath }/assets/js/ejs/template/listitem.ejs"
});
var messageBox = function(title, message, callback) {
    $( "#dialog-message" ).attr("title", title);
    $( "#dialog-message p" ).text(message);

	$( "#dialog-message" ).dialog({
        modal: true,
        buttons: {
	          "확인": function() {
	            $( this ).dialog( "close" );
	          }
        },
        close : callback || function(){}
      });
}
	
//삭제 시 비밀번호 입력 모달 다이알로그



var render = function(mode, vo){
	var html = ejsListItem.render(vo);
/* 		"<li data-no='"+ vo.no +"'>"+
		"<img class='user' src='${pageContext.request.contextPath }/assets/images/user.png' />"+
		"<strong>"+ vo.name +"</strong>"+
		"<span class='content'>"+ vo.content +"</span>"+
		"<a href='#' data-no='"+ vo.no +"'>삭제</a>"+	
		"</li>"; */
	if(mode == true) {
		$("#list-guestbook").prepend(html);
	} else {
		$("#list-guestbook").append(html);
	}
	
}


var fetchList = function() {
	if(isEnd == true) {
		return;
	}
	
	var startNo = $("#list-guestbook li").last().data("no") || 0;
	
	$.ajax({
		url:"/mysite3/api/guestbook/list?no=" + startNo,
		type:"get",
		dataType: "json",
		success: function(response) {
			//console.log(response);
			if(response.result != "success") {
				console.log(response.message);
				return;
			}
			
			if(response.data.length < 5) {
				isEnd = true;
				$("#btn-fetch").prop("disabled", true);
			}
			
			// render 
			$.each(response.data, function(index, vo) {		
				render(false, vo);
			});
		}
	});
}

$(function(){
	
	// 삭제 시 비밀번호 입력 모달 다이알로그
	var deleteDialog = $( "#dialog-delete-form" ).dialog({
      autoOpen: false,
      modal: true,
      buttons: {
		"삭제": function(){
			var password = $("#password-delete").val();
			var no = $("#hidden-no").val();
			
			console.log( password + ":" + no );
			
			// ajax 통신
			$.ajax({
				url: "/mysite3/api/guestbook/delete",
				type: "post",
				dataType: "json",
				data:"no=" + no + "&password=" + password,
				success: function(response){
					if( response.result == "fail" ) {
						console.log( response.message );
						return;
					}
					
					if( response.data == -1 ) {
						$(".validateTips.normal").hide();
						$(".validateTips.error").show();
						$("#password-delete").val("");
						return;
					}
					
					$("#list-guestbook li[data-no=" + response.data + "]").remove();
					deleteDialog.dialog( "close" );
				}
			});
		},
        "취소": function() {
        	deleteDialog.dialog( "close" );
        }
      },
      close: function() {
			$("#password-delete").val( "" );
			$("#hidden-no").val( "" );
			$(".validateTips.normal").show();
			$(".validateTips.error").hide();
			
      }
    });
 	
	
	//Live Event Listener
	$(document).on("click", "#list-guestbook li a", function(event) {
		event.preventDefault(); //# 눌러지면 위로 올라가는 이벤트가 생기기 때문에 삭제클릭하는 순간에는 올라가는 이벤트를 막아야 한다.
		var no = $(this).data("no");
		$("#hidden-no").val(no);
		console.log(no);
		deleteDialog.dialog("open");
	});
	
	$("#add-form").submit(function(event){
		event.preventDefault();
		var queryString = $(this).serialize(); //url encoding을 직접할 필요가 없음!
		// console.log(queryString);	
	 	
		var data = {};
		$.each($(this).serializeArray(), function(index, o) {
			data[o.name] = o.value;
		});
		
		if(data["name"] == '') {
			messageBox("메시지 등록", 
					"이름이 비어 있습니다.!",
					function() {
						$("#name").focus();
					});
			return ;
		}
		
		if(data["password"] == '') {
			messageBox("메시지 등록", 
					"비밀번호가 비어 있습니다.!", function() {
				$("#password").focus();
			});
		
			return ;
		}
		
		if(data["content"] == '') {
			messageBox("메시지 등록", "내용이 비어 있습니다.!", function(){
				$("#content").focus();
			});
			
			return ;
		}

		console.log(data);
		
	 	$.ajax({
			url:"/mysite3/api/guestbook/insert",
			type:"post",
			data: JSON.stringify(data),
			dataType:"json",
			contentType : "application/json",
			success:function(response) {
				console.log(response);
				render(true, response.data);
				$("#add-form")[0].reset();
			}
		});
	});
	
	$("#btn-fetch").click(function(){
		fetchList();
	});
	
	$(window).scroll(function(){
		var $window = $(this);
		var scrollTop = $window.scrollTop();
		var windowHeight = $window.height();
		var documentHeight = $(document).height();
		
		//console.log(scrollTop + ":" + windowHeight + ":" + documentHeight);
		// scrollbar의 thumb가 바닥 던 30px까지 도달
		if(scrollTop + windowHeight + 30 > documentHeight) {
			fetchList();
		}
	});
	
	fetchList(); //최초 리스트 가져오기
	
	//plugin 테스트
	$("#container").hello();
	
});

</script>
<style type="text/css">
* {
	padding: 0;
	margin: 0;
}

body {
	font-family: '맑은 고딕';
	font-size: 0.75em;
	color: #333;
}


.title {
	margin: 0 5px;
}

.icon {
	float: left;
	width: 30px;
}

#add-form input {
	display: block;
	width: 100%;
	padding: 5px;
	margin: 5px 0;
}

#add-form textarea {
	padding: 5px; 
	resize: none;
	width: 100%;
	
}

#add-form input[type=submit] {
	background: none;
	border: 1px solid rgb(169, 169, 169);
}

#list-guestbook {
	border-top : 1px solid #cbccce; 
	display: block;
}

#guestbook ul li {
	margin: 20px 0;
	position: relative;
	height: 70px;
}

.user {
	float: left;
	width: 60px;
}

#guestbook ul li strong, #guestbook ul li a {
	display: block;
}


#guestbook ul li a {
	position: absolute;
	top: 40px;
	left: 35px;
	background : url('${pageContext.request.contextPath }/assets/images/delete.png') no-repeat;
	font-size : 0;
	width:30px; 
	height:20px;
}

#guestbook ul li p {
	float : left;
	padding : 10px;
	margin : 5px 0;
	width : 84%;
	background-color : #EEEEEE;
	border : 1px solid #cbccce;
}


#guestbook ul li strong {
	color : #C0C0C0;
	
}

#list-guestbook .content {
	
}

</style>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>
					<img class="icon"
						src='${pageContext.request.contextPath }/assets/images/guestbook.png' />
					<span class="title">방명록</span>
				</h1>
				<form id="add-form">
					<input type="text" id="name" name="name" placeholder="이름"> 
					<input type="password" id="password" name="password" placeholder="비밀번호">
					<textarea id="content" name="content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				<ul id="list-guestbook">
				</ul>
				<input id="btn-fetch" type="button" value="패치"/>
			</div>
			<div id="dialog-delete-form" title="메세지 삭제 " style="display:none">
				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
				<p class="validateTips error" style="display: none">비밀번호가 틀립니다.</p>
				<form action="">
					<input type="password" id="password-delete" value="" class="text ui-widget-content ui-corner-all"> 
					<input type="hidden" id="hidden-no" value=""> 
					<input type="submit" tabindex="-1" style="position: absolute; top: -1000px">
				</form>
			</div>
			<div id="dialog-message" title="" style="display: none">
				<p></p>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax" />
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>