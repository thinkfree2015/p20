<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/1/7
  Time: 10:23
  To change this template use File | Settings | File Templates.
  微信答题--活动说明
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html class="no-js">
<head>
  <title>活动说明</title>
</head>
<body id="bodyone">

<div class="wechat-bg">
  <div class="wechat-header ae">
    <div class="wechat-top"></div>
    <div class="wea-bg"></div>
  </div>
  <div class="explain ae">
    <div class="explain-top ae">
      <h5>活动说明:</h5>
      <p>点击“马上开始”进入问答页面开始答题，完成当日所有题目即可获得当日奖励。累计答题还可以赢月度大奖，快来参加活动吧！</p>
    </div>
    <div class="explain-bot">
      <h5>活动规则:</h5>
      <p><span>一、</span>参加活动者当天24：00以前完成答题可以获得当日奖励，超过该时间可以答题，但无奖励；</p>
      <p><span>二、</span>每人每天可以参加活动一次，答完为止；</p>
      <p><span>三、</span>打错的题目可以发送给好友求助，好友必须在当天24：00以前完成答题，否则无效；</p>
      <p><span>四、</span>e飞蚁保留对活动的最终解释权。</p>
    </div>
    <div class="explain-btn">
      <%--<c:if test="${not empty examination}">
        <a href="<c:url value='/answer/assistAnswer/${examination.id}'/>" class="wechat-btn">帮助好友</a>
      </c:if>--%>
      <a href="<c:url value='/answer/start2Answer.do'/>" class="wechat-btn">马上开始</a>
    </div>
    <div class="explain-bg"></div>
  </div>
</div>

</body>
</html>