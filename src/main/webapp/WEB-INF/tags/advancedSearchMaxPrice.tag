<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="error" required="true" %>

<tr>
    <td>Max price</td>
    <td>
        <input name="maxPrice" id="maxPrice" value="${not empty param.maxPrice ? param.maxPrice : ""}"><br>
        <c:if test="${not empty error}">
            <p style="color: red">${error}</p>
        </c:if>
    </td>
</tr>