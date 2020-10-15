<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<script>
    $(function () {
        // 初始化视频数据表格
        $('#video-tt').jqGrid({
            url: 'static/yxue/02.json',
            datatype: 'json',
            colNames: ['id', 'name'],
            styleUI: 'Bootstrap',
            colModel: [
                {'name': 'id', editable: true},
                {'name': 'name', editable: true},
            ],
            autowidth: true,
            mtype: 'get',
            pager: '#video-pager',
            rowList: [10, 20, 30],
            viewrecords: true,
            editurl: '',
            cellurl: '',
            multiselect: true,
            height: '300px',
            rownumbers: true,
            page: 1,
        }).jqGrid('navGrid', '#video-pager');
    });

</script>
<div class="panel panel-info">

    <div class="panel panel-heading">
        <h2>视频信息</h2>
    </div>

    视频
    <table id="video-tt"></table>
    <div id="video-pager"></div>

</div>
