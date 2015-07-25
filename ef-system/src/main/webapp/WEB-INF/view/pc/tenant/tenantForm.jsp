<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/6/29
  Time: 15:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="ming800" uri="http://java.ming800.com/taglib" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
    <script src="/scripts/ckeditor/ckeditor.js"></script>
</head>
<body>

<div class="am-g">

    <div class="am-u-sm-12 am-u-md-4 am-u-md-push-8">
        <div class="am-panel am-panel-default">
        </div>
    </div>

    <div class="am-u-sm-12 am-u-md-8 am-u-md-pull-4">
        <form action="<c:url value="/basic/xmm.do"/>" method="post" class="am-form am-form-horizontal"
              enctype="multipart/form-data">
            <input type="hidden" value="saveOrUpdateTenant" name="qm">
            <input type="hidden" name="id" value="${object.id}">

            <div class="am-form-group">
                <label for="name" class="am-u-sm-3 am-form-label">姓名拼音 / NamePinYi</label>

                <div class="am-u-sm-9">
                    <input type="text" id="name" name="name" placeholder="姓名拼音 / NamePinYi" value="${object.name}">
                </div>
            </div>
            <div class="am-form-group">
                <label for="fullName" class="am-u-sm-3 am-form-label">中文姓名 / FullName</label>

                <div class="am-u-sm-9">
                    <input type="text" id="fullName" name="fullName" placeholder="中文姓名 / FullName" value="${object.fullName}">
                </div>
            </div>

            <div class="am-form-group">
                <label class="am-u-sm-3 am-form-label">等级 / Level</label>

                <div class="am-u-sm-9">
                    <ming800:status name="level" dataType="Tenant.level" checkedValue="${object.level}" type="select"/>

                </div>
            </div>


            <div class="am-form-group">
                <label for="birthday" class="am-u-sm-3 am-form-label">出生日期 / Birthday</label>

                <div class="am-u-sm-9">
                    <div class="am-form-group am-form-icon">
                        <i class="am-icon-calendar"></i>
                        <input type="text" id="birthday" name="birthday" class="am-form-field am-input-sm"
                               placeholder="出生日期" value="${object.birthday}" data-am-datepicker="{format: 'yyyy ', viewMode: 'years', minViewMode: 'years'}" readonly/>
                    </div>
                </div>
            </div>
            <div class="am-form-group">
                <label for="provinceName" class="am-u-sm-3 am-form-label">籍贯 / ProvinceName</label>

                <div class="am-u-sm-9">
                    <input type="text" id="provinceName" name="provinceName" placeholder="籍贯 / ProvinceName" value="${object.provinceName}">
                </div>
            </div>
            <div class="am-form-group">
                <label for="presentAddress" class="am-u-sm-3 am-form-label">现居地/ PresentAddress</label>

                <div class="am-u-sm-9">
                    <input type="text" id="presentAddress" name="presentAddress" placeholder="现居地/ PresentAddress" value="${object.presentAddress}">
                </div>
            </div>
            <div class="am-form-group">
                <label for="title" class="am-u-sm-3 am-form-label">头衔/ Title</label>

                <div class="am-u-sm-9">
                    <input type="text" id="title" name="title" placeholder="头衔/ Title" value="${object.title}">
                </div>
            </div>
            <div class="am-form-group">
                <label for="sex" class="am-u-sm-3 am-form-label">性别 / Sex</label>

                <div class="am-u-sm-9">
                    <ming800:status name="sex" dataType="Tenant.sex" checkedValue="${object.sex}" type="select" />
                </div>
            </div>

            <div class="am-form-group">
                <label for="favicon" class="am-u-sm-3 am-form-label">头像 / Favicon</label>

                <div class="am-u-sm-9">
                    <input type="file" id="favicon" name="favicon" placeholder="头像 / Favicon"
                           value="${object.favicon}">
                </div>
            </div>

            <div class="am-form-group">
                <label for="background" class="am-u-sm-3 am-form-label">背景图片</label>

                <div class="am-u-sm-9">
                    <input type="file" id="background" name="background" placeholder="背景图片"
                           value="${object.favicon}">
                </div>
            </div>

            <div class="am-form-group">
                <label for="user-intro" class="am-u-sm-3 am-form-label">短简介 / Brief</label>
                <div class="am-u-sm-9">
                    <textarea class="" cols="6" name="brief" rows="2" id="user-intro" placeholder="输入短简介">
                        ${object.brief}
                    </textarea>
                </div>
            </div>


            <div class="am-form-group">
                <label for="content" class="am-u-sm-3 am-form-label">简介 / Intro</label>

                <div class="am-u-sm-9">
                            <textarea id="content" name="content" class="ckeditor"
                                      placeholder="输入简介">${object.content}</textarea>
                </div>
            </div>

            <div class="am-form-group">
                <div class="am-u-sm-9 am-u-sm-push-3">
                    <button type="submit" class="am-btn am-btn-primary">保存</button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
