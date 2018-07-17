<html xmlns:th="http://www.thymeleaf.org" xmlns:sec="http://www.thymeleaf.org/extras/spring-security">
<head lang="en">
    <link rel="stylesheet" type="text/css" />
</head>
<body>

<div class="container">
    <form action="/logout" method="post" >
        <div class="col-sm-2" style="padding-top: 30px;">
                     <span sec:authorize="isAuthenticated()">
                    <input type="submit" value="Sign Out"/>
                     </span>
        </div>
    </form>
</div>


</body>
</html>