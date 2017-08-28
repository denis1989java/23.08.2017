<%@ page language="java"
         contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
           prefix="c" %>

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
        <a class="navbar-brand">Change Order</a>
        <div class="collapse navbar-collapse" id="navbarExample">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/user/news/0">news</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/user/cabinet">cabinet</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/user/catalogue/0">catalogue</a>
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
            <div class="col-md-6" style="text-align: center">
                <table>
                    <tr>
                        <th style="color: #FFFFFF">Order №</th>
                        <th style="color: #FFFFFF">Order price</th>
                        <th style="color: #FFFFFF">Order date</th>
                    </tr>
                    <tr>
                        <td style="color: #FFFFFF"><c:out value="${viewDTO.viewMap['orderDTO'].orderId}"/></td>
                        <td style="color: #FFFFFF"><c:out value="${viewDTO.viewMap['orderDTO'].orderPrice}"/></td>
                        <td style="color: #FFFFFF"><c:out value="${viewDTO.viewMap['orderDTO'].orderDate}"/></td>
                    </tr>

                </table>
                <c:choose>
                    <c:when test="${empty viewDTO.viewMap['OrderBookTimesDTO']}">
                        <c:out value="No items in you order"></c:out>
                        <form action="/user/order/delete" method="post">
                            <input type="hidden" name="orderId" value="${viewDTO.viewMap['orderDTO'].orderId}">
                            <input name="deleteOrder" value="delete order" type="submit" style="text-align:left">
                        </form>
                        <form action="/user/order/addToOrder/0" method="get">
                            <input type="hidden" name="orderId" value="${viewDTO.viewMap['orderDTO'].orderId}">
                            <input value="Add to order" type="submit" style="text-align:left">
                        </form>
                    </c:when>
                    <c:otherwise>
                        <c:out value="Order Details"></c:out>
                        <form action="/user/order/deleteBook?orderId=${viewDTO.viewMap['orderDTO'].orderId}" method="post">
                            <table>
                                <tr>
                                    <th style="color: #FFFFFF">Book name</th>
                                    <th style="color: #FFFFFF">Book price</th>
                                    <th style="color: #FFFFFF">Book quantity</th>
                                    <th style="color: #FFFFFF">Change quantity</th>
                                    <th style="color: #FFFFFF"><input value="delete" name="delete" type="submit"
                                                                      style="text-align:left"></th>
                                </tr>
                                <c:forEach var="ordersBooksDTO" items="${viewDTO.viewMap['OrderBookTimesDTO']}" varStatus="status">
                                    <tr>
                                        <td style="color: #FFFFFF"><c:out value="${ordersBooksDTO.bookName}"/></td>
                                        <td style="color: #FFFFFF"><c:out
                                                value="${ordersBooksDTO.bookPrice}"/></td>
                                        <td style="color: #FFFFFF"><c:out
                                                value="${ordersBooksDTO.bookQuantity}"/></td>
                                        <td>
                                            <form>
                                            </form>
                                            <form method="post" action="/user/order/changeBookQuantity">
                                                <input size="1" name="bookQuantity" style="text-align:center;" maxlength="2"
                                                       pattern="[1-9][0-9]||[1-9]" type="text">
                                                <input type="hidden" name="orderId" value="${viewDTO.viewMap['orderDTO'].orderId}">
                                                <input type="hidden" name="ordersBooksTimesId"
                                                       value="${ordersBooksDTO.ordersBooksTimesId}">
                                                <input name="change" value="change" type="submit"
                                                       style="text-align:left">
                                            </form>
                                        </td>
                                        <td>
                                            <input style="text-align:left; width: 3vw;height: 3vh;border-radius: 10px"
                                                   maxlength="2" name="deleting"
                                                   value="${ordersBooksDTO.ordersBooksTimesId}" type="checkbox"></td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </form>
                        <div class="col-md-6" style="text-align: center;float: right">
                            <table style="text-align: center">
                                <tr>
                                    <th style="color: #FFFFFF"><p>Summ: <c:out value="${viewDTO.viewMap['summ']}"></c:out></p></th>
                                    <th>
                                        <form action="/user/order/save" method="post">
                                            <input type="hidden" name="orderId" value="${viewDTO.viewMap['orderDTO'].orderId}">
                                            <input value="${viewDTO.viewMap['summ']}" name="fullPrice" type="hidden">
                                            <input name="save" value="save" type="submit" style="text-align:left">
                                        </form>
                                    </th>
                                </tr>
                            </table>
                            <form action="/user/order/addToOrder/0" method="get">
                                <input type="hidden" name="orderId" value="${viewDTO.viewMap['orderDTO'].orderId}">
                                <input value="Add to order" type="submit" style="text-align:left">
                            </form>
                            <form action="/user/order/delete" method="post">
                                <input type="hidden" name="orderId" value="${viewDTO.viewMap['orderDTO'].orderId}">
                                <input name="deleteOrder" value="delete order" type="submit" style="text-align:left">
                            </form>
                        </div>
                    </c:otherwise>
                </c:choose>

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
