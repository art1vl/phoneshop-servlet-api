<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="request"/>
<div class="miniCart">
    <a href="<c:url value="/cart"/>">
        <img src="http://cdn.onlinewebfonts.com/svg/img_519120.png">
        <c:if test="${cart.totalQuantity != 0}">
            :${cart.totalQuantity}|${cart.subCost} USD
        </c:if>
    </a>
</div>
