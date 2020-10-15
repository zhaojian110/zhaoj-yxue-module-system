<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <!-- Title and other stuffs -->
    <title>百知后台登录页面</title>
    <!-- Stylesheets 样式 -->
    <link href="http://system.tjldsd.com:80/style/bootstrap.css" rel="stylesheet" />
    <link rel="stylesheet" href="http://system.tjldsd.com:80/style/animate.css" />
    <link href="http://system.tjldsd.com:80/style/loginstyle.css" rel="stylesheet" />
    <!--引入jQuery-->
    <script src="${pageContext.request.contextPath}/static/jqgrid/js/jquery.min.js" type="text/javascript"></script>
    <!--引入js文件-->
    <script src="${pageContext.request.contextPath}/static/jqgrid/js/bootstrap-multiselect.js" type="text/javascript"></script>
    <script type="text/javascript">
        function changeImage() {
            var num = Math.random();
            $("#imgVcode").attr("src", "${pageContext.request.contextPath }/admin/getCode?" + num);
        }
        //控制的标签是谁
        $(function () {
            /**
             *
             * @param obj 要控制的标签对象
             * @param wait  等待的时间
             */
            function time(obj,wait) {
                let $this = $(obj);

                if (wait==0) {
                    // 如果等待时间wait为0的时候，设置按钮可以触发
                    $this.css('pointer-events','');
                    $this.html('发送验证码');
                    wait = 60;
                } else {
                    // 如果等待时间wait大于0,设置按钮禁用（不可以触发事件）
                    $this.css('pointer-events','none');
                    $this.html(wait+'秒后可以重新发送')
                    // 手动减秒
                    wait--;
                    setTimeout(function () {
                        // 递归调用time函数
                        time(obj, wait);
                    }, 1000);
                }


            }

            // 点击发送验证码
            $('#sendPhoneCode').click(function () {

                // 获取手机号 发送到后台
                var phoneNum = $('#phone').val();
                if(phoneNum.length!=11){
                    alert("滚回去重新输入！");
                }else {
                    alert("我要拨打的手机号是"+phoneNum);
                    // 控制发送验证码的周期
                    time(this, 60);
                    $.ajax({
                        url: '${pageContext.request.contextPath}/admin/sendPhoneCode?phone='+phoneNum,
                        type: "post",
                        dataType:"text",
                        data: '' , //上传的文件域id
                        //文件选择框的id属性，即<input type="file">的id
                        success: function () {
                            alert("我发送了手机验证码！QAQ");
                        }
                    })
                }

            })
        })

        function sendPCd() {

            //控制发送验证码的周期
            time(this,60);

        }
    </script>
</head>
<body class="gray-bg">
    <div class="middle-box text-center loginscreen animated fadeInDown">
        <div>
            <div>
                <h1 class="logo-name">BZ</h1>
            </div>
                <h3>百知仓库后台管理系统</h3>
                <h4>${sessionScope.loginMessage}</h4>
            <form class="m-t" action="${pageContext.request.contextPath}/admin/login">
                <div class="form-group">
                    <input type="text" class="form-control" name="username" placeholder="用户名"/>
                </div>
                <div class="form-group">
                    <input type="password" class="form-control" name="password" placeholder="密码"/>
                </div>

                <div class="form-group">
                    <div class="Captcha-operate">
                        <div class="Captcha-imageConatiner">
                            <a class="code_pic" id="vcodeImgWrap" name="change_code_img"
                               href="javascript:void(0);">
                                <img id="imgVcode" src="${pageContext.request.contextPath}/admin/getCode"
                                     class="Ucc_captcha Captcha-image" onClick="changeImage()">
                            </a>
                            <a id="vcodeImgBtn" name="change_code_link" class="code_picww"
                               href="javascript:changeImage()">换张图</a>
                            <input type="text" class="form-control" name="code" placeholder="验证码"/>
                            <span id="spn_vcode_ok" class="icon_yes pin_i" style="display: none;"></span>
                            <span id="J_tipVcode" class="cue warn"></span>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="Captcha-operate">
                        <div class="Captcha-imageConatiner">
                            <input type="text" class="form-control" id="phone" name="phone" placeholder="请输入手机号"/>


                            <a id="sendPhoneCode" href="javascript:void(0)"  onclick="sendPCd()" class="btn btn-default">发送验证码</a>
                            <input type="text" class="form-control" id="phoneCode" name="phoneCode" placeholder="请输入手机验证码"/>
                        </div>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary block full-width m-b">登 录 </button>
                <p class="text-muted text-center"><a href="login.html#">
                    <small>忘记密码了？</small>
                </a> | <a href="${pageContext.request.contextPath}/regist1.jsp">注册一个新账号</a>
                </p>

            </form>
        </div>
    </div>
</body>
</html>