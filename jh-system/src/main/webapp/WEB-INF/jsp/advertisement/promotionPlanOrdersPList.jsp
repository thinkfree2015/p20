<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/12/04
  Time: 09:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ming800" uri="http://java.ming800.com/taglib" %>
<html>
<head>
    <title>返利链接订单列表</title>
</head>
<body style="height: auto">
<%--<div style="text-align: left;margin-left: 10px;">
    <input onclick="window.location.href='<c:url value="/basic/xm.do?qm=formPromotionPlan&rebate=rebate"/>'"
           type="button" class="am-btn am-btn-default am-btn-xs"
           style="margin-top: 4px;margin-bottom: 6px;margin-left:2px;height: 35px;"
           value="新建返利链接"/>
</div>--%>
<div style="text-align: left;margin-left: 10px;">
    <input onclick="window.history.go(-1);"
           type="button" class="am-btn am-btn-default am-btn-xs"
           style="margin-top: 4px;margin-bottom: 6px;margin-left:2px;height: 35px;"
           value="返回"/>
</div>
<div>
    <table class="am-table am-table-bordered am-table-radius am-table-striped">
        <tr style="text-align:left">
            <td>订单号</td>
            <td>用户</td>
            <td>下单时间</td>
            <td>订单总价</td>
            <td>订单原价</td>
            <td>支付方式</td>
            <td>订单状态</td>
        </tr>
        <c:forEach items="${orderList}" var="order">
            <tr>
                <td>${order.serial}</td>
                <td>${order.user.username}</td>
                <td>
                    <fmt:formatDate value="${order.createDatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
                <td>${order.total}</td>
                <td>${order.originalPrice}</td>
                <td>${order.payWay}</td>
                <td>${order.orderStatus}</td>
            </tr>
        </c:forEach>
    </table>
</div>
<div style="clear: both">
    <c:url value="/promotionPlan/getZCLInfo.do" var="url"/>
    <ming800:pcPageList bean="${pageEntity}" url="${url}">
        <ming800:pcPageParam name="id" value="${promotionPlanId}"/>
    </ming800:pcPageList>
</div>
</body>
</html>
