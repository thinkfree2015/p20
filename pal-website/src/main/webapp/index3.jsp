<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/11/17
  Time: 10:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>全文检索测试</title>
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
    <link type="text/css" rel="stylesheet" href="/shop2015PC/css/amazeui.min.css?v=20150831">
    <link type="text/css" rel="stylesheet" href="/shop2015PC/css/app.css?v=20150831">
    <link type="text/css" rel="stylesheet" href="/shop2015PC/css/myorder.css?v=20150831">
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
            <input type="hidden" id="priceUD" name="priceUD" value="0"/>
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
<div class="wh nav-new">
    <div class="hd">
        <div class="cate">
            <div class="ld"><h2>非遗商品分类<i class="icon-new"></i></h2></div>
            <div class="allsort">
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
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
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
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
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
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
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
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
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
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
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
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
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
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
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
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
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
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
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
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
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
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
                            <a href="" title=""> <img class="imgfilter" src="/shop2015PC/upload/B010102_03.jpg" alt=""></a>
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
<div class="wh focus-new">
    <div class="hd">
        <ul class="slider-main">
            <li style="display: block;"> <img src="/shop2015PC/upload/b1.jpg" width="1280" height="481" alt=""/></li>
            <li> <img src="/shop2015PC/upload/b2.jpg" width="1280" height="481" alt=""/></li>
            <li> <img src="/shop2015PC/upload/b3.jpg" width="1280" height="481" alt=""/></li>
            <li> <img src="/shop2015PC/upload/b4.jpg" width="1280" height="481" alt=""/></li>
            <li> <img src="/shop2015PC/upload/b5.jpg" width="1280" height="481" alt=""/></li>
        </ul>
        <ul class="slider-nav">
            <li class="active"></li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
        </ul>
        <div class="btn btn-prev" title="上一页"></div>
        <div class="btn btn-next" title="下一页"></div>
    </div>
