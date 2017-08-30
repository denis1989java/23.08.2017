<%@ page language="java"
         contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
           prefix="c" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>IT Book Store</title>

    <!-- Bootstrap core CSS -->
    <link href="${pageContext.request.contextPath}/resources/news/vendor/bootstrap/css/bootstrap.min.css"
          rel="stylesheet">

    <!-- Custom fonts for this template -->
    <link href="${pageContext.request.contextPath}/resources/news/vendor/font-awesome/css/font-awesome.min.css"
          rel="stylesheet" type="text/css">
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800'
          rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,400italic,700,700italic,900,900italic'
          rel='stylesheet' type='text/css'>

    <!-- Plugin CSS -->
    <link href="${pageContext.request.contextPath}/resources/news/vendor/magnific-popup/magnific-popup.css"
          rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/resources/news/css/creative.min.css" rel="stylesheet">

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

        .col-md-12 {
            text-align: left;
        }

        .pagination {
            display: flex;
            float: right;
        }

        .pagination a {
            padding: 10px;
            float: right;
            color: white;
            border: 1px solid white;
            background-color: black;
        }

        .pagination li a:hover:not(.active) {
            background-color: #e13612;
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

<body id="page-top">

<!-- Navigation -->
<nav class="navbar fixed-top navbar-toggleable-md navbar-light" id="mainNav">
    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse"
            data-target="#navbarExample" aria-controls="navbarExample" aria-expanded="false"
            aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="container">
        <a class="navbar-brand">NEWS</a>
        <div class="collapse navbar-collapse" id="navbarExample">
            <ul class="navbar-nav ml-auto">
                <security:authorize access="hasAuthority('USER')">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/cabinet">cabinet</a>
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
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/cabinet">Cabinet</a>
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
                        <a class="nav-link" href="${pageContext.request.contextPath}/superAdmin/users/0">users</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/superAdmin/cabinet">Cabinet</a>
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
        <div class="row">
            <div class="col-md-12">
                <security:authorize access="hasAuthority('USER')">
                    <c:forEach var="news" items="${viewDTO.viewMap['newsDTOS']}">
                        <p><a style="color: white;text-align: left"
                              href="<c:url context="/" value="/user/new/${news.newsId}"/>">${news.newsName}</a></p>
                    </c:forEach>
                </security:authorize>
                <security:authorize access="hasAuthority('ADMIN') || hasAuthority('SUPER_ADMIN')">
                    <table>
                        <tr>
                            <th style="color: #FFFFFF">New date</th>
                            <th style="color: #FFFFFF">New name</th>
                            <th style="color: #FFFFFF">New text</th>
                            <th style="color: #FFFFFF">Action</th>
                        </tr>
                        <c:forEach var="news" items="${viewDTO.viewMap['newsDTOS']}">
                            <tr>
                                <td style="color: #FFFFFF"><c:out value="${news.newsDate}"/></td>
                                <td style="color: #FFFFFF"><c:out value="${news.newsName}"/></td>
                                <td style="color: #FFFFFF"><c:out value="${news.newsText}"/></td>
                                <td>
                                    <security:authorize access="hasAuthority('ADMIN')">
                                        <spring:url value="/admin/new/${news.newsId}" var="showNewUrl"/>
                                        <spring:url value="/admin/changeNew/${news.newsId}" var="changeNew"/>
                                        <spring:url value="/admin/deleteNew/${news.newsId}" var="deleteNew"/>
                                    </security:authorize>
                                    <security:authorize access="hasAuthority('SUPER_ADMIN')">
                                        <spring:url value="/superAdmin/new/${news.newsId}" var="showNewUrl"/>
                                        <spring:url value="/superAdmin/changeNew/${news.newsId}" var="changeNew"/>
                                        <spring:url value="/superAdmin/deleteNew/${news.newsId}" var="deleteNew"/>
                                    </security:authorize>
                                    <button style="background-color: #e13612" class="btn btn-info"
                                            onclick="location.href='${showNewUrl}'">Show new
                                    </button>
                                    <button style="background-color: #e13612" class="btn btn-info"
                                            onclick="location.href='${changeNew}'">Change new
                                    </button>
                                    <button style="background-color: #e13612" class="btn btn-info"
                                            onclick="location.href='${deleteNew}'" >Delete new
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                    <security:authorize access="hasAuthority('ADMIN')">
                        <spring:url value="/admin/addNews" var="addNews"/>
                        <button style="background-color: #e13612; float: right" class="btn btn-info"
                                onclick="location.href='${addNews}'">Add new
                        </button>
                    </security:authorize>
                    <security:authorize access="hasAuthority('SUPER_ADMIN')">
                        <spring:url value="/superAdmin/addNews" var="addNews"/>
                        <button style="background-color: #e13612; float: right;" class="btn btn-info"
                                onclick="location.href='${addNews}'">Add new
                        </button>
                    </security:authorize>
                </security:authorize>
            </div>

        </div>
        <div class="row">
            <div class="col-md-2">
                <div class="pagination">
                    <security:authorize access="hasAuthority('USER')">
                        <c:forEach var="page" items="${viewDTO.viewMap['pagination']}">
                            <li><a href="?page=${page}">${page+1}</a></li>
                        </c:forEach>
                    </security:authorize>
                    <security:authorize access="hasAuthority('ADMIN') || hasAuthority('SUPER_ADMIN')">
                        <c:forEach var="page" items="${viewDTO.viewMap['pagination']}">
                            <li><a href="?page=${page}">${page+1}</a></li>
                        </c:forEach>
                    </security:authorize>
                </div>
            </div>
        </div>


    </div>

</header>


<script src="${pageContext.request.contextPath}/resources/news/vendor/jquery/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/news/vendor/tether/tether.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/news/vendor/bootstrap/js/bootstrap.min.js"></script>

<!-- Plugin JavaScript -->
<script src="${pageContext.request.contextPath}/resources/news/vendor/jquery-easing/jquery.easing.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/news/vendor/scrollreveal/scrollreveal.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/news/vendor/magnific-popup/jquery.magnific-popup.min.js"></script>

<!-- Custom scripts for this template -->
<script src="${pageContext.request.contextPath}/resources/news/js/creative.min.js"></script>

</body>

</html>
