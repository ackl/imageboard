<%@tag description="Base page template" pageEncoding="UTF-8"%>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/templates" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title></title>
    </head>
    <body>
        <template:header></template:header>
        <jsp:doBody/>
        <template:footer></template:footer>
    </body>
</html>
