<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="admin" required="true" type="java.lang.Boolean"%>

<form class="form" name='loginForm' action="<c:url value='/users' />" method='POST'>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

    <label for="username"> User: </label>
    <input type='text' name='username' value=''>

    <label for="password"> Password: </label>
    <input type='text' name='password' />

    <label for="role"> Role: </label>
    <c:choose>
    <c:when test="${admin}">
        <select name='role'>
            <option value="user">user</option>
            <option value="moderator">moderator</option>
            <option value="admin">admin</option>
        </select>
    </c:when>
    <c:otherwise>
        <input type="hidden" name="role" value="user">
    </c:otherwise>
    </c:choose>

    <input name="submit" type="submit" value="submit" />
</form>
