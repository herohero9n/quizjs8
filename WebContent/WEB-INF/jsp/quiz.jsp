<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="model.Quiz" %>
<%
	Quiz quiz = (Quiz)session.getAttribute("quiz");
	//String errorMsg = (String)request.getAttribute("errorMsg");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JavaSilver8 4択問題</title>
<script type="text/javascript">
<!--
function check() {
	let flag = 0;
	//check
	for (let i = 0; i < document.form.choice.length; i++) {
		if (document.form.choice[i].checked) {
			flag = 1;
			break;
		}
	}

	if (flag == 0) {
		window.alert('選択されていません');
		return false;//送信中止
	} else {
		return true;
	}

}
//-->
</script>
 <!--<script src="/js/nullCheck.js" type="text/javascript"></script>-->
</head>
<body>
	<div><%=quiz.getQuestion() %></div>
	<div>
		<form action="/quizjs8/QuizServlet" method="post" name="form" onSubmit="return check()">
			<p><label><input type="radio" name="choice" value="1"><%= quiz.getChoice1() %></label></p>
			<p><label><input type="radio" name="choice" value="2"><%= quiz.getChoice2() %></label></p>
			<p><label><input type="radio" name="choice" value="3"><%= quiz.getChoice3() %></label></p>
			<p><label><input type="radio" name="choice" value="4"><%= quiz.getChoice4() %></label></p>
			<p><input type="submit" value="解答"></p>
		</form>
	</div>
	<%--
	<% if(errorMsg != null){ %>
	<p><%= errorMsg %></p>
	<%} %>
	--%>

</body>
</html>