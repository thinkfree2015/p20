<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/1/11
  Time: 16:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html class="no-js">
<head>
  <title>题目解释</title>
</head>
<body>
<div class="wechat-bg">
  <div class="wechat-header ae">
    <div class="headline">
      <div class="head-number">${examination.examinationEdition.name}</div>
    </div>
    <div class="wea-bg"></div>
  </div>
  <div class="topic ae">
    <div class="topic-img ae"><img src="/scripts/wap/upload/mypurchase02.png"> </div>
    <%--<div class="topic-img ae"><img src="<c:url value='${question.pictureUrl}'/>"></div>--%>
    <div class="topic-txt ae">
      <p>${question.questionContent}</p>
      <div class="txc-box">${question.answerKnowledge}</div>
      <div class="txc-btn">
        <a href="<c:url value='/wx/descriptionReturn.do?examId=${examination.id}'/>" class="wechat-btn cart-ft">返&nbsp;回</a>
        <a href=""  class="wechat-btn cart-ft">我&nbsp;要&nbsp;分&nbsp;享</a>
      </div>
    </div>
  </div>
</div>
</body>
</html>