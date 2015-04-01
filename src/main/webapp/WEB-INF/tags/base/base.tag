<%@tag description="Base page template" pageEncoding="UTF-8"%>
<%@taglib prefix="base" tagdir="/WEB-INF/tags/base" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta name="viewport" content="initial-scale = 1.0,maximum-scale = 1.0" />
        <meta charset="UTF-8">
        <title></title>
        <link rel="stylesheet" href="<c:url value="/public/dist/css/style.css" />">
        <link href='http://fonts.googleapis.com/css?family=Lato:100,300,400,700,900' rel='stylesheet' type='text/css'>
        <link href='http://fonts.googleapis.com/css?family=Inconsolata:400,700' rel='stylesheet' type='text/css'>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.js"></script>
    </head>
    <body class="light">
        <jsp:doBody/>

    <script src='<c:url value="/public/dist/js/bundle.js"/>'></script>
    </body>
</html>

