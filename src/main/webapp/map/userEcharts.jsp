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
    <script src="${path}/echarts/china.js"></script>
    <script type="text/javascript">
        $(function(){

            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));

            var option = {
                title : {
                    text: '每月用户注册量',
                    subtext: '纯属虚构',
                    left: 'center'
                },
                tooltip : {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data:['1月','2月','3月']
                },
                visualMap: {
                    min: 0,
                    max: 200,
                    left: 'left',
                    top: 'bottom',
                    text:['高','低'],           // 文本，默认为数值文本
                    calculable : true
                },
                toolbox: {
                    show: true,
                    orient : 'vertical',
                    left: 'right',
                    top: 'center',
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                series : [
                    {
                        name: '1月',
                        type: 'map',
                        mapType: 'china',
                        roam: false,
                        label: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        data:[
                            {name: '北京',value: 34},
                            {name: '天津',value: 45},
                            {name: '上海',value: 32},
                            {name: '重庆',value: 22},
                            {name: '河北',value: 34},
                            {name: '河南',value: 56},
                            {name: '云南',value: 23},
                            {name: '辽宁',value: 34},
                            {name: '湖南',value: 23},
                            {name: '安徽',value: 24},
                            {name: '山东',value: 23},
                            {name: '新疆',value: 34},
                            {name: '江苏',value: 32},
                            {name: '浙江',value: 54},
                            {name: '江西',value: 23},
                            {name: '湖北',value: 32},
                            {name: '广西',value: 22},
                            {name: '甘肃',value: 12},
                            {name: '山西',value: 33},
                            {name: '陕西',value: 23},
                            {name: '吉林',value: 34},
                            {name: '福建',value: 54},
                            {name: '贵州',value: 23},
                            {name: '广东',value: 45},
                            {name: '青海',value: 23},
                            {name: '西藏',value: 35},
                            {name: '四川',value: 45},
                            {name: '宁夏',value: 32},
                            {name: '海南',value: 43},
                            {name: '台湾',value: 54},
                            {name: '香港',value: 42},
                            {name: '澳门',value: Math.round(Math.random()*1000)}
                        ]
                    },
                    {
                        name: '2月',
                        type: 'map',
                        mapType: 'china',
                        label: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        data:[
                            {name: '北京',value: 31},
                            {name: '天津',value: 22},
                            {name: '上海',value: 32},
                            {name: '重庆',value: 34},
                            {name: '河北',value: 53},
                            {name: '安徽',value: 63},
                            {name: '新疆',value: 73},
                            {name: '浙江',value: 37},
                            {name: '江西',value: 43},
                            {name: '山西',value: 39},
                            {name: '内蒙古',value: 33},
                            {name: '吉林',value: 36},
                            {name: '福建',value: 33},
                            {name: '广东',value: 33},
                            {name: '西藏',value: 39},
                            {name: '四川',value: 83},
                            {name: '宁夏',value: 63},
                            {name: '香港',value: 53},
                            {name: '澳门',value: 37}
                        ]
                    },
                    {
                        name: '3月',
                        type: 'map',
                        mapType: 'china',
                        label: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        data:[
                            {name: '北京',value: 20},
                            {name: '天津',value: 20},
                            {name: '上海',value: 15},
                            {name: '广东',value: 18},
                            {name: '台湾',value: 25},
                            {name: '香港',value: 23},
                            {name: '澳门',value: 32}
                        ]
                    }
                ]
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);

        });
    </script>
</head>
<body>
    <div id="main" style="width: 600px;height:400px;"></div>

</body>
</html>
