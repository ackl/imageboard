<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form class="form" name='loginForm' action="<c:url value='/users/keycode' />" method='POST'>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <input name="submit" type="submit" value="submit" />
</form>
