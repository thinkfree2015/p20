<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ming800" uri="http://java.ming800.com/taglib" %>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>商品检索测试</title>
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
    <link type="text/css" rel="stylesheet" href="../../shop2015/css/amazeui.min.css?v=20150831">
    <link type="text/css" rel="stylesheet" href="../../shop2015/css/app.css?v=20150831">
    <link type="text/css" rel="stylesheet" href="../../shop2015/css/myorder.css?v=20150831">
</head>
<body>
<div class="topbar wh" data-am-sticky>
    <div class="hd">
        <ul class="ul-item">
            <li class="btn-top-nav">
                <strong>您好,189****4791</strong>
                <a href="" title="退出">退出</a>
                <div class="ul-details">
                    <a href="#">个人信息</a>
                    <a href="#">我的卡券</a>
                </div>
            </li>
            <li><a href="" title="请登录">请登录</a></li>
            <li><a href="" title="快速注册">快速注册</a></li>
            <li><a href="" title="商家入驻">商家入驻</a></li>
            <li class="btn-top-wechat">
                <a title="手机e飞蚁">手机e飞蚁</a>
                <span class="top-wechat"></span>
            </li>
            <li class="cart">
                <a href="" title="购物车"><i class="icon"></i>购物车</a>
                <span class="tips"><em>0</em></span>
            </li>
        </ul>
    </div>
</div>
<!-- //End--topbar-->
<div class="header-new wh">
    <div class="hd">
        <div class="logo"><a class="icon" href="" target="_blank" title="e飞蚁-爱非遗"></a></div>
        <form action="<c:url value='/search.do'/>" method="get">
            <input type="text" class="txt" placeholder="" name="q" id="q" value="">
            <input type="submit" class="icon-new btn" value="">
            <%-- 全文检索测试 --%>
            <input type="hidden" id="resultPage" name="resultPage" value="/search3"/>
            <input type="hidden" id="facetFields" name="facetFields" value="project_name"/>
            <input type="hidden" id="group" name="group" value="efeiyi"/>
            <%-- End全文检索测试 --%>
            <div class="keywords">
                <a href="">剪纸</a>
                <a href="">景泰蓝</a>
                <a href="">景德镇</a>
                <a href="">二锅头</a>
                <a href="">徽州三雕</a>
                <a href="">藏药</a>
                <a href="">十全大补丸</a>
            </div>
        </form>
    </div>
