<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="error" required="true" %>

<tr>
    <td>Min price</td>
    <td>
        <input name="minPrice" id="minPrice" value="${not empty param.minPrice ? param.minPrice : ""}"><br>
        <c:if test="${not empty error}">
            <p style="color: red">${error}</p>
        </c:if>
    </td>
</tr>