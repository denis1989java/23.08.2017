<%@ page language="java"
         contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
           prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html lang="en">
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>IT Book Store</title>

    <!-- Bootstrap core CSS -->
    <link href="${pageContext.request.contextPath}/resources/user/vendor/bootstrap/css/bootstrap.min.css"
          rel="stylesheet">

    <!-- Custom fonts for this template -->
    <link href="${pageContext.request.contextPath}/resources/user/vendor/font-awesome/css/font-awesome.min.css"
          rel="stylesheet" type="text/css">
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800'
          rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,400italic,700,700italic,900,900italic'
          rel='stylesheet' type='text/css'>

    <!-- Plugin CSS -->
    <link href="${pageContext.request.contextPath}/resources/user/vendor/magnific-popup/magnific-popup.css"
          rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/resources/user/css/creative.min.css" rel="stylesheet">

    <!-- Temporary navbar container fix -->
    <style>
        .navbar-toggler {
            z-index: 1;
        }

        @media (max-width: 576px) {
            nav > .container {
                width: 100%;
            }
        }
    </style>

</head>

<body id="page-top">

<!-- Navigation -->
<nav class="navbar fixed-top navbar-toggleable-md navbar-light" id="mainNav">
    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse"
            data-target="#navbarExample" aria-controls="navbarExample" aria-expanded="false"
            aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="container">
        <a class="navbar-brand">Cabinet</a>
        <div class="collapse navbar-collapse" id="navbarExample">
            <ul class="navbar-nav ml-auto">

                <security:authorize access="hasAuthority('USER')">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/news/0">news</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/user/catalogue/0">catalogue</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/profile">my profile</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/orders">my orders</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/basket">basket</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/logout">logout</a>
                    </li>
                </security:authorize>
                <security:authorize access="hasAuthority('ADMIN')">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/messages/0">messages</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/news/0">news</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/admin/catalogue/0">catalogue</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/profile">my profile</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/orders/0">Orders</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/logout">logout</a>
                    </li>
                </security:authorize>
                <security:authorize access="hasAuthority('SUPER_ADMIN')">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/superAdmin/messages/0">messages</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/superAdmin/users/0">users</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/superAdmin/news/0">news</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/superAdmin/catalogue/0">catalogue</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/superAdmin/profile">my
                            profile</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/superAdmin/orders/0">Orders</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/logout">logout</a>
                    </li>
                </security:authorize>
            </ul>
        </div>
    </div>
</nav>
<header class="masthead">
    <div class="header-content ">
        <div class="header-content-inner">
            <security:authorize access="hasAuthority('USER')">
                <h1 id="homeHeading">Welcome to your personal account</h1>
            </security:authorize>
            <security:authorize access="hasAuthority('ADMIN')">
                <h1 id="homeHeading">Welcome admin</h1>
            </security:authorize>
            <security:authorize access="hasAuthority('SUPER_ADMIN')">
                <h1 id="homeHeading">Welcome SuperAdmin</h1>
            </security:authorize>
        </div>
    </div>
</header>


<script src="${pageContext.request.contextPath}/resources/user/vendor/jquery/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/user/vendor/tether/tether.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/user/vendor/bootstrap/js/bootstrap.min.js"></script>

<!-- Plugin JavaScript -->
<script src="${pageContext.request.contextPath}/resources/user/vendor/jquery-easing/jquery.easing.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/user/vendor/scrollreveal/scrollreveal.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/user/vendor/magnific-popup/jquery.magnific-popup.min.js"></script>

<!-- Custom scripts for this template -->
<script src="${pageContext.request.contextPath}/resources/js/creative.min.js"></script>

</body>
</html>
