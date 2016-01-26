<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html class="no-js">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="description" content="">
  <meta name="keywords" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <title>答题完成</title>
  <!-- Set render engine for 360 browser -->
  <meta name="renderer" content="webkit">
  <!-- No Baidu Siteapp-->
  <meta http-equiv="Cache-Control" content="no-siteapp"/>
  <link rel="icon" type="image/png" href="assets/i/favicon.png">
  <!-- Add to homescreen for Chrome on Android -->
  <meta name="mobile-web-app-capable" content="yes">
  <link rel="icon" sizes="192x192" href="assets/i/app-icon72x72@2x.png">
  <!-- Add to homescreen for Safari on iOS -->
  <meta name="apple-mobile-web-app-capable" content="yes">
  <meta name="apple-mobile-web-app-status-bar-style" content="black">
  <meta name="apple-mobile-web-app-title" content="Amaze UI"/>
  <link rel="apple-touch-icon-precomposed" href="assets/i/app-icon72x72@2x.png">
  <!-- Tile icon for Win8 (144x144 + tile color) -->
  <meta name="msapplication-TileImage" content="assets/i/app-icon72x72@2x.png">
  <meta name="msapplication-TileColor" content="#0e90d2">
  <link type="text/css" rel="stylesheet" href="../shop2015/css/amazeui.min.css?v=20150831">
  <link type="text/css" rel="stylesheet" href="../shop2015/css/app.css?v=20150831">
  <link type="text/css" rel="stylesheet" href="../shop2015/css/myorder.css?v=20150831">
</head>
<body>
<div class="atmosphere-pictures ae"><img src="../shop2015/upload/header-top.jpg"></div>
<div class="wechat-bg">
  <div class="ae  weachat-tab"> <div class="rewarded"></div></div>
  <div class="bouns-box  weachat-tab ae">
    <div class="bonus ae">
      <div class="bouns-like coupon_link">
        <div class="bouns_coupon">
          <div class="coupon_left">
            <em class="coupon-text">${coupon}<strong>¥</strong></em>
          </div>
          <div class="coupon_right">
            <strong>优惠券</strong>
          </div>
        </div>
      </div>
      <p class="bouns-text text-center">奖励以优惠券的形式发放到您的e飞蚁账户中，购物时抵现金使用，进入个人中心“我的卡券”查看。</p>
      <div class="ds-btn"><a href="${couponUrl}" class="wechat-btn">立即领取</a></div>
    </div>
    <div class="fiy-box">
      <div class="fiy-title ae"><i class="fiy-icon"></i></div>
      <div class="ae text-fiy">今日闯关英雄榜,您目前排名${rank}名。<a class="right-btn" href="">炫耀一下!</a></div>
      <!--月榜-->
      <ul class="ae">
        <c:forEach items="${rankList}" var="participationRecord" >
        <li>
          <div class="fiy-text">
            <div class="fiy-pic"><a href="#"><img src="${participationRecord.wxCalledRecord.callback}"></a></div>
            <span class="fiy-txt">${participationRecord.wxCalledRecord.requestSource}</span>
          </div>
          <span class="fiy-buck">${participationRecord.consumer.balance}元</span>
        </li>
          </c:forEach>
      </ul>
    </div>
  </div>
</div>
<footer class="bd footer-new">
  <!----//End---->
  <div class="bd info" style="border-top: 0;">
    <a class="icon"></a>
    <div class="txt">中&nbsp;&nbsp;国&nbsp;&nbsp;非&nbsp;&nbsp;遗&nbsp;&nbsp;电&nbsp;&nbsp;商&nbsp;&nbsp;平&nbsp;&nbsp;台</div>
    <div class="wechat"><img src="<c:url value='/scripts/wap/images/icon-wechat.png'/>"></div>
    <div class="txt">关注微信公众号</div>
    <div class="txt">领取超值代金券</div>
  </div>
  <div class="bd copyright">京ICP备15032511号-1</div>
</footer>
</body>
</html>