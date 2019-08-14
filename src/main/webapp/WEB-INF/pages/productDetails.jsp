<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product Details">
    <p>
        Product details
    </p>
    <c:if test="${not empty param.message}">
        <p class="added">${param.message}</p>
    </c:if>
    <c:if test="${not empty error}">
        <p class="error">Error</p>
    </c:if>
    <div>
        <p>
            <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
        </p>
        code: ${product.code} <br>
        description: ${product.description} <br>
        price: ${product.price}$ <br>
        <form method="post" action="<c:url value="/products/${product.id}"/>">
            <input name="quantity" class="price" value="${empty param.quantity ? 1 : param.quantity}">
            <c:if test="${not empty error}">
                <p class="error">${error}</p>
            </c:if>
            <button>Add to card</button>
        </form>
    </div>
    <jsp:include page="/listOfRecentlyViewedProducts"/>
</tags:master>