</div>
<!-- //End--header-->
<div class="wh nav-new bdb-1">
    <div class="hd">
        <div class="cate">
            <div class="ld"><h2>非遗商品分类<i class="icon-new"></i></h2></div>
            <div class="allsort" style="display: none;">
                <div class="item">
                    <h3><a href="" title="">陶冶烧造</a><i class="icon-new icon-link"></i></h3>
                    <div class="i-mc" style="display: none;">
                        <div class="links">
                            <a href="" title="">陶冶烧造</a>
                            <a href="" title="">陶器</a>
                            <a href="" title="">景泰蓝</a>
                            <a href="" title="">紫砂紫砂紫砂</a>
                            <a href="" title="">瓷器</a>
                            <a href="" title="">琉璃</a>
                            <a href="" title="">陶器</a>
                            <a href="" title="">景泰蓝</a>
                            <a href="" title="">紫砂</a>
                            <a href="" title="">瓷器瓷器瓷器</a>
                            <a href="" title="">琉璃</a>
                            <a href="" title="">陶器</a>
                            <a href="" title="">景泰蓝</a>
                            <a href="" title="">紫砂</a>
                            <a href="" title="">瓷器瓷器瓷器</a>
                        </div>
                        <!-- //End--links-->
                        <div class="reco">
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                        </div>
                    </div>
                </div>
                <div class="item">
                    <h3><a href="" title="">木作编扎</a><i class="icon-new icon-link"></i></h3>
                    <div class="i-mc" style="display: none;">
                        <div class="links">
                            <a href="" title="">木作编扎</a>
                        </div>
                        <!-- //End--links-->
                        <div class="reco">
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                        </div>
                    </div>
                </div>
                <div class="item">
                    <h3><a href="" title="">指染印绣</a><i class="icon-new icon-link"></i></h3>
                    <div class="i-mc" style="display: none;">
                        <div class="links">
                            <a href="" title="">指染印绣</a>
                        </div>
                        <!-- //End--links-->
                        <div class="reco">
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                        </div>
                    </div>
                </div>
                <div class="item">
                    <h3><a href="" title="">传统美术</a><i class="icon-new icon-link"></i></h3>
                    <div class="i-mc" style="display: none;">
                        <div class="links">
                            <a href="" title="">传统美术</a>
                        </div>
                        <!-- //End--links-->
                        <div class="reco">
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                        </div>
                    </div>
                </div>
                <div class="item">
                    <h3><a href="" title="">琢玉成器</a><i class="icon-new icon-link"></i></h3>
                    <div class="i-mc" style="display: none;">
                        <div class="links">
                            <a href="" title="">琢玉成器</a>
                        </div>
                        <!-- //End--links-->
                        <div class="reco">
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                        </div>
                    </div>
                </div>
                <div class="item">
                    <h3><a href="" title="">金石錾锻</a><i class="icon-new icon-link"></i></h3>
                    <div class="i-mc" style="display: none;">
                        <div class="links">
                            <a href="" title="">金石錾锻</a>
                        </div>
                        <!-- //End--links-->
                        <div class="reco">
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                        </div>
                    </div>
                </div>
                <div class="item">
                    <h3><a href="" title="">如胶似漆</a><i class="icon-new icon-link"></i></h3>
                    <div class="i-mc" style="display: none;">
                        <div class="links">
                            <a href="" title="">如胶似漆</a>
                        </div>
                        <!-- //End--links-->
                        <div class="reco">
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                        </div>
                    </div>
                </div>
                <div class="item">
                    <h3><a href="" title="">文房四宝</a><i class="icon-new icon-link"></i></h3>
                    <div class="i-mc" style="display: none;">
                        <div class="links">
                            <a href="" title="">文房四宝</a>
                        </div>
                        <!-- //End--links-->
                        <div class="reco">
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                        </div>
                    </div>
                </div>
                <div class="item">
                    <h3><a href="" title="">饕餮美食</a><i class="icon-new icon-link"></i></h3>
                    <div class="i-mc" style="display: none;">
                        <div class="links">
                            <a href="" title="">饕餮美食</a>
                        </div>
                        <!-- //End--links-->
                        <div class="reco">
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                        </div>
                    </div>
                </div>
                <div class="item">
                    <h3><a href="" title="">茗品佳酿</a><i class="icon-new icon-link"></i></h3>
                    <div class="i-mc" style="display: none;">
                        <div class="links">
                            <a href="" title="">茗品佳酿</a>
                        </div>
                        <!-- //End--links-->
                        <div class="reco">
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                        </div>
                    </div>
                </div>
                <div class="item">
                    <h3><a href="" title="">医药养生</a><i class="icon-new icon-link"></i></h3>
                    <div class="i-mc" style="display: none;">
                        <div class="links">
                            <a href="" title="">医药养生</a>
                        </div>
                        <!-- //End--links-->
                        <div class="reco">
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="../shop2015/upload/B010102_03.jpg" alt=""></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="items">
            <a href="">首页</a>
            <a href="">品牌专区</a>
            <a href="">各地非遗</a>
            <a href="">大师</a>
            <a href="">工艺</a>
        </div>
    </div>
