<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/12/23
  Time: 14:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ming800" uri="http://java.ming800.com/taglib" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
  <title>试卷详情</title>
</head>
<body style="height: auto">
<div class="am-cf am-padding">
  <div class="am-fl am-cf">
    <strong class="am-text-primary am-text-lg">试卷基本信息</strong>
  </div>
</div>
<div class="am-cf am-padding">
  <div class="am-fl am-cf">
    <table class="am-table am-table-bordered am-table-radius am-table-striped">
      <tr>
        <td>试卷名称</td>
        <td>${object.name}</td>
        <td>创建时间</td>
        <td><fmt:formatDate value="${object.createDatetime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
      </tr>
    </table>
  </div>
</div>

<div style="text-align: left;margin-left: 10px;">
  <input type="button" class="am-btn am-btn-default am-btn-xs"
         style="margin-top: 4px;margin-bottom: 6px;margin-left:2px;height: 35px;"
         data-am-modal="{target: '#questionModel'}"
         value="关联题目"/>
</div>

<div>
  <table class="am-table am-table-bordered am-table-radius am-table-striped">
    <tr style="text-align:left">
      <td>操作</td>
      <td>题目编号</td>
      <td>题目名称</td>
      <td>创建时间</td>
    </tr>
    <c:forEach items="${EQList}" var="EQ">
      <tr>
        <td>
          <div class="am-btn-toolbar">
            <div class="am-btn-group am-btn-group-xs" style="width: 100%;">
              <button onclick="window.location.href='<c:url
                      value="/examination/delRelatedQuestion.do?examId=${object.id}&eqId=${EQ.id}"/>'"
                      class="am-btn am-btn-default am-btn-xs am-hide-sm-only">取消关联
              </button>
            </div>
          </div>
        </td>
        <td>${EQ.question.serial}</td>
        <td>${EQ.question.questionName}</td>
        <td><fmt:formatDate value="${EQ.question.createDatetime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
      </tr>
    </c:forEach>
  </table>
</div>

<div class="am-popup" id="questionModel" style="height: 550px; width: 900px">
  <div class="am-popup-inner">
    <div class="am-popup-hd">
      <h4 class="am-popup-title">关联题目</h4>
      <span data-am-modal-close class="am-close">&times;</span>
    </div>
    <div class="am-popup-bd" style="height: 10px">
      <input type="text" name="selQuestionModel"  style="float: left" placeholder="编号或名称" value=""/>
      <a style="width: 10%;float: left;margin-left: 10px;"
         class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only"
         href="javascript:void(0);" onclick="selQuestionModel()">查找
      </a>
    </div>
    <div class="am-popup-bd" style="height: 420px">
      <table class="am-table am-table-bd am-table-bdrs am-table-striped am-table-hover">
        <tr>
          <td class="am-text-center" width="4%">操作</td>
          <td class="am-text-center" width="12%">题目编号</td>
          <td class="am-text-center" width="20%">题目名称</td>
          <td class="am-text-center" width="16%">创建时间</td>
        </tr>
      </table>
      <div style="height: 350px; overflow-y: auto;">
        <table class="am-table am-table-bd am-table-bdrs am-table-striped am-table-hover"
               id="questionTable">
          <tbody>
          <c:forEach var="question" items="${questionList}">
            <tr name="${question.questionName}" serial="${question.serial}">
              <td class="am-text-center" width="4%">
                <input type="checkbox" name="qModel" value="${question.id}">
              </td>
              <td class="am-text-center" width="12%">${question.serial}</td>
              <td class="am-text-center" width="20%">${question.questionName}</td>
              <td class="am-text-center" width="16%">
                <fmt:formatDate value="${question.createDatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>
              </td>
            </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>
      <div style="height: 10px; margin-top: 10px" align="center">
        <input type="button" name="cancel" value="取消" onclick="btnCancel()"/>
        <input type="button" name="confirm" value="确定"
               onclick="btnConfirm('<c:url value="/examination/relateQuestions.do?examId=${object.id}"/>')"/>
      </div>
    </div>
  </div>
</div>

<script>
  function selQuestionModel(){
    var v = $("input[name='selQuestionModel']").val();
    if(v==""){
      $("#questionTable tr:gt(0)").each(function(){
        $(this).show();
      });
    }else {
      $("#questionTable tr:gt(0)").each(function () {
        if ($(this).attr("name").indexOf(v) == -1 && $(this).attr("serial").indexOf(v) == -1 ) {
          $(this).hide();
        } else {
          $(this).show();
        }
      });
    }
  }

  function btnCancel(){
    $("#questionModel").modal('close');
  }

  function btnConfirm(url){
    var qIdList = "";
    $("input[name='qModel']:checked").each(function(){
      if(qIdList != ""){
        qIdList = qIdList + "," + $(this).val();
      }else {
        qIdList = $(this).val();
      }
    });

    $("#questionModel").modal('close');
    window.location.href = url + "&qIdList=" + qIdList;
  }
</script>

</body>
</html>
