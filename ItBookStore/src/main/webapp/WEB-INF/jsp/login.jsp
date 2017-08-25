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
        .navbar-toggler {
            z-index: 1;
        }

        @media (max-width: 576px) {
            nav > .container {
                width: 100%;
            }
        }

        #login-dp {
            min-width: 250px;
            padding: 14px 14px 0;
            overflow: hidden;
            background-color: rgba(255, 255, 255, .8);
        }

        #login-dp .bottom {
            background-color: rgba(255, 255, 255, .8);
            border-top: 1px solid #ddd;
            clear: both;
            padding: 14px;
        }

        #login-dp .social-buttons a {
            width: 49%;
        }

        #login-dp .form-group {
            margin-bottom: 10px;
        }

        @media (max-width: 750px) {
            #login-dp {
                background-color: inherit;
                color: #fff;
            }

            #login-dp .bottom {
                background-color: inherit;
                border-top: 0 none;
            }
        }
    </style>

</head>

<body id="page-top">
<nav class="navbar fixed-top navbar-toggleable-md navbar-light" id="mainNav">
    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse"
            data-target="#navbarExample" aria-controls="navbarExample" aria-expanded="false"
            aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="container">
        <a class="navbar-brand" href="#page-top">IT Book Store</a>
        <div class="collapse navbar-collapse" id="navbarExample">
            <ul class="navbar-nav ml-auto">
                <a style="color: #e13612"> <c:out value="${successRegistration}"></c:out></a>
                <a style="color: #e13612"><c:out value="${status}"></c:out></a>
                <security:authorize
                        access="!hasAuthority('USER')&& !hasAuthority('ADMIN') && !hasAuthority('SUPER_ADMIN') ">
                    <li class="nav-item">
                        <a style="color: #e13612" class="nav-link" href="#"><c:out value="${error}"></c:out></a>
                    </li>
                    <li class="nav-item">
                        <a style="color: #e13612" class="nav-link" href="#"><c:out value="${success}"></c:out></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#about">About</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#services">Services</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#portfolio">Portfolio</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#contact">Contact</a>
                    </li>
                    <li class="nav-link">
                    <li class="dropdown" id="dropdown">
                        <a style="color: lightgrey; font-weight: bolder" href="#" class="dropdown-toggle"
                           data-toggle="dropdown" aria-expanded="false">Log in<span class="caret"></span></a>
                        <ul id="login-dp" class="dropdown-menu">
                            <li>
                                <div class="row">
                                    <div class="col-md-12">
                                        <form method='post' action="login"
                                              accept-charset="UTF-8"
                                              id="login-nav">
                                            <c:if test="${param['error']}">
                                                <p>Username or password is not valid</p>
                                            </c:if>
                                            <div class="form-group">
                                                <input id="username" type="email" name="username" required
                                                       class="form-control"/>
                                            </div>
                                            <div class="form-group">
                                                <input type="password" id="password" name="password" required
                                                       class="form-control"/>
                                            </div>
                                            <div class="form-group">
                                                <button type="submit" class="btn btn-primary btn-block">Sign in
                                                </button>
                                            </div>
                                        </form>
                                    </div>
                                    <div class="bottom text-center">
                                        <a href="${pageContext.request.contextPath}/registration"><b>registration</b></a>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </li>
                    </li>
                </security:authorize>
                <security:authorize access="hasAuthority('USER')">
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
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/profile">my
                            profile</a>
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
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/profile">my
                            profile</a>
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
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/superAdmin/cabinet">cabinet</a>
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
        <div class="header-content-inner">
            <security:authorize
                    access="!hasAuthority('USER') && !hasAuthority('ADMIN') && !hasAuthority('SUPER_ADMIN')  ">
                <h1 id="homeHeading">Your Favorite Store of the best books arroun the world</h1>
                <hr>
                <p>Log In and Start ordering the best books from around the world! Just register for the first time
                    in our Online IT BookStore</p>
                <a class="btn btn-primary btn-xl" href="#about">Find Out More</a>
            </security:authorize>
            <security:authorize access="hasAuthority('SUPER_ADMIN')">
                <h1 id="homeHeading">Welcome, Super Admin</h1>
            </security:authorize>
            <security:authorize access="hasAuthority('SUPER_ADMIN')">
                <h1 id="homeHeading">Welcome, Admin</h1>
            </security:authorize>
            <security:authorize access="hasAuthority('USER')">
                <h1 id="homeHeading">Welcome</h1>
                <h1 id="homeHeading">Watch catalogue and order books around the world!</h1>
            </security:authorize>
        </div>
    </div>
