<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/1/13
  Time: 11:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <c:if test="${examination.status != 2}">
            <c:set var="count" value="0" scope="page"/>
            <c:forEach items="${eqList}" var="examQuestion">
                <c:if test="${examQuestion.answerStatus == 2}">
                    <c:set var="count" value="${count = count + 1}" scope="page"/>
                </c:if>
            </c:forEach>
            <div class="add-head">
                <c:if test="${count != 0}">
                    答错了，让我安静的哭一会儿，好桑心！虽然我帮不了忙，但我们还有好朋友，等我的好消息吧
                </c:if>
                <c:if test="${count == 0}">
                    答对啦！果然是颜值越高，智商越高呢，真羡慕你，你一定颜值爆表啦！
                </c:if>
            </div>
        </c:if>
        <c:if test="${examination.status == 2}">
            <div class="add-head">
                不好意思，您来晚了，已有好友帮助完成！
            </div>
        </c:if>
        <div class="wea-bg"></div>
    </div>
    <div class="topic ae">
        <div class="plan">
            <c:if test="${examination.status != 2}">
                <ul class="plan-issue">
                    <c:forEach items="${eqList}" var="examQuestion">
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

            </c:if>
            <div class="txc-btn">
                <c:if test="${examination.status != 2}">
                    <c:if test="${count != 0}">
                        <a href="#" onclick="answerHelp()"
                           class="wechat-btn cart-ft share">我&nbsp;要&nbsp;求&nbsp;助</a>
                        <%--分享链接  <c:url value="/wx/startHelp.do?examId=${examination.id}"/>  --%>
                    </c:if>
                    <c:if test="${count == 0}">
                        <a href="#" onclick="answerHelp()"
                           class="wechat-btn cart-ft share">我&nbsp;要&nbsp;分&nbsp;享</a>
                        <%--分享链接  <c:url value="/wx/start.do"/>  --%>
                    </c:if>
                </c:if>
                <a href="<c:url value='/wx/start.do'/>" class="wechat-btn">答&nbsp;题&nbsp;赚&nbsp;红&nbsp;包</a>

                <div id="cover" style="display: none;">
                    <em class="bg"></em>
                    <img src="/scripts/wap/upload/guide-share.png" class="share-picture" alt="">
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

    var wx_share_title = "【求助】${sessionScope.nickname}需要你的帮助！"; //分享标题
    var wx_share_des = "${sessionScope.nickname}在玩非遗知识闯关，有道题答不上来，需要你的帮助，快来帮ta答题吧！";  //分享描述
    <%--var wx_share_link = "http://dati.efeiyi.com/wx/start.do?examinationId=${examination.id}"; //分享的链接地址  //需要动态获取，而不是直接填写静态值--%>
    var wx_share_link = "http://dati.efeiyi.com/answer/assistAnswer/${examination.id}"; //分享的链接地址  //需要动态获取，而不是直接填写静态值
    var wx_share_imgUrl = "${sessionScope.headimgurl}"; //分享图片的url
    var wx_share_type = '';   //分享的类型   分享类型,music、video或link，不填默认为link
    var wx_share_dataUrl = ""; // 如果type是music或video，则要提供数据链接，默认为空
    var wx_api_list = ['onMenuShareAppMessage', 'onMenuShareTimeline'];    //需要使用的JS接口列表
    <c:if test="${count == 0}">
    var wx_share_link = "http://dati.efeiyi.com/wx/start.do"; //分享的链接地址  //需要动态获取，而不是直接填写静态值
    var wx_share_title = "${sessionScope.nickname}完成了在非遗知识闯关答题，敢来挑战吗？"; //分享标题
    var wx_share_des = "${sessionScope.nickname}在玩非遗知识闯关，涨姿势还有钱赚，敢来挑战Ta吗？";  //分享描述
    </c:if>
    function isWeiXin() {
        var ua = window.navigator.userAgent.toLowerCase();
        if (ua.match(/MicroMessenger/i) == 'micromessenger') {
            return true;
        } else {
            return false;
        }
    }

    function answerHelp() {
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
    });

    initWx("http://dati.efeiyi.com/wx/init.do", "<c:url value='/wx/shareExamination/${examination.id}'/>", wx_share_title, wx_share_des, wx_share_link, wx_share_imgUrl, wx_share_type, wx_share_dataUrl, wx_api_list);
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