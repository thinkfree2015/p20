<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/1/13
  Time: 11:46
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
    <div class="add-head">
      <c:if test="${count != 0}">
        答错了，让我安静的哭一会儿，好桑心！虽然我帮不了忙，但我们还有好朋友，等我的好消息吧
      </c:if>
      <c:if test="${count == 0}">
        答对啦！果然是颜值越高，智商越高呢，真羡慕你，你一定颜值爆表啦！
      </c:if>
    </div>
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
        <%--<c:if test="${count != 0}">--%>
          <a href="" class="wechat-btn cart-ft">我&nbsp;要&nbsp;求&nbsp;助</a>
        <%--</c:if>--%>
        <a href=""  class="wechat-btn cart-ft">领&nbsp;取&nbsp;奖&nbsp;励</a>
      </div>
    </div>
  </div>
</div>
</div>

</body>
</html>