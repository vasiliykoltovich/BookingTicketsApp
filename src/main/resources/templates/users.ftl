<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org" >
<head>
    <meta charset="UTF-8">
    <title>Users</title>
    <link href="/css/main.css" rel="stylesheet">
</head>
<body>
<!--<th:block th:include="header :: header"></th:block>-->
    <#include "header.html">
<h2 class="users-list">Users:</h2>
<table>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Email</th>
        <th>Birthday</th>
        <th>Password</th>
        <th>Roles</th>
    </tr>
    <#list users as us>
    <tr>
        <td>${us.id}</td>
        <td>${us.name}</td>
        <td>${us.email}</td>
        <td>${us.birthday}</td>
        <td>${us.password}</td>
        <td>${us.roles}</td>
    </tr>
   </#list>
</table>



<script src="/js/main.js"></script>
</body>
</html>