<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" tagdir="/WEB-INF/tags/form" %>
<%@taglib prefix="base" tagdir="/WEB-INF/tags/base" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/templates" %>

<base:page>
    <div class="page-meta-info" data-page-name="profile"></div>

    <div class="page">
        <h1> ${message} </h1>
        <form:profile></form:profile>
    </div>
</base:page>
