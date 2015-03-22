<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" tagdir="/WEB-INF/tags/form" %>
<%@taglib prefix="base" tagdir="/WEB-INF/tags/base" %>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/templates" %>

<base:page>
    <div class="page">
        ${message}
    </div>

    <script src='<c:url value="/public/dist/js/bundle.js"/>'></script>
    <template:threadList></template:threadList>
    <template:thread></template:thread>
    <template:reply></template:reply>
    <template:paginate></template:paginate>
</base:page>
