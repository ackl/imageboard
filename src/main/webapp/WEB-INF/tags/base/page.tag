<%@tag description="Base page template" pageEncoding="UTF-8"%>
<%@taglib prefix="base" tagdir="/WEB-INF/tags/base" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta name="viewport" content="initial-scale = 1.0,maximum-scale = 1.0" />
        <meta charset="UTF-8">
        <title></title>
        <!--<script src="http://code.jquery.com/jquery-1.11.2.js"></script>-->
        <!--<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.js"></script>-->
        <!--<script src='<c:url value="/public/dist/js/can.jquery.js"/>'></script>-->
        <!--<script src='<c:url value="/public/dist/js/can.stache.js"/>'></script>-->
        <link rel="stylesheet" href="<c:url value="/public/dist/css/style.css" />">
    </head>
    <body>
        <base:header></base:header>
        <jsp:doBody/>
        <base:footer></base:footer>
    </body>
</html>
