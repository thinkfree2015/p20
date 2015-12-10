<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ming800" uri="http://java.ming800.com/taglib" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html class="no-js">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="description" content="">
  <meta name="keywords" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <title>分类筛选-移动</title>
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
<header class="am-header custom-header">
  <div class="am-header-left am-header-nav">
    <a href="javascript:window.history.back();" class="chevron-left"></a>
  </div>
  <h1 class="am-header-title">筛选</h1>
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
<article>
  <div class="wh search">
    <div class="bd search-item">
      <h3 class="bd">分类</h3>
      <ul id="classify" class="bd ul-list">
        <li <c:if test="${empty searchParamBean.queryFacet}">class="active"</c:if>><a href="javascript:void(0);" title="全 部" onclick="classify(this, '')">全 部</a></li>
        <c:forEach items="${searchParamBean.facetFieldsMap}" var="facetFields">
          <c:forEach items="${facetFields.value}" var="facetEntry">
            <li <c:if test="${not empty searchParamBean.queryFacet && fn:contains(searchParamBean.queryFacet, facetEntry.key)}">class="active"</c:if>>
              <a href="javascript:void(0);" onclick="classify(this, '${facetEntry.key}')">${facetEntry.key}</a>
            </li>
          </c:forEach>
        </c:forEach>
      </ul>
    </div>
    <div class="bd search-item">
      <h3 class="bd">价格</h3>
      <ul id="price" class="bd ul-list">
        <li <c:if test="${empty searchParamBean.fq || searchParamBean.fq == 'product_model_price:[* TO *]'}">class="active"</c:if>>
          <a href="javascript:void(0);" onclick="price(this, '')" title="全 部">全 部</a>
        </li>
        <li <c:if test="${searchParamBean.fq == 'product_model_price:[* TO 100]'}">class="active"</c:if>>
          <a href="javascript:void(0);" onclick="price(this, '[* TO 100]')" title="百元以下">百元以下</a>
        </li>
        <li <c:if test="${searchParamBean.fq == 'product_model_price:[100 TO 1000]'}">class="active"</c:if>>
          <a href="javascript:void(0);" onclick="price(this, '[100 TO 1000]')" title="100~1000">100~1000</a>
        </li>
        <li <c:if test="${searchParamBean.fq == 'product_model_price:[1000 TO 5000]'}">class="active"</c:if>>
          <a href="javascript:void(0);" onclick="price(this, '[1000 TO 5000]')" title="1000~5000">1000~5000</a>
        </li>
        <li <c:if test="${searchParamBean.fq == 'product_model_price:[5000 TO 10000]'}">class="active"</c:if>>
          <a href="javascript:void(0);" onclick="price('[5000 TO 10000]')" title="5000~1万">5000~1万</a>
        </li>
        <li <c:if test="${searchParamBean.fq == 'product_model_price:[10000 TO 100000]'}">class="active"</c:if>>
          <a href="javascript:void(0);" onclick="price(this, '[10000 TO 100000]')" title="1万~10万">1万~10万</a>
        </li>
        <li <c:if test="${searchParamBean.fq == 'product_model_price:[100000 TO *]'}">class="active"</c:if>>
          <a href="javascript:void(0);" onclick="price(this, '[100000 TO *]')" title="10万以上">10万以上</a>
        </li>
      </ul>
    </div>
    <div class="bd search-item">
      <h3 class="bd">用途</h3>
      <ul class="bd ul-list">
        <li class="active"><a href="#" title="">全 部</a></li>
        <li><a href="#" title="">家居摆设</a></li>
        <li><a href="#" title="">生日礼物</a></li>
        <li><a href="#" title="">创 意</a></li>
        <li><a href="#" title="">爱 情</a></li>
        <li><a href="#" title="">收 藏</a></li>
      </ul>
    </div>
  </div>
  <a class="bd search-item-btn" href="javascript:void(0);" onclick="confirm()">确    定</a>
</article>

<div class="login-reg">
  <div class="bd logined">李先生8899，<a class="btn-exit" href="#退出">退出</a></div>
  <a href="#login" class="btn-login" title="登录">登&nbsp;&nbsp;&nbsp;&nbsp;录</a>
  <a href="#reg" class="btn-reg">注&nbsp;&nbsp;&nbsp;&nbsp;册</a>
</div>
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
<script src="/shop2015YD/js/jquery.min.js"></script>
<script src="/shop2015YD/js/amazeui.min.js"></script>
<script src="/shop2015YD/js/system.js?v=20150831"></script>

<script type="text/javascript">
  var q = "${searchParamBean.q}";//记录检索关键字
  var resultPage = "/search4";//结果页面路径
  var queryFacet = "${searchParamBean.queryFacet}";//当前查询条件
  var facetFieldJson = "${searchParamBean.facetFieldJson}";//分类 存储在Json字符串中
  var queryFacetJson = "${searchParamBean.queryFacetJson}";//所有查询条件 存储在Json字符串中
  var sortField = "${searchParamBean.sortField}";//排序字段
  var sortOrder = "${searchParamBean.sortOrder}";//排序方式desc/asc
  var group = "efeiyi";//common配置文件分组
  var fq = "${searchParamBean.fq}";//价格区间
  var priceUD = "${searchParamBean.priceUD}";//价格排序图标方向

  function classify(obj, val){
    $("#classify li").removeClass("active");
    $(obj).parent().addClass("active");
    if(val != ""){
      queryFacet = "project_name:" + val;
    }else{
      queryFacet = "";
    }
  }

  function price(obj, val){
    $("#price li").removeClass("active");
    $(obj).parent().addClass("active");
    if(val != ""){
      fq = "product_model_price:" + val;
    }else{
      fq="";
    }
  }

  function confirm(){
    var url = "/search.do?q="+q+"&resultPage="+resultPage+
            "&queryFacet="+queryFacet+"&facetFieldJson="+facetFieldJson+
            "&queryFacetJson="+queryFacetJson+"&sortField="+sortField+
            "&sortOrder="+sortOrder+"&group="+group+"&priceUD="+priceUD+"&fq="+fq;
    window.location.href = url;
  }
</script>

</body>
</html>
