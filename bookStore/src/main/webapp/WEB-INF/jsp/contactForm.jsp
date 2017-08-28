<%@ page language="java"
         contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
           prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>IT Book Store</title>

    <!-- Bootstrap core CSS -->
    <link href="${pageContext.request.contextPath}/resources/homePage/vendor/bootstrap/css/bootstrap.min.css"
          rel="stylesheet">

    <!-- Custom fonts for this template -->
    <link href="${pageContext.request.contextPath}/resources/homePage/vendor/font-awesome/css/font-awesome.min.css"
          rel="stylesheet" type="text/css">
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800'
          rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,400italic,700,700italic,900,900italic'
          rel='stylesheet' type='text/css'>

    <!-- Plugin CSS -->
    <link href="${pageContext.request.contextPath}/resources/homePage/vendor/magnific-popup/magnific-popup.css"
          rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/resources/homePage/css/creative.min.css" rel="stylesheet">

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
        <a class="navbar-brand" href="#">Contact form</a>
        <div class="collapse navbar-collapse" id="navbarExample">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/login">BOOK STORE</a>
                </li>
            </ul>

        </div>
    </div>
</nav>

<header class="masthead">
    <div class="header-content">
        <div class="container">
            <div class="row main" id="login-nav">
                <div class="main-login main-center">
                    <form:form method="post" modelAttribute="message" action="/contact/form">
                        <div class="form-inline">
                            <p class="bg-danger"><form:errors path="authorEmail"/></p>
                            <form:input id="authorEmail" cssClass="form-control" path="authorEmail" type="text"/>
                            <label for="authorEmail">Email</label>
                        </div>
                        <div class="form-inline">
                            <p class="bg-danger"><form:errors path="messageText"/></p>
                            <form:input id="messageText" cssClass="form-control" path="messageText" type="text"/>
                            <label for="messageText">Text</label>
                        </div>
                        <div class="form-group">
                            <button type="submit" class="btn btn-primary btn-block">Send</button>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</header>

<script src="${pageContext.request.contextPath}/resources/homePage/vendor/jquery/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/homePage/vendor/tether/tether.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/homePage/vendor/bootstrap/js/bootstrap.min.js"></script>

<!-- Plugin JavaScript -->
<script src="${pageContext.request.contextPath}/resources/homePage/vendor/jquery-easing/jquery.easing.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/homePage/vendor/scrollreveal/scrollreveal.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/homePage/vendor/magnific-popup/jquery.magnific-popup.min.js"></script>

<!-- Custom scripts for this template -->
<script src="${pageContext.request.contextPath}/resources/homePage/js/creative.min.js"></script>

</body>

</html>
