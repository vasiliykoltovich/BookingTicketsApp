<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Users</title>
    <link href="/css/main.css" rel="stylesheet">
</head>
<body>
<h2 class="users-list">Users:</h2>


<table>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Email</th>
        <th>Birthday</th>
    </tr>
    <#list users as user>
    <tr>
        <td>${user.id}</td>
        <td>${user.name}</td>
        <td>${user.email}</td>
        <td>${user.birthday}</td>
    </tr>
   </#list>
</table>



<script src="/js/main.js"></script>
</body>
</html>