</div>
<!--//End--focus-new-->
<div class="homenew hd">
    <div class="deduce ae">
        <ul class="list-top ae">
            <li><a href="#" target="_blank"><img class="imgfilter" src="/shop2015PC/upload/homenew1-1.jpg"></a></li>
            <li><a href="#" target="_blank"><img class="imgfilter" src="/shop2015PC/upload/homenew1-2.jpg"></a></li>
            <li><a href="#" target="_blank"><img class="imgfilter" src="/shop2015PC/upload/homenew1-3.jpg"></a></li>
            <li><a href="#" target="_blank"><img class="imgfilter" src="/shop2015PC/upload/homenew1-4.jpg"></a></li>
            <li><a href="#" target="_blank"><img class="imgfilter" src="/shop2015PC/upload/homenew1-5.jpg"></a></li>
            <li><a href="#" target="_blank"><img class="imgfilter" src="/shop2015PC/upload/homenew1-6.jpg"></a></li>
        </ul>
        <ul class="list-bottom ae">
            <li>
                <a href="#" target="_blank">
                    <strong>静幽</strong>
                    <img class="imgfilter" src="/shop2015PC/upload/homenew2-1.jpg">
                </a>
                <span class="money"><em>￥</em><font>1500</font></span>
                <span class="fiery">热卖</span>
            </li>
            <li>
                <a href="#" target="_blank">
                    <strong>静幽</strong>
                    <img class="imgfilter" src="/shop2015PC/upload/homenew2-2.jpg">
                </a>
                <span class="money"><em>￥</em><font>1500</font></span>
                <span class="fiery">热卖</span>
            </li>
            <li>
                <a href="#" target="_blank">
                    <strong>静幽</strong>
                    <img class="imgfilter" src="/shop2015PC/upload/homenew2-3.jpg">
                </a>
                <span class="money"><em>￥</em><font>1500</font></span>
                <span class="fiery">热卖</span>
            </li>
            <li>
                <a href="#" target="_blank">
                    <strong>静幽</strong>
                    <img class="imgfilter" src="/shop2015PC/upload/homenew2-4.jpg">
                </a>
                <span class="money"><em>￥</em><font>1500</font></span>
                <span class="fiery">热卖</span>
            </li>
            <li>
                <a href="#" target="_blank">
                    <strong>婉约如玉青瓷</strong>
                    <img class="imgfilter" src="/shop2015PC/upload/homenew2-5.jpg">
                </a>
                <span class="money"><em>￥</em><font>1500</font></span>
                <span class="fiery">热卖</span>
            </li>
            <li>
                <a href="#" target="_blank">
                    <strong>静幽</strong>
                    <img class="imgfilter" src="/shop2015PC/upload/homenew2-6.jpg">
                </a>
                <span class="money"><em>￥</em><font>1500</font></span>
                <span class="fiery">热卖</span>
            </li>
            <li>
                <a href="#" target="_blank">
                    <strong>静幽</strong>
                    <img class="imgfilter" src="/shop2015PC/upload/homenew2-7.jpg">
                </a>
                <span class="money"><em>￥</em><font>1500</font></span>
                <span class="fiery">热卖</span>
            </li>
            <li>
                <a href="#" target="_blank">
                    <strong>静幽</strong>
                    <img class="imgfilter" src="/shop2015PC/upload/homenew2-8.jpg">
                </a>
                <span class="money"><em>￥</em><font>1500</font></span>
                <span class="fiery">热卖</span>
            </li>
            <li>
                <a href="#" target="_blank">
                    <strong>静幽</strong>
                    <img class="imgfilter" src="/shop2015PC/upload/homenew2-9.jpg">
                </a>
                <span class="money"><em>￥</em><font>1500</font></span>
                <span class="fiery">热卖</span>
            </li>
            <li>
                <a href="#" target="_blank">
                    <strong>静幽</strong>
                    <img class="imgfilter" src="/shop2015PC/upload/homenew2-10.jpg">
                </a>
                <span class="money"><em>￥</em><font>1500</font></span>
                <span class="fiery">热卖</span>
            </li>
        </ul>
    </div>
    <!-- 放banner-->
    <div class="middle-banner"><span>banner</span></div>
    <div class="max-cat ae">
        <!--一个类别-->
        <div class="category ae">
            <div class="cat-left">
                <div class="cat-title">织染印绣</div>
                <div class="cat-txt ">不乱于心</div>
                <div class="cat-txt c-tx2">不困于情</div>
                <div class="c-o-list">
                    <a href="#" target="_blank">铜雕</a>
                    <a href="#" target="_blank">花丝镶嵌</a>
                    <a href="#" target="_blank">锡雕</a>
                    <a href="#" target="_blank">青铜雕雕雕12</a>
                    <a href="#" target="_blank">石雕</a>
                    <a href="#" target="_blank">澄泥石</a>
                    <a href="#" target="_blank">斑铜</a>
                    <a href="#" target="_blank">银饰</a>
                    <a href="#" target="_blank">银器</a>
                    <a href="#" target="_blank">澄泥石</a>
                    <a href="#" target="_blank">斑铜</a>
                    <a href="#" target="_blank">银饰</a>
                    <a href="#" target="_blank">银器</a>
                </div>
            </div>
            <ul class="cat-right">
                <li>
                    <a href="#" target="_blank">
                        <strong>静幽</strong>
                        <img class="imgfilter" src="/shop2015PC/upload/homenew3-1.jpg">
                    </a>
                    <span class="cat-money"><em>￥</em><font>1500</font></span>
                </li>
                <li>
                    <a href="#" target="_blank">
                        <strong>静幽</strong>
                        <img class="imgfilter" src="/shop2015PC/upload/homenew3-2.jpg">
                    </a>
                    <span class="imgfilter" class="cat-money"><em>￥</em><font>1500</font></span>
                </li>
                <li>
                    <a href="#" target="_blank">
                        <strong>静幽</strong>
                        <img class="imgfilter" src="/shop2015PC/upload/homenew3-3.jpg">
                    </a>
                    <span class="cat-money"><em>￥</em><font>1500</font></span>
                </li>
                <li>
                    <a href="#" target="_blank">
                        <strong>静幽</strong>
                        <img class="imgfilter" src="/shop2015PC/upload/homenew3-4.jpg">
                    </a>
                    <span class="cat-money"><em>￥</em><font>1500</font></span>
                </li>
                <li>
                    <a href="#" target="_blank">
                        <strong>静幽</strong>
                        <img class="imgfilter" src="/shop2015PC/upload/homenew3-5.jpg">
                    </a>
                    <span class="cat-money"><em>￥</em><font>1500</font></span>
                </li>
                <li>
                    <a href="#" target="_blank">
                        <strong>静幽</strong>
                        <img class="imgfilter" src="/shop2015PC/upload/homenew3-6.jpg">
                    </a>
                    <span class="cat-money"><em>￥</em><font>1500</font></span>
                </li>
                <li>
                    <a href="#" target="_blank">
                        <strong>静幽</strong>
                        <img class="imgfilter" src="/shop2015PC/upload/homenew3-7.jpg">
                    </a>
                    <span class="cat-money"><em>￥</em><font>1500</font></span>
                </li>
                <li>
                    <a href="#" target="_blank">
                        <strong>静幽</strong>
                        <img class="imgfilter" src="/shop2015PC/upload/homenew3-8.jpg">
                    </a>
                    <span class="cat-money"><em>￥</em><font>1500</font></span>
                </li>
            </ul>
        </div>
        <!--一个类别-->
        <div class="category ae">
            <div class="cat-left">
                <div class="cat-title">陶风瓷韵</div>
                <div class="cat-txt ">不乱于心</div>
                <div class="cat-txt c-tx2">不困于情</div>
                <div class="c-o-list">
                    <a href="#" target="_blank">铜雕</a>
                    <a href="#" target="_blank">花丝镶嵌</a>
                    <a href="#" target="_blank">锡雕</a>
                    <a href="#" target="_blank">青铜雕</a>
                    <a href="#" target="_blank">石雕</a>
                    <a href="#" target="_blank">澄泥石</a>
                    <a href="#" target="_blank">斑铜</a>
                    <a href="#" target="_blank">银饰</a>
                    <a href="#" target="_blank">银器</a>
                    <a href="#" target="_blank">澄泥石</a>
                    <a href="#" target="_blank">斑铜</a>
                    <a href="#" target="_blank">银饰</a>
                    <a href="#" target="_blank">银器</a>
                </div>
            </div>
            <ul class="cat-right">
                <li>
                    <a href="#" target="_blank">
                        <strong>静幽</strong>
                        <img class="imgfilter" src="/shop2015PC/upload/homenew3-1.jpg">
                    </a>
                    <span class="cat-money"><em>￥</em><font>1500</font></span>
                </li>
                <li>
                    <a href="#" target="_blank">
                        <strong>静幽</strong>
                        <img class="imgfilter" src="/shop2015PC/upload/homenew3-2.jpg">
                    </a>
                    <span class="cat-money"><em>￥</em><font>1500</font></span>
                </li>
                <li>
                    <a href="#" target="_blank">
                        <strong>静幽</strong>
                        <img class="imgfilter" src="/shop2015PC/upload/homenew3-3.jpg">
                    </a>
                    <span class="cat-money"><em>￥</em><font>1500</font></span>
                </li>
                <li>
                    <a href="#" target="_blank">
                        <strong>静幽</strong>
                        <img class="imgfilter" src="/shop2015PC/upload/homenew3-4.jpg">
                    </a>
                    <span class="cat-money"><em>￥</em><font>1500</font></span>
                </li>
                <li>
                    <a href="#" target="_blank">
                        <strong>静幽</strong>
                        <img class="imgfilter" src="/shop2015PC/upload/homenew3-5.jpg">
                    </a>
                    <span class="cat-money"><em>￥</em><font>1500</font></span>
                </li>
                <li>
                    <a href="#" target="_blank">
                        <strong>静幽</strong>
                        <img class="imgfilter" src="/shop2015PC/upload/homenew3-6.jpg">
                    </a>
                    <span class="cat-money"><em>￥</em><font>1500</font></span>
                </li>
                <li>
                    <a href="#" target="_blank">
                        <strong>静幽</strong>
                        <img class="imgfilter" src="/shop2015PC/upload/homenew3-7.jpg">
                    </a>
                    <span class="cat-money"><em>￥</em><font>1500</font></span>
                </li>
                <li>
                    <a href="#" target="_blank">
                        <strong>静幽</strong>
                        <img class="imgfilter" src="/shop2015PC/upload/homenew3-8.jpg">
                    </a>
                    <span class="cat-money"><em>￥</em><font>1500</font></span>
                </li>
            </ul>
        </div>
        <!--一个类别-->
        <div class="category ae">
            <div class="cat-left">
                <div class="cat-title">金石錾锻</div>
                <div class="cat-txt ">一物一乾坤</div>
                <div class="cat-txt c-tx2">一处一知音</div>
                <div class="c-o-list">
                    <a href="#" target="_blank">铜雕</a>
                    <a href="#" target="_blank">花丝镶嵌</a>
                    <a href="#" target="_blank">锡雕</a>
                    <a href="#" target="_blank">青铜雕</a>
                    <a href="#" target="_blank">石雕</a>
                    <a href="#" target="_blank">澄泥石</a>
                    <a href="#" target="_blank">斑铜</a>
                    <a href="#" target="_blank">银饰</a>
                    <a href="#" target="_blank">银器</a>
                    <a href="#" target="_blank">澄泥石</a>
                    <a href="#" target="_blank">斑铜</a>
                    <a href="#" target="_blank">银饰</a>
                    <a href="#" target="_blank">银器</a>
                </div>
            </div>
            <ul class="cat-right">
                <li>
                    <a href="#" target="_blank">
                        <strong>静幽</strong>
                        <img class="imgfilter" src="/shop2015PC/upload/homenew3-1.jpg">
                    </a>
                    <span class="cat-money"><em>￥</em><font>1500</font></span>
                </li>
                <li>
                    <a href="#" target="_blank">
                        <strong>静幽</strong>
                        <img class="imgfilter" src="/shop2015PC/upload/homenew3-2.jpg">
                    </a>
                    <span class="cat-money"><em>￥</em><font>1500</font></span>
                </li>
                <li>
                    <a href="#" target="_blank">
                        <strong>静幽</strong>
                        <img class="imgfilter" src="/shop2015PC/upload/homenew3-3.jpg">
                    </a>
                    <span class="cat-money"><em>￥</em><font>1500</font></span>
                </li>
                <li>
                    <a href="#" target="_blank">
                        <strong>静幽</strong>
                        <img class="imgfilter" src="/shop2015PC/upload/homenew3-4.jpg">
                    </a>
                    <span class="cat-money"><em>￥</em><font>1500</font></span>
                </li>
                <li>
                    <a href="#" target="_blank">
                        <strong>静幽</strong>
                        <img class="imgfilter" src="/shop2015PC/upload/homenew3-5.jpg">
                    </a>
                    <span class="cat-money"><em>￥</em><font>1500</font></span>
                </li>
                <li>
                    <a href="#" target="_blank">
                        <strong>静幽</strong>
                        <img class="imgfilter" src="/shop2015PC/upload/homenew3-6.jpg">
                    </a>
                    <span class="cat-money"><em>￥</em><font>1500</font></span>
                </li>
                <li>
                    <a href="#" target="_blank">
                        <strong>静幽</strong>
                        <img class="imgfilter" src="/shop2015PC/upload/homenew3-7.jpg">
                    </a>
                    <span class="cat-money"><em>￥</em><font>1500</font></span>
                </li>
                <li>
                    <a href="#" target="_blank">
                        <strong>静幽</strong>
                        <img class="imgfilter" src="/shop2015PC/upload/homenew3-8.jpg">
                    </a>
                    <span class="cat-money"><em>￥</em><font>1500</font></span>
                </li>
            </ul>
        </div>
    </div>
</div>
<!--//End--homenew-->
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
            <a href="http://www.miitbeian.gov.cn" target="_blank" title="网站域名备案"> <img class="imgfilter" src="/shop2015PC/images/authentication-1.png" alt="网站域名备案"></a>
            <a href="http://www.baic.gov.cn/" target="_blank" title="企业信用信息备案"> <img class="imgfilter" src="/shop2015PC/images/authentication-2.png" alt="企业信用信息备案"></a>
            <a href="/scripts/images/license.jpg" target="_blank" title="企业营业执照"> <img class="imgfilter" src="/shop2015PC/images/authentication-3.png" alt="企业营业执照 "></a>
        </div>
    </div>
</div>
<!--//End--footernew-->

<!--[if (gte IE 9)|!(IE)]><!-->
<script src="/shop2015PC/js/jquery.min.js"></script>
<!--<![endif]-->
<!--[if lte IE 8 ]>
<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="assets/js/amazeui.ie8polyfill.min.js"></script>
<![endif]-->
<script src="/shop2015PC/js/system.js"></script>
<!--<script type="text/javascript" src="http://v3.jiathis.com/code/jia.js" charset="utf-8"></script>-->

</body>
</html>
