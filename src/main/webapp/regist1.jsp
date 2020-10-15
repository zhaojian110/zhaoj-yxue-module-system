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
        /*function changeImage() {
            var num = Math.random();
            $("#imgVcode").attr("src", "${pageContext.request.contextPath }/admin/getCode?" + num);
        }*/
    </script>
</head>
<body class="gray-bg">
    <div class="middle-box text-center loginscreen animated fadeInDown">
        <div>
            <div>
                <h1 class="logo-name">BZ</h1>
            </div>
                <h3>百知仓库后台管理系统</h3>

            <form class="m-t" action="${pageContext.request.contextPath}/admin/regist">
                <div class="form-group">
                    <input type="text" class="form-control" name="username" placeholder="用户名"/>
                </div>
                <div class="form-group">
                    <input type="password" class="form-control" name="password" placeholder="密码"/>
                </div>
                <button type="submit" class="btn btn-primary block full-width m-b">注 册 </button>
            </form>
            <h4>${sessionScope.message}</h4>
        </div>
    </div>
</body>
</html>