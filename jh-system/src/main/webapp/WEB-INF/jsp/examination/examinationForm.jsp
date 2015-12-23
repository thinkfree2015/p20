<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/12/23
  Time: 10:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ming800" uri="http://java.ming800.com/taglib" %>
<html>
<head>
  <title>试卷信息</title>
  <script src="<c:url value='/DatePicker/WdatePicker.js'/>"></script>
</head>
<body>
<div class="am-cf am-padding">
  <div class="am-fl am-cf">
    <strong class="am-text-primary am-text-lg">试卷信息</strong>
  </div>
</div>
<hr/>
<div class="am-g">
  <form id="examForm" onsubmit="return afterSubmitForm('examForm')"
          action="<c:url value='/basic/xm.do?qm=saveOrUpdateExamination'/>"
          method="post" enctype="multipart/form-data" class="am-form am-form-horizontal">
    <input type="hidden" name="id" value="${object.id}">
    <input type="hidden" name="serial" value="${object.serial}"/>
    <input type="hidden" name="status" value="${object.status}"/>

    <div class="am-form-group">
      <label for="name" class="am-u-sm-3 am-form-label">试卷名称<small>*</small></label>
      <div class="am-u-sm-9">
        <input type="text" name="name" id="name"
               title="试卷名称" placeholder="试卷名称"
               value="${object.name}" required="true">
      </div>
    </div>

    <div class="am-form-group">
      <label for="url" class="am-u-sm-3 am-form-label">试卷链接<small>&nbsp;&nbsp;</small></label>
      <div class="am-u-sm-9">
        <input type="text" name="url" id="url"
               title="试卷链接" placeholder="试卷链接"
               value="${object.url}">
      </div>
    </div>

    <div class="am-form-group">
      <label for="relayLimit" class="am-u-sm-3 am-form-label">限制次数<small>*</small></label>
      <div class="am-u-sm-9">
        <input type="number" name="relayLimit" id="relayLimit"
               title="限制次数" placeholder="限制次数"
               value="${object.relayLimit}" required="true">
      </div>
    </div>

    <div class="am-form-group">
      <label for="expireDate" class="am-u-sm-3 am-form-label">答题时间<small>*</small></label>
      <div class="am-u-sm-9">
        <input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
               type="text" style="height: 35px"
               name="expireDate" id="expireDate" title="答题时间" placeholder="答题时间"
               value="${object.expireDate}" required="true" readonly>
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
