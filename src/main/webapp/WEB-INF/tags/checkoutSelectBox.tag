<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="firstAttribute" required="true" %>
<%@ attribute name="secondAttribute" required="true" %>
<%@ attribute name="fieldName" required="true" %>

<tr>
    <td>${fieldName}</td>
    <td>
        <select name="${name}">
            <option value="${firstAttribute}">${firstAttribute}</option>
            <option value="${secondAttribute}">${secondAttribute}</option>
        </select>
    </td>
</tr>