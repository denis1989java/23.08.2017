<%@ page language="java"
         contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
           prefix="c" %>
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
        <a class="navbar-brand">Orders</a>
        <div class="collapse navbar-collapse" id="navbarExample">
            <ul class="navbar-nav ml-auto">
                <security:authorize access="hasAuthority('ADMIN')">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/cabinet">Cabinet</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/news?page=0">news</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/admin/catalogue?page=0">catalogue</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/profile">my profile</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/logout">logout</a>
                    </li>
                </security:authorize>
                <security:authorize access="hasAuthority('SUPER_ADMIN')">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/superAdmin/cabinet">Cabinet</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/superAdmin/users?page=0">users</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/superAdmin/news?page=0">news</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/superAdmin/catalogue?page=0">catalogue</a>
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
        <div class="row">
            <div class="col-md-10" style="text-align: center">
                <c:choose>
                    <c:when test="${empty orders}">
                        <c:out value="no orders"></c:out>
                    </c:when>
                    <c:otherwise>
                        <table>
                            <tr>
                                <th style="color: #FFFFFF">Order number</th>
                                <th style="color: #FFFFFF">Delivery status</th>
                                <th style="color: #FFFFFF">Receive status</th>
                                <th style="color: #FFFFFF">Order price</th>
                                <th style="color: #FFFFFF">Order date</th>
                                <th style="color: #FFFFFF">Change delivery status</th>
                                <th style="color: #FFFFFF">Show order</th>
                            </tr>
                            <c:forEach var="order" items="${orders}">
                                <tr>
                                    <td style="color: #FFFFFF"><c:out value="${order.orderId}"/></td>
                                    <td style="color: #FFFFFF"><c:out value="${order.orderDelivery}"/></td>
                                    <td style="color: #FFFFFF"><c:out value="${order.orderReceive}"/></td>
                                    <td style="color: #FFFFFF"><c:out value="${order.orderPrice}"/></td>
                                    <td style="color: #FFFFFF"><c:out value="${order.orderDate}"/></td>
                                    <td>
                                        <form>
                                        </form>
                                        <security:authorize access="hasAuthority('ADMIN')">
                                            <form method="post" action="/admin/changeDeliveryStatus" >
                                                <select name="deliveryStatus">
                                                    <option value="NEW">NEW</option>
                                                    <option value="REWIWING">REWIWING</option>
                                                    <option value="IN_PROGRESS">IN_PROGRESS</option>
                                                    <option value="DELIVERED">DELIVERED</option>
                                                </select>
                                                <input type="hidden" name="page" value="${page}">
                                                <input type="hidden" name="orderId" value="${order.orderId}">
                                                <input name="changeStatus" value="change" type="submit"
                                                       style="text-align:left">
                                            </form>
                                        </security:authorize>
                                        <security:authorize access="hasAuthority('SUPER_ADMIN')">
                                            <form method="post" action="/superAdmin/changeDeliveryStatus" >
                                                <select name="deliveryStatus">
                                                    <option value="NEW">NEW</option>
                                                    <option value="REWIWING">REWIWING</option>
                                                    <option value="IN_PROGRESS">IN_PROGRESS</option>
                                                    <option value="DELIVERED">DELIVERED</option>
                                                </select>
                                                <input type="hidden" name="orderId" value="${order.orderId}">
                                                <input type="hidden" name="page" value="${page}">
                                                <input name="changeStatus" value="change" type="submit"
                                                       style="text-align:left">
                                            </form>
                                        </security:authorize>

                                    </td>
                                    <td>
                                        <security:authorize access="hasAuthority('ADMIN')">
                                            <form action="/admin/orders/${page}" method="get" >
                                                <input type="hidden" name="orderId" value="${order.orderId}">
                                                <input name="showDetails" value="show details" type="submit"
                                                       style="text-align:left">
                                            </form>
                                        </security:authorize>
                                        <security:authorize access="hasAuthority('SUPER_ADMIN')">
                                            <form action="/superAdmin/orders/${page}" method="get" >
                                                <input type="hidden" name="orderId" value="${order.orderId}">
                                                <input name="showDetails" value="show details" type="submit"
                                                       style="text-align:left">
                                            </form>
                                        </security:authorize>

                                    </td>
                                    <c:choose>
                                        <c:when test="${!empty ordersBooksDTO && order.orderId eq orderID}">
                                            <td style="color: #FFFFFF">
                                                <table>
                                                    <tr>
                                                        <th style="color: black">Book name</th>
                                                        <th style="color: black">Book price</th>
                                                        <th style="color: black">Quantity</th>
                                                    </tr>
                                                    <c:forEach var="order" items="${ordersBooksDTO}">
                                                        <tr>
                                                            <td style="color: black"><c:out
                                                                    value="${order.bookName}"></c:out></td>
                                                            <td style="color: black"><c:out
                                                                    value="${order.bookPrice}"></c:out></td>
                                                            <td style="color: black"><c:out
                                                                    value="${order.bookQuantity}"></c:out></td>
                                                        </tr>
                                                    </c:forEach>
                                                </table>
                                            </td>
                                        </c:when>
                                        <c:otherwise>
                                        </c:otherwise>
                                    </c:choose>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:otherwise>
                </c:choose>
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