<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form name='loginForm' action="<c:url value='/users' />" method='POST'>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

    <label for="username"> User: </label>
    <input type='text' name='username' value=''>

    <label for="password"> Password: </label>
    <input type='text' name='password' />

    <label for="role"> Role: </label>
    <input type='text' name='role' />

    <input name="submit" type="submit" value="submit" />
</form>
