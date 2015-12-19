
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/10/9
  Time: 10:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="<c:url value='/resources/jquery/jquery-2.1.3.min.js'/>"></script>
<script type='text/javascript' src='http://www.huimg.cn/app/baikesurvey/js/json2.js'></script>
<!doctype html>
<html class="no-js">
<head>

    <title>e飞蚁工艺秀</title>


</head>
<body>




<!-- //End--footer-->


<script>

    $(document).ready(function(){
        var json = ' \{"destCity": "北京市", "destDistrict": "", "destProvince": "北京", "logisticCompanyID": "DEPPON", "originalCity": "上海市", "originalDistrict": "", "originalProvince": "上海"\}';

        $.ajax({
            type: "post",
            url: "<c:url value='/freight/seachPrice.do'/>",//设置请求的脚本地址
            //contentType: 'application/json;charset=utf-8',
            dataType: "json",
            data:{
              "json": json,"weight":"7"
            },
            success: function (data) {
                alert(data);
            },
            error:function(){

                alert("出错了，请联系管理员！！！");
                return false;
            }
        });

    });

</script>
<!-- //End--footer-->
<script type="text/javascript" src="http://v3.jiathis.com/code/jia.js?uid=" charset="utf-8"></script>
</body>
</html>


