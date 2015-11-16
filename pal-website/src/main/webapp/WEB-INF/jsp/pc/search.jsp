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
<form id="form" action="<c:url value='/search.do'/>" method="get">
    <input class="txt" type="text" name="q" id="q" value="${q}"/>
    <input class="btn" type="submit" id="btn" value="检 索"/>
    <input type="hidden" id="resultPage" name="resultPage" value="/search"/>
    <input type="hidden" id="facetFields" name="facetFields" value="project_name"/>
</form>
<c:if test="${not empty q}">

    <%--分类选项project_name start--%>
    <div>分类：<a href="/search.do?q=${q}&resultPage=${resultPage}&facetFieldJson=${facetFieldJson}">全部：</a>
        <c:forEach items="${facetFieldsMap}" var="facetFields">
            <c:forEach items="${facetFields.value}" var="facetEntry">
                <form action="<c:url value='/search.do'/>" method="get">
                    <input type="hidden" name="q" value="${q}"/>
                    <input type="hidden" name="resultPage" value="${resultPage}"/>
                    <input type="hidden" name="queryFacet" value="project_name:${facetEntry.key}"/>
                    <input type="hidden" name="queryFacetJson" value="${queryFacetJson}"/>
                    <input type="hidden" name="facetFieldJson" value="${facetFieldJson}"/>
                    <input type="submit" value='${facetEntry.key}'>
                </form>
            </c:forEach>
        </c:forEach>
    </div>
    <%--分类选项project_name end--%>

    <%--排序start--%>
    <div><input value="价格降序"  onclick="sortForward('product_model_price','')" type="button"></div>
    <div><input value="价格升序"  onclick="sortForward('product_model_price','asc')" type="button"></div>
    <%--排序end--%>

    <%--检索结果Start--%>
    <div>
        <c:forEach items="${searchResultList}" var="result">
            价格：${result.product_model_price}
            商品名：${result.product_name}
            分类名：${result.project_name}
            <br/>
            <%--<img src="http://ec-efeiyi.oss-cn-beijing.aliyuncs.com/${result.picture_url}"/>--%>
        </c:forEach>
    </div>
    <%--检索结果End--%>

    <%--翻页start--%>
    <div style="clear: both">
        <c:url value="/search.do" var="url"/>
        <ming800:pcPageList bean="${requestScope.pageEntity}" url="${url}">
            <ming800:pcPageParam name="q" value="${requestScope.q}"/>
            <ming800:pcPageParam name="resultPage" value="${requestScope.resultPage}"/>
            <ming800:pcPageParam name="queryFacet" value="${queryFacet}"/>
            <ming800:pcPageParam name="facetFieldJson" value="${facetFieldJson}"/>
            <ming800:pcPageParam name="queryFacetJson" value="${queryFacetJson}"/>
            <ming800:pcPageParam name="sortField" value="${sortField}"/>
            <ming800:pcPageParam name="sortOrder" value="${sortOrder}"/>
        </ming800:pcPageList>
    </div>
    <%--翻页end--%>
</c:if>
<script type="text/javascript">
    var facets = "${facetFieldJson}";
    function facetForward(url) {
        window.location.href = url + "&facetFieldJson=" + facets + "&queryFacetJson=${queryFacetJson}" ;
    }
    function sortForward(sortField,sortOrder) {
        var url = "<c:url value='/search.do?q=${q}&resultPage=${resultPage}&queryFacet=${queryFacet}&sortField='/>" + sortField + "&sortOrder=" + sortOrder;
        facetForward(url)
    }
</script>
