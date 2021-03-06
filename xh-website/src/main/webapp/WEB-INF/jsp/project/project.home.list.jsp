<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/10/10
  Time: 14:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="wh directory">
    <div class="title">非遗名录<a class="btn-more"
                              href="<c:url value='/project/project.do?qm=plistProject_default&provinceid=-1&type=-1'/>"
                              title="更多">更多</a></div>
    <div id="list-img">
        <ul class="list-img-txt">
            <c:forEach items="${objectList}" var="project" >
                <li>
                    <a href="<c:url value='/project/project.do?qm=plistProject_default&provinceid=${project.addressDistrict.addressCity.addressProvince.id}&type=${project.type}'/>"
                       target="_self" title=""><img
                            <c:if test="${not empty project.picture_url}">src="http://wiki-oss.efeiyi.com/${project.picture_url}"
                    </c:if>
                            <c:if test="${empty project.picture_url}">src="<c:url value='/shop2015/upload/exp4.jpg'/>"
                    </c:if>
                            alt=""/></a>

                    <p>${project.name}</p>
                </li>
            </c:forEach>
        </ul>
    </div>
    <a href="#上一页" class="btn btn-prev" title="上一页"></a>
    <a href="#下一页" class="btn btn-next" title="下一页"></a>
</div>
<script src="<c:url value='/shop2015/js/system.js'/>" />