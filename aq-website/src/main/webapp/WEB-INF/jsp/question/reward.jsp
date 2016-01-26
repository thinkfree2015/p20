<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/1/14
  Time: 11:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html class="no-js">
<head>
  <title>领取奖励</title>
</head>
<body>
<div class="atmosphere-pictures ae"><img src="/scripts/wap/upload/header-top.jpg"></div>
<div class="wechat-bg">
  <div class="ae  weachat-tab"> <div class="rewarded"></div></div>
  <div class="bouns-box  weachat-tab ae">
    <div class="bonus ae">
      <div class="bouns-like"><span>${balanceRecord.changeBalance}</span>元</div>
      <p class="bouns-text">奖励以余额的形式发放到您的e飞蚁账户中，购物时抵现金使用，可累计，全场无限制，进入个人中心“账户余额”查看。</p>
      <div class="ds-btn"><a href="http://www.efeiyi.com" class="wechat-btn">立即使用</a></div>
<c:if test="${not empty coupon}">
  <div class="bouns-like"><span>${coupon}</span>元</div>
  <p class="bouns-text">奖励以优惠券的形式发放到您的e飞蚁账户中，购物时抵现金使用，进入个人中心“我的卡券”查看。</p>
  <div class="ds-btn"><a href="${couponUrl}" class="wechat-btn">立即使用</a></div>
</c:if>
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
        <%--<li>--%>
          <%--<div class="fiy-text">--%>
            <%--<div class="fiy-pic"><a href="#"><img src="/scripts/wap/upload/des-master.jpg"></a></div>--%>
            <%--<span class="fiy-txt">剑指天山</span>--%>
          <%--</div>--%>
          <%--<span class="fiy-buck">4210元</span>--%>
        <%--</li>--%>
        <%--<li>--%>
          <%--<div class="fiy-text">--%>
            <%--<div class="fiy-pic"><a href="#"><img src="/scripts/wap/upload/des-master.jpg"></a></div>--%>
            <%--<span class="fiy-txt">剑指天山</span>--%>
          <%--</div>--%>
          <%--<span class="fiy-buck">4210元</span>--%>
        <%--</li>--%>
        <%--<li>--%>
          <%--<div class="fiy-text">--%>
            <%--<div class="fiy-pic"><a href="#"><img src="/scripts/wap/upload/des-master.jpg"></a></div>--%>
            <%--<span class="fiy-txt">剑指天山</span>--%>
          <%--</div>--%>
          <%--<span class="fiy-buck">4210元</span>--%>
        <%--</li>--%>
      </ul>
    </div>
  </div>
</div>

<footer class="bd footer-new">
  <div class="bd info" style="border-top: 0;">
    <a class="icon"></a>
    <div class="txt">中&nbsp;&nbsp;国&nbsp;&nbsp;非&nbsp;&nbsp;遗&nbsp;&nbsp;电&nbsp;&nbsp;商&nbsp;&nbsp;平&nbsp;&nbsp;台</div>
    <div class="wechat"><img src="/scripts/wap/images/icon-wechat.png"></div>
    <div class="txt">关注微信公众号</div>
    <div class="txt">领取超值代金券</div>
  </div>
  <div class="bd copyright">京ICP备15032511号-1</div>
</footer>
</body>
</html>