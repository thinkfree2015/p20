<%@ page import="com.efeiyi.ec.website.organization.util.AuthorizationUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/8/29
  Time: 11:01
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ming800" uri="http://java.ming800.com/taglib" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html class="no-js">
<head>
</head>
<body>
<!-- //End--header-->
<div class="hd product-intro">
  <div class="wh">
    <ol class="am-breadcrumb">
      <li><a href="/">首页</a></li>
      <li><a href="#">分类</a></li>
      <li class="am-active">内容</li>
    </ol>
  </div>
  <!-- //End--面包屑-->
  <%--<div class="wh itemInfo">--%>
    <%--<div class="preview">--%>
      <%--<div class="collect" id ="show" onclick="collect('${productModel.id}')">--%>
        <%--<i class="icon icon-hover"></i>--%>
        <%--<span class="hover">收藏</span>--%>
      <%--</div>--%>
  <div class="wh itemInfo">
    <div class="preview">
      <div class="collect" id ="show" onclick="collect('${productModel.id}')" >
        <div class="biao"><i class="icon"></i></div>
        <span class="txt hover">添加收藏</span>
      </div>
      <div class="collect" id="hidden" style="visibility: hidden" onclick="deCollect('${productModel.id}')" >
        <div class="biao"><i class="icon"></i></div>
        <span class="txt hover">添加收藏</span>
      </div>
      <%--<div class="collect" id="hidden" style="visibility: hidden" onclick="deCollect('${productModel.id}')">--%>
        <%--<i class="icon icon-hover"></i>--%>
        <%--<span class="hover">已收藏</span>--%>
      <%--</div>--%>
      <%--<div class="collect" disabled =disable  onclick="getStatus('${productModel.id}')">  <i class="icon" > </i> <span class="hover" id="collection" >收藏</span><span class="active">已收藏</span>  </div>--%>
      <%--<div class="collect" > <a <a onclick="getStatus('${productModel.id}')"> method="post"/> <i class="icon"></i></a><span class="hover">收藏</span></div>--%>
      <div class="slider-img">
        <ul>
          <%--<c:if test="${productPicture.status=='2'}">--%>
          <li><img src="http://pro.efeiyi.com/${productModel.productModel_url}@!product-detail-pc-view"
                                  alt=""/></li>
          <%--</c:if>--%>
          <c:forEach items="${productPictures}" var="productPicture" varStatus="rec">
            <c:if test="${productPicture.status=='1'}">
              <li ><img src="http://pro.efeiyi.com/${productPicture.pictureUrl}@!product-detail-pc-view"
                                      alt=""/></li>
            </c:if>
          </c:forEach>
        </ul>
      </div>
      <!-- //End--sliderimg-->
      <div class="slider-main">
        <ul>
         <%--<c:if test="${productPicture.status=='2'}">--%>
          <li>
            <img src="http://pro.efeiyi.com/${productModel.productModel_url}@!product-details-picture"  alt=""/>
          </li>
         <%--</c:if>--%>
          <c:forEach items="${productPictures}" var="productPicture" varStatus="rec">
            <c:if test="${productPicture.status=='1'}">
            <li>
              <img src="http://pro.efeiyi.com/${productPicture.pictureUrl}@!product-details-picture"  alt=""/>
            </li>
            </c:if>
          </c:forEach>
        </ul>
      </div>
      <!-- //End--slider-main-->
    </div>
    <!-- //End--des-focus-->
    <%--<div class="itme-ext">--%>
      <%--<div class="name">--%>
        <%--<p>${product.master.fullName}</p>--%>
        <%--<p><strong>${product.name}</strong></p>--%>
      <%--</div>--%>
      <%--<!-- //End-->--%>
      <%--<div class="des">--%>
        <%--<ul class="ul-list">--%>
        <%--<c:if test="${fn:length(productModelList) >1}">--%>
        <%--</c:if>--%>
          <%--<c:if test="${fn:length(productModelList) >1}">--%>
            <%--<c:forEach items="${productModelList}" var="productModelTmp" varStatus="rec">--%>
              <%--<c:if test="${productModel.id == productModelTmp.id}">--%>
                <%--<li class="active" >--%>
                  <%--<a href="/product/productModel/${productModelTmp.id}">--%>
                    <%--<c:forEach items="${productModelTmp.productPropertyValueList}" var="productPropertyValue" varStatus="rec">--%>
                      <%--${productPropertyValue.projectPropertyValue.value}--%>
                    <%--</c:forEach>--%>
                      <%--${product.name}</a>--%>
                <%--</li>--%>
              <%--</c:if>--%>
              <%--<c:if test="${productModel.id != productModelTmp.id}">--%>
                <%--<li class="">--%>
                  <%--<a href="/product/productModel/${productModelTmp.id}">--%>
                    <%--<c:forEach items="${productModelTmp.productPropertyValueList}" var="productPropertyValue" varStatus="rec">--%>
                      <%--${productPropertyValue.projectPropertyValue.value}--%>
                    <%--</c:forEach>--%>
                      <%--${product.name}</a>--%>
                <%--</li>--%>
              <%--</c:if>--%>


            <%--</c:forEach>--%>


          <%--</c:if>--%>
                  <%--</ul>--%>
      <%--</div>--%>
      <%--<!-- //End-->--%>
      <%--&lt;%&ndash;<div class="amount">&ndash;%&gt;--%>
        <%--&lt;%&ndash;<div class="ipt">&ndash;%&gt;--%>
          <%--&lt;%&ndash;<input class="txt" type="text" value=${productModel.amount}/><em class="ge">个</em>&ndash;%&gt;--%>
        <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<div class="btns">&ndash;%&gt;--%>
          <%--&lt;%&ndash;<a href="#btn-add" class="btn-add" title="加">+</a>&ndash;%&gt;--%>
          <%--&lt;%&ndash;<a href="#btn-sub" class="btn-sub" title="减">-</a>&ndash;%&gt;--%>
        <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
      <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
      <%--<!-- //End-->--%>
      <%--<div class="price">--%>
        <%--<div class="p-text">飞蚁价：</div>--%>
        <%--<div class="p-price"><em>￥</em><span>${productModel.price}</span></div>--%>
        <%--<div class="m-price">市场价：￥${productModel.marketPrice}</div>--%>
      <%--</div>--%>
      <%--<!-- //End-->--%>

      <%--<div class="choose-btns">--%>
         <%--<c:if test="${productModel.amount <= 0}">--%>
         <%--<a id ="modelId" class="btn btn-append"  title="缺货">缺货</a>--%>
         <%--</c:if>--%>
        <%--<c:if test="${productModel.amount > 0}">--%>
          <%--<a id ="modelId" class="btn btn-append"  href="<c:url value="/cart/addProduct.do?id=${productModel.id}"/>" title="放入购物车" dis>放入购物车</a>--%>
          <%--<a class="btn btn-buy" href="<c:url value="/order/easyBuy/${productModel.id}"/>" title="立即购买"  >立即购买</a>--%>
        <%--</c:if>--%>
        <%--<!-- JiaThis Button BEGIN -->--%>
      <%--</div>--%>
      <%--<!-- //End-->--%>
    <%--</div>--%>
    <!-- //End--itme-ext-->
    <!-- //End--des-focus-->

    <div class="itme-ext">
      <div class="name">
        <p><strong>${product.name}</strong></p>
        <p>${product.subName}</p>
      </div>
      <!-- //End-->
      <div class="master-name">
        <p class="p1"><a href="http://${product.master.name}.efeiyi.com" target="_blank"><span>${product.master.fullName}</span></a>[${product.master.getMainProjectName().getProject().getName()}]</p>
        <p class="p2"><ming800:status name="level" dataType="Project.level" checkedValue="${productModel.product.master.getMainProjectName().getProject().getLevel()}" type="normal"/>大师</p>
        <div class="master-img-pt"><a  href="http://${product.master.name}.efeiyi.com" target="_blank" title=""><img src="http://tenant.efeiyi.com/${productModel.product.master.favicon}@!tanent-details-view"></a></div>
      </div>
      <!-- //End-->
      <div class="master-cost">
        <p><font>市场价：</font><em>${productModel.marketPrice}</em></p>
        <p>飞蚁价</p>
        <p><strong>￥</strong><span>${productModel.price}</span></p>
      </div>
      <!-- //End-->
      <!-- //End-->
      <div class="des">
        <div class="colour">发货地：</div>
        <div class="colour-page">
          <c:if test="${product.tenant.address==null}">
            <span>未知  包邮</span>
            </c:if>
          <c:if test="${product.tenant.address!=null}">
          <span>${product.tenant.address}  包邮</span>
          </c:if>
        </div>
      </div>
      <div class="des">
        <div class="colour">规格：</div>
        <div class="colour-page">
          <ul class="ul-list">
            <c:if test="${fn:length(productModelList) >1}">
              <c:forEach items="${productModelList}" var="productModelTmp" varStatus="rec">
                <c:if test="${productModel.id == productModelTmp.id}">
                  <li class="active" >
                    <a href="/product/productModel/${productModelTmp.id}">
                      <c:forEach items="${productModelTmp.productPropertyValueList}" var="productPropertyValue" varStatus="rec">
                        ${productPropertyValue.projectPropertyValue.value}
                      </c:forEach>
                        ${product.name}</a>
                  </li>
                </c:if>
                <c:if test="${productModel.id != productModelTmp.id}">
                  <li class="">
                    <a href="/product/productModel/${productModelTmp.id}">
                      <c:forEach items="${productModelTmp.productPropertyValueList}" var="productPropertyValue" varStatus="rec">
                        ${productPropertyValue.projectPropertyValue.value}
                      </c:forEach>
                        ${product.name}</a>
                  </li>
                </c:if>
              </c:forEach>
            </c:if>
          </ul>
        </div>
      </div>
      <div class="choose-btns">
        <c:if test="${productModel.amount <= 0}">
          <a id ="modelId" class="btn btn-append"  title="缺货">缺货</a>
        </c:if>
        <c:if test="${productModel.amount > 0}">
          <a id ="modelId" class="btn btn-append"  href="<c:url value="/cart/addProduct.do?id=${productModel.id}"/>" title="放入购物车" dis>放入购物车</a>
          <a class="btn btn-buy" href="<c:url value="/order/easyBuy/${productModel.id}"/>" title="立即购买"  >立即购买</a>
        </c:if>
        <%--<a class="btn btn-append" href="<c:url value="/cart/addProduct.do?id=${productModel.id}"/>" title="放入购物车">放入购物车</a>--%>
        <%--<a class="btn btn-buy" href="<c:url value="/order/easyBuy/${productModel.id}"/>" title="立即购买">立即购买</a>--%>
        <%--<!--JiaThis Button BEGIN-->--%>
        <!--  <div class="jiathis_style">
              <span class="jiathis_txt">分享到</span>
              <a class="jiathis_button_weixin"></a>
              <a class="jiathis_button_tqq"></a>
              <a class="jiathis_button_tsina"></a>
              <a class="jiathis_button_cqq"></a>
          </div>-->
        <!--  JiaThis Button END-->
      </div>
      <!-- //End-->


    </div>
  </div>
  <!-- //End--itemInfo-->
  <div class="wh">
    <div class="tab-wrap">
      <!-- JiaThis Button BEGIN -->
      <div class="jiathis_style">
        <span class="jiathis_txt">分享到</span>
        <a class="jiathis_button_weixin"></a>
        <a class="jiathis_button_tqq"></a>
        <a class="jiathis_button_tsina"></a>
        <a class="jiathis_button_cqq"></a>
      </div>
      <!-- JiaThis Button END -->
      <div class="tab-items">
        <ul>
          <c:if test="${empty product.master.id}">
            <li><a href="#detail" title="商品详情">商 品 详 情</a></li>
            <li><a href="#title" title="商品评价">商 品 评 价<i class="icon"></i></a></li>
          </c:if>
          <c:if test="${not empty product.master.id}">
          <li><a href="#detail" title="商品详情">商 品 详 情<i class="icon"></i></a></li>
          <%--<li><a href="#feeling" title="大师感悟">大 师 感 悟<i class="icon"></i></a></li>--%>
          <li><a href="#title" title="商品评价">商 品 评 价<i class="icon"></i></a></li>
          <%--<li><a href="#" title="服务保障">服 务 保 障<i class="icon"></i></a></li>--%>

          <li><a href="<c:url value="/tenant/${product.tenant.id}"/>" title="同店精品">进 入 店 铺</a></li>
          </c:if>
        </ul>
      </div>
      <!-- //End-->
      <div class="btns">
     <c:if test="${productModel.amount > 0}">
        <a class="buy" href="/order/easyBuy/${productModel.id}" title="立即购买">立 即 购 买</a>
        <a class="append" href="<c:url value="/cart/addProduct.do?id=${productModel.id}"/>" title="放入购物车"><i class="icon"></i>放 入 购 物 车</a>
     </c:if>
      </div>
    </div>
  </div>
  <div class="wh detail" id="detail">
    <div class="wh title"><h3>商品详情</h3></div>
      <div class="wh part">

        <%--<div class="wh part">--%>
          ${product.productDescription.content}
          <div class="discuss">
            <div class="title" id="title"><h3>商品评价</h3></div>
            <div class="dis-con">
              <div class="dis-title">用户印象：</div>
              <div class="dis-ul">
                <ul>
                  <li>
                    <c:forEach items="${purchaseOrderProductList}" var="purchaseOrderProduct" varStatus="rec">
                      <div class="txt">
                        <c:if test="${not empty purchaseOrderProduct.purchaseOrderComment}">
                       ${purchaseOrderProduct.purchaseOrderComment.content}
                        </c:if>
                      </div>
                      <%--<div class="star">5星</div>--%>
                      <c:set var="user" >
                        ${purchaseOrderProduct.purchaseOrder.user}
                      </c:set>
                      <div class="user"><i class="icon"></i>${fn:substring(user, 0,3 )}*****${fn:substring(user,7,11)}</div>
                    </c:forEach>
                   </li>
                </ul>
              </div>
            </div>
          </div>
     </div>
    </div>
    </div>




</div>
<script type="text/javascript">
  function collect(o) {
    style="visibility: none;"
    $.ajax({
      type: 'post',
      async: false,
      url: '<c:url value="/product/addProductFavorite.do?id="/>' + o,
      dataType: 'json',
      success: function (data) {
        if(data==false){
          window.location.href = "<c:url value="http://passport.efeiyi.com/login"/>";
        }else{
          document.getElementById("show").style.visibility="hidden";
          document.getElementById("hidden").style.visibility="visible";
        }
      },
    });
  }
</script>

<script type="text/javascript">
  function deCollect(o) {
    style="visibility: none;"
    $.ajax({
      type: 'post',
      async: false,
      url: '<c:url value="/product/removeProductFavorite.do?id="/>' + o,
      dataType: 'json',
      success: function (data) {
        if(data==false){
          window.location.href = "<c:url value="http://passport.efeiyi.com/login"/>";
        }else{
          document.getElementById("show").style.visibility="visible";
          document.getElementById("hidden").style.visibility="hidden";
        }
      },
    });
  }
</script>
<script type="text/javascript" src="http://v3.jiathis.com/code/jia.js?uid=" charset="utf-8"></script>
</body>
</html>









