<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>

    $(function () {
        // 初始化用户数据表格
        $('#feedback-tt').jqGrid({
            url: '${pageContext.request.contextPath}/feedback/queryAll',
            datatype: 'json',
            colNames: ['编号', '标题', '内容','时间','用户编号','用户名称'],
            styleUI: 'Bootstrap',
            colModel: [
                {name: 'id', },
                {name: 'title', editable: true,},
                {name: 'content',editable: true,},
                {name: 'createAt',
                    edittype:'date',
                    formatter: 'date',
                    formatoptions: { srcformat: 'Y/m/d',newformat: 'Y/m/d'},
                },
                {name: 'userId',},
                {name: 'user.username',},
            ],
            autowidth: true,
            mtype: 'get',
            pager: '#feedback-pager',
            rowList: [5,10, 20, 30],
            rowNum:5,
            viewrecords: true,
            editurl: '${pageContext.request.contextPath}/feedback/edit',//修改表单提交的路径
            multiselect: true,
            height: '400px',
            rownumbers: true,
            page: 1,
        }).jqGrid('navGrid', '#feedback-pager', {
            edit: true,
            add: true,
            del: true,
            edittext: "修改",
            addtext: "添加",
            deltext: "删除",
            serch: true
        });
    });


</script>
<div class="panel panel-info">

    <div class="panel panel-heading">
        <h2>反馈信息</h2>
    </div>
    <div>反馈信息表</div>


    <table id="feedback-tt"></table>
    <div id="feedback-pager"></div>

</div>


