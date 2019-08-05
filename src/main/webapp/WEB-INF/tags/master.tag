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
    <c:if test="${empty flagDoNotShowBasket}">
    <div class="basket">
      <a href="<c:url value="/cart"/>">
        <img src="http://cdn.onlinewebfonts.com/svg/img_519120.png">
          <c:if test="${totalQuantity != 0}">
            :${totalQuantity}|${totalCost} USD
          </c:if>
      </a>
    </div>
    </c:if>
  </header>
  <main>
    <jsp:doBody/>
  </main>
</body>
</html>