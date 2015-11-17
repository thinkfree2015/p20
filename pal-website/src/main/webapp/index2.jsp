<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/11/13
  Time: 17:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ming800" uri="http://java.ming800.com/taglib" %>
<html>
<body>
<form id="form" action="<c:url value='/search.do'/>" method="get">
    <input class="txt" type="text" name="q" id="q" value=""/>
    <input class="btn" type="submit" id="btn" value="检 索"/>
    <input type="hidden" id="resultPage" name="resultPage" value="/search"/>
    <input type="hidden" id="facetFields" name="facetFields" value="project_name"/>
    <input type="hidden" id="group" name="group" value="efeiyi"/>

</form>
</body>
</html>
