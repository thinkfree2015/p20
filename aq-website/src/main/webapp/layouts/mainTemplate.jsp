<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.ming800.core.util.HttpUtil" %>
<html>
<head>
    <title><sitemesh:write property='title'/></title>
    <%@include file="mobileMainHeader.jsp" %>
    <sitemesh:write property='head'/>
</head>
<body>
<sitemesh:write property='body'/>
<!--[if (gte IE 9)|!(IE)]><!-->
<script src="<c:url value="/scripts/wap/js/jquery.min.js"/>"></script>
<!--<![endif]-->
<!--[if lte IE 8 ]>
<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="assets/js/amazeui.ie8polyfill.min.js"></script>
<![endif]-->
<script src="<c:url value="/scripts/wap/js/amazeui.min.js"/>"></script>
<!--自定义js--Start-->
<script src="<c:url value="/scripts/wap/js/system.js?v=20150831"/>"></script>
<script src="<c:url value="/scripts/wap/js/myorder.js?v=20150831"/>"></script>
<!--自定义js--End-->
</body>
</html>
