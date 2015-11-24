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
    <title>检索结果列表-移动</title>
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
    <link type="text/css" rel="stylesheet" href="/shop2015YD/css/myorder.css?v=20150831">
</head>
<body>
<header class="am-header custom-header newheader">
    <div class="am-header-left am-header-nav">
        <a href="javascript:window.history.back();" class="chevron-left"></a>
    </div>
    <!-- //End--chevron-left-->
    <div class="newsearch">
        <%--<form>
            <input class="newsebox" placeholder="茶叶罐">
            <input type="submit" class="newsebut" value="搜">
        </form>--%>
            <form class="bd form-search" action="<c:url value='/search.do'/>" method="get">
                <input type="text" class="newsebox" placeholder="" name="q" id="q" value="<c:if test="${searchParamBean.q != '*'}">${searchParamBean.q}</c:if>">
                <input type="submit" class="newsebut" value="搜">
                <input type="hidden" id="resultPage" name="resultPage" value="/search4"/>
                <input type="hidden" id="facetFields" name="facetFields" value="project_name"/>
                <input type="hidden" id="group" name="group" value="efeiyi"/>
                <input type="hidden" id="priceUD" name="priceUD" value="0"/>
            </form>
    </div>
    <!-- //End--title-->
    <div class="am-header-right am-header-nav">
        <a href="#chevron-right" class="chevron-right" id="menu">
            <i class="line"></i>
        </a>
    </div>
    <div class="menu-list">
        <ul class="bd">
            <li><a href="" title="购物车">购物车</a></li>
            <li class="active"><a href="" title="个人中心">个人中心</a></li>
            <li><a href="" title="分类">分类</a></li>
            <li><a href="" title="传承人">传承人</a></li>
        </ul>
    </div>
</header>
<!--//End--header-->
<article>
    <div id="result" class="bd search-sort">
        <ul class="bd tnav">
            <li>
                <a href="javascript:void(0);"
                   onclick="facetForward('<c:url value="/search.do?q=${searchParamBean.q}&resultPage=${searchParamBean.resultPage}&queryFacet=${searchParamBean.queryFacet}&priceUD=0"/>')"
                   title="综 合">综 合</a>
            </li>
            <li>销 量</li>
            <li><%--价 格<i class="icon-a1"></i>--%>
                <a href="javascript:void(0)" onclick="sortForward4('product_model_price')">价  格
                    <i <%--class="icon-a1"--%>
                       class="<c:if test='${searchParamBean.priceUD == 0}'>icon-a1</c:if><c:if test='${searchParamBean.priceUD != 0}'>icon-a2</c:if>"></i>
                </a>
            </li>
            <li>筛 选</li>
        </ul>
        <!-- //End-->
        <ul class="ul-list">
            <%-- 没有获取到数据时显示 --%>
            <c:if test="${empty searchParamBean.searchResultList}">
                抱歉，没有找到“${searchParamBean.q}”的搜索结果，您可以换个关键词试试。
            </c:if>
            <%-- 数据显示 --%>
            <c:forEach items="${searchParamBean.searchResultList}" var="result">
                <li>
                    <a href="http://192.168.1.57/ef-website/product/productModel/${result.id}" title="">
                        <img src="http://ec-efeiyi.oss-cn-beijing.aliyuncs.com/${result.picture_url}" alt="">
                        <p class="name">${result.product_name}<c:if test="${result.frequent != 1}">[${result.specification}]</c:if></p>
                        <p class="price"><em>￥</em>${result.product_model_price}</p>
                    </a>
                </li>
            </c:forEach>
        </ul>
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

<script type="text/javascript">
    var facets = "${searchParamBean.facetFieldJson}";
    function facetForward(url) {
        window.location.href = url + "&facetFieldJson=" + facets + "&queryFacetJson=${searchParamBean.queryFacetJson}&group=efeiyi" ;
    }

    var priceUD = ${searchParamBean.priceUD};
    function sortForward4(sortField) {
        var sortOrder = "asc";
        if(priceUD == 0){
            sortOrder = "";
            priceUD = 1;
        }else{
            priceUD = 0;
        }
        var url = "<c:url value='/search.do?q=${searchParamBean.q}&resultPage=${searchParamBean.resultPage}&queryFacet=${searchParamBean.queryFacet}&sortField='/>" + sortField + "&sortOrder=" + sortOrder + "&priceUD=" + priceUD;
        facetForward(url)
    }

    var dynamicTag = true;
    window.onscroll = function(){
        var bch = document.body.clientHeight;
        var dch = document.documentElement.clientHeight;
        var ch = 0;//窗口可视高度

        if(bch && dch){
            ch = (bch < dch)? bch : dch;
        }else{
            ch = (bch > dch)? bch : dch;
        }

        var bsh = document.body.scrollHeight;
        var dsh = document.documentElement.scrollHeight;
        var sh = Math.max(bsh, dsh);//页面实际高度

        var st = document.documentElement.scrollTop || document.body.scrollTop;//滚动条距离顶部高度
        var sf = sh-ch-st;//滚动条距离底部高度=实际高度-可视窗口高度-滚动条距离顶部高度
        if(sf <= 500 && dynamicTag){
            AjaxGetSearch();
        }
    };

    var pageIndex = ${searchParamBean.pageEntity.index};
    function AjaxGetSearch(){
        pageIndex = pageIndex + 1;
        var size = ${searchParamBean.pageEntity.size};
        var q = "${searchParamBean.q}";//记录检索关键字
        var resultPage = "/dynamicResult";//结果页面路径
        var queryFacet = "${searchParamBean.queryFacet}";//当前查询条件
        var facetFieldJson = "${searchParamBean.facetFieldJson}";//分类 存储在Json字符串中
        var queryFacetJson = "${searchParamBean.queryFacetJson}";//所有查询条件 存储在Json字符串中
        var sortField = "${searchParamBean.sortField}";//排序字段
        var sortOrder = "${searchParamBean.sortOrder}";//排序方式desc/asc
        var group = "efeiyi";//common配置文件分组
        var priceUD = "${searchParamBean.priceUD}";//价格排序图标方向
        var url1 = "/search.do?q="+q+"&resultPage="+resultPage+
                  "&queryFacet="+queryFacet+"&facetFieldJson="+facetFieldJson+
                  "&queryFacetJson="+queryFacetJson+"&sortField="+sortField+
                  "&sortOrder="+sortOrder+"&group="+group+"&priceUD="+priceUD+
                  "&pageEntity.index="+pageIndex+"&pageEntity.size="+size;

        if(dynamicTag) {
            dynamicTag = false;
            $.ajax({
                type: "get",
                url: url1,
                cache: false,
                success: function (data) {
                    data = data.replace( /^\s*/, '');
                    if (data != "") {
                        document.getElementById("result").innerHTML += data;
                        dynamicTag = true;
                    }
                },
                error: function (message, a, b) {
                    alert(message.responseText + a + b);
                    dynamicTag = true;
                }
            });
        }
    }
</script>

</body>
</html>
