<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/1/8
  Time: 18:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
        你共有${count}道题回答错误？不过没关系，可以让好友帮助你，俗话说得好，众人拾柴火焰高嘛。
      </c:if>
      <c:if test="${count == 0}">
        哇塞，居然全对了!
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
        <c:if test="${examination.status == '0'}">
          <a href="#" onclick="answerHelp()" class="wechat-btn cart-ft share">我&nbsp;要&nbsp;求&nbsp;助</a>
          <%--分享链接  <c:url value="/wx/startHelp.do?examId=${examination.id}"/>  --%>
        </c:if>

        <c:if test="${examination.status != '0'}">
          <a href="<c:url value='/answer/inquireProgress.do?examId=${examination.id}'/>"
             class="wechat-btn cart-ft share">分&nbsp;享&nbsp;进&nbsp;度</a>
        </c:if>

        <a href="#" onclick="afterReward()"  class="wechat-btn cart-ft">领&nbsp;取&nbsp;奖&nbsp;励</a>

        <div id="cover" style="display: none;">
          <em class="bg"></em>
          <img src="/scripts/wap/upload/guide-share.png" class="share-picture" alt="">
        </div>

        <div id="cover2" style="display: none" style="text-align: center">
          <div class="text-co2">
            <strong class="cov-titie">提示</strong>
            <p class="covtext">
              <c:if test="${count != 0}">
                在浏览器中寻找分享按钮,将本页面分享给您的好友吧。
              </c:if>
              <c:if test="${count == 0}">
                您已回答全部正确,请点击领取奖励!
              </c:if>
            </p>
            <div class="ae" style="text-align: center"><a class="covbtn">确定</a></div>
          </div>
          <div class="bg"></div>
        </div>

      </div>
    </div>
  </div>
</div>

<script>
  function isWeiXin() {
    var ua = window.navigator.userAgent.toLowerCase();
    if (ua.match(/MicroMessenger/i) == 'micromessenger') {
      return true;
    } else {
      return false;
    }
  }

  function answerHelp(){
    var count = ${count == 0};
    if(count){
      $("#cover2").show();
      $(".custom-header").css("z-index", "0");
      return;
    }
    if (isWeiXin()) {
      $("#cover").show();
      $(".custom-header").css("z-index", "0");
    } else {
      $("#cover2").show();
      $(".custom-header").css("z-index", "0");
    }
  }

  $().ready(function () {
    $("#cover").click(function () {
      $(this).hide();
    });
  })

  function afterReward(){
    var tag = ${count != 0};
    if(tag){
      $("#cover2").show();
      $(".custom-header").css("z-index", "0");
      return;
    }
    window.location.href = "<c:url value='/answer/getAward/${examination.id}'/>";
  }
</script>

<script>

    var wx_share_title = "微信答题直充现金！"; //分享标题
    var wx_share_des = "参加答题赢取现金！可直接购买商品！";  //分享描述
    var wx_share_link = "http://dati.efeiyi.com/answer/assistAnswer.do/${examination.id}"; //分享的链接地址  //需要动态获取，而不是直接填写静态值
    var wx_share_imgUrl = "http://ec-efeiyi.oss-cn-beijing.aliyuncs.com/Clipboard%20Image.png"; //分享图片的url
    var wx_share_type = '';   //分享的类型   分享类型,music、video或link，不填默认为link
    var wx_share_dataUrl = ""; // 如果type是music或video，则要提供数据链接，默认为空
    var wx_api_list = ['onMenuShareAppMessage', 'onMenuShareTimeline'];    //需要使用的JS接口列表

</script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<%--<script src="<c:url value="/scripts/wap/js/weixin.js"/>"></script>--%>

<script>
    initWx("http://dati.efeiyi.com/wx/init.do");
    function initWx(initUrl) {
      var noncestr = "Wm3WZYTPz0wzccnW";
      var timestamp = new Date().getTime();
      var config = {
        debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        appId: 'wx2db1e25ab3dac836', // 必填，公众号的唯一标识
        timestamp: timestamp, // 必填，生成签名的时间戳
        nonceStr: noncestr, // 必填，生成签名的随机串
        signature: '',// 必填，签名，见附录1
        jsApiList: typeof wx_api_list != "undefined" ? wx_api_list : ['onMenuShareAppMessage', 'onMenuShareTimeline'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
      };
      ajaxRequest(initUrl, {
        "nonceStr": noncestr,
        "timestamp": timestamp,
        "callUrl": encodeURIComponent(window.location.href.split('#')[0])
      }, function (data) {
        config.signature = data;
        wx.config(config);


        wx.ready(function () {
          //目前只提供了分享给朋友的的接口，如果需要其他的分享接口功能，再往下添加
          wx.onMenuShareAppMessage({     //发送给朋友的接口
            title: wx_share_title, // 分享标题
            desc: wx_share_des, // 分享描述
            link: wx_share_link, // 分享链接
            imgUrl: wx_share_imgUrl, // 分享图标
            type: wx_share_type, // 分享类型,music、video或link，不填默认为link
            dataUrl: wx_share_dataUrl, // 如果type是music或video，则要提供数据链接，默认为空
            success: function () {
            },
            cancel: function () {
              // 用户取消分享后执行的回调函数
            }
          });

          wx.onMenuShareTimeline({
            title: wx_share_title, // 分享标题
            link: wx_share_link, // 分享链接
            imgUrl: wx_share_imgUrl, // 分享图标
            success: function () {
              // 用户确认分享后执行的回调函数
            },
            cancel: function () {
              // 用户取消分享后执行的回调函数
            }
          });
        });
        wx.error(function (res) {
          alert("微信验证失败");
        });

      }, function () {
      }, "get");

    }
</script>
</body>
</html>