<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form name='loginForm' class="login-form" action="<c:url value='/login.do' />" method='POST'>
    <label for="username"> Username: </label>
    <input type='text' name='username' value=''>
    <label for="password"> Password: </label>
    <input type='password' name='password' />
    <button type="submit" class="button login-form__submit">Login</button>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>
