<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html class="no-js">
<head>
  <title>领取奖励</title>
</head>
<body>
<div class="atmosphere-pictures ae"><img src="<c:url value='/scripts/wap/upload/header-top.jpg'/>"></div>
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
  <div class="bd info" style="border-top: 0;">
    <a class="icon"></a>
    <div class="txt">中&nbsp;&nbsp;国&nbsp;&nbsp;非&nbsp;&nbsp;遗&nbsp;&nbsp;电&nbsp;&nbsp;商&nbsp;&nbsp;平&nbsp;&nbsp;台</div>
    <div class="wechat"><img src="<c:url value='/scripts/wap/images/icon-wechat.png'/>"></div>
    <div class="txt">关注微信公众号</div>
    <div class="txt">领取超值代金券</div>
  </div>
  <div class="bd copyright">京ICP备15032511号-1</div>
</footer>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="<c:url value="/scripts/wap/js/weixin.js"/>"></script>
<script>
  var wx_share_imgUrl = "${sessionScope.headimgurl}"; //分享图片的url
  var wx_share_type = '';   //分享的类型   分享类型,music、video或link，不填默认为link
  var wx_share_dataUrl = ""; // 如果type是music或video，则要提供数据链接，默认为空
  var wx_api_list = ['onMenuShareAppMessage', 'onMenuShareTimeline'];    //需要使用的JS接口列表
  var wx_share_link = "http://dati.efeiyi.com/wx/start.do";
  var wx_share_title = "${sessionScope.nickname}完成了在非遗知识闯关答题，敢来挑战吗？"; //分享标题
  var wx_share_des = "${sessionScope.nickname}在玩非遗知识闯关，涨姿势还有钱赚，敢来挑战Ta吗？";  //分享描述
  initWx("http://dati.efeiyi.com/wx/init.do","",wx_share_title,wx_share_des,wx_share_link,wx_share_imgUrl,wx_share_type,wx_share_dataUrl,wx_api_list);
</script>
</body>
</html>