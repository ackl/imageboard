<%@tag description="Base page template" pageEncoding="UTF-8"%>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/templates" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta name="viewport" content="initial-scale = 1.0,maximum-scale = 1.0" />
        <meta charset="UTF-8">
        <title></title>
        <script src="https://code.jquery.com/jquery-1.11.2.js"></script>
        <link rel="stylesheet" href="<c:url value="/public/dist/css/style.css" />">
    </head>
    <body>
        <template:header></template:header>
        <jsp:doBody/>
        <template:footer></template:footer>
    </body>
</html>
