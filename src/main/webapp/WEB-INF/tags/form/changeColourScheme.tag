<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form class="form hide" name='changeColourSchemeForm' action="<c:url value='/users/colour' />" method='POST'>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>
