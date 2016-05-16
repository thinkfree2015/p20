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


<!doctype html>
<html class="no-js">
<head>
</head>
<body id="bodyone">
<div class="wechat-bg">
  <div class="e-logo">
    <div class="img"><img src="../shop2015/images/wechat-sq1.png" alt=""></div>
    <div class="txt">
      <p>答题赚红包</p>
      <p>小飞蚁恭候多时啦!</p>
    </div>
  </div>
  <div class="e-info">
    <p>1、每天完成答题即可领红包；</p>
    <p>2、答错也没关系，求助好友同样得红包；</p>
    <p>3、先到先得，快快行动吧！</p>
  </div>
  <div class="e-btn">
    <a href="<c:url value='/answer/start2Answer.do'/>" class="wechat-btn">马上开始答题</a>
  </div>
</div>
</body>
</html>
