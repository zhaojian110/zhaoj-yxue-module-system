<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 2020/10/12
  Time: 17:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>应学App后台管理系统</title>

    <script src="${path}/static/bs/js/jquery-3.3.1.min.js"></script>
    <script src="${path}/echarts/echarts.js"></script>
    <script src="${path}/goEasyJS/goeasy-1.0.17.js"></script>
    <script type="text/javascript">
        $(function () {
            $.ajax({
                url: "${pageContext.request.contextPath}/echarts/queryUserRegist",
                type: "get",
                dataType:"JSON",
                data: '',
                //上传的文件域id
                //文件选择框的id属性，即<input type="file">的id
                success: function (data) {
                    //上传成功 所作的事情
                    // 基于准备好的dom，初始化echarts实例
                    var myChart = echarts.init(document.getElementById('main'));
                    // 指定图表的配置项和数据
                    var option = {
                        title: {
                            text: '用户注册统计图',  //标题
                            subtext:"就往后查了查",
                        },
                        tooltip: {}, //鼠标提示
                        legend: {
                            data:['注册时间',"状态"]  //选项卡
                        },
                        xAxis: {
                            data: data.month //横轴
                        },
                        yAxis: {},  //纵轴
                        series: [{
                            name: '注册时间',
                            type: 'bar',
                            data: data.time
                        },{
                            name: '状态',
                            type: 'line',
                            data:data.status
                        }]
                    };

                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);


                    /*window.location.reload();*/
                }
            })


        })
    </script>
    <script type="text/javascript">
        //发送请求
        var goEasy = new GoEasy({
            host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
            appkey: "BC-802a1ac5b8124929b19dfed4d54c8650", //替换为您的应用appkey
        });
        $(function () {
            var myChart = echarts.init(document.getElementById('main'));
            goEasy.subscribe({
                channel: "11111", //替换为您自己的channel
                onMessage: function (message) {
                    // alert(message.channel+"111111"+message.content);
                    var data = JSON.parse(message.content);
                    // 指定图表的配置项和数据
                    var option = {
                        title: {
                            text: '用户注册统计图',  //标题
                            subtext:"就往后查了查",
                        },
                        tooltip: {}, //鼠标提示
                        legend: {
                            data:['注册时间',"状态"]  //选项卡
                        },
                        xAxis: {
                            data: data.month //横轴
                        },
                        yAxis: {},  //纵轴
                        series: [{
                            name: '注册时间',
                            type: 'bar',
                            data: data.time
                        },{
                            name: '状态',
                            type: 'line',
                            data:data.status
                        }]
                    };
                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);
                }
            });

        })
    </script>
</head>
<body>
<div id="main" style="width: 600px;height:400px;"></div>

</body>
</html>
