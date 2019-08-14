<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.order.Order" scope="request"/>
<tags:master pageTitle="Checkout">
    <c:if test="${empty order.cartItems}">
        Sorry, your cart is empty. Add some products and return
    </c:if>
    <c:if test="${not empty order.cartItems}">
        <p>
            Checkout
        </p>
        <c:if test="${not empty outOfStockException}">
            <p style="color: red">${outOfStockException}</p>
        </c:if>
        <div class="mainTable">
            <form method="post" action="<c:url value="/checkout"/>">
                <table>
                    <thead>
                    <tr>
                        <td>Image</td>
                        <td>Description</td>
                        <td>Quantity</td>
                        <td class="price">Price for one</td>
                        <td class="price">Total price</td>
                    </tr>
                    </thead>
                    <c:forEach var="cartItem" items="${order.cartItems}" varStatus="status">
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
                            <td style="text-align: right">
                                ${cartItem.quantity}
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
                        </tr>
                    </c:forEach>
                    <tr style="background-color: bisque">
                        <td colspan="2" style="text-align: left">Subtotal:</td>
                        <td style="text-align: right">${order.totalQuantity}</td>
                        <td colspan="2" class="price">
                            <fmt:formatNumber value="${order.subCost}"
                                              type="currency" currencySymbol="${order.cartItems[0].product.currency.symbol}"/>
                        </td>
                    </tr>
                    <tr style="background-color: bisque">
                        <td colspan="2" style="text-align: left">Delivery cost:</td>
                        <td colspan="3" class="price">
                            <fmt:formatNumber value="${order.deliveryCost}"
                                              type="currency" currencySymbol="${order.cartItems[0].product.currency.symbol}"/>
                        </td>
                    </tr>
                    <tr style="background-color: bisque">
                        <td colspan="2" style="text-align: left">Total:</td>
                        <td style="text-align: right">${order.totalQuantity}</td>
                        <td colspan="2" class="price">
                            <fmt:formatNumber value="${order.orderCost}"
                                              type="currency" currencySymbol="${order.cartItems[0].product.currency.symbol}"/>
                        </td>
                    </tr>
                </table>
                <div class="checkoutTable">
                    <table>
                        <tags:checkoutInfoName error="${errorName}"/>
                        <tags:checkoutInfoSurname error="${errorSurname}"/>
                        <tags:checkoutInfoPhone error="${errorPhone}"/>
                        <tags:checkoutInfoAddress error="${errorAddress}"/>
                        <tags:checkoutSelectBox name="deliveryMode" firstAttribute="courier" secondAttribute="pickup in store"
                                            fieldName="Delivery mode"/>
                        <tags:checkoutSelectBox name="paymentMethod" firstAttribute="cash" secondAttribute="credit card"
                                                fieldName="Payment method"/>
                        <tr>
                            <td>Date</td>
                            <td>
                                <input name="date" value="<fmt:formatDate type = "date" value = "${date}"/>" readonly><br>
                            </td>
                        </tr>
                        <tr>
                            <td>Delivery cost</td>
                            <td>
                                <input name="deliveryCost" value="<fmt:formatNumber value="${order.deliveryCost}"
                                              type="currency" currencySymbol="${order.cartItems[0].product.currency.symbol}"/>"
                                       readonly><br>
                            </td>
                        </tr>
                    </table>
                </div>
                <br><button>Place order</button>
            </form>
        </div>
    </c:if>
</tags:master>
