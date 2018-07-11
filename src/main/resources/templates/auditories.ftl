<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Auditories</title>
    <link href="/css/main.css" rel="stylesheet">
</head>
<body>
<h2 class="events-list">Auditories:</h2>


<table>
    <tr>
        <th>Name</th>
        <th>SeatsNumber</th>
        <th>VipSeats</th>
    </tr>
    <#list auditoriums as a>
    <tr>
        <td>${a.name}</td>
        <td>${a.seatsNumber}</td>
        <td>${a.vipSeats}</td>
    </tr>
</#list>
</table>



<script src="/js/main.js"></script>
</body>
</html>