<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <form name='loginForm' action="<c:url value='/login.do' />" method='POST'>
        <label for="username"> User: </label>
        <input type='text' name='username' value=''>
        <label for="password"> Password: </label>
        <input type='password' name='password' />
        <input name="submit" type="submit" value="submit" />
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    </form>
