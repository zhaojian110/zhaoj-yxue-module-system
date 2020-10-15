<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script>
    $(function () {

        //点击搜索按钮执行
        $("#searchBtn").click(function(){

            //清楚表单内容
            $("#tableId").empty();

            //1.获取输入框的内容
            var content = $("#content").val();

            //清楚输入框内容
            $("#content").val("");
            //2.向后台发送请求
            $.ajax({
                url:"${path}/video/querySearchVideo",
                type:"get",
                data:{"content":content},
                dataType:"JSON",
                success:function(data){
                    $("#tableId").append("<tbody>" +
                        "<td>id</td>" +
                        "<td>标题</td>" +
                        "<td>描述</td>" +
                        "<td>图片</td>" +
                        "<td>视频</td>" +
                        "<td>日期</td>" +
                        "</tbody>")
                     //渲染页面
                    //遍历集合获取数据
                    //参数:要遍历的集合（集合下标,集合元素）
                    $.each(data,function(index,video){
                        //向表单中添加数据
                        $("#tableId").append("<tr>" +
                            "<td>"+video.id+"</td>" +
                            "<td>"+video.title+"</td>" +
                            "<td>"+video.intro+"</td>" +
                            "<td><img src="+"${pageContext.request.contextPath}/upload_file/videoImg/"+video.coverUrl+" width='300px' heigth='200px'></td>" +
                            "<td><video src="+"http://qhb8occix.hn-bkt.clouddn.com/"+video.videoUrl+" width='300px' heigth='200px'></td>" +
                            "<td>"+video.createAt+"</td>" +
                        //
                        "</tr>")
                    });
                }
            })
        });
    })

</script>

<br>
<%--搜索框--%>
<div align="center">
    <div class="input-group" style="width: 300px">
        <input id="content" type="text" class="form-control" placeholder="请输入标题或描述" aria-describedby="basic-addon2">
        <span class="input-group-btn" id="basic-addon2">
            <button class="btn btn-info" id="searchBtn">点击搜索</button>
        </span>
    </div>
</div><br>

<%--内容展示区域--%>
<div class="panel panel-success">

    <%--面板头--%>
    <div class="panel panel-heading">搜索结果</div>

    <%--表单--%>
    <table class="table" id="tableId" />

</div>