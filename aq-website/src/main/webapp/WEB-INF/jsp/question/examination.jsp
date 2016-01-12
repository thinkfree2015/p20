<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/1/7
  Time: 17:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html class="no-js">
<head>
  <title>微信知识竞赛</title>
</head>
<body>
<div class="wechat-bg">
  <div class="wechat-header ae">
    <div class="headline">
      <div class="head-number">${examination.examinationEdition.name}</div>
    </div>
    <div class="wea-bg"></div>
  </div>
  <div class="addae ae">
    <c:forEach items="${examination.examinationQuestionList}" var="examQuestion">
      <div class="topic addtopic ae <c:if test="${examQuestion.questionOrder == 1}">active</c:if>">
        <h5>第<span>${examQuestion.questionOrder}</span>题</h5>
        <div class="topic-img ae"><img src="<c:url value='/scripts/wap/upload/mypurchase02.png'/>"></div>
        <%--<div class="topic-img ae"><img src="<c:url value='${examQuestion.question.pictureUrl}'/>"></div>--%>
        <div class="topic-txt ae">
          <p>${examQuestion.question.questionContent}</p>
          <a href="#" onclick="answerQuestion('A')" class="bg-link">${examQuestion.question.answerA}</a>
          <a href="#" onclick="answerQuestion('B')" class="bg-link">${examQuestion.question.answerB}</a>
          <a href="#" onclick="answerQuestion('C')" class="bg-link">${examQuestion.question.answerC}</a>
          <a href="#" onclick="answerQuestion('D')" class="bg-link">${examQuestion.question.answerD}</a>
        </div>
      </div>
    </c:forEach>
  </div>
</div>
<script>
  var answerList = "";
  var examId = "${examination.id}";
  var consumerId = "${consumer.id}";
  function answerQuestion(val){
    if(answerList == ""){
      answerList = val;
    }else {
      answerList = answerList + "," + val;
    }
  }

  $(function(){
    //微信问题选项
    (function(){
      var _this =$(".addae").index()-1;
      var _index = $(".topic").length;
      $(".topic .topic-txt.topic-txt .bg-link").click(function(){
        _this++;
        $(".topic").eq(_this).addClass("active").siblings().removeClass("active");
        if(_this == _index){
          var url = "?examId="+ examId + "&answerList=" + answerList + "&consumerId=" + consumerId;
          window.location.href="<c:url value='/answer/commitAnswer.do'/>" + url ;
        }
      });
    })();
  });
</script>
</body>
</html>