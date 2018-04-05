<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath}/assets/css/user.css" rel="stylesheet" type="text/css">
<script src="${pageContext.servletContext.contextPath}/assets/js/jquery/jquery-1.9.0.js" type="text/javascript"></script>
<script>
$(function(){
	
	$('input[type=submit]').submit(function() {
		if($('#agree-prov').checked == false) {
			
		}
	});
	
	
	$("#email").blur(function(){
		var email = $("#email").val();
		if(email == "") {
			$('#result-email').html('<p style="padding:0; font-weight:bold; text-align:left; color:red;"> 이메일이 없습니다.</p>');
			return ;
		}

		$.ajax({
			url: "${pageContext.servletContext.contextPath}/api/user/checkmail?email=" + email,
			type: "get",
			data: "",
			dataType:"json",
			success: function(response) {
				// console.log(response.message);
				if(response.result != "success") {
					console.log(response.message);
					return;
				} 
				
				if(response.data == "exist") {
					$('#result-email').html('<p style="padding:0; font-weight:bold; text-align:left; color:red;"> 중복된 이메일입니다.</p>');
					$('input[name=email]').css('background-color','white');
					return;
				} else {
					$('#result-email').empty();
					$('input[name=email]').css('background-color','#BCD985');
				}
				
				$("#img-check").show();
				$("#check-id").hide();
				return; 
			}, 
			error: function(xhr, status, e) {
				console.error(status + ":" + e);
			}
		});
	});

});

</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="user">

				<form:form
					modelAttribute="userVo"
					class="join-form"
					id="join-form"
					method="post"
					action="${pageContext.request.contextPath}/user/join">
					<label class="block-label" for="name">이름</label>
					<form:input path="name"/>
					
					<p style="padding:0; font-weight:bold; text-align:left; color:red;">
						<form:errors path="name"/>
					</p>

					<label class="block-label" for="email">이메일</label>
					<form:input path="email"/>
					
					<span id="result-email"></span>
					
					<p style="padding:0; font-weight:bold; text-align:left; color:red;">
						<form:errors path="email"/>
					</p>
					
					<label class="block-label">패스워드</label>
					<form:password path="password"/>
					
					<p style="padding:0; font-weight:bold; text-align:left; color:red;">
						<form:errors path="password"/>
					</p>
					
					<fieldset>
						<legend>성별</legend>
						<label>여</label> <input type="radio" name="gender" value="female" checked="checked">
						<label>남</label> <input type="radio" name="gender" value="male">
					</fieldset>
					
					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>
					
					<input type="submit" value="가입하기">
					
				</form:form>

			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>