</div>
<!--//End--nav-new-->
<div class="wh fly-nev">
    <div class="hd">
        <div class="searching ae">
            <ul class="sech-look1">
                <li><a href="#">检索结果</a></li>
                <li class="icon"></li>
                <li class="active"><a href="#">${searchParamBean.q}</a></li>
            </ul>
        </div>
        <div class="searching ae">
            <div class="sech-look2">
                <!--一个类-->
                <div class="loog-ground ae">
                    <div class="look-head">
                        <span>分类：</span>
                        <c:if test="${empty searchParamBean.queryFacet}">
                            <strong><a href="<c:url value="/search.do?q=${searchParamBean.q}&resultPage=${searchParamBean.resultPage}&facetFieldJson=${searchParamBean.facetFieldJson}&priceUD=0&group=efeiyi" />">全部</a></strong>
                        </c:if>
                        <c:if test="${not empty searchParamBean.queryFacet}">
                            <strong><a href="<c:url value="/search.do?q=${searchParamBean.q}&resultPage=${searchParamBean.resultPage}&facetFieldJson=${searchParamBean.facetFieldJson}&priceUD=0&group=efeiyi" />">全部</a></strong>
                        </c:if>
                    </div>
                    <div class="look-body">

                        <c:forEach items="${searchParamBean.facetFieldsMap}" var="facetFields">
                            <c:forEach items="${facetFields.value}" var="facetEntry">
                                <a href="javascript:void(0);"
                                   class="<c:if test="${searchParamBean.queryFacet == '${facetFields.key:facetEntry.key}'}"></c:if>"
                                   onclick="facetForward('<c:url value="/search.do?q=${searchParamBean.q}&resultPage=${searchParamBean.resultPage}&queryFacet=project_name:${facetEntry.key}&priceUD=0"/>')">
                                   ${facetEntry.key}

                                </a>
                            </c:forEach>
                        </c:forEach>
                        <%--<a href="#">龙泉青瓷</a>
                        <a href="#">紫砂</a>
                        <a href="#">铜雕</a>
                        <a href="#">汝瓷</a>
                        <a href="#">建盏</a>--%>
                    </div>
                </div>
                <!--一个类-->
                <div class="loog-ground ae">
                    <div class="look-head">
                        <span>分类：</span>
                        <c:if test="${empty searchParamBean.fq || searchParamBean.fq == 'product_model_price:[* TO *]'}">
                            <strong><a href="javascript:void(0);" onclick="priceSectionForward('[* TO *]')">全部</a></strong>
                        </c:if>
                        <c:if test="${not empty searchParamBean.fq && searchParamBean.fq != 'product_model_price:[* TO *]'}">
                            <a href="javascript:void(0);" onclick="priceSectionForward('[* TO *]')">全部</a>
                        </c:if>
                    </div>
                    <div class="look-body">
                        <a href="javascript:void(0);" onclick="priceSectionForward('[* TO 100]')" class="<c:if test="${searchParamBean.fq == 'product_model_price:[* TO 100]'}">active</c:if>">百元以内</a>
                        <a href="javascript:void(0);" onclick="priceSectionForward('[100 TO 1000]')" class="<c:if test="${searchParamBean.fq == 'product_model_price:[100 TO 1000]'}">active</c:if>">100~1000</a>
                        <a href="javascript:void(0);" onclick="priceSectionForward('[1000 TO 5000]')" class="<c:if test="${searchParamBean.fq == 'product_model_price:[1000 TO 5000]'}">active</c:if>">1000~5000</a>
                        <a href="javascript:void(0);" onclick="priceSectionForward('[5000 TO 10000]')" class="<c:if test="${searchParamBean.fq == 'product_model_price:[5000 TO 10000]'}">active</c:if>">5000~1万</a>
                        <a href="javascript:void(0);" onclick="priceSectionForward('[10000 TO 100000]')" class="<c:if test="${searchParamBean.fq == 'product_model_price:[10000 TO 100000]'}">active</c:if>">1万~10万</a>
                        <a href="javascript:void(0);" onclick="priceSectionForward('[100000 TO *]')" class="<c:if test="${searchParamBean.fq == 'product_model_price:[100000 TO *]'}">active</c:if>">10万以上</a>
                    </div>
                </div>
                <!--一个类-->
                <div class="loog-ground ae">
                    <div class="look-head">
                        <span>分类：</span>
                        <strong><a href="#">全部</a></strong>
                    </div>
                    <div class="look-body">
                        <a href="#">家居摆设 </a>
                        <a href="#">生日礼物</a>
                        <a href="#">创意</a>
                        <a href="#">爱情</a>
                    </div>
                </div>
            </div>
        </div>
        <div class="shop-sort newshop-sort ae">
            <ul class="link1">
                <li class="active">
                    <a href="javascript:void(0);"
                       onclick="facetForward('<c:url value="/search.do?q=${searchParamBean.q}&resultPage=${searchParamBean.resultPage}&queryFacet=${searchParamBean.queryFacet}&priceUD=0&fq=${searchParamBean.fq}"/>')"
                       title="综合排序">综合排序</a>
                </li>
                <li class="line"><i class="icon"></i></li>
                <li class="active">
                    <c:if test="${searchParamBean.priceUD == 0}">
                        <a href="javascript:void(0)" title="价  格" onclick="sortForward2('product_model_price')">价  格<i id="priceUD${searchParamBean.priceUD}" class="icon arrow-up"></i></a>
                    </c:if>
                    <c:if test="${searchParamBean.priceUD != 0}">
                        <a href="javascript:void(0)" title="价  格" onclick="sortForward2('product_model_price')">价  格<i id="priceUD${searchParamBean.priceUD}" class="icon arrow-down"></i></a>
                    </c:if>
                </li>
                <li class="line"><i class="icon"></i></li>
                <li class="active">
                    <a href="#" title="销量">销量</a>
                </li>
                <li class="line"><i class="icon"></i></li>
                <li class="active">
                    <a href="#" title="人气">人气</a>
                </li>
                <li class="line"><i class="icon"></i></li>
                <li class="active">
                    <a href="#" title="新品">新品</a>
                </li>
                <li class="line"><i class="icon"></i></li>
            </ul>
            <ul class="link3">
                <li>
                    <input type="checkbox">
                    <span>诚品宝认证</span>
                </li>
                <li>
                    <input type="checkbox">
                    <span>推荐精品</span>
                </li>
                <li>
                    <input type="checkbox">
                    <span>纯手工制作</span>
                </li>
                <li>
                    <a href="#">商品</a>
                    <a href="javascript:void(0)" class="line-i">|</a>
                    <a href="#" class="hover">店铺</a>
                </li>
            </ul>
        </div>
        <div class="category">
            <div class="list-pro">
                <ul class="ul-item">
                    <%-- 没有获取到数据时显示 --%>
                    <c:if test="${empty searchParamBean.searchResultList}">
                        抱歉，没有找到“${searchParamBean.q}”的搜索结果，您可以换个关键词试试。
                    </c:if>
                    <%-- 数据显示 --%>
                    <c:forEach items="${searchParamBean.searchResultList}" var="result">
                        <li>
                            <a href="http://192.168.1.57/ef-website/product/productModel/${result.id}" target="_blank" title="">
                                <%--<img class="imgfilter" src="../../shop2015/upload/category-1.jpg" alt="">--%>
                                <img class="imgfilter" src="http://ec-efeiyi.oss-cn-beijing.aliyuncs.com/${result.picture_url}" alt="">
                                <p class="wh name">${result.product_name}[${result.specification}]</p>
                                <p class="wh price">￥${result.product_model_price}</p>
                            </a>
                        </li>
                    </c:forEach>
                    <%--<li>
                        <a href="" target="_blank" title="">
                            <img class="imgfilter" src="../shop2015/upload/category-1.jpg" alt="">
                            <p class="wh name">太极八卦砚</p>
                            <p class="wh price">￥1500000</p>
                        </a>
                    </li>
                    <li>
                        <a href="" target="_blank" title="">
                            <img class="imgfilter" src="../shop2015/upload/category-1.jpg" alt="">
                            <p class="wh name">太极八卦砚</p>
                            <p class="wh price">￥1500000</p>
                        </a>
                    </li>
                    <li>
                        <a href="" target="_blank" title="">
                            <img class="imgfilter" src="../shop2015/upload/category-1.jpg" alt="">
                            <p class="wh name">太极八卦砚</p>
                            <p class="wh price">￥1500000</p>
                        </a>
                    </li>
                    <li>
                        <a href="" target="_blank" title="">
                            <img class="imgfilter" src="../shop2015/upload/category-1.jpg" alt="">
                            <p class="wh name">太极八卦砚</p>
                            <p class="wh price">￥1500000</p>
                        </a>
                    </li>
                    <li>
                        <a href="" target="_blank" title="">
                            <img class="imgfilter" src="../shop2015/upload/category-1.jpg" alt="">
                            <p class="wh name">太极八卦砚</p>
                            <p class="wh price">￥1500000</p>
                        </a>
                    </li>
                    <li>
                        <a href="" target="_blank" title="">
                            <img class="imgfilter" src="../shop2015/upload/category-1.jpg" alt="">
                            <p class="wh name">太极八卦砚</p>
                            <p class="wh price">￥1500000</p>
                        </a>
                    </li>
                    <li>
                        <a href="" target="_blank" title="">
                            <img class="imgfilter" src="../shop2015/upload/category-1.jpg" alt="">
                            <p class="wh name">太极八卦砚</p>
                            <p class="wh price">￥1500000</p>
                        </a>
                    </li>
                    <li>
                        <a href="" target="_blank" title="">
                            <img class="imgfilter" src="../shop2015/upload/category-1.jpg" alt="">
                            <p class="wh name">太极八卦砚</p>
                            <p class="wh price">￥1500000</p>
                        </a>
                    </li>
                    <li>
                        <a href="" target="_blank" title="">
                            <img class="imgfilter" src="../shop2015/upload/category-1.jpg" alt="">
                            <p class="wh name">太极八卦砚</p>
                            <p class="wh price">￥1500000</p>
                        </a>
                    </li>
                    <li>
                        <a href="" target="_blank" title="">
                            <img class="imgfilter" src="../shop2015/upload/category-1.jpg" alt="">
                            <p class="wh name">太极八卦砚</p>
                            <p class="wh price">￥1500000</p>
                        </a>
                    </li>
                    <li>
                        <a href="" target="_blank" title="">
                            <img class="imgfilter" src="../shop2015/upload/category-1.jpg" alt="">
                            <p class="wh name">太极八卦砚</p>
                            <p class="wh price">￥1500000</p>
                        </a>
                    </li>
                    <li>
                        <a href="" target="_blank" title="">
                            <img class="imgfilter" src="../shop2015/upload/category-1.jpg" alt="">
                            <p class="wh name">太极八卦砚</p>
                            <p class="wh price">￥1500000</p>
                        </a>
                    </li>
                    <li>
                        <a href="" target="_blank" title="">
                            <img class="imgfilter" src="../shop2015/upload/category-1.jpg" alt="">
                            <p class="wh name">太极八卦砚</p>
                            <p class="wh price">￥1500000</p>
                        </a>
                    </li>
                    <li>
                        <a href="" target="_blank" title="">
                            <img class="imgfilter" src="../shop2015/upload/category-1.jpg" alt="">
                            <p class="wh name">太极八卦砚</p>
                            <p class="wh price">￥1500000</p>
                        </a>
                    </li>
                    <li>
                        <a href="" target="_blank" title="">
                            <img class="imgfilter" src="../shop2015/upload/category-1.jpg" alt="">
                            <p class="wh name">太极八卦砚</p>
                            <p class="wh price">￥1500000</p>
                        </a>
                    </li>
                    <li>
                        <a href="" target="_blank" title="">
                            <img class="imgfilter" src="../shop2015/upload/category-1.jpg" alt="">
                            <p class="wh name">太极八卦砚</p>
                            <p class="wh price">￥1500000</p>
                        </a>
                    </li>--%>
                </ul>

            </div>
            <!-- //End--list-pro-->
            <div class="pages wh">
            <%--<div style="clear: both">--%>
                <%--<ul class="am-pagination am-pagination-centered">
                    <li><a href="">1</a></li>
                    <li class="am-active"><a href="#">2</a></li>
                    <li class="am-disabled bigRound"><a href="#">最后一页</a></li>
                </ul>--%>
                <c:url value="/search.do" var="url"/>
                <ming800:pcPageList bean="${searchParamBean.pageEntity}" url="${url}">
                    <ming800:pcPageParam name="q" value="${searchParamBean.q}"/><%-- 记录检索关键字 --%>
                    <ming800:pcPageParam name="resultPage" value="${searchParamBean.resultPage}"/><%-- 结果页面路径 --%>
                    <ming800:pcPageParam name="queryFacet" value="${searchParamBean.queryFacet}"/> <%-- 当前查询条件 --%>
                    <ming800:pcPageParam name="facetFieldJson" value="${searchParamBean.facetFieldJson}"/><%--分类 存储在Json字符串中--%>
                    <ming800:pcPageParam name="queryFacetJson" value="${searchParamBean.queryFacetJson}"/><%--所有查询条件 存储在Json字符串中--%>
                    <ming800:pcPageParam name="sortField" value="${searchParamBean.sortField}"/><%--排序字段--%>
                    <ming800:pcPageParam name="sortOrder" value="${searchParamBean.sortOrder}"/><%--排序方式desc/asc--%>
                    <ming800:pcPageParam name="group" value="efeiyi"/>
                    <ming800:pcPageParam name="fq" value="${searchParamBean.fq}"/>
                    <ming800:pcPageParam name="priceUD" value="${searchParamBean.priceUD}"/>
                </ming800:pcPageList>
            </div>
            <!-- //End--page-->
        </div>
    </div>
