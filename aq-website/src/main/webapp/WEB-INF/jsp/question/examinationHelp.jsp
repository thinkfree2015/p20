<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/1/13
  Time: 10:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html class="no-js">
<head>
  <title>帮助好友</title>
</head>
<body>
<div class="wechat-bg">
  <div class="wechat-header ae">
    <div class="headline">
      <div class="head-number">四</div>
    </div>
    <div class="add-head">你的好友正在向你求助，据说智商和颜值是成正比的哦~</div>
    <div class="wea-bg"></div>
  </div>
  <div class="addae ae">
    <c:set var="tag" value="1" scope="page"/>
    <c:forEach items="${examination.examinationQuestionList}" var="examQuestion">
      <c:if test="${examQuestion.answerStatus == 2}">
        <div class="topic addtopic ae <c:if test="${tag == 1}">active</c:if>">
          <h5>第<span>${examQuestion.questionOrder}</span>题</h5>
          <div class="topic-img ae"><img src="<c:url value='/scripts/wap/upload/mypurchase02.png'/>"></div>
            <%--<div class="topic-img ae"><img src="<c:url value='${examQuestion.question.pictureUrl}'/>"></div>--%>
          <div class="topic-txt ae">
            <p>${examQuestion.question.questionContent}</p>
            <a href="#" onclick="answerQuestionHelp('${examQuestion.questionOrder}', 'A')" class="bg-link">${examQuestion.question.answerA}</a>
            <a href="#" onclick="answerQuestionHelp('${examQuestion.questionOrder}', 'B')" class="bg-link">${examQuestion.question.answerB}</a>
            <a href="#" onclick="answerQuestionHelp('${examQuestion.questionOrder}', 'C')" class="bg-link">${examQuestion.question.answerC}</a>
            <a href="#" onclick="answerQuestionHelp('${examQuestion.questionOrder}', 'D')" class="bg-link">${examQuestion.question.answerD}</a>
          </div>
        </div>
        <c:set var="tag" value="2" scope="page"/>
      </c:if>
    </c:forEach>
  </div>
</div>

<script>
  var answerList = "";
  var examId = "${examination.id}";
  var consumerId = "${consumer.id}";
  function answerQuestionHelp(num, val){
    var numVal = num + "," + val;
    if(answerList == ""){
      answerList = numVal;
    }else {
      answerList = answerList + ";" + numVal;
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
          window.location.href="<c:url value='/answer/commitHelpAnswer.do'/>" + url ;
        }
      });
    })();
  });
</script>

</body>
</html>