<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/1/14
  Time: 13:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
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
    <div class="add-head">你的好友正拼尽全力帮你解答呢，稍等片刻哦。刷新页面查看求助进度，您也可以稍后查看（关注公众号“e飞蚁非遗电商”→菜单-非遗知识-答题赚红包-查看进度</div>
    <div class="wea-bg"></div>
  </div>
  <div class="topic ae">
    <div class="plan">
      <ul class="plan-box">
        <c:set var="count" value="0" scope="page"/>
        <c:forEach items="${examination.participationRecordList}" var="ppr">
          <c:if test="${ppr.recordType == 2}">
            <c:set var="count" value="${count + 1}" scope="page"/>
            <li>
              <div class="plan-pic"><a href="#"><img src="${ppr.wxCalledRecord.callback}"></a></div>
              <div class="plan-text">
                <p>${ppr.wxCalledRecord.requestSource}</p><%--微信好友昵称--%>
                <p class="correct">
                  <c:if test="${ppr.answer == '1'}">
                    <font color="green">回答正确!</font>
                  </c:if>
                  <c:if test="${ppr.answer == '2'}">
                    <font color="red">回答错误!</font>
                  </c:if>
                </p>
              </div>
            </li>
          </c:if>
        </c:forEach>
        <%--<li>
          <div class="plan-pic"><a href="#"><img src="<c:url value='/scripts/wap/upload/mx-1-1.png'/>"></a></div>
          <div class="plan-text">
            <p>感谢你的温柔</p>
            <p>选择了答案C 34项，回答正确！</p>
          </div>
        </li>
        <li>
          <div class="plan-pic"><a href="#"><img src="<c:url value='/scripts/wap/upload/mx-1-1.png'/>"></a></div>
          <div class="plan-text">
            <p>感谢你的温柔</p>
            <p>选择了答案C 34项，回答正确！</p>
          </div>
        </li>
        <li>
          <div class="plan-pic"><a href="http://www.baidu.com"><img src="<c:url value='/scripts/wap/upload/mx-1-1.png'/>"></a></div>
          <div class="plan-text">
            <p>感谢你的温柔</p>
            <p>选择了答案C 34项，回答正确！</p>
          </div>
        </li>
        <li>
          <div class="plan-pic"><a href="#"><img src="<c:url value='/scripts/wap/upload/mx-1-1.png'/>"></a></div>
          <div class="plan-text">
            <p>感谢你的温柔</p>
            <p>选择了答案C 34项，回答正确！</p>
          </div>
        </li>--%>
        <!-- 没有小伙伴答题状态-->
        <c:if test="${count == 0}">
          <li>
            <div class="plan-pic"><a href="#"><img src="<c:url value='/scripts/wap/upload/fx-x-1.png'/>"></a></div>
            <div class="plan-text">
              <p>还没有小伙伴帮你答题</p>
            </div>
          </li>
        </c:if>
      </ul>
      <div class="txc-btn">
        <a href="#" class="wechat-btn cart-ft share">继&nbsp;续&nbsp;分&nbsp;享</a>
        <a href="<c:url value='/wx/shareReturn.do?examId=${examination.id}'/>" class="wechat-btn cart-ft">查&nbsp;看&nbsp;答&nbsp;案</a>

        <div id="cover" style="display: none;">
          <em class="bg"></em>
          <img src="<c:url value='/scripts/wap/upload/guide-share.png'/>" class="share-picture" alt="">
        </div>

        <div id="cover2" style="display: none" style="text-align: center">
          <div class="text-co2">
            <strong class="cov-titie">提示</strong>
            <p class="covtext">请在微信浏览器中打开本页面！</p>
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

  var wx_share_title = "【第${examination.examinationEdition.name}期非遗答题赚红包】${sessionScope.nickname}需要你的帮助！"; //分享标题
  var wx_share_des = "${sessionScope.nickname}在玩非遗知识闯关，有道题答不上来，需要你的帮助，快来帮ta答题吧！";  //分享描述
  var wx_share_link = "http://dati.efeiyi.com/answer/assistAnswer/${examination.id}"; //分享的链接地址  //需要动态获取，而不是直接填写静态值
  //  var wx_share_link = "http://dati.efeiyi.com/wx/start.do";
  var wx_share_imgUrl = "${sessionScope.headimgurl}"; //分享图片的url
  var wx_share_type = '';   //分享的类型   分享类型,music、video或link，不填默认为link
  var wx_share_dataUrl = ""; // 如果type是music或video，则要提供数据链接，默认为空
  var wx_api_list = ['onMenuShareAppMessage', 'onMenuShareTimeline'];    //需要使用的JS接口列表

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
  });

  initWx("http://dati.efeiyi.com/wx/init.do","<c:url value='/wx/shareExamination/${examination.id}'/>",wx_share_title,wx_share_des,wx_share_link,wx_share_imgUrl,wx_share_type,wx_share_dataUrl,wx_api_list);
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