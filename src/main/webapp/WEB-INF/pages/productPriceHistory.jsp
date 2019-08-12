<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product price history">
    <p>Price history of ${product.description}</p>
    <p>
        <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
    </p>
    <div class="mainTable">
    <table>
        <thead>
        <tr>
            <td>Date</td>
            <td class="price">Price</td>
        </tr>
        </thead>
        <c:forEach var="dateAndPrice" items="${product.priceHistory}">
            <tr>
                <td>
                    ${dateAndPrice.stringDate}
                </td>
                <td>
                    <fmt:formatNumber value="${dateAndPrice.price}" type="currency" currencySymbol="${dateAndPrice.currency.symbol}"/>
                </td>
            </tr>
        </c:forEach>
    </table>
    </div>
    <tags:recentlyViewed></tags:recentlyViewed>
</tags:master>
