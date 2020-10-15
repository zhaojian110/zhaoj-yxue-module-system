<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>

    $(function () {
        // 初始化用户数据表格
        $('#user-tt').jqGrid({
            url: '${pageContext.request.contextPath}/log/queryAll',
            datatype: 'json',
            colNames: ['id', '操作用户用户名', '操作时间','操作表名称','操作类型','操作方法名称','data_id','data_info','数据恢复'],
            styleUI: 'Bootstrap',
            colModel: [
                {name: 'id', },
                {name: 'username', editable: true,},
                {name: 'operactionAt', edittype:'date',editable: true,
                    formatter: 'date',formatoptions: { srcformat: 'Y/m/d',newformat: 'Y/m/d'},},
                {name: 'tableName', editable: true},
                {name: 'operationMethod', editable: true},
                {name: 'methodName', editable: true},
                {name: 'dataId', editable: true},
                {name: 'dataInfo', editable: true},
                {name: 'cover', editable: true,
                    edittype:"select",
                    editoptions:{ value:"c:还原"},
                    align : "center",
                    formatter:function(cellvalue, options, rowObject){
                        //三个参数：列的值，操作，行对象  rowObject.id

                        return "<a class='btn btn-success' onclick='updateStatus(\""+rowObject.id+","+rowObject.tableName+","+rowObject.operationMethod+"\")'>还原</a>";
                    },
                }
            ],
            autowidth: true,
            mtype: 'get',
            pager: '#user-pager',
            rowList: [5,10, 20, 30],
            rowNum:5,
            viewrecords: true,
            <%--editurl: '${pageContext.request.contextPath}/user/edit',//修改表单提交的路径--%>
            <%--cellurl: '${pageContext.request.contextPath}/user/edit',//添加--%>
            multiselect: true,
            height: '600px',
            rownumbers: true,
            page: 1,
        }).jqGrid('navGrid', '#user-pager', {
                edit: true,
                add: true,
                del: true,
                edittext: "修改",
                addtext: "添加",
                deltext: "删除",
                serch: true
            },
            {
                closeAfterEdit: true,//关闭添加框
                //修改之后的额外操作

            },  //修改之后的额外操作
            {
                closeAfterAdd: true,//关闭添加框
                //添加之后的额外操作

            },  //添加之后的额外操作
            {}   //删除之后的额外操作
        );
        //给工具栏加自定义元素
    });
    function updateStatus(a,b,c) {
        $.ajax({
            url:"${pageContext.request.contextPath}/log/recover",
            type:"post",
            data: {id:a},
            success:function () {
                $("#user-tt").trigger("reloadGrid");
            }
        });
    }
    
    function logsImport() {
        var val = $("#file").val();
        if(val==null){
            alert("起飞");
        }else {
            $("#user-tt").trigger("reloadGrid");
        }
    }

</script>
<div class="panel panel-info">

    <div class="panel panel-heading">
        <h2>日志信息</h2>
    </div>
    <div>日志操作</div>
    <div style="text-align: right">
        <form action="${pageContext.request.contextPath}/log/importLog" method="post" enctype="multipart/form-data">
            <input id="file" type="file" name="file"/>
            <input class="btn btn-success"  type="submit" value="日志导入" onclick="logsImport()" />
        </form>
    </div>

    <div class="btn btn-default">
        <a href="${pageContext.request.contextPath}/log/export">日志导出</a>
    </div>


    <table id="user-tt"></table>
    <div id="user-pager"></div>
    
</div>


