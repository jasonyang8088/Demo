<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
<title>fileupload | mvc-showcase</title>
<script type="text/javascript"
	src="<c:url value="/resources/jquery/1.6/jquery.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/jqueryform/2.8/jquery.form.js" />"></script>
</head>
<body>
	<div id="fileuploadContent">
		<h2>File Upload</h2>
		<form id="fileuploadForm" action="${actionUrl}" method="POST"
			enctype="multipart/form-data" class="cleanform">
			<div>
				<span>学段 ： </span><select name="stage">
					<option value="1">小学</option>
					<option value="2">初中</option>
					<option value="3" selected>高中</option>
				</select>
			</div>
			<br>
			<div>
				<span>学科 ： </span><select name="subjectName">
					<option value="语文" selected>语文</option>
					<option value="数学">数学</option>
					<option value="英语">英语</option>
					<option value="生物">生物</option>
					<option value="化学">化学</option>
					<option value="物理">物理</option>
					<option value="历史">历史</option>
					<option value="政治">政治</option>
				</select>
			</div>
			<br> <label for="file">File</label> <input id="file" type="file"
				name="file" />
			<p>
				<button type="submit">Upload</button>
			</p>
		</form>
		<h2>显示试题</h2>
		<c:forEach items="${lstQuestion}" var="question">
			<ul>
				<c:if test="${question.type!=null}">
					<li><span>题型: </span>${question.type}
				</c:if>
				<c:if test="${question.stem!=null}">
					<li><span>题干</span><br>${question.stem }
				</c:if>
				<c:if test="${question.options!=null}">
					<li><span>选项</span><br>${question.options }
				</c:if>
				<c:if test="${question.answer!=null}">
					<li><span>答案: </span>${question.answer }
				</c:if>
				<c:if test="${question.analyse!=null}">
					<li><span>解析: </span>${question.analyse }
				</c:if>
				<c:if test="${question.difficult!=null}">
					<li><span>难度: </span>${question.difficult }
				</c:if>
				<c:if test="${question.fenzhi!=null}">
					<li><span>分值: </span>${question.fenzhi }
				</c:if>


			</ul>
			<hr>
		</c:forEach>

		<form class="upload_cover" action="download" method="post">
			<input type="hidden" name="lstQuestion" value="${lstQuestion }">
			<button type="submit">download</button>
		</form>
	</div>
</body>
</html>
