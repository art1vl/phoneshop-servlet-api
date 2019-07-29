<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product">
    <p>Price history of ${product.description}</p>
    <p>
        <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
    </p>
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
                    ${dateAndPrice.date}
                </td>
                <td>
                    <fmt:formatNumber value="${dateAndPrice.price}" type="currency" currencySymbol="${dateAndPrice.currency.symbol}"/>
                </td>
            </tr>
        </c:forEach>
    </table>
</tags:master>