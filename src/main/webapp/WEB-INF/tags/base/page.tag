<%@tag description="Base page template" pageEncoding="UTF-8"%>
<%@taglib prefix="base" tagdir="/WEB-INF/tags/base" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<base:base>
    <div class="wrapper">
        <base:header></base:header>
        <jsp:doBody/>
        <div class="push"></div>
    </div>
    <base:footer></base:footer>
</base:base>