</div>
<!--//End--fly-nev-->
<div class="footernew wh">
    <div class="publ-top hd">
        <dl class="city">
            <dt>正</dt>
            <dd>非&nbsp;遗&nbsp;正&nbsp;品</dd>
        </dl>
        <dl class="city">
            <dt>真</dt>
            <dd>诚&nbsp;品&nbsp;宝&nbsp;保&nbsp;真</dd>
        </dl>
        <dl class="city">
            <dt>好</dt>
            <dd>手&nbsp;工&nbsp;精&nbsp;品</dd>
        </dl>
        <dl class="city">
            <dt>值</dt>
            <dd>好&nbsp;工&nbsp;好&nbsp;料</dd>
        </dl>
    </div>
    <div class="servicenew wh">
        <div class="hd tct">
            <div class=" foremax">
                <dl class="fore">
                    <dt>帮助中心</dt>
                    <dd><a href="#" target="_blank">购物流程</a></dd>
                    <dd><a href="#" target="_blank">配送方式</a></dd>
                    <dd><a href="#" target="_blank">常见问题</a></dd>
                </dl>
                <dl class="fore">
                    <dt>服务保障</dt>
                    <dd><a href="#" target="_blank">正品保障</a></dd>
                    <dd><a href="#" target="_blank">7天退货</a></dd>
                </dl>
                <dl class="fore">
                    <dt>支付方式</dt>
                    <dd><a href="#" target="_blank">支付宝</a></dd>
                    <dd><a href="#" target="_blank">微信支付</a></dd>
                    <dd><a href="#" target="_blank">银行转账</a></dd>
                </dl>
                <dl class="fore">
                    <dt>售后服务</dt>
                    <dd><a href="#" target="_blank">售后政策</a></dd>
                    <dd><a href="#" target="_blank">退款说明</a></dd>
                    <dd><a href="#" target="_blank">退换货</a></dd>
                </dl>
                <dl class="fore">
                    <dt>商家服务</dt>
                    <dd><a href="#" target="_blank">商家入驻</a></dd>
                    <dd><a href="#" target="_blank">商家中心</a></dd>
                    <dd><a href="#" target="_blank">运营服务</a></dd>
                </dl>
                <div class="wechatnew" title="手机e飞蚁"></div>
            </div>
        </div>
    </div>
    <div class="max-links wh">
        <div class="links wh">
            <a href="" target="_blank" title="关于我们">关于我们</a>
            <a class="line"></a>
            <a href="" target="_blank" title="联系我们">联系我们</a>
            <a class="line"></a>
            <a href="" target="_blank" title="诚聘英才">诚聘英才</a>
            <a class="line"></a>
            <a href="" target="_blank" title="意见反馈">意见反馈</a>
            <a class="line"></a>
            <a href="" target="_blank" title="帮助中心">帮助中心</a>
            <a class="line"></a>
            <a href="" target="_blank" title="诚信保障">诚信保障</a>
            <a class="line"></a>
            <a href="" target="_blank" title="新闻资讯">新闻资讯</a>
        </div>
        <div class="copyright wh">
            <div class="phone">
                <strong>商家入驻热线</strong>
                <em>400-876-8766</em>
            </div>
            <div class="frlinks">
                <span>友情链接：</span>
                <a rel="nofollow" href="http://www.unesco.org.cn/" target="_blank" title="联合国教科文组织">联合国教科文组织</a>
                <a rel="nofollow" href="http://www.mcprc.gov.cn/" target="_blank" title="中国文化部">中国文化部</a>
                <a rel="nofollow" href="" target="_blank" title="中国文化部非物质文化遗产保护司">中国文化部非物质文化遗产保护司</a>
                <a rel="nofollow" href="" target="_blank" title="中国非物质文化遗产保护中心">中国非物质文化遗产保护中心</a>
            </div>
            <div class="info">Copyright © 2012-2022 永新华韵文化发展有限公司版权所有-京ICP备15032511号-1</div>
        </div>
        <div class="wh authentication">
            <a href="http://www.miitbeian.gov.cn" target="_blank" title="网站域名备案"> <img class="imgfilter" src="../shop2015/images/authentication-1.png" alt="网站域名备案"></a>
            <a href="http://www.baic.gov.cn/" target="_blank" title="企业信用信息备案"> <img class="imgfilter" src="../shop2015/images/authentication-2.png" alt="企业信用信息备案"></a>
            <a href="/scripts/images/license.jpg" target="_blank" title="企业营业执照"> <img class="imgfilter" src="../shop2015/images/authentication-3.png" alt="企业营业执照 "></a>
        </div>
    </div>
