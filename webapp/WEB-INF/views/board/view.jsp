<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath}/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board" class="board-form">
				<table class="tbl-ex">
					<tr>
						<th colspan="2">글보기</th>
					</tr>
					<tr>
						<td class="label">제목</td>
						<td>${vo.title}</td>
					</tr>
					<tr>
						<td class="label">내용</td>
						<td>
							<div class="view-content">
								<c:out value="${vo.content}" escapeXml="true"></c:out> 
							</div>
						</td>
					</tr>
				</table>
				<div class="bottom">
					<a href="${pageContext.servletContext.contextPath}/board">글목록</a>
					<c:if test="${sessionScope.authUser ne null}">
						<a href="${pageContext.servletContext.contextPath}/board/write?userno=${sessionScope.authUser.no}&parentno=${vo.no}&groupno=${vo.groupNo}&orderno=${vo.orderNo}&depth=${vo.depth}">답글</a>
					</c:if>
					<c:if test="${sessionScope.authUser.no eq vo.userNo}">
						<a href="${pageContext.servletContext.contextPath}/board/modify/${vo.no}">글수정</a>
					</c:if>
				</div>
			</div>
			
		<div id="comment">
			
			<c:set var="count" value="${fn:length(commentList)}"/>
			<ul>
	 			<c:forEach items="${commentList}" var="vo" varStatus="status">	
	 			<li>
					<table style=" margin-left: auto; margin-right: auto;" width="510" border="1">
						<tr>
							<td>[${status.count}]</td>
							<td>${vo.name}</td>
							<td>${vo.regDate}</td>
							<c:if test="${sessionScope.authUser.no eq vo.userNo}">
								<td><a href="${pageContext.servletContext.contextPath}/board?a=deletecomment&no=${param.no}&commentno=${vo.no}">삭제</a></td>
							</c:if>
						</tr>
						<tr>
							<td colspan="4">${vo.content}</td>
						</tr>
					</table>	
				</li>			
				</c:forEach>
			</ul>
				
				<c:if test="${sessionScope.authUser ne null}">
					<form action="${pageContext.servletContext.contextPath}/board" method="post">
						<input type="hidden" name="a" value="insertcomment">
						<input type="hidden" name="no" value="${param.no}">
						<table>
							<tr>
								<td colspan=4>
									<textarea  style="height:70px; width:550px; resize:none;" name="content" id="content">
									</textarea>
								</td>
							</tr>
							<tr>
								<td colspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
							</tr>
						</table>
					</form>
				</c:if>
				
			</div>
			
		</div>
		
<%-- 
		<div id="content">
	
		</div> --%>

		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>