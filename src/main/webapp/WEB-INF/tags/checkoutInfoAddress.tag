<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="error" required="true" %>

<tr>
    <td>Address</td>
    <td>
        <input name="address" id="address" value="${not empty param.address ? param.address : ""}"><br>
        <c:if test="${not empty error}">
            <p style="color: red">${error}</p>
        </c:if>
    </td>
</tr>