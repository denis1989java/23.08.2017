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
    <link href="${pageContext.request.contextPath}/resources/catalogue/vendor/bootstrap/css/bootstrap.min.css"
          rel="stylesheet">

    <!-- Custom fonts for this template -->
    <link href="${pageContext.request.contextPath}/resources/catalogue/vendor/font-awesome/css/font-awesome.min.css"
          rel="stylesheet" type="text/css">
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800'
          rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,400italic,700,700italic,900,900italic'
          rel='stylesheet' type='text/css'>

    <!-- Plugin CSS -->
    <link href="${pageContext.request.contextPath}/resources/catalogue/vendor/magnific-popup/magnific-popup.css"
          rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/resources/catalogue/css/creative.min.css" rel="stylesheet">

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

        .col-md-8 {
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
        <a class="navbar-brand">Add to order</a>
        <div class="collapse navbar-collapse" id="navbarExample">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/user/cabinet">cabinet</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/user/news/0">news</a>
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
            </ul>
        </div>
    </div>
</nav>

<header class="masthead">
    <div class="header-content">
        <div class="row">
            <div class="col-md-8">
                <table>
                    <tr>
                        <th style="color: #FFFFFF">Book name</th>
                        <th style="color: #FFFFFF">Book author</th>
                        <th style="color: #FFFFFF">Price</th>
                        <th style="color: #FFFFFF">Description</th>
                        <th style="color: #FFFFFF">Quantity</th>
                    </tr>
                    <c:forEach var="book" items="${catalogue}">
                        <tr>
                            <td style="color: #FFFFFF"><c:out value="${book.bookName}"/></td>
                            <td style="color: #FFFFFF"><c:out value="${book.bookAuthor}"/></td>
                            <td style="color: #FFFFFF"><c:out value="${book.bookPrice}"/></td>
                            <td style="color: #FFFFFF"><c:out value="${book.bookDescription}"/></td>
                            <td style="color: #FFFFFF">
                                <form method="post" action="/user/order/addToOrder">
                                    <input size="1" name="bookQuantity" style="text-align:center;"
                                           maxlength="2"
                                           pattern="[1-9]?[0-9]" type="text" required>
                                    <input type="hidden" name="orderId" value="${orderId}">
                                    <input type="hidden" name="bookId" value="${book.bookId}">
                                    <input name="addToChangeOrder" value="add to order" type="submit"
                                           style="text-align:left">
                                </form>

                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>

        <div class="row">
            <div class="col-md-3">
                <div class="pagination">
                    <c:forEach var="page" items="${pagination}">
                        <li>
                            <a href="${page}?orderId=${orderId}">${page+1}</a>
                        </li>
                    </c:forEach>
                </div>
            </div>
        </div>

    </div>
</header>


<script src="${pageContext.request.contextPath}/resources/catalogue/vendor/jquery/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/catalogue/vendor/tether/tether.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/catalogue/vendor/bootstrap/js/bootstrap.min.js"></script>

<!-- Plugin JavaScript -->
<script src="${pageContext.request.contextPath}/resources/catalogue/vendor/jquery-easing/jquery.easing.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/catalogue/vendor/scrollreveal/scrollreveal.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/catalogue/vendor/magnific-popup/jquery.magnific-popup.min.js"></script>

<!-- Custom scripts for this template -->
<script src="${pageContext.request.contextPath}/resources/catalogue/js/creative.min.js"></script>

</body>

</html>
