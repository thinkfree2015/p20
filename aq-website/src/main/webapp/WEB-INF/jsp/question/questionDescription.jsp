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
  <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
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
        <a href="#" onclick="window.location.href=document.referrer;" class="wechat-btn cart-ft">返&nbsp;回</a>
        <a href="#分享"  class="wechat-btn cart-ft share">我&nbsp;要&nbsp;分&nbsp;享</a>

        <div id="cover" style="display: none;">
          <em class="bg"></em>
          <img src="/scripts/wap/upload/guide-share.png" class="share-picture" alt="">
        </div>

        <div id="cover2" style="display: none" style="text-align: center">
          <div class="text-co2">
            <strong class="cov-titie">提示</strong>
            <p class="covtext">在浏览器中寻找分享按钮，将本页面分享给您的好友吧。</p>
            <div class="ae" style="text-align: center"><a class="covbtn">确定</a></div>
          </div>
          <div class="bg"></div>
        </div>

      </div>
    </div>
  </div>
</div>

<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="<c:url value="/scripts/wap/js/weixin.js"/>"></script>
<script>

  var wx_share_title = "${question.questionName},你知道答案吗？"; //分享标题
  var wx_share_des = "${sessionScope.nickname}在玩非遗知识闯关，涨姿势还有钱赚，你也来玩吧！";  //分享描述
  var wx_share_link = "http://dati.efeiyi.com/wx/start.do; //分享的链接地址  //需要动态获取，而不是直接填写静态值
  var wx_share_imgUrl = "${sessionScope.headimgurl}"; //分享图片的url
  var wx_share_type = '';   //分享的类型   分享类型,music、video或link，不填默认为link
  var wx_share_dataUrl = ""; // 如果type是music或video，则要提供数据链接，默认为空
  var wx_api_list = ['onMenuShareAppMessage', 'onMenuShareTimeline'];    //需要使用的JS接口列表

  function isWeiXin() {
    var ua = window.navigator.userAgent.toLowerCase();
    if (ua.match(/MicroMessenger/i) == 'micromessenger') {
      return true;
    } else {
      return false;
    }
  }
  $().ready(function () {
    $(".share").click(function () {
      if (isWeiXin()) {
        $("#cover").show();
        $(".custom-header").css("z-index", "0");
      } else {
        $("#cover2").show();
        $(".custom-header").css("z-index", "0");
      }
    });

    $("#cover").click(function () {
      $(this).hide();
    });
  })

  initWx("http://dati.efeiyi.com/wx/init.do", "<c:url value='/wx/shareExamination/${examination.id}'/>", wx_share_title,wx_share_des,wx_share_link,wx_share_imgUrl,wx_share_type,wx_share_dataUrl,wx_api_list);
</script>

<style type="text/css">
  #cover2 .text-co2 {
    width: 282px;
    height: 153px;
    background: #fff;
    border: 1px solid #c4c4c4;
    position: absolute;
    z-index: 10;
    top: -42px;
    left: 50%;
    margin-left: -140px;
  }
</style>

</body>
</html>