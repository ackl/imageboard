<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" tagdir="/WEB-INF/tags/form" %>
<%@taglib prefix="base" tagdir="/WEB-INF/tags/base" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/templates" %>

<base:page>
    <div class="page">
        ${message}
        <form:post></form:post>
        <ul id="posts-list" class="posts"></ul>
    </div>

    <script src='<c:url value="/public/dist/js/posts.js"/>'></script>
    <template:post></template:post>
    <template:postSingle></template:postSingle>
    <template:reply></template:reply>
</base:page>
