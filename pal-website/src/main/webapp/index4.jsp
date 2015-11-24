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
    <title>商品搜索测试-移动</title>
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
    <link type="text/css" rel="stylesheet" href="/shop2015YD/css/amazeui.min.css?v=20150831">
    <link type="text/css" rel="stylesheet" href="/shop2015YD/css/app.css?v=20150831">
</head>
<body>

<article class="custom">
    <div class="search">
        <%--<form class="bd form-search" action="">--%>
        <form class="bd form-search" action="<c:url value='/search.do'/>" method="get">
            <a href="#arrow-lef" class="chevron-left"></a>
            <%--<input type="text" name="" id=""/>--%>
            <%--<input type="button" value="搜索"/>--%>
            <input type="text" placeholder="" name="q" id="q" value="">
            <input type="button" onclick="javascript:form.submit();" value="搜索">
            <input type="hidden" id="resultPage" name="resultPage" value="/search4"/>
            <input type="hidden" id="facetFields" name="facetFields" value="project_name"/>
            <input type="hidden" id="group" name="group" value="efeiyi"/>
            <input type="hidden" id="priceUD" name="priceUD" value="0"/>
        </form>
        <!-- //End--form-->
        <div class="bd search-item">
            <h3 class="bd">搜索记录</h3>
            <ul class="bd ul-list">
                <li><a href="#木板年画" title="木板年画">木板年画</a></li>
                <li><a href="#景德镇" title="景德镇">景德镇</a></li>
                <li><a href="#武夷山茶" title="武夷山茶">武夷山茶</a></li>
                <li><a href="#歙砚" title="歙砚">歙砚</a></li>
                <li><a href="#铜雕" title="铜雕">铜雕</a></li>
                <li><a href="#木板水印" title="木板水印">木板水印</a></li>
            </ul>
            <a class="bd btn" href="#清空搜索记录" title="清空搜索记录">清空搜索记录</a>
        </div>
        <!-- //End-->
        <div class="bd search-item">
            <h3>热搜</h3>
            <ul class="bd ul-list">
                <li><a href="#木板年画" title="木板年画">木板年画</a></li>
                <li><a href="#景德镇" title="景德镇">景德镇</a></li>
                <li><a href="#武夷山茶" title="武夷山茶">武夷山茶</a></li>
                <li><a href="#歙砚" title="歙砚">歙砚</a></li>
                <li><a href="#铜雕" title="铜雕">铜雕</a></li>
                <li><a href="#木板水印" title="木板水印">木板水印</a></li>
            </ul>
        </div>
        <!-- //End-->
    </div>
</article>

<div class="login-reg">
    <div class="bd logined">李先生8899，<a class="btn-exit" href="#退出">退出</a></div>
    <a href="#login" class="btn-login" title="登录">登&nbsp;&nbsp;&nbsp;&nbsp;录</a>
    <a href="#reg" class="btn-reg">注&nbsp;&nbsp;&nbsp;&nbsp;册</a>
</div>
<!--//End--login-reg-->
<footer class="bd footer">
    <div class="bd info">
        <a class="icon"></a>
        <div class="txt">中&nbsp;&nbsp;国&nbsp;&nbsp;非&nbsp;&nbsp;遗&nbsp;&nbsp;电&nbsp;&nbsp;商&nbsp;&nbsp;平&nbsp;&nbsp;台</div>
        <div class="wechat"></div>
        <div class="txt">关注微信公众号</div>
        <div class="txt">领取超值代金券</div>
    </div>
    <div class="bd copyright">京ICP备15032511号-1</div>
</footer>
<!--//End--footer-->

<!--[if (gte IE 9)|!(IE)]><!-->
<script src="/shop2015YD/js/jquery.min.js"></script>
<!--<![endif]-->
<!--[if lte IE 8 ]>
<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="assets/js/amazeui.ie8polyfill.min.js"></script>
<![endif]-->
<script src="/shop2015YD/js/amazeui.min.js"></script>
<!--自定义js--Start-->
<script src="/shop2015YD/js/system.js?v=20150831"></script>
<!--自定义js--End-->
</body>
</html>
