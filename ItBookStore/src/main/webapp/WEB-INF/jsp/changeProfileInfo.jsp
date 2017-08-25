<%@ page language="java"
         contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
           prefix="c" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>IT Book Store</title>

    <!-- Bootstrap core CSS -->
    <link href="${pageContext.request.contextPath}/resources/profile/vendor/bootstrap/css/bootstrap.min.css"
          rel="stylesheet">

    <!-- Custom fonts for this template -->
    <link href="${pageContext.request.contextPath}/resources/profile/vendor/font-awesome/css/font-awesome.min.css"
          rel="stylesheet" type="text/css">
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800'
          rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,400italic,700,700italic,900,900italic'
          rel='stylesheet' type='text/css'>

    <!-- Plugin CSS -->
    <link href="${pageContext.request.contextPath}/resources/profile/vendor/magnific-popup/magnific-popup.css"
          rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/resources/profile/css/creative.min.css" rel="stylesheet">

    <!-- Temporary navbar container fix -->
    <style>


        #login-nav .social-buttons a {
            width: 49%;
        }

        #login-nav .form-group {
            margin-bottom: 10px;

        }

        .navbar-toggler {
            z-index: 1;
        }

        @media (max-width: 576px) {
            nav > .container {
                width: 100%;
            }
        }

        table {
            border: 1px solid white;
            width: 100%;
            text-align: center;
        }

        th, td {
            border: 1px solid white;
            text-align: left;
        }

    </style>

</head>
<body>
<nav class="navbar fixed-top navbar-toggleable-md navbar-light" id="mainNav">
    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse"
            data-target="#navbarExample" aria-controls="navbarExample" aria-expanded="false"
            aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="container">
        <a class="navbar-brand" href="profile">Change Info</a>
        <div class="collapse navbar-collapse" id="navbarExample">
            <ul class="navbar-nav ml-auto">
                <security:authorize access="hasAuthority('USER')">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/home">IT Book Store</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/cabinet">cabinet</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/news/0">news</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/user/catalogue/0">catalogue</a>
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
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/cabinet">cabinet</a>
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
                        <a class="nav-link" href="${pageContext.request.contextPath}/superAdmin/cabinet">cabinet</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/superAdmin/users/0">users</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/superAdmin/news/0">news</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/superAdmin/catalogue/0">catalogue</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/superAdmin/profile">my profile</a>
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
    <div class="header-content">
        <div class="container">
            <div class="row main" id="login-nav">
                <div class="main-login main-center">
                    <form:form modelAttribute="userInformationDTO" method="post" action="changeInfo">
                        <div class="form-inline">
                            <form:input path="userName"
                                        type="text" id="userName" name="userName" class="form-control"/>
                        </div>
                        <div class="form-inline">
                            <form:input path="userSurname"
                                        type="text" class="form-control" id="userSurname" name="userSurname"/>
                        </div>
                        <div class="form-inline">
                            <form:input path="userSecondName"
                                        type="text" class="form-control" id="userSecondName" name="userSecondName"/>
                        </div>
                        <div class="form-inline">
                            <form:input path="userPhoneNumber"
                                        class="form-control" id="userPhoneNumber" name="userPhoneNumber"/>
                        </div>
                        <div class="form-inline">
                            <form:input path="userAddress" type="text" class="form-control" id="userAddress"
                                        name="userAddress"/>
                        </div>
                        <div class="form-inline">
                            <form:input path="userAdditionalInfo" type="text"
                                        class="form-control" id="userAdditionalInfo" name="userAdditionalInfo"/>
                        </div>
                        <div class="form-inline">
                            <button type="submit" class="btn btn-primary btn-block" name="saveInfo" value="save">Save
                            </button>
                        </div>
                        <strong> <c:out value="${error}"></c:out></strong>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
    </div>
</header>

<script src="${pageContext.request.contextPath}/resources/profile/vendor/jquery/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/profile/vendor/tether/tether.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/profile/vendor/bootstrap/js/bootstrap.min.js"></script>

<!-- Plugin JavaScript -->
<script src="${pageContext.request.contextPath}/resources/profile/vendor/jquery-easing/jquery.easing.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/profile/vendor/scrollreveal/scrollreveal.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/profile/vendor/magnific-popup/jquery.magnific-popup.min.js"></script>

<!-- Custom scripts for this template -->
<script src="${pageContext.request.contextPath}/resources/profile/js/creative.min.js"></script>

</body>

</html>
