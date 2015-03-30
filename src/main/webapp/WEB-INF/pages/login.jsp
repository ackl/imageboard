<%@taglib prefix="base" tagdir="/WEB-INF/tags/base" %>
<%@taglib prefix="form" tagdir="/WEB-INF/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<base:base>
    <h1 class="login__welcome">Welcome to &lt;imageboard&gt; </h1>
    <div class="login">
    <div class="row">
        <div class="columns small-12">
            <c:if test="${not empty error}">
                <div class="message error">${error}</div>
            </c:if>
            <c:if test="${not empty msg}">
                <div class="message">${msg}</div>
            </c:if>
            <form:login></form:login>
        </div>
    </div>
    </div>
</base:base>
