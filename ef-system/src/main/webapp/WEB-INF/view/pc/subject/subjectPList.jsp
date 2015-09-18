<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/6/25
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ming800" uri="http://java.ming800.com/taglib" %>

<html>
<head>
    <title></title>
    <script>
        function removeProduct(productId){
            jQuery.ajax({
                type:"GET",
                url:'<c:url value="/basic/xmj.do?qm=removeProduct"/>',
                data:{id:productId},
                dataType:"json",
                success:function(data){
                    $("#"+productId).remove();
                }
            });
        }
    </script>
</head>
<body>


<div style="text-align: left" >
    <input onclick="window.location.href='<c:url value="/basic/xm.do?qm=formSubject"/>'" type="button" class="am-btn am-btn-default am-btn-xs" style="margin-top: 4px;margin-bottom: 6px;width: 100px;margin-left:2px;height: 35px;" value="新建专题" />
</div>
<jsp:include page="/do/generateTabs.do?qm=${requestScope.qm}&conditions=${requestScope.conditions}"/>
<div class="admin-content">
    <div class="am-g">
        <div class="am-u-sm-12 am-u-md-6">
        </div>
        <div class="am-u-sm-12">
            <table class="am-table am-table-striped am-table-hover table-main">
                <thead>
                <tr>
                    <th class="table-set" width="33%">操作</th>
                    <th class="table-title" width="33%">专题名称</th>
                    <th class="table-title" width="33%">专题封面</th>
                    <%--<th class="table-title">产品价格</th>--%>

                </tr>
                </thead>
                <tbody>



                <c:forEach items="${requestScope.pageInfo.list}" var="subject">
                    <tr id="${subject.id}">
                        <td>
                            <div class="am-btn-toolbar">
                                <div class="am-btn-group am-btn-group-xs">

                                    <%--<a class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only"  href="<c:url value="/basic/xm.do?qm=formProduct&view=${view}&id=${product.id}&tenantId=${tenantId}&masterId=${masterId}"/>">--%>
                                        <%--修改信息--%>
                                    <%--</a>--%>
                                    <a class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only"  href="<c:url value="/basic/xm.do?qm=formSubject&view=${view}&id=${subject.id}"/>">
                                        编辑
                                    </a>
                                    <a class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only"  href="javascript:void (0);" onclick="showConfirm('提示','是否删除',function(){removeSubject('${subject.id}')})">
                                        删除
                                    </a>
                                    <a class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only" onclick="recommendSubject('${subject.id}')" href="javascript:void (0);">
                                        推荐
                                    </a>
                                </div>
                            </div>
                        </td>
                        <td class="am-hide-sm-only"><a href="<c:url value='/basic/xm.do?qm=viewSubject&id=${subject.id}'/>">${subject.name}</a></td>
                        <td class="am-hide-sm-only">
                            <img width="35px;" src="<c:url value="http://pro.efeiyi.com/${subject.pictureUrl}@!product-model"/>"
                                 alt=""/>
                        </td>
                        <%--<td class="am-hide-sm-only">${product.price}</td>--%>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <div style="clear: both">
        <c:url value="/basic/xm.do" var="url" />
        <ming800:pcPageList bean="${requestScope.pageInfo.pageEntity}" url="${url}">
            <ming800:pcPageParam name="qm" value="${requestScope.qm}"/>
            <ming800:pcPageParam name="conditions" value="${requestScope.conditions}"/>
        </ming800:pcPageList>
    </div>
</div>
<script>
    function removeSubject(divId){
        $.ajax({
            type: "get",
            url: '<c:url value="/basic/xmj.do?qm=removeSubject"/>',
            cache: false,
            dataType: "json",
            data:{id:divId},
            success: function (data) {
                $("#"+divId).remove();
            }
        });
    }

    function recommendSubject(divId){
        $.ajax({
            type: "get",
            url: '<c:url value="/product/updateSubjectIndex.do"/>',
            cache: false,
            dataType: "json",
            data:{subjectId:divId},
            success: function (data) {
               location.reload();
            }
        });
    }
</script>

</body>
</html>