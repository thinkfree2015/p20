<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/1/7
  Time: 17:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <div class="wea-bg"></div>
    </div>
    <div class="addae ae">
        <c:forEach items="${examination.examinationQuestionList}" var="examQuestion" varStatus="status1">
            <div class="topic addtopic ae <c:if test="${examQuestion.questionOrder == 1}">active</c:if>">
                <h5>第<span>${examQuestion.questionOrder}</span>题</h5>
                    <%--<div class="topic-img ae"><img src="<c:url value='/scripts/wap/upload/mypurchase02.png'/>"></div>--%>
                <div class="topic-img ae"><img src="<c:url value='${examQuestion.question.pictureUrl}'/>"></div>
                <div class="topic-txt ae">
                    <p>${examQuestion.question.questionContent}</p>
                    <c:forEach items="${randomAnswerList}" var="randomAnswerMap" begin="${status1.index}" end="${status1.index}">
                    <c:forEach items="${randomAnswerMap}" var="answerEntry">
                    <a href="javascript:" onclick="answerQuestion('${answerEntry.key}')"
                       class="bg-link">${answerEntry.value}
                        </c:forEach>
                        </c:forEach>
                            <%--<a href="javascript:" onclick="answerQuestion('A')" class="bg-link">${examQuestion.question.answerA}</a>--%>
                            <%--<a href="javascript:" onclick="answerQuestion('B')" class="bg-link">${examQuestion.question.answerB}</a>--%>
                            <%--<a href="javascript:" onclick="answerQuestion('C')" class="bg-link">${examQuestion.question.answerC}</a>--%>
                            <%--<a href="javascript:" onclick="answerQuestion('D')" class="bg-link">${examQuestion.question.answerD}</a>--%>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<script>
    var answerList = "";
    var examId = "${examination.id}";
    var consumerId = "${examination.consumer.id}";
    function answerQuestion(val) {
        if (answerList == "") {
            answerList = val;
        } else {
            answerList = answerList + "," + val;
        }
    }

    $(function () {
        //微信问题选项
        (function () {
            var _this = $(".addae").index() - 1;
            var _index = $(".topic").length;
            $(".topic .topic-txt.topic-txt .bg-link").click(function () {
                _this++;
                $(".topic").eq(_this).addClass("active").siblings().removeClass("active");
                if (_this == _index) {
                    var url = "/" + examId + "/" + answerList + "/" + consumerId;
                    window.location.href = "<c:url value='/answer/commitAnswer.do'/>" + url;
                }
            });
        })();
    });
</script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="<c:url value="/scripts/wap/js/weixin.js"/>"></script>
<script>
    var wx_share_imgUrl = "${sessionScope.headimgurl}"; //分享图片的url
    var wx_share_type = '';   //分享的类型   分享类型,music、video或link，不填默认为link
    var wx_share_dataUrl = ""; // 如果type是music或video，则要提供数据链接，默认为空
    var wx_api_list = ['onMenuShareAppMessage', 'onMenuShareTimeline'];    //需要使用的JS接口列表
    var wx_share_link = "http://dati.efeiyi.com/wx/start.do";
    var wx_share_title = "${sessionScope.nickname}在非遗知识闯关答题，敢来挑战吗？"; //分享标题
    var wx_share_des = "${sessionScope.nickname}在玩非遗知识闯关，涨姿势还有钱赚，敢来挑战Ta吗？";  //分享描述
    initWx("http://dati.efeiyi.com/wx/init.do", "", wx_share_title, wx_share_des, wx_share_link, wx_share_imgUrl, wx_share_type, wx_share_dataUrl, wx_api_list);
</script>
</body>
</html>