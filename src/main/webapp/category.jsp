<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>

    $(function () {
        // 初始化用户数据表格
        $('#user-tt').jqGrid({
            url: '${pageContext.request.contextPath}/user/querryAll',
            datatype: 'json',
            colNames: ['id', '用户名', '手机号','简介','头像','用户状态','注册时间','学分','VX号'],
            styleUI: 'Bootstrap',
            colModel: [
                {name: 'id', },
                {name: 'username', editable: true,},
                {name: 'mobile', editable: true},
                {name: 'sign', editable: true},
                {
                    name: 'headShow',
                    edittype: "file",
                    editable: true,
                    index: 'name asc, invdate',
                    width: 100,
                    align: "center",
                    //参数：各子的值,操作,行对象
                    formatter: function (cellvalue, options, rowObject) {
                        return "<img src='${pageContext.request.contextPath}/static/img/" + cellvalue + "' width='64px' height='64px'   >"
                    }
                },
                {name: 'status',
                    editable: true,
                    edittype:"select",
                    editoptions:{ value:"N:冻结; Y:正常"},
                    align : "center",
                    formatter:function(cellvalue, options, rowObject){
                        //三个参数：列的值，操作，行对象  rowObject.id
                        if(cellvalue=="Y"){
                            return "<a class='btn btn-success' onclick='updateStatus(\""+rowObject.id+"\")'>正常</a>";
                        }else{
                            return "<a class='btn btn-danger' onclick='updateStatus(\""+rowObject.id+"\")' >冻结</a>";
                        }
                }},
                {name: 'regTime',edittype:'date',editable: true,
                    formatter: 'date',formatoptions: { srcformat: 'Y/m/d',newformat: 'Y/m/d'},
                    },
                {name: 'score', editable: true},
                {name: 'wechat', editable: true},
            ],
            autowidth: true,
            mtype: 'get',
            pager: '#user-pager',
            rowList: [5,10, 20, 30],
            rowNum:5,
            viewrecords: true,
            editurl: '${pageContext.request.contextPath}/user/edit',//修改表单提交的路径
            cellurl: '${pageContext.request.contextPath}/user/edit',//添加
            serchurl: '${path}/',
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
                afterSubmit: function (response) {
                    //添加成功之后执行的内容
                    //1.数据入库    文件数据不对   文件没有上传
                    //2.文件异步上传   添加成功之后  上传
                    //3.修改文件路径   （id,要的的字段内容）
                    //id=  data.responseText
                    var id = response.responseJSON.message;
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/user/upload",
                        type: "post",
                        dataType:"text",
                        data: {id: id},
                        fileElementId:"headShow", //上传的文件域id
                        //文件选择框的id属性，即<input type="file">的id
                        success: function () {
                            //上传成功 所作的事情
                            //刷新页面
                            $("user-tt").trigger("reloadGrid");
                            /*window.location.reload();*/
                        }
                    });
                    return "ok";
                }
            },  //修改之后的额外操作
            {
                closeAfterAdd: true,//关闭添加框
                //添加之后的额外操作
                editData:{id:''},
                afterSubmit: function (response) {
                    //添加成功之后执行的内容
                    //1.数据入库    文件数据不对   文件没有上传
                    //2.文件异步上传   添加成功之后  上传
                    //3.修改文件路径   （id,要的的字段内容）
                    //id=  data.responseText
                    var id = response.responseJSON.message;
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/user/upload",
                        type: "post",
                        dataType:"text",
                        data: {id: id},
                        fileElementId:"headShow", //上传的文件域id
                        //文件选择框的id属性，即<input type="file">的id
                        success: function () {
                            //上传成功 所作的事情
                            //刷新页面
                            $("#user-tt").trigger("reloadGrid");
                            /*window.location.reload();*/
                        }
                    });
                    return "2333";
                }
            },  //添加之后的额外操作
            {}   //删除之后的额外操作
        );
        //给工具栏加自定义元素
        $("#lui_user-tt").append($('<input type="text"><input type="button" value="搜索">'))
    });

    function updateStatus(a) {
        $.ajax({
            "url":"${pageContext.request.contextPath}/user/updateStatus",
            "type":"post",
            "data":"id="+a,
            "success":function () {
                $("#user-tt").trigger("reloadGrid");
            }
        });
    }
</script>
<div class="panel panel-info">

    <div class="panel panel-heading">
        <h2>用户信息</h2>
    </div>
    <div>用户</div>


    <table id="user-tt"></table>
    <div id="user-pager"></div>

</div>


