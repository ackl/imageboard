<%@tag description="Header template" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" tagdir="/WEB-INF/tags/form" %>

<header>
    <div class="nav__wrap">
    <nav class="top-bar" data-topbar role="navigation">
        <ul class="title-area">
            <li class="name">
            <h1><a href="/">imageboard</a></h1>
            </li>
            <li class="toggle-topbar menu-icon"><a href="#"><span></span></a></li>
        </ul>

        <section class="top-bar-section">
        <!-- Right Nav Section -->
        <c:if test="${pageContext.request.userPrincipal.name != null}">
        <ul class="right">
            <li class="has-dropdown">
                <a href="#">Account</a>
                <ul class="dropdown">
                    <li><a href="/users/profile/${pageContext.request.userPrincipal.name}">${pageContext.request.userPrincipal.name}</a></li>
                    <li><a href="#"><form:logout></form:logout></a></li>
                </ul>
            </li>
        </ul>
        </c:if>

        <!-- Left Nav Section -->
        <ul class="left">
            <li><a href="#" class="colour-scheme-switch">
                <i class="fa"></i>
                <form:changeColourScheme></form:changeColourScheme>
            </a></li>
            <li class="hide-for-small-only">
                <a href="#" class="anchor-nav-switch">
                    <i class="fa fa-anchor"></i>
                </a>
            </li>
        </ul>
        </section>
    </nav>
    </div>

</header>
