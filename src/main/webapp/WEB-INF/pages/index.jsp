<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="posts" tagdir="/WEB-INF/tags/posts" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/templates" %>

<template:base>
<script type="text/mustache" id="postTemplate">
    {{#posts}}
        <li>
            {{content}}
            <p class="post__timestamp">Posted at: {{formatDate date}}</p>
            <div class="post__info">
                <p class="post__info--id">postID: {{id}}</p>
                <p class="post__info--parent-id">parentId: {{parentId}}</p>
            </div>

            {{#thread}}
                <button class="thread__reply">Reply to thread</button>
            {{/thread}}
        </li>
    {{/posts}}
</script>

<div class="page">
    ${message}


    <posts:form></posts:form>
    <ul id="posts-list" class="posts"></ul>

    <script src='<c:url value="/public/dist/js/posts.js"/>'></script>
</div>
</template:base>
