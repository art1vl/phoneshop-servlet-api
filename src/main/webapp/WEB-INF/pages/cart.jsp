<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="request"/>
<tags:master pageTitle="Cart">
    <c:if test="${empty cart.cartItems}">
        Sorry, your cart is empty. Add some products and return
    </c:if>
    <c:if test="${not empty cart.cartItems}">
    <p>
        Cart
    </p>
    <c:if test="${not empty param.message}">
        <p style="color: green">${param.message}</p>
    </c:if>
    <c:if test="${not empty errors}">
        <p style="color: red">Error via updating cart</p>
    </c:if>
    <div class="mainTable">
        <form method="post" action="<c:url value="/cart"/>">
            <table>
                <thead>
                    <tr>
                        <td>Image</td>
                        <td>Description</td>
                        <td>Quantity</td>
                        <td class="price">Price for one</td>
                        <td class="price">Total price</td>
                        <td>Action</td>
                    </tr>
                </thead>
                <c:forEach var="cartItem" items="${cart.cartItems}" varStatus="status">
                    <tr>
                        <td>
                            <a href="<c:url value="/products/${cartItem.product.id}"/>">
                                <img class="product-tile"
                                    src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master
                                         /${cartItem.product.imageUrl}">
                            </a>
                        </td>
                        <td>
                            <a href="<c:url value="/products/${cartItem.product.id}"/>">
                                ${cartItem.product.description}
                            </a>
                        </td>
                        <td>
                            <input name="quantity"
                                value="${not empty errors[status.index] ? paramValues.quantity[status.index] : cartItem.quantity}"
                                   style="text-align: right">
                            <input type="hidden" name="productId" value="${cartItem.product.id}">
                            <c:if test="${not empty errors[status.index]}">
                                <p style="color: red">${errors[status.index]}</p>
                            </c:if>
                        </td>
                        <td class="price">
                            <a href="<c:url value="/products/priceHistory/${cartItem.product.id}"/>">
                                <fmt:formatNumber value="${cartItem.product.price}"
                                          type="currency" currencySymbol="${cartItem.product.currency.symbol}"/>
                            </a>
                        </td>
                        <td class="price">
                            <fmt:formatNumber value="${cartItem.totalCost}"
                                          type="currency" currencySymbol="${cartItem.product.currency.symbol}"/>
                        </td>
                        <td>
                            <button formaction="<c:url value="/cart/deleteCartItem/${cartItem.product.id}"/>">Delete</button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <br><button>Update</button>
        </form>
    </div>
    <tags:recentlyViewed></tags:recentlyViewed>
    </c:if>
</tags:master>
