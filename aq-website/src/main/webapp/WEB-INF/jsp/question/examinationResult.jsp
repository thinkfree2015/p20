<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/1/8
  Time: 18:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html class="no-js">
<head>
  <title>答题完成</title>
</head>
<body>
<div class="wechat-bg">
  <div class="wechat-header ae">
    <div class="headline">
      <div class="head-number">${examination.examinationEdition.name}</div>
    </div>
    <c:set var="count" value="0" scope="page"/>
    <c:forEach items="${examination.examinationQuestionList}" var="examQuestion">
      <c:if test="${examQuestion.answerStatus == 2}">
        <c:set var="count" value="${count = count + 1}" scope="page"/>
      </c:if>
    </c:forEach>
    <div class="add-head">你共有${count}道题回答错误？不过没关系，可以让好友帮助你，俗话说得好，众人拾柴火焰高嘛。</div>
    <div class="wea-bg"></div>
  </div>
  <div class="topic ae">
    <div class="plan">
      <ul class="plan-issue">
        <c:forEach items="${examination.examinationQuestionList}" var="examQuestion">
          <li>
            <span class="plan-titie">${examQuestion.questionOrder}、</span>
            <div class="plan-txt">
              <p>${examQuestion.question.questionContent}</p>
              <c:if test="${examQuestion.answerStatus == 1}">
                <p class="correct">正确，
                  <a href="<c:url value='/answer/questionDescription.do?examId=${examination.id}&qId=${examQuestion.question.id}'/>">查看解释</a>
                </p>
              </c:if>
              <c:if test="${examQuestion.answerStatus == 2}">
                <p class="mistake">错误</p>
              </c:if>
            </div>
          </li>
        </c:forEach>
      </ul>
      <div class="txc-btn">
        <a href="" class="wechat-btn cart-ft">我&nbsp;要&nbsp;求&nbsp;助</a>
        <a href=""  class="wechat-btn cart-ft">领&nbsp;取&nbsp;奖&nbsp;励</a>
      </div>
    </div>
  </div>
</div>
</div>

</body>
</html>