<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="posts" tagdir="/WEB-INF/tags/posts" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/templates" %>

<template:base>
<div class="page">
    ${message}


    <posts:form></posts:form>
    <ul class="posts"></ul>

    <script src='<c:url value="/public/dist/js/posts.js"/>'></script>
</div>
</template:base>
