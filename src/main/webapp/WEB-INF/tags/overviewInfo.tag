<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="fieldName" required="true" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="value" required="true" %>

<tr>
    <td>${fieldName}</td>
    <td>
        <input name="${name}" id="${name}" value="${value}" readonly><br>
    </td>
</tr>