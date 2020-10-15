<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
            url: '${pageContext.request.contextPath}/video/queryAll',
            datatype: 'json',
            colNames: ['编号', '视频标题','简介','视频封面','视频','上传时间','上传用户','所属分类','分组','播放量','点赞数量'],
            styleUI: 'Bootstrap',
            pager : '#video-pager',
            height: '600px',
            multiselect: true,
            colModel: [
                {'name': 'id', },
                {'name': 'title', editable: true},
                {'name': 'intro', editable: true},
                {'name': 'coverUrl',
                    edittype:"file",
                    align:"center",
                    formatter: function (cellvalue, options, rowObject) {
                        return "<img src='${pageContext.request.contextPath}/upload_file/videoImg/" + cellvalue + "' width='64px' height='64px'   >"
                    }
                },
                {name : 'videoUrl',editable:true,width : 180,align : "center",edittype:"file",
                        formatter:
                            //参数：各子的值,操作,行对象
                        function(cellvalue, options, rowObject){
                        return "<video width='400px' height='120px' src='http://qhb8occix.hn-bkt.clouddn.com/" + cellvalue + "' controls poster='"+rowObject.cover+"'/>";
                    }},
                {name : 'createAt',editable:true,width : 100,edittype:'date',
                    formatter: 'date',formatoptions: { srcformat: 'Y/m/d',newformat: 'Y/m/d'},
                },
                {name : 'user.username',/*editable:true,*/width : 100},
                {name : 'category.name',editable:true,
                    width : 100,
                    edittype:"select",
                    editoptions:{
                        dataUrl:'${pageContext.request.contextPath}/video/queryCate',
                    },
                },
                {name : 'group.name',/*editable:true,*/width : 100},
                {name : 'play.playNum',width : 80,align : "center"},
                {name : 'cou',width : 80,align : "center"}
            ],
            rowList: [5,10, 20, 30],
            rowNum:5,
            autowidth: true,
            editurl: '${pageContext.request.contextPath}/video/edit',//修改表单提交的路径
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
                closeAfterAdd: true,//关闭添加框
                afterSubmit: function (response) {
                    //添加成功之后执行的内容
                    //1.数据入库    文件数据不对   文件没有上传
                    //2.文件异步上传   添加成功之后  上传
                    //3.修改文件路径   （id,要的的字段内容）
                    //id=  data.responseText
                    var id = response.responseJSON.message;
                    alert(id);
                    console.log(id);
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/video/upload",
                        type: "post",
                        dataType:"text",
                        data: {id: id},
                        fileElementId: "videoUrl", //上传的文件域id
                        //文件选择框的id属性，即<input type="file">的id
                        success : function () {
                            //上传成功 所作的事情
                            //刷新页面
                            $("#category-tt").trigger("reloadGrid");
                            alert("我成功了！");
                            /*window.location.reload();*/
                        }
                    });
                    return "2333";
                }
            },{
                closeAfterDel: true,//关闭添加框
            }
        );
    })
</script>
<div class="panel panel-info">

    <div class="panel panel-heading">
        <h2>视频信息</h2>
    </div>

    视频
    <table id="video-tt"></table>
    <div id="video-pager"></div>

</div>
