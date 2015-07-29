<!doctype html>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ming800" uri="http://java.ming800.com/taglib" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html class="no-js">
<head></head>
<body style="background-color:#F1F5F8">
<header class="work-details">
  <div class="work-details-i"><a href="javascript:history.go(-1)"><img src="<c:url value="/scripts/assets/wap/images/logo10.gif"/>"></a></div>
</header>
<section class="work-text-h1">
  <h1>${product.name}</h1>
  <p>
    <c:forEach items="${product.productPictureList}" var="productPicture">
      <div class="am-u-sm-12"><img src="http://master.efeiyi.com/${productPicture.pictureUrl}"/></div>
    </c:forEach>
    <span>${product.productDescription.content}</span>
  </p>
</section>
</body>
</html>