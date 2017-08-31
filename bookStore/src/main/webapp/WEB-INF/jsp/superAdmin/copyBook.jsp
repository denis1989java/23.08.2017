<%@ page language="java"
         contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
           prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>IT Book Store</title>

    <!-- Bootstrap core CSS -->
    <link href="${pageContext.request.contextPath}/resources/basket/vendor/bootstrap/css/bootstrap.min.css"
          rel="stylesheet">

    <!-- Custom fonts for this template -->
    <link href="${pageContext.request.contextPath}/resources/basket/vendor/font-awesome/css/font-awesome.min.css"
          rel="stylesheet" type="text/css">
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800'
          rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,400italic,700,700italic,900,900italic'
          rel='stylesheet' type='text/css'>

    <!-- Plugin CSS -->
    <link href="${pageContext.request.contextPath}/resources/new/vendor/magnific-popup/magnific-popup.css"
          rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/resources/new/css/creative.min.css" rel="stylesheet">

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

        .col-md-6 {
            text-align: left;
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
        <a class="navbar-brand">Copy book</a>
        <div class="collapse navbar-collapse" id="navbarExample">
            <ul class="navbar-nav ml-auto">
                <security:authorize access="hasAuthority('SUPER_ADMIN')">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/superAdmin/users?page=0">users</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/superAdmin/cabinet">Cabinet</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/superAdmin/news?page=0">news</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/superAdmin/catalogue?page=0">catalogue</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/superAdmin/orders?page=0">Orders</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/superAdmin/profile">my profile</a>
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
        <div class="main-login main-center">
            <div class="col-md-5">
                <form:form method="post" modelAttribute="book" action="/superAdmin/saveBook" >
                    <div class="form-inline">
                        <form:input type="text" id="bookName" path="bookName" class="form-control"/>
                        <label for="bookName">Book name</label>
                        <p class="bg-danger"><form:errors path="bookName"/></p>
                    </div>
                    <div class="form-inline">
                        <form:input type="text" id="bookAuthor" path="bookAuthor" class="form-control"/>
                        <label for="bookAuthor">Book Author</label>
                        <p class="bg-danger"><form:errors path="bookAuthor"/></p>
                    </div>
                    <div class="form-inline">
                        <form:input type="text" id="bookQuantity" path="bookQuantity" class="form-control"/>
                        <label for="bookQuantity">Book quantity</label>
                        <p class="bg-danger"><form:errors path="bookQuantity"/></p>
                    </div>
                    <div class="form-inline">
                        <form:input type="text" id="bookPrice" path="bookPrice" class="form-control"/>
                        <label for="bookPrice">Book price</label>
                        <p class="bg-danger"><form:errors path="bookPrice"/></p>
                    </div>
                    <div class="form-inline">
                        <form:input type="text" id="bookDescription" path="bookDescription" name="bookDescription" class="form-control"/>
                        <label for="bookDescription">Book description</label>
                        <p class="bg-danger"><form:errors path="bookDescription"/></p>
                    </div>
                    <div class="form-inline">
                        <button type="submit" class="btn btn-primary btn-block">Add book</button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</header>

</body>
