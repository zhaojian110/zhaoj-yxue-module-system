<%@page isELIgnored="false" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<!doctype html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Document</title>
        <script src="${path}/static/jqgrid/js/jquery.min.js"></script>
        <script type="text/javascript" src="${path}/static/goeasy-1.0.17.js"></script>
        <script type="text/javascript">

            //初始化GoEasy对象
            var goEasy = new GoEasy({
                host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
                appkey: "BC-49c4bb3ed91945448c35358477615835", //替换为您的应用appkey
            });


            $(function(){

                var msgs;

                //接收消息
                goEasy.subscribe({
                    channel: "2004Ch", //替换为您自己的channel
                        onMessage: function (message) {
                        //获取内容
                        var content=message.content;

                        //判断内容是不是你发的
                        if(msgs!=content){
                            //设置内容样式
                            var msgStyle = ("" +
                                "<div style='width: auto;height: 30px;'>" +
                                "<div style='float: left;background-color: #c1e2b3 ;border-radius:100px'>" +
                                "" + content + "" +
                                "</div>" +
                                "</div>"
                            );

                            //渲染页面
                            $("#showMsg").append(msgStyle);
                        }
                    }
                });

                //发送消息
                $("#sedMsg").click(function(){
                    //获取输入的消息内容
                    var msg=$("#msg").val();

                    msgs=msg;

                    //清楚输入框
                    $("#msg").val("");

                    goEasy.publish({
                        channel: "2004Ch", //替换为您自己的channel
                        message: msg, //替换为您想要发送的消息内容
                        onSuccess:function(){

                            //设置消息样式
                            /*var msgStyle=("" +
                                "<div style='height: 20px;width: auto;'>" +
                                "<div style='float: right;background-color: #4cae4c;border-radius: 50px'>"+
                                ""+msg+"" +
                                "</div>" +
                                "</div>"
                            );*/

                            //设置内容样式
                            var msgStyle = ("" +
                                "<div style='width: auto;height: 30px;'>" +
                                "<div style='float: right;background-color: #78FA84;border-radius:100px'>" +
                                "" + msg + "" +
                                "</div>" +
                                "</div>"
                            );

                            //渲染页面
                            $("#showMsg").append(msgStyle);
                        },
                        onFailed: function (error) {
                            alert("消息发送失败，错误编码："+error.code+" 错误信息："+error.content);
                        }
                    });

                });

            });

        </script>
    </head>
    <body>
        <div align="center">
            <h1> 2004 聊天群</h1>

            <div style="width: 600px;height: 700px;border: 3px #4cae4c solid ">

                <%--聊天内容展示区域--%>
                <div id="showMsg" style="width: 594px;height: 600px;border: 3px #e4b9b9 solid "></div>
                <%--输入发送聊天内容--%>
                <div style="width: 594px;height: 88px;border: 3px #ccaadd solid ">
                    <%--输入框--%>
                    <textarea id="msg" style="width: 500px;height: 82px;border: 3px black solid;float: left "></textarea>
                    <%--提交按钮--%>
                    <button id="sedMsg" style="width: 84px;height: 88px;border: 3px #2aabd2 solid;float: right ">发送</button>
                </div>
            </div>
        </div>


    </body>
</html>