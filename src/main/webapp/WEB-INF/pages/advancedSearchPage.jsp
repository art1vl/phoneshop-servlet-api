<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="advancedSearchPage">
        <p>
            Advanced Search
        </p>
        <c:if test="${not empty exception}">
            <p style="color: red">Incorrect input</p>
        </c:if>
            <form method="post" action="<c:url value="/advancedSearch"/>">
                <div class="checkoutTable">
                    <table>
                       <tags:advancedSearchDescription error="${errorDescription}"/>
                        <tags:advancedSearchMinPrice error="${errorMinPrice}"/>
                        <tags:advancedSearchMaxPrice error="${errorMaxPrice}"/>
                        <tags:advancedSearchMinStock error="${errorMinStock}"/>
                        <tags:advancedSearchMaxStock error="${errorMaxStock}"/>
                    </table>
                </div>
                <br><button>Search</button>
            </form>
    <c:if test="${not empty list}">
        <div class="mainTable">
        <table>
            <thead>
            <tr>
                <td>Image</td>
                <td>Description</td>
                <td class="price">Price</td>
            </tr>
            </thead>
            <c:forEach var="product" items="${list}">
                <tr>
                    <td>
                        <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}" alt="phonePhoto">
                    </td>
                    <td>
                        <a href="<c:url value="/products/${product.id}"/>">${product.description}</a>
                    </td>
                    <td class="price">
                        <a href="<c:url value="/products/priceHistory/${product.id}"/>">
                            <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </table>
        </div>
    </c:if>
    <jsp:include page="/listOfRecentlyViewedProducts"/>
</tags:master>
