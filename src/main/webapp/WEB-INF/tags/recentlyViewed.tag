<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>

<c:if test="${not empty recentlyViewed}">
    <div class="recentlyViewedTable">
        <h3>
            <strong>Recently viewed:</strong>
        </h3>
        <table>
            <tr>
                <c:forEach var="product" items="${recentlyViewed}">
                    <td align="center">
                        <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}"><br>
                        <a href="<c:url value="/products/${product.id}"/>">${product.description}</a><br>
                            ${product.price}$
                    </td>
                </c:forEach>
            </tr>
        </table>
    </div>
</c:if>