</header>

<security:authorize access="!hasAuthority('USER') && !hasAuthority('ADMIN') && !hasAuthority('SUPER_ADMIN') ">
    <section class="bg-primary" id="about">
        <div class="container">
            <div class="row">
                <div class="col-lg-8 offset-lg-2 text-center">
                    <h2 class="section-heading text-white">We've got what you need!</h2>
                    <hr class="light">
                    <p class="text-faded">Start buying in our Online Store and you'll get all the books on time straight
                        home. All books are in our warehouse, and the rarest are delivered from around the World for
                        several
                        days. You only need to do a few keystrokes and you will soon be immersed in the world of
                        reading!</p>
                    <a class="btn btn-default btn-xl sr-button" href="#services">Get Started!</a>
                </div>
            </div>
        </div>
    </section>

    <section id="services">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2 class="section-heading">At Your Service</h2>
                    <hr class="primary">
                </div>
            </div>
        </div>
        <div class="container">
            <div class="row">
                <div class="col-lg-3 col-md-6 text-center">
                    <div class="service-box">
                        <i class="fa fa-4x fa-diamond text-primary sr-icons"></i>
                        <hr class="primary">
                        <h3>Reliable partners</h3>
                        <p class="text-muted">Our partners always do their work perfectly</p>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6 text-center">
                    <div class="service-box">
                        <i class="fa fa-4x fa-paper-plane text-primary sr-icons"></i>
                        <hr class="primary">
                        <h3>Ready to Ship</h3>
                        <p class="text-muted">Each book in the catalog is ready and waiting for sending</p>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6 text-center">
                    <div class="service-box">
                        <i class="fa fa-4x fa-newspaper-o text-primary sr-icons"></i>
                        <hr class="primary">
                        <h3>Up to Date</h3>
                        <p class="text-muted">We use only modern and fastest delivery methods</p>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6 text-center">
                    <div class="service-box">
                        <i class="fa fa-4x fa-heart text-primary sr-icons"></i>
                        <hr class="primary">
                        <h3>Made with Love</h3>
                        <p class="text-muted">You have to read your book with love these days</p>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <section class="no-padding" id="portfolio">
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2 class="section-heading">Portfolio</h2>
                    <hr class="primary">
                </div>
            </div>
            <div class="row no-gutter popup-gallery">
                <div class="col-lg-4 col-sm-6">
                    <a class="portfolio-box"
                       href="${pageContext.request.contextPath}/resources/homePage/img/portfolio/fullsize/1.jpg">
                        <img class="img-fluid"
                             src="${pageContext.request.contextPath}/resources/homePage/img/portfolio/thumbnails/1.jpg"
                             alt="">
                        <div class="portfolio-box-caption">
                            <div class="portfolio-box-caption-content">
                                <div class="project-category text-faded">
                                    IT Book Store
                                </div>
                            </div>
                        </div>
                    </a>
                </div>
                <div class="col-lg-4 col-sm-6">
                    <a class="portfolio-box"
                       href="${pageContext.request.contextPath}/resources/homePage/img/portfolio/fullsize/2.jpg">
                        <img class="img-fluid"
                             src="${pageContext.request.contextPath}/resources/homePage/img/portfolio/thumbnails/2.jpg"
                             alt="">
                        <div class="portfolio-box-caption">
                            <div class="portfolio-box-caption-content">
                                <div class="project-category text-faded">
                                    IT Book Store
                                </div>
                            </div>
                        </div>
                    </a>
                </div>
                <div class="col-lg-4 col-sm-6">
                    <a class="portfolio-box"
                       href="${pageContext.request.contextPath}/resources/homePage/img/portfolio/fullsize/3.jpg">
                        <img class="img-fluid"
                             src="${pageContext.request.contextPath}/resources/homePage/img/portfolio/thumbnails/3.jpg"
                             alt="">
                        <div class="portfolio-box-caption">
                            <div class="portfolio-box-caption-content">
                                <div class="project-category text-faded">
                                    IT Book Store
                                </div>
                            </div>
                        </div>
                    </a>
                </div>
                <div class="col-lg-4 col-sm-6">
                    <a class="portfolio-box"
                       href="${pageContext.request.contextPath}/resources/homePage/img/portfolio/fullsize/4.jpg">
                        <img class="img-fluid"
                             src="${pageContext.request.contextPath}/resources/homePage/img/portfolio/thumbnails/4.jpg"
                             alt="">
                        <div class="portfolio-box-caption">
                            <div class="portfolio-box-caption-content">
                                <div class="project-category text-faded">
                                    IT Book Store
                                </div>
                            </div>
                        </div>
                    </a>
                </div>
                <div class="col-lg-4 col-sm-6">
                    <a class="portfolio-box"
                       href="${pageContext.request.contextPath}/resources/homePage/img/portfolio/fullsize/5.jpg">
                        <img class="img-fluid"
                             src="${pageContext.request.contextPath}/resources/homePage/img/portfolio/thumbnails/5.jpg"
                             alt="">
                        <div class="portfolio-box-caption">
                            <div class="portfolio-box-caption-content">
                                <div class="project-category text-faded">
                                    IT Book Store
                                </div>
                            </div>
                        </div>
                    </a>
                </div>
                <div class="col-lg-4 col-sm-6">
                    <a class="portfolio-box"
                       href="${pageContext.request.contextPath}/resources/homePage/img/portfolio/fullsize/6.jpg">
                        <img class="img-fluid"
                             src="${pageContext.request.contextPath}/resources/homePage/img/portfolio/thumbnails/6.jpg"
                             alt="">
                        <div class="portfolio-box-caption">
                            <div class="portfolio-box-caption-content">
                                <div class="project-category text-faded">
                                    IT Book Store
                                </div>
                            </div>
                        </div>
                    </a>
                </div>
            </div>
        </div>
    </section>

    <div class="call-to-action bg-dark">
        <div class="container text-center">
            <h2>Download our catalogue</h2>
            <a class="btn btn-default btn-xl sr-button" href="http://startbootstrap.com/template-overviews/creative/">Download
                Now!</a>
        </div>
    </div>

    <section id="contact">
        <div class="container">
            <div class="row">
                <div class="col-lg-8 offset-lg-2 text-center">
                    <h2 class="section-heading">Let's Get In Touch!</h2>
                    <hr class="primary">
                    <p>Give us a call or send us an email if you have any problems with registration or ordering! We
                        will help you so fast, so it possible! Thanks for choosing us!</p>
                </div>
                <div class="col-lg-4 offset-lg-2 text-center">
                    <i class="fa fa-phone fa-3x sr-contact"></i>
                    <p>+375296522540</p>
                </div>
                <div class="col-lg-4 text-center">
                    <i class="fa fa-envelope-o fa-3x sr-contact"></i>
                    <p><a href="mailto:your-email@your-domain.com">denis1989@bk.ru</a></p>
                </div>
            </div>
        </div>
    </section>
</security:authorize>

<!-- Navigation -->


<!-- Bootstrap core JavaScript -->
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
