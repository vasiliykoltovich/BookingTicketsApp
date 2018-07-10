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
    <#list users as us>
    <tr>
        <td>${us.id}</td>
        <td>${us.name}</td>
        <td>${us.email}</td>
        <td>${us.birthday}</td>
    </tr>
   </#list>
</table>



<script src="/js/main.js"></script>
</body>
</html>