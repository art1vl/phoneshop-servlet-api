<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.List" scope="request"/>
<tags:master pageTitle="Product List">
  <p>
    Welcome to Expert-Soft training!
  </p>
  <form>
      <input name="query" value="${param.query}">
      <button>Search</button>
  </form>
    <div class="mainTable">
        <table>
            <thead>
                <tr>
                    <td>Image</td>
                    <td>Description
                        <tags:sort query="${param.query}" sort="description" order="asc" url="https://image.flaticon.com/icons/png/512/44/44674.png"></tags:sort>
                        <tags:sort query="${param.query}" sort="description" order="desc" url="https://cdn.pixabay.com/photo/2012/04/28/18/55/arrow-44008_960_720.png"></tags:sort>
                    </td>
                    <td class="price">Price
                        <tags:sort query="${param.query}" sort="price" order="asc" url="https://image.flaticon.com/icons/png/512/44/44674.png"></tags:sort>
                        <tags:sort query="${param.query}" sort="price" order="desc" url="https://cdn.pixabay.com/photo/2012/04/28/18/55/arrow-44008_960_720.png"></tags:sort>
                    </td>
                </tr>
            </thead>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>
                    <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
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
    <jsp:include page="/listOfRecentlyViewedProducts"/>
</tags:master>
