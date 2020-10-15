<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>

    $(function () {
        // 初始化用户数据表格
        $('#category-tt').jqGrid({
            url: '${pageContext.request.contextPath}/category/queryAllOne',
            datatype: 'json',
            colNames: ['id', '类别名称', '级别','pId'],
            styleUI: 'Bootstrap',
            colModel: [
                {name: 'id', },
                {name: 'name', editable: true, search:true,
                    searchoptions : {spot:['cn']},attr : { title: "Some title" }},
                {name: 'level', },
                {name: 'pId',
                },

            ],
            autowidth: true,
            mtype: 'get',
            pager: '#category-pager',
            rowList: [5,10, 20, 30],
            rowNum:5,
            viewrecords: true,
            editurl: '${pageContext.request.contextPath}/category/edit',//修改表单提交的路径
            cellurl: '${pageContext.request.contextPath}/category/edit',//添加
            multiselect: true,
            height: '400px',
            rownumbers: true,
            page: 1,
            subGrid : true,
            subGridRowExpanded : function(subgrid_id, row_id) {
                var subgrid_table_id =subgrid_id+"_t";
                var pager_id ="p_"+subgrid_table_id;
                $("#" + subgrid_id).html(
                    "<table id='" + subgrid_table_id
                    + "' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div>");
                jQuery("#" + subgrid_table_id).jqGrid(
                    {
                        url : "${path}/category/queryAllTwo?pid="+row_id/*ctx+"/SubGrid?q=2&id=" + row_id*/,
                        editurl: '${pageContext.request.contextPath}/category/edit?pid='+row_id,//修改表单提交的路径
                        datatype : "json",
                        styleUI: 'Bootstrap',
                        autowidth: true,
                        multiselect: true,
                        colNames : [ 'id', '名称', '级别', '父类id'],
                        colModel : [
                            {name : "id", width:55},
                            {name : "name",editable: true,width:80},
                            {name : "level",width:90},
                            {name : "pId",width:100},
                        ],
                        rowNum : 5,
                        pager : pager_id,
                        sortorder : "asc",
                        height : '200px'
                    });
                    $("#"+subgrid_table_id).jqGrid('navGrid', "#"+pager_id, {
                        edit : true,
                        add : true,
                        del : true,
                        edittext: "修改",
                        addtext: "添加",
                        deltext: "删除",
                        },
                        {
                            closeAfterAdd: true,//关闭添加框
                            //修改之后的额外操作
                            /*afterSubmit: function () {
                                $("#"+subgrid_table_id).trigger("reloadGrid");
                                /!*window.location.reload();*!/
                            }*/
                        },{
                            closeAfterDel: true
                        }
                    );
            }
        }).jqGrid('navGrid', '#category-pager', {
                edit: true,
                add: true,
                del: true,
                edittext: "修改",
                addtext: "添加",
                deltext: "删除",
                serch: true
            },
            {
                closeAfteredit: true,//关闭添加框
                //修改之后的额外操作
                afterSubmit: function () {
                    $("#category-tt").trigger("reloadGrid");
                    /*window.location.reload();*/
                }
            },
            {
                closeAfterAdd: true,//关闭添加框
                //修改之后的额外操作
                afterSubmit: function () {
                    $("#category-tt").trigger("reloadGrid");
                    /*window.location.reload();*/
                }
            }
        );
    });


</script>
<div class="panel panel-info">

    <div class="panel panel-heading">
        <h2>类别信息</h2>
    </div>
    <div>类别</div>
    <div class="btn btn-default">
        <a href="${pageContext.request.contextPath}/category/export">日志导出</a>
    </div>


    <table id="category-tt"></table>
    <div id="category-pager"></div>

</div>


