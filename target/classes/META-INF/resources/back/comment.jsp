<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<link rel="stylesheet" href="${path}/static/bs/css/bootstrap.css" type="text/css">
<link rel="stylesheet" href="${path}/static/jqgrid/css/trirand/ui.jqgrid-bootstrap.css" type="text/css">

<script type="text/javascript" src="${path}/static/bs/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="${path}/static/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${path}/static/bs/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${path}/static/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="${path}/static/jqgrid/js/trirand/jquery.jqGrid.min.js"></script>
<script>
    $(function () {
        // 初始化视频数据表格
        $('#video-tt').jqGrid({
            url: '${pageContext.request.contextPath}/comment/queryAllFirst',
            datatype: 'json',
            colNames: ['编号', '用户名','资源Id','视频名称','图片名称','评论时间','评论内容','互动评论编号'],
            styleUI: 'Bootstrap',
            pager : '#video-pager',
            height: '600px',
            multiselect: true,
            colModel: [
                {name: 'id', },
                {name: 'user.username', },
                {name: 'sourceId',
                    editable: true,
                    edittype:"select",
                    search:false,
                    searchoptions:{sopt: ['eq','cn']},
                    editoptions:{
                        dataUrl:'/yx/comment/queryAllInter',
                    }
                },
                {name: 'video.coverUrl', },
                {name: 'img', },
                {name : 'createAt',width : 100,edittype:'date',
                    formatter: 'date',formatoptions: { srcformat: 'Y/m/d',newformat: 'Y/m/d'},
                },
                {name : 'content',editable:true,width : 100},
                {name : 'interactId',/*editable:true,*/width : 100,
                }
            ],
            rowList: [5,10, 20, 30],
            rowNum:5,
            autowidth: true,
            editurl: '${pageContext.request.contextPath}/comment/edit',//修改表单提交的路径
            subGrid : true,
            subGridRowExpanded : function(subgrid_id, row_id) {
                var subgrid_table_id =subgrid_id+"_t";
                var pager_id ="p_"+subgrid_table_id;
                $("#" + subgrid_id).html(
                    "<table id='" + subgrid_table_id
                    + "' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div>");
                jQuery("#" + subgrid_table_id).jqGrid(
                    {
                        url : "${pageContext.request.contextPath}/comment/queryAllTwo?pId="+row_id/*ctx+"/SubGrid?q=2&id=" + row_id*/,
                        editurl: '${pageContext.request.contextPath}/comment/edit?pId='+row_id,//修改表单提交的路径
                        datatype : "json",
                        styleUI: 'Bootstrap',
                        autowidth: true,
                        multiselect: true,
                        colNames : [ 'id', '名称', '评论时间', '评论内容'],
                        colModel : [
                            {name : "id", width:55},
                            {name : "user.username",width:80},
                            {name : "createAt",width:90,formatter: 'date',formatoptions: { srcformat: 'Y/m/d',newformat: 'Y/m/d'}},
                            {name : "content",editable:true,width:100},
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
        });
        $("#video-tt").jqGrid('navGrid', '#video-pager', {
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

            },{

            },{
                closeAfterDel: true,//关闭添加框
            }
        );
    })
</script>
<div class="panel panel-info">

    <div class="panel panel-heading">
        <h2>评论信息</h2>
    </div>
    <div class="btn btn-success">
        <a href="${pageContext.request.contextPath}/comment/export">日志导出</a>
    </div>

    评论
    <table id="video-tt"></table>
    <div id="video-pager"></div>

</div>
