<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="error" required="true" %>

<tr>
    <td>Phone</td>
    <td>
        <input name="phone" id="phone" value="${not empty param.phone ? param.phone : ""}"><br>
        <c:if test="${not empty error}">
            <p style="color: red">${error}</p>
        </c:if>
    </td>
</tr>