<%@tag description="Header template" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" tagdir="/WEB-INF/tags/form" %>

<header>
    <nav class="top-bar" data-topbar role="navigation">
        <ul class="title-area">
            <li class="name">
            <h1><a href="#">imageboard</a></h1>
            </li>
            <!-- Remove the class "menu-icon" to get rid of menu icon. Take out "Menu" to just have icon alone -->
            <li class="toggle-topbar menu-icon"><a href="#"><span>Menu</span></a></li>
        </ul>

        <section class="top-bar-section">
        <!-- Right Nav Section -->
        <c:if test="${pageContext.request.userPrincipal.name != null}">
        <ul class="right">
            <li class="has-dropdown">
                <a href="#">Account</a>
                <ul class="dropdown">
                    <li><a href="#">${pageContext.request.userPrincipal.name}</a></li>
                    <li><a href="#"><form:logout></form:logout></a></li>
                </ul>
            </li>
        </ul>
        </c:if>

        <!-- Left Nav Section -->
        <ul class="left">
            <li><a href="#">Left Nav Button</a></li>
        </ul>
        </section>
    </nav>
</header>
