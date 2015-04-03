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
            <li class="has-dropdown settings-switch">
                <a href="#" class="">
                    <i class="fa-wrench fa"></i>
                </a>
                <ul class="dropdown">
                <li class="has-dropdown">
                    <a class="item">
                        <i class="fa fa-comments-o"></i>
                        Posts per page
                    </a>
                    <ul class="dropdown level-two posts-per-page">
                        <li> <a class="item"> 2 </a> </li>
                        <li> <a class="item"> 3 </a> </li>
                        <li> <a class="item"> 4 </a> </li>
                        <li> <a class="item"> 5 </a> </li>
                    </ul>
                </li>
                <li class="has-dropdown">
                    <a class="item">
                        <i class="fa fa-reply-all"></i>
                        Reply preview limit
                    </a>
                    <ul class="dropdown level-two replies-per-thread">
                        <li> <a class="item"> 2 </a> </li>
                        <li> <a class="item"> 3 </a> </li>
                        <li> <a class="item"> 4 </a> </li>
                        <li> <a class="item"> 5 </a> </li>
                    </ul>
                </li>
                <li class="has-dropdown">
                    <a class="item">
                        <i class="fa fa-sort-amount-asc"></i>
                        Sort by
                    </a>
                    <ul class="dropdown level-two sort-threads-by">
                        <li> <a class="item" data-param="lastactive"> Last active </a> </li>
                        <li> <a class="item" data-param="popularity"> Popularity </a> </li>
                    </ul>
                </li>
                </ul>

            </li>
        </ul>
        </section>
    </nav>
    </div>

</header>
