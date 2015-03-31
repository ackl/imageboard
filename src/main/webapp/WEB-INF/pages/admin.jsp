<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="base" tagdir="/WEB-INF/tags/base" %>
<%@taglib prefix="form" tagdir="/WEB-INF/tags/form" %>

<base:base>
    <div class="page-meta-info" data-page-name="admin"></div>

    <h1>Admin page</h1>
    <div class="row">
    <div class="columns medium-6">
        <form:createUser admin="true"></form:createUser>
    </div>
    <div class="columns medium-6">
        <form:createKeycode></form:createKeycode>
    </div>


</div>
</base:base>
