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

        .col-md-3 {
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
        <a class="navbar-brand">Users</a>
        <div class="collapse navbar-collapse" id="navbarExample">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/superAdmin/cabinet">Cabinet</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/superAdmin/news/=0">news</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link"
                       href="${pageContext.request.contextPath}/superAdmin/catalogue/0">catalogue</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/superAdmin/profile">my profile</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/superAdmin/allOrders/0">Orders</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/logout">logout</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<header class="masthead">
    <div class="header-content">
        <div class="row">
            <div class="col-md-10" style="text-align: center">
                <form action="/superAdmin/deleteUser" method="get">
                    <table>
                        <tr>
                            <th style="color: #FFFFFF">Id</th>
                            <th style="color: #FFFFFF">Login</th>
                            <th style="color: #FFFFFF">Role</th>
                            <th style="color: #FFFFFF">Status</th>
                            <th style="color: #FFFFFF"><input value="${userDTO.userId}" name="deleting" type="submit"
                                                              style="text-align:left"></th>
                            <th style="color: #FFFFFF">Change role</th>
                            <th style="color: #FFFFFF">Change status</th>

                        </tr>
                        <c:forEach var="userDTO" items="${users}">
                            <tr>
                                <td style="color: #FFFFFF"><c:out value="${userDTO.userId}"/></td>
                                <td style="color: #FFFFFF"><c:out value="${userDTO.userEmail}"/></td>
                                <td style="color: #FFFFFF"><c:out value="${userDTO.userRole}"/></td>
                                <td style="color: #FFFFFF"><c:out value="${userDTO.userStatus}"/></td>
                                <td><input style="text-align:left; width: 3vw;height: 3vh;border-radius: 10px"
                                           maxlength="2" name="deleting" value="${userDTO.userId}"
                                           type="checkbox"/>
                                </td>
                                <td>
                                    <form method="get" action="/superAdmin/changeRole">
                                        <select name="userRole">
                                            <option value="USER">USER</option>
                                            <option value="ADMIN">ADMIN</option>
                                            <option value="SUPER_ADMIN">SUPER_ADMIN</option>
                                            <input type="hidden" name="userId" value="${userDTO.userId}"/>
                                            <input name="changeRole" value="change" type="submit"
                                                   style="text-align:left"/>
                                        </select>
                                    </form>
                                </td>
                                <td>
                                    <form method="get" action="/superAdmin/changeStatus">
                                        <select name="userStatus">
                                            <option value="ACTIVE">ACTIVE</option>
                                            <option value="BLOCKED">BLOCKED</option>
                                            <input type="hidden" name="userId" value="${userDTO.userId}"/>
                                            <input name="changeStatus" value="change" type="submit"
                                                   style="text-align:left"/>
                                        </select>
                                    </form>
                                </td>

                            </tr>
                        </c:forEach>
                    </table>
                </form>
            </div>
        </div>
        <div class="row">
            <div class="col-md-5">
                <div class="pagination">
                    <c:forEach var="page" items="${pagination}">
                        <li><a href="${page}">${page+1}</a></li>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</header>


<script src="${pageContext.request.contextPath}/resources/basket/vendor/jquery/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/basket/vendor/tether/tether.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/basket/vendor/bootstrap/js/bootstrap.min.js"></script>

<!-- Plugin JavaScript -->
<script src="${pageContext.request.contextPath}/resources/basket/vendor/jquery-easing/jquery.easing.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/basket/vendor/scrollreveal/scrollreveal.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/basket/vendor/magnific-popup/jquery.magnific-popup.min.js"></script>

<!-- Custom scripts for this template -->
<script src="${pageContext.request.contextPath}/resources/basket/js/creative.min.js"></script>

</body>

</html>
