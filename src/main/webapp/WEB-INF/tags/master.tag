<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="pageTitle" required="true" %>

<html>
<head>
  <title>${pageTitle}</title>
  <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
  <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
</head>
<body class="product-list">
  <header>
    <a href="${pageContext.servletContext.contextPath}">
      <img src="${pageContext.servletContext.contextPath}/images/logo.svg"/>
      PhoneShop
    </a>
    <div class="basket">
    <img src="http://cdn.onlinewebfonts.com/svg/img_519120.png">:</a>
    ${cart}
    </div>
  </header>
  <main>
    <jsp:doBody/>
  </main>
  <footer>
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
  </footer>
</body>
</html>