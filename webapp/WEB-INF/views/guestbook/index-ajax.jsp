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
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
/* $(function() {
	$('.delete').click(function() {
		var $href = $(this).attr('href');
	});
	
    function layer_popup(el){

        var $el = $(el);        //레이어의 id를 $el 변수에 저장
        var isDim = $el.prev().hasClass('dimBg');   //dimmed 레이어를 감지하기 위한 boolean 변수

        isDim ? $('.dim-layer').fadeIn() : $el.fadeIn();

        var $elWidth = ~~($el.outerWidth()),
            $elHeight = ~~($el.outerHeight()),
            docWidth = $(document).width(),
            docHeight = $(document).height();

        // 화면의 중앙에 레이어를 띄운다.
        if ($elHeight < docHeight || $elWidth < docWidth) {
            $el.css({
                marginTop: -$elHeight /2,
                marginLeft: -$elWidth/2
            })
        } else {
            $el.css({top: 0, left: 0});
        }
	
}); */
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

#dialog-delete-form {
	display:none;
	position: absolute;
	top: 50%;
	left: 50%;
	width: 410px;
	height: auto;
	background-color: #fff;
	border: 5px solid #3571B5;
	z-index: 10;
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
				<form id="add-form" action="${pageContext.servletContext.contextPath}/guestbook/insert" method="post">
					<input type="text" id="name" placeholder="이름"> <input
						type="password" id="password" placeholder="비밀번호">
					<textarea id="content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				<ul id="list-guestbook">
					<c:forEach items="${list}" var="vo" varStatus="status">	
						<li data-no='${count - status.index}'>
						<img class="user" src='${pageContext.request.contextPath }/assets/images/user.png' />
							<strong> ${vo.name} </strong>
							<p> ${vo.content} </p> 
							<a href='#dialog-delete-form' class="delete">삭제</a>
						</li>
					</c:forEach>
				</ul>
	
			</div>
			<div id="dialog-delete-form" title="메세지 삭제">
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