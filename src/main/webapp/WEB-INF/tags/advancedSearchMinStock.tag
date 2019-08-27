<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="error" required="true" %>

<tr>
    <td>Min stock</td>
    <td>
        <input name="minStock" id="minStock" value="${not empty param.minStock ? param.minStock : ""}"><br>
        <c:if test="${not empty error}">
            <p style="color: red">${error}</p>
        </c:if>
    </td>
</tr>