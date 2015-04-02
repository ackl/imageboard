<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" tagdir="/WEB-INF/tags/form" %>
<%@taglib prefix="base" tagdir="/WEB-INF/tags/base" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/templates" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authentication var="user" property="principal" />

<base:page>
    <div class="page-meta-info" data-page-name="profile"></div>

    <div class="page profile">
        <h1> ${message} </h1>
        <h2> ${request_username} </h2>

        <div class="row">
            <div class="columns medium-6">
                <div class="profile-image">
                    <img src="${response_user.imageUrl}" alt="">
                </div>
            </div>
            <div class="columns medium-6">
            <c:if test="${user.username eq request_username}">
                <form:profile></form:profile>
            </c:if>
            </div>
        </div>
        <h3>Posts</h3>
        <div class="row">
            <div class="columns medium-6">
                <c:forEach items="${posts}" var="post">
                <c:choose>
                <c:when test="${post.parentId ne '0'}">
                    <a href="/#!threads/${post.parentId}">a reply to</a>
                </c:when>
                <c:otherwise>
                    <a href="/#!threads/${post.id}">a thread</a>
                </c:otherwise>
                </c:choose>
                    <p> ${post.content} </p>
                </c:forEach>
            </div>
        </div>
    </div>
</base:page>
