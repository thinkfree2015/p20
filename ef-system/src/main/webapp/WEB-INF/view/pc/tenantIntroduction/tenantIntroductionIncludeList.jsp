<%@ taglib prefix="ming800" uri="http://java.ming800.com/taglib" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/7/21
  Time: 15:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="am-g">
    <div class="am-u-sm-12">
        <table class="am-table am-table-striped am-table-hover table-main">
            <thead>
            <tr>
                <th class="table-set">操作</th>
                <th class="table-title">类型</th>
                <th class="table-title">姓名</th>

            </tr>
            </thead>
            <tbody>

            <c:forEach items="${objectList}" var="tenantIntroduction">
                <tr>
                    <td>
                        <div class="am-btn-toolbar">
                            <div class="am-btn-group am-btn-group-xs">
                                <a class="am-btn am-btn-default am-btn-xs am-text-secondary"
                                   href="<c:url value="/basic/xm.do?qm=formTenantIntroduction&id=${tenantIntroduction.id}"/>"><span
                                        class="am-icon-pencil-square-o"></span> 编辑
                                </a>
                                <a class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only"
                                   href="<c:url value="/basic/xm.do?qm=removeTenantIntroduction&id=${tenantIntroduction.id}"/>"><span
                                        class="am-icon-trash-o"></span> 删除
                                </a>
                            </div>
                        </div>
                    </td>
                    <td class="am-hide-sm-only">${tenantIntroduction.type}</td>
                    <td class="am-hide-sm-only"><a
                            href="<c:url value="/basic/xm.do?qm=viewTenantIntroduction&id=${tenantIntroduction.id}"/>">${tenantIntroduction.tenant.name}</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>