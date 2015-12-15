<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/12/14
  Time: 11:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ming800" uri="http://java.ming800.com/taglib" %>
<html>
<head>
  <title>虚拟数据批次</title>
</head>
<body style="height: auto">
<div style="text-align: left;margin-left: 10px;">
  <input onclick="window.location.href='<c:url value="/basic/xm.do?qm=formUser&company=company"/>'"
         type="button" class="am-btn am-btn-default am-btn-xs"
         style="margin-top: 4px;margin-bottom: 6px;margin-left:2px;height: 35px;"
         value="新建企业用户"/>
</div>
<div>
  <table class="am-table am-table-bordered am-table-radius am-table-striped">
    <tr style="text-align:left">
      <td>操作</td>
      <td>用户名</td>
      <td>用户状态</td>
    </tr>
    <c:forEach items="${requestScope.pageInfo.list}" var="user">
      <tr>
        <td>
          <div class="am-btn-toolbar">
            <div class="am-btn-group am-btn-group-xs" style="width: 100%;">
              <button onclick="window.location.href='<c:url
                      value="/basic/xm.do?qm=formUser&company=company&id=${user.id}"/>'"
                      class="am-btn am-btn-default am-btn-xs am-hide-sm-only"><span
                      class="am-icon-edit"></span> 编辑
              </button>
              <button onclick="window.location.href='<c:url
                      value="/basic/xm.do?qm=removeUser&id=${user.id}"/>'"
                      class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only"><span
                      class="am-icon-trash-o"></span> 删除
              </button>
            </div>
          </div>
        </td>
        <td>${user.name}</td>
        <td>
          <ming800:status name="status" dataType="PCUser.status" checkedValue="${user.status}" type="normal"/>
        </td>
      </tr>
    </c:forEach>
  </table>
</div>
<div style="clear: both">
  <c:url value="/basic/xm.do" var="url"/>
  <ming800:pcPageList bean="${requestScope.pageInfo.pageEntity}" url="${url}">
    <ming800:pcPageParam name="qm" value="${requestScope.qm}"/>
    <ming800:pcPageParam name="conditions" value="${requestScope.conditions}"/>
  </ming800:pcPageList>
</div>
</body>
</html>
