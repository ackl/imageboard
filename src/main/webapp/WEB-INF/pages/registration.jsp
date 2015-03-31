<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" tagdir="/WEB-INF/tags/form" %>
<%@taglib prefix="base" tagdir="/WEB-INF/tags/base" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/templates" %>

<base:page>
    <div class="page row">
        <h1>Registration page</h1>
        <p>${message}</p>
        <c:if test="${valid}">
            <form:createUser admin="false"></form:createUser>
        </c:if>
    </div>
</base:page>
