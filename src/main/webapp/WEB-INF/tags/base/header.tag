<%@tag description="Header template" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" tagdir="/WEB-INF/tags/form" %>

<header>
    <h1>imageboard</h1>

	<c:if test="${pageContext.request.userPrincipal.name != null}">
        <h2>Welcome : ${pageContext.request.userPrincipal.name}</h1>
        <form:logout></form:logout>
	</c:if>
</header>
