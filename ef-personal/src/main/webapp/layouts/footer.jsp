<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DocType html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>传承人</title>

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

    <link rel="stylesheet" href="<c:url value="/scripts/assets/wap/css/amazeui.min.css"/> ">
    <link rel="stylesheet" href="<c:url value="/scripts/assets/wap/css/app.css"/>">
    <link rel="stylesheet" href="<c:url value="/scripts/assets/wap/css/style.css"/>">
    <script src="<c:url value="/scripts/assets/wap/js/amazeui.min.js"/>"></script>
    <script src="<c:url value="/scripts/assets/wap/js/jquery.min.js"/>"></script>
    <sitemesh:write property='head'/>
</head>
<body style="color:#F1F5F8;">
<header data-am-widget="footer" class="am-footer am-footer-default gather-am-1" data-am-footer="{  }" >
    <div class="am-footer-switch">
        <span class="am-footer-ysp" data-rel="mobile" data-am-modal="{target: '#am-switch-mode'}"><a href="#"><img src="images/logo.gif" width="61" height="23"></a></span>
    </div>
    <div class="am-footer-miscs ">
        <p>中国非遗电商平台·传承人官网</p>
    </div>
</header>
<div id="am-footer-modal" class="am-modal am-modal-no-btn am-switch-mode-m am-switch-mode-m-default">
    <div class="am-modal-dialog">
        <div class="am-modal-hd am-modal-footer-hd">
            <a href="javascript:void(0)" data-dismiss="modal" class="am-close am-close-spin "
               data-am-modal-close>&times;</a>
        </div>
        <div class="am-modal-bd">
            <span class="am-switch-mode-owner"></span>
            <span class="am-switch-mode-slogan">中国非遗电商平台·传承人官网</span>
        </div>
    </div>
</div>
<div data-am-widget="slider" class="am-slider am-slider-a1"  data-am-slider='{"directionNav":false}'>
    <ul class="am-slides">
        <li>
            <img src="http://s.amazeui.org/media/i/demos/bing-1.jpg">
        </li>
        <li>
            <img src="http://s.amazeui.org/media/i/demos/bing-2.jpg">
        </li>
        <li>
            <img src="http://s.amazeui.org/media/i/demos/bing-3.jpg">
        </li>
        <li>
            <img src="http://s.amazeui.org/media/i/demos/bing-4.jpg">
        </li>
    </ul>
</div>
<sitemesh:write property='body'/>
</body>
</html>
