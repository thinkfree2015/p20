<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/12/14
  Time: 13:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ming800" uri="http://java.ming800.com/taglib" %>
<html>
<head>
    <title>企业用户</title>
</head>
<body>
<div class="am-cf am-padding">
    <div class="am-fl am-cf">
        <strong class="am-text-primary am-text-lg">企业用户</strong>
    </div>
</div>
<hr/>
<div class="am-g">
    <form action="<c:url value='/basic/xm.do?qm=saveOrUpdateUser'/>"
          method="post" enctype="multipart/form-data" class="am-form am-form-horizontal">
        <input type="hidden" name="id" value="${object.id}">
        <input type="hidden" name="status" value="${object.status}"/>

        <div class="am-form-group">
            <label for="username" class="am-u-sm-3 am-form-label">企业用户名称<small>*</small></label>
            <div class="am-u-sm-9">
                <input type="text" name="username" id="username" title="企业用户名称" placeholder="企业用户名称"
                       value="${object.username}" required="true">
            </div>
        </div>

        <div class="am-form-group">
            <div class="am-u-sm-9 am-u-sm-push-3">
                <input type="submit" class="am-btn am-btn-primary" value="保存"/>
            </div>
        </div>

    </form>
</div>
</body>
</html>