</div>
<!--//End--footernew-->
<!--[if (gte IE 9)|!(IE)]><!-->
<script src="../../shop2015/js/jquery.min.js"></script>
<!--<![endif]-->
<!--[if lte IE 8 ]>
<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="assets/js/amazeui.ie8polyfill.min.js"></script>
<![endif]-->
<script src="../../shop2015/js/system.js"></script>
<!--<script type="text/javascript" src="http://v3.jiathis.com/code/jia.js" charset="utf-8"></script>-->

<script type="text/javascript">
    var facets = "${searchParamBean.facetFieldJson}";
    function facetForward(url) {
        window.location.href = url + "&facetFieldJson=" + facets + "&queryFacetJson=${searchParamBean.queryFacetJson}&group=efeiyi" ;
    }
    function sortForward(sortField,sortOrder) {
        var url = "<c:url value='/search.do?q=${searchParamBean.q}&resultPage=${searchParamBean.resultPage}&queryFacet=${searchParamBean.queryFacet}&sortField='/>" + sortField + "&sortOrder=" + sortOrder +"&fq=${searchParamBean.fq}";
        facetForward(url)
    }
    var eId = "#priceUD${searchParamBean.priceUD}";
    function sortForward2(sortField) {

        var iClass = $(eId).attr("class");
        var sortOrder = "asc";
        var priceUD = 0;
        var tag = iClass.indexOf("arrow-up")>0;
        if(tag){
            sortOrder = "";
            priceUD = 1;
        }

        var url = "<c:url value='/search.do?q=${searchParamBean.q}&resultPage=${searchParamBean.resultPage}&queryFacet=${searchParamBean.queryFacet}&sortField='/>" + sortField + "&sortOrder=" + sortOrder +"&fq=${searchParamBean.fq}" + "&priceUD=" + priceUD;
        facetForward(url)
    }
    function priceSectionForward(priceSection){
        var url = "<c:url value='/search.do?q=${searchParamBean.q}&resultPage=${searchParamBean.resultPage}&queryFacet=${searchParamBean.queryFacet}&priceUD=${searchParamBean.priceUD}&fq=product_model_price:'/>" + priceSection;
        facetForward(url)
    }
</script>


</body>
</